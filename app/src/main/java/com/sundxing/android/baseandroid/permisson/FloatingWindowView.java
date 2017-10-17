package com.sundxing.android.baseandroid.permisson;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 *
 */
public class FloatingWindowView extends LinearLayout {
    public FloatingWindowView(Context context) {
        super(context);
    }

    public FloatingWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                Toast.makeText(getContext(), "BACK!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.dispatchKeyEvent(event);
    }

}