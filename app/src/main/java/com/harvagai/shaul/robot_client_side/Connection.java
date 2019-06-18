package com.harvagai.shaul.robot_client_side;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    final private int BUFFER_SIZE = 128;
    private PrintWriter buffOut;
    private BufferedReader buffIn;

    public void execute(String[] args)
    {
        new ConnectTask().execute((args));
    }

    public Connection()
    {
        this.buffIn = null;
        this.buffOut = null;
    }

    public Connection(PrintWriter buffOut, BufferedReader buffIn)
    {
        this.buffIn = buffIn;
        this.buffOut = buffOut;
    }

    public int GetMessageCode()
    {
        int code = 0;
        char[] buff = new char[BUFFER_SIZE];
        try {
            buffIn.read(buff,0,4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        code = Integer.parseInt(new String(buff));
        return code;
    }

    public String[] GetMessageParts()
    {
        return null;
    }


    public class ConnectTask extends AsyncTask<String, String, Connection> {
        private String myServerIp;
        private String myServerPort;
        private String myServerMessage;
        private String myMessage;
        private boolean myRun = false;
        private PrintWriter myBuffOut;
        private BufferedReader myBuffIn;
        @Override
        protected Connection doInBackground(String... args) {
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

                    myServerMessage =new String(buff,0,i);
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
            //arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
        }

        public boolean SignIn(String email, String password)
        {


            return false;
        }
    }
}
