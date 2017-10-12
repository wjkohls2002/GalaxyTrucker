package com.example.toast.galaxy;


import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        Thread background = new Thread() {
            public void run() {
                Intent i = new Intent(Splash.this, com.example.toast.galaxy.MainActivity.class);
                try{sleep(1500);}catch (InterruptedException ignore){}
                Splash.this.finish();
                Splash.this.startActivity(i);


            }
        };

        background.start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}

