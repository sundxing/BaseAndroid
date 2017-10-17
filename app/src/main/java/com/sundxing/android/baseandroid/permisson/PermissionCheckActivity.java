package com.sundxing.android.baseandroid.permisson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sundxing.android.baseandroid.R;
import com.sundxing.android.baseandroid.util.OpenOtherApp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by sundxing on 17/1/18.
 */

public class PermissionCheckActivity extends AppCompatActivity {
    private static final String TAG = PermissionCheckActivity.class.getSimpleName();
    private TextView textView;
    private int mType;
    private WindowManager windowManager;
    private LinearLayout mRootView;
    private TextView viewFlags;
    private TextView windowFlags;

    private Set<CharSequence> mSeletedViewFlags = new HashSet<>();
    private Set<CharSequence> mSeletedWindowFlags = new HashSet<>();

    private static Map<CharSequence, Integer> sMapViewFlag = new TreeMap<>();
    private static Map<CharSequence, Integer> sWindowViewFlag = new TreeMap<>();

    @ColorInt public static final int RED         = 0xFFFF0000;
    @ColorInt public static final int GREEN       = 0xFF00FF00;
    @ColorInt public static final int BLUE        = 0xFF0000FF;
    @ColorInt public static final int YELLOW      = 0xFFFFFF00;
    private static int[] sColorList = new int[] {RED, GREEN, BLUE, YELLOW};

    static {
        sMapViewFlag.put("SYSTEM_UI_FLAG_VISIBLE", View.SYSTEM_UI_FLAG_VISIBLE);
        sMapViewFlag.put("SYSTEM_UI_FLAG_LOW_PROFILE", View.SYSTEM_UI_FLAG_LOW_PROFILE);
        sMapViewFlag.put("SYSTEM_UI_FLAG_FULLSCREEN", View.SYSTEM_UI_FLAG_FULLSCREEN);
        sMapViewFlag.put("SYSTEM_UI_FLAG_HIDE_NAVIGATION", View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        sMapViewFlag.put("SYSTEM_UI_FLAG_IMMERSIVE", View.SYSTEM_UI_FLAG_IMMERSIVE);
        sMapViewFlag.put("SYSTEM_UI_FLAG_IMMERSIVE_STICKY", View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        sMapViewFlag.put("SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN", View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        sMapViewFlag.put("SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION", View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        sMapViewFlag.put("SYSTEM_UI_FLAG_LAYOUT_STABLE", View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        sWindowViewFlag.put("FLAG_FULLSCREEN", WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sWindowViewFlag.put("FLAG_LAYOUT_IN_SCREEN", WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        sWindowViewFlag.put("FLAG_LAYOUT_IN_OVERSCAN", WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
        sWindowViewFlag.put("FLAG_LAYOUT_NO_LIMITS", WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        sWindowViewFlag.put("FLAG_LAYOUT_ATTACHED_IN_DECOR", WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR);
        sWindowViewFlag.put("FLAG_NOT_FOCUSABLE", WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        sWindowViewFlag.put("FLAG_NOT_TOUCH_MODAL", WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        sWindowViewFlag.put("FLAG_NOT_TOUCHABLE", WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        sWindowViewFlag.put("FLAG_WATCH_OUTSIDE_TOUCH", WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        sWindowViewFlag.put("FLAG_ALT_FOCUSABLE_IM", WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


    }

    private int seletedColorIndex;
    private Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permisson_check);

        setTitle("OverLay Window");
        textView = (TextView) findViewById(R.id.tv_perm_overlay);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mRootView = new FloatingWindowView(this);
        mRootView.setOrientation(LinearLayout.VERTICAL);

        viewFlags = (TextView)findViewById(R.id.view_flags);
        findViewById(R.id.view_flags_add).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                addViewFlags();
             }
         });
        windowFlags = (TextView)findViewById(R.id.window_flags);
        findViewById(R.id.window_flags_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWindowFlags();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            findViewById(R.id.click_perm_overlay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OpenOtherApp.requestDrawOverLays(PermissionCheckActivity.this);
                }
            });
        }

        updateState();

        Spinner spinner = (Spinner) findViewById(R.id.spanner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setUpWindowType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addWindowFlags() {
        addFlags(sWindowViewFlag, mSeletedWindowFlags, windowFlags);
    }

    private void addViewFlags() {
        addFlags(sMapViewFlag, mSeletedViewFlags, viewFlags);
    }

    private void addFlags(Map<CharSequence, Integer> map, final Set<CharSequence> seleted, final TextView textView) {
        final CharSequence[] items = map.keySet().toArray(new CharSequence[0]);
        final boolean[] selectedMarks = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            if (seleted.contains(items[i])) {
                selectedMarks[i] = true;
            } else {
                selectedMarks[i] = false;
            }
        }
        new AlertDialog.Builder(this)
                .setMultiChoiceItems(items, selectedMarks, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        CharSequence flagName = items[which];
                        Log.d(TAG, (isChecked ? "Selected : " : " UnSeleted:") + flagName);

                        if (isChecked) {
                            seleted.add(flagName);
                        } else {
                            seleted.remove(flagName);
                        }
                        textView.setText(Arrays.toString(seleted.toArray()));

                    }
                }).setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    private void setUpWindowType(int position) {
        switch (position) {
            case 0:
                mType = WindowManager.LayoutParams.TYPE_PHONE;
                break;
            case 1:
                mType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                break;

            case 2:
                mType = WindowManager.LayoutParams.TYPE_TOAST;

                break;
            case 3:
                mType = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                break;
        }
    }

    @SuppressLint("NewApi")
    private boolean canDrawOverlay(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    @Override
    protected void onRestart() {
        updateState();
        super.onRestart();
    }

    private void updateState() {
        textView.setText(canDrawOverlay(this) ? "Yes" : "No");
    }

    public void showWindow(View view) {
        mRootView.removeAllViewsInLayout();
        TextView textView = new TextView(this);
        textView.setText("Click to change color");
        textView.setTextSize(90);
        mRootView.addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button button = new Button(this);
        button.setText("Exit");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mRootView.addView(button);

        seletedColorIndex = 0;
        mRootView.setBackgroundColor(sColorList[seletedColorIndex]);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootView.setBackgroundColor(getNextColor());
            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(mType);
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.flags |= getWindowFlags();
        layoutParams.gravity = Gravity.CENTER;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 10000);
        int uiFlag = getViewFlags();
        mRootView.setSystemUiVisibility(uiFlag);
        mRootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("SUNDXING", "KEYCODE:" + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return false;
            }
        });
        windowManager.addView(mRootView, layoutParams);

    }

    private int getNextColor() {
        int len = sColorList.length;
        return sColorList[++seletedColorIndex % len];
    }

    private int getViewFlags() {
        int flag = 0;
        for (CharSequence flagStr : mSeletedViewFlags) {
            flag |= sMapViewFlag.get(flagStr);
        }
        return flag;    }

    private int getWindowFlags() {
        int flag = 0;
        for (CharSequence flagStr : mSeletedWindowFlags) {
            flag |= sWindowViewFlag.get(flagStr);
        }
        return flag;
    }

    private void dismiss() {
        try {
            mHandler.removeCallbacksAndMessages(null);
            mRootView.setSystemUiVisibility(0);
            windowManager.removeView(mRootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
