package com.sundxing.android.baseandroid.floatwindow;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by sundxing on 2018/6/14.
 */

public class FloatWindow {


    private final WindowManager windowManager;
    private TextView textView;
    private boolean shown;

    public FloatWindow(Context context) {
        mContext = context;
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#55cc6699"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView != null) {
                    windowManager.removeView(textView);
                    shown = false;
                }
            }
        });
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void show() {
        textView.setText(new Random().nextFloat() + "");
        if (shown) {
            windowManager.updateViewLayout(textView, getLockScreenParams());
            textView.postInvalidate();
            return;
        }
        shown = true;
        windowManager.addView(textView, getLockScreenParams());
    }

    Context mContext;

    WindowManager.LayoutParams getLockScreenParams() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (needsSystemErrorFloatWindow()) {
            /**
             *      TYPE_SYSTEM_ERROR    Show on keyguard.
             */
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            layoutParams.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            layoutParams.systemUiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            layoutParams.systemUiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            layoutParams.systemUiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            layoutParams.systemUiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
//            int phoneWidth = displayMetrics.widthPixels;
//            int phoneHeight = displayMetrics.widthPixels;
//            layoutParams.width = phoneWidth < phoneHeight ? phoneWidth : phoneHeight;
//            layoutParams.height = phoneWidth > phoneHeight ? phoneWidth : phoneHeight;
        }
        layoutParams.format = PixelFormat.TRANSPARENT;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        return layoutParams;
    }

    private static boolean needsSystemErrorFloatWindow() {
        return false;
    }


}
