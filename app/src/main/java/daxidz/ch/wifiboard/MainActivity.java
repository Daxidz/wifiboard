package daxidz.ch.wifiboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {





    private EditText et;
    private TextWatcher tw;
    private Client client;

    float initX, initY, disX, disY;
    boolean mouseMoved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to the Server
        client = new Client("192.168.1.196", Protocol.PORT);
        client.execute();

        // our mousepad
        TextView mousePad = (TextView) findViewById(R.id.mousePad);

        mousePad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // This code was taken from http://codesmith.in/control-pc-from-android-app-using-java/
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //save X and Y positions when user touches the TextView
                        initX = event.getX();
                        initY = event.getY();
                        mouseMoved = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        disX = (event.getX() - initX) * (float) 1.5; //Mouse movement in x direction
                        disY = (event.getY() - initY) * (float) 1.5; //Mouse movement in y direction
                            /*set init to new position so that continuous mouse movement
                            is captured*/
                        initX = event.getX();
                        initY = event.getY();
                        if (disX != 0 || disY != 0) {
                            client.send(Protocol.SEND_MOUSE_MOVE + Protocol.DELIMITER + disX + Protocol.DELIM_COORD + disY);
                        }
                        mouseMoved = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        //consider a tap only if usr did not move mouse after ACTION_DOWN
                        if (!mouseMoved) {
                            client.send(Protocol.SEND_MOUSE_CLICK);
                        }
                }

                return true;
            }
        });

        et = (EditText) findViewById(R.id.editText);

        // special event which cannot be send as single character
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    String keyEventMsg = Protocol.SEND_KEYEVENT + Protocol.DELIMITER;
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DEL:
                            keyEventMsg = keyEventMsg + "backspace";
                            break;
                        case KeyEvent.KEYCODE_VOLUME_DOWN:
                            keyEventMsg = keyEventMsg + "audio_vol_down";
                            break;
                        case KeyEvent.KEYCODE_VOLUME_UP:
                            keyEventMsg = keyEventMsg + "audio_vol_up";
                            break;
                        default:
                            return false;
                    }
                    client.send(keyEventMsg);
                }
                return true;
            }
        });

        // This text watcher gets the current letter typed, erases it and sends it to the server
        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // As soon as a letter is typed, the textwatcher send it to the client which send it
                // to the computer
                et.removeTextChangedListener(tw);
                char c = et.getText().charAt(0);

                // spécial case for the '\n'
                if (c == '\n') {
                    client.send(Protocol.SEND_KEYEVENT + Protocol.DELIMITER + "enter");
                } else {
                    client.send(Protocol.SEND_KEYCODE + Protocol.DELIMITER + c);
                }

                // It then removes the letter
                et.setText("");
                et.addTextChangedListener(tw);
            }
        };
        et.addTextChangedListener(tw);
    }


    @Override
    protected void onPause() {
        super.onPause();
        client.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        client = new Client("192.168.1.196", Protocol.PORT);
        client.execute();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        client.disconnect();
    }


}
