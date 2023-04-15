package com.example.mopsfinalproject;

import com.example.mopsfinalproject.util.DBOperator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener{
    Button btnCheckout, btnQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheckout = (Button) findViewById(R.id.btnCheckoutPage);
        btnCheckout.setOnClickListener(this);

        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(this);

        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
    // End onCreate
    }

    public void onClick(View v){
        int id = v.getId();

        if(id==R.id.btnCheckoutPage){
            Intent intent = new Intent(this, CheckoutActivity.class);
            this.startActivity(intent);

        }else if(id==R.id.btnQuery){
            Intent intent = new Intent(this, QueryActivity.class);
            this.startActivity(intent);
        }
    // End onClick
    }

// End class
}

