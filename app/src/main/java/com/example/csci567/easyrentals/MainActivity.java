package com.example.csci567.easyrentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
import android.widget.TextView;

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView selectDate, selectTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectDate = (TextView) findViewById(R.id.dateText);
        selectTime = (TextView) findViewById(R.id.timeText);

        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);
    }

    public void listCar(View view){
        Intent verifyPhone = new Intent(this, phoneNumberVerification.class);
        startActivity(verifyPhone);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dateText:
                final Calendar calendarStart = Calendar.getInstance();
                final Calendar calendarEnd = Calendar.getInstance();
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
                                calendarStart.set(yearStart, monthStart, dayStart);
                                calendarEnd.set(yearEnd, monthEnd, dayEnd);
                                selectDate.setText(calendarStart.toString() + " to " + calendarEnd.toString());

                            }
                        });

                smoothDateRangePickerFragment.setMinDate(tomorrow);
                tomorrow.add(Calendar.DAY_OF_YEAR, 1000);
                smoothDateRangePickerFragment.setMaxDate(tomorrow);
                smoothDateRangePickerFragment.show(getFragmentManager(), "smoothDateRangePicker");
                break;

            case R.id.timeText:
                /* Time Picker logic expected here */
        }

    }
}
