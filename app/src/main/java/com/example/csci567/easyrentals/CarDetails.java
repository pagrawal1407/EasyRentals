package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class CarDetails extends AppCompatActivity {
    private EditText address, city, make, model, odometer, trim;
    private Spinner year, transmission, style, state;
    private CheckBox gps, hybrid, petFriendly, bluetooth, audioPlayer, sunRoof;

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
        //state = (Spinner) findViewById(R.id.acceptState);
    }

    private void initializeEditTexts() {
        address = (EditText) findViewById(R.id.acceptAddress);
        city = (EditText) findViewById(R.id.acceptCity);
        make = (EditText) findViewById(R.id.acceptMake);
        model = (EditText) findViewById(R.id.acceptModel);
        odometer = (EditText) findViewById(R.id.acceptOdometer);
        trim = (EditText) findViewById(R.id.acceptTrim);
    }


    public void onNextPressed(View view) {
        Intent nextIntent = new Intent(this, ExteriorImageUpload.class);
        startActivity(nextIntent);
    }
}
