import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class accountGeneration {
    public static Connection conn;
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String PATH = "jdbc:mysql://localhost/";

    // connects to the abstract_project database
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
            e.printStackTrace();
            return false;
        }
    }

    public static String getMostRecentID(String role) {
        // use "student", "faculty", or "publicUser" for role
        return "SELECT MAX(" + role + "ID) FROM " + role;
    }

    // Returns all college ID's of colleges students/faculty can be associated with
    public String[] getCollegeIDs() {
        List<String> results = new ArrayList<>();
        try {
            
            String sql = "SELECT * FROM college ORDER BY collegeID ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add("[" + rs.getInt(1) + "] " + rs.getString(2)); // stores an array of college ID's in the format of "[[[1] Golisano], [[2] Engineering], etc.]"
            }
            return results.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return results.toArray(new String[0]);
        }
    }

    /**
    * Creates an account for the chosen user type (student, faculty, publicUser)
    * Reused in:
    *  createFacultyAccount  
    *  createStudentAccount
    *  createPublicUserAccount
    */
    public void insertAccount(String table, String un, String pswd, int id) {
        String insertAccount = "INSERT INTO account (username, password, " + table + "ID) VALUES(?, ?, ?)"; // will insert into the account table based on table type, (username, password, facultyID)
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

    // Creates a Faculty account using the faculty's created username and password, their first and last name, email, office number,
    // what college's they belong to, and what building their office is in
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


    // Creates a Student account using the user's created username and password, their first and last name, their email, what college they belong to
    // and their major
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

    // Creates a PublicUser account using a username, password, where the publicuser is (ex: Library), and their contact info
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

    // encrypts the user's password
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

    // Creates an abstract for a faculty member
    public void insertFacultyAbstract(String title, String authors, String content, int facultyID) {
        try {
            // checks if the abstract already exists so if a faculty member creates an account
            // and their abstract is already there they will be linked to it
        
            String checkAbstract = "SELECT abstractID FROM abstract WHERE title = ?";
            PreparedStatement check = conn.prepareStatement(checkAbstract);
            check.setString(1, title);
            ResultSet rs = check.executeQuery();
            int abstractID;
            if (rs.next()) {
                abstractID = rs.getInt("abstractID");
            } else {
                String sql = "INSERT INTO abstract (title, author, content) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Will allow the autogenerated abstractID to be stored in a variable
                stmt.setString(1, title);
                stmt.setString(2, authors);
                stmt.setString(3, content);
                stmt.executeUpdate();

                ResultSet generatedAbsID = stmt.getGeneratedKeys(); // gets the auto-generated id
                if (generatedAbsID.next()) {
                    abstractID = generatedAbsID.getInt(1);
                } else {
                    throw new SQLException("Abstract Not Created");
                }
            }

            // links the abstract id to the faculty member
            String fa = "INSERT INTO facultyabstract VALUES (?, ?)";
            PreparedStatement faLink = conn.prepareStatement(fa);
            faLink.setInt(1, facultyID);
            faLink.setInt(2, abstractID);
            faLink.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // inserts/modifys a user's interests based on their role (faculty, student, publicuser)
    public void insertInterests(String[] interests, String role, int id) {
        try {

            // when inserting interests, the ones that exist already are deleted at first to ensure
            // a user only has 3 interests
            String delete = "DELETE FROM " + role + "interest WHERE " + role + "ID = ?";
            PreparedStatement deletestatement = conn.prepareStatement(delete);
            deletestatement.setInt(1, id);
            deletestatement.executeUpdate();
            for (int i = 0; i < 3; i++) {
                String interest = interests[i].toLowerCase();
                String sql = "SELECT interestID FROM interest WHERE content LIKE ?";
                PreparedStatement check = conn.prepareStatement(sql);
                check.setString(1, interest);
                ResultSet rs = check.executeQuery();
                int interestID;
                if (rs.next()) {
                    interestID = rs.getInt(1);
                } else {
                    String insertInterest = "INSERT INTO interest (content) VALUES (?)";
                    PreparedStatement stmt = conn.prepareStatement(insertInterest, Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, interest);
                    stmt.executeUpdate();

                    ResultSet generatedIntID = stmt.getGeneratedKeys();
                    if (generatedIntID.next()) {
                        interestID = generatedIntID.getInt(1);
                    } else {
                        throw new SQLException("Interest Not Added");
                    }
                }

                String roleInsert = "INSERT INTO " + role + "interest VALUES (?, ?)";
                PreparedStatement roleLink = conn.prepareStatement(roleInsert);
                roleLink.setInt(1, id);
                roleLink.setInt(2, interestID);
                roleLink.executeUpdate();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    // gets the three interests a user is associated with
    public String[] getInterest(int id, String role) {
        List<String> results = new ArrayList<>();
        try {
            String sql = "SELECT content FROM interest JOIN " + 
                role + "interest USING (interestID) JOIN " + 
                role + " USING (" + role + "ID) WHERE " + role + "ID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
            return results.toArray(new String[0]);

        } catch (SQLException e) {
            e.printStackTrace();
            return new String[results.size()];
        }
    }
    
    // logs a user in if their username and password matches their account based on what role they have
    public boolean loggedIn(String username, String password, String role) {
        password = encrypt(password);
        try {
            String sql = "SELECT username, password, " + role + "ID FROM account " +
                         "WHERE username = ? and password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // returns information about the user
    // for a student, their name, email, major, etc. is stored in an array
    // for a faculty, their building and office number along with name, etc. is stored in an array
    public Object[] getBasicInformation(String username, String password, String role) {
        password = encrypt(password);
        List<String> accountResults = new ArrayList<>();
        List<Object> finalResults = new ArrayList<>();
        try {
            String sqlAcc = "SELECT username, password, " + role + "ID FROM account" +
                             " WHERE username = ? and password = ?";
            PreparedStatement accStmt = conn.prepareStatement(sqlAcc);
            accStmt.setString(1, username);
            accStmt.setString(2, password);
            ResultSet rs = accStmt.executeQuery();
            while (rs.next()) {
                accountResults.add(rs.getString(1));
                accountResults.add(rs.getString(2));
                accountResults.add(rs.getString(3));
            }
            if (role.equals("faculty")) {
                String basicFacultyInfo = "SELECT * FROM faculty where facultyID=?";
                PreparedStatement basicFacultyInfoStmt = conn.prepareStatement(basicFacultyInfo);
                basicFacultyInfoStmt.setString(1, accountResults.get(2));
                ResultSet resultsFaculty = basicFacultyInfoStmt.executeQuery();
                while (resultsFaculty.next()) {
                    finalResults.add(resultsFaculty.getInt(1));
                    finalResults.add(resultsFaculty.getString(2));
                    finalResults.add(resultsFaculty.getString(3));
                    finalResults.add(resultsFaculty.getString(4));
                    finalResults.add(resultsFaculty.getInt(5));
                    finalResults.add(resultsFaculty.getString(6));
                }
                return finalResults.toArray(new Object[0]);
            } else if (role.equals("student")) {
                String basicStudentInfo = "SELECT * FROM student where studentID=?";
                PreparedStatement basicStudentInfoStmt = conn.prepareStatement(basicStudentInfo);
                basicStudentInfoStmt.setString(1, accountResults.get(2));
                ResultSet resultsStudent = basicStudentInfoStmt.executeQuery();
                while (resultsStudent.next()) {
                    finalResults.add(resultsStudent.getInt(1));
                    finalResults.add(resultsStudent.getString(2));
                    finalResults.add(resultsStudent.getString(3));
                    finalResults.add(resultsStudent.getString(4));
                }
                return finalResults.toArray(new Object[0]);
            } else if (role.equals("publicUser")) {
                String basicPublicInfo = "SELECT * FROM publicuser WHERE publicUserID=?";
                PreparedStatement basicPublicStmt = conn.prepareStatement(basicPublicInfo);
                basicPublicStmt.setString(1, accountResults.get(2));
                ResultSet resultsPublic = basicPublicStmt.executeQuery();
                while (resultsPublic.next()) {
                    finalResults.add(resultsPublic.getInt(1));
                    finalResults.add(resultsPublic.getString(2));
                    finalResults.add(resultsPublic.getString(3));
                }
                return finalResults.toArray(new Object[0]);
            } else {
                return new Object[finalResults.size()];
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[finalResults.size()];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No Results Found");
            return new Object[finalResults.size()];
        }
    }

    // allows a faculty to delete an abstract from being associated with them while keeping the abstract
    // in the table if it is authored by someone else
    public void deleteAbstract(int facultyID, int abstractID) {
        try{
            String sql = "DELETE FROM facultyabstract WHERE facultyID=? AND abstractID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, facultyID);
            stmt.setInt(2, abstractID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // allows a student to search for an abstract based off of their interest
    // stored in an array of arrays because if there is multiple abstracts 
    // that match the interest, they will have different values that will need to 
    // be accessed
    public String[][] studentSearchAbstract(String interest){
        List<String[]> searchResults = new ArrayList<>();

        try {
            String query = "{CALL search_abstract_student(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, interest);
            ResultSet rs = stmt.executeQuery();
            
            boolean hasRs = false;
            while (rs.next()) {
                String[] indivResults = new String[5];
                hasRs = true;
                indivResults[0] = rs.getString(1);
                indivResults[1] = rs.getString(2);
                indivResults[2] = rs.getString(3);
                indivResults[3] = rs.getString(4);
                indivResults[4] = rs.getString(5);
                searchResults.add(indivResults);
            }
            
            if (!hasRs){
                System.out.println("No Abstracts Found");
            }
            return searchResults.toArray(new String[0][]); 
        } catch (SQLException e) {
            e.printStackTrace();
            return searchResults.toArray(new String[0][0]);
        }
    }

    // allows faculty to search for student information based on if they share the
    // provided interest
    // also stored in an array of arrays if there is multiple students
    public String[][] facultySearchStudents(String interest) {
        List<String[]> searchResults = new ArrayList<>();
        
        try {
            String query = "{CALL faculty_search_students(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, interest);
            ResultSet rs = stmt.executeQuery();
            boolean hasRs = false;
            while (rs.next()) {
                String[] indivResults = new String[2];
                hasRs = true;
                indivResults[0] = rs.getString(1);
                indivResults[1] = rs.getString(2);
                searchResults.add(indivResults);
            }
            
            if (!hasRs) {
                System.out.println("No students currently interested in " + interest);
            }
            return searchResults.toArray(new String[0][]);
        } catch (SQLException e) {
            e.printStackTrace();
            return searchResults.toArray(new String[0][0]);
        }
    }

    public static void main(String[] args) {
        accountGeneration cli = new accountGeneration();
        cli.connect("abstract_project", "root", "student");

        String[] arr= new String[] {"2", "3"};
        String[] interests = new String[] {"Python", "Java", "SQL"};
        String[] interests2 = new String[] {"Java", "Systems Administration", "Kerberos", "C++"};
       
        // +--------------------------------------------------------------------------------------------+
        // | UNCOMMENT ALL OF THE cli.* AND RUN WHEN DATABASE IS SOURCED, IT WILL POPULATE THE DATABASE |
        // +--------------------------------------------------------------------------------------------+

        // cli.createFacultyAccount("weisusername", "password", "Johnathon", "Weismann", "jweissman@g.rit.edu", 5432, arr, "Golisano Hall");
        // cli.createFacultyAccount("jhabermas", "password", "Jim", "Habermas", "jha@rit.edu", 1234, arr, "Golisano Hall");
        // cli.createFacultyAccount("gpvaks", "password", "Garrett", "Arcoraci", "gpvaks@rit.edu", 9765, new String[] {"2"}, "Golisano Hall");
        // cli.createStudentAccount("abc123", "newpass", "Test", "User", "abc123@gmail.com", 3, "SoftDev");
        // cli.createStudentAccount("msw7476", "studentpassword", "Michael", "Williams", "msw7476@g.rit.edu", 3, "CIT");
        // cli.createPublicUserAccount("public", "user", "Library", "3151234567");
        // cli.insertFacultyAbstract("My Abstract", "Jim Habermas, Garret Aroraci", "This is the content of my abstract", 3);
        // cli.insertFacultyAbstract("My Abstract", "Jim Habermas, Garret Aroraci", "This is the content of my abstract", 2);
        // cli.insertFacultyAbstract("Weisman Abstract", "Johnathon Weismann", "Abstract text", 1);
        // cli.insertInterests(interests, "faculty", 1);
        // cli.insertInterests(interests2, "faculty", 2);
        // cli.insertInterests(interests, "faculty", 3);
        // cli.insertInterests(interests2, "student", 1);
        // cli.insertInterests(interests, "student", 2);


        // String[] results = cli.getInterest(1, "faculty");
        // for (String result : results) {
        //     System.out.println(result);
        // }
        Object[] bi = cli.getBasicInformation("weisusernam", "password", "faculty"); //intentionally wrong to test make sure it prints no results found
        for (Object b : bi) {
            System.out.println(b + "__");
        }
        
        // String[][] ssa = cli.facultySearchStudents("sql");
        // System.out.println(Arrays.deepToString(ssa));
        // System.out.println(ssa[0][1]);
        // String[][] ssf = cli.studentSearchAbstract("java");
        // for (String[] a : ssf) {
        //     System.out.println(Arrays.toString(a));
        // }
        // cli.facultySearchStudents("Ubuntu");
    }
}