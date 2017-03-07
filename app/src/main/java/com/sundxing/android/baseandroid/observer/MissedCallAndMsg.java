package com.sundxing.android.baseandroid.observer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by sundxing on 17/3/7.
 */

public class MissedCallAndMsg {
    public static final String TAG = MissedCallAndMsg.class.getSimpleName();

    private Context mContext;

    private ContentObserver newMsmObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(boolean selfChange) {
            // Add barrage
            int msgCount = getNewMmsCount() + getNewSmsCount();
            int missedCalls = readMissCall();
            Log.d(TAG, "message count : " + msgCount + "; missed calls : " + missedCalls);
        }
    };

    private void registerMsgObserver() {
        getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, newMsmObserver);
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    private void unRegisterMsgObserver() {
        if (newMsmObserver != null) {
            getContentResolver().unregisterContentObserver(newMsmObserver);
        }
    }

    private int getNewSmsCount() {
        int result = 0;
        Cursor csr = null;
        csr = getContentResolver().query(Uri.parse("content://sms/inbox"), null,
                "type = 1 and read = 0", null, null);

        if (csr != null) {
            result = csr.getCount();
            csr.close();
        }
        return result;
    }

    private int readMissCall() {
        int result = 0;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return result;
        }
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{
                CallLog.Calls.TYPE
        }, " type=? and new=?", new String[]{
                CallLog.Calls.MISSED_TYPE + "", "1"
        }, null);

        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }
        return result;
    }

    private int getNewMmsCount() {
        int result = 0;
        Cursor csr = getContentResolver().query(Uri.parse("content://mms/inbox"),
                null, "read = 0", null, null);
        if (csr != null) {
            result = csr.getCount();
            csr.close();
        }
        return result;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
