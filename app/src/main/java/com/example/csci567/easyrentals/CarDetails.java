package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class CarDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText address, city, make, model, odometer, trim, drivingLicenseNumber;
    private Spinner year, transmission, style, state, drivingLicenseState;
    private CheckBox gps, hybrid, petFriendly, bluetooth, audioPlayer, sunRoof;
    private String yearText, transmissionText, styleText, stateText, licenseStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        initializeEditTexts();
        initializeSpinners();
        initializeCheckBoxes();

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

        transmission.setOnItemSelectedListener(this);
        year.setOnItemSelectedListener(this);
        style.setOnItemSelectedListener(this);
        state.setOnItemSelectedListener(this);
        drivingLicenseState.setOnItemSelectedListener(this);

    }

    private void initializeCheckBoxes() {
        gps = (CheckBox) findViewById(R.id.GPSCheckBox);
        hybrid = (CheckBox) findViewById(R.id.HybridCheckBox);
        petFriendly = (CheckBox) findViewById(R.id.PetfriendlyCheckBox);
        bluetooth = (CheckBox) findViewById(R.id.BluetoothCheckBox);
        audioPlayer = (CheckBox) findViewById(R.id.AudioCheckBox);
        sunRoof = (CheckBox) findViewById(R.id.sunroofCheckBox);
    }

    private void initializeSpinners() {
        year = (Spinner) findViewById(R.id.acceptYear);
        transmission = (Spinner) findViewById(R.id.acceptTransmission);
        style = (Spinner) findViewById(R.id.acceptStyle);
        state = (Spinner) findViewById(R.id.acceptState);
        drivingLicenseState = (Spinner) findViewById(R.id.acceptLicenseState);
    }

    private void initializeEditTexts() {
        address = (EditText) findViewById(R.id.acceptAddress);
        city = (EditText) findViewById(R.id.acceptCity);
        make = (EditText) findViewById(R.id.acceptMake);
        model = (EditText) findViewById(R.id.acceptModel);
        odometer = (EditText) findViewById(R.id.acceptOdometer);
        trim = (EditText) findViewById(R.id.acceptTrim);
        drivingLicenseNumber = (EditText) findViewById(R.id.acceptLicenseNumber);
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

        String addressText, cityText, makeText, modelText, odometerText, trimText, drivingLicenseNumberText;
        String gpsText, hybridText, petFriendlyText, bluetoothText, audioText, sunRoofText;

        addressText = address.getText().toString();
        cityText = city.getText().toString();
        makeText = make.getText().toString();
        modelText = model.getText().toString();
        odometerText = odometer.getText().toString();
        trimText = trim.getText().toString();
        drivingLicenseNumberText = drivingLicenseNumber.getText().toString();


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

       /* transmissionText = transmission.getSelectedItem().toString();
        yearText = year.getSelectedItem().toString();
        styleText = style.getSelectedItem().toString();
*/
        Intent nextIntent = new Intent(this, ExteriorImageUpload.class);
        Bundle bundle = new Bundle();
        bundle.putString("address", addressText);
        bundle.putString("city", cityText);
        bundle.putString("make", makeText);
        bundle.putString("model", modelText);
        bundle.putString("odometer", odometerText);
        bundle.putString("trim", trimText);
        bundle.putString("licenseNumber", drivingLicenseNumberText);
        bundle.putString("gps", gpsText);
        bundle.putString("hybrid", hybridText);
        bundle.putString("petFriendly", petFriendlyText);
        bundle.putString("bluetooth", bluetoothText);
        bundle.putString("audioPlayer", audioText);
        bundle.putString("sunRoof", sunRoofText);
        bundle.putString("transmission", transmissionText);
        bundle.putString("year", yearText);
        bundle.putString("style", styleText);
        bundle.putString("state", stateText);
        bundle.putString("licenseState", licenseStateText);
        Toast.makeText(this, transmissionText + " " + yearText + " " + styleText,Toast.LENGTH_SHORT).show();
        nextIntent.putExtras(bundle);
        startActivity(nextIntent);
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
