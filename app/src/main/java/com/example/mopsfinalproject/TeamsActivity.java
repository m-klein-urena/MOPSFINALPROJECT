package com.example.mopsfinalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.SQLScripts;
import com.example.mopsfinalproject.custom.DBOPS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamsActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    Map<String, String> studentData;
    private TextView header;
    String studentID;
    Button btnBack, btnDetails;
    NestedScrollView scrollView;
    List<String> listTeams;

    String[] arrayTeams, arrayTeamID;
    String selectedTeamID;
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_matches);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getString("student_prjID").split("\\$");

        studentID = data[0];

        studentData = DBOPS.StudentToHashMap(studentID);

        btnDetails = (Button) findViewById(R.id.btnDetailsMatches);
        btnDetails.setVisibility(View.INVISIBLE);

        scrollView = (NestedScrollView) findViewById(R.id.scrollviewSkills);
        scrollView.setVisibility(View.INVISIBLE);

        btnBack = (Button) findViewById(R.id.btnGoBackMatchView);
        btnBack.setOnClickListener(this);

        header = (TextView) findViewById(R.id.queryHeaderMatches);
        header.setText(studentData.get("stud_first_name") + "\'s Teams");

        //        Call up toolbar and menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_main, studentID);

        initListView(studentID);
    }



    public void onClick(View v){
        int id=v.getId();

        if (id == R.id.btnGoBackMatchView) {
            this.finish();
        }

    }

    public void initListView(String studentID) {
        listView = (ListView) findViewById(R.id.listMatches);
        listTeams = new ArrayList<String>();


        arrayTeams = DBOPS.getAttributeCol(SQLScripts._09_GET_TEAMS, "_name", new String[] {studentID});
        arrayTeamID = DBOPS.getAttributeCol(SQLScripts._09_GET_TEAMS, "_id", new String[] {studentID});

        for (String team : arrayTeams)
            listTeams.add(team);

        ArrayAdapter<String> viewAdapter = new ArrayAdapter<String>(this, R.layout.layout_listview_item, listTeams);

        listView.setAdapter(viewAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(android.R.color.darker_gray);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTeamID = arrayTeamID[i];
            }
        });


    }


}
