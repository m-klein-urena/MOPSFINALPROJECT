package com.example.mopsfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.SQLCommand;
import com.example.mopsfinalproject.custom.DBOPS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchStudentActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    Map<String, String> projectData;
    private TextView header;
    private String selectedStudentID;
    String projectID, studentID;
    Button btnBack, btnDetails;
    TextView viewSkills, labelSkills;
    String[] arrayStudentsFirst, arrayStudentsLast;
    String[] arrayStudentID;
    List<String> listMatches;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        studentID = data[0];
        projectID = data[1];


        projectData = DBOPS.AttributesToHashMap(DBOPS.projectAttributes(), SQLCommand._10_GET_PROJECT_DATA, new String[] {projectID});

        btnBack = (Button) findViewById(R.id.btnGoBackMatchView);
        btnBack.setOnClickListener(this);

        labelSkills = (TextView) findViewById(R.id.matchesSkillsLabel);
        labelSkills.setText("Skills needed:");

        btnDetails = (Button) findViewById(R.id.btnDetailsMatches);
        btnDetails.setOnClickListener(this);
        btnDetails.setEnabled(false);

        header = (TextView) findViewById(R.id.queryHeaderMatches);
        header.setText("Matches for " + projectData.get("proj_title"));

        //        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_main, studentID);


        init();
    }

    public void onClick(View v){
        int id=v.getId();

        if (id == R.id.btnGoBackMatchView) {
            this.finish();
        }

        if (id == R.id.btnDetailsMatches) {
            if (btnDetails.isEnabled()) {
                Intent intent = new Intent(this, DataProfileActivity.class);
                DBOPS.PackExtras(intent, selectedStudentID, "ukn");
                this.startActivity(intent);
            }
        }
    }

    public void getMatches(String studentID) {
        listView = (ListView) findViewById(R.id.listMatches);
        listMatches = new ArrayList<String>();
        String[] args = new String[] {projectID};

        arrayStudentsFirst = DBOPS.getAttributeCol(SQLCommand._18_GET_STUDENT_MATCHES, "_first", args);
        arrayStudentsLast = DBOPS.getAttributeCol(SQLCommand._18_GET_STUDENT_MATCHES, "_last", args);
        arrayStudentID = DBOPS.getAttributeCol(SQLCommand._18_GET_STUDENT_MATCHES, "_id", args);

        for (int i = 0; i < arrayStudentsFirst.length; i++) {
            listMatches.add(arrayStudentsFirst[i] + " " + arrayStudentsLast[i]);
        }

        ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(this, R.layout.layout_listview_item, listMatches);

        listView.setAdapter(projectAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(android.R.color.darker_gray);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btnDetails.setEnabled(true);
                selectedStudentID = arrayStudentID[i];
            }
        });
    }

    private void init() {
        String skills = DBOPS.ArrayToString(DBOPS.getAttributeCol(SQLCommand._14_GET_PROJECT_SKILLS, "_skillname", new String[] {projectID}));
        System.out.println("SKILLS: " + skills);


        viewSkills = (TextView) findViewById(R.id.displaySkills);
        viewSkills.setText(skills);

        getMatches(projectID);
    }






}
