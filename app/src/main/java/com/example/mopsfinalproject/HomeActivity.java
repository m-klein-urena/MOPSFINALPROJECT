package com.example.mopsfinalproject;

import com.example.mopsfinalproject.util.DBOperator;
import com.example.mopsfinalproject.menu.Menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends Activity implements OnClickListener{
    Button btnLogin, btnRegister;
    Toolbar toolbar;

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

        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
    // End onCreate
    }


    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnLogin){
            Toast.makeText(getBaseContext(), "Feature coming soon...", Toast.LENGTH_SHORT).show();

        }else if(id==R.id.btnRegister){
            Intent intent = new Intent(this, RegisterActivity01.class);
            this.startActivity(intent);
        }
    // End onClick
    }

// End class
}

