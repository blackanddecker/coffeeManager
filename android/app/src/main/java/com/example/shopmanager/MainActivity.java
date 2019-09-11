package com.example.shopmanager;

import android.support.v7.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import android.os.Bundle;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        select_product();
    }
    

    private void select_product(){
        String url = "http://127.0.0.1:5000/select_product";
        AndroidNetworking.get("http://127.0.0.1:5000/select_product")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        res = response.toString()
                        System.out.println(res)
                    }
                    @Override
                    public void onError(ANError error) {
                        System.out.println(error)

                    }
                });

    }

}
