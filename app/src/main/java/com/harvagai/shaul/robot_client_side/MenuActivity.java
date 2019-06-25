package com.harvagai.shaul.robot_client_side;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    TextView tvEmail;
    SharedPreferences spRememberMe;
    ArrayList<String> arrayList;
    Button btnManageUsers;
    int authority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //handle connection
        arrayList = new ArrayList<String>();
        authority = getIntent().getIntExtra("authority",-1);//-1 for error

        //handle graphics
        String email;
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = spRememberMe.getString("Email","Def value");
        tvEmail.setText(email);
        if (authority > 2)//MISC NUMBER
        {
            btnManageUsers = (Button)findViewById(R.id.btnManageUsers);
            btnManageUsers.setVisibility(View.VISIBLE);
        }
        //TODO- Design EVERYTHING

    }

    public void onClickLogOut(View v)
    {
        finish();
    }

    public void onClickConnect(View v) {
        String[] args = new String[2];
        args[0] = "192.168.5.112";//ip
        args[1] = "1267";//port
        Connection cn = new Connection();
        cn.execute(args);
    }

    public void onClickCamera(View v)
    {
        Intent i = new Intent(this,CameraActivity.class);
        i.putExtra("authority",authority);
        startActivity(i);
    }

    public void onClickManageUsers(View v)
    {
        Intent i = new Intent(this,ManageUsersActivity.class);
        i.putExtra("authority",authority);
        startActivity(i);
    }

    public void onClickPollination(View v)
    {
        Intent i = new Intent(this,PollinationActivity.class);
        i.putExtra("authority",authority);
        startActivity(i);
    }

    public void onClickSchedule(View v)
    {
        Intent i = new Intent(this,ScheduleActivity.class);
        i.putExtra("authority",authority);
        startActivity(i);
    }

    public void onClickSensors(View v)
    {
        Intent i = new Intent(this,SensorsActivity.class);
        i.putExtra("authority",authority);
        startActivity(i);
    }

    public void onClickWatering(View v)
    {
        Intent i = new Intent(this,WateringActivity.class);
        i.putExtra("authority",authority);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        spRememberMe.edit().putString("Password","").apply();
        super.onDestroy();
    }
}
