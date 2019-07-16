package com.sundxing.android.baseandroid;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Outline;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sundxing.android.baseandroid.anim.NoDrawCallActivity;
import com.sundxing.android.baseandroid.drawable.VectorTestActivity;
import com.sundxing.android.baseandroid.jump.ShowPopWindowActivity;
import com.sundxing.android.baseandroid.permisson.PermissionCheckActivity;
import com.sundxing.android.baseandroid.sensor.Accelerometer;
import com.sundxing.android.baseandroid.service.DJobService;
import com.sundxing.android.baseandroid.view.FontMetricActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * TODO  测试 View.postDelay， View.anim 是否自动取消，
 */
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

        final TextView textView  = (TextView) findViewById(R.id.single_text);
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("很好很傲\n,很傲很傲很傲河,\n岸哦哦哦哦哦哦哦哦的，可靠的撒娇发好房，打机开关卡戴珊公交卡\ndddddddddddd.");
            }
        }, 300);


        try {
            // Empty Receiver has registered successfully.
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_RECEIVERS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        final FloatWindow floatWindow = new FloatWindow(this.getApplicationContext());
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                floatWindow.show();
//                mHandler.postDelayed(this,5000);
//            }
//        },4000);

        findViewById(R.id.textViewIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.textViewIcon).setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(new Rect(0, (int) (0.1f * view.getHeight()),view.getWidth(),view.getHeight()), view.getHeight() / 2);
                }
            });
        }

        new Accelerometer(this).register();

        findViewById(R.id.scale_up_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.scale_up_view).setOnTouchListener(new ScaleUpTouchListener());
//        startActivity(new Intent(this, UpgradeTargetVersionTestActivity.class));

    }

    private void testJobService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DJobService.startJobService(this);
        }

    }

    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

    @Override
    protected void onResume() {
        super.onResume();

        try {
            checkXiaomiPermission();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    AppOpsManager appOpsManager;
    Method opMthod;
    int permAllow;
    int permAsk;
    private void checkXiaomiPermission() throws
            NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Log.d("PermissionCheck", " == start ==");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            if (appOpsManager == null) {
                appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                opMthod = AppOpsManager.class.getMethod("checkOp", int.class, int.class, String.class);
                Field mode = AppOpsManager.class.getField("MODE_ALLOWED");
                Field modeAsk = AppOpsManager.class.getField("MODE_ASK");
                mode.setAccessible(true);
                modeAsk.setAccessible(true);
                permAllow = mode.getInt(appOpsManager);
                permAsk = modeAsk.getInt(appOpsManager);
                Log.d("PermissionCheck", "Check filed allow = " + permAllow + ", ask = " + permAsk);
            }

            checkPerm( "OP_AUTO_START");
            checkPerm( "OP_READ_CONTACTS");
            checkPerm( "OP_SHOW_WHEN_LOCKED");
            checkPerm( "OP_BACKGROUND_START_ACTIVITY");
            checkPerm("OP_SYSTEM_ALERT_WINDOW");

        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void checkPerm(String permName) throws
            NoSuchFieldException, IllegalAccessException, InvocationTargetException  {
        if (opMthod != null) {
            Field filedDefine = AppOpsManager.class.getField(permName);
            if (filedDefine != null) {
                filedDefine.setAccessible(true);
                int valueDefine = filedDefine.getInt(appOpsManager);
                int checkResult = (int) opMthod.invoke(appOpsManager, valueDefine, Process.myUid(), getPackageName());
                Log.d("PermissionCheck", permName
                        + ", Check = " + checkResult
                        +", Given = " + (checkResult == permAllow));
            }
        }
    }

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
        Intent intent = new Intent(this, FontMetricActivity.class);
        intent.putExtra("test", "New version has extra name 111");
        startActivity(intent);
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

    public void showVideoWindow(View view) {
        final WindowManager wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        final TextureView textureView = new TextureView(getApplicationContext());
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Log.d("SURFACE", "available");
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Log.d("SURFACE", "destroy");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        final SurfaceView surfaceView = new SurfaceView(getApplicationContext());
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("SURFACE", "Surface create");

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("SURFACE", "Surface destroy");
            }
        });

//        FrameLayout layout = new FrameLayout(this);
//        layout.addView(textureView);
        final View targetView = textureView;
        wm.addView(targetView, getLayoutParams());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wm.removeViewImmediate(targetView);
            }
        }, 5000);
    }

    public WindowManager.LayoutParams getLayoutParams() {
        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            type = WindowManager.LayoutParams.TYPE_TOAST;

        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this)) {
            type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = type;
        lp.format = PixelFormat.RGBA_8888;
        lp.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        lp.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            lp.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        }

        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;

        return lp;
    }

    public void startDigPicture(View view) {
        startActivity(new Intent(this, DigPictureActivity.class));
    }

    enum  Hi {
        A, B, C
    }


}
