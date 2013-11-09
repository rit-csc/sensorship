package edu.rit.cs.csc.sensorship;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.*;
import android.widget.*;
import edu.rit.cs.csc.sensorship.deltaforce.DeltaForceRequest;

public class Sensorship extends Activity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;
    private String sensorName;
    private TextView text;
    private float[] lastSent = {0, 0, 0, 0, 0, 0, 0};
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
        //called when accurracy of sensor has changed (tells you if you must callibrate)
    }

    public void onSensorChanged(SensorEvent event) {
        if( shouldUpdate( lastSent, event )){
            lastSent = event.values;
            //outvalues = "1) " + lastSent[0] + "\n2)" + lastSent[1] + "\n3)" + lastSent[2];
            //text.setText(outValues);
            float[][] formattedLastSent = {lastSent};
            DeltaForceRequest newRequest = new DeltaForceRequest( sensorName, formattedLastSent );
            text.setText( "" + newRequest );
        }
    }

    public boolean shouldUpdate( float[] prevValues, SensorEvent theEvent ){
        float[] compValues = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        for( int i = 0; i < theEvent.values.length; i++ ){
            float change = Math.abs(prevValues[i]-theEvent.values[i]);
            if(compValues[i]!=Float.NaN && change < compValues[i]){
                return false;
            }
        }
        return true;
    }
}
