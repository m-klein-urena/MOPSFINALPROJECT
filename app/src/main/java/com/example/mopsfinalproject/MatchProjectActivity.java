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
import com.example.mopsfinalproject.custom.SQLScripts;
import com.example.mopsfinalproject.custom.DBOPS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchProjectActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    Map<String, String> studentData;
    private TextView header;
    private String selectedProjectID;
    String studentID;
    Button btnBack, btnDetails;
    TextView viewSkills;
    String[] arrayProjects;
    String[] arrayProjectID;
    List<String> listMatches;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_matches);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        studentID = data[0];
        studentData = DBOPS.StudentToHashMap(studentID);

        btnBack = (Button) findViewById(R.id.btnGoBackMatchView);
        btnBack.setOnClickListener(this);

        btnDetails = (Button) findViewById(R.id.btnDetailsMatches);
        btnDetails.setOnClickListener(this);
        btnDetails.setEnabled(false);

        header = (TextView) findViewById(R.id.queryHeaderMatches);
        header.setText(studentData.get("stud_first_name") + "\'s Matches");

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
                Intent intent = new Intent(this, DataMatchesProjectsActivity.class);
                DBOPS.PackExtras(intent, "ukn", selectedProjectID);

                this.startActivity(intent);
            }
        }
    }

    public void getMatches(String studentID) {
        listView = (ListView) findViewById(R.id.listMatches);
        listMatches = new ArrayList<String>();

        arrayProjects = DBOPS.getAttributeCol(SQLScripts._18_GET_PROJECT_MATCHES, "Name", new String[] {studentID});
        arrayProjectID = DBOPS.getAttributeCol(SQLScripts._18_GET_PROJECT_MATCHES, "_id", new String[] {studentID});

        for (String prj : arrayProjects)
            listMatches.add(prj);

        ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(this, R.layout.layout_listview_item, listMatches);

        listView.setAdapter(projectAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(android.R.color.darker_gray);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btnDetails.setEnabled(true);
                selectedProjectID = arrayProjectID[i];
            }
        });
    }

    private void init() {
        String skills = DBOPS.ArrayToString(DBOPS.getStudentSkills(studentID));

        viewSkills = (TextView) findViewById(R.id.displaySkills);
        viewSkills.setText(skills);

        getMatches(studentID);
    }



}
