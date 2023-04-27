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

import com.example.mopsfinalproject.constant.SQLCommand;
import com.example.mopsfinalproject.custom.DBOPS;
import com.example.mopsfinalproject.util.DBOperator;
import com.example.mopsfinalproject.view.TableView;

import java.util.Map;

public class MatchActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    ScrollView queryResults;
    Map<String, String> studentData;
    private TextView header;
    String studentID;
    Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        Bundle extras = getIntent().getExtras();
        studentID = extras.getString("studentID");
        studentData = DBOPS.StudentToHashMap(studentID);

        btnBack = (Button) findViewById(R.id.btnGoBackMatchView);
        btnBack.setOnClickListener(this);

        header = (TextView) findViewById(R.id.queryHeaderMatches);
        header.setText(studentData.get("stud_first_name") + "\'s Matches");

        queryResults = (ScrollView) findViewById(R.id.queryResultMatches);

        getMatches(studentID);
    }

    public void onClick(View v){
        int id=v.getId();

        if (id == R.id.btnGoBackMatchView) {
            this.finish();
        }

    }

    public void getMatches(String studentID) {
        listView = (ListView) findViewById(R.id.listMatches);
        queryResults.removeAllViews();

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand._00_GET_MATCHES,  new String[] {studentID});

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getApplicationContext(), R.layout.activity_matches, cursor,
                new String[] {"Name", "Team", "Contact"}, new int[] {
                R.string.matchesName, R.string.matchesTeam, R.string.matchesContact},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);

        listView.setAdapter(adapter);

        queryResults.addView(new TableView(this.getBaseContext(), cursor));
        cursor.close();
    }



}
