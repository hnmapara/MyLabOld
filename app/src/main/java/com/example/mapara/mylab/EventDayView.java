package com.example.mapara.mylab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mapara on 11/4/14.
 */
public class EventDayView extends View {
    Paint paint = new Paint();
    int width;

    public EventDayView(Context context) {
        this(context, null);
    }

    public EventDayView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public EventDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.LTGRAY);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        //paint.setStrokeWidth(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();
//        canvas.drawLine(left, top, right, top, paint);
        paint.setStrokeWidth(getHeight());
        //canvas.drawLine(getPaddingLeft(), getPaddingTop(), getPaddingLeft()+getWidth(), getPaddingTop(), paint);
//        canvas.drawLine(getX(), getY(), getX()+getWidth(), getY(), paint);
        canvas.drawPaint(paint);
        Path path = new Path();
        path.lineTo(getX()+getWidth(), getY());
        path.moveTo(getX(), getY());


        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        canvas.drawPath(path, paint);

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int measuredHeight = measureHeight(heightMeasureSpec);
//        int measureWidth = width = measureWidth(widthMeasureSpec);
//        setMeasuredDimension(measureWidth,
//                measuredHeight);
//        paint.setStrokeWidth(measuredHeight);
//    }
    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result =  getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = (int)   getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}