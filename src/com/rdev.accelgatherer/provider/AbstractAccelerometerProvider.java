package com.rdev.accelgatherer.provider;

import com.rdev.accelgatherer.data.SensorDataListener;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: revenge
 * Date: 7/10/11
 * Time: 12:01 AM
 */
public abstract class AbstractAccelerometerProvider {
    protected Collection<SensorDataListener> listeners;

    protected AbstractAccelerometerProvider() {
                listeners = new LinkedList<SensorDataListener>();
    }

    public void addListener(SensorDataListener lstnr){
        listeners.add(lstnr);
    }

    public void removeListener(SensorDataListener lstnr){
        listeners.remove(lstnr);
    }

    protected void notifySensorChanged(com.rdev.accelgatherer.data.SensorEvent evt) {
        for (SensorDataListener lstnr : listeners) {
            lstnr.onSensorChanged(evt);
        }
    }

    public abstract void startTracking();
    public abstract void stopTracking();
}
