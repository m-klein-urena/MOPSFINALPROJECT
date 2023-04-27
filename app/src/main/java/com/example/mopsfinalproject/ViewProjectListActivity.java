package com.example.mopsfinalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mopsfinalproject.constant.SQLCommand;
import com.example.mopsfinalproject.util.DBOperator;

public class ViewProjectListActivity extends Activity {
    private ListView listView;

    private String studentID;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectview);

        listView = (ListView) this.findViewById(R.id.listProjects);

        Intent intent = this.getIntent();
        String message = intent.getStringExtra("data");

        String[] parced = message.split("$");
        studentID = parced[0];
        String query = parced[1];

        Cursor cursor = DBOperator.getInstance().execQuery(query, new String[]{studentID});


    }
}
