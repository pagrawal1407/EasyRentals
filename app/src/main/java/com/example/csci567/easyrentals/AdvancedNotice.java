package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AdvancedNotice extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private Spinner advanceNotice, shortestTrip, longestDist;
    private CheckBox withDriverCheckBox, withoutDriverCheckBox;
    private EditText setLimit;
    private String advNotice;
    private String shortTrip;
    private String longestTrip, withDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_notice);

        intializeSpinners();
        setLimit = (EditText) findViewById(R.id.setlimit);
        withoutDriverCheckBox = (CheckBox) findViewById(R.id.withoutDriver);
        withDriverCheckBox = (CheckBox) findViewById(R.id.withDriver);

        ArrayAdapter<CharSequence> adapteradvNotice = ArrayAdapter.createFromResource(this, R.array.advNoticeArray, R.layout.spinner_layout);
        adapteradvNotice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        advanceNotice.setAdapter(adapteradvNotice);

        ArrayAdapter<CharSequence> adaptershortestTrip = ArrayAdapter.createFromResource(this, R.array.shortestTripArray, R.layout.spinner_layout);
        adaptershortestTrip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        shortestTrip.setAdapter(adaptershortestTrip);

        ArrayAdapter<CharSequence> adapterlongestDist = ArrayAdapter.createFromResource(this, R.array.longestDistanceArray, R.layout.spinner_layout);
        adapterlongestDist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        longestDist.setAdapter(adapterlongestDist);

        advanceNotice.setOnItemSelectedListener(this);
        shortestTrip.setOnItemSelectedListener(this);
        longestDist.setOnItemSelectedListener(this);

        withDriverCheckBox.setOnCheckedChangeListener(this);
        withoutDriverCheckBox.setOnCheckedChangeListener(this);

    }

    private void intializeSpinners() {
        advanceNotice = (Spinner) findViewById(R.id.acceptAdvanceNotice);
        shortestTrip = (Spinner) findViewById(R.id.acceptShortestTrip);
        longestDist = (Spinner) findViewById(R.id.acceptLongestTrip);
    }

    public void onNextPressed(View view) {

        String limit;

        if (longestTrip.equals("Set Limit")) {
            limit = setLimit.getText().toString();
        } else limit = longestTrip;
        Toast.makeText(this, limit, Toast.LENGTH_SHORT).show();
        if (limit.equals("")) {
            Toast.makeText(this, "Please enter a value in set limit field", Toast.LENGTH_SHORT).show();
        } else {
            Intent nextIntent = new Intent(this, InsuranceDetails.class);
            startActivity(nextIntent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {
            case R.id.acceptAdvanceNotice:
                advNotice = adapterView.getSelectedItem().toString();
                break;
            case R.id.acceptShortestTrip:
                shortTrip = adapterView.getSelectedItem().toString();
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (withoutDriverCheckBox.isChecked()){
            withDriver = "true";
            withDriverCheckBox.setChecked(false);
        }
        if (withDriverCheckBox.isChecked()){
            withDriver = "false";
            withoutDriverCheckBox.setChecked(false);
        }
    }
}
