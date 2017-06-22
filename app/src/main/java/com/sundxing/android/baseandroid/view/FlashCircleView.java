package com.sundxing.android.baseandroid.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.reflect.Field;

public class FlashCircleView extends View {

    private Field field;

    public interface ViewListener {
        void onViewed();

        void onAnimationEnd();
    }

    private static final long DURATION_ENTER_ANIMATION = 750;

    private static final float DEFAULT_RING_BREADTH = 3f;

    private static final float ARC_SWEEP_MAX_ANGLE = 60f;
    private static final float ARC_START_ANGLE = -180f;

    private static final int COLOR_WHITE = 0xFFFFFFFF;

    private RectF arcBound = new RectF();

    private float arcStartAngle;
    private float arcSweepAngle;
    private float radius;
    private boolean isOnDraw;

    private Paint arcPaint;

    private ViewListener mViewListener;

    public FlashCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        arcPaint = new Paint();
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(DEFAULT_RING_BREADTH);
        arcPaint.setColor(Color.BLUE);
        arcPaint.setAntiAlias(true);
    }

    public void setViewListener(ViewListener viewListener) {
        mViewListener = viewListener;
    }

    public void startAnimation() {

        ValueAnimator enterAnimator = ValueAnimator.ofFloat(ARC_SWEEP_MAX_ANGLE, ARC_SWEEP_MAX_ANGLE, ARC_SWEEP_MAX_ANGLE, 0);
        enterAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        enterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arcStartAngle = ARC_START_ANGLE + animation.getAnimatedFraction() * 360;
                arcSweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        enterAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != mViewListener) {
                    mViewListener.onAnimationEnd();
                }
            }
        });
        enterAnimator.setDuration(DURATION_ENTER_ANIMATION).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize((int) (2 * radius), widthMeasureSpec);
        int height = getDefaultSize((int) (2 * radius), heightMeasureSpec);
        int maxRadius = Math.min(width, height);
        radius = (maxRadius - DEFAULT_RING_BREADTH) / 2;
        arcBound.set(DEFAULT_RING_BREADTH / 2, DEFAULT_RING_BREADTH / 2, maxRadius - DEFAULT_RING_BREADTH / 2, maxRadius - DEFAULT_RING_BREADTH / 2);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(arcBound, arcStartAngle, arcSweepAngle, false, arcPaint);
        if (!isOnDraw && null != mViewListener) {
            mViewListener.onViewed();
            try {
                Class calzz = getRootView().getParent().getClass();
                field  = calzz.getDeclaredField("mReportNextDraw");
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        if (field != null) {
            try {
                field.set(getRootView().getParent(), true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        isOnDraw = true;
    }
}
