package com.sundxing.android.baseandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by sundxing on 17/11/9.
 */

public class ScaleTextView extends android.support.v7.widget.AppCompatTextView {

    private static final float RATIO_SHRINK = 0.86f;
    private static final float RATIO_ENLARGE = 1.2f;
    public ScaleTextView(Context context) {
        super(context);
        init();
        
    }

    public ScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ScaleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, getScaledTextSize(dm, getTextSize()));
    }

    public static float getScaledTextSize(DisplayMetrics dm, float textSizePx) {
        if (dm.densityDpi <= DisplayMetrics.DENSITY_HIGH) {
            return textSizePx * RATIO_SHRINK;
        } else if (dm.densityDpi >= DisplayMetrics.DENSITY_XXXHIGH) {
            return textSizePx * RATIO_ENLARGE;
        }
        return textSizePx;
    }

}
