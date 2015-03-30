package com.example.sagar.findout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.sagar.findout.MainActivity;
import com.example.sagar.findout.R;

public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Holds the splash page for 2 seconds
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class)); //starts the MainActivity
                finish();
            }
        }, secondsDelayed * 1000);
    }
}