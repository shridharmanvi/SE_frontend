package com.example.team4.findout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;





public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class)); //starts the MainActivity
                finish();
            }
        }, secondsDelayed * 1000);
    }
}