package com.example.mopsfinalproject;

import com.example.mopsfinalproject.custom.SpinnerAdapter;
import com.example.mopsfinalproject.custom.Menu;
import com.example.mopsfinalproject.custom.Person;
import com.example.mopsfinalproject.custom.Person.Attribute;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import java.util.EnumMap;
import java.util.Map;

public class RegisterActivity02 extends Activity implements View.OnClickListener {

    Spinner spinnerDegree;
    Spinner spinnerSchool;
    Spinner spinnerSkill1;
    Spinner spinnerSkill2;
    Spinner spinnerSkill3;
    Button btnFinish;
    Toolbar toolbar;
    private Person user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_02);

//        Load and instantiate user data
        user = new Person();

//        Create toolbar and load menu
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_home);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_register);

//        Set button
        btnFinish = (Button) findViewById(R.id.btnRegister02Finish);
        btnFinish.setOnClickListener(this);

//        Retrieve dropdown items
        String[] pronouns = getResources().getStringArray(R.array.pronoun_array);
        String[] states = getResources().getStringArray(R.array.states_array);
        String[] schools = getResources().getStringArray(R.array.school_array);
        String[] skills = getResources().getStringArray(R.array.skill_array);

//        Populate permanent spinners
        spinnerSchool = BuildSpinner(R.id.newUserSchool, schools);
//        spinnerDegree = BuildSpinner(R.id.newUserDegree, degrees);
        spinnerSkill1 = BuildSpinner(R.id.newUserSkill1, skills);
        spinnerSkill2 = BuildSpinner(R.id.newUserSkill2, skills);
        spinnerSkill3 = BuildSpinner(R.id.newUserSkill3, skills);

//    End onCreate
    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnRegister02Finish){
            WriteAttributes(user);

            Intent intent = new Intent(this, ProfileHomeActivity.class);
            user.sequenceGenes(intent);

            this.startActivity(intent);
//            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();

        }

        // End onClick
    }

    private Spinner BuildSpinner(int spinnerView, String[] array) {
        Spinner spinner = (Spinner) findViewById(spinnerView);
        SpinnerAdapter arrayAdapter = new SpinnerAdapter(this, array);
        spinner.setAdapter(arrayAdapter);

        return spinner;

    }

    private void WriteAttributes(Person person) {
        Map<Person.Attribute, String> attr = new EnumMap<Person.Attribute, String>(Person.Attribute.class);

        attr.put(Attribute.school, findViewById(R.id.newUserSchool).toString());
        attr.put(Attribute.skill1, findViewById(R.id.newUserSkill1).toString());
        attr.put(Attribute.skill2, findViewById(R.id.newUserSkill2).toString());
        attr.put(Attribute.skill3, findViewById(R.id.newUserSkill3).toString());

        person.setAttributes(attr);
    }


}
