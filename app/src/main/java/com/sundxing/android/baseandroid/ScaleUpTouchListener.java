package com.sundxing.android.baseandroid;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;

public class ScaleUpTouchListener implements View.OnTouchListener, Animator.AnimatorListener {

    private static final String TAG = "ScaleUpTouchListener";
    private Interpolator mPathInterpolator1 = PathInterpolatorCompat.create(0f, 0f, 0.58f, 1.00f);
    private Interpolator mPathInterpolator2 = PathInterpolatorCompat.create( 0.42f, 0.00f, 1.00f, 1.00f);
    private boolean isScaleUp;
    private boolean isScaleDown;
    private ValueAnimator mValueAnimatorDown = ValueAnimator.ofFloat(1, 0.95f).setDuration(200);

    private View mView;

    public ScaleUpTouchListener() {
        mValueAnimatorDown.setInterpolator(mPathInterpolator1);
        mValueAnimatorDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mView.setScaleX(animatedValue);
                mView.setScaleY(animatedValue);
                Log.d(TAG, "anim update : " + animatedValue);
            }
        });
        mValueAnimatorDown.addListener(this);
    }

    ValueAnimator.AnimatorUpdateListener animListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float animatedValue = (float) animation.getAnimatedValue();
//            mView.setScaleX(animatedValue);
//            mView.setScaleY(animatedValue);
            Log.d(TAG, "anim update : " + animatedValue);
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mView = v;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isScaleDown) {
                    isScaleDown = true;
                    isScaleUp = false;
                    Log.d(TAG, "anim start");
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(200).setUpdateListener(animListener).setListener(this).start();
//                    mValueAnimatorDown.setInterpolator(mPathInterpolator1);
//                    mValueAnimatorDown.start();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isScaleUp) {
                    isScaleUp = true;
                    isScaleDown = false;
                    v.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
//                    mValueAnimatorDown.setInterpolator(mPathInterpolator2);
//                    mValueAnimatorDown.reverse();
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onAnimationStart(Animator animation) {
        Log.d(TAG, "anim onAnimationStart");
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isScaleUp = false;
        isScaleDown = false;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
