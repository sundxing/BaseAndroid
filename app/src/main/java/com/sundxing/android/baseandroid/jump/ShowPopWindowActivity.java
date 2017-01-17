package com.sundxing.android.baseandroid.jump;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPopWindowActivity extends AppCompatActivity {

    private static final String TAG = ShowPopWindowActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, getApplicationContext().toString());
        Log.d(TAG, getBaseContext().toString());

        // Example of a call to a native method
        final TextView tv = (TextView) new TextView(this);
        tv.setText("PopWindow");
        setContentView(tv);

        try {
            showPop(tv, "");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Toast.makeText(this, "can not show pop in onCreate method!", Toast.LENGTH_SHORT).show();
        }

        try {
            tv.post(new Runnable() {
                @Override
                public void run() {
                    showPop(tv, "Runnable");
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
            Toast.makeText(this, "can not show pop in onCreate -> post runnable!", Toast.LENGTH_SHORT).show();
        }

        showDialog();
    }

    private void showPop(TextView tv, String re) {
        PopupWindow window = new PopupWindow(this);
        TextView tv2 = (TextView) new TextView(this);
        tv2.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900));
        tv2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        tv2.setText("pop window show at onCreate()" + re);
        window.setContentView(tv2);
        window.setFocusable(true);
        window.setBackgroundDrawable(getResources().getDrawable(android.support.v7.appcompat.R.drawable.abc_btn_check_material));
        window.showAsDropDown(tv);
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("showDialog on Create")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();
    }


}
