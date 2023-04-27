package com.example.mopsfinalproject.custom;

import android.content.Context;
import android.database.Cursor;

import com.example.mopsfinalproject.R;
import com.example.mopsfinalproject.constant.SQLCommand;
import com.example.mopsfinalproject.util.DBOperator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class DBOPS {
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

    public static String DateToday() {
        String formatString = "YYYY-MM-dd";
        DateFormat formatDate = new SimpleDateFormat(formatString);

        Date today = Calendar.getInstance().getTime();
        return formatDate.format(today);
    }

    public static Map <String, String> getSchoolID(Context context) {
        Map<String, String> map = new HashMap<>();

        map.put(context.getString(R.string.schoolWPI), "WPI");
        map.put(context.getString(R.string.schoolWSU), "WSU");
        map.put(context.getString(R.string.schoolClark), "CLR");

        return map;
    }

    public static  String getSchoolName(Context context, String schoolID) {
        Map<String, String> keys = DBOPS.getSchoolID(context);
        Map<String, String> schools = new HashMap<>();

        for (Map.Entry<String, String> entry : keys.entrySet()) {
            schools.put(entry.getValue(), entry.getKey());
        }

        return schools.get(schoolID);
    }

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

    public static String getSchoolID(Context context, String key) {
        Map<String, String> map = DBOPS.getSchoolID(context);

        return map.get(key);
    }

    public static String getSkillID(Context context, String key) {
        Map<String, String> map = DBOPS.getSkillMap(context);

        return map.get(key);
    }

    public static void CommitSkills (String studentID, String[] skills) {

        for (String skill : skills) {
            String[] args = new String[] {studentID, skill};
            DBOperator.getInstance().execSQL(SQLCommand._01_INSERT_STUDENT_SKILLS, args);

        }
    }

    public static void CommitNewStudent (String[] args) {
        DBOperator.getInstance().execSQL(SQLCommand._00_COMMIT_NEW_USER, args);

    }


    public static Map<String, String> StudentToHashMap (String studentID) {
        Map<String, String> map = new HashMap<>();
        String[] attributes = DBOPS.studentAttributes();
        String val;

        for (int i = 0; i < attributes.length; i++) {
            Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand._00_GET_STUDENT_DATA, new String[] {studentID});

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

    public static String GetUserName (String studentID) {
        String name;
        String [] args = new String[] {studentID};

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand._01_GET_FIRST_NAME, args);

        if (cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex("stud_first_name");
            name = cursor.getString(idx);
            cursor.close();
            return name;
        }

        return "";
    }

    public static String GetAttribute(String colLookup, String table, String colPK, String pk) {
        String attr;
        String[] args = new String[] {colLookup, table, colPK, pk};

        String query;

        query = "SELECT " + colLookup + " FROM " + table + " WHERE " + colPK + " = " + pk;

        Cursor cursor = DBOperator.getInstance().execQuery(query, args);

        if (cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex(colLookup);
            attr = cursor.getString(idx);
            cursor.close();
            return attr;
        }

        return "ukn";

    }

    public static String[] getStudentSkills(String studentID) {
        String[] skills = new String[3];


        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand._00_GET_STUDENT_SKILLS, new String[] {studentID});

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

    public static String[] getUsers () {
        String[] users;

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand._00_GET_ALL_USERS);
        int size = cursor.getCount();

        users = new String[size];

        cursor.moveToFirst();
        int i = 0;
        while (cursor.isAfterLast() == false && i < size) {
            int idx = cursor.getColumnIndex("_username");
            users[i] = cursor.getString(idx);
            i++;
            cursor.moveToNext();
        }
        cursor.close();

        return users;
    }




//End of class
}
