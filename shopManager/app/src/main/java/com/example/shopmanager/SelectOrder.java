package com.example.shopmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SelectOrder extends AppCompatActivity {
    RequestBody body;
    String postUrl;
    String name;
    Integer id;
    Integer shop_id;
    String status;
    String email;
    JSONArray orderHistory = new JSONArray();
    String clickedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order);

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
            if (email.isEmpty()) Log.d("Add Order", "No variables");
        }


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        JSONObject sendOrderForm = new JSONObject();
        List<String> listOr = new ArrayList<String>();

        try {
            sendOrderForm.put("end_date", "2021-10-28");
            sendOrderForm.put("start_date", "2009-10-28");
            sendOrderForm.put("shop_id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("SendOrder", String.valueOf(sendOrderForm));

        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendOrderForm.toString());


        PostSelectRequestOrder selectOrd = new PostSelectRequestOrder();
        postUrl = MainActivity.postUrl+"/select_order";
        selectOrd.execute(postUrl);

    }
    private class PostSelectRequestOrder extends AsyncTask<String, Integer,String> {
        @Override
        protected String doInBackground(String... username) {
//            public void postSelectRequestOrder (String postUrl, RequestBody postBody){
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(postUrl)
                    .post(body)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Cancel the post on failure.
                    Toast.makeText(getApplicationContext(), "Server Error.", Toast.LENGTH_LONG).show();

                    call.cancel();
                    Log.d("FAIL", e.getMessage());

                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    intent.putExtra("email", email);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("status", status);
                    intent.putExtra("shop_id", shop_id);
                    startActivity(intent);
                }

                @Override
                public void onResponse(Call call, final Response response) {
                    // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final String resStr = response.body().string();
                                Log.d("selectOrder", "Response from the server : "+resStr);

                                final JSONArray responseObject = new JSONArray(resStr);

                                ArrayList<String> ordersList = new ArrayList<>();
                                final ListView simpleList;
                                for (int i = 0; responseObject.length() > i; i++) {
                                    JSONObject selectProductResponse = responseObject.getJSONObject(i);
                                    if (selectProductResponse.getString("status").equals("paid") == false){
                                        ordersList.add(selectProductResponse.getString("id") + "-" + selectProductResponse.getString("table_id") + "-" + selectProductResponse.getString("status"));
                                    }
                                }
                                simpleList = findViewById(R.id.orderListView);
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SelectOrder.this, R.layout.activity_list_orders, R.id.textView, ordersList);
                                simpleList.setAdapter(arrayAdapter);

                                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String[] arrOfStr = ((String) simpleList.getItemAtPosition(position)).split("-", 0);
                                        Log.d("selectedlickedItem0", String.valueOf(arrOfStr));
                                        Log.d("selectedlickedItem1", String.valueOf(arrOfStr[0]));

                                        clickedItem = arrOfStr[0];
                                        Log.d("selectedlickedItem2", clickedItem);
                                    }
                                });

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Check Inputs", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });
            return("");
        }

    }



    public void updatePaidorder(View v) {

        JSONObject sendOrderForm = new JSONObject();
        try {
            sendOrderForm.put("order_id", Integer.valueOf(clickedItem));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("SendOrder", String.valueOf(sendOrderForm));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendOrderForm.toString());

        postUpdateRequestOrder(MainActivity.postUrl+"/update_paid_order", body);
    }


    public void postUpdateRequestOrder(String postUrl2, RequestBody postBody) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl2)
                .post(postBody)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Check Inputs.", Toast.LENGTH_LONG).show();

                // Cancel the post on failure.
                call.cancel();
                Log.d("FAIL", e.getMessage());

                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                intent.putExtra("email", email);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("status", status);
                intent.putExtra("shop_id", shop_id);
                startActivity(intent);
            }

            @Override
            public void onResponse(Call call, final Response response) {

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String resStr = response.body().string();

                            Log.d("Paid", "Successful paid");

                            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                            intent.putExtra("email", email);
                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            intent.putExtra("status", status);
                            intent.putExtra("shop_id", shop_id);
                            startActivity(intent);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Check Inputs", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                    }

                });
            }
        });
    }

}
