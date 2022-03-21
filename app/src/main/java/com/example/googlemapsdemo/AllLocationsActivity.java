package com.example.googlemapsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllLocationsActivity extends AppCompatActivity {
    private RecyclerView locationsRecView;
    private LocationRecViewAdapter adapter;

    private RequestQueue requestQueue;

    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_locations);

        adapter = new LocationRecViewAdapter(this);
        locationsRecView = findViewById(R.id.locationRecView);

        locationsRecView.setAdapter(adapter);
        locationsRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setLocations(Utils.getLocations());
    }
}