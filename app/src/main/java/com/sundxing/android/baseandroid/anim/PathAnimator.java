package com.sundxing.android.baseandroid.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v4.view.animation.PathInterpolatorCompat;

import java.text.ParseException;

public class PathAnimator {
        Path mPath;
        PathMeasure mPathMeasure;
        float[] points = new float[2];
        float[] tan = new float[2];
        private float mLength;
        private float mProgress;
        private int mDuration;

        private Callback mCallback;
        private int mCurrentRepeat;

        public void init() throws ParseException {
            String test = "M101.003368,63 C70.8861809,89.0625 50.7182121,192.375 135.558056,195.53125 C220.3979,198.6875 209.839306,158.261719 195.011181,137.242188 C180.183056,116.222656 101.003368,144.253906 114.776806,206.953125 C128.550243,269.652344 250.089306,308.984375 256.819775,268.996094";
            SvgPathParser pathParser = new SvgPathParser();
            mPath = pathParser.parsePath(test);
            mPathMeasure = new PathMeasure(mPath, false);
            mLength = mPathMeasure.getLength();
        }

        public int getDuration() {
            return mDuration;
        }

        public void setDuration(int duration) {
            mDuration = duration;
        }

        public void setCallback(Callback callback) {
            mCallback = callback;
        }

        public void startAnim() {
            if (mPath == null) {
                try {
                    init();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            ValueAnimator animator = ValueAnimator
                    .ofFloat(0, 1f)
                    .setDuration(1200);
            animator.setInterpolator(PathInterpolatorCompat.create(.55f,.02f,.45f,1f));
            animator.setRepeatCount(1);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fragmentProgress = (float) animation.getAnimatedValue();
                    float progress = fragmentProgress / 2f + mCurrentRepeat / 2f;
                    onProgress(progress);
                }
            });

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    mCurrentRepeat++;
                }
            });

            animator.start();
        }

        private void onProgress(float progress) {
            mProgress = progress;
            float distance = progress * mLength;
            mPathMeasure.getPosTan(distance, points, tan);
            if (mCallback != null) {
                mCallback.onPoint(points);
            }
        }

        public float getProgress() {
            return mProgress;
        }

        public interface Callback {
            void onPoint(float[] points);
        }
    }
