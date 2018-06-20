package mohitjain.getweather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mohitjain.getweather.R;
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
    Button des;
    TextView temp1;
    String cityname = "";
    String state = "";
    String description = "";
    String temperature = "";
    String pressure22 = "";
    String humid22 = "";
    String latitude22 = "";
    String longitude22 = "";

    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String Api = "&appid=e49d9f88297ef678ede51cbe15bed56a";

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        isOnline();
        //if (isOnline()==false) {
          //  MainActivity.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        //}


        button = (Button) findViewById(R.id.button);

        city = (EditText)findViewById(R.id.city);

        result = (TextView)findViewById(R.id.result);

        image = (ImageView)findViewById(R.id.imageView);

        temp1 = (TextView)findViewById(R.id.temp);

        des = (Button)findViewById(R.id.des);

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

                                        //Jsonarray for getting description info and weather info
                                        JSONArray ar = new JSONArray(info);

                                        for(int i = 0;i<ar.length();i++){
                                            JSONObject parObj = ar.getJSONObject(i);

                                            state = parObj.getString("main");
                                            description = parObj.getString("description");
                                        }
                                        state = state.toUpperCase();
                                        description = description.toUpperCase();


                                        //Json object fr getting coordinates from website
                                        JSONObject co1 = new JSONObject(response.getString("coord"));
                                        latitude22 = co1.getString("lat");
                                        longitude22 = co1.getString("lon");


                                        //Json object fr getting temperature, preesure and humidity from website
                                        JSONObject co = new JSONObject(response.getString("main"));
                                        double keltemp = co.getDouble("temp");
                                        double celtemp = keltemp - 273.15;

                                        temperature = String.format("%.2f Celsius",celtemp).toString();

                                        pressure22 = co.getString("pressure");
                                        humid22 = co.getString("humidity");


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
                                    Toast.makeText(MainActivity.this, "PLEASE CHECK THE CITY NAME...", Toast.LENGTH_SHORT).show();
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

        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, details.class);
                intent.putExtra("111", description);
                intent.putExtra("222", temperature);
                intent.putExtra("333", pressure22);
                intent.putExtra("444", humid22);
                intent.putExtra("555", latitude22);
                intent.putExtra("666", longitude22);
                startActivity(intent);
            }
        });
    }
}