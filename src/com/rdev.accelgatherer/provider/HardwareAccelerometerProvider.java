package com.rdev.accelgatherer.provider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.rdev.accelgatherer.data.SensorTypeEnum;

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
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final int sensorType = event.sensor.getType();

        if (!(sensorType == Sensor.TYPE_ACCELEROMETER || sensorType == Sensor.TYPE_ORIENTATION))
            return;
        SensorTypeEnum sensorTypeE = SensorTypeEnum.ACCELEROMETER;
        if (sensorType == Sensor.TYPE_ORIENTATION) {
            sensorTypeE = SensorTypeEnum.ORIENTATION;
        }

        com.rdev.accelgatherer.data.SensorEvent evt = new com.rdev.accelgatherer.data.SensorEvent(event.values, event.accuracy, String.valueOf(event.timestamp), sensorTypeE);

        notifySensorChanged(evt);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
