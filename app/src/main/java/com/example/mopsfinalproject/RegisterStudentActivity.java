package com.example.mopsfinalproject;

import android.os.Bundle;
import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.SpinnerAdapter;
import com.example.mopsfinalproject.custom.DBOPS;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

public class RegisterStudentActivity extends Activity implements OnClickListener{

    Button btnFinish;
    Toolbar toolbar;
    Spinner spinnerPronoun;
    Spinner spinnerState;
    Spinner spinnerSchool;
    Spinner spinnerSkill1;
    Spinner spinnerSkill2;
    Spinner spinnerSkill3;
    String studentID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(this);

        spinnerPronoun = (Spinner) findViewById(R.id.newUserPronouns);
        spinnerState = (Spinner) findViewById(R.id.newUserState);
        spinnerSchool = (Spinner) findViewById(R.id.newUserSchool);
        spinnerSkill1 = (Spinner) findViewById(R.id.newUserSkill1);
        spinnerSkill2 = (Spinner) findViewById(R.id.newUserSkill2);
        spinnerSkill3 = (Spinner) findViewById(R.id.newUserSkill3);

        String[] pronouns = getResources().getStringArray(R.array.pronoun_array);
        String[] states = getResources().getStringArray(R.array.states_array);
        String[] schools = getResources().getStringArray(R.array.school_array);
        String[] skills = getResources().getStringArray(R.array.skill_array);

        SpinnerAdapter arrayAdapterPronoun = new SpinnerAdapter(this, pronouns);
        ArrayAdapter<String> arrayAdapterState = new ArrayAdapter<String>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,states);
        SpinnerAdapter arrayAdapterSchool = new SpinnerAdapter(this, schools);
        SpinnerAdapter arrayAdapterSkill1 = new SpinnerAdapter(this, skills);
        SpinnerAdapter arrayAdapterSkill2 = new SpinnerAdapter(this, skills);
        SpinnerAdapter arrayAdapterSkill3 = new SpinnerAdapter(this, skills);

        spinnerPronoun.setAdapter(arrayAdapterPronoun);
        spinnerState.setAdapter(arrayAdapterState);
        spinnerSchool.setAdapter(arrayAdapterSchool);
        spinnerSkill1.setAdapter(arrayAdapterSkill1);
        spinnerSkill2.setAdapter(arrayAdapterSkill2);
        spinnerSkill3.setAdapter(arrayAdapterSkill3);

        spinnerState.setSelection(21);

//        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_register, "ukn");
    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnFinish){

            if (DBOPS.checkSkillSpinners(this, spinnerSkill1, spinnerSkill2, spinnerSkill3)) {
                studentID = CommitToDatabase();

                Intent intent = new Intent(this, ProfileHomeActivity.class);
                DBOPS.PackExtras(intent, studentID, "ukn");

                this.startActivity(intent);
            }
        }

    // End onClick
    }

    private String CommitToDatabase() {
        EditText fieldFirst = (EditText) findViewById(R.id.newUserFirst);
        EditText fieldLast = (EditText) findViewById(R.id.newUserLast);
        EditText fieldPreferred = (EditText) findViewById(R.id.newUserPreferred);
        EditText fieldAddress1 = (EditText) findViewById(R.id.newUserAddress1);
        EditText fieldAddress2 = (EditText) findViewById(R.id.newUserAddress2);
        EditText fieldCity = (EditText) findViewById(R.id.newUserCity);
        EditText fieldZIP = (EditText) findViewById(R.id.newUserZIP);
        EditText fieldPhone = (EditText) findViewById(R.id.newUserPhone);
        EditText fieldEmail = (EditText) findViewById(R.id.newUserEmail);

        String commitFirst = fieldFirst.getText().toString();
        String commitLast = fieldLast.getText().toString();
        String commitPreferred = fieldPreferred.getText().toString();
        String commitPronoun = spinnerPronoun.getSelectedItem().toString();
        String commitAddress1 = fieldAddress1.getText().toString();
        String commitAddress2 = fieldAddress2.getText().toString();
        String commitCity = fieldCity.getText().toString();
        String commitState = spinnerState.getSelectedItem().toString();
        String commitZIP = fieldZIP.getText().toString();
        String commitPhone = fieldPhone.getText().toString();
        String commitEmail = fieldEmail.getText().toString();

        String studentID = DBOPS.GenerateID(6);
        String commitDate = DBOPS.DateToday();

        String commitSchool = DBOPS.getSchoolID(this, spinnerSchool.getSelectedItem().toString());

        String commitSkill1 = DBOPS.getSkillID(this, spinnerSkill1.getSelectedItem().toString());
        String commitSkill2 = DBOPS.getSkillID(this, spinnerSkill2.getSelectedItem().toString());
        String commitSkill3 = DBOPS.getSkillID(this, spinnerSkill3.getSelectedItem().toString());

        final String[] argsNewStudent = new String[] {
                studentID,
                commitFirst,
                commitLast,
                commitPreferred,
                commitPronoun,
                commitAddress1,
                commitAddress2,
                commitCity,
                commitState,
                commitZIP,
                commitEmail,
                commitPhone,
                commitDate,
                commitSchool
        };

        final String[] argsSkills = new String[] {
                commitSkill1,
                commitSkill2,
                commitSkill3
        };

        DBOPS.CommitNewStudent(argsNewStudent);
        DBOPS.CommitStudentSkills(studentID, argsSkills);

        return studentID;

//        End of CommitToDatabase()
    }


// End of class
}

