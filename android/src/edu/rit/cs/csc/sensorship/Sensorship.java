package edu.rit.cs.csc.sensorship;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.*;
import android.widget.*;

public class Sensorship extends Activity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;
    private String sensorName;
    private TextView text;
    private float[] currValues;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //parent constructor call
        super.onCreate(savedInstanceState);
        //simple layout setup
        setContentView(R.layout.main);
        //static sensor type for now
        sensorName = "TYPE_ACCELEROMETER";

        //create sensor manager and get the specified sensor
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorName.equals("TYPE_ACCELEROMETER")) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        //retrieve the textview by its id for later use
        text = (TextView)findViewById(R.id.sensor_data_text);
    }
    
    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        //store values retrieved by sensor as current values
        currValues = event.values;
        String outValues = "1) " + currValues[0] + "\n2)" + currValues[1] + "\n3)" + currValues[2];
        //change textview
        text.setText(outValues);
    }
}
