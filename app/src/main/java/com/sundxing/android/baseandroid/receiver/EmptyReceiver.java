package com.sundxing.android.baseandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sundxing on 2018/1/20.
 */

public class EmptyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "Empty receiver called!", Toast.LENGTH_SHORT).show();
    }
}
