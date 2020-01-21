package com.example.shopmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.lang.String;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void submit(View v) {
        EditText usernameView = findViewById(R.id.loginUsername);
        EditText passwordView = findViewById(R.id.loginPassword);

        String username = usernameView.getText().toString().trim();
        String password = passwordView.getText().toString().trim();

        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Something is wrong. Please check your inputs.", Toast.LENGTH_LONG).show();
            return;
        }

        JSONObject loginForm = new JSONObject();
        try {
            loginForm.put("subject", "login");
            loginForm.put("email", username);
            loginForm.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), loginForm.toString());

        postRequest(MainActivity.postUrl+"/login_mobile", body);
    }

    public void postRequest(String postUrl, RequestBody postBody) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
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
                            Log.d("LOGIN", "Response from the server : " );
                            String resStr = response.body().string();
                            JSONArray responseObject = new JSONArray(resStr);
                            JSONObject loginResponse = responseObject.getJSONObject(0);
                            Log.d("LOGIN", "Response from the server! Done!" );
                            Log.d("LOGIN", "Response from the server : " + loginResponse.getString("email"));

                            if (loginResponse.length() != 0) {
                                Log.d("LOGIN", "Successful Login");

                                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                intent.putExtra("email", loginResponse.getString("email"));
                                intent.putExtra("id", loginResponse.getInt("id"));
                                intent.putExtra("name", loginResponse.getString("name"));
                                intent.putExtra("status", loginResponse.getString("status"));
                                intent.putExtra("shop_id", loginResponse.getInt("shop_id"));
                                startActivity(intent);

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