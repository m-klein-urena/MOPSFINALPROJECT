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
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;

import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.SQLScripts;
import com.example.mopsfinalproject.custom.DBOPS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectsActivity extends Activity implements View.OnClickListener {
    String studentID;
    Map<String, String> studentData;
    private ListView listView;
    private TextView header;

    private List<String> listProjects;
    private String[] arrayProjects, arrayProjectID;
    private String selectedProjectID;
    Button btnAddProject, btnGoBack, btnDetails;
    ArrayAdapter<String> viewAdapter;
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        studentID = data[0];

        studentData = DBOPS.StudentToHashMap(studentID);

        btnAddProject = (Button) findViewById(R.id.btnAddProject);
        btnAddProject.setOnClickListener(this);

        btnGoBack = (Button) findViewById(R.id.btnGoBackProjectView);
        btnGoBack.setOnClickListener(this);

        btnDetails = (Button) findViewById(R.id.btnDetailsProject);
        btnDetails.setOnClickListener(this);
        btnDetails.setEnabled(false);

        header = (TextView) findViewById(R.id.queryHeaderProjects);
        header.setText(studentData.get("stud_first_name") + "\'s Projects");

        //        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_main, studentID);

        initListView(studentID);

    }


    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnAddProject) {
            Intent intent = new Intent(this, RegisterProjectActivity.class);
            DBOPS.PackExtras(intent, studentID, "ukn");
            viewAdapter.clear();

            this.startActivity(intent);
        }

        if (id == R.id.btnDetailsProject) {
            if (btnDetails.isEnabled()) {
                Intent intent = new Intent(this, DataProjectActivity.class);
                DBOPS.PackExtras(intent, studentID, selectedProjectID);
                viewAdapter.clear();
                this.startActivity(intent);
            } else {
                Toast.makeText(this, "Select a project from the list to see details", Toast.LENGTH_LONG).show();
            }
        }

        if (id == R.id.btnGoBackProjectView) {
            viewAdapter.clear();
            this.finish();
        }

    }

    public void initListView(String studentID) {
        listView = (ListView) findViewById(R.id.listProjects);
        listProjects = new ArrayList<String>();


        arrayProjects = DBOPS.getAttributeCol(SQLScripts._08_GET_PROJECTS, "Title", new String[] {studentID});
        arrayProjectID = DBOPS.getAttributeCol(SQLScripts._08_GET_PROJECTS, "_id", new String[] {studentID});

        for (String prj : arrayProjects)
            listProjects.add(prj);

        viewAdapter = new ArrayAdapter<String>(this, R.layout.layout_listview_item, listProjects);

        listView.setAdapter(viewAdapter);
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


//    End of class
}
