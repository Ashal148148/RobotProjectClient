package com.harvagai.shaul.robot_client_side;
import android.util.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class handle
 *
 * @author Shaul Carvalho
 *         Date: 24/02/2019
 */
class TcpClient {
    private String myServerIp;
    private String myServerPort;
    private String myServerMessage;
    private String myMessage;
    private OnMessageReceived myMessageListener = null;
    private boolean myRun = false;
    private PrintWriter myBuffOut;
    private BufferedReader myBuffIn;

    public TcpClient(OnMessageReceived omr, String Ip, String Port) {
        myMessageListener = omr;
    }

    public void sendMessage(String message) {
        if (myBuffOut != null && !myBuffOut.checkError()) {
            myBuffOut.println(message);
            myBuffOut.flush();
        }
    }

    public void stopClient() {
        myRun = false;

        if (myBuffOut != null) {
            myBuffOut.flush();
            myBuffOut.close();
        }

        myMessageListener = null;
        myBuffIn = null;
        myBuffOut = null;
        myServerMessage = null;
    }

    public void run() {
        myRun = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(myServerIp);
            Log.d("TCP", "Connecting to " + myServerIp);
            Socket sock = new Socket(serverAddr, Integer.parseInt(myServerPort));
            try {
                myBuffOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
                myBuffIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                sendMessage("Hello!");
                //the conversation loop
                /*while (myRun) {

                    myServerMessage = myBuffIn.readLine();

                    if (myServerMessage != null && myMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        myMessageListener.ReceivedMessage(myServerMessage);
                    }

                }*/

                Log.d("RESPONSE FROM SERVER", "S: Received Message: '" + myServerMessage + "'");

            } catch (Exception e) {
                Log.e("TCP", "Error:", e);
            } finally {
                //if statement reached here it must mean the socket can no longer reconnect
                sock.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}


