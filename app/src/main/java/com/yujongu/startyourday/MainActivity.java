package com.yujongu.startyourday;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    ViewPager viewPager;
    LinearLayout dots;

    LocationManager locationManager;

    private SliderAdapter sliderAdapter;

    Context context;

    ArrayList<CityInfo> cities = new ArrayList<CityInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
        eventListener();
    }

    private void initInstance() {

        queue = Volley.newRequestQueue(this);

        context = this;

        cities.add(new CityInfo());
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        viewPager = findViewById(R.id.vPager);
        dots = findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(context, cities);
    }

    private void eventListener() {
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            findWeather(latitude, longitude, "");


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String city = data.getStringExtra("cityName");

                cities.add(new CityInfo());
                sliderAdapter.notifyDataSetChanged();
                findWeather(0, 0, city);
            }
        }
    }


    public void findWeather(double latitude, double longitude, final String cityName){

        String url = "http://api.openweathermap.org/data/2.5/weather?";
        String apikey = "appid=a06a63c1527bea66c1dcc1abe1ee4170";
        String cityInput = "&q=";
        String units = "&units=metric";
        String lat = "&lat=" + latitude;
        String lon = "&lon=" + longitude;

        if (cityName.length() != 0){
            lat = "";
            lon = "";
        } else {
            cityInput = "";
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + apikey + cityInput + cityName + units + lat + lon, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main_object = response.getJSONObject("main");
                            JSONObject sys_object = response.getJSONObject("sys");
                            JSONObject coord_object = response.getJSONObject("coord");
                            JSONArray array = response.getJSONArray("weather");
                            JSONObject weather = array.getJSONObject(0);

                            cities.get(cities.size() - 1).setCountry(sys_object.getString("country"));
                            cities.get(cities.size() - 1).setName(response.getString("name"));
                            cities.get(cities.size() - 1).setId(response.getInt("id"));
                            cities.get(cities.size() - 1).setCel(main_object.getDouble("temp"));
                            double fehrenheit = (cities.get(cities.size() - 1).getCel() * 9 / 5) + 32;
                            BigDecimal bd = BigDecimal.valueOf(fehrenheit);
                            bd = bd.setScale(2, RoundingMode.HALF_UP);
                            fehrenheit = bd.doubleValue();

                            cities.get(cities.size() - 1).setFeh(fehrenheit);
                            cities.get(cities.size() - 1).setDesc(weather.getString("description"));
                            cities.get(cities.size() - 1).setImageId(weather.getString("icon"));

                            cities.get(cities.size() - 1).setLat(coord_object.getDouble("lat"));
                            cities.get(cities.size() - 1).setLon(coord_object.getDouble("lon"));

                            viewPager.setAdapter(sliderAdapter);
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
