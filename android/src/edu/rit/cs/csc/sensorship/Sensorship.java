package edu.rit.cs.csc.sensorship;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;
import android.widget.*;

import edu.rit.cs.csc.sensorship.deltaforce.*;
import java.net.*;
import java.io.*;
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

class RequestThread extends Thread {
    Sensorship instance = null;
    
    public RequestThread(Sensorship instance) {
        // Oh my god.
        this.instance = instance;
    }
    
    public void run() {
        try {
            ServerSocket server = new ServerSocket(DeltaForceConfig.PORT);
            while(true) {
                // Block and wait for the desktop to give us a request
                Socket desktop = server.accept();
                
                if(Sensorship.master == null) {
                    Sensorship.master = desktop.getInetAddress();
                    Socket outgoingSocket = new Socket(desktop.getInetAddress(), DeltaForceConfig.PORT);
                    Sensorship.outgoing = new PrintWriter(outgoingSocket.getOutputStream());
                }
                
                BufferedReader in = new BufferedReader(new InputStreamReader(desktop.getInputStream()));
                String inLine = in.readLine();
                Log.i("request", "Desktop sent sensor request: " + inLine);
                
                DeltaForceRequest req = new DeltaForceRequest(inLine);
                
                synchronized(Sensorship.criteriaBySensor) {
                    SensorCriteria criteria = new SensorCriteria(null, req.deltaVectors);
                    
                    if(!Sensorship.criteriaBySensor.containsKey(req.sensorType)) {
                        SensorManager manager = (SensorManager)instance.getSystemService(Sensorship.SENSOR_SERVICE);
                        Sensor sensor = manager.getDefaultSensor(req.sensorType);
                        manager.registerListener(instance, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    Sensorship.criteriaBySensor.put(req.sensorType, criteria);
                }
                
            }
        } catch(IOException e) {
            
        }
    }
}

public class Sensorship extends Activity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView text;
    private float[] lastSent = null;
    public static Map<Integer, SensorCriteria> criteriaBySensor = new HashMap<Integer, SensorCriteria>();
    
    public static InetAddress master = null;
    // public static Socket outgoingSocket = null;
    public static PrintWriter outgoing = null;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        RequestThread reqThread = new RequestThread(this);
        reqThread.start();
        
        //parent constructor call
        super.onCreate(savedInstanceState);
        //simple layout setup
        setContentView(R.layout.main);

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
        //called when accurracy of sensor has changed (tells you if you must calibrate)
    }

    public void onSensorChanged(SensorEvent event) {
        synchronized(criteriaBySensor) {
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
                
                // outgoing should not be null; it can't be
                outgoing.println(event.sensor.getType() + ":" + Arrays.toString(event.values));
                outgoing.flush();
            }
        }
    }

    public boolean shouldUpdate( float[] newValues, SensorCriteria criteria ){
       outer:
       for( float[] deltaVector : criteria.deltaVectors ) {
            for( int i = 0; i < newValues.length; i++ ){
                float change = Math.abs(newValues[i]-criteria.lastSent[i]);
                if(change < deltaVector[i]){
                    continue outer; // try another deltaVector
                }
            }
            return true; // this deltaVector matched
        }
        return false;
    }
}
