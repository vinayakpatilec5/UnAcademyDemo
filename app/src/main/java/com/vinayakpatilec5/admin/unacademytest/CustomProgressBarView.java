package com.vinayakpatilec5.admin.unacademytest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.logging.Handler;

public class CustomProgressBarView extends View {

    private Paint backgroundPaint;
    private Paint progressIndicatorPaint;
    private Paint progressPaint;

    private Path circlePath;
    private Path animatePath;
    private Context mContext;

    float[] position = new float[2];

    public CustomProgressBarView(Context context) {
        super(context);
        initView(context);
    }

    public CustomProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mContext = context;
        backgroundPaint=new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(context.getResources().getColor(R.color.lightgray));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(getDpForPixel(3f));

        progressIndicatorPaint=new Paint();
        backgroundPaint.setAntiAlias(true);
        progressIndicatorPaint.setColor(context.getResources().getColor(R.color.orange));
        progressIndicatorPaint.setStyle(Paint.Style.FILL);

        progressPaint=new Paint();
        backgroundPaint.setAntiAlias(true);
        progressPaint.setColor(context.getResources().getColor(R.color.green));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(getDpForPixel(3f));

        circlePath = new Path();
        circlePath.moveTo(getDpForPixel(60), getDpForPixel(60));
        circlePath.addCircle(getDpForPixel(60),getDpForPixel(60),getDpForPixel(50), Path.Direction.CW);

        //set initial position of indicator
        position[0] = getDpForPixel(110);
        position[1] = getDpForPixel(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(circlePath,backgroundPaint);
        //drow anim path only if value is set
        if(animatePath != null) {
            canvas.drawPath(animatePath, progressPaint);
        }
        canvas.drawCircle(position[0],position[1],getDpForPixel(4),progressIndicatorPaint);
    }

    private int getDpForPixel(float pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, mContext.getResources().getDisplayMetrics());
    }


    public void animatePath(int value){

        //get all the values on path
        animatePath = new Path();
        final PathMeasure pathMeasure = new PathMeasure(circlePath, false );
        int pathLength = (int)pathMeasure.getLength();
        float precision = (float)value/100f;
        int pointsOnPath = (int) ((float)((float)pathLength * precision)) + 1;



        //animate till value using valuanimator
        ValueAnimator va = ValueAnimator.ofFloat(0f, pointsOnPath);
        int mDuration = 3000; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animationValue = (float)animation.getAnimatedValue();
                pathMeasure.getPosTan(animationValue, position, null /* tangent */);
                if(animationValue == 0){
                    animatePath.moveTo(position[0],position[1]);
                }else {
                    animatePath.lineTo(position[0], position[1]);
                }
                invalidate();
            }
        });
        va.start();
    }
}
