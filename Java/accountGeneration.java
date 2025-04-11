import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

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
                insertAccount("faculty", un, encrypt(pswd), fID.getInt(1));
            }

            // inserts records for what colleges the faculty is associated with
            // need to fix so that only 3 are read
            PreparedStatement deptIDstmt = conn.prepareStatement("INSERT INTO collegefaculty VALUES (?, ?)");
            for (String collegeIDStr : collegeIDs) {
                int collegeID = Integer.parseInt(collegeIDStr);
                deptIDstmt.setInt(1, collegeID);     
                deptIDstmt.setInt(2, fID.getInt(1)); 
                deptIDstmt.executeUpdate();
            }   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createStudentAccount(String un, String pswd, String firstName, String lastName, String email, int collegeID, String major) {
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
                insertAccount("student", un, encrypt(pswd), sID.getInt(1));
            }     
            PreparedStatement collegeStmt = conn.prepareStatement("INSERT INTO studentmajor VALUES (?, ?, ?)");
            collegeStmt.setInt(1, collegeID);
            collegeStmt.setInt(2, sID.getInt(1));
            collegeStmt.setString(3, major);
            collegeStmt.executeUpdate();
                   
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
                insertAccount("publicUser", un, encrypt(pswd), puID.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String secret){//Endcypt password
        String sha1 = "";
        String value = new String(secret);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
   	    digest.reset();
   	    digest.update(value.getBytes("utf8"));
   	    sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
   	    }
   	    return sha1;
    }

    public void insertFacultyAbstract(String title, String author, String content, int facultyID) {
        try {
            String checkAbstract = "SELECT abstractID FROM abstract WHERE title = ?";
            PreparedStatement check = conn.prepareStatement(checkAbstract);
            check.setString(1, title);
            ResultSet rs = check.executeQuery();
            int abstractID;
            if (rs.next()) {
                abstractID = rs.getInt("abstractID");
            } else {
                String sql = "INSERT INTO abstract (title, content) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, title);
                stmt.setString(2, content);
                stmt.executeUpdate();

                ResultSet generatedAbsID = stmt.getGeneratedKeys();
                if (generatedAbsID.next()) {
                    abstractID = generatedAbsID.getInt(1);
                } else {
                    throw new SQLException("Abstract Not Created");
                }
            }
            String fa = "INSERT INTO facultyabstract VALUES (?, ?)";
            PreparedStatement faLink = conn.prepareStatement(fa);
            faLink.setInt(1, facultyID);
            faLink.setInt(2, abstractID);
            faLink.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        accountGeneration cli = new accountGeneration();
        cli.connect("abstract_project", "root", "student");

        String[] arr= new String[] {"2", "3"};
        String p = null;
        cli.createFacultyAccount("professor", "password", "JIM", "Habermas", "asdklfjasdkljf", 2342 ,arr, "Golisano Hall");
        cli.createFacultyAccount("professor2", "garretpassword", "Garret", "Arrorcaci", "gpvaks@g.rit.edu", 789, arr, "Golisano Hall");
        // cli.createStudentAccount("msw7476", "studentpassword", "Michael", "Williams", "msw7476@g.rit.edu", 3, "CIT");
        cli.insertFacultyAbstract("My Abstract", "Jim Habermas", "This is the content of my abstract", 1);
        cli.insertFacultyAbstract("My Abstract", "Garret", "This is the content of my abstract", 2);
    }
}