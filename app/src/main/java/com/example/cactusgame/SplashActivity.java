package com.example.cactusgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread1 = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch(Exception e){
                    e.printStackTrace();
                } finally {
                    Intent mainIntent = new Intent(SplashActivity.this, Menu.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
