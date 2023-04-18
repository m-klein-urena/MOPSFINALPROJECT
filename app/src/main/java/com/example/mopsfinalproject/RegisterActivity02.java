package com.example.mopsfinalproject;

import com.example.mopsfinalproject.menu.SpinnerAdapter;
import com.example.mopsfinalproject.menu.Menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity02 extends Activity implements View.OnClickListener {

    Spinner spinnerDegree;
    Spinner spinnerSchool;
    Spinner spinnerSkill1;
    Spinner spinnerSkill2;
    Spinner spinnerSkill3;
    Button btnNext;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_02);

//        Create toolbar and load menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_home);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_register);

//        Set button
        btnNext = (Button) findViewById(R.id.btnRegister02Finish);
        btnNext.setOnClickListener(this);

//        Retrieve dropdown items
        String[] degrees = getResources().getStringArray(R.array.degree_array);
        String[] schools = getResources().getStringArray(R.array.school_array);
        String[] skills = getResources().getStringArray(R.array.skill_array);

//        Populate permanent spinners
        spinnerSchool = BuildSpinner(R.id.newUserSchool, schools);
        spinnerDegree = BuildSpinner(R.id.newUserDegree, degrees);
        spinnerSkill1 = BuildSpinner(R.id.newUserSkill1, skills);
        spinnerSkill2 = BuildSpinner(R.id.newUserSkill2, skills);
        spinnerSkill3 = BuildSpinner(R.id.newUserSkill3, skills);

//        Create array of skill buttons
        Map<Integer, Spinner> arraySkills = new HashMap<Integer, Spinner>();

        arraySkills.put(new Integer(R.id.newUserSkill1), spinnerSkill1);
        arraySkills.put(new Integer(R.id.newUserSkill2), spinnerSkill3);
        arraySkills.put(new Integer(R.id.newUserSkill3), spinnerSkill2);

    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnRegister02Finish){
//            Intent intent = new Intent(this, RegisterActivity02.class);
//            this.startActivity(intent);
            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();

        }

        // End onClick
    }

    private Spinner BuildSpinner(int spinnerView, String[] array) {
        Spinner spinner = (Spinner) findViewById(spinnerView);
        SpinnerAdapter arrayAdapter = new SpinnerAdapter(this, array);
        spinner.setAdapter(arrayAdapter);

        return spinner;

    }


}
