package com.example.csci567.easyrentals;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, TextView.OnEditorActionListener {

    private DrawerLayout mDrawerLayout;
    private TextView selectDate, selectTime;
    private ActionBarDrawerToggle mToggle;
    private EditText searchBar;
    private CheckBox withDriverCheckBox, withoutDriverCheckBox;
    private Date startDate, endDate;
    private String start, end;
    private Boolean withDriver, withoutDriver;
    private NavigationView navigationView;
    //private Button bookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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

        withDriverCheckBox = (CheckBox) findViewById(R.id.withDriver);
        withoutDriverCheckBox = (CheckBox) findViewById(R.id.withoutDriver);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                selectedItem(item);
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("searchBar",searchBar.getText().toString());
        outState.putString("dates", selectDate.getText().toString());
        outState.putString("time", selectTime.getText().toString());
        outState.putBoolean("withDriver", withDriverCheckBox.isChecked());
        outState.putBoolean("withoutDriver", withoutDriverCheckBox.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        searchBar.setText(savedInstanceState.getString("searchBar"));
        selectDate.setText(savedInstanceState.getString("dates"));
        selectTime.setText(savedInstanceState.getString("time"));
        withDriverCheckBox.setChecked(savedInstanceState.getBoolean("withDriver"));
        withoutDriverCheckBox.setChecked(savedInstanceState.getBoolean("withoutDriver"));
    }

    private void selectedItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_acc:
                accountActivity();
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_log_out:
                try {
                    end(item);
                    mDrawerLayout.closeDrawers();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    private void accountActivity() {
        Intent accountIntent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(accountIntent);
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
                                start = monthStart + "-" + dayStart + "-" + yearStart;
                                end = monthEnd + "-" + dayEnd + "-" + yearEnd;
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

        start = start + " " + hourString + ":" + minuteString;
        end = end + " " + hourString + ":" + minuteString;

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

        assignDates();
        assignCheckBoxes();

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        Intent carListIntent = new Intent(this, CarListActivity.class);
        carListIntent.putExtra("Location", text);
        carListIntent.putExtra("Start Date", startDate);
        carListIntent.putExtra("End Date", endDate);
        carListIntent.putExtra("with driver", withDriver);
        carListIntent.putExtra("without driver", withoutDriver);
        startActivity(carListIntent);

    }

    private void assignDates(){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.US);
        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
            Log.i("Start Date: ", startDate.toString());
            Log.i("End Date: ", endDate.toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void bookCar(View view) {
        String text;

        text = searchBar.getText().toString();

        if (ifNull(text)){
            Toast.makeText(this, "Please enter a search location", Toast.LENGTH_SHORT).show();
        }
        else {
            assignDates();
            assignCheckBoxes();

            Intent carListIntent = new Intent(this, CarListActivity.class);
            carListIntent.putExtra("Location", text);
            carListIntent.putExtra("Start Date", startDate);
            carListIntent.putExtra("End Date", endDate);
            carListIntent.putExtra("with driver", withDriver);
            carListIntent.putExtra("without driver", withoutDriver);

            Log.i("URL value", "http://45.79.76.22/EasyRentals/EasyRentals/car/findByDistance" +
                    "&dist=20&withDriver=" + withDriver + "&withoutDriver=" + withoutDriver + "&startDate=" + startDate + "&endDate=" + endDate);
            startActivity(carListIntent);
        }
    }

    private void assignCheckBoxes() {
        withoutDriver = withoutDriverCheckBox.isChecked();
        withDriver = withDriverCheckBox.isChecked();
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

    public void listCarItem(MenuItem item) {
        Intent verifyPhone = new Intent(this, phoneNumberVerification.class);
        startActivity(verifyPhone);
    }
}
