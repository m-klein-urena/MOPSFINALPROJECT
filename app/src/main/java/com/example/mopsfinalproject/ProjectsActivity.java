package com.example.mopsfinalproject;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mopsfinalproject.constant.SQLCommand;
import com.example.mopsfinalproject.custom.DBOPS;
import com.example.mopsfinalproject.util.DBOperator;
import com.example.mopsfinalproject.view.TableView;

import java.util.Map;

public class ProjectsActivity extends Activity implements View.OnClickListener {
    ScrollView queryResults;
    String studentID;
    Map<String, String> studentData;
    private ListView listView;
    private TextView header;

    Button btnAddProject, btnGoBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        Bundle extras = getIntent().getExtras();
        studentID = extras.getString("studentID");
        studentData = DBOPS.StudentToHashMap(studentID);

        btnAddProject = (Button) findViewById(R.id.btnAddProject);
        btnAddProject.setOnClickListener(this);

        btnGoBack = (Button) findViewById(R.id.btnGoBackProjectView);
        btnGoBack.setOnClickListener(this);

        header = (TextView) findViewById(R.id.queryHeaderProjects);
        header.setText(studentData.get("stud_first_name") + "\'s Projects");


        queryResults = (ScrollView) findViewById(R.id.queryResultProjects);
        getProjects(studentID);

    }


    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnAddProject) {
            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.btnGoBackProjectView) {
            this.finish();
        }

    }

    public void getProjects(String studentID) {

        listView = (ListView) findViewById(R.id.listProjects);
        queryResults.removeAllViews();

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand._00_GET_PROJECTS, new String[] {studentID});


        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getApplicationContext(), R.layout.activity_projects, cursor,
                new String[] { "Title", "Description", "Team" }, new int[] {
                R.string.prjName, R.string.prjDesc, R.string.prjTeam},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);

        listView.setAdapter(adapter);

        queryResults.addView(new TableView(this.getBaseContext(), cursor));
        cursor.close();
    }


//    End of class
}
