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
        // use "student", "faculty", or "publicUser" for role
        return "SELECT MAX(" + role + "ID) FROM " + role;
    }

    public void insertAccount(String table, String un, String pswd, int id) {
        String insertAccount = "INSERT INTO account (username, password, " + table + "ID) VALUES(?, ?, ?)";
        try {
            PreparedStatement ia = conn.prepareStatement(insertAccount);
            ia.setString(1, un);
            ia.setString(2, pswd);
            ia.setInt(3, id);
            ia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void createFacultyAccount(String un, String pswd, String firstName, String lastName, String email, String department, int officeNum) {
        String insertFaculty = "INSERT INTO faculty VALUES (NULL, ?, ?, ?, ?, ?)";
        String getID = getMostRecentID("faculty");
        try (PreparedStatement stmt = conn.prepareStatement(insertFaculty)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, department);
            stmt.setInt(5, officeNum);
            stmt.executeUpdate();
            PreparedStatement id = conn.prepareStatement(getID);
            ResultSet fID = id.executeQuery();
            if (fID.next()) {
                insertAccount("faculty", un, pswd, fID.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createStudentAccount(String un, String pswd, String firstName, String lastName, String email) {
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
                insertAccount("student", un, pswd, sID.getInt(1));
            }                
        } catch (SQLException e) {
            e.printStackTrace();                   
        }               
    }
    

    public void login(String un, String pswd) {

    }

    public static void main(String[] args) {
        testCLI cli = new testCLI();
        cli.connect("abstract_project", "root", "student");

        // cli.createStudentAccount("un", "pw", "Michael", "Williams", "msw7476@g.rit.edu");
        cli.createFacultyAccount("jimhabermas", "password", "Jim", "Habermas", "jhabermas@g.rit.edu", "GCCIS", 345);
    }
}