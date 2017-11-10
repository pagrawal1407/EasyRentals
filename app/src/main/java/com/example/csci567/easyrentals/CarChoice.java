package com.example.csci567.easyrentals;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import utility.AppPreferences;
import utility.DataPOJO;
import utility.UserPreferences;

public class CarChoice extends AppCompatActivity {
    private android.os.Handler handler;
    private AppPreferences appPreferences;
    private UserPreferences userPreferences;
    DataPOJO data;
    private Date startDate, endDate;
    String telephoneNumber, email, licPlateNumber;
    private ViewPager pageView1;
    private CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_choice);

        appPreferences = new AppPreferences(getApplicationContext());
        userPreferences = new UserPreferences(getApplicationContext());

        String jsonmyobject = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jsonmyobject = bundle.getString("DataPOJO object");
            startDate = (Date)getIntent().getSerializableExtra("Start Date");
            endDate = (Date)getIntent().getSerializableExtra("End Date");

        }
        data = new Gson().fromJson(jsonmyobject, DataPOJO.class);
        TextView textView = (TextView) findViewById(R.id.datapojo_display);
        pageView1 = (ViewPager) findViewById(R.id.viewpager1);
        adapter = new CustomSwipeAdapter(this, data.drivingLicenseNumber);
        pageView1.setAdapter(adapter);
        pageView1.setClipToPadding(false);
        pageView1.setPadding(140,0,140,0);
        pageView1.setPageMargin(30);

        //Toast.makeText(this,data.make + " " + data.model, Toast.LENGTH_SHORT).show();
        if (!data.model.equals("") && !data.make.equals(""))
            textView.setText(data.make + " " + data.model);

        TextView name = (TextView) findViewById(R.id.carChoiceName);

        //Toast.makeText(this,data.fName + " " + data.lName, Toast.LENGTH_SHORT).show();
//        if (data.fName != ""  && data.lName != "")
//            name.setText(data.fName + " " + data.lName);

        if (data.address.geoLocation.getLatitude() != 0 && data.address.geoLocation.getLongitude() != 0) {
            Geocoder gc = new Geocoder(getApplicationContext());
            List<Address> addList = null;
            try {
                addList = gc.getFromLocation(data.address.geoLocation.getLatitude(), data.address.geoLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address add = null;
            if (addList != null) {
                add = addList.get(0);
            }
            String location = null;
            if (add != null) {
                location = add.getLocality();
            }
            TextView locationView = (TextView) findViewById(R.id.carChoiceLocation);
            locationView.setText(location);
        }

        TextView details = (TextView) findViewById(R.id.carChoiceDetails);

        if (data.transmission != ""  && data.odometer != 0)
            details.setText(data.transmission + " " + ((int) data.odometer) + " miles");

       // final RequestQueue queue = Volley.newRequestQueue(this);
        //final String URL = "http://45.79.76.22/EasyRentals/EasyRentals/image/download";

        handler = new android.os.Handler();
        final Map<String,String> jsonparams = new HashMap<String, String>();
        Toast.makeText(getApplicationContext(), data.drivingLicenseNumber, Toast.LENGTH_LONG).show();
        jsonparams.put("fileName",data.drivingLicenseNumber);
        final String[] fileName = new String[1];
        final String[] image = new String[1];

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
               // RequestBody fileBody = RequestBody.create(MediaType.parse(content_type),bos.toByteArray());
                String URL = "http://45.79.76.22/EasyRentals/EasyRentals/image/download" + "?fileName="+data.drivingLicenseNumber + "Exterior1";

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(URL)
                        .build();

                okhttp3.Response response = null;

                try {
                    response = client.newCall(request).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    fileName[0] = jsonObject.getString("name");
                    image[0] = jsonObject.getString("data");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView imageName = (TextView) findViewById(R.id.carImageName);
                            imageName.setText(fileName[0]);

                           // ImageView carImage = (ImageView) findViewById(R.id.car_image);
                            byte[] decodedImage = Base64.decode(image[0], Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                            //carImage.setImageBitmap(decodedByte);
                        }
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        t.start();

    }

    public void reserveCar(View view) {

        if (appPreferences.contains(appPreferences.KEY_PREFS_PHONE_NUMBER) && userPreferences.contains(userPreferences.KEY_PREFS_EMAIL)) {
            telephoneNumber = appPreferences.getPhoneNumber();
            licPlateNumber = appPreferences.getDrivingLicense();

            volleyCall ();
        }
        else {
            Intent phoneNumberIntent = new Intent(getApplicationContext(), phoneNumberVerification.class);
            startActivityForResult(phoneNumberIntent, 999);

            Intent signInIntent = new Intent(getApplicationContext(), signin.class);
            startActivityForResult(signInIntent, 900);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 999){
            telephoneNumber = data.getStringExtra("PhoneNumber");
        }

        if (resultCode == RESULT_OK && requestCode == 900){
            email = data.getStringExtra("Email");
        }

        if (telephoneNumber != null && email != null)
            volleyCall();
    }

    private void volleyCall() {

        Map<String, String> jsonparams = new HashMap<>();
        jsonparams.put("contactNum", telephoneNumber);
        jsonparams.put("licenseNum", data.drivingLicenseNumber);
        jsonparams.put("startDate", startDate.toString());
        jsonparams.put("endDate", endDate.toString());

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("JSON parameters","Json: " + new JSONObject(jsonparams));
        String URL = "http://45.79.76.22/EasyRentals/EasyRentals/reservation/add";

        Toast.makeText(this,"Sending data",Toast.LENGTH_SHORT).show();
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonparams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response value", response.toString());
                        String msg = null;
                        try{
                            msg = (String) response.get("Value");
                            Log.i("Value of msg: ",msg);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        if (msg.equals("Saved") ){
                            Intent intent = new Intent(getBaseContext(), ExteriorImageUpload.class);
                            Toast.makeText(CarChoice.this,"Success, data sent",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(CarChoice.this, "Data not sent. Look for error!", Toast.LENGTH_LONG).show();
                        }
                    }

                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e ("Error recieved","Error: " + error + "\n Message: " + error.getMessage());
            }
        }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;

            }
        };
        queue.add(postRequest);
    }
}
