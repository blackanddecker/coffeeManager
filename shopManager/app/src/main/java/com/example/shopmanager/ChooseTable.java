package com.example.shopmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChooseTable extends AppCompatActivity {
    String name;
    Integer id;
    Integer shop_id;
    String status;
    String email;
    String table_id ="-1" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);

        Bundle extras = getIntent().getExtras();
        // Ask for the credentials again
        if (extras == null) {
            Intent intent = new Intent(this, SelectOrder.class);
            startActivity(intent);
        } else {
            email = extras.getString("email");
            Log.d("Select Order", email);
            name = extras.getString("name");
            id = extras.getInt("id");
            shop_id = extras.getInt("shop_id");
            status = extras.getString("status");

            // if one of them is null, ask credentials again
            if (email.isEmpty()) Log.d("Show Tables", "No variables");
        }
        showTables();
    }

    public void showTables() {

        JSONObject sendOrderForm = new JSONObject();
        try {
            sendOrderForm.put("shop_id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("SendOrder", String.valueOf(sendOrderForm));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendOrderForm.toString());

        getTables(MainActivity.postUrl+"/show_tables/"+shop_id, body);
    }

    public void chooseTableBtn(View v) {
        Log.d("Choose Table ", "button pressed"+table_id);

        if (table_id == "-1"){
            Toast.makeText(getApplicationContext(), "Check Inputs", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), ChooseTable.class);
            intent.putExtra("email", email);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("status", status);
            intent.putExtra("shop_id", shop_id);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), AddOrder.class);
            intent.putExtra("email", email);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("status", status);
            intent.putExtra("shop_id", shop_id);
            intent.putExtra("table_id", Integer.parseInt(table_id));
            startActivity(intent);

        }

    }
    public void getTables(String postUrl, RequestBody postBody) {
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

                Intent intent = new Intent(getApplicationContext(), ChooseTable.class);
                intent.putExtra("email", email);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("status", status);
                intent.putExtra("shop_id", shop_id);
                startActivity(intent);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String resStr = response.body().string();
                            final JSONArray responseObject = new JSONArray(resStr);

                            ArrayList<String> ordersList = new ArrayList<>();
                            final ListView simpleList;
                            for(int i = 0; responseObject.length() > i; i++) {
                                JSONObject selectProductResponse = responseObject.getJSONObject(i);
                                Log.d("ChooseTable",selectProductResponse.getString("table_id"));

                                ordersList.add(selectProductResponse.getString("table_id"));
                            }
                            simpleList = (ListView)findViewById(R.id.tableListView);
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChooseTable.this, R.layout.activity_list_tables, R.id.tableView, ordersList);
                            simpleList.setAdapter(arrayAdapter);

                            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    table_id =(String)simpleList.getItemAtPosition(position);
                                    Log.d("ChoseTable", String.valueOf(table_id));

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
