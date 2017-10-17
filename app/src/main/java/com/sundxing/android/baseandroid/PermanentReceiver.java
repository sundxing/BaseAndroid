package com.sundxing.android.baseandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sundxing on 17/10/9.
 */

public class PermanentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("PermanentReceiver : ", "Start!");

    }
}
