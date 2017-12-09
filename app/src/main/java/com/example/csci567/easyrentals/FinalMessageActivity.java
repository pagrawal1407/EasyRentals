package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FinalMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_message);
    }

    public void goHome(View view) {
        Intent homeIntent = new Intent(FinalMessageActivity.this, MainActivity.class);
        startActivity(homeIntent);
    }
}
