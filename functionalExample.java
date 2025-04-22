// Author:    Michael Williams
// Date:      April 18, 2025
// Class:     ISTE330
// Professor  Habermas
// Project:   Abstract Project

public class functionalExample {
    public static void main(String[] args) {
        

        // connects to database
        databaseFunctions db = new databaseFunctions();
        db.connect("abstract_project", "root", "student");

        // IF DATABASE EMPTY AND SQL SCRIPT IS NOT INSERTING DATA
        // UNCOMMENT LINES 18 - 46 AND RUN THIS SCRIPT, OTHERWISE IGNORE

        // String[] colleges = {"2", "3"};
        // String[] interests = new String[] {"Python", "Java", "SQL"};
        // String[] interests2 = new String[] {"Python", "Calculus", "Construction"};

        // // creates faculty accounts
        // db.createFacultyAccount("username", "password", "Professor", "LastName", "pl@g.rit.edu", 1234, colleges, "Golisano Hall");
        // db.createFacultyAccount("username2", "password", "NewProfessor", "NewLast", "nn@g.rit.edu", 3456, new String[] {"4"}, "Gleason Hall");
        // db.createFacultyAccount("username3", "pass3", "Another", "Professor", "ap@rit.edu", 3214, colleges, "Golisano Hall");

        // // creates student accounts
        // db.createStudentAccount("msw7476", "mypassword", "Michael", "Williams", "msw7476@g.rit.edu", 3, "CIT");
        // db.createStudentAccount("abc123", "newpass", "Test", "User", "abc123@g.rit.edu", 5, "Engineer");

        // // creates a public user account
        // db.createPublicUserAccount("public", "user", "MVCC Library", "123-456-7890");

        // // inserts abstracts for faculty members
        // db.insertFacultyAbstract("Professor Abstract", "NewProfessor NewLast", "This is my abstract", 2);
        // db.insertFacultyAbstract("Two Authors", "Professor Lastname, Another Professor", "We both wrote this abstract", 1);
        // db.insertFacultyAbstract("Two Authors", "Professor Lastname, Another Professor", "We both wrote this abstract", 3);

        // // inserts interests for faculty members
        // db.insertInterests(interests, "faculty", 1);
        // db.insertInterests(interests, "faculty", 3);
        // db.insertInterests(interests2, "faculty", 2);
        
        // // inserts interests for students 
        // db.insertInterests(interests, "student", 1);
        // db.insertInterests(interests2, "student", 2);

        // prints out a faculty members interests
        System.out.println("\nFaculty Members Interests: ");
        String[] aFacultyInterests = db.getInterest(1, "faculty");
        for (String result : aFacultyInterests) {
            System.out.println("\tInterest: " + result);
        }
        
        // prints out colleges in the database
        System.out.println("\nColleges in Database: ");
        String[] collegeIDS = db.getCollegeIDs();
        for (String college : collegeIDS) {
            System.out.println("\t" + college);
        }

        // prints out all basic info related to chosen user, in this case faculty member with the username: "username" and password: "password"
        System.out.println("\nBasic Information of a faculty member:");
        Object[] basicInfo = db.getBasicInformation("username", "password", "faculty");
        for (Object info : basicInfo) {
            System.out.println("\tInfo: " + info);
        }

        // prints out results when faculty searches for students interested in python
        System.out.println("\nResults when searching for students interested in python: ");
        String[][] searchForStudents = db.facultySearchStudents("python");
        for (String[] student : searchForStudents) {
            System.out.println("\tName: " + student[0]);
            System.out.println("\teMail: " + student[1] + "\n\n");
        }

        
        System.out.println("\nStudent information of a student interested in Calculus");
        String[][] searchForOneStudent = db.facultySearchStudents("Calculus");
        for (String[] student : searchForOneStudent) {
            System.out.println("\tName: " + student[0]);
            System.out.println("\teMail: " + student[1] + "\n");
        }

        System.out.println("\nFaculty information for a student interested in Python");
        String[][] studentSearchAbstract = db.studentSearchAbstract("python");
        for (String[] abst : studentSearchAbstract) {
            System.out.println("\tTitle: " + abst[0]);
            System.out.println("\tAuthor(s): " + abst[1]);
            System.out.println("\tContent: " + abst[2]);
            System.out.println("\tEmail(s): " + abst[3]);
            System.out.println("\tBuilding(s) and Office(s): " + abst[4] + "\n\n");
            
        }

    }
}
