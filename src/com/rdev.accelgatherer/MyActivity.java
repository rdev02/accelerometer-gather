package com.rdev.accelgatherer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.widget.TextView;
import com.rdev.accelgatherer.data.SensorDataListener;
import com.rdev.accelgatherer.data.SensorEvent;
import com.rdev.accelgatherer.provider.AbstractAccelerometerProvider;
import com.rdev.accelgatherer.provider.AccelProviderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        private long lastUpdate = System.currentTimeMillis();
        private List<SensorEvent> dataList;

        public SimulationView(Context context) {
            super(context);
            dataList = new LinkedList<SensorEvent>();
        }

        @Override
        public void onSensorChanged(com.rdev.accelgatherer.data.SensorEvent event) {
            final long now = System.currentTimeMillis();

            float[] values = event.getValues();
            float sensorX = values[0];
            float sensorY = values[1];
            float sensorZ = values[2];

            String text = String.format("Sensor data: %.2f : %.2f : %.2f", sensorX, sensorY, sensorZ);
            this.setText(text);
            dataList.add(event);

            final int dataSize = dataList.size();
            if (now - lastUpdate > 1000 && dataSize > 0)//more then a second{}
            {
                final List<float[]> copy = new ArrayList<float[]>(dataSize);
                //postToServer(copy);
                printToSout(dataList);
                dataList.clear();
                lastUpdate = System.currentTimeMillis();
            }
        }

        private void printToSout(List<SensorEvent> copy) {
            StringBuffer strBUf = new StringBuffer();
            for (SensorEvent data : copy) {
                final float[] values = data.getValues();
                strBUf.append(String.format("%.2f,%.2f,%.2f,%s\n", values[0], values[1], values[2], data.getSensorType().toString()));

            }
            //System.out.println(strBUf.toString());
            File file = new File(Environment.getExternalStorageDirectory(), "file.txt");
            BufferedWriter writer = null;

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
                writer.write(strBUf.toString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } finally {
                if (writer != null)
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
            }


        }

        private void postToServer(List<float[]> copy) {
            //Collections.copy(dataList, copy);
            //new PostToServerTask().execute(dataList);
        }
    }

}
