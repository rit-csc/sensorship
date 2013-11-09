package edu.rit.cs.csc.sensorship.deltaforce;

import java.util.*;

/**
 * DeltaForceRequest is the protocol class for passing messages from master to tablet.
 * It provides a mechanism for a master to ask a tablet for updates.
 * Actually, it just represents such a request.
 */
public class DeltaForceRequest {
	public String sensorName;
	public double[][] deltaVectors;
	
	/**
	 * Construct from actual data (desktop does this)
	 */
	public DeltaForceRequest(String sensorName, double[][] deltaVectors) {
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
		
		double[][] deltaVectors = new double[deltaVectorStrings.length][];
		
		for(int i = 0; i < deltaVectors.length; ++i) {
			deltaVectors[i] = fromArrStr(deltaVectorStrings[i]);
		}
		
		this.deltaVectors = deltaVectors;
	}
	
	private double[] fromArrStr(String arrStr) {
		String[] doubleStrArr = arrStr.substring(1, arrStr.length() - 1).split(", ");
		double[] doubleArr = new double[doubleStrArr.length];
		
		for(int i = 0; i < doubleStrArr.length; ++i) {
			doubleArr[i] = Double.parseDouble(doubleStrArr[i]);
		}
		
		return doubleArr;
	}
	
	public String toString() {
		// This allows us to construct the string more efficiently than a bunch of concatenation
		StringBuilder sb = new StringBuilder();
		
		// Add the sensor name
		sb.append(sensorName);
		sb.append(":");
		
		// Add all the delta vectors
		String prefix = "";
		for(double[] deltaVector : deltaVectors) {
			sb.append(prefix);
			prefix = " or ";
			sb.append(Arrays.toString(deltaVector));
		}
		
		return sb.toString();
	}
}
