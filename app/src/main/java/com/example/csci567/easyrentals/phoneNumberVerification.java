package com.example.csci567.easyrentals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import utility.AppPreferences;

public class phoneNumberVerification extends AppCompatActivity {

    private EditText phoneNumber, verificationCode;
    private TextView number;
    private String telephoneNumber;
    private Button sendCode, verifyCode;
    //private ProgressDialog progressDialog;
    private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE= 999;
    private AppPreferences appPreferences;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);

        if (checkSelfPermission(Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.SEND_SMS)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

        }

        appPreferences = new AppPreferences(getApplicationContext());
        phoneNumber = (EditText) findViewById(R.id.getNumber);
        verificationCode = (EditText) findViewById(R.id.getCode);
        number = (TextView) findViewById(R.id.displayCode);
        sendCode = (Button) findViewById(R.id.sendCodeButton);
        verifyCode = (Button) findViewById(R.id.verifyCodeButton);

        sendSMS();
    }

    private void verifyCodeMethod(final String code) {

       /* verifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                if (verificationCode.getText().toString().equals(code)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    if (getCallingActivity() != null){
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("PhoneNumber", telephoneNumber);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                    else {*/
                Intent listCar = new Intent(getApplicationContext(), CarDetails.class);
                startActivity(listCar);
                   /* }
                }
                else Toast.makeText(getApplicationContext(), "The code you entered is wrong. Get new code", Toast.LENGTH_LONG ).show();
            }
        });*/

            }




    public void sendSMS() {

        final String[] message = {""};
        final String[] code = {""};
        final Random random = new Random();
        final int max = 999999;
        final int min = 100000;

        sendCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


               // ProgressDialog.show(phoneNumberVerification.this, "Verification code", "Sending verification code", true);
                telephoneNumber = phoneNumber.getText().toString();
                message[0] = "Use this verification code: ";
                code[0] = String.valueOf(random.nextInt((max - min) + 1) + min);
                message[0] = message[0] + code[0];

                appPreferences.savePhoneNumber(telephoneNumber);

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(telephoneNumber, null, message[0], null, null);

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                verificationCode.setVisibility(View.VISIBLE);
                number.setVisibility(View.VISIBLE);
                verifyCode.setVisibility(View.VISIBLE);
               // progressDialog.dismiss();

                verifyCodeMethod("55");
            }
        });
    }
}
