package com.harvagai.shaul.robot_client_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Server {
    final private int BUFFER_SIZE = 128;
    private PrintWriter buffOut;
    private BufferedReader buffIn;

    public Server(PrintWriter buffOut, BufferedReader buffIn)
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

    }
}
