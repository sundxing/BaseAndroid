package com.sundxing.android.baseandroid.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by sundxing on 17/3/15.
 */

public class PackageResolver {

    public static ComponentName getComponentName(String pkg, String cls) {
        if (TextUtils.isEmpty(cls)) {
            return new ComponentName(pkg, "*");
        }
        if (cls.startsWith(".")) {
            cls = pkg + cls;
        }
        return new ComponentName(pkg, cls);
    }

    public ComponentName getComponentName(String str) {
        int index = str.indexOf('/');
        if (index < 0) {
            return getComponentName(str, "*");
        }
        if (index + 1 >= str.length()) {
            return getComponentName(str.substring(0, index), "*");
        }
        String pkg = str.substring(0, index);
        String cls = str.substring(index + 1);
        if (cls.length() > 0 && cls.charAt(0) == '.') {
            cls = pkg + cls;
        }
        return getComponentName(pkg, cls);
    }


    public static ComponentName getDefaultDialerInfo(Context context) {
        String pkgName = null;
        String clsName = null;
        if (Build.VERSION.SDK_INT >= 23) {
            TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
            if (telecomManager != null) {
                pkgName = telecomManager.getDefaultDialerPackage();
            }
            if (pkgName != null) {
                ComponentName resolveActivity = new Intent("android.intent.action.DIAL").setPackage(pkgName).resolveActivity(context.getPackageManager());
                if (resolveActivity != null) {
                    clsName = resolveActivity.getClassName();
                }
            }
            if (pkgName != null) {
                return getComponentName(pkgName, clsName);
            }
        }
        Intent intent = new Intent("android.intent.action.DIAL");
        ResolveInfo resolveActivity2 = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveActivity2 != null) {
            pkgName = resolveActivity2.activityInfo.packageName;
            clsName = resolveActivity2.activityInfo.name;
        }
        if ("android".equals(pkgName) && Build.BRAND != null) {
            String toLowerCase = Build.BRAND.toLowerCase();
            switch (toLowerCase) {
                case "samsung":
                    pkgName = "com.android.contacts";
                    clsName = "com.android.contacts.activities.DialtactsActivity";
                    break;
                case "htc":
                    pkgName = "com.htc.contacts";
                    clsName = "com.htc.contacts.DialerTabActivity";
                    break;
                case "sony":
                    pkgName = "com.sonyericsson.android.socialphonebook";
                    clsName = "com.sonyericsson.android.socialphonebook.DialerEntryActivity";
                    break;
                case "lge":
                    pkgName = "com.android.contacts";
                    clsName = "com.android.contacts.activities.DialtactsActivity";
                    break;
                case "google":
                    pkgName = "com.google.android.dialer";
                    clsName = "com.google.android.dialer.extensions.GoogleDialtactsActivity";
                    break;
                default:
                    List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                    if (queryIntentActivities.size() > 0 && ((ResolveInfo) queryIntentActivities.get(0)).activityInfo != null) {
                        pkgName = ((ResolveInfo) queryIntentActivities.get(0)).activityInfo.packageName;
                        clsName = ((ResolveInfo) queryIntentActivities.get(0)).activityInfo.name;
                        break;
                    }
            }
        }
        if (pkgName == null || clsName == null) {
            Log.w("TelephonyLoader", "getDefaultDialerInfo fail. pkg = " + pkgName + " cls = " + clsName);
        }
        return getComponentName(pkgName, clsName);
    }
}
