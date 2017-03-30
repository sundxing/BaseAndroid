package com.sundxing.android.baseandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sundxing on 17/3/30.
 */

public class ScureInnerReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Receiver", "action: " + intent.getAction());
    }
}
