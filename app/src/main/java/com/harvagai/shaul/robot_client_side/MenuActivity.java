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
    ListView lv;
    TextView tvEmail;
    SharedPreferences spRememberMe;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //handle connection
        arrayList = new ArrayList<String>();

        //handle graphics
        String email;
        tvEmail = (TextView)(findViewById(R.id.tvEmail));
        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);
        email = spRememberMe.getString("Email","Def value");
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
        String[] args = new String[2];
        args[0] = "192.168.4.111";//ip
        args[1] = "1267";//port
        new ConnectTask().execute((args));
    }

    public class ConnectTask extends AsyncTask<String, String, Server> {
        private String myServerIp;
        private String myServerPort;
        private String myServerMessage;
        private String myMessage;
        private boolean myRun = false;
        private PrintWriter myBuffOut;
        private BufferedReader myBuffIn;
        @Override
        protected Server doInBackground(String... args) {
            try {
                Log.d("TCP", "Connecting to " + args[0]);
                Socket sock = new Socket(args[0], Integer.parseInt(args[1]));
                try {
                    myBuffOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
                    myBuffIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                    if (myBuffOut != null && !myBuffOut.checkError()) {
                        myBuffOut.println("Hello!");
                        myBuffOut.flush();
                    }
                    Log.d("TCP", "1 msg sent: " + "Hello!");
                    char[] buff = new char[128];
                    int i=0;
                    while(i>-1 && i<4) {
                        buff[i] = (char)myBuffIn.read();
                        i++;
                    }
                    myServerMessage =new String(buff);
                    Log.d("TCP", "2 i=" + i);

                    Log.d("TCP", "S: Received Message: '" + myServerMessage + "'");
                } catch (Exception e) {
                    Log.e("TCP", "Error:", e);
                }
                if (myBuffOut != null && !myBuffOut.checkError()) {
                    myBuffOut.println("201");
                    myBuffOut.flush();
                }
                sock.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override//not sure if necessary
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            }
    }

    @Override
    protected void onDestroy() {
        spRememberMe.edit().putString("Password","").apply();
        super.onDestroy();
    }
}
