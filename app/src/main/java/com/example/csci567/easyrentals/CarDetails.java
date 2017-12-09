package com.example.csci567.easyrentals;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.AppPreferences;

public class CarDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText  city, make, model, odometer, drivingLicenseNumber, zipcode, setLimit, setAmount, licensePlate;
    private Spinner year, transmission, style, state, drivingLicenseState, longestDist;
    private CheckBox gps, hybrid, petFriendly, bluetooth, audioPlayer, sunRoof, withDriverCheckBox, withoutDriverCheckBox;
    private String yearText, transmissionText, styleText, stateText, licenseStateText, longestTrip;
    private AppPreferences appPreferences;
    private PlacesAutocompleteTextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        initializeEditTexts();
        initializeSpinners();
        initializeCheckBoxes();

        appPreferences = new AppPreferences(getApplicationContext());

        ArrayAdapter<CharSequence> adapterTransmission = ArrayAdapter.createFromResource(this, R.array.transmissionArray, R.layout.spinner_layout);
        adapterTransmission.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        transmission.setAdapter(adapterTransmission);


        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2007; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(adapterYear);

        ArrayAdapter<CharSequence> adapterStyle = ArrayAdapter.createFromResource(this, R.array.styleArray, R.layout.spinner_layout);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        style.setAdapter(adapterStyle);

        ArrayAdapter<CharSequence> adapterState = ArrayAdapter.createFromResource(this, R.array.statesArray, R.layout.spinner_layout);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        state.setAdapter(adapterState);
        drivingLicenseState.setAdapter(adapterState);

        ArrayAdapter<CharSequence> adapterlongestDist = ArrayAdapter.createFromResource(this, R.array.longestDistanceArray, R.layout.spinner_layout);
        adapterlongestDist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        longestDist.setAdapter(adapterlongestDist);


        transmission.setOnItemSelectedListener(this);
        year.setOnItemSelectedListener(this);
        style.setOnItemSelectedListener(this);
        state.setOnItemSelectedListener(this);
        drivingLicenseState.setOnItemSelectedListener(this);
        longestDist.setOnItemSelectedListener(this);

       /* setAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                setAmount.setText("Rs  " + setAmount.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });*/

    }

    private void initializeCheckBoxes() {
        gps = (CheckBox) findViewById(R.id.GPSCheckBox);
        hybrid = (CheckBox) findViewById(R.id.HybridCheckBox);
        petFriendly = (CheckBox) findViewById(R.id.PetfriendlyCheckBox);
        bluetooth = (CheckBox) findViewById(R.id.BluetoothCheckBox);
        audioPlayer = findViewById(R.id.AudioCheckBox);
        sunRoof = (CheckBox) findViewById(R.id.sunroofCheckBox);
        withoutDriverCheckBox = (CheckBox) findViewById(R.id.withoutDriver);
        withDriverCheckBox = (CheckBox) findViewById(R.id.withDriver);
    }

    private void initializeSpinners() {
        year = (Spinner) findViewById(R.id.acceptYear);
        transmission = (Spinner) findViewById(R.id.acceptTransmission);
        style = (Spinner) findViewById(R.id.acceptStyle);
        state = (Spinner) findViewById(R.id.acceptState);
        drivingLicenseState = (Spinner) findViewById(R.id.acceptLicenseState);
        longestDist = (Spinner) findViewById(R.id.acceptLongestTrip);
    }

    private void initializeEditTexts() {
        address = (PlacesAutocompleteTextView) findViewById(R.id.acceptAddress);
        city = (EditText) findViewById(R.id.acceptCity);
        make = (EditText) findViewById(R.id.acceptMake);
        model = (EditText) findViewById(R.id.acceptModel);
        odometer = (EditText) findViewById(R.id.acceptOdometer);
        drivingLicenseNumber = (EditText) findViewById(R.id.acceptLicenseNumber);
        zipcode = (EditText) findViewById(R.id.acceptZipCode);
        setLimit = (EditText) findViewById(R.id.setlimit);
        setAmount = (EditText) findViewById(R.id.setamount);
        licensePlate = (EditText) findViewById(R.id.acceptlicensePlate);
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

    public void onNextPressed(View view) {

        String addressText, cityText, makeText, modelText, odometerText, drivingLicenseNumberText, zipcodeText, licensePlateText;
        String gpsText, hybridText, petFriendlyText, bluetoothText, audioText, sunRoofText;
        String limit, withDriver, withoutDriver, amount;

        if (longestTrip.equals("Set Limit")) {
            limit = setLimit.getText().toString();
        }
        else
            limit = longestTrip;
        Toast.makeText(this, limit, Toast.LENGTH_SHORT).show();

        addressText = address.getText().toString();
        cityText = city.getText().toString();
        makeText = make.getText().toString();
        modelText = model.getText().toString();
        odometerText = odometer.getText().toString();
        drivingLicenseNumberText = drivingLicenseNumber.getText().toString();
        zipcodeText = zipcode.getText().toString();
        amount = setAmount.getText().toString();
        licensePlateText = licensePlate.getText().toString();


        if (gps.isChecked())
            gpsText = "true";
        else gpsText = "false";

        if (hybrid.isChecked())
            hybridText = "true";
        else hybridText = "false";

        if (petFriendly.isChecked())
            petFriendlyText = "true";
        else petFriendlyText = "false";

        if (bluetooth.isChecked())
            bluetoothText = "true";
        else bluetoothText = "false";

        if (audioPlayer.isChecked())
            audioText = "true";
        else audioText = "false";

        if (sunRoof.isChecked())
            sunRoofText = "true";
        else sunRoofText = "false";

        if (withoutDriverCheckBox.isChecked()){
            withoutDriver = "true";
        }
        else withoutDriver = "false";
        if (withDriverCheckBox.isChecked()){
            withDriver = "true";
        }
        else withDriver = "false";

       /* transmissionText = transmission.getSelectedItem().toString();
        yearText = year.getSelectedItem().toString();
        styleText = style.getSelectedItem().toString();
*/

        Geocoder gc = new Geocoder(getApplicationContext());
        List<Address> list = null;
        try {
            list = gc.getFromLocationName(addressText, 1);
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


        JSONObject location = new JSONObject();
        JSONObject address = new JSONObject();
        try {
            location.put("latitude",latitude);
            location.put("longitude", longitude);
            address.put("geoLocation", location);
            address.put("city", cityText);
            address.put("street",addressText);
            address.put("postalCode",zipcodeText);
            address.put("state", stateText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String,Object> jsonparams = new HashMap<String, Object>();

       /* Intent nextIntent = new Intent(this, ExteriorImageUpload.class);
        Bundle bundle = new Bundle();*/
        jsonparams.put("address", address);
        jsonparams.put("make", makeText);
        jsonparams.put("model", modelText);
        jsonparams.put("odometer", odometerText);
        jsonparams.put("drivingLicenseNumber", drivingLicenseNumberText);
        jsonparams.put("gps", gpsText);
        jsonparams.put("hybrid", hybridText);
        jsonparams.put("petFriendly", petFriendlyText);
        jsonparams.put("bluetooth", bluetoothText);
        jsonparams.put("audioPlayer", audioText);
        jsonparams.put("sunRoof", sunRoofText);
        jsonparams.put("transmission", transmissionText);
        jsonparams.put("year", yearText);
        jsonparams.put("style", styleText);
        jsonparams.put("phoneNumber", appPreferences.getPhoneNumber());
        jsonparams.put("licenseState", licenseStateText);
        jsonparams.put("longestDistance", limit);
        jsonparams.put("withDriver", withDriver);
        jsonparams.put("withoutDriver", withoutDriver);
        jsonparams.put("amount", amount);
        jsonparams.put("licenseNum", licensePlateText);
        Toast.makeText(this, transmissionText + " " + yearText + " " + styleText,Toast.LENGTH_SHORT).show();

        appPreferences.saveDrivingLicense(drivingLicenseNumberText);

        if (ifNull(limit, amount)) {
            Toast.makeText(this, "Please enter a value in set limit and amount field", Toast.LENGTH_LONG).show();

        } else {
            volleycall(jsonparams);
        }
/*      nextIntent.putExtras(bundle);
        startActivity(nextIntent);*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("address", address.getText().toString());
        outState.putString("city", city.getText().toString());
        outState.putInt("state", state.getSelectedItemPosition());
        outState.putInt("licenseState", drivingLicenseState.getSelectedItemPosition());
        outState.putInt("year", year.getSelectedItemPosition());
        outState.putInt("transmission", transmission.getSelectedItemPosition());
        outState.putInt("style", style.getSelectedItemPosition());
        outState.putInt("longestTrip", longestDist.getSelectedItemPosition());
        if (longestTrip.equals( "Set Limit"))
            outState.putString("limit", setLimit.getText().toString());

        outState.putString("licenseNumber", drivingLicenseNumber.getText().toString());
        outState.putString("zipcode", zipcode.getText().toString());
        outState.putString("make", make.getText().toString());
        outState.putString("model", model.getText().toString());
        outState.putString("odometer", odometer.getText().toString());
        outState.putString("rentAmount", setAmount.getText().toString());

        outState.putBoolean("gps", gps.isChecked());
        outState.putBoolean("hybrid", hybrid.isChecked());
        outState.putBoolean("sunRoof", sunRoof.isChecked());
        outState.putBoolean("bluetooth", bluetooth.isChecked());
        outState.putBoolean("petFriendly", petFriendly.isChecked());
        outState.putBoolean("audioPlayer", audioPlayer.isChecked());
        outState.putBoolean("withDriver", withDriverCheckBox.isChecked());
        outState.putBoolean("withDriver", withDriverCheckBox.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            address.setText(savedInstanceState.getString("address", ""));
            city.setText(savedInstanceState.getString("city", ""));
            zipcode.setText(savedInstanceState.getString("zipcode", ""));
            drivingLicenseNumber.setText(savedInstanceState.getString("licenseNumber", ""));
            make.setText(savedInstanceState.getString("make", ""));
            model.setText(savedInstanceState.getString("model", ""));
            odometer.setText(savedInstanceState.getString("odometer", ""));
            setAmount.setText(savedInstanceState.getString("rentAmount", ""));

            gps.setChecked(savedInstanceState.getBoolean("gps", false));
            hybrid.setChecked(savedInstanceState.getBoolean("hybrid", false));
            sunRoof.setChecked(savedInstanceState.getBoolean("sunRoof", false));
            bluetooth.setChecked(savedInstanceState.getBoolean("bluetooth", false));
            petFriendly.setChecked(savedInstanceState.getBoolean("petFriendly", false));
            audioPlayer.setChecked(savedInstanceState.getBoolean("audioPlayer", false));
            withDriverCheckBox.setChecked(savedInstanceState.getBoolean("withDriver", false));
            withoutDriverCheckBox.setChecked(savedInstanceState.getBoolean("withoutDriver", false));

            drivingLicenseState.setSelection(savedInstanceState.getInt("licenseState", 0));
            state.setSelection(savedInstanceState.getInt("state", 0));
            year.setSelection(savedInstanceState.getInt("year", 0));
            transmission.setSelection(savedInstanceState.getInt("transmission", 0));
            style.setSelection(savedInstanceState.getInt("style", 0));
            longestDist.setSelection(savedInstanceState.getInt("longestTrip", 0));
        }
    }

    private void volleycall(Map jsonparams) {

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("JSON parameters","Json: " + new JSONObject(jsonparams));
        String URL = "http://45.79.76.22/EasyRentals/EasyRentals/car/listyourcar";

        Toast.makeText(this,"Sending data",Toast.LENGTH_SHORT).show();
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, URL, new JSONObject(jsonparams),
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
                            Intent intent = new Intent(getBaseContext(), ImageUpload.class);
                            Toast.makeText(CarDetails.this,"Success, data sent",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(CarDetails.this, "Data not sent. Look for error!", Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.acceptTransmission:
                transmissionText = adapterView.getSelectedItem().toString();
                break;
            case R.id.acceptYear:
                yearText = adapterView.getSelectedItem().toString();
                break;
            case R.id.acceptStyle:
                styleText = adapterView.getSelectedItem().toString();
                break;
            case R.id.acceptState:
                stateText = adapterView.getSelectedItem().toString();
                break;
            case R.id.acceptLicenseState:
                licenseStateText = adapterView.getSelectedItem().toString();
                break;

            case R.id.acceptLongestTrip:
                longestTrip = adapterView.getSelectedItem().toString();
                if (longestTrip.equals("Set Limit")) {
                    setLimit.setVisibility(View.VISIBLE);
                } else if (longestTrip.equals("No Limit")) {
                    setLimit.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
