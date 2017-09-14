package com.example.csci567.easyrentals;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utility.DataPOJO;

public class CarListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
    }

    private void volleyCall(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL;
        Log.d("Search query","Query: " + query);
        if (!query.equals("") ) {
            URL = "http://45.79.76.22:9080/EasyRentals/car/getCarList/" + query;
        }
        else
            URL = "http://45.79.76.22:9080/EasyRentals/car/getCarList";
        //final TextView nullServerData = (TextView) findViewById(R.id.serverData);

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
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
                                DataPOJO data = new DataPOJO();
                                data.zipcode = singleItem.optInt("zipcode", 0);
                                data.latitude = singleItem.optDouble("latitude", 0);
                                data.longitude = singleItem.optDouble("longitude", 0);
                                data.year = singleItem.optInt("year", 0);
                                data.make = singleItem.optString("make", "make");
                                data.model = singleItem.optString("model", "model");
                                data.transmission = singleItem.optString("transmission", "transmission");
                                data.odometer = singleItem.optDouble("odometer", 0);
                                data.style = singleItem.optString("style", "style");
                                data.carDescription = singleItem.optString("carDes", "carDes");
                                data.advanceNotice = singleItem.optString("advNotice", "advNotice");
                                data.trim = singleItem.optString("trim", "trim");
                                //data.image = singleItem.optString("carPic", "null");
                                data.licenseNumber = singleItem.optString("licenseNum", "null");
                                data.issuingCountry = singleItem.optString("issuingCountry", "null");
                                data.issuingState = singleItem.optString("issuingState", "null");
                                data.licensePlateNumber = singleItem.optString("licensePlateNum", "null");
                                data.licenseState = singleItem.optString("licenseState", "null");
                                data.lName = singleItem.optString("lNameOnLic", "fname");
                                data.fName = singleItem.optString("fNameOnLic", "lname");
                                data.minimumDuration = singleItem.optString("shortPT", "minimumDuration");
                                data.longestDistance = singleItem.optString("longPT", "longestDistance");
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
                               /* Intent carChoiceIntent = new Intent(getApplicationContext(), CarChoice.class);
                                carChoiceIntent.putExtra("DataPOJO object", new Gson().toJson(list.get(position)));
                                startActivity(carChoiceIntent);*/
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error in request: "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(postRequest);
    }
}
