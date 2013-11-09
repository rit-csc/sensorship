package api;

import edu.rit.cs.csc.sensorship.deltaforce.DeltaForceRequest;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Registrar {
	private static Map<Integer, Map<Integer, Listener>> registered = new HashMap<Integer, Map<Integer, Listener>>();

	public static int addPlayer(InetAddress device) {
		return Backend.addPlayer(device);
	}

	public static int numPlayers() {
		return Backend.numPlayers();
	}

	public static boolean registerListener(int player, int sensor, float[][] tolerances, Listener allEars) {
		if(player<0 || player>=numPlayers())
			return false;
		if(sensor<0)
			return false;
		if(tolerances==null || tolerances.length==0)
			return false;
		if(allEars==null)
			return false;

		if(!registered.containsKey(player))
			registered.put(player, new HashMap<Integer, Listener>());
		registered.get(player).put(sensor, allEars);

		Backend.requestDataFromClient(player, new DeltaForceRequest(sensor, tolerances));

		return true;
	}

	// precondition: the client is not insane and doesn't give us data we didn't request
	static void invokeListener(int player, int sensor, float[][] tolerances) {
		registered.get(player).get(sensor).sensorUpdated(player, sensor, tolerances[0]);
	}
}
