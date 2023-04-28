package com.example.mopsfinalproject.custom;

/**
 * This custom class contains all the static methods used for database-related tasks in this app.
 *
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mopsfinalproject.R;
import com.example.mopsfinalproject.util.DBOperator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class DBOPS {


//    Generate string from string array for display in TextViews
    public static String ArrayToString (String[] array) {
        String line = "";
        int size = array.length;
        int i = 0;

        for (String str : array) {
            line += str;
            if (i < size - 1) line += "\n";
            i++;
        }

        return line;
    }


//    From a given SQL record, return requested attributes and their values as a hash map for easy retrieval
    public static Map<String, String> AttributesToHashMap (String[] attributes, String sql, String[] args) {
        Map<String, String> map = new HashMap<>();
        String val;

        for (int i = 0; i < attributes.length; i++) {
            Cursor cursor = DBOperator.getInstance().execQuery(sql, args);

            if (cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(attributes[i]);
                val = cursor.getString(idx);
                cursor.close();
            } else val = "ukn";

            map.put(attributes[i], val);
        }

        return map;
//   End AttributesToMap
    }


//    Checks that input skill spinner values are distinct
    public static boolean checkSkillSpinners (Context context, Spinner spinnerSkill1, Spinner spinnerSkill2, Spinner spinnerSkill3) {
        String Skill1 = DBOPS.getSkillID(context, spinnerSkill1.getSelectedItem().toString());
        String Skill2 = DBOPS.getSkillID(context, spinnerSkill2.getSelectedItem().toString());
        String Skill3 = DBOPS.getSkillID(context, spinnerSkill3.getSelectedItem().toString());

        String[] skills = new String[] {Skill1, Skill2, Skill3};

        if (isDuplicateSkillset(skills)) {
            Toast.makeText(context,"Please select distinct skills.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


//    Commit new student personal data to database
    public static void CommitNewStudent (String[] args) {
        DBOperator.getInstance().execSQL(SQLScripts._01_COMMIT_NEW_USER, args);

    }


//    Commit new team skills to database
    public static void CommitProjectSkills(String teamID, String[] skills) {
        for (String skill : skills) {
            String[] args = new String[] {teamID, skill};
            DBOperator.getInstance().execSQL(SQLScripts._17_ADD_PROJECT_SKILLS, args);
        }
    }


//    Commit new student skill data to database
    public static void CommitStudentSkills(String studentID, String[] skills) {
        for (String skill : skills) {
            String[] args = new String[] {studentID, skill};
            DBOperator.getInstance().execSQL(SQLScripts._02_INSERT_STUDENT_SKILLS, args);

        }
    }


//    Return today's date as SQL-ready string
    public static String DateToday() {
        String formatString = "YYYY-MM-dd";
        DateFormat formatDate = new SimpleDateFormat(formatString);

        Date today = Calendar.getInstance().getTime();
        return formatDate.format(today);
    }


//    Generate a random n-length string ID with alphanumeric characters
    public static String GenerateID(int n) {
        StringBuilder buildID = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rnd = new Random();

        while (buildID.length() < n) {
            int idx = (int) (rnd.nextFloat() * chars.length());
            buildID.append(chars.charAt(idx));
        }

        String stringID = buildID.toString();
        return stringID;
    }


//    Return a list of all active users
    public static String[] getAllUsers() {
        return getAttributeCol(SQLScripts._00_GET_ALL_USERS, "_username");
    }


//    Return string array for a given SQL query result and a given column name
    public static String[] getAttributeCol(String sql, String col) {
        String[] results;

        Cursor cursor = DBOperator.getInstance().execQuery(sql);
        int size = cursor.getCount();

        results = new String[size];

        cursor.moveToFirst();
        int i = 0;
        while (cursor.isAfterLast() == false && i < size) {
            int idx = cursor.getColumnIndex(col);
            results[i] = cursor.getString(idx);
            i++;
            cursor.moveToNext();
        }
        cursor.close();

        return results;

    }


//    Overloaded previous method in cases where SQL arguments are required
    public static String[] getAttributeCol(String sql, String col, String[] args) {
        String[] results;

        Cursor cursor = DBOperator.getInstance().execQuery(sql, args);
        int size = cursor.getCount();

        results = new String[size];

        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast() && i < size) {
            int idx = cursor.getColumnIndex(col);
            results[i] = cursor.getString(idx);
            i++;
            cursor.moveToNext();
        }
        cursor.close();

        return results;

    }


//    Generate hashmap of school ID keys
    public static Map <String, String> getSchoolID(Context context) {
        Map<String, String> map = new HashMap<>();

        map.put(context.getString(R.string.schoolWPI), "WPI");
        map.put(context.getString(R.string.schoolWSU), "WSU");
        map.put(context.getString(R.string.schoolClark), "CLR");

        return map;
    }


//    Get school ID from a given school name
    public static String getSchoolID(Context context, String key) {
        Map<String, String> map = DBOPS.getSchoolID(context);

        return map.get(key);
    }


//    Get school name from a given school ID
    public static  String getSchoolName(Context context, String schoolID) {
        Map<String, String> keys = DBOPS.getSchoolID(context);
        Map<String, String> schools = new HashMap<>();

        for (Map.Entry<String, String> entry : keys.entrySet()) {
            schools.put(entry.getValue(), entry.getKey());
        }

        return schools.get(schoolID);
    }


//    Return skill ID from a given skill name
    public static String getSkillID(Context context, String key) {
        Map<String, String> map = DBOPS.getSkillMap(context);

        return map.get(key);
    }


//    Generate hashmap of skill ID keys
    public static Map<String, String> getSkillMap (Context context){
        Map<String, String> map = new HashMap<>();

        map.put(context.getString(R.string.skill01), "SKL001");
        map.put(context.getString(R.string.skill02), "SKL002");
        map.put(context.getString(R.string.skill03), "SKL003");
        map.put(context.getString(R.string.skill04), "SKL004");
        map.put(context.getString(R.string.skill05), "SKL005");
        map.put(context.getString(R.string.skill06), "SKL006");
        map.put(context.getString(R.string.skill07), "SKL007");
        map.put(context.getString(R.string.skill08), "SKL008");
        map.put(context.getString(R.string.skill09), "SKL009");
        map.put(context.getString(R.string.skill10), "SKL010");

        return map;
    }


//    Get student name from ID
    public static String getStudentName(String studentID) {
        String name;
        String [] args = new String[] {studentID};

        Cursor cursor = DBOperator.getInstance().execQuery(SQLScripts._05_GET_FIRST_NAME, args);

        if (cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex("stud_first_name");
            name = cursor.getString(idx);
            cursor.close();
            return name;
        }

        return "";
    }


//    Return string array of student's skills
    public static String[] getStudentSkills(String studentID) {
        String[] skills = new String[3];


        Cursor cursor = DBOperator.getInstance().execQuery(SQLScripts._07_GET_STUDENT_SKILLS, new String[] {studentID});

        cursor.moveToFirst();
        int i = 0;
        while (cursor.isAfterLast() == false) {
            int idx = cursor.getColumnIndex("_skillname");
            skills[i] = cursor.getString(idx);
            i++;
            cursor.moveToNext();
        }
        cursor.close();

        return skills;
    }


    //    Check that input skillset doesn't contain duplicate entries
    public static boolean isDuplicateSkillset(String[] skills)
    {
        List<String> skillList = Arrays.asList(skills);

        if (new HashSet<>(skillList).size() < skills.length) {
            return true;
        }

        return false;
    }


    //    Check whether user is already part of project team
    public static boolean isInProject(String userID, String projectID) {
        List listProjects = Arrays.asList(getAttributeCol(SQLScripts._08_GET_PROJECTS, "_id", new String[] {userID}));

        if (listProjects.contains(projectID)) {
            return true;
        } else return false;
    }


    //    Check that project start date is before end date
    public static Boolean isValidDate(Context context, DatePicker start, DatePicker end) {
        int yearStart = start.getYear();
        int monthStart = start.getMonth();
        int dayStart = start.getDayOfMonth();

        int yearEnd = end.getYear();
        int monthEnd = end.getMonth();
        int dayEnd = end.getDayOfMonth();

        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();

        calStart.set(yearStart, monthStart, dayStart);
        calEnd.set(yearEnd, monthEnd, dayEnd);

        if (!calStart.before(calEnd)) {
            Toast.makeText(context,"Project start date must be before end date.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    //    Join student with project
    public static void JoinProject(String studentID, String projectID) {
        String teamID = DBOPS.getAttributeCol(SQLScripts._11_GET_TEAM, "_id", new String[] {projectID})[0];

        String [] argsJoinTeam = new String[] {teamID, studentID};

        DBOperator.getInstance().execSQL(SQLScripts._15_JOIN_TEAM, argsJoinTeam);
        System.out.println("USER " + studentID + "SUCCESSFULLY JOINED TEAM " + teamID);

    }


//    Packs student and project ID data into a delimited string that can be packed and passed between activities
    public static void PackExtras(Intent intent, String studentID, String projectID) {
        StringBuilder msg = new StringBuilder();
        msg.append(studentID);
        msg.append("$");
        msg.append(projectID);

        intent.putExtra("student_prjID", msg.toString());
    }


//    Return array of project attributes
    public static String[] projectAttributes() {
        return new String[] {
                "proj_id",
                "proj_title",
                "proj_desc",
                "proj_created_on",
                "proj_end",
                "proj_start",
                "advisor_email"
        };
    }


//    Return array of student attributes
    public static String[] studentAttributes() {
        return new String[] {
                "student_id",
                "stud_first_name",
                "stud_last_name",
                "stud_name_pref",
                "stud_pronouns",
                "stud_address1",
                "stud_address2",
                "stud_city",
                "stud_state",
                "stud_zip",
                "stud_email",
                "stud_PHnum",
                "stud_acctcreate",
                "univ_id"
        };
    }


//    From a given student ID, return a hash map with the student's attributes
    public static Map<String, String> StudentToHashMap (String studentID) {
        Map<String, String> map = new HashMap<>();
        String[] attributes = DBOPS.studentAttributes();
        String val;

        for (int i = 0; i < attributes.length; i++) {
            Cursor cursor = DBOperator.getInstance().execQuery(SQLScripts._06_GET_STUDENT_DATA, new String[] {studentID});

            if (cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(attributes[i]);
                val = cursor.getString(idx);
                cursor.close();
            } else val = "ukn";

            map.put(attributes[i], val);
        }

        return map;

//        End StudentToHashMap
    }

//End of class
}
