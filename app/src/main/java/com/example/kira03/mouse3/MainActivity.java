package com.example.kira03.mouse3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,View.OnLongClickListener {

    String de="debug";
    Button left,right;
    Socket s;
    OutputStream o;
    DataOutputStream dos;
    int i=0,j=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.textView);
        // this is the view on which you will listen for touch events
        final View touchView = findViewById(R.id.touchView);


        new Thread() {
            @Override
            public void run() {

                try

                {
                    Log.i(de, "attempting to connect");
                    s = new Socket("192.168.1.194", 5000);
                    Log.i(de, "connected");
                    o = s.getOutputStream();
                    dos = new DataOutputStream(o);

                } catch (
                        Exception e
                        )

                {
                    Log.e(de, e.getMessage());
                }
            }
        }.start();
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                    try {
                        textView.setText(String.valueOf((int) event.getX()) + " " + String.valueOf((int) event.getY()));

                        String a;
                        a = (String) textView.getText();
                        dos.writeUTF(a);
                    } catch (IOException e) {
                        Log.e(de, e.getMessage());
                    }


                return true;
            }
        });
        left=(Button)findViewById(R.id.left);
        right=(Button)findViewById(R.id.right);
        left.setOnClickListener( this);
        right.setOnClickListener( this);
        left.setOnLongClickListener( this);
        right.setOnLongClickListener(this);
    }
    public void onClick(View view)
    {
        Button b = (Button)view;
        String s = b.getText().toString();
        if(s.equals("left click"))
        {
            try
            {
                dos.writeUTF("l");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                dos.writeUTF("r");
            }catch (Exception e){}


        }
    }
    public boolean onLongClick(View view)
    {
        Button b = (Button)view;
        String s = b.getText().toString();
        if(s.equals("left click"))
        {
            i=(i+1)%2;
            try
            {

                if(i==1)
                    dos.writeUTF("l1");
                else
                    dos.writeUTF("l2");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(s.equals("right click"))
        {
            j=(j+1)%2;
            try
            {   if(j==1)
                    dos.writeUTF("r1");
                else
                    dos.writeUTF("r2");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return true;
    }
}
