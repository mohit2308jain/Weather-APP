package com.example.mohitjain.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;
    ImageView image;
    TextView des;
    TextView temp1;
    String cityname = "";
    String state = "";
    String description = "";
    String temperature = "";

    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String Api = "&appid=e49d9f88297ef678ede51cbe15bed56a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        city = (EditText)findViewById(R.id.city);

        result = (TextView)findViewById(R.id.result);

        image = (ImageView)findViewById(R.id.imageView);

        temp1 = (TextView)findViewById(R.id.temp);

        des = (TextView)findViewById(R.id.des);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cityname = city.getText().toString();
                cityname = cityname.replaceAll("\\s","%20");

                //if city name is not null
                if(cityname.isEmpty() == false) {
                    String myURL = baseURL + cityname + Api;
                    //Log.i("URL", "URl " + myURL);

                    //Json object request initialized
                    final JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(Request.Method.GET, myURL, null,
                            new Response.Listener<JSONObject>() {

                                //if we get response from website
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        String info = response.getString("weather");
                                        //Log.i("Info","INFOOO" + info);

                                        //Jsonarray for getting description info and weather info
                                        JSONArray ar = new JSONArray(info);

                                        for(int i = 0;i<ar.length();i++){
                                            JSONObject parObj = ar.getJSONObject(i);

                                            state = parObj.getString("main");
                                            description = parObj.getString("description");

                                        }
                                        state = state.toUpperCase();
                                        description = description.toUpperCase();

                                        //Json object fr getting temperature from website
                                        JSONObject co = new JSONObject(response.getString("main"));
                                        double keltemp = co.getDouble("temp");
                                        double celtemp = keltemp - 273.15;

                                        temperature = String.format("%.2f Celsius",celtemp).toString();

                                        //switch statement to change the image according to weather condition
                                        switch (state){
                                            case "DRIZZLE":
                                                image.setImageResource(R.drawable.drizzle);
                                                break;

                                            case "RAIN":
                                                image.setImageResource(R.drawable.rain);
                                                break;

                                            case "CLEAR":
                                                image.setImageResource(R.drawable.clear);
                                                break;

                                            case "CLOUDS":
                                                image.setImageResource(R.drawable.clouds);
                                                break;

                                            case "MIST":
                                                image.setImageResource(R.drawable.fog);
                                                break;

                                            case "FOG":
                                                image.setImageResource(R.drawable.fog);
                                                break;

                                            case "HAZE":
                                                image.setImageResource(R.drawable.fog);
                                                break;

                                            case "THUNDERSTORM":
                                                image.setImageResource(R.drawable.tstorn);
                                                break;
                                        }

                                        //finally to display information on screen
                                        result.setText(state);
                                        temp1.setText(temperature);
                                        des.setText(description);

                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {

                                //if we don't get response from website
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("ERROR", "Something went wrong" + error);
                                }
                            }
                    );
                    MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
                }

                //if city name is not entered
                else if(cityname.isEmpty()||cityname.matches("Enter your City...")){
                    Toast.makeText(MainActivity.this, "Please enter city...", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
