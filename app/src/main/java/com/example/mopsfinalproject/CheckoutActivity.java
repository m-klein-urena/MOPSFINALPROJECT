package com.example.mopsfinalproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.mopsfinalproject.constant.SQLCommand;
import com.example.mopsfinalproject.util.DBOperator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

public class CheckoutActivity extends Activity implements OnClickListener{

    EditText editStudentID, editBookID;
    DatePicker editDatePicker;
    Button btnCheckout, btnReturn, btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(this);

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        editStudentID = (EditText) findViewById(R.id.editStudentID);
        editBookID = (EditText) findViewById(R.id.editBookID);
        editDatePicker = (DatePicker) findViewById(R.id.editDatePicker);
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnCheckout) {
            DBOperator.getInstance().execSQL(SQLCommand.CHECK_BOOK, this.getArgs(true));
            Toast.makeText(getBaseContext(), "Book checked out successfully", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.btnReturn) {
            DBOperator.getInstance().execSQL(SQLCommand.RETURN_BOOK, this.getArgs(false));
            Toast.makeText(getBaseContext(), "Book returned successfully", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.btnBack) {
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
        }
//  End onClick
    }

    private String[] getArgs(boolean isCheckout) {
        int i;
        String checkout;

        if (isCheckout) {
            i = 4;
            checkout = "Y";
        } else {
            i = 3;
            checkout = "N";
        }

        String[] args = new String[i];
        args[0] = checkout;

        args[1] = editStudentID.getText().toString();
        args[2] = editBookID.getText().toString();

        if (isCheckout) {
            int year = editDatePicker.getYear();
            int month = editDatePicker.getMonth();
            int day = editDatePicker.getDayOfMonth();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            args[3] = dateFormat.format(calendar.getTime());
        }

        return args;
//  end getArgs
    }

// End class
}
