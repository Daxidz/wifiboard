package daxidz.ch.wifiboard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static HashMap<Integer, Integer> androidKCtoJavaKC = new HashMap<>();


    static {
        // Numbers
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_0, 48);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_1, 49);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_2, 50);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_3, 51);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_4, 52);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_5, 53);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_6, 54);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_7, 55);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_8, 56);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_9, 57);

        // Letters

        androidKCtoJavaKC.put(KeyEvent.KEYCODE_A, 65);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_B, 66);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_C, 67);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_D, 68);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_E, 69);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_F, 70);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_G, 71);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_H, 72);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_I, 73);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_J, 74);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_K, 75);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_L, 76);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_M, 77);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_N, 78);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_O, 79);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_P, 80);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_Q, 81);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_R, 82);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_S, 83);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_T, 84);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_U, 85);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_V, 86);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_W, 87);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_X, 88);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_Y, 89);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_Z, 90);

        // Other keys
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_ENTER, 13);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_DEL, 8);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_SPACE, 32);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_PERIOD, 190);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_NUMPAD_DOT, 110);
        androidKCtoJavaKC.put(KeyEvent.KEYCODE_COMMA, 188);
    }

    EditText et;
    TextWatcher tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText)  findViewById(R.id.editText);
        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et.removeTextChangedListener(tw);
                char c = et.getText().charAt(0);
                et.setText("");
                et.addTextChangedListener(tw);
            }
        };
        et.addTextChangedListener(tw);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //disconect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //String test = KeyEvent.keyCodeToString(keyCode);
        char  test = (char)event.getUnicodeChar();

        Log.i("Keycode: ", Integer.toString(keyCode));
        Log.i("Char: ", Character.toString((char)event.getUnicodeChar()));

        if (androidKCtoJavaKC.containsKey(keyCode))
            new Client("10.192.91.76", 8989).execute(androidKCtoJavaKC.get(keyCode));

        return true;
    }






}
