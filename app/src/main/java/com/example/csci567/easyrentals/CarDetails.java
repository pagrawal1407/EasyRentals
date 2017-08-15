package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CarDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
    }


    public void onNextPressed(View view) {
        Intent nextIntent = new Intent(this, ExteriorImageUpload.class);
        startActivity(nextIntent);
    }
}
