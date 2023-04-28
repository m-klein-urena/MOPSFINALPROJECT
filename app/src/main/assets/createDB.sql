.open database.db

PRAGMA foreign_keys = OFF;   
  
DROP TABLE IF EXISTS university;    
DROP TABLE IF EXISTS student;    
DROP TABLE IF EXISTS skills;    
DROP TABLE IF EXISTS student_skills;    
DROP TABLE IF EXISTS teams;    
DROP TABLE IF EXISTS team_members;    
DROP TABLE IF EXISTS projects;    
DROP TABLE IF EXISTS team_projects;    
DROP TABLE IF EXISTS team_skills;   

DROP VIEW IF EXISTS available_schools;    
DROP VIEW IF EXISTS available_skills;    
DROP VIEW IF EXISTS personal_info;   
DROP VIEW IF EXISTS matching_studentsP00001;  
DROP VIEW IF EXISTS matching_studentsP00002;  
DROP VIEW IF EXISTS matching_studentsP00003;  
DROP VIEW IF EXISTS WPI_students;  
DROP VIEW IF EXISTS CLARK_students;  
DROP VIEW IF EXISTS WSU_students;  
  
PRAGMA foreign_keys = ON;

  
CREATE TABLE university (    
  
  univ_id CHAR(4) NOT NULL,    
  
  univ_name_full NVARCHAR(100) NOT NULL,    
  
  univ_name_short NVARCHAR(20) NOT NULL,    
  
  univ_address1 NVARCHAR(40) NOT NULL,    
  
  univ_address2 NVARCHAR(40),    
  
  univ_city NVARCHAR(20) NOT NULL,    
  
  univ_state CHAR(2) NOT NULL,    
  
  univ_zip CHAR(5) NOT NULL,    
  
  univ_email NVARCHAR(40) NOT NULL,    
  
  univ_PHnum INTEGER NOT NULL,    
  
  CONSTRAINT pk_university PRIMARY KEY (univ_id)    
  
);    

CREATE INDEX idx_univ_state ON university (univ_state);  


CREATE TABLE student (    
    student_id CHAR(6) PRIMARY KEY,    
    stud_first_name NVARCHAR(20),    
    stud_last_name NVARCHAR(40),    
    stud_name_pref NVARCHAR(20),    
    stud_pronouns NVARCHAR(15),   
    stud_address1 NVARCHAR(40),    
    stud_address2 NVARCHAR(40),    
    stud_city NVARCHAR(20),    
    stud_state VARCHAR(20),    
    stud_zip CHAR(5),    
    stud_email NVARCHAR(40),    
    stud_PHnum INTEGER,   
    stud_acctcreate DATE,       
    univ_id CHAR(4),   
    FOREIGN KEY (univ_id) REFERENCES university(univ_id)   
);    

CREATE INDEX idx_stud_last ON student(stud_last_name);   

CREATE TABLE skills (    
  skill_id CHAR(5) PRIMARY KEY,    
  skill_name NVARCHAR(40),    
  skill_desc NVARCHAR(150)    
);    
CREATE TABLE student_skills (    
  student_id CHAR(6),    
  skill_id CHAR(5),    
  PRIMARY KEY (student_id, skill_id),    
  FOREIGN KEY (student_id) REFERENCES student(student_id),    
FOREIGN KEY (skill_id) REFERENCES skills(skill_id)    
);    
CREATE TABLE teams (   
  team_id CHAR(6) PRIMARY KEY,   
  team_name NVARCHAR(40),   
  Open CHAR(1),   
  Min INTEGER,   
  Max INTEGER,   
  preferred INTEGER   
);    
CREATE TABLE team_members (    
  team_id CHAR(6),    
  student_id CHAR(6),    
  PRIMARY KEY (team_id, student_id),    
  FOREIGN KEY (team_id) REFERENCES teams(team_id),    
  FOREIGN KEY (student_id) REFERENCES student(student_id)    
);    
CREATE TABLE team_skills (   
team_id CHAR(6) NOT NULL,   
  skill_id CHAR(5) NOT NULL,   
  qty INTEGER,   
  PRIMARY KEY (team_id, skill_id),   
  FOREIGN KEY (team_id) REFERENCES teams(team_id),   
  FOREIGN KEY (skill_id) REFERENCES skills(skill_id)   
);   
CREATE TABLE projects (    
  proj_id CHAR(6) PRIMARY KEY,    
  proj_title NVARCHAR(40),   
  proj_desc NVARCHAR(150),    
  proj_created_on DATE,    
  proj_end DATE,     
  proj_start DATE,   
  advisor_email NVARCHAR(40)   
);    

CREATE INDEX idx_proj_created_on ON projects(proj_created_on); 

CREATE TABLE team_projects (    
  team_id CHAR(6),    
  proj_id CHAR(6),    
  PRIMARY KEY (team_id, proj_id),    
  FOREIGN KEY (team_id) REFERENCES teams(team_id),    
  FOREIGN KEY (proj_id) REFERENCES projects(proj_id)    
);    

  
INSERT INTO university (univ_id, univ_name_full, univ_name_short, univ_address1, univ_address2, univ_city, univ_state, univ_zip, univ_email, univ_PHnum)   
VALUES  
(' ', '  ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',' ' ),  
('WPI', 'Worcester Polytechnic Institute', 'WPI', '100 Institute Road', NULL, 'Worcester', 'MA', '01609', 'admissions@wpi.edu', 5088315000),  
('CLR', 'Clark University', 'Clark', '950 Main St', NULL, 'Worcester', 'MA', '01610', 'admissions@clarku.edu', 5087937711),  
('WSU', 'Worcester State University', 'WSU', '486 Chandler St', NULL, 'Worcester', 'MA', '01602', 'admissions@worcester.edu', 5089298010);   


INSERT INTO student (student_id, stud_first_name, stud_last_name, stud_name_pref, stud_pronouns, stud_address1, stud_address2, stud_city, stud_state, stud_zip, stud_email, stud_PHnum, stud_acctcreate, univ_id)   
VALUES    
(' ', ' ', ' ', '  ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),  

('000001', 'Michael', 'Klein', 'Michael', 'he/him', '123 Main St', NULL, 'Boston', 'Massachusetts', '02110', 'mklein@wpi.edu', 5085551212, '2022-08-01', 'WPI'), 
 
('000002', 'Ozge', 'Aygul', 'Aygul', 'she/her', '456 Elm St', NULL, 'Worcester', 'Massachusetts', '01609', 'oaygul@wpi.edu', 5085551213, '2021-08-01', 'WPI'),  
 
('000003', 'Pete', 'Mohan', 'Pete', 'he/him', '789 Pine St', NULL, 'Worcester', 'Massachusetts', '01610', 'pmohan@wpi.edu', 5085551214, '2022-08-01', 'WPI'), 
 
('000004', 'Salim Eray', 'Celik', 'Salim', 'he/him', '12 Oak St', NULL, 'Worcester', 'Massachusetts', '01608', 'scelik@wpi.edu', 5085551215, '2022-08-01', 'WPI'),  
 
('000005', 'Matts', 'Jo', 'Matts', 'he/him', '34 Maple St', NULL, 'Boston', 'Massachusetts', '02110', 'mjo@clarku.edu', 5085551234, '2021-08-01', 'CLR'), 
 
('000006', 'Sahar', 'Bajgani', 'Sahar', 'she/her', '56 Walnut St', NULL, 'Worcester', 'Massachusetts', '01610', 'sbajgani@clarku.edu', 5085551235, '2022-05-02', 'CLR'), 
 
('s10456', 'Shreya', 'Shirodkar', 'Shreya', 'she/her', '123 Main St', '', 'Worcester', 'Massachusetts', '01609', 'shreya@example.com', '5085551234', '2022-08-01',  'CLR'),  
 
('s10567', 'Josh', 'Cleiman', 'Josh', 'he/him', '456 Maple Ave', '', 'Worcester', 'Massachusetts', '01604', 'josh@example.com', '5085552345', '2022-08-01', 'WSU'),  
 
('s10678', 'Leonardo', 'Coelho', 'Leo', 'he/him', '789 Elm St', '', 'Worcester', 'Massachusetts', '01605', 'leo@example.com', '5085553456', '2022-08-01', 'WSU'), 
 
('s10789', 'Alex', 'de Souza', 'Alex', 'he/him', '246 Oak Rd', '', 'Worcester', 'Massachusetts', '01606', 'alex@example.com', '5085554567', '2022-08-01', 'WSU');  
 
-- Populate Skills relation 
INSERT INTO skills (skill_id, skill_name, skill_desc)   
  
VALUES     
(' ', ' ', ' '),   
  
('SKL001', 'Python programming', 'Proficiency in Python programming language'),   
('SKL002', 'Data analysis', 'Experience in data analysis and interpretation'),   
('SKL003', 'Leadership', 'Ability to lead and manage a team effectively'),   
('SKL004', 'Project management', 'Knowledge of project management principles and tools'),   
('SKL005', 'Communication', 'Strong written and verbal communication skills'),   
('SKL006', 'Time management', 'Ability to manage time effectively and prioritize tasks'),   
('SKL007', 'Critical thinking', 'Ability to analyze information and make informed decisions'),   
('SKL008', 'Collaboration', 'Ability to work effectively with others towards a common goal'),   
('SKL009', 'Creativity', 'Ability to think creatively and develop innovative solutions'),   
('SKL010', 'Problem solving', 'Ability to identify and solve complex problems');   


INSERT INTO projects (proj_id, proj_title, proj_desc, proj_created_on, proj_end, proj_start, advisor_email)   
VALUES    
(' ', ' ', ' ', '', '1', '0 ', ' '),   
('P00001', 'Sustainable Supply Chain', 'Description of Project 1', '2023-04-24', '2023-07-31', '2023-05-01', 'advisor1@univ.edu'),   
('P00002', 'AI', 'Description of Project 2', '2023-04-24', '2023-07-31', '2023-05-01', 'advisor2@univ.edu'),   
('P00003', 'Blockchain', 'Description of Project 3', '2023-04-24', '2023-07-31', '2023-05-0B1', 'advisor3@univ.edu');   
INSERT INTO teams (team_id, team_name, Open, min, max, preferred)   
VALUES   
(' ', ' ', ' ', ' ', ' ', ' '), 
('T00001', 'GOAT', '2023-04-24', '2023-07-31', '2023-05-01', 'advisor1@univ.edu');   
  

INSERT INTO team_members (team_id, student_id)   
VALUES    
(' ',' '),   
('T00001','000001'),   
('T00001','000002'),   
('T00001','000003'),   
('T00001','000004');   

INSERT INTO student_skills ('student_id', 'skill_id')   
VALUES    
(' ',' '), 
('000001','SKL001'),
('000001','SKL002'), 
('000001','SKL003'),    
('000002','SKL004'),
('000002','SKL005'), 
('000002','SKL006'),    
('000003','SKL007'),
('000003','SKL008'), 
('000003','SKL009'),     
('000004','SKL010'),
('000004','SKL001'),      
('000004','SKL002');   


INSERT INTO team_skills ('team_id', 'skill_id', 'qty')   
VALUES    
(' ',' ',' '),  
('T00001','SKL001',2),   
('T00001','SKL002',2),   
('T00001','SKL003',2),   
('T00001','SKL004',2),   
('T00001','SKL005',2), 
('T00001','SKL006',2), 
('T00001','SKL007',2), 
('T00001','SKL008',2), 
('T00001','SKL009',2), 
('T00001','SKL010',2); 

INSERT INTO team_projects ('team_id', 'proj_id')   
VALUES    
(' ' ,' ' ), 
('T00001','P00001');   
CREATE VIEW available_schools AS   
SELECT univ_name_full AS school_name   
FROM university;   
  
CREATE VIEW available_skills AS   
SELECT skill_id, skill_name, skill_desc   
FROM skills;   
  
CREATE VIEW personal_info AS    
SELECT    
  s.stud_first_name || ' ' || s.stud_last_name AS Name,    
  s.stud_name_pref || ' (' || s.stud_pronouns || ')' AS Preferred_Name_and_Pronouns,    
  u.univ_name_full || ', ' || u.univ_address1 || ', ' || u.univ_city || ', ' || u.univ_state || ' ' || u.univ_zip AS School_and_Degree,    
  s.stud_address1 || ', ' || s.stud_address2 || ', ' || s.stud_city || ', ' || s.stud_state || ' ' || s.stud_zip AS Address,    
  s.stud_PHnum AS Phone,    
  s.stud_email AS Email   
FROM    
  student s    
  INNER JOIN university u ON s.univ_id = u.univ_id;   
  CREATE VIEW matching_studentsP00001 AS  
SELECT s.student_id, s.stud_first_name, s.stud_last_name  
FROM student s  
INNER JOIN student_skills ss ON s.student_id = ss.student_id  
INNER JOIN team_skills ts ON ss.skill_id = ts.skill_id  
INNER JOIN team_members tm ON ts.team_id = tm.team_id  
INNER JOIN team_projects tp ON tm.team_id = tp.team_id  
INNER JOIN projects p ON tp.proj_id = p.proj_id  
WHERE p.proj_id = 'P00001'  
GROUP BY s.student_id  
HAVING COUNT(DISTINCT ts.skill_id) = COUNT(DISTINCT ss.skill_id);  
  
CREATE VIEW matching_studentsP00002 AS  
SELECT s.student_id, s.stud_first_name, s.stud_last_name  
FROM student s  
INNER JOIN student_skills ss ON s.student_id = ss.student_id  
INNER JOIN team_skills ts ON ss.skill_id = ts.skill_id  
INNER JOIN team_members tm ON ts.team_id = tm.team_id  
INNER JOIN team_projects tp ON tm.team_id = tp.team_id  
INNER JOIN projects p ON tp.proj_id = p.proj_id  
WHERE p.proj_id = 'P00002'  
GROUP BY s.student_id  
HAVING COUNT(DISTINCT ts.skill_id) = COUNT(DISTINCT ss.skill_id);  
CREATE VIEW matching_studentsP00003 AS  
SELECT s.student_id, s.stud_first_name, s.stud_last_name  
FROM student s  
INNER JOIN student_skills ss ON s.student_id = ss.student_id  
INNER JOIN team_skills ts ON ss.skill_id = ts.skill_id  
INNER JOIN team_members tm ON ts.team_id = tm.team_id  
INNER JOIN team_projects tp ON tm.team_id = tp.team_id  
INNER JOIN projects p ON tp.proj_id = p.proj_id  
WHERE p.proj_id = 'P00003'  
GROUP BY s.student_id  
HAVING COUNT(DISTINCT ts.skill_id) = COUNT(DISTINCT ss.skill_id);  
  
CREATE VIEW WPI_students AS  
SELECT student_id, stud_first_name || ' ' || stud_last_name AS name, stud_pronouns  
FROM student  
WHERE univ_id = 'WPI';  
CREATE VIEW CLARK_students AS  
SELECT student_id, stud_first_name || ' ' || stud_last_name AS name, stud_pronouns  
FROM student  
WHERE univ_id = 'CLR';  
CREATE VIEW WSU_students AS  
SELECT student_id, stud_first_name || ' ' || stud_last_name AS name, stud_pronouns  
FROM student  
WHERE univ_id = 'WSU';  