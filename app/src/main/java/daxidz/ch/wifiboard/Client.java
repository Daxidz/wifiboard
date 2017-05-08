package daxidz.ch.wifiboard;

import android.content.Context;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.datatype.Duration;

/**
 * Created by David on 07.05.2017.
 */

public class Client extends AsyncTask<Integer, Void, Boolean> {


    private final int PORT = 8989;

    private final int PORT_MULTICAST = 57850;

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
    public Boolean doInBackground(Integer... e) {
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

    public void send(char keycode) {
        out.write(keycode);
        out.flush();
    }
}
