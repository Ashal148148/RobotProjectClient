package com.harvagai.shaul.robot_client_side;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {
    TextView tvEmail;
    SharedPreferences spRememberMe;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //handle graphics
        String email;
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = spRememberMe.getString("Email","Def value");
        tvEmail.setText(email);
    }


    public void onClickPickPlant(View v)
    {
        //TODO--
    }

    public void onClickPicDate(View v)
    {
        //TODO--
    }

    public void onClickPickPic(View v)
    {
        //TODO--
    }

    public void onClickBack(View v)
    {


        finish();
    }
}
