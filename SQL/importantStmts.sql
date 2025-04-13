USE abstract_project;
SELECT a.abstractID, a.title, GROUP_CONCAT(CONCAT(f.firstName, ' ', f.lastName) SEPARATOR ', ') AS Authors
    FROM abstract a
    JOIN facultyabstract fa USING (abstractID)
    JOIN faculty f USING (facultyID)
    GROUP BY a.abstractID, a.title;


SELECT CONCAT(f.firstName, ' ', f.lastName) AS Faculty, GROUP_CONCAT(i.content) AS Interests 
    FROM faculty f
    JOIN facultyinterest USING (facultyID)
    JOIN interest i USING (interestID)
    GROUP BY f.facultyID;

select content FROM interest 
    JOIN facultyinterest USING (interestID)
    JOIN faculty USING(facultyID) 
    where facultyID=2

SELECT * FROM account
    WHERE facultyID IS NOT NULL
    OR studentID IS NOT NULL
    OR publicUserID IS NOT NULL;