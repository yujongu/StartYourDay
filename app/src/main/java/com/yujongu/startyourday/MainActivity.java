package com.yujongu.startyourday;

import android.content.res.ColorStateList;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{

    CityInfo cityInfo = new CityInfo();

    TextView tempTv;
    TextView descTv;
    TextView cityNameTv;
    Button celBt;
    Button fehBt;
    ColorStateList tint;
    ColorStateList transp;

    String unit = "metric";

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
        eventListener();

        queue = Volley.newRequestQueue(this);
        findWeather(unit);
        
    }

    private void initInstance(){
        tempTv = findViewById(R.id.tvTemp);
        descTv = findViewById(R.id.tvDesc);
        cityNameTv = findViewById(R.id.tvCityName);
        celBt = findViewById(R.id.btCel);
        fehBt = findViewById(R.id.btFeh);

        tint = celBt.getBackgroundTintList();
        transp = fehBt.getBackgroundTintList();

    }
    private void eventListener(){
        celBt.setOnClickListener(listener);
        fehBt.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){
                case R.id.btCel:
                    celBt.setBackgroundTintList(tint);
                    fehBt.setBackgroundTintList(transp);

                    tempTv.setText(String.valueOf(cityInfo.getCel()));


                    break;

                case R.id.btFeh:
                    celBt.setBackgroundTintList(transp);
                    fehBt.setBackgroundTintList(tint);

                    tempTv.setText(String.valueOf(cityInfo.getFeh()));
                    break;
            }
        }
    };

    public void findWeather(String unit){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=a06a63c1527bea66c1dcc1abe1ee4170&units=metric";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONObject sys_object = response.getJSONObject("sys");
                    JSONObject coord_object = response.getJSONObject("coord");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject weather = array.getJSONObject(0);


                    cityInfo.setCountry(sys_object.getString("country"));
                    cityInfo.setName(response.getString("name"));
                    cityInfo.setId(response.getInt("id"));
                    cityInfo.setCel(main_object.getDouble("temp"));
                    double fehrenheit = (cityInfo.getCel() * 9 / 5) + 32;
                    cityInfo.setFeh(fehrenheit);
                    cityInfo.setDesc(weather.getString("description"));
                    cityInfo.setLat(coord_object.getDouble("lat"));
                    cityInfo.setLon(coord_object.getDouble("lon"));

                    tempTv.setText(String.valueOf(cityInfo.getCel()));
                    descTv.setText(cityInfo.getDesc());
                    cityNameTv.setText(cityInfo.getName());
                    cityNameTv.append(", " + cityInfo.getCountry());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }



}
