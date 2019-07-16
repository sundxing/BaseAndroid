package com.sundxing.android.baseandroid.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by sundxing on 2018/6/29.
 */

public class Accelerometer implements SensorEventListener {

    private final SensorManager sensorManager;
    private Sensor sensor;

    public Accelerometer(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void register() {
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.d("ACCELE", event.values[1] + "");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
