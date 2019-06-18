package com.harvagai.shaul.robot_client_side;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PollinationActivity extends AppCompatActivity {
    TextView tvEmail, tvPollinationInfo;
    SharedPreferences spRememberMe;
    Button btnAddPollination;
    CalendarView cvPollination;
    int authority;
    Date chosenDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pollination);

        //bind elements
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        cvPollination = (CalendarView)findViewById(R.id.cvPollination);
        tvPollinationInfo = (TextView)findViewById(R.id.tvPollinationInfo);

        //handle system
        authority = getIntent().getIntExtra("authority",2);//MISC DEF VALUE
        chosenDay = new Date(); //for some reason the default method gets the year wrong
        chosenDay.setYear(Calendar.getInstance().get(Calendar.YEAR));//this is to fix the year
        cvPollination.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                chosenDay.setDate(dayOfMonth);
                chosenDay.setMonth(month);
                chosenDay.setYear(year);
                tvPollinationInfo.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        });

        //handle graphics
        String email;
        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = spRememberMe.getString("Email","Def value");
        tvPollinationInfo.setText(chosenDay.getDate() + "/" + (chosenDay.getMonth() + 1) + "/" + chosenDay.getYear());
        tvEmail.setText(email);
        if(authority > 2)//MISC VALUE
        {
            btnAddPollination = (Button)findViewById(R.id.btnAddPollination);
            btnAddPollination.setVisibility(View.VISIBLE);
        }
    }

    public void onClickBack(View v)
    {


        finish();
    }



    public void onClickAddPollination(View v)
    {

    }
}
