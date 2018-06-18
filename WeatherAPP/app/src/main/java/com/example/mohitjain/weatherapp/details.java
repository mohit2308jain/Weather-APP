package com.example.mohitjain.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class details extends AppCompatActivity {

    EditText city;
    TextView descrip11;
    TextView temp11;
    TextView pressure11;
    TextView humid11;
    TextView lati11;
    TextView long11;
    String temperature11 = "";
    String state = "";
    String pressure22 = "";
    String humid22 = "";
    String latitude22 = "";
    String longitude22 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        city = (EditText) findViewById(R.id.city);
        descrip11 = (TextView) findViewById(R.id.descrip);
        temp11 = (TextView) findViewById(R.id.temp1);
        pressure11 = (TextView) findViewById(R.id.pressure1);
        humid11 = (TextView) findViewById(R.id.humidity1);
        lati11 = (TextView) findViewById(R.id.lati1);
        long11 = (TextView) findViewById(R.id.longi1);

        Intent i = getIntent();

        if((i.getStringExtra("111")).matches("")){
            state = "DESCRIPTION";
        }
        else {
            state = i.getStringExtra("111");
        }
        descrip11.setText(state);



        if((i.getStringExtra("222")).matches("")){
            temperature11 = "TEMPERATURE";
        }
        else {
            temperature11 = i.getStringExtra("222");
        }
        temp11.setText(temperature11);



        if((i.getStringExtra("333")).matches("")){
            pressure22 = "PRESSURE";
        }
        else {
            pressure22 = i.getStringExtra("333") + " hpa";
        }
        pressure11.setText(pressure22);



        if((i.getStringExtra("444")).matches("")){
            humid22 = "HUMIDITY";
        }
        else {
            humid22 = "HUMIDITY: " + i.getStringExtra("444") + " %";
        }
        humid11.setText(humid22);



        if((i.getStringExtra("555")).matches("")){
            latitude22 = "LATITUDE";
        }
        else {
            latitude22 = "LATITUDE: " + i.getStringExtra("555");
        }
        lati11.setText(latitude22);



        if((i.getStringExtra("666")).matches("")){
            longitude22 = "LONGITUDE";
        }
        else {
            longitude22 = "LONGITUDE: " + i.getStringExtra("666");
        }
        long11.setText(longitude22);



    }
}




