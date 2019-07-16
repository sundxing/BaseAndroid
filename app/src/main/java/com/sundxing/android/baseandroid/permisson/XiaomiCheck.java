package com.sundxing.android.baseandroid.permisson;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build.VERSION;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *  去权限页面
 *  Intent intent = new Intent();
 *  intent.setAction("miui.intent.action.APP_PERM_EDITOR");
 *  intent.addCategory(Intent.CATEGORY_DEFAULT);
 *  intent.putExtra("extra_pkgname", "应用包名");
 *
 *  自启动权限
 *  Intent intent = new Intent();
 *  intent.setAction("miui.intent.action.OP_AUTO_START");
 *  intent.addCategory(Intent.CATEGORY_DEFAULT)
 */
public class XiaomiCheck {
    private AppOpsManager S;
    private Method T;
    private int U;
    private int V;
    private Context W;

    @TargetApi(19)
    public XiaomiCheck(Context context) {
        if (VERSION.SDK_INT >= 19) {
            this.W = context;
            this.S = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                this.T = AppOpsManager.class.getMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class});
                Field x = AppOpsManager.class.getField("MODE_ALLOWED");
                Field field = AppOpsManager.class.getField("MODE_ASK");
                x.setAccessible(true);
                field.setAccessible(true);
                this.U = x.getInt(AppOpsManager.class);
                this.V = field.getInt(AppOpsManager.class);
            } catch (Exception context2) {
                context2.printStackTrace();
            }
        }
    }

    @TargetApi(19)
    public int a(int i) {
        int i2 = 0;
        if (VERSION.SDK_INT < 19) {
            return 0;
        }
        if (i == 4) {
            return a("OP_AUTO_START");
        }
        if (i == 35) {
            i2 = a("OP_BLUETOOTH_CHANGE");
        } else if (i == 44) {
            i2 = a("OP_GET_ACCOUNTS");
        } else if (i != 100) {
            switch (i) {
                case 1:
                    i2 = a("OP_READ_PHONE_STATE");
                    break;
                case 2:
                    i2 = a("OP_WRITE_EXTERNAL_STORAGE");
                    break;
                default:
                    switch (i) {
                        case 9:
                            i2 = a("OP_PROCESS_OUTGOING_CALLS");
                            break;
                        case 10:
                            i2 = a("OP_CALL_PHONE");
                            break;
                        case 11:
                            i2 = a("OP_READ_CALL_LOG");
                            break;
                        case 12:
                        case 13:
                            i2 = a("OP_WRITE_CALL_LOG");
                            break;
                        case 14:
                            i2 = a("OP_READ_SMS");
                            break;
                        case 15:
                        case 16:
                            i2 = a("OP_WRITE_SMS");
                            break;
                        case 17:
                            i2 = a("OP_READ_NOTIFICATION_SMS");
                            break;
                        case 18:
                            i2 = a("OP_READ_MMS");
                            break;
                        case 19:
                            i2 = a("OP_WRITE_MMS");
                            break;
                        case 20:
                            i2 = a("OP_SEND_MMS");
                            break;
                        case 21:
                            i2 = a("OP_READ_CONTACTS");
                            break;
                        case 22:
                            i2 = a("OP_WRITE_CONTACTS");
                            break;
                        case 23:
                            i2 = a("OP_DELETE_CONTACTS");
                            break;
                        case 24:
                            i2 = a("OP_FINE_LOCATION");
                            break;
                        default:
                            switch (i) {
                                case 26:
                                    i2 = a("OP_INSTALL_SHORTCUT");
                                    break;
                                case 27:
                                    i2 = a("OP_READ_CALENDAR");
                                    break;
                                case 28:
                                    i2 = a("OP_WRITE_CALENDAR");
                                    break;
                                case 29:
                                    i2 = a("OP_CAMERA");
                                    break;
                                case 30:
                                    i2 = a("OP_RECORD_AUDIO");
                                    break;
                                case 31:
                                    i2 = a("OP_WRITE_SETTINGS");
                                    break;
                                case 32:
                                    i2 = a("OP_SHOW_WHEN_LOCKED");
                                    break;
                                default:
                                    break;
                            }
                    }
            }
        } else {
            i2 = a("OP_BACKGROUND_START_ACTIVITY");
        }
        return i2;
    }

    @TargetApi(19)
    private int a(String str) {

        return 0;
    }
}