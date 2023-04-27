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

    DBOperator db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        Menu.createMenu(getBaseContext(), toolbar, R.menu.menu_home);

    // End onCreate
    }


    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnLogin){

            login = (EditText) findViewById(R.id.inptLogin);

            if (checkUser(login.getText().toString())) {
                Intent intent = new Intent(this, ProfileHomeActivity.class);
                intent.putExtra("studentID", login.getText().toString());

                this.startActivity(intent);
            }   else {
                String msg = "User ID " + login.getText().toString() + " not found.";
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }

        }else if(id==R.id.btnRegister){
            Intent intent = new Intent(this, RegisterActivity.class);
            this.startActivity(intent);
        }
    // End onClick
    }

    private boolean checkUser (String userID) {

        List listUsers = Arrays.asList(DBOPS.getUsers());

        if (listUsers.contains(userID)) {
            return true;
        } else return false;
    }

// End class
}

