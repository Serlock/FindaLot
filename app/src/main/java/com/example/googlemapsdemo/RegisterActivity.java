package com.example.googlemapsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtTxtNameRegister, edtTxtSurnameRegister, edtTxtEmailRegister, edtTxtPasswordRegister, edtTxtCountryRegister, edtTxtCityRegister,
            edtTxtDistrictRegister;

    private Button buttonRegister;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtTxtNameRegister = findViewById(R.id.edtTxtNameRegister);
        edtTxtSurnameRegister = findViewById(R.id.edtTxtSurnameRegister);
        edtTxtEmailRegister = findViewById(R.id.edtTxtEmailRegister);
        edtTxtPasswordRegister = findViewById(R.id.edtTxtPasswordRegister);
        edtTxtCountryRegister = findViewById(R.id.edtTxtCountryRegister);
        edtTxtCityRegister = findViewById(R.id.edtTxtCityRegister);
        edtTxtDistrictRegister = findViewById(R.id.edtTxtDistrictRegister);
        buttonRegister = findViewById(R.id.buttonRegister);

        requestQueue = Volley.newRequestQueue(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTxtNameRegister.getText().toString().matches("") || edtTxtSurnameRegister.getText().toString().matches("") ||
                        edtTxtEmailRegister.getText().toString().matches("") ||
                        edtTxtPasswordRegister.getText().toString().matches("") ||
                        edtTxtCountryRegister.getText().toString().matches("") ||
                        edtTxtCityRegister.getText().toString().matches("") ||
                        edtTxtDistrictRegister.getText().toString().matches(""))
                {
                    Toast.makeText(RegisterActivity.this, "Please Fill All the Form", Toast.LENGTH_LONG).show();
                }
                else {
                    String url = "http://www.findalotapi.somee.com/api/Users";

                    JSONObject user = new JSONObject();
                    try {
                        user.put("name", edtTxtNameRegister.getText().toString());
                        user.put("surname", edtTxtSurnameRegister.getText().toString());
                        user.put("email", edtTxtEmailRegister.getText().toString());
                        user.put("password", edtTxtPasswordRegister.getText().toString());
                        user.put("country", edtTxtCountryRegister.getText().toString());
                        user.put("city", edtTxtCityRegister.getText().toString());
                        user.put("district", edtTxtDistrictRegister.getText().toString());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("response", response.toString());
                                    Toast.makeText(RegisterActivity.this, "You Have Been Successfully Registered", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "You Couldn't Registered", Toast.LENGTH_SHORT).show();
                            Log.d("response", error.toString());
                        }
                    });

                    requestQueue.add(request);
                }
            }
        });
    }
}