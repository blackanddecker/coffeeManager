package com.example.shopmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class AddOrder extends AppCompatActivity {
    String name;
    Integer id;
    Integer shop_id;
    String status;
    String email;
    Integer table_id;
    JSONArray orderHistory = new JSONArray();
    JSONArray responseObject = new JSONArray();
    String clickedItem;
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
            table_id = extras.getInt("table_id");

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
    public void sendOrder(View v) {
        if (orderHistory.length() > 0 ) {
            JSONObject sendOrderForm = new JSONObject();
            try {
                sendOrderForm.put("table_id", table_id);
                sendOrderForm.put("orderHistory", orderHistory);
                sendOrderForm.put("order_id", -1);
                sendOrderForm.put("user_id", id);
                sendOrderForm.put("shop_id", shop_id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("SendOrder", String.valueOf(sendOrderForm));

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendOrderForm.toString());

            postRequestOrder(MainActivity.postUrl + "/add_order", body);
        }
        else{
            Toast.makeText(getApplicationContext(), "Empty order", Toast.LENGTH_LONG).show();

        }
    }

    public void addProductonClick(View v) {
        try {

            JSONObject orderObject = new JSONObject();

            for (int i = 0; i < responseObject.length(); i++) {
                JSONObject selectProductResponse = null;
                try {
                    selectProductResponse = responseObject.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Log.d("selectedlickedItem1", clickedItem);
                    Log.d("selectedlickedItem2", selectProductResponse.getString("name"));

                    if (clickedItem == selectProductResponse.getString("name")) {
                        try {
                            EditText details = findViewById(R.id.detailsInput);
                            String productDetails = details.getText().toString().trim();

                            Log.d("Details", productDetails);
                            orderObject.put("product_id", selectProductResponse.getInt("id"));
                            orderObject.put("details", productDetails);
                            Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Check Inputs", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Check Inputs", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            }
            Log.d("selectedlickedItem1", "Here");
            orderHistory.put(orderObject);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postRequestOrder(String postUrl, RequestBody postBody) {
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
                Toast.makeText(getApplicationContext(), "Check Inputs", Toast.LENGTH_LONG).show();

                // Cancel the post on failure.
                call.cancel();
                Log.d("FAIL", e.getMessage());
                Intent intent = new Intent(getApplicationContext(), AddOrder.class);
                intent.putExtra("email", email);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("status", status);
                intent.putExtra("shop_id",shop_id);
                startActivity(intent);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseTextLogin = findViewById(R.id.responseTextLogin);
                        try {
                            Log.d("Add order", "Succeful Add");
                            Toast.makeText(getApplicationContext(), "Add Order", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                            intent.putExtra("email", email);
                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            intent.putExtra("status", status);
                            intent.putExtra("shop_id",shop_id);
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

                Intent intent = new Intent(getApplicationContext(), AddOrder.class);
                intent.putExtra("email", email);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("status", status);
                intent.putExtra("shop_id",shop_id);
                startActivity(intent);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final TextView responseTextLogin = findViewById(R.id.responseTextLogin);

                        try {
                            Log.d("selectOrder", "Response from the server : " );
                            final String resStr = response.body().string();
                            responseObject = new JSONArray(resStr);

                            ArrayList<String> productsList = new ArrayList<>();
                            final ListView simpleList;
                            for(int i=0; i<responseObject.length(); i++) {
                                JSONObject selectProductResponse = responseObject.getJSONObject(i);
                                productsList.add(selectProductResponse.getString("name"));
                                Log.d("selectOrder", selectProductResponse.getString("name") );
                            }
                            simpleList = (ListView)findViewById(R.id.simpleListView);
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddOrder.this, R.layout.activity_list_products, R.id.textView, productsList);
                            simpleList.setAdapter(arrayAdapter);

                            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    clickedItem=(String) simpleList.getItemAtPosition(position);
                                    Log.d("selectedlickedItem",clickedItem );

                                }
                            });

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
