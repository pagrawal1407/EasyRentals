package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpChoiceScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_choice_screen);
    }

    public void signIn(View view) {
        Intent signIn = new Intent (this, signin.class);
        startActivity(signIn);
    }

    public void signUp(View view) {
        Intent signUp = new Intent (this, signup.class);
        startActivity(signUp);
    }
}
