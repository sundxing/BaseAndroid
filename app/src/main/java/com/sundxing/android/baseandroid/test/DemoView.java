package com.sundxing.android.baseandroid.test;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by sundxing on 17/6/19.
 */

public class DemoView extends View {
    public DemoView(Context context) {
        super(context);
    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.d("SUNDXING", "--------onPreDraw----------");
                Log.d("SUNDXING", "--------isShown----------" + isShown());
                return true;
            }
        });

        getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                Log.d("SUNDXING", "--------onGlobalFocusChanged----------");
                Log.d("SUNDXING", "--------isShown----------" + isShown());
            }

        });
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Log.d("SUNDXING", "--------onFocusChanged----------" + gainFocus);
        Log.d("SUNDXING", "--------isShown----------" + isShown());
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowSystemUiVisibilityChanged(int visible) {
        Log.d("SUNDXING", "--------onWindowSystemUiVisibilityChanged----------" + visible);
        Log.d("SUNDXING", "--------isShown----------" + isShown());
        super.onWindowSystemUiVisibilityChanged(visible);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        Log.d("SUNDXING", "--------onVisibilityChanged----------" + visibility);
        Log.d("SUNDXING", "--------isShown----------" + isShown());

        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onWindowVisibilityChanged( int visibility) {
        Log.d("SUNDXING", "--------onWindowVisibilityChanged----------" + visibility);
        Log.d("SUNDXING", "--------isShown----------" + isShown());
        super.onWindowVisibilityChanged(visibility);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.d("SUNDXING", "--------onWindowFocusChanged----------" + hasWindowFocus);
        Log.d("SUNDXING", "--------isShown----------" + isShown());

        super.onWindowFocusChanged(hasWindowFocus);
    }
}
