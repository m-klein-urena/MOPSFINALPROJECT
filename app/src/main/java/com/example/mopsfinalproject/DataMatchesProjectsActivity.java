package com.example.mopsfinalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.SQLCommand;
import com.example.mopsfinalproject.custom.DBOPS;

import java.util.Map;

public class DataMatchesProjectsActivity extends Activity implements View.OnClickListener{
    Map<String, String> projectData;
    String projectID;
    String studentID;

    TextView txtID, txtDesc, txtTeam, txtMembers, txtSkills, txtCreated, txtStart, txtEnd, txtAdvisor, txtHeader;

    Button btnBack, btnJoin;
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_project);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        studentID = data[0];
        projectID = data[1];

        projectData = DBOPS.AttributesToHashMap(DBOPS.projectAttributes(), SQLCommand._10_GET_PROJECT_DATA, new String[] {projectID});

        PopulateData();

        txtHeader = (TextView) findViewById(R.id.headerDataProject);
        txtHeader.setText(projectData.get("proj_title"));

        btnBack = (Button) findViewById(R.id.btnBackData);
        btnBack.setOnClickListener(this);

        btnJoin = (Button) findViewById(R.id.btnJoinProject);
        btnJoin.setOnClickListener(this);

        //        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_main, studentID);

    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnBackData){
            this.finish();
        }

        if (id == R.id.btnJoinProject) {
            if (!DBOPS.isInProject(studentID, projectID)) {
                DBOPS.JoinProject(studentID, projectID);

                String msg = "Successfully joined Project: " + projectData.get("proj_title") +
                        " (Team: " + txtTeam.getText().toString() + ")";

                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();

                this.finish();

            } else Toast.makeText(getBaseContext(), "You're already on this project.", Toast.LENGTH_SHORT).show();
        }
        // End onClick
    }

    public void PopulateData() {
        String[] args = new String[] {projectID};
        String[] attributes = DBOPS.projectAttributes();

        txtID = (TextView) findViewById(R.id.dataProjectID);
        txtDesc = (TextView) findViewById(R.id.dataProjectDesc);
        txtCreated = (TextView) findViewById(R.id.dataProjectCreated);
        txtStart = (TextView) findViewById(R.id.dataProjectStart);
        txtEnd = (TextView) findViewById(R.id.dataProjectEnd);
        txtAdvisor = (TextView) findViewById(R.id.dataProjectAdvisor);

        txtTeam = (TextView) findViewById(R.id.dataProjectTeam);
        txtMembers = (TextView) findViewById(R.id.dataProjectMembers);
        txtSkills = (TextView) findViewById(R.id.dataProjectSkills);

        txtID.setText(projectData.get(attributes[0]));
        txtDesc.setText(projectData.get(attributes[2]));
        txtCreated.setText(projectData.get(attributes[3]));
        txtStart.setText(projectData.get(attributes[4]));
        txtEnd.setText(projectData.get(attributes[5]));
        txtAdvisor.setText(projectData.get(attributes[6]));

        String team = DBOPS.getAttributeCol(SQLCommand._11_GET_TEAM, "_teamname", args)[0];
        txtTeam.setText(team);

        String[] skillsarray = DBOPS.getAttributeCol(SQLCommand._13_GET_TEAM_SKILLS_NEEDED, "_skillname", args);
        String skills = DBOPS.ArrayToString(skillsarray);

        txtSkills.setText(skills);

        String[] members_first = DBOPS.getAttributeCol(SQLCommand._12_GET_TEAM_MEMBERS, "_first", args);
        String[] members_last = DBOPS.getAttributeCol(SQLCommand._12_GET_TEAM_MEMBERS, "_last", args);

        StringBuilder members = new StringBuilder();
        for (int i = 0; i < members_first.length; i++) {
            members.append(members_first[i]);
            members.append(" ");
            members.append(members_last[i]);

            if (i < members_first.length - 1) members.append("\n");
        }

        txtMembers.setText(members);

    }

}
