package com.example.mine_locator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.os.Vibrator;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.VIBRATOR_SERVICE;


public class custom extends View {
    private Rect rect;
    private Paint paint;
    private Rect rect1;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;
    private Paint paint6;
    private int i;
    private int j;
    private int k;
    private int [][]ar;
    private int l;
    private int m;
    private int []bomb;
    private Vibrator v;
    public int check = 0;
    private int score = 0;
    private Random rand;
    private int count[][];
    private int width;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public custom(Context context) {
        super(context);
        v = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        init(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public custom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public custom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public custom(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    private void init(@Nullable AttributeSet set) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity2) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        rect = new Rect();
        paint = new Paint();
        rect1 = new Rect();
        paint1 = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();
        paint4 = new Paint();
        paint5 = new Paint();
        paint6 = new Paint();
        rand = new Random();
        paint.setColor(Color.BLACK);
        paint1.setColor(Color.WHITE);
        paint2.setColor(Color.GREEN);
        paint3.setColor(Color.RED);
        paint4.setColor(Color.BLUE);
        paint5.setColor(Color.BLUE);
        paint6.setColor(Color.RED);
        paint3.setTextSize(width/12);
        paint4.setTextSize(width/12);
        paint5.setTextSize((width/8)-40);
        paint6.setTextSize((width/8)-60);
        ar = new int[8][8];
        count = new int[8][8];
        bomb = new int[64];

        for(i=0;i<MainActivity.get();i++)
        {
            int u=0;
            int count=1;
            while(count!=0) {
                count=0;
                u = ThreadLocalRandom.current().nextInt(0, 63);
                if(i>0){
                    for (j = 0; j < i; j++) {
                        if (bomb[j] == u)
                            count++;
                    }
                }
            }
            bomb[i]=u;
        }
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                ar[i][j] = 0;
            }
        }
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                count[i][j] = 0;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        rect.set(40, getHeight() / 2 - (width/2)+40, width-40, getHeight() / 2 + (width/2)-40);
        canvas.drawRect(rect, paint);
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if (ar[i][j] == 1)
                    score++;
            }
        }
        canvas.drawText("Score : " + score, getWidth() / 3 - 60, width/8-40, paint3);

        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                rect1.set(50 + ((width/8)-10)* j, getHeight() / 2 - (width/2)+50+((width/8)-10)* i, 30 + ((width/8)-10)* (j+1), getHeight() / 2 - (width/2)+50+((width/8)-10)* (i+1)-20);
                if (ar[i][j] == 0)
                    canvas.drawRect(rect1, paint1);
                if (ar[i][j] == 1) {
                    canvas.drawRect(rect1, paint2);
                    canvas.drawText("" + count[i][j], rect1.left + 25, rect1.bottom - 15, paint5);
                }
                if (ar[i][j] == 2) {
                    canvas.drawRect(rect1, paint3);
                    check=1;
                    canvas.drawText("Game Over !", getWidth() / 4 - 20, width/10+width/8-40, paint4);
                    SharedPreferences sh = getContext().getSharedPreferences("pong", Context.MODE_PRIVATE);
                    int a = sh.getInt("highscore", 0);
                    if (a < score) {
                        SharedPreferences.Editor myEdit = sh.edit();
                        myEdit.putInt("highscore", score);
                        canvas.drawText("High Score:" + score, canvas.getWidth() / 4 - 40, width/5+width/8-40, paint4);
                        myEdit.apply();
                    } else {
                        canvas.drawText("High Score:" + a, canvas.getWidth() / 4 - 40, width/5+width/8-40, paint4);
                    }
                    v.vibrate(400);
                    canvas.drawText("Press Back ->\n Main Menu" , canvas.getWidth() / 4 - 140, getHeight()-width/7, paint6);
                }
            }
        }

        score = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();
                for (i = 0; i < 8; i++) {
                    for (j = 0; j < 8; j++) {
                        if ((x > (50 + ((width/8)-10)* j)) && (x < (30 + ((width/8)-10)* (j+1))) && (y > (getHeight() / 2 - (width/2)+50+((width/8)-10)* i)) && (y < (getHeight() / 2 - (width/2)+50+((width/8)-10)* (i+1)-20))) {
                            if (ar[i][j] == 0) {
                                ar[i][j] = 1;
                                for (k = 0; k < MainActivity.get(); k++) {

                                    l = bomb[k] / 8;
                                    m = bomb[k] % 8;
                                    if (l == i - 1) {
                                        if (m == j || m == j - 1 || m == j + 1)
                                            count[i][j]++;
                                    }
                                    if (l == i) {
                                        if (m == j - 1 || m == j + 1)
                                            count[i][j]++;
                                    }
                                    if (l == i + 1) {
                                        if (m == j || m == j - 1 || m == j + 1)
                                            count[i][j]++;
                                    }

                                    if (i == l && j == m) {
                                        ar[i][j] = 2;

                                    }
                                }
                            }
                        }
                        if (check == 0)
                            postInvalidate();

                    }
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE: {

            }

            return value;
        }
        return value;
    }
}

