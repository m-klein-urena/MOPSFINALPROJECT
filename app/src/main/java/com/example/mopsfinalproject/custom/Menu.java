package com.example.mopsfinalproject.custom;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.mopsfinalproject.HomeActivity;
import com.example.mopsfinalproject.UserHomeActivity;
import com.example.mopsfinalproject.R;

public class Menu {


    public static void createMenu(Context context, Toolbar toolbar, int menu, String studentID) {

        toolbar.inflateMenu(menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Menu.menuActions(context, item, studentID);
                return false;
            }
        });

    }

    private static void menuActions(Context context, MenuItem item, String studentID) {
        if (item.getItemId()== R.id.actionProfileHome) {
            Intent intent = new Intent(context, UserHomeActivity.class);
            DBOPS.PackExtras(intent, studentID, "ukn");
            context.startActivity(intent);

        } else if (item.getItemId()==R.id.actionLogout) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);

        } else if (item.getItemId()==R.id.actionCancelRegister) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }
    }

}
