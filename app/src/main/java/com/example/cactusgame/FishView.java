package com.example.cactusgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class FishView extends View {
    private Bitmap player[] = new Bitmap[2];
    int highscore;
    private int charX = 15;
    private int charY;
    private double flyDinoX, flyDinoY;
    private double flyDinoSpeed = 10, groundDinoSpeed = 7;
    private double groundDinoX, groundDinoY;
    private int score;
    private double sunX, sunY;
    private double sunSpeed = 15;
    private int speed;
    private int canvasW;
    private int canvasH;
    private int lives = 3;
    private Bitmap backgroundPic;
    private Bitmap sun;
    private Bitmap[] flyingDino = new Bitmap[2];
    private Bitmap groundDino;
    private Bitmap[] life = new Bitmap[2];
    private Paint scorePaint = new Paint();
    private boolean touch = false;

    public FishView(Context context) {
        super(context);
        player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cactus1);
        player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.cactus2);
        backgroundPic = BitmapFactory.decodeResource(getResources(), R.drawable.desert);
        sun = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
        groundDino = BitmapFactory.decodeResource(getResources(), R.drawable.ground_dino);
        flyingDino[0] = BitmapFactory.decodeResource(getResources(), R.drawable.flying_dino1);
        flyingDino[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fly_dino2);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        charY = 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasW = canvas.getWidth();
        canvasH = canvas.getHeight();
        int minY = player[0].getHeight();
        int maxY = canvasH - 3*player[0].getHeight()/2;
        charY += speed;
        canvas.drawBitmap(backgroundPic, 0, 0, null);
        if (hitPlayerChecker(sunX, sunY)) {
            score += 10;
            sunX = -100;
        }
        if(hitPlayerChecker(flyDinoX, flyDinoY)) {
            flyDinoX = -100;
            lives--;
        }
        if(hitPlayerChecker(groundDinoX, groundDinoY)) {
            groundDinoX = -100;
            lives--;
        }
        if (charY < minY) {
            charY = minY;
        }
        if (charY > maxY) {
            charY = maxY;
        }
        if (touch) {
            canvas.drawBitmap(player[1], charX, charY, null);
            touch = false;
        } else {
            canvas.drawBitmap(player[0], charX, charY, null);
        }
        sunX -= sunSpeed;
        if (sunX < -10) {
            sunX = canvasW;
            sunY = (int) Math.floor(Math.random() * (maxY - minY) + minY);
        }
        flyDinoX -= flyDinoSpeed;
        if (flyDinoX < -10) {
            flyDinoX = canvasW;
            flyDinoY = (int) Math.random() * (maxY - minY * 2) + (minY * 2);
        }
        groundDinoX -= groundDinoSpeed;
        if(groundDinoX < -10) {
            groundDinoY = maxY + 20;
            groundDinoX = canvasW;
        }
        canvas.drawBitmap(groundDino, (int)groundDinoX, (int)groundDinoY, null);
        canvas.drawBitmap(flyingDino[0], (int)flyDinoX, (int)flyDinoY, null);
        canvas.drawBitmap(sun, (int)sunX, (int)sunY, null);
        speed += 2;
        //canvas.drawBitmap(player[0], charX, charY, null);
        canvas.drawText("Score: " + score, 20, 60, scorePaint);
        for(int i = 0; i < 3; i++) {
            int x = 500;
            if(i < lives) {
                canvas.drawBitmap(life[0], x+i*65, 10, null);
            } else {
                canvas.drawBitmap(life[1], x+i*65, 10, null);
            }
        }
        if(lives == 0) {
            Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
            Intent gameOver = new Intent(getContext(), Menu.class);
            gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            lives = -1;
            if(score > highscore) {
                highscore = score;
                saveScore(getContext());
            }
            getContext().startActivity(gameOver);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            speed = -22;
        }
        return true;
    }

    public boolean hitPlayerChecker(double x, double y) {
        if (charX < x && x < (charX + player[0].getWidth()) && charY < y && y < charY + player[0].getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    private void saveScore(Context c) {
        SharedPreferences.Editor editor = c.getSharedPreferences("nom", MODE_PRIVATE).edit();
        editor.putInt("highscore", score);
        editor.commit();

    }

}
