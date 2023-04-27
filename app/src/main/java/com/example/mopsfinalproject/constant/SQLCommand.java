package com.example.mopsfinalproject.constant;

/**
 * SQL commands
 * Including select/delete/update/insert
 */

public abstract class SQLCommand
{
    //query all students
    public static String QUERY_STUDENT = "select stid, stname from Student";

    public static String QUERY_01 = "SELECT lbcallnum, lbtitle FROM libbook";

    public static String QUERY_02 = "SELECT lbcallnum FROM libbook WHERE lbtitle LIKE '%Database Management%'";

//    Query 03: Show all students who have checked out a book
    public static String QUERY_03 = "SELECT DISTINCT Student.stid, Student.stname " +
                                        "FROM CheckOut, Student " +
                                        "WHERE CheckOut.stid = Student.stid";

//    Query 04: Show books that haven't been checked out
    public static String QUERY_04 = "SELECT lbcallnum, lbtitle " +
                                        "FROM LibBook " +
                                        "WHERE lbcallnum NOT IN " +
                                        "(SELECT DISTINCT lbcallnum FROM CheckOut)";

//    Query 05: Show how much students owe in fines
    public static String QUERY_05 = "SELECT Student.stname, SUM(CheckOut.cofine) " +
                                        "FROM Student, CheckOut " +
                                        "WHERE Student.stid = Checkout.stid " +
                                        "GROUP BY CheckOut.stid";

//    Query 06: Show how many times each book has been checked out
    public static String QUERY_06 = "SELECT LibBook.lbtitle, COUNT(CheckOut.lbcallnum) " +
                                        "FROM LibBook LEFT JOIN CheckOut " +
                                            "ON LibBook.lbcallnum = CheckOut.lbcallnum " +
                                        "GROUP BY LibBook.lbtitle";

//    Query 07: Show how many times each student has checked out a book.
    public static String QUERY_07 = "SELECT Student.stname, COUNT(CheckOut.stid) " +
                                        "FROM Student LEFT JOIN CheckOut " +
                                            "ON Student.stid = CheckOut.stid " +
                                        "GROUP BY Student.stname";

    public static String RETURN_BOOK = "UPDATE CheckOut SET coreturned=? " +
                                            "WHERE stid=? AND lbcallnum=?";

    public static String CHECK_BOOK = "INSERT INTO CheckOut(coreturned, stid, lbcallnum, coduedate) " +
                                        "VALUES(?,?,?,?)";

    public static String _00_COMMIT_NEW_USER = "INSERT INTO student(student_id, stud_first_name, " +
                                                           "stud_last_name, stud_name_pref, stud_pronouns, " +
                                                           "stud_address1, stud_address2, stud_city, " +
                                                           "stud_state, stud_zip, stud_email, stud_PHnum, " +
                                                           "stud_acctcreate, univ_id) " +
                                                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String _01_INSERT_STUDENT_SKILLS = "INSERT INTO student_skills(student_id, skill_id) VALUES (?,?)";

    public static String _01_GET_FIRST_NAME = "SELECT stud_first_name FROM student WHERE student_id = ?";

    public static String _00_GET_STUDENT_DATA = "SELECT * from student WHERE student_id = ?";

    public static String _00_GET_STUDENT_SKILLS = "SELECT skills.skill_name AS _skillname FROM skills, student_skills WHERE student_id = ? AND student_skills.skill_id = skills.skill_id";

    public static String _00_GET_ATTRIBUTE = "SELECT ? AS _attr FROM ? WHERE ? = ?";

    public static String _00_GET_PROJECTS = "SELECT DISTINCT projects.proj_id AS _id, projects.proj_title AS Title, projects.proj_desc AS Description, teams.team_name AS Team " +
                                                "FROM projects, team_projects, team_members, teams " +
                                                "WHERE ? = team_members.student_id " +
                                                    "AND team_members.team_id = team_projects.team_id " +
                                                    "AND team_projects.proj_id = projects.proj_id";

    public static  String _00_GET_TEAMS = "SELECT teams.team_id AS _id, teams.team_name as _name " +
                                                   "FROM teams, team_members " +
                                                        "WHERE team_members.student_id = ?";

    public static String _00_GET_ALL_USERS = "SELECT student_id AS _username FROM student";

    public static String _00_GET_PROJECT_MATCHES = "SELECT DISTINCT projects.proj_id AS \"_id\", projects.proj_title AS Name, teams.team_name AS Team, projects.advisor_email AS Contact " +
                                                "FROM projects, teams, student_skills, team_skills, team_projects " +
                                                    "WHERE student_skills.student_id = ? " +
                                                    "AND student_skills.skill_id = team_skills.skill_id " +
                                                    "AND team_skills.team_id = teams.team_id " +
                                                    "AND teams.team_id = team_projects.team_id " +
                                                    "AND team_projects.proj_id = projects.proj_id";

    public static String _00_GET_PROJECT_DATA = "SELECT * FROM projects WHERE proj_id = ?";

    public static String _00_GET_TEAM = "SELECT teams.team_id as _id, teams.team_name as _teamname FROM teams, team_projects " +
                                            "WHERE team_projects.proj_id = ? AND team_projects.team_id = teams.team_id";

    public static String _00_GET_TEAM_MEMBERS = "SELECT student.student_id AS _id, student.stud_first_name as _first, student.stud_last_name as _last " +
                                                    "FROM student, team_members, team_projects " +
                                                    "WHERE team_projects.proj_id = ? " +
                                                    "AND team_projects.team_id = team_members.team_id " +
                                                    "AND team_members.student_id = student.student_id";

    public static String _00_GET_TEAM_SKILLS_NEEDED = "SELECT skills.skill_id AS _id, skills.skill_name AS _skillname FROM skills, team_skills, team_projects " +
                                                            "WHERE team_projects.proj_id = ? AND team_projects.team_id = team_skills.team_id " +
                                                                "AND team_skills.skill_id = skills.skill_id";

    public static String _00_JOIN_TEAM = "INSERT INTO team_members(team_id, student_id) VALUES (?,?)";

    public static String _00_JOIN_PROJECT = "INSERT INTO project_teams(proj_id, team_id) VALUES (?,?)";

    public static String _00_GET_STUDENT_MATCHES = "SELECT DISTINCT student.student_id AS _id, student.stud_first_name AS _first, student.stud_last_name AS _last " +
                                                        "FROM student, student_skills, team_skills, team_projects " +
                                                            "WHERE team_projects.proj_id = ? " +
                                                                "AND team_projects.team_id = team_skills.team_id " +
                                                                "AND student_skills.skill_id = team_skills.skill_id " +
                                                                "AND student_skills.student_id = student.student_id";

    public static String _00_GET_PROJECT_SKILLS = "SELECT skills.skill_id AS _id, skills.skill_name as _skillname " +
                                                        "FROM skills, team_projects, team_skills " +
                                                        "WHERE ? = team_projects.proj_id " +
                                                            "AND team_projects.team_id = team_skills.team_id " +
                                                            "AND team_skills.skill_id = skills.skill_id";




}



