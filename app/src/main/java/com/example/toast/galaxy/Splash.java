package com.example.toast.galaxy;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.ProgressBar;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        Thread background = new Thread() {
            public void run() {

                try {
                    ProgressBar loady = (ProgressBar) findViewById(R.id.loady);
                    for(int i = 0; i<=25;i++)
                    {
                        sleep(80);loady.setProgress(i*4);
                    }
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        background.start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}

