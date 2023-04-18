package com.example.mopsfinalproject;

import android.os.Bundle;
import com.example.mopsfinalproject.menu.Menu;
import com.example.mopsfinalproject.menu.SpinnerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

public class RegisterActivity01 extends Activity implements OnClickListener{

    Button btnNext;
    Toolbar toolbar;
    Spinner spinnerPronoun;
    Spinner spinnerState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_01);

        btnNext = (Button) findViewById(R.id.btnRegister01Next);
        btnNext.setOnClickListener(this);

        spinnerPronoun = (Spinner) findViewById(R.id.newUserPronouns);
        spinnerState = (Spinner) findViewById(R.id.newUserState);

        String[] pronouns = getResources().getStringArray(R.array.pronoun_array);
        String[] states = getResources().getStringArray(R.array.states_array);

        SpinnerAdapter arrayAdapterPronoun = new SpinnerAdapter(this, pronouns);
        ArrayAdapter<String> arrayAdapterState = new ArrayAdapter<String>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,states);


        spinnerPronoun.setAdapter(arrayAdapterPronoun);
        spinnerState.setAdapter(arrayAdapterState);

        spinnerState.setSelection(21);

        spinnerPronoun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select Pronouns")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select State")){
                }else {
//                    String item = parent.getItemAtPosition(position).toString();
//                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_home);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_register);
    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnRegister01Next){
            Intent intent = new Intent(this, RegisterActivity02.class);
            this.startActivity(intent);

        }

        // End onClick
        }

// End of class
}

