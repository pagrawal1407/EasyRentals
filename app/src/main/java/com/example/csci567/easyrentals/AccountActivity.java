package com.example.csci567.easyrentals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import utility.AppPreferences;
import utility.UserPreferences;

public class AccountActivity extends AppCompatActivity {

    private String telephoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //TextView phoneNumber = (TextView) findViewById(R.id.phone_number_value);
        AppPreferences appPreferences = new AppPreferences(getApplicationContext());
        UserPreferences userPreferences = new UserPreferences(getApplicationContext());

        if (appPreferences.contains(AppPreferences.KEY_PREFS_PHONE_NUMBER) && userPreferences.contains(UserPreferences.KEY_PREFS_EMAIL)) {
            getCars();
        }
        else {
            Intent phoneNumberIntent = new Intent(getApplicationContext(), phoneNumberVerification.class);
            startActivityForResult(phoneNumberIntent, 999);

            Intent signInIntent = new Intent(getApplicationContext(), signin.class);
            startActivityForResult(signInIntent, 900);
        }

    }

    private void getCars() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 999){
            telephoneNumber = data.getStringExtra("PhoneNumber");
        }

        if (resultCode == RESULT_OK && requestCode == 900){
            String email = data.getStringExtra("Email");
        }
    }
}
