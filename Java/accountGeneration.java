import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class accountGeneration {
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
    public void insertCollegeBuilding(int collegeID, String buildingName) {
        String sql = "INSERT INTO building (buildingName, collegeID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, buildingName);
            stmt.setInt(2, collegeID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFacultyAccount(String un, String pswd, String firstName, String lastName, String email, int officeNum, String[] collegeIDs, String buildingName) {
        String insertFaculty = "INSERT INTO faculty VALUES (NULL, ?, ?, ?, ?, ?)";
        String getID = getMostRecentID("faculty");
        try (PreparedStatement stmt = conn.prepareStatement(insertFaculty)) {
            // Insert a faculty record
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setInt(4, officeNum);
            stmt.setString(5, buildingName);
            stmt.executeUpdate();
            
            PreparedStatement id = conn.prepareStatement(getID);
            ResultSet fID = id.executeQuery(); // gets the faculty's id

            // Inserts an account record
            if (fID.next()) {
                insertAccount("faculty", un, pswd, fID.getInt(1));
            }

            PreparedStatement deptIDstmt = conn.prepareStatement("INSERT INTO collegefaculty VALUES (?, ?)");
            for (String collegeIDStr : collegeIDs) {
                int collegeID = Integer.parseInt(collegeIDStr);
                deptIDstmt.setInt(1, collegeID);     // Correct: collegeID
                deptIDstmt.setInt(2, fID.getInt(1)); // Correct: facultyID
                deptIDstmt.executeUpdate();
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

    public void createPublicUserAccount(String un, String pswd, String orgName, String contactInfo) {
        String insertPublicUser = "INSERT INTO publicuser VALUES (NULL, ?, ?)";
        String getID = getMostRecentID("publicUser");
        try (PreparedStatement stmt = conn.prepareStatement(insertPublicUser)) {
            stmt.setString(1, orgName);
            stmt.setString(2, contactInfo);
            stmt.executeUpdate();
            PreparedStatement id = conn.prepareStatement(getID);
            ResultSet puID = id.executeQuery();
            if (puID.next()) {
                insertAccount("publicUser", un, pswd, puID.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        accountGeneration cli = new accountGeneration();
        cli.connect("abstract_project", "root", "student");

        String[] arr= new String[] {"2", "3"};
        cli.createFacultyAccount("professor", "password", "JIM", "Habermas", "asdklfjasdkljf", 2342 ,arr, "Golisano Hall");
    }
}