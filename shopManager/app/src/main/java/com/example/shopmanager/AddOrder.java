package com.example.shopmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AddOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        Bundle extras = getIntent().getExtras();
        // Ask for the credentials again
        if (extras == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            String email = extras.getString("email");
            Log.d("Add Order", email);
            String name = extras.getString("name");
            Integer id = extras.getInt("id");
            Integer shop_id = extras.getInt("shop_id");
            String status = extras.getString("status");

            // if one of them is null, ask credentials again
            if (email.isEmpty()) {
                Log.d("Add Order", "No variables");
            }
        }
    }
    public void addorder(View v) {
        Log.d("Add order", "Successful Here");

    }
}
