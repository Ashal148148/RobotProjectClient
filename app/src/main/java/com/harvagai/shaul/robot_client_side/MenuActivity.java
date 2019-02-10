package com.harvagai.shaul.robot_client_side;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    ListView lv;
    TextView tvEmail;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String email;
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        sp = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = sp.getString("Email","Def value");
        tvEmail.setText(email);
        //TODO- Design EVERYTHING
        lv = (ListView)findViewById(R.id.lv);
        String[] arr = {"a","b","c","d","e","f","g","h","i","j","k"};
        ArrayAdapter<String> bob = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(bob);
    }

    public void onClickLogOut(View v)
    {
        finish();
    }

    @Override
    protected void onDestroy() {
        sp.edit().putString("Password","").apply();
        super.onDestroy();
    }
}
