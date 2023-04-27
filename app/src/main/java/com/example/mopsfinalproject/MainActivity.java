package com.example.mopsfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mopsfinalproject.util.DBOperator;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}