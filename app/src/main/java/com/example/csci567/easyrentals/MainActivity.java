package com.example.csci567.easyrentals;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.util.Calendar;

//import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, TextView.OnEditorActionListener {

    private TextView selectDate, selectTime;
    private ActionBarDrawerToggle mToggle;
    private EditText searchBar;
    private CheckBox withDriver, withoutDriver;
    //private Button bookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectDate = (TextView) findViewById(R.id.dateText);
        selectTime = (TextView) findViewById(R.id.timeText);

        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);

        searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener(this);

        withDriver = (CheckBox) findViewById(R.id.withDriver);
        withoutDriver = (CheckBox) findViewById(R.id.withoutDriver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    public void listCar(View view){
        Intent verifyPhone = new Intent(this, phoneNumberVerification.class);
        startActivity(verifyPhone);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dateText:
//                final Calendar calendarStart = Calendar.getInstance();
//                final Calendar calendarEnd = Calendar.getInstance();
                Calendar tomorrow = Calendar.getInstance();
                tomorrow.add(Calendar.DAY_OF_YEAR, 1);

                SmoothDateRangePickerFragment smoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance(
                        new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                            @Override
                            public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                       int yearStart, int monthStart,
                                                       int dayStart, int yearEnd,
                                                       int monthEnd, int dayEnd) {
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
//                                calendarStart.set(yearStart, monthStart, dayStart);
//                                calendarEnd.set(yearEnd, monthEnd, dayEnd);
                                selectDate.setText(monthStart+"/"+ dayStart + "/" + yearStart +" to " + monthEnd + "/" + dayEnd + "/" + yearEnd);

                            }
                        });

                /*smoothDateRangePickerFragment.setMinDate(tomorrow);
                tomorrow.add(Calendar.DAY_OF_YEAR, 1000);
                smoothDateRangePickerFragment.setMaxDate(tomorrow);*/
                smoothDateRangePickerFragment.show(getFragmentManager(), "smoothDateRangePicker");
                break;

            case R.id.timeText:
                /* Time Picker logic expected here */

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        MainActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");
        }

    }

    public void end(MenuItem item) throws InterruptedException {
        Toast.makeText(getApplicationContext(), "You logged out successfully",Toast.LENGTH_SHORT).show();
        Runnable r = new Runnable() {
            @Override
            public void run(){
               finish(); //<-- put your code in here.
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 1000);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0"+hourOfDayEnd : ""+hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0"+minuteEnd : ""+minuteEnd;
        String time = hourString+":"+minuteString+" to "+hourStringEnd+":"+minuteStringEnd;

        selectTime.setText(time);


    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch(textView.getText().toString());
            return true;
        }
        return false;
    }

    private void performSearch(String text) {
        searchBar.clearFocus();
        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        Intent carListIntent = new Intent(this, CarListActivity.class);
        carListIntent.putExtra("Location", text);
        startActivity(carListIntent);

    }


    public void bookCar(View view) {
        String text;

        text = searchBar.getText().toString();

        if (text == null){
            Toast.makeText(this, "Please enter a search location", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent carListIntent = new Intent(this, CarListActivity.class);
            carListIntent.putExtra("Location", text);
            startActivity(carListIntent);
        }
    }
}
