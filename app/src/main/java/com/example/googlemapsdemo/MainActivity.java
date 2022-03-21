package com.example.googlemapsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.VoiceInteractor;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button btnCreateLot, btnFindalot, btnLeaveLot;
    FusedLocationProviderClient fusedLocationProviderClient; //this is a trial comment

    private RequestQueue requestQueue;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateLot = findViewById(R.id.btnCreateLot);
        btnFindalot = findViewById(R.id.btnFindalot);
        btnLeaveLot = findViewById(R.id.btnLeaveLot);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        requestQueue = Volley.newRequestQueue(MainActivity.this);


        btnFindalot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.findalotapi.somee.com/api/Locations";


                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                Utils.get_instance().clearLocations();

                                Log.d("response", response.toString());

                                for (int i = 0; i < response.length(); i++){
                                    try {
                                        JSONObject j = response.getJSONObject(i);

                                        Log.d("responseItem", j.getString("addressline"));
                                        int userId = 0;
                                        try {
                                            userId = j.getInt("userId");
                                        }catch (JSONException e){
                                            Log.d("JsonException", e.toString());
                                            userId = 0;
                                        }

                                        com.example.googlemapsdemo.Location location = new com.example.googlemapsdemo.Location(j.getInt("locationId"),
                                                userId, j.getDouble("longitude"),
                                                j.getDouble("latitude"), j.getString("addressline"), j.getString("countryName"),
                                                j.getString("adminArea"), j.getString("subAdminArea"));

                                        Log.d("responseItemObject", location.getAddressLine());

                                        Utils.get_instance().addToLocations(location);

                                        //Location location = new Location();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                Log.d("locationsSize", String.valueOf(Utils.getLocations().size()));
                                if (Utils.getLocations().size() > 0){
                                    Intent intent = new Intent(MainActivity.this, AllLocationsActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "There is no Location ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response Error: ", error.toString());
                    }
                });

                requestQueue.add(request);
            }
        });

        btnCreateLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check permissions
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //when permissions granted
                    getLocation();
                }
                else {
                    //when permissions are not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    private void setLocation(int userId, double longitude, double latitude, String addressLine, String countryName, String adminArea, String subAdminArea){
        String url = "http://www.findalotapi.somee.com/api/Locations";

        JSONObject location = new JSONObject();
        try {
            location.put("userId", userId);
            location.put("longitude", longitude);
            location.put("latitude", latitude);
            location.put("addressLine", addressLine);
            location.put("adminArea", adminArea);
            location.put("countryName", countryName);
            location.put("subAdminArea", subAdminArea);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, location,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        Toast.makeText(MainActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "It Seems You Have Already Added a Location For Yourself!", Toast.LENGTH_SHORT).show();
                Log.d("error", error.toString());
            }
        });

        requestQueue.add(request);
    }

    @SuppressWarnings("MissingPermission")
    private void getLocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null){
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        Address address = addresses.get(0);

                        String addressLine = address.getAddressLine(0);

                        Log.d("userId", String.valueOf(Utils.get_instance().getUser().getId()));

                        setLocation(Utils.get_instance().getUser().getId(), address.getLongitude(), address.getLatitude(),
                                addressLine, address.getCountryName(), address.getAdminArea(), address.getSubAdminArea());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}