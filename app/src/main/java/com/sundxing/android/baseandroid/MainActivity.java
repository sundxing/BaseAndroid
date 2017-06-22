package com.sundxing.android.baseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sundxing.android.baseandroid.anim.NoDrawCallActivity;
import com.sundxing.android.baseandroid.drawable.VectorTestActivity;
import com.sundxing.android.baseandroid.jump.ShowPopWindowActivity;
import com.sundxing.android.baseandroid.permisson.PermissionCheckActivity;
import com.sundxing.android.baseandroid.view.FontMetricActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Handler mHandler = new Handler();
    private boolean toogleDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, getApplicationContext().toString());
        Log.d(TAG, getBaseContext().toString());

        sendBroadcast(new Intent("com.sundxing.android.baseandroid.START_RECEIVER"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SUNDXING", "--------onStop----------");

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

    public void startNoDrawTest(View view) {
        Intent intent = new Intent(this, NoDrawCallActivity.class);

        if (toogleDraw) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "No animtion!", Toast.LENGTH_SHORT).show();
                }
            }, 1000);
        }
        toogleDraw = !toogleDraw;
        startActivity(intent);
    }
}
