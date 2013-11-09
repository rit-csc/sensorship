package edu.rit.cs.csc.sensorship;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.*;
import android.widget.*;
import edu.rit.cs.csc.sensorship.deltaforce.DeltaForceRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class SensorCriteria {
    public float[] lastSent = null;
    public float[][] deltaVectors = null;

    public SensorCriteria(float[] lastSent, float[][] deltaVectors) {
        this.lastSent = lastSent;
        this.deltaVectors = deltaVectors;
    }
}

public class Sensorship extends Activity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;
    private String sensorName;
    private TextView text;
    private float[] lastSent = null;
    private Map<Integer, SensorCriteria> criteriaBySensor = new HashMap<Integer, SensorCriteria>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //parent constructor call
        super.onCreate(savedInstanceState);
        //simple layout setup
        setContentView(R.layout.main);
        //static sensor type for now
        android.util.Log.i("create", "ed");
        sensorName = "TYPE_ACCELEROMETER";

        criteriaBySensor.put(Sensor.TYPE_ACCELEROMETER, new SensorCriteria(null, new float[][] {{5, 5, 5}}));

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
        SensorCriteria criteria = criteriaBySensor.get(event.sensor.getType());
        if( criteria.lastSent == null || shouldUpdate( event.values, criteria )){
            //outvalues = "1) " + lastSent[0] + "\n2)" + lastSent[1] + "\n3)" + lastSent[2];
            //text.setText(outValues);
            // criteria.lastSent = event.values;
            criteria.lastSent = new float[event.values.length];
            for(int i = 0; i < criteria.lastSent.length; ++i) {
                criteria.lastSent[i] = event.values[i];
            }
            //float[][] formattedLastSent = {lastSent};
            //DeltaForceRequest newRequest = new DeltaForceRequest( sensorName, formattedLastSent );
            text.setText( "" + Arrays.toString(event.values) );
        }
    }

    public boolean shouldUpdate( float[] newValues, SensorCriteria criteria ){
       outer:
       for( float[] deltaVector : criteria.deltaVectors ) {
            for( int i = 0; i < newValues.length; i++ ){
                // android.util.Log.d("this", i+" "+);
                android.util.Log.d("this", newValues[i] + ", " + criteria.lastSent[i]);
                float change = Math.abs(newValues[i]-criteria.lastSent[i]);
                android.util.Log.d("this", i+" "+change);
                if(change < deltaVector[i]){
                    android.util.Log.d("this", "failed");
                    continue outer; // try another deltaVector
                }
            }
            android.util.Log.i("this", "worked");
            return true; // this deltaVector matched
        }
        return false;
    }
}
