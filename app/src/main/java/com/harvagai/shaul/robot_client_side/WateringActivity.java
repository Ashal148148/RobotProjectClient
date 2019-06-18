package com.harvagai.shaul.robot_client_side;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class WateringActivity extends AppCompatActivity {
    TextView tvEmail,tvCurrentWateringValue;
    SharedPreferences spRememberMe;
    ArrayList<String> arrayList;
    Button btnPickPlant, btnSetWateringValue;
    int plant,authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watering);

        //bind elements
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        tvCurrentWateringValue = (TextView)(findViewById(R.id.tvCurrentWateringValue));
        btnPickPlant = (Button)(findViewById(R.id.btnPickPlantWatering));
        btnSetWateringValue = (Button)(findViewById(R.id.btnSetWateringValue));

        //handle system
        authority = getIntent().getIntExtra("authority",-1);//-1 for error

        //handle graphics
        String email;
        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = spRememberMe.getString("Email","Def value");
        tvEmail.setText(email);
        if (authority > 2)//MISC VALUES
        {
            btnSetWateringValue.setVisibility(View.VISIBLE);
        }
    }

    public void onClickBack(View v)
    {


        finish();
    }

    public void onClickPickPlant(View v)
    {
        createWateringDialog().show();
    }

    public void onClickSetWateringValue(View v)
    {

    }

    @SuppressLint("ValidFragment")
    public class myAlertDialog extends DialogFragment {

    }


    public Dialog createWateringDialog() {
        String[] arr = {"Plant 1","Plant 2","Plant 3","Plant 4","Plant 5","Plant 6"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("pick plant")
                .setItems(arr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        plant = which;
                        btnPickPlant.setText("Plant " + (which + 1));
                    }
                });
        return builder.create();
    }
}
