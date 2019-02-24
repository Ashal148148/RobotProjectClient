package com.harvagai.shaul.robot_client_side;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    ListView lv;
    TextView tvEmail;
    SharedPreferences sp;
    ArrayList<String> arrayList;
    ClientListAdapter myAdapter;
    TcpClient myTcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //handle connection
        arrayList = new ArrayList<String>();

        //handle graphics
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

    public void onClickConnect(View v) {
        myTcpClient.sendMessage("Hello!");
    }
    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object and
            myTcpClient = new TcpClient(new TcpClient.OnMessageReceived(){
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            },"127.0.0.1","1267");
            myTcpClient.run();

            return null;
        }

        @Override//not sure if necessary
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        sp.edit().putString("Password","").apply();
        super.onDestroy();
    }
}
