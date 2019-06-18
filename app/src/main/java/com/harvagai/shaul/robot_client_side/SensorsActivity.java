package com.harvagai.shaul.robot_client_side;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SensorsActivity extends AppCompatActivity {
    private static final int NO_SENSOR = 0;
    TextView tvEmail,tvSensorValue;
    SharedPreferences spRememberMe;
    ArrayList<String> arrayList;
    Button btnPickSensor;
    int sensorPicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        //bind elements
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        btnPickSensor = (Button)findViewById(R.id.btnPickSensor);
        tvSensorValue = (TextView)findViewById(R.id.tvSensorValue);

        //handle system
        sensorPicked = NO_SENSOR;

        //handle graphics
        String email;
        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = spRememberMe.getString("Email","Def value");
        tvEmail.setText(email);
    }

    public void onClickPickSensor(View v)
    {
        createSensorsDialog().show();
    }

    public void onClickRefreshSensor(View v)
    {
        //TODO--
    }

    public void onClickShowRecentValues(View v)
    {
        //TODO--
    }

    public void onClickPickDate(View v)
    {
        //TODO--
    }

    public void onClickBack(View v)
    {


        finish();
    }

    @SuppressLint("ValidFragment")
    public class myAlertDialog extends DialogFragment{

    }


    public Dialog createSensorsDialog() {
        String[] arr = {"Sensor1","Sensor2","Sensor3","Sensor4","Sensor5","Sensor6","Sensor7"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("pick sensor")
                .setItems(arr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sensorPicked = which;
                        btnPickSensor.setText("Sensor" + (which + 1));
                    }
                });
        return builder.create();
    }


}
