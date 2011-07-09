package com.rdev.accelgatherer.provider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by IntelliJ IDEA.
 * User: revenge
 * Date: 7/10/11
 * Time: 12:01 AM
 */
class HardwareAccelerometerProvider extends AbstractAccelerometerProvider implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    public void startTracking() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void stopTracking() {
        mSensorManager.unregisterListener(this);
    }

    HardwareAccelerometerProvider(Context cntx) {

        mSensorManager = (SensorManager) cntx.getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;

        com.rdev.accelgatherer.data.SensorEvent evt = new com.rdev.accelgatherer.data.SensorEvent(event.values, event.accuracy, String.valueOf(event.timestamp));

        notifySensorChanged(evt);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
