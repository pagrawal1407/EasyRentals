package com.example.csci567.easyrentals;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utility.DataPOJO;

public class CarListActivity extends AppCompatActivity {

    private Date startDate, endDate;
    private String location;
    private Boolean withDriver, withoutDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        startDate = (Date)getIntent().getSerializableExtra("Start Date");
        endDate = (Date)getIntent().getSerializableExtra("End Date");
        location = getIntent().getStringExtra("Location");
        withDriver = getIntent().getBooleanExtra("with driver", true);
        withoutDriver = getIntent().getBooleanExtra("without driver", true);

        geoLocationResolver(location);
    }

    private void geoLocationResolver(String location) {

        Geocoder gc = new Geocoder(getApplicationContext());
        List<Address> list = null;
        try {
            list = gc.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String latitude = "";
        String longitude = "";
        double lat = 0;
        double lgn = 0;
        Address add = null;
        if (list != null) {
            add = list.get(0);
            lat = add.getLatitude();
            lgn = add.getLongitude();
            latitude = Double.toString(lat);
            longitude = Double.toString(lgn);
        }

        volleyCall(longitude, latitude);
    }

    private void volleyCall(String longitude, String latitude) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL;
       //Log.d("Search query","Query: " + query);
        if (!ifNull(latitude, longitude) ) {
            URL = "http://45.79.76.22/EasyRentals/EasyRentals/car/findByDistance" + "?long=" + longitude + "&lat=" + latitude +
                    "&dist=20&withDriver=" + withDriver + "&withoutDriver=" + withoutDriver + "&startDate=" + startDate.getTime() + "&endDate=" + endDate.getTime();

        }
        else
            URL = "http://45.79.76.22/EasyRentals/EasyRentals/car/getCarList";

        Log.i("URL value", URL);
        //final TextView nullServerData = (TextView) findViewById(R.id.serverData);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("response data", response.toString());
                        final ArrayList<DataPOJO> list = new ArrayList<>();
                        try {
                            JSONArray jArray = response;
                            Log.i("jArray data", jArray.toString());
                            for (int i = 0; i < jArray.length(); i++){
                                JSONObject singleItem = jArray.getJSONObject(i);
                                JSONObject address = singleItem.getJSONObject("address");
                                JSONObject geoLocation = address.getJSONObject("geoLocation");
                                DataPOJO data = new DataPOJO();
                               /* data.zipcode = singleItem.optInt("zipcode", 0);
                                data.latitude = singleItem.optDouble("latitude", 0);
                                data.longitude = singleItem.optDouble("longitude", 0);*/
                                data.l.setLatitude(geoLocation.optDouble("latitude" , 0));
                                data.l.setLongitude(geoLocation.optDouble("longitude" , 0));
                                data.address.city = address.optString("city", "city");
                                data.address.state = address.optString("state", "state");
                                data.address.street = address.optString("street", "street");
                                data.address.zipcode = address.optInt("postalCode", 0);
                                data.year = singleItem.optInt("year", 0);
                                data.make = singleItem.optString("make", "make");
                                data.model = singleItem.optString("model", "model");
                                data.transmission = singleItem.optString("transmission", "transmission");
                                data.odometer = singleItem.optDouble("odometer", 0);
                                data.style = singleItem.optString("style", "style");

                                data.drivingLicenseNumber = singleItem.optString("drivingLicenseNumber", "null");
                                data.drivingLicenseState = singleItem.optString("drivingLicenseState", "null");

                                data.maximumDistance = singleItem.optString("maximumDistance", "longestDistance");
                                data.audioPlayer = singleItem.optString("audioPlayer", "audioPlayer");
                                data.bluetooth = singleItem.optString("bluetooth", "bluetooth");
                                data.hybrid = singleItem.optString("hybrid", "hybrid");
                                data.gps = singleItem.optString("gps", "gps");
                                data.petFriendly = singleItem.optString("petFriendly", "petFriendly");
                                data.sunRoof = singleItem.optString("sunRoof", "sunRoof");
                                data.withDriver = singleItem.optBoolean("withDriver", true);
                                data.withoutDriver = singleItem.optBoolean("withoutDriver", true);
                                data.amount = singleItem.optInt("amount", 0);
                                list.add(data);
                            }
                        } catch (JSONException e) {
                            Log.e("JSONException: ", e.getMessage());
                            e.printStackTrace();
                        }
                        Log.i("Array list data:",list.toString());
                        ListView lst = (ListView) findViewById(R.id.car_list);
                        ListViewAdapter adapter = new ListViewAdapter(list,getApplicationContext());
                        lst.setAdapter(adapter);

                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent carChoiceIntent = new Intent(getApplicationContext(), CarChoice.class);
                                carChoiceIntent.putExtra("DataPOJO object", new Gson().toJson(list.get(position)));
                                startActivity(carChoiceIntent);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int data = error.networkResponse.statusCode;
                Log.i("network data", Integer.toString(data));
                Toast.makeText(getApplicationContext(), "Error in request: "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(getRequest);
    }

    private Boolean ifNull(String... args){
        Boolean result = false;
        for (String arg : args) {
            if (arg.equals("")) {
                result = true;
            }
        }
        return result;
    }
}
