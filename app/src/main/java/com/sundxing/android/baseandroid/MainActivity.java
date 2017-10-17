package com.sundxing.android.baseandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sundxing.android.baseandroid.anim.NoDrawCallActivity;
import com.sundxing.android.baseandroid.drawable.VectorTestActivity;
import com.sundxing.android.baseandroid.jump.ShowPopWindowActivity;
import com.sundxing.android.baseandroid.permisson.PermissionCheckActivity;
import com.sundxing.android.baseandroid.service.DJobService;
import com.sundxing.android.baseandroid.view.FontMetricActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Handler mHandler = new Handler();
    private boolean toogleDraw;

    private Hi mHi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, getApplicationContext().toString());
        Log.d(TAG, getBaseContext().toString());

        Intent intent = new Intent("com.sundxing.android.baseandroid.START_RECEIVER");
        sendBroadcast(intent, getString(R.string.app_name));

        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.thumb_orig);

        testDrawableEffective();

        testJobService();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void testJobService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DJobService.startJobService(this);
        }

        DJobService.startJobServiceTrigger(this);
    }

    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

    @Override
    public void onAttachedToWindow() {
        this.getWindow().addFlags(FLAG_HOMEKEY_DISPATCHED);

        super.onAttachedToWindow();
    }

    private void startAlarmWork() {
        ComponentName componentName = new ComponentName("im.mixbox.magnet", "im.mixbox.magnet.ui.SplashActivity");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 5000, DateUtils.DAY_IN_MILLIS, pendingIntent);
    }

    /**
     * {@link ImageView#setImageDrawable(Drawable)} is much lighter than {@link ImageView#setImageResource(int)}
     */
    private void testDrawableEffective() {
        long start = 0;

        ImageView imageView = (ImageView) findViewById(R.id.image);
        ImageView imageView2 = (ImageView) findViewById(R.id.image2);

        start = System.currentTimeMillis();
        for (int i = 0 ; i < 50;i ++) {
            imageView.setImageDrawable(null);
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
        }
        Log.d("SUNDXING","setImageDrawable consume : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0 ; i < 50;i ++) {
            imageView2.setImageResource(R.mipmap.ic_launcher);
        }
        Log.d("SUNDXING","setImageResource consume : " + (System.currentTimeMillis() - start));

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

    enum  Hi {
        A, B, C
    }
}
