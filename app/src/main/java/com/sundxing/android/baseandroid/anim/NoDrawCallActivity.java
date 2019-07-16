package com.sundxing.android.baseandroid.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;

import com.sundxing.android.baseandroid.R;
import com.sundxing.android.baseandroid.view.FlashCircleView;

import java.util.ArrayList;
import java.util.List;

/**
 * When activity start, Value animation call invalide() not result draw().
 */

public class NoDrawCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_draw_test);
        final FlashCircleView flashCircleView = (FlashCircleView) findViewById(R.id.circle_view);

        flashCircleView.startAnimation();
        flashCircleView.setViewListener(new FlashCircleView.ViewListener() {
            @Override
            public void onViewed() {
                Object imple =  flashCircleView.getRootView().getParent();
            }

            @Override
            public void onAnimationEnd() {

            }
        });

        RotationView rotationView = new RotationView(findViewById(R.id.rotate_cover_above)
                , findViewById(R.id.rotate_cover_back));
        rotationView.startRotate();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    private static class RotationView {

        private View above;
        private View back;
        private float thick;
        private Interpolator interpolator;

        public RotationView(View above, View back) {
            this.above = above;
            this.back = back;
        }

        public void startRotate() {
            init3DRotationView();
        }

        private void init3DRotationView() {
            back.postDelayed(new Runnable() {
                @Override
                public void run() {
                    do3DAnimation();
                }
            }, 1000);
        }

        private void do3DAnimation() {
            interpolator = PathInterpolatorCompat.create(.5f,.01f,.5f,.98f);
            thick = 0.05f * above.getHeight();

            List<Animator> valueAnimators = new ArrayList<>(6);

            valueAnimators.add(buildRotateYAnimator(0f, 30f));
            valueAnimators.add(buildRotateYAnimator(30f, -30f));
            valueAnimators.add(buildRotateYAnimator(-30f, 0f));
            valueAnimators.add(buildRotateXAnimator(0f, 20f));
            valueAnimators.add(buildRotateXAnimator(20f, -20f));
            valueAnimators.add(buildRotateXAnimator(-20f, 0f));
//            ObjectAnimator anim = ObjectAnimator.ofObject(this, "", new PathEvaluator(), mPath.getPoints().toArray());
            PathMeasure pathMeasure = new PathMeasure();
            above.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            back.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    above.setLayerType(View.LAYER_TYPE_NONE, null);
                    back.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            });
            animatorSet.playSequentially(valueAnimators);
            animatorSet.start();
        }

        private ValueAnimator buildRotateXAnimator(float start, float end) {
            ValueAnimator rotateX = ValueAnimator.ofFloat(start, end)
                    .setDuration(500);
            rotateX.setInterpolator(interpolator);
            rotateX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle = (float) animation.getAnimatedValue();
                    above.setRotationX(angle);
                    back.setRotationX(angle);
                    back.setTranslationY(getTrans(thick, angle));
                }
            });
            return rotateX;
        }

        private ValueAnimator buildRotateYAnimator(float start, float end) {
            ValueAnimator rotateY = ValueAnimator.ofFloat(start, end)
                    .setDuration(500);
            rotateY.setInterpolator(interpolator);
            rotateY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle = (float) animation.getAnimatedValue();
                    above.setRotationY(angle);
                    back.setRotationY(angle);
                    back.setTranslationX(-getTrans(thick, angle));
                }
            });
            return rotateY;
        }

        private float getTrans(float thick, float angle) {
            return (float) (thick * Math.sin(angle * Math.PI / 180f));
        }
    }

}
