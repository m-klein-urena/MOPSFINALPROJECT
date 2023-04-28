package com.example.mopsfinalproject;

import com.example.mopsfinalproject.custom.DBOPS;
import com.example.mopsfinalproject.util.DBOperator;
import com.example.mopsfinalproject.custom.Menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends Activity implements OnClickListener{
    Button btnLogin, btnRegister;
    Toolbar toolbar;
    EditText login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

    // End onCreate
    }


    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnLogin){

            login = (EditText) findViewById(R.id.inptLogin);

            if (checkUser(login.getText().toString())) {
                Intent intent = new Intent(this, ProfileHomeActivity.class);
                DBOPS.PackExtras(intent, login.getText().toString(), "ukn");

                this.startActivity(intent);
            }   else {
                String msg = "User ID " + login.getText().toString() + " not found.";
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }

        }else if(id==R.id.btnRegister){
            Intent intent = new Intent(this, RegisterStudentActivity.class);
            this.startActivity(intent);
        }
    // End onClick
    }

    private boolean checkUser (String userID) {

        List listUsers = Arrays.asList(DBOPS.getAllUsers());

        if (listUsers.contains(userID)) {
            return true;
        } else return false;
    }

// End class
}

