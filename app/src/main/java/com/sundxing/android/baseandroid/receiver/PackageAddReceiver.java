package com.sundxing.android.baseandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


/**
 * Created by sundxing on 2018/3/5.
 */

public class PackageAddReceiver extends BroadcastReceiver {

    public static final String TAG = "PackageAddReceiver";
    PackageManager manager;
    Handler mHandler = new Handler(Looper.getMainLooper());
    private PendingResult result;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (manager == null) {
            manager = context.getPackageManager();
        }

        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())
                || Intent.ACTION_PACKAGE_INSTALL.equals(intent.getAction())) {
            final String pkgAdd = intent.getData().getSchemeSpecificPart();
            Log.d("PackageAddReceiver", "Pkg add :" + pkgAdd);

            result = goAsync();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!getPackageInfo(pkgAdd)){
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }
    }

    private boolean getPackageInfo(String pkgAdd) {
        try {
            PackageInfo packageInfo = manager.getPackageInfo(pkgAdd, 0);

            Log.d(TAG, "getPackageInfo : " + packageInfo);
            if (result != null) {
                result.finish();
            }
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "getPackageInfo fail! ");
            e.printStackTrace();
        }
        return false;
    }
}
