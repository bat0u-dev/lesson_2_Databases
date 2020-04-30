import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    private static final String FILE_PATH = "C://Users/User/Desktop/Coding{}/University/DZ_update.txt";
    private Scanner sc;
    private Statement st;
    private List<String> list;
    Connection connection;

    public List<String> fileDataToListPacker() throws SQLException {
        list = new ArrayList<>();
        try {
            sc = new Scanner(new File(FILE_PATH));
            while (sc.hasNext()) {
                list.add(sc.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void putDataToDB(int columnQuantity) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
        
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO students (id, name, score)" +
                "VALUES (?, ?, ?);");
        connection.setAutoCommit(false);
        for (int i = 0; i < list.size() - columnQuantity + 1; i += columnQuantity) {
            pstmt.setString(1, list.get(i));
            pstmt.setString(2, list.get(i + 1));
            pstmt.setString(3, list.get(i + 2));
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        connection.setAutoCommit(true);
    }
}
