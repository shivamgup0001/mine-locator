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
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.os.Vibrator;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
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
    private int i;
    private int j;
    private int k;
    private int ar[][];
    private int l;
    private int m;
    private int bomb[];
    private Vibrator v;
    public int check = 0;
    private int score = 0;
    private Random rand;
    private int count[][];
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
        rect = new Rect();
        paint = new Paint();
        rect1 = new Rect();
        paint1 = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();
        paint4 = new Paint();
        paint5 = new Paint();
        rand = new Random();
        paint.setColor(Color.BLACK);
        paint1.setColor(Color.WHITE);
        paint2.setColor(Color.GREEN);
        paint3.setColor(Color.RED);
        paint4.setColor(Color.BLUE);
        paint5.setColor(Color.BLUE);
        paint3.setTextSize(90);
        paint4.setTextSize(90);
        paint5.setTextSize(60);
        ar = new int[8][8];
        count = new int[8][8];
        bomb = new int[64];

        for(i=0;i<MainActivity.get();i++)
        {
            int u= ThreadLocalRandom.current().nextInt(0,63);
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
        rect.set(getWidth() / 2 - 320, getHeight() / 2 - 320, getWidth() / 2 + 320, getHeight() / 2 + 320);
        canvas.drawRect(rect, paint);
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if (ar[i][j] == 1)
                    score++;
            }
        }
        canvas.drawText("Score : " + score, getWidth() / 3 - 60, 120, paint3);

        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                rect1.set(getWidth() / 2 - 310 + 80 * j, getHeight() / 2 - 310 + 80 * i, getWidth() / 2 - 310 + 60 + 80 * j, getHeight() / 2 - 310 + 60 + 80 * i);
                if (ar[i][j] == 0)
                    canvas.drawRect(rect1, paint1);
                if (ar[i][j] == 1) {
                    canvas.drawRect(rect1, paint2);
                    canvas.drawText("" + count[i][j], rect1.left + 15, rect1.bottom - 15, paint5);
                }
                if (ar[i][j] == 2) {
                    canvas.drawRect(rect1, paint3);
                   check=1;
                    canvas.drawText("Game Over !", getWidth() / 4 - 40, 200, paint4);
                    SharedPreferences sh = getContext().getSharedPreferences("pong", Context.MODE_PRIVATE);
                    int a = sh.getInt("highscore", 0);
                    if (a < score) {
                        SharedPreferences.Editor myEdit = sh.edit();
                        myEdit.putInt("highscore", score);
                        canvas.drawText("High Score:" + score, canvas.getWidth() / 4 - 40, 290, paint4);
                        myEdit.apply();
                    } else {
                        canvas.drawText("High Score:" + a, canvas.getWidth() / 4 - 40, 290, paint4);
                    }
                    v.vibrate(400);
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

                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int left = getWidth() / 2 - 310;
                int top = getHeight() / 2 - 310;
                float x = event.getX();
                float y = event.getY();
                for (i = 0; i < 8; i++) {
                    for (j = 0; j < 8; j++) {
                        if ((x > (left + (80 * j))) && (x < (left + (80 * j) + 60)) && (y > (top + (80 * i))) && (y < (top + (80 * i) + 60))) {
                            if (ar[i][j] == 0) {
                                ar[i][j] = 1;
                                for (k = 0; k < MainActivity.get(); k++) {

                                    l = bomb[k] / 8;
                                    m = bomb[k] % 8;
                                    //if (ar[i][j] == 1) {
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
            }

            return value;
        }
        return value;
    }
}

