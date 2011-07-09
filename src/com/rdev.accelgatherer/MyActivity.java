package com.rdev.accelgatherer;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.widget.TextView;
import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

public class MyActivity extends Activity {
    private SimulationView mSimulationView;
    //private SensorManager mSensorManager;
    private SensorManagerSimulator mSensorManager;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rdev.accelgatherer.R.layout.main);

        super.onCreate(savedInstanceState);

        // Get an instance of the SensorManager
        //mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); //real manager
        mSensorManager =  SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);

        // Get an instance of the PowerManager
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Create a bright wake lock
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());

        // instantiate our simulation view and set it as the activity's content
        mSimulationView = new SimulationView(this);
        setContentView(mSimulationView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        * when the activity is resumed, we acquire a wake-lock so that the
        * screen stays on, since the user will likely not be fiddling with the
        * screen or buttons.
        */
        mWakeLock.acquire();

        // Start the simulation
        mSimulationView.startSimulation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
        * When the activity is paused, we make sure to stop the simulation,
        * release our sensor resources and wake locks
        */

        // Stop the simulation
        mSimulationView.stopSimulation();

        // and release our wake-lock
        mWakeLock.release();
    }

    class SimulationView extends TextView implements SensorEventListener {

        private Sensor mAccelerometer;

        public void startSimulation() {
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
                mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
                mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
                mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE), SensorManager.SENSOR_DELAY_FASTEST);

                //mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        }

        public void stopSimulation() {
            mSensorManager.unregisterListener(this);
        }

        public SimulationView(Context context) {
            super(context);

            try{mSensorManager.connectSimulator();}
            catch(Exception ex){
                System.out.println("ex = " + ex);
            }
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.type != Sensor.TYPE_ACCELEROMETER)
                return;
            /*
            * record the accelerometer data, the event's timestamp as well as
            * the current time. The latter is needed so we can calculate the
            * "present" time during rendering. In this application, we need to
            * take into account how the screen is rotated with respect to the
            * sensors (which always return data in a coordinate space aligned
            * to with the screen in its native orientation).
            */

            float sensorX = event.values[0];
            float sensorY = event.values[1];
            float sensorZ = event.values[2];

            String text = String.format("Sensor data: %.2f : %.2f : %.2f", sensorX, sensorY, sensorZ);
            this.setText(text);
            System.out.println(text);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

}
