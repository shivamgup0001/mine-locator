package com.example.mine_locator;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class custom extends View {
    private Rect rect;
    private Paint paint;
    private Rect rect1;
    private Paint paint1;
    private Rect rect2;
    private Paint paint2;
    private int i;
    private int j;
    private int mleft[];
    private int mright[];
    private int mtop[];
    private int mbottom[];
    private  static int index=0;
    private int k=0;

    public custom(Context context) {
        super(context);
        init(null);
    }

    public custom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public custom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public custom(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void init(@Nullable AttributeSet set) {
rect=new Rect();
paint= new Paint();
        rect1=new Rect();
        paint1= new Paint();
        rect2=new Rect();
        paint2= new Paint();
paint.setColor(Color.BLACK);
paint1.setColor(Color.WHITE);
        paint2.setColor(Color.GREEN);
        mright=new int[1000];
        mleft=new int[1000];
        mtop=new int[1000];
        mbottom=new int[1000];
        for(i=0;i<64;i++)
        {
            mright[i]=0;
            mleft[i]=0;
            mtop[i]=0;
            mbottom[i]=0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
canvas.drawColor(Color.YELLOW);
            for (i = 1; i < 5; i++) {
                for (j = 0; j < 4; j++) {
                    rect.set(getWidth() / 2 - (80 * i), getHeight() / 2 + (80 * j), getWidth() / 2 - (80 * i) + 80, getHeight() / 2 + (80 * j) + 80);
                    rect1.set(getWidth() / 2 - (80 * i) + 10, getHeight() / 2 + (80 * j) + 10, getWidth() / 2 - (80 * i) + 70, getHeight() / 2 + (80 * j) + 70);
                    canvas.drawRect(rect, paint);
                    canvas.drawRect(rect1, paint1);

                }
            }
            for (i = 1; i < 5; i++) {
                for (j = 1; j < 5; j++) {
                    rect.set(getWidth() / 2 - (80 * i), getHeight() / 2 - (80 * j), getWidth() / 2 - (80 * i) + 80, getHeight() / 2 - (80 * j) + 80);
                    rect1.set(getWidth() / 2 - (80 * i) + 10, getHeight() / 2 - (80 * j) + 10, getWidth() / 2 - (80 * i) + 70, getHeight() / 2 - (80 * j) + 70);
                    canvas.drawRect(rect, paint);
                    canvas.drawRect(rect1, paint1);

                }
            }
            for (i = 0; i < 4; i++) {
                for (j = 1; j < 5; j++) {
                    rect.set(getWidth() / 2 + (80 * i), getHeight() / 2 - (80 * j), getWidth() / 2 + (80 * i) + 80, getHeight() / 2 - (80 * j) + 80);
                    rect1.set(getWidth() / 2 + (80 * i) + 10, getHeight() / 2 - (80 * j) + 10, getWidth() / 2 + (80 * i) + 70, getHeight() / 2 - (80 * j) + 70);
                    canvas.drawRect(rect, paint);
                    canvas.drawRect(rect1, paint1);

                }
            }
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    rect.set(getWidth() / 2 + (80 * i), getHeight() / 2 + (80 * j), getWidth() / 2 + (80 * i) + 80, getHeight() / 2 + (80 * j) + 80);
                    rect1.set(getWidth() / 2 + (80 * i) + 10, getHeight() / 2 + (80 * j) + 10, getWidth() / 2 + (80 * i) + 70, getHeight() / 2 + (80 * j) + 70);
                    canvas.drawRect(rect, paint);
                    canvas.drawRect(rect1, paint1);

                }
            }


        if((mleft[index]!=0)&&(mright[index]!=0)&&(mtop[index]!=0)&&(mbottom[index]!=0)) {

            if (k == 1) {

                for(i=0;i<=index;i++) {
                    rect2.set(mleft[i], mtop[i], mright[i], mbottom[i]);
                    canvas.drawRect(rect2, paint2);
                }
                k = 0;
                index++;
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean value= super.onTouchEvent(event);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {

                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int left=getWidth()/2-310;
                int top=getHeight()/2-310;
                float x = event.getX();
                float y = event.getY();
                for (i = 0; i < 8; i++) {
                    for(j=0;j<8;j++){
                       if((x>(left+(80*i)))&&(x<(left+(80*i)+60))&&(y>(top+(80*j)))&&(y<(top+(80*j)+60))){


                               mleft[index] = left + (80 * i);
                               mright[index] = left + (80 * i) + 60;
                               mtop[index] = top + (80 * j);
                               mbottom[index] = top + (80 * j) + 60;
                               k=1;
                           postInvalidate();

                       }
                    }
                }

                return value;
            }

        }

        return value;
    }
}
