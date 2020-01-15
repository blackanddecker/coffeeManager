package com.example.shopmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddOrder extends AppCompatActivity {
    String name;
    Integer id;
    Integer shop_id;
    String status;
    String email;

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
            email = extras.getString("email");
            Log.d("Add Order", email);
            name = extras.getString("name");
            id = extras.getInt("id");
            shop_id = extras.getInt("shop_id");
            status = extras.getString("status");

            // if one of them is null, ask credentials again
            if (email.isEmpty()) {
                Log.d("Add Order", "No variables");
            }
        }
        addorder();
    }
    public void addorder() {
        Log.d("Add order", "Successful Here");

        JSONObject selectProduct = new JSONObject();
        try {
            selectProduct.put("subject", "selectProduct");
            selectProduct.put("shop_id", shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), selectProduct.toString());
        getRequest(MainActivity.postUrl+"/select_product/"+shop_id, body);
    }

    public void getRequest(String postUrl, RequestBody postBody) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();
                Log.d("FAIL", e.getMessage());

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseTextLogin = findViewById(R.id.responseTextLogin);
                        responseTextLogin.setText("Failed to Connect to Server. Please Try Again.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseTextLogin = findViewById(R.id.responseTextLogin);
                        try {
                            Log.d("selectOrder", "Response from the server : " );
                            String resStr = response.body().string();
                            JSONArray responseObject = new JSONArray(resStr);
                            JSONObject loginResponse = responseObject.getJSONObject(0);
                            Log.d("selectOrder", "Response from the server! Done!" );

                            if (loginResponse.length() != 0) {
                                Log.d("selectOrder", "Successful Login");

//                                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
//                                intent.putExtra("email", loginResponse.getString("email"));
//                                intent.putExtra("id", loginResponse.getInt("id"));
//                                intent.putExtra("name", loginResponse.getString("name"));
//                                intent.putExtra("status", loginResponse.getString("status"));
//                                intent.putExtra("shop_id", loginResponse.getInt("shop_id"));
//                                startActivity(intent);

//                                finish();//finishing activity and return to the calling activity.
                            } else
                                responseTextLogin.setText("Login Failed. Invalid username or password.");
                        } catch (Exception e) {
                            e.printStackTrace();
                            responseTextLogin.setText("Something went wrong. Please try again ");
                        }
                    }
                });
            }
        });
    }
}
