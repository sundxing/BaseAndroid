package com.sundxing.android.baseandroid.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sundxing on 17/6/19.
 */

public class DemoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("SUNDXING", "--------onCreate----------");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SUNDXING", "--------StartCommand----------");
        return super.onStartCommand(intent, flags, startId);
    }
}
