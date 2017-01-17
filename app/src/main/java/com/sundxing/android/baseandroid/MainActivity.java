package com.sundxing.android.baseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sundxing.android.baseandroid.jump.ShowPopWindowActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, getApplicationContext().toString());
        Log.d(TAG, getBaseContext().toString());
    }

    public void startPopupWindow(View view) {
        startActivity(new Intent(this, ShowPopWindowActivity.class));
    }

}
