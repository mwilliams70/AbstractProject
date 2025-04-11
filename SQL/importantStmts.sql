USE abstract_project;
SELECT a.abstractID, a.title, GROUP_CONCAT(CONCAT(f.firstName, ' ', f.lastName) SEPARATOR ', ') AS Authors
    FROM abstract a
    JOIN facultyabstract fa USING (abstractID)
    JOIN faculty f USING (facultyID)
    GROUP BY a.abstractID, a.title;