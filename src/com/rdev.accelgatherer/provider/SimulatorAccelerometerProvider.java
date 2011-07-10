package com.rdev.accelgatherer.provider;

import android.content.Context;
import android.hardware.SensorManager;
import com.rdev.accelgatherer.data.SensorTypeEnum;
import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

/**
 * Created by IntelliJ IDEA.
 * User: revenge
 * Date: 7/10/11
 * Time: 12:02 AM
 */
class SimulatorAccelerometerProvider extends AbstractAccelerometerProvider implements SensorEventListener {


    private SensorManagerSimulator mSensorManager;

    public void startTracking() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE), SensorManager.SENSOR_DELAY_FASTEST);

        //mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

    }

    public void stopTracking() {
        mSensorManager.unregisterListener(this);
    }

    SimulatorAccelerometerProvider(Context context) {
        super();

        mSensorManager = SensorManagerSimulator.getSystemService(context, Context.SENSOR_SERVICE);

        try {
            mSensorManager.connectSimulator();
        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!(event.type == Sensor.TYPE_ACCELEROMETER || event.type == Sensor.TYPE_ORIENTATION))
            return;

        SensorTypeEnum sensorTypeE = SensorTypeEnum.ACCELEROMETER;
        if (event.type == Sensor.TYPE_ORIENTATION) {
            sensorTypeE = SensorTypeEnum.ORIENTATION;
        }
        com.rdev.accelgatherer.data.SensorEvent evt = new com.rdev.accelgatherer.data.SensorEvent(event.values.clone(), event.accuracy, event.time, sensorTypeE);

        notifySensorChanged(evt);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

