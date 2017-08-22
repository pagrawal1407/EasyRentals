package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listCar(View view){
        Intent verifyPhone = new Intent(this, phoneNumberVerification.class);
        startActivity(verifyPhone);
    }
}
