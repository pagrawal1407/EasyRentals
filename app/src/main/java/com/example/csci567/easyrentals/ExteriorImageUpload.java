package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExteriorImageUpload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exterior_image_upload);
    }

    public void onNextPressed(View view) {
        Intent intent = new Intent(this, InteriorImageUpload.class);
        startActivity(intent);
    }
}
