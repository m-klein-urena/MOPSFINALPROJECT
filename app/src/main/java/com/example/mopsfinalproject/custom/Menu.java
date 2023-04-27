package com.example.mopsfinalproject.custom;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.mopsfinalproject.HomeActivity;
import com.example.mopsfinalproject.R;

public class Menu {

    public static void createMenu(Context context, Toolbar toolbar, int menu) {

        toolbar.inflateMenu(menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Menu.menuActions(context, item);
                return false;
            }
        });

    }

    private static void menuActions(Context context, MenuItem item) {
        if (item.getItemId()== R.id.actionAbout) {
            Toast.makeText(context, "You clicked About Us", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.actionLogout) {
            Toast.makeText(context, "You clicked logout", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.actionHome) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }
    }

}
