package com.example.mopsfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mopsfinalproject.constant.SQLCommand;
import com.example.mopsfinalproject.custom.DBOPS;
import com.example.mopsfinalproject.util.DBOperator;

public class ProfileHomeActivity extends Activity implements View.OnClickListener{
    TextView btnProfile;
    TextView btnProjects;
    TextView btnTeams;
    TextView btnMatches;
    String userID;
    String name;
    TextView header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("studentID");
        name = DBOPS.GetUserName(userID);

        btnProjects = (TextView) findViewById(R.id.btnProject);
        btnProjects.setOnClickListener(this);

        btnProfile = (TextView) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(this);

        btnTeams = (TextView) findViewById(R.id.btnTeam);
        btnTeams.setOnClickListener(this);

        btnMatches = (TextView) findViewById(R.id.btnMatches);
        btnMatches.setOnClickListener(this);

        header = (TextView) findViewById(R.id.headerProfileHome);
        header.setText("Welcome, " + name);

    }


    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnProfile){
            Intent intent = new Intent(this, DataActivity.class);
            intent.putExtra("studentID", userID);

            this.startActivity(intent);
//            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();
        }

        if(id==R.id.btnProject){
            Intent intent = new Intent(this, ProjectsActivity.class);
            intent.putExtra("studentID", userID);

            this.startActivity(intent);
//            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();
        }

        if(id==R.id.btnTeam){
            Intent intent = new Intent(this, TeamsActivity.class);
            intent.putExtra("studentID", userID);

            this.startActivity(intent);
//            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();
        }

        if(id==R.id.btnMatches){
            Intent intent = new Intent(this, MatchActivity.class);
            intent.putExtra("studentID", userID);

            this.startActivity(intent);
//            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();
        }


        // End onClick
    }


//    End class
}
