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

public class SignInNew extends AppCompatActivity {

    private EditText email, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_new);

        email = (EditText) findViewById(R.id.signinnew_email);
        passwd = (EditText) findViewById(R.id.signinnew_password);
        final TextView newMemberText = (TextView) findViewById(R.id.notamembertextnew);

        newMemberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newSignupActivity = new Intent(getApplicationContext(),signup.class);
                if (getCallingActivity() != null)
                    newSignupActivity.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(newSignupActivity);
                finish();
            }
        });

        Button signin = (Button) findViewById(R.id.signinnew_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString();
                String passwdInput = passwd.getText().toString();

                if (ifNull(emailInput, passwdInput)){
                    Toast.makeText(SignInNew.this, "Please input email and password.", Toast.LENGTH_LONG).show();
                }
                else {
//                    Intent intent = new Intent(getBaseContext(), WelcomePage.class);
//                    startActivity(intent);
                    volleyCall(emailInput,passwdInput);
                }
            }
        });

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


    private void volleyCall(final String emailInput, String passwdInput) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://45.79.76.22/EasyRentals/EasyRentals/getUserDetails";

        Map<String,String> jsonparams = new HashMap<>();
        jsonparams.put("email",emailInput);
        jsonparams.put("password",passwdInput);

        Log.d("JSON parameters","Json: " + new JSONObject(jsonparams));
        //Toast.makeText(signin.this,"In the volleycall method",Toast.LENGTH_SHORT).show();
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonparams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response value", response.toString());
                        String msg = null;
                        try{
                            msg = (String) response.get("value");
                            Log.i("Value of msg: ", msg);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        if (msg != null) {
                            if (!msg.equals("false") ){

                                /*if (!userPreferences.contains(UserPreferences.KEY_PREFS_EMAIL)){
                                    userPreferences.saveEmail(emailInput);
                                }*/
                                Intent intent = new Intent(getBaseContext(), CarDetails.class);
                                Toast.makeText(SignInNew.this, "Success, you are logged in", Toast.LENGTH_SHORT).show();
                                //intent.putExtra("name", msg);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(SignInNew.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                                passwd.setText("");
                                passwd.setHighlightColor(0xffff0000);
                                email.setText("");
                                email.setHighlightColor(0xffff0000);
                            }
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
