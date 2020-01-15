package com.example.shopmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.content.Intent;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Bundle extras = getIntent().getExtras();
        // Ask for the credentials again
        if (extras == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else{
            String email = extras.getString("email");
            Log.d("MainMenu", email);
            String name = extras.getString("name");
            Integer id = extras.getInt("id");
            Integer shop_id = extras.getInt("shop_id");
            String status = extras.getString("status");

            // if one of them is null, ask credentials again
            if (email.isEmpty()) {
                Log.d("MainMenu", "No variables" );
            }

        }
    }
    public void addOrder(View v) {
        Intent intent = new Intent(this, AddOrder.class);
        startActivityForResult(intent, 2);
    }

    public void selectOrder(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
