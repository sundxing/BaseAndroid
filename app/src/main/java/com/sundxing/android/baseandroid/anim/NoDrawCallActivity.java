package com.sundxing.android.baseandroid.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sundxing.android.baseandroid.R;
import com.sundxing.android.baseandroid.view.FlashCircleView;

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

    }
}
