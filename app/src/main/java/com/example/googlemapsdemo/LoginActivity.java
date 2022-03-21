package com.example.googlemapsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText edtTxtEmail, edtTxtPassword;
    private TextView txtEmailErrorMessage, txtPasswordErrorMessage;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtTxtEmail = findViewById(R.id.edtTxtRegName);
        edtTxtPassword = findViewById(R.id.edtTxtRegSurname);
        txtEmailErrorMessage = findViewById(R.id.txtNameRegErrorMessage);
        txtPasswordErrorMessage = findViewById(R.id.txtPasswordRegErrorMessage);

        requestQueue = Volley.newRequestQueue(this);

        Utils.get_instance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTxtEmail.getText().toString().matches("") || edtTxtPassword.getText().toString().matches("")){
                    Toast.makeText(LoginActivity.this, "Please Fill All the Form!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String url = "http://www.findalotapi.somee.com/api/Users";

                    JSONObject user = new JSONObject();
                    try {
                        user.put("email", edtTxtEmail.getText().toString());
                        user.put("password", edtTxtPassword.getText().toString());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("response", response.toString());
                                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    try {
                                        Log.d("userId", response.get("userId").toString());
                                        intent.putExtra("userId", response.get("userId").toString());
                                        Utils.get_instance().setUser(response.getInt("userId"), response.getString("name"),
                                                response.getString("surname"), response.getString("email"), response.getString("password"),
                                                response.getString("country"), response.getString("city"), response.getString("district"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            Log.d("response", error.toString());
                        }
                    });

                    requestQueue.add(request);
                }
            }
        });
    }
}