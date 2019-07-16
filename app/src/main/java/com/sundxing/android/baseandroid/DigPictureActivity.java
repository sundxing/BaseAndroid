package com.sundxing.android.baseandroid;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * @author sundxing
 */
public class DigPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDensityRatio(this);

        setContentView(new DigView(this));
    }

    private static class DigView extends View {

        private float ballRadius = pxFromDp(2);
        private Paint mPaint;
        private Picture picture = new Picture();
        private Bitmap mDotResultBitmap;
        private Canvas bitmapCanvas;

        private Bitmap mSourceBitmap;

        private Paint mBitmapPaint;
        private Paint mAnimPaint;
        private float progress;
        private int maxStokeWidth;
        private ValueAnimator animator;
        private Bitmap mDotCropBitmap;

        public DigView(Context context) {
            super(context);
            init();
        }

        public DigView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public DigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            maxStokeWidth = pxFromDp(100);

            mPaint = new Paint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.RED);

            mBitmapPaint = new Paint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
            mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            mAnimPaint = new Paint(mPaint);
            mAnimPaint.setStyle(Paint.Style.STROKE);

            mSourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_1).copy(Bitmap.Config.ARGB_8888, true);
            mDotResultBitmap = Bitmap.createBitmap(mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            mDotCropBitmap = Bitmap.createBitmap(mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);

            // New picture
            bitmapCanvas = new Canvas(mDotResultBitmap);
            doDraw(bitmapCanvas);

            // Clear and reset
            mSourceBitmap.recycle();
            bitmapCanvas = new Canvas(mDotCropBitmap);

            // Animation
            animator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    progress = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            animator.start();

        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            animator.cancel();
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            long startMills = System.currentTimeMillis();
            bitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            Log.d("DigP", "draw end1 , duration " + (System.currentTimeMillis() - startMills));

            mAnimPaint.setStrokeWidth(progress * maxStokeWidth);
            bitmapCanvas.drawCircle(mDotResultBitmap.getWidth() / 2,
                    mDotResultBitmap.getHeight() / 2,
                    mDotResultBitmap.getHeight() * progress * 0.5f, mAnimPaint);
            Log.d("DigP", "draw end2 , duration " + (System.currentTimeMillis() - startMills));

            bitmapCanvas.drawBitmap(mDotResultBitmap, 0, 0, mBitmapPaint);
            Log.d("DigP", "draw end3 , duration " + (System.currentTimeMillis() - startMills));

            canvas.drawBitmap(mDotCropBitmap, 0, 0, null);
            Log.d("DigP", "draw end , duration " + (System.currentTimeMillis() - startMills));
        }

        private void doDraw(Canvas canvas) {
            long startMills = System.currentTimeMillis();
            Log.d("DigP", "start draw : ");
            float tY = ballRadius;
            float tX = ballRadius;
            int w = canvas.getWidth();
            int h = canvas.getHeight();


            Canvas recodingCanvas = picture.beginRecording(w, h);
            while (tX < w) {
                recodingCanvas.drawCircle(tX, tY, ballRadius, mPaint);
                tX += 4 * ballRadius;
            }
            picture.endRecording();
            canvas.save();
            while (tY < h) {
                canvas.drawPicture(picture);
                tY += 4 * ballRadius;
                canvas.translate(0, 4 * ballRadius);
            }
            canvas.restore();
            Log.d("DigP", "start end , duration " + (System.currentTimeMillis() - startMills));


            canvas.drawBitmap(mSourceBitmap, 0, 0, mBitmapPaint);

            Log.d("DigP", "drawBitmap end , duration " + (System.currentTimeMillis() - startMills));
        }
    }

    private static float sDensityRatio;

    public static int pxFromDp(float dp) {
        return Math.round(dp * sDensityRatio);
    }

    public static int dpFromPx(int px) {
        return Math.round(px / sDensityRatio);
    }

    public static float getDensityRatio(Context context) {
        if (sDensityRatio > 0f) {
            return sDensityRatio;
        }
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        sDensityRatio = (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        return sDensityRatio;
    }
}
