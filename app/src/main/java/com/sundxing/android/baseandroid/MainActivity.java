package com.sundxing.android.baseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sundxing.android.baseandroid.drawable.VectorTestActivity;
import com.sundxing.android.baseandroid.jump.ShowPopWindowActivity;
import com.sundxing.android.baseandroid.permisson.PermissionCheckActivity;
import com.sundxing.android.baseandroid.view.FontMetricActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, getApplicationContext().toString());
        Log.d(TAG, getBaseContext().toString());

        sendBroadcast(new Intent("com.sundxing.android.baseandroid.START_RECEIVER"));
    }

    public void startPopupWindow(View view) {
        startActivity(new Intent(this, ShowPopWindowActivity.class));
    }

    public void startVector(View view) {
        startActivity(new Intent(this, VectorTestActivity.class));
    }

    public void startDrawOverlay(View view) {
        startActivity(new Intent(this, PermissionCheckActivity.class));
    }

    public void startFontMetrics(View view) {
        startActivity(new Intent(this, FontMetricActivity.class));
    }
}
