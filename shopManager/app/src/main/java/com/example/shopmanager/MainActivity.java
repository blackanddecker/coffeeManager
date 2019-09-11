package com.example.shopmanager;

import android.support.v7.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

    }


    public void select_product(View V){
        String url = "http://127.0.0.1:5000/select_product";
        AndroidNetworking.get("http://10.0.2.2:8080/select_product")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                    }
                    @Override
                    public void onError(ANError error) {
                        System.out.println(error);
                    }
                });

    }

}
