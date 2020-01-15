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
    String name;
    Integer id;
    Integer shop_id;
    String status;
    String email;

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
            email = extras.getString("email");
            Log.d("MainMenu", email);
            name = extras.getString("name");
            id = extras.getInt("id");
            shop_id = extras.getInt("shop_id");
            status = extras.getString("status");

            // if one of them is null, ask credentials again
            if (email.isEmpty()) {
                Log.d("MainMenu", "No variables" );
            }

        }
    }
    public void addOrder(View v) {
        Intent intent = new Intent(this, AddOrder.class);

        intent.putExtra("email", email);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("status", status);
        intent.putExtra("shop_id", shop_id);

        startActivityForResult(intent, 2);
    }

    public void selectOrder(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
