package com.example.cactusgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
    private int highscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final Intent gameIntent = new Intent(Menu.this, MainActivity.class);
        Button start = findViewById(R.id.startgame);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread2 = new Thread() {
                    public void run() {
                        startActivity(gameIntent);
                    }
                };
                thread2.start();
            }
        });
        loadScore(this);
    }
    public void loadScore(Activity a) {
        TextView scoreboard = findViewById(R.id.highscore);
        SharedPreferences prefs = a.getSharedPreferences("nom", MODE_PRIVATE);
        try {
            highscore = prefs.getInt("highscore", 0); // will return 0 if no  value is saved
        } catch(NullPointerException e){
            Log.i("Null", "Error");
        }
        scoreboard.setText("High Score: "+highscore);
    }


}
