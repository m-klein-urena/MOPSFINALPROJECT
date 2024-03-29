package com.example.mopsfinalproject;
import com.example.mopsfinalproject.custom.DBOPS;
import com.example.mopsfinalproject.custom.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Map;

public class UserDataActivity extends Activity implements View.OnClickListener{
    Map<String, String> studentData;
    String userID;

    TextView txtID;
    TextView txtCreate;
    TextView txtFirst;
    TextView txtLast;
    TextView txtPreferred;
    TextView txtPronoun;
    TextView txtAddress1;
    TextView txtAddress2;
    TextView txtCity;
    TextView txtState;
    TextView txtZIP;
    TextView txtEmail;
    TextView txtPhone;
    TextView txtSchool;
    TextView txtSkill1;
    TextView txtSkill2;
    TextView txtSkill3;
    Button btnBack;
    TextView header;
    Toolbar toolbar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        userID = data[0];

        studentData = DBOPS.StudentToHashMap(userID);

        header = (TextView) findViewById(R.id.headerUserProfile);
        header.setText(studentData.get("stud_first_name") + " " + studentData.get("stud_last_name"));

        PopulateData();

        btnBack = (Button) findViewById(R.id.btnBackData);
        btnBack.setOnClickListener(this);

        //        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_main, userID);

    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnBackData){
            this.finish();
        }

        // End onClick
    }

    public void PopulateData() {
        String[] attributes = DBOPS.studentAttributes();

        txtID = (TextView) findViewById(R.id.dataStudentID);
        txtCreate = (TextView) findViewById(R.id.dataAcctCreate);
        txtFirst = (TextView) findViewById(R.id.dataFirst);
        txtLast = (TextView) findViewById(R.id.dataLast);
        txtPreferred = (TextView) findViewById(R.id.dataPreferred);
        txtPronoun = (TextView) findViewById(R.id.dataPronouns);
        txtAddress1 = (TextView) findViewById(R.id.dataAddress1);
        txtAddress2 = (TextView) findViewById(R.id.dataAddress2);
        txtCity = (TextView) findViewById(R.id.dataCity);
        txtState = (TextView) findViewById(R.id.dataState);
        txtZIP = (TextView) findViewById(R.id.dataZIP);
        txtEmail = (TextView) findViewById(R.id.dataEmail);
        txtPhone = (TextView) findViewById(R.id.dataPhone);
        txtSchool = (TextView) findViewById(R.id.dataSchool);
        txtSkill1 = (TextView) findViewById(R.id.dataSkill1);
        txtSkill2 = (TextView) findViewById(R.id.dataSkill2);
        txtSkill3 = (TextView) findViewById(R.id.dataSkill3);

        txtID.setText(studentData.get(attributes[0]));
        txtFirst.setText(studentData.get(attributes[1]));
        txtLast.setText(studentData.get(attributes[2]));
        txtPreferred.setText(studentData.get(attributes[3]));
        txtPronoun.setText(studentData.get(attributes[4]));
        txtAddress1.setText(studentData.get(attributes[5]));
        txtAddress2.setText(studentData.get(attributes[6]));
        txtCity.setText(studentData.get(attributes[7]));
        txtState.setText(studentData.get(attributes[8]));
        txtZIP.setText(studentData.get(attributes[9]));
        txtEmail.setText(studentData.get(attributes[10]));
        txtPhone.setText(studentData.get(attributes[11]));
        txtCreate.setText(studentData.get(attributes[12]));

        String schoolName = DBOPS.getSchoolName(this, studentData.get(attributes[13]));
        txtSchool.setText(schoolName);

        String[] skills = DBOPS.getStudentSkills(userID);

        txtSkill1.setText(skills[0]);
        txtSkill2.setText(skills[1]);
        txtSkill3.setText(skills[2]);

    }

}
