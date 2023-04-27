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

public class TeamsActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    ScrollView queryResults;
    Map<String, String> studentData;
    private TextView header;
    String studentID;
    Button btnBack, btnAddTeam;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        Bundle extras = getIntent().getExtras();
        studentID = extras.getString("studentID");
        studentData = DBOPS.StudentToHashMap(studentID);

        btnAddTeam = (Button) findViewById(R.id.btnAddTeam);
        btnAddTeam.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnGoBackTeamView);
        btnBack.setOnClickListener(this);

        header = (TextView) findViewById(R.id.queryHeaderTeams);
        header.setText(studentData.get("stud_first_name") + "\'s Teams");

        queryResults = (ScrollView) findViewById(R.id.queryResultTeams);

        getTeams(studentID);
    }



    public void onClick(View v){
        int id=v.getId();

        if (id == R.id.btnAddTeam) {
            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.btnGoBackTeamView) {
            this.finish();
        }

    }

    public void getTeams(String studentID) {

        listView = (ListView) findViewById(R.id.listTeams);
        queryResults.removeAllViews();

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand._00_GET_TEAMS, new String[] {studentID});


        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getApplicationContext(), R.layout.activity_teams, cursor,
                new String[] { "Name"}, new int[] {
                R.string.teamName},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);

        listView.setAdapter(adapter);

        queryResults.addView(new TableView(this.getBaseContext(), cursor));
        cursor.close();
    }


}
