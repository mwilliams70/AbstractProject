/*
Names: Ben Arbelo, Manaswini Singh, 
       Akshay Sreekumar, Kyle Weishaar,
       Michael Williams

Class: ISTE330.01
Abstract Project
Professor Habermas
*/

DROP DATABASE IF EXISTS abstract_project;
CREATE DATABASE abstract_project;
USE abstract_project;

CREATE TABLE Faculty (
    facultyID INT AUTO_INCREMENT PRIMARY KEY, 
    firstName VARCHAR(20),
    lastName VARCHAR(20),
    email VARCHAR(50),
    officeNum VARCHAR(50),
    buildingName VARCHAR(100)
);
CREATE TABLE Interest (
    interestID INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(25)
);
CREATE TABLE College (
    collegeID INT PRIMARY KEY,
    cName VARCHAR(75)
);


CREATE TABLE FacultyInterest (
    facultyID INT,
    interestID INT,
    PRIMARY KEY (facultyID, interestID),
    FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID),
    FOREIGN KEY (interestID) REFERENCES Interest(interestID)
);


CREATE TABLE CollegeFaculty (
    collegeID INT,
    facultyID INT,
    PRIMARY KEY (collegeID, facultyID),
    FOREIGN KEY (collegeID) REFERENCES College(collegeID),
    FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID)
);

CREATE TABLE Abstract (
    abstractID INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
    author VARCHAR(50),
    content TEXT
);

CREATE TABLE FacultyAbstract (
    facultyID INT,
    abstractID INT,
    PRIMARY KEY (facultyID, abstractID),
    FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID),
    FOREIGN KEY (abstractID) REFERENCES Abstract(abstractID)
);

CREATE TABLE PublicUser (
    publicUserID INT AUTO_INCREMENT PRIMARY KEY, 
    organizationName VARCHAR(50),
    contactInfo VARCHAR(100)
);

CREATE TABLE PublicUserInterest (
    publicUserID INT,
    interestID INT,
    PRIMARY KEY (publicUserID, interestID),
    FOREIGN KEY (publicUserID) REFERENCES PublicUser(publicUserID),
    FOREIGN KEY (interestID) REFERENCES Interest(interestID)
);

CREATE TABLE Student (
    studentID INT AUTO_INCREMENT PRIMARY KEY, 
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(50)
);

CREATE TABLE StudentMajor (
    collegeID INT,
    studentID INT,
    major VARCHAR(50),
    PRIMARY KEY (collegeID, studentID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID),
    FOREIGN KEY (collegeID) REFERENCES College(collegeID)
);

CREATE TABLE StudentInterest (
    studentID INT,
    interestID INT,
    PRIMARY KEY (studentID, interestID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID),
    FOREIGN KEY (interestID) REFERENCES Interest(interestID)
);

CREATE TABLE Account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255),
    facultyID INT,
    studentID INT,
    publicUserID INT,
    FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID),
    FOREIGN KEY (publicUserID) REFERENCES PublicUser(publicUserID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
);


-- INSERT VALUES INTO THE college TABLE
INSERT INTO college VALUES(1, "College of Art and Design");
INSERT INTO college VALUES(2, "Saunders College of Business");
INSERT INTO college VALUES(3, "Golisano College of Computing and Information Sciences");
INSERT INTO college VALUES(4, "Kate Gleason College of Engineering");
INSERT INTO college VALUES(5, "College of Engineering Technology");
INSERT INTO college VALUES(6, "College of Health and Sciences and Technology");
INSERT INTO college VALUES(7, "College of Liberal Arts");
INSERT INTO college VALUES(8, "National Technical Institute for the Deaf");
INSERT INTO college VALUES(9, "College of Science");
INSERT INTO college VALUES(10, "Golisano Institute for Sustainability");
INSERT INTO college VALUES(11, "School of Individualized Study");
