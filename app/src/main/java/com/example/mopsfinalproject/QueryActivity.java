package com.example.mopsfinalproject;

import android.view.View;
import com.example.mopsfinalproject.constant.SQLCommand;
import com.example.mopsfinalproject.util.DBOperator;
import com.example.mopsfinalproject.view.TableView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class QueryActivity extends Activity implements View.OnClickListener {
    Button btnBack, btnResult;
    Spinner spinnerQuery;
    ScrollView scrollView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query);
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnResult = (Button) findViewById(R.id.btnResult);
        btnResult.setOnClickListener(this);
        spinnerQuery = (Spinner) findViewById(R.id.spinnerQuery);

        scrollView= (ScrollView) findViewById(R.id.scrollView);
    // End onCreate
    }

    public void onClick(View v) {
        String sql="";
        int id=v.getId();

        if (id==R.id.btnResult){
            // Show query result
            int pos= spinnerQuery.getSelectedItemPosition();
            if (pos==Spinner.INVALID_POSITION) {
                // User doesn't choose query, show warning
                Toast.makeText(this.getBaseContext(), "Please choose a query!", Toast.LENGTH_SHORT).show();
                return;
            }

            scrollView.removeAllViews();

            switch(pos){
                case 0:
                    sql = SQLCommand.QUERY_01;
                    break;
                case 1:
                    sql = SQLCommand.QUERY_02;
                    break;
                case 2:
                    sql = SQLCommand.QUERY_03;
                    break;
                case 3:
                    sql = SQLCommand.QUERY_04;
                    break;
                case 4:
                    sql = SQLCommand.QUERY_05;
                    break;
                case 5:
                    sql = SQLCommand.QUERY_06;
                    break;
                case 6:
                    sql = SQLCommand.QUERY_07;
                    break;
            }

            Cursor cursor = DBOperator.getInstance().execQuery(sql);
            scrollView.addView(new TableView(this.getBaseContext(), cursor));
        //End if
        }else if (id==R.id.btnBack){
            // Go back to main screen
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
        }
        //end OnClick
    }
// End class
}