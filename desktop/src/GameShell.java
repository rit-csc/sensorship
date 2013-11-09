import java.io.*;
import java.net.*;
import java.util.*;

import edu.rit.cs.csc.sensorship.deltaforce.DeltaForceRequest;
import api.Listener;
import api.Registrar;

public class GameShell implements Listener {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		
		String inLine;
		screen:
		while(true) {
			System.out.print("> ");
			inLine = scan.nextLine();
			
			String[] tok = inLine.split(" ");
			
			if(tok[0].equals("quit")) {
				break screen;
			} else if(tok[0].equals("connect")) {
				InetAddress device = null;
				try {
					device = InetAddress.getByName(tok[1]);
				} catch(UnknownHostException e) {
					System.err.println("Couldn't find host");
					continue;
				}
				
				// Don't delete this line... it's imPORTant!
				System.out.println("Device assigned player number "+Registrar.addPlayer(device));
			} else if(tok[0].equals("request")) {
				int playerNum = Integer.parseInt(tok[1]);
				System.out.print("\nEnter request string: ");
				DeltaForceRequest req = new DeltaForceRequest(scan.nextLine());
				Registrar.registerListener(playerNum, req.sensorType, req.deltaVectors, new GameShell());
			}
		}
	}

	public void sensorUpdated(int playah, int sensah, float[] resultah) {
		System.out.printf("Player %d: Sensor %d reports %s", playah, sensah, Arrays.toString(resultah));
	}
}
