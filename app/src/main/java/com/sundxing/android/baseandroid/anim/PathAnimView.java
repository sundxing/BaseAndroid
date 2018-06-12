package com.sundxing.android.baseandroid.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class PathAnimView extends View {
    public Paint paint;
    int countColor;
    float[] points = new float[2];

    public PathAnimView(Context context, AttributeSet attributeSet) throws Exception {
        super(context, attributeSet);
        paint = new Paint();
        paint.setStrokeWidth(3);
        countColor = Color.RED;
        paint.setColor(countColor);
        paint.setStyle(Paint.Style.STROKE);

        PathAnimator pathAnimator = new PathAnimator();
        pathAnimator.setCallback(new PathAnimator.Callback() {
            @Override
            public void onPoint(float[] p) {
                points[0] = p[0];
                points[1] = p[1];
                invalidate();
            }
        });
        pathAnimator.startAnim();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(points[0], points[1], 20, paint);
    }

}