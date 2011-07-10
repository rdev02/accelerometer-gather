package com.rdev.accelgatherer.data;

/**
 * Created by IntelliJ IDEA.
 * User: revenge
 * Date: 7/10/11
 * Time: 12:14 AM
 */
public class SensorEvent {
    private SensorTypeEnum sensorType;
    private final float[] values;
    private int accuracy;
    private String timestamp;

    public SensorEvent(float[] values, int accuracy, String timestamp, SensorTypeEnum sensorType) {
        this.values = values;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
        this.sensorType = sensorType;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public float[] getValues() {
        return values != null ? values.clone() : null;
    }

    public SensorTypeEnum getSensorType(){
        return sensorType;
    }


}
