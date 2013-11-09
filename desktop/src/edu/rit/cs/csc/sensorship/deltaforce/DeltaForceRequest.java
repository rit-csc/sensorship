package edu.rit.cs.csc.sensorship.deltaforce;

import java.util.*;

/**
 * DeltaForceRequest is the protocol class for passing messages from master to tablet.
 * It provides a mechanism for a master to ask a tablet for updates.
 * Actually, it just represents such a request.
 */
public class DeltaForceRequest {
	public String sensorName;
	public float[][] deltaVectors;
	
	/**
	 * Construct from actual data (desktop does this)
	 */
	public DeltaForceRequest(String sensorName, float[][] deltaVectors) {
		this.sensorName = sensorName;
		this.deltaVectors = deltaVectors;
	}
	
	/**
	 * Construct from a query string over the net
	 */
	public DeltaForceRequest(String req) {
		// Parts are: sensor name and delta array array
		String[] parts = req.split(":");
		
		this.sensorName = parts[0];
		
		String[] deltaVectorStrings = parts[1].split(" or ");
		
		float[][] deltaVectors = new float[deltaVectorStrings.length][];
		
		for(int i = 0; i < deltaVectors.length; ++i) {
			deltaVectors[i] = fromArrStr(deltaVectorStrings[i]);
		}
		
		this.deltaVectors = deltaVectors;
	}
	
	private float[] fromArrStr(String arrStr) {
		String[] floatStrArr = arrStr.substring(1, arrStr.length() - 1).split(", ");
		float[] floatArr = new float[floatStrArr.length];
		
		for(int i = 0; i < floatStrArr.length; ++i) {
			floatArr[i] = Float.parseFloat(floatStrArr[i]);
		}
		
		return floatArr;
	}
	
	public String toString() {
		// This allows us to construct the string more efficiently than a bunch of concatenation
		StringBuilder sb = new StringBuilder();
		
		// Add the sensor name
		sb.append(sensorName);
		sb.append(":");
		
		// Add all the delta vectors
		String prefix = "";
		for(float[] deltaVector : deltaVectors) {
			sb.append(prefix);
			prefix = " or ";
			sb.append(Arrays.toString(deltaVector));
		}
		
		return sb.toString();
	}
}
