package com.example.mopsfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.SQLScripts;
import com.example.mopsfinalproject.custom.DBOPS;

import java.util.Map;

public class DataProjectActivity extends Activity implements View.OnClickListener{
    Map<String, String> projectData;
    String projectID, studentID;

    TextView txtID, txtDesc, txtTeam, txtMembers, txtSkills, txtCreated, txtStart, txtEnd, txtAdvisor, txtHeader;

    Button btnBack, btnMatches;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_project);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        studentID = data[0];
        projectID = data[1];

        projectData = DBOPS.AttributesToHashMap(DBOPS.projectAttributes(), SQLScripts._10_GET_PROJECT_DATA, new String[] {projectID});

        PopulateData();

        txtHeader = (TextView) findViewById(R.id.headerDataProject);
        txtHeader.setText(projectData.get("proj_title"));

        btnBack = (Button) findViewById(R.id.btnBackData);
        btnBack.setOnClickListener(this);

        btnMatches = (Button) findViewById(R.id.btnJoinProject);
        btnMatches.setOnClickListener(this);
        btnMatches.setText("Find Matches");

        //        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_main, studentID);

    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnBackData){
            Intent intent = new Intent(this, UserHomeActivity.class);
            DBOPS.PackExtras(intent, studentID, "ukn");
            this.startActivity(intent);
        }

        if (id == R.id.btnJoinProject) {
            Intent intent = new Intent(this, MatchStudentActivity.class);
            DBOPS.PackExtras(intent, studentID, projectID);

            this.startActivity(intent);

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

        String team = DBOPS.getAttributeCol(SQLScripts._11_GET_TEAM, "_teamname", args)[0];
        txtTeam.setText(team);

        String[] skillsarray = DBOPS.getAttributeCol(SQLScripts._13_GET_TEAM_SKILLS_NEEDED, "_skillname", args);
        String skills = DBOPS.ArrayToString(skillsarray);

        System.out.println(skillsarray.length);

        txtSkills.setText(skills);

        String[] members_first = DBOPS.getAttributeCol(SQLScripts._12_GET_TEAM_MEMBERS, "_first", args);
        String[] members_last = DBOPS.getAttributeCol(SQLScripts._12_GET_TEAM_MEMBERS, "_last", args);

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
