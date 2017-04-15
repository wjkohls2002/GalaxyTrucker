package com.example.toast.galaxy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        Thread background = new Thread() {
            public void run() {

                try {
                    Typeface getFace = Typeface.createFromAsset(getAssets(),"fonts2/Orbitron-Regular.ttf");
                    TextView textView = (TextView) findViewById(R.id.textView7);
                    textView.setTypeface(getFace);
                    TextView textView2 = (TextView) findViewById(R.id.textView8);
                    textView2.setTypeface(getFace);
                    ProgressBar loady = (ProgressBar) findViewById(R.id.loady);
                    for(int i = 0; i<=25;i++)
                    {
                        sleep(80);loady.setProgress(i*4);
                    }
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {
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

