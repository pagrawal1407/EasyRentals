package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utility.UserPreferences;

public class SignUpNew extends AppCompatActivity {

    public TextView memberText;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText fname,lname, email,passwd, contactNum;
        final Button signup;

        fname = (EditText) findViewById(R.id.firstnamenew);
        lname = (EditText) findViewById(R.id.lastnamenew);
        email = (EditText) findViewById(R.id.signupnew_email);
        passwd = (EditText) findViewById(R.id.signupnew_password);
        contactNum = (EditText) findViewById(R.id.signupnew_contactNum);

        memberText = (TextView)findViewById(R.id.membertext);

        userPreferences = new UserPreferences(getApplicationContext());

        memberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newSignupActivity = new Intent(getApplicationContext(),signin.class);
                startActivity(newSignupActivity);
                finish();
            }
        });

        signup = (Button) findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fnameInput = fname.getText().toString();
                String lnameInput = lname.getText().toString();
                String emailInput = email.getText().toString();
                String passwdInput = passwd.getText().toString();
                String contactNumInput = contactNum.getText().toString();

                if (ifNull(fnameInput, lnameInput, emailInput, passwdInput, contactNumInput)){
                    Toast.makeText(SignUpNew.this, "Please enter all the details", Toast.LENGTH_LONG).show();
                }

               /* if (fnameInput.equals("") && lnameInput.equals("") && emailInput.equals("") && passwdInput.equals(""))
                    Toast.makeText(signup.this, "Please enter all the details", Toast.LENGTH_LONG).show();
                if (fnameInput.equals("") )
                    Toast.makeText(signup.this, "Please enter First Name.", Toast.LENGTH_LONG).show();
                else if(lnameInput.equals(""))
                    Toast.makeText(signup.this, "Please enter Last Name.", Toast.LENGTH_LONG).show();
                else if (emailInput.equals(""))
                    Toast.makeText(signup.this, "Please enter Email.", Toast.LENGTH_LONG).show();
                else if(passwdInput.equals(""))
                    Toast.makeText(signup.this, "Please enter Password.", Toast.LENGTH_LONG).show();
*/
                else {

                    if (isValidEmail(emailInput)) {
                        volleyCall(fnameInput, lnameInput, emailInput, passwdInput, contactNumInput);
                    }
                    else
                        Toast.makeText(SignUpNew.this, "Please enter a valid Email.", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private boolean isValidEmail(String emailInput) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches();
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

    private void volleyCall(final String fnameInput, final String lnameInput, final String emailInput, String passwdInput, String contactNumInput) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://45.79.76.22/EasyRentals/EasyRentals/registerUser";

        Map<String,String> jsonparams = new HashMap<>();

        jsonparams.put("fName",fnameInput);
        jsonparams.put("lName",lnameInput);
        jsonparams.put("email",emailInput);
        jsonparams.put("password",passwdInput);
        jsonparams.put("contactNum", contactNumInput);

        Log.d("JSON parameters","Json: " + new JSONObject(jsonparams));
        //Toast.makeText(signup.this,"In the volleycall method",Toast.LENGTH_SHORT).show();
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonparams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String msg = null;
                        try{
                            msg = (String) response.get("value");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        if (msg != null) {
                            if (msg.equals("Thanks for Signup") ){

                                userPreferences.saveEmail(emailInput);
                                userPreferences.saveFirstName(fnameInput);
                                userPreferences.saveLastName(lnameInput);

                                Intent intent = new Intent(getBaseContext(), CarDetails.class);
                                Toast.makeText(SignUpNew.this,"Success, you are logged in", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(SignUpNew.this,"Incorrect Username or Password", Toast.LENGTH_LONG).show();
                        }
                    }

                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e ("Error recieved","Error: " + error + "\n Message: " + error.getMessage());
            }
        });
        queue.add(postRequest);
    }
}
