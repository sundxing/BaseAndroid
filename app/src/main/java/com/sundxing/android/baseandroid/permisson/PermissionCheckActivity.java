package com.sundxing.android.baseandroid.permisson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sundxing.android.baseandroid.R;
import com.sundxing.android.baseandroid.util.OpenOtherApp;

/**
 * Created by sundxing on 17/1/18.
 */

public class PermissionCheckActivity extends AppCompatActivity {
    private TextView textView;
    private int mType;
    private WindowManager windowManager;
    private TextView rootView;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permisson_check);

        textView = (TextView) findViewById(R.id.tv_perm_overlay);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        rootView = new TextView(this);

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
        rootView.setText("Window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(mType);
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 2000);
        windowManager.addView(rootView, layoutParams);
    }

    private void dismiss() {
        windowManager.removeView(rootView);
    }
}
