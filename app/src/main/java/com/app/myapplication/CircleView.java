package com.app.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HJ on 2016/4/19 0019.
 * 自定义球形View
 */
public class CircleView extends View {
    private int angle = 0;//角度
    private int r;//半径
    private List<Point> circleList = new ArrayList<Point>();
    private Paint paint;
    private int status = 0;
    private float leftInitX;//左侧线初始化位置
    private float leftInitY;
    private float leftX;
    private float leftY;
    private float rightInitX;//左侧线初始化位置
    private float rightInitY;
    private float rightX;
    private float rightY;
    private int circleRate = 5;//画圆速率
    private int lineRate = 8;//画线速率
    private int alpha;
    private float mWidth, mHeight;
    private int color;
    private CircleListener circleListener;
    public static final boolean RIGHT = true;
    public static final boolean WRONG = false;
    private boolean correctFlag;
    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.circle);
        mWidth = mTypedArray.getDimension(R.styleable.circle_circleWidth, 0);
        mHeight = mTypedArray.getDimension(R.styleable.circle_circleHeight, 0);
        color = mTypedArray.getColor(R.styleable.circle_circleColor, Color.RED);
        init();
    }

    public void setCircleListener (CircleListener circleListener) {
        this.circleListener = circleListener;
    }

    private void init() {
        r = (int) (mWidth * 0.4F);
        paint = new Paint();
        paint.setStrokeWidth(0.06F * mWidth);
        paint.setColor(color);
        paint.setStrokeCap(Paint.Cap.ROUND);
        alpha = 255;
    }

    private void drawView(Canvas canvas) {
        switch (status) {
            case 0:
                for( int i = 0; i < circleRate; i++) {
                    float x = (float) (mWidth/2 + Math.cos(angle*Math.PI/180)*r);
                    float y = (float) (mWidth/2 + Math.sin(angle*Math.PI/180)*r);
                    circleList.add(new Point(x,y));
                    angle +=2;
                }
                break;
            case 1:
                if(correctFlag) {
                    leftX = leftX + lineRate;
                    leftY = leftX * 1.31F;
                    canvas.drawLine(leftInitX, leftInitY, leftInitX+leftX, leftInitY+leftY, paint);
                } else {
                    leftX = leftX + lineRate;
                    leftY = leftX;
                    canvas.drawLine(leftInitX, leftInitY, leftInitX+leftX, leftInitY+leftY, paint);
                }
                break;
            case 2:
                if(correctFlag) {
                    rightX = rightX + lineRate;
                    rightY = rightY - lineRate;
                    canvas.drawLine(leftInitX, leftInitY, leftInitX+leftX, leftInitY+leftY, paint);
                    canvas.drawLine(leftInitX+leftX, leftInitY+leftY, rightX, rightY, paint);
                } else {
                    rightX = rightX + lineRate;
                    rightY = rightY - lineRate;
                    canvas.drawLine(leftInitX, leftInitY, leftInitX+leftX, leftInitY+leftY, paint);
                    canvas.drawLine(rightInitX, rightInitY, rightInitX + rightX, rightInitY + rightY, paint);
                }

                break;
            case 3:
                if(correctFlag) {
                    canvas.drawLine(leftInitX, leftInitY, leftInitX+leftX, leftInitY+leftY, paint);
                    canvas.drawLine(leftInitX+leftX, leftInitY+leftY, rightX, rightY, paint);
                } else {
                    canvas.drawLine(leftInitX, leftInitY, leftInitX+leftX, leftInitY+leftY, paint);
                    canvas.drawLine(rightInitX, rightInitY, rightInitX + rightX, rightInitY + rightY, paint);
                }
                break;
        }
        for(int i = 0; i < circleList.size(); i++) {
            canvas.drawPoint(circleList.get(i).getX(), circleList.get(i).getY(), paint);
        }
    }
    
    private void update() {
        switch (status) {
            case 0:
                if(angle >= 320) {
                    angle = 0;
                    status = 1;
                }
                break;
            case 1:
                if(correctFlag) {
                    if(leftInitX+leftX >= mWidth * 0.42F) {
                        status = 2;
                        rightX = leftInitX+leftX;
                        rightY = leftInitY+leftY;
                    }
                } else {
                    if(leftInitX+leftX >= mWidth * 0.71F) {
                        status = 2;
                    }
                }

                break;
            case 2:
                if(correctFlag) {
                    if(rightX >= mWidth* 0.8F) {
                        status = 3;
                    }
                } else {
                    if(rightInitX+rightX >= mWidth * 0.71F) {
                        status = 3;
                    }
                }
                break;
            case 3:
                if(alpha > 5) {
                    alpha -= 10;
                    paint.setAlpha(alpha);
                } else {
                    circleListener.AnimFinish();
                    status = 4;
                    setVisibility(GONE);
                }
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawView(canvas);
        update();
        if(status == 4) return;
        invalidate();
    }

    public void setRightOrWrong(boolean correctFlag) {
        this.correctFlag = correctFlag;
        if(correctFlag) {
            leftInitX = mWidth/4F;
            leftInitY = mHeight/2F;
        } else {
            leftInitX = mWidth * 0.27F;
            leftInitY = mHeight * 0.27F;
            rightInitX = mWidth * 0.27F;
            rightInitY = mHeight * 0.71F;
        }
        setVisibility(View.VISIBLE);
    }

    class Point{
        private float x;
        private float y;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public interface CircleListener {
        void AnimFinish();
    }
}
