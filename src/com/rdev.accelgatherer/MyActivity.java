package com.rdev.accelgatherer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.TextView;
import com.rdev.accelgatherer.data.SensorDataListener;
import com.rdev.accelgatherer.provider.AbstractAccelerometerProvider;
import com.rdev.accelgatherer.provider.AccelProviderFactory;

public class MyActivity extends Activity {
    private SimulationView mSimulationView;
    private AbstractAccelerometerProvider accProvider;
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

        accProvider = AccelProviderFactory.getInstance().getSimulatorAccelerator(this);

        // Get an instance of the PowerManager
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Create a bright wake lock
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());

        // instantiate our simulation view and set it as the activity's content
        mSimulationView = new SimulationView(this);

        accProvider.addListener(mSimulationView);

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
        accProvider.startTracking();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
        * When the activity is paused, we make sure to stop the simulation,
        * release our sensor resources and wake locks
        */

        // Stop the simulation
        accProvider.stopTracking();

        // and release our wake-lock
        mWakeLock.release();
    }

    class SimulationView extends TextView implements SensorDataListener {

        public SimulationView(Context context) {
            super(context);
        }

        @Override
        public void onSensorChanged(com.rdev.accelgatherer.data.SensorEvent event) {
            float[] values = event.getValues();
            float sensorX = values[0];
            float sensorY = values[1];
            float sensorZ = values[2];

            String text = String.format("Sensor data: %.2f : %.2f : %.2f", sensorX, sensorY, sensorZ);
            this.setText(text);
        }
    }

}
