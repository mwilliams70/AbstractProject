import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testCLI {
    public static Connection conn;
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String PATH = "jdbc:mysql://localhost/";

    public boolean connect(String db, String un, String pass) {
        String DATABASE = PATH + db;
        try {
            Class.forName(DRIVER);
            System.out.println("Successfully Loaded Driver: \n" + DRIVER);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could Not Load Driver: \n" + DRIVER);
        }

        if (pass.equalsIgnoreCase("")) {
            pass = "student";
        }

        try {
            conn = DriverManager.getConnection(DATABASE, un, pass);
            System.out.println("Your DB Connection works.");
            return true;
        }
        catch (SQLException e) {
            System.out.println("No DB Connected");
            return false;
        }
    }

    public void printFacultyMenu() {
        System.out.println("I am a faculty member");
    }

    public static void main(String[] args) {
        testCLI cli = new testCLI();
        cli.connect("abstract_project", "root", "student");
    }
}