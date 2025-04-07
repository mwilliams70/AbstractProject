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
    department VARCHAR(50),
    officeNum VARCHAR(50)
);

CREATE TABLE FacultyInterest (
    facultyID INT AUTO_INCREMENT PRIMARY KEY,
    interestID INT AUTO_INCREMENT
);

CREATE TABLE Interest (
    interestID INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT
);

CREATE TABLE CollegeFaculty (
    collegeID INT PRIMARY KEY,
    facultyID INT
);

CREATE TABLE College (
    collegeID INT PRIMARY KEY,
    cName VARCHAR(75),
    buildingNum INT
);