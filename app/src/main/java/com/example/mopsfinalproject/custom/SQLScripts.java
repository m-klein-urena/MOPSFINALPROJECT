package com.example.mopsfinalproject.custom;

/**
 * SQL commands
 * Including select/delete/update/insert
 */

public abstract class SQLScripts
{
    /**
     * VERIFICATION AND LOGIN
     **/
//    Get a list of all current users to check login credentials
    public static String _00_GET_ALL_USERS = "SELECT student_id AS _username FROM student";


    /**
     * INSERT AND COMMIT NEW RECORDS
     **/

//    Commit new user to database
    public static String _01_COMMIT_NEW_USER = "INSERT INTO student(student_id, stud_first_name, " +
                                                           "stud_last_name, stud_name_pref, stud_pronouns, " +
                                                           "stud_address1, stud_address2, stud_city, " +
                                                           "stud_state, stud_zip, stud_email, stud_PHnum, " +
                                                           "stud_acctcreate, univ_id) " +
                                                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


//    Commit new user's skills to database
    public static String _02_INSERT_STUDENT_SKILLS = "INSERT INTO student_skills(student_id, skill_id) VALUES (?,?)";


//    Create new team
    public static String _03_INSERT_NEW_TEAM = "INSERT INTO teams(team_id, team_name, Open, Min, Max, preferred) " +
                                                    "VALUES(?,?,?,?,?,?)";

//    Create new project
    public static String _04_INSERT_NEW_PROJECT = "INSERT INTO projects(proj_id, proj_title, proj_desc, proj_created_on, proj_end, proj_start, advisor_email) " +
                                                        "VALUES(?,?,?,?,?,?,?)";


    /**
     * STUDENT DATA
     **/
//    Return student first name from student ID
    public static String _05_GET_FIRST_NAME = "SELECT stud_first_name FROM student WHERE student_id = ?";

//    Return all student attributes
    public static String _06_GET_STUDENT_DATA = "SELECT * from student WHERE student_id = ?";

//    Return all skills for a given student
    public static String _07_GET_STUDENT_SKILLS = "SELECT skills.skill_name AS _skillname FROM skills, student_skills " +
                                                        "WHERE student_id = ? AND student_skills.skill_id = skills.skill_id";


    /**
     * PROJECT AND TEAM DATA
     **/

//    Get all projects associated with a given student
    public static String _08_GET_PROJECTS = "SELECT DISTINCT projects.proj_id AS _id, projects.proj_title AS Title, projects.proj_desc AS Description, teams.team_name AS Team " +
                                                "FROM projects, team_projects, team_members, teams " +
                                                    "WHERE ? = team_members.student_id " +
                                                        "AND team_members.team_id = team_projects.team_id " +
                                                        "AND team_projects.proj_id = projects.proj_id";

//    Get all teams associated with a given student
    public static  String _09_GET_TEAMS = "SELECT teams.team_id AS _id, teams.team_name as _name " +
                                                   "FROM teams, team_members " +
                                                        "WHERE team_members.student_id = ?";


//    Return attributes associated with a given project ID
    public static String _10_GET_PROJECT_DATA = "SELECT * FROM projects WHERE proj_id = ?";


//    Get the team associated with a project
    public static String _11_GET_TEAM = "SELECT teams.team_id as _id, teams.team_name as _teamname FROM teams, team_projects " +
                                            "WHERE team_projects.proj_id = ? AND team_projects.team_id = teams.team_id";


//    Get team members associated with a given project
    public static String _12_GET_TEAM_MEMBERS = "SELECT student.student_id AS _id, student.stud_first_name as _first, student.stud_last_name as _last " +
                                                    "FROM student, team_members, team_projects " +
                                                        "WHERE team_projects.proj_id = ? " +
                                                            "AND team_projects.team_id = team_members.team_id " +
                                                            "AND team_members.student_id = student.student_id";


    public static String _13_GET_TEAM_SKILLS_NEEDED = "SELECT skills.skill_id AS _id, skills.skill_name AS _skillname " +
                                                            "FROM skills, team_skills, team_projects " +
                                                                "WHERE team_projects.proj_id = ? " +
                                                                    "AND team_projects.team_id = team_skills.team_id " +
                                                                    "AND team_skills.skill_id = skills.skill_id";


//    Get skills required for a given project
    public static String _14_GET_PROJECT_SKILLS = "SELECT skills.skill_id AS _id, skills.skill_name as _skillname " +
                                                        "FROM skills, team_projects, team_skills " +
                                                            "WHERE ? = team_projects.proj_id " +
                                                                "AND team_projects.team_id = team_skills.team_id " +
                                                                "AND team_skills.skill_id = skills.skill_id";


//    Have a student join a given project team
    public static String _15_JOIN_TEAM = "INSERT INTO team_members(team_id, student_id) VALUES (?,?)";


//    Associated a given team with a given project
    public static String _16_JOIN_PROJECT = "INSERT INTO team_projects(proj_id, team_id) VALUES (?,?)";


//    Associate skill with project
    public static String _17_ADD_PROJECT_SKILLS = "INSERT INTO team_skills(team_id, skill_id) VALUES(?,?)";


    /**
     * MATCHING QUERIES
     **/

//    For a given student, find projects that required skills that are in the student's skillset
    public static String _18_GET_PROJECT_MATCHES = "SELECT DISTINCT projects.proj_id AS _id, projects.proj_title AS Name, teams.team_name AS Team, projects.advisor_email AS Contact " +
                                                        "FROM projects, teams, student_skills, team_skills, team_projects " +
                                                            "WHERE student_skills.student_id = ? " +
                                                                "AND student_skills.skill_id = team_skills.skill_id " +
                                                                "AND team_skills.team_id = teams.team_id " +
                                                                "AND teams.team_id = team_projects.team_id " +
                                                                "AND team_projects.proj_id = projects.proj_id";


//    For a given project, return students who have skills that match the project's required skillset
    public static String _18_GET_STUDENT_MATCHES = "SELECT DISTINCT student.student_id AS _id, student.stud_first_name AS _first, student.stud_last_name AS _last " +
                                                        "FROM student, student_skills, team_skills, team_projects " +
                                                            "WHERE team_projects.proj_id = ? " +
                                                                "AND team_projects.team_id = team_skills.team_id " +
                                                                "AND student_skills.skill_id = team_skills.skill_id " +
                                                                "AND student_skills.student_id = student.student_id";

//End class
}



