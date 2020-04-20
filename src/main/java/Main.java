import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void main(String[] args) throws SQLException {

        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        System.out.println(create());

//       System.out.println(update());ГШНГШЩ4   43ЩЗЩЗ 4ЩЗЖЖДЖ+яЩЗ

//        System.out.println(select(1009));

//        System.out.println(delete(1016));

        FileReader reader = new FileReader();
        List<String> list1 = reader.fileDataToListPacker();
        System.out.println(list1);
        System.out.println(list1.get(0));
        try {
            reader.putDataToDB(3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        disconnect();

    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
        stmt = connection.createStatement();

    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int newTable() throws SQLException {
        return stmt.executeUpdate("CREATE TABLE students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "score TEXT)");
    }

    public static void putDataToDB(String participant, int participantsQuantity, int racePlace) throws SQLException {
//        return stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob1','101')");

        pstmt = connection.prepareStatement("INSERT INTO students (name, score)" +
                "VALUES (?, ?);");
        connection.setAutoCommit(false);
        for (int i = 0; i < participantsQuantity; i++) {
            pstmt.setString(1, participant + i);
            pstmt.setInt(2, 10*racePlace);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        connection.setAutoCommit(true);
    }

    public static int updateScoreByID() throws SQLException {
        return stmt.executeUpdate("UPDATE students SET score = '202' WHERE id = '1009'");
    }

    public static String getDataByID(int id) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT id, name, score FROM students WHERE id = '" + id + "'");
        return rs.getString("name") + " " + rs.getString("score");
    }

    public static int delete(int id) throws SQLException {
        return stmt.executeUpdate("DELETE from students WHERE id = '" + id + "'");
    }
}
