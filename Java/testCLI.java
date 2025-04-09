import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static String getMostRecentID(String role) {
        return "SELECT MAX(" + role + "ID) FROM " + role;
    }

    public void createAccount(String un, String pswd, String firstName, String lastName, String email, int role) {
        switch (role) {
            case 1:
                break;
                
            case 2:
                String insertStudent = "INSERT INTO student VALUES (NULL, ?, ?, ?)";
                String getID = getMostRecentID("student");
                try (PreparedStatement stmt = conn.prepareStatement(insertStudent)) {
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setString(3, email);
                    stmt.executeUpdate();
                    PreparedStatement id = conn.prepareStatement(getID);
                    ResultSet sID = id.executeQuery();
                    if (sID.next()) {
                        String insertAccount = "INSERT INTO account (username, password, studentID) VALUES(?, ?, ?)";
                        PreparedStatement ia = conn.prepareStatement(insertAccount);
                        ia.setString(1, un);
                        ia.setString(2, pswd);
                        ia.setInt(3, sID.getInt(1));
                        ia.executeUpdate();
                    }                
                } catch (SQLException e) {
                    e.printStackTrace();                   
                }               
            case 3:
                break;
        }
    } 

    public void login(String un, String pswd) {

    }

    public static void main(String[] args) {
        testCLI cli = new testCLI();
        cli.connect("abstract_project", "root", "student");

        cli.createAccount("un", "pw", "Michael", "Williams", "msw7476@g.rit.edu", 2);
    }
}