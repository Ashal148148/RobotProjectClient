package com.harvagai.shaul.robot_client_side;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    TextView tvEmail, tvNextActionTime;
    SharedPreferences spRememberMe;
    ListView lvSchedule;
    ArrayList<String> arrayList;
    ArrayAdapter adapter;
    Date nextActionTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //bind elements
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        lvSchedule = (ListView)(findViewById(R.id.lvSchedule));
        tvNextActionTime = (TextView)findViewById(R.id.tvNextActionTime);

        //system
        nextActionTime = new Date();
        nextActionTime.setYear(Calendar.getInstance().get(Calendar.YEAR));

        //handle graphics
        String email;
        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = spRememberMe.getString("Email","Def value");
        tvEmail.setText(email);
        tvNextActionTime.setText(nextActionTime.getYear() + "/" + nextActionTime.getMonth() + "/" + nextActionTime.getDate()
                + " " + String.format("%02d", nextActionTime.getHours()) + ":" + String.format("%02d", nextActionTime.getMinutes()));
        arrayList = new ArrayList<String>();
        for(int i = 0; i < 20 ; i++)
        {
            arrayList.add(i + "");
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        lvSchedule.setAdapter(adapter);
    }

    public void onClickBack(View v)
    {


        finish();
    }
}
