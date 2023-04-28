package com.example.mopsfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import com.example.mopsfinalproject.custom.DBOPS;
import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.SQLScripts;
import com.example.mopsfinalproject.custom.SpinnerAdapter;
import com.example.mopsfinalproject.util.DBOperator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterProjectActivity extends Activity implements OnClickListener{

    Button btnFinish;
    Toolbar toolbar;
    String projectID, teamID, studentID;
    DatePicker pickStart, pickEnd;
    EditText txtTeam, txtName, txtDesc, txtAdvisor;
    Spinner spinnerSkill1, spinnerSkill2, spinnerSkill3;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        studentID = data[0];

        txtTeam = (EditText) findViewById(R.id.newProjectTeam);
        txtName = (EditText) findViewById(R.id.newProjectName);
        txtDesc = (EditText) findViewById(R.id.newProjectDesc);
        txtAdvisor = (EditText) findViewById(R.id.newProjectAdvisor);

        btnFinish = (Button) findViewById(R.id.btnFinishNewProject);
        btnFinish.setOnClickListener(this);

        pickStart = (DatePicker) findViewById(R.id.newProjectStart);
        pickEnd = (DatePicker) findViewById(R.id.newProjectEnd);

        spinnerSkill1 = (Spinner) findViewById(R.id.newProjectSkill1);
        spinnerSkill2 = (Spinner) findViewById(R.id.newProjectSkill2);
        spinnerSkill3 = (Spinner) findViewById(R.id.newProjectSkill3);

        String[] skills = getResources().getStringArray(R.array.skill_array);
        SpinnerAdapter arrayAdapterSkills = new SpinnerAdapter(this, skills);

        spinnerSkill1.setAdapter(arrayAdapterSkills);
        spinnerSkill2.setAdapter(arrayAdapterSkills);
        spinnerSkill3.setAdapter(arrayAdapterSkills);

//        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_main, studentID);


    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnFinishNewProject){
            if (DBOPS.checkSkillSpinners(this, spinnerSkill1, spinnerSkill2, spinnerSkill3)
                    && DBOPS.isValidDate(this, pickStart, pickEnd)) {
                CommitToDatabase();

                Intent intent = new Intent(this, DataProjectActivity.class);
                DBOPS.PackExtras(intent, studentID, projectID);

                this.startActivity(intent);
            }
        }


    // End onClick
    }

    private void CommitToDatabase() {

        teamID = DBOPS.GenerateID(6);
        projectID = DBOPS.GenerateID(6);

        final String commitTeamName = txtTeam.getText().toString();
        final String commitProjectName = txtName.getText().toString();
        final String commitDesc = txtDesc.getText().toString();
        final String commitAdvisor = txtAdvisor.getText().toString();
        final String commitCreate = DBOPS.DateToday();
        final String commitStart = getDate(pickStart);
        final String commitEnd = getDate(pickEnd);

        final String[] skills = new String[] {
                DBOPS.getSkillID(this, spinnerSkill1.getSelectedItem().toString()),
                DBOPS.getSkillID(this, spinnerSkill2.getSelectedItem().toString()),
                DBOPS.getSkillID(this, spinnerSkill3.getSelectedItem().toString())
        };

        final String[] argsNewTeam = new String[] {
                teamID,
                commitTeamName,
                "Y",
                "1",
                "10",
                "5"
        };

        final String[] argsNewProject = new String[] {
                projectID,
                commitProjectName,
                commitDesc,
                commitCreate,
                commitEnd,
                commitStart,
                commitAdvisor
        };

        final String[] argsNewTeamMember = new String[] {teamID, studentID};
        final String[] argsNewProjectTeam = new String[] {projectID, teamID};

        DBOperator.getInstance().execSQL(SQLScripts._03_INSERT_NEW_TEAM, argsNewTeam);
        DBOperator.getInstance().execSQL(SQLScripts._04_INSERT_NEW_PROJECT, argsNewProject);
        DBOperator.getInstance().execSQL(SQLScripts._15_JOIN_TEAM, argsNewTeamMember);
        DBOperator.getInstance().execSQL(SQLScripts._16_JOIN_PROJECT, argsNewProjectTeam);

        DBOPS.CommitProjectSkills(teamID, skills);


//        End of CommitToDatabase()
    }

    private String getDate(DatePicker datePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        return dateFormat.format(calendar.getTime());
    }


// End of class
}

