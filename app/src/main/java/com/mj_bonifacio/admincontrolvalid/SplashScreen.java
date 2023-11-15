package com.mj_bonifacio.admincontrolvalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent splash = new Intent(".MainActivity");

        Thread tmr = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    startActivity(splash);
                    finish();
                }
            }
        };
        tmr.start();
    }
}