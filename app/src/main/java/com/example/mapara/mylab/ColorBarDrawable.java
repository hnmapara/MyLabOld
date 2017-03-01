package com.example.mapara.mylab;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by mapara on 10/22/14.
 */
public class ColorBarDrawable extends Drawable {

    private int percent;
    private boolean fillFromTop = false;

    public ColorBarDrawable(int percentFilling, boolean fillFromTop) {
        this.percent = percentFilling;
        this.fillFromTop = fillFromTop;

    }

    @Override
    public void draw(Canvas canvas) {

        // get drawable dimensions
        Rect bounds = getBounds();

        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;

        // draw background gradient
        Paint backgroundPaint = new Paint();
//        int barWidth = width / themeColors.length;
//        int barHeight = height/themeColors.length;
//        int barWidthRemainder = width % themeColors.length;
//        for (int i = 0; i < themeColors.length; i++) {
//            backgroundPaint.setColor(themeColors[i]);
//            canvas.drawRect(0, i * barHeight, width, (i + 1) * barHeight,  backgroundPaint);
//        }

        int barTop = fillFromTop? 0 : (100-percent)*height/100;
        int barBottom = fillFromTop? (percent)*height/100 : height;
        backgroundPaint.setColor(Color.LTGRAY);
        canvas.drawRect(0, barTop, width, barBottom,  backgroundPaint);

        // draw remainder, if exists
//        if (barWidthRemainder > 0) {
//            canvas.drawRect(themeColors.length * barWidth, 0, themeColors.length * barWidth + barWidthRemainder, height, backgroundPaint);
//        }

    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

}

