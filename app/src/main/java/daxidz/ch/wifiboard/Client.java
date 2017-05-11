package daxidz.ch.wifiboard;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static daxidz.ch.wifiboard.Protocol.END_COMMAND;

/**
 * Created by David on 07.05.2017.
 */

public class Client extends AsyncTask<Void, Void, Boolean> {


    private int port;
    private String pcIP;

    private Socket socket;
    private PrintWriter out;

    private boolean isConnected;

    public Client(String ip, int port) {
        this.pcIP = ip;
        this.port = port;
    }

    @Override
    public Boolean doInBackground(Void... e) {
        try {
            socket = new Socket(pcIP, port);
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    @Override
    public void onPostExecute(Boolean b) {
        isConnected = b;
        if (isConnected) {
            try {
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    public void send(String msg) {
        if (isConnected) {
            out.println(msg + END_COMMAND);
            out.flush();
        }
    }
}
