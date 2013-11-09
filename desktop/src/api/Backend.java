package api;

import edu.rit.cs.csc.sensorship.deltaforce.DeltaForceConfig;
import edu.rit.cs.csc.sensorship.deltaforce.DeltaForceRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Backend {
	static {
		new ServerThread().start();
	}

	private static List<InetAddress> playerNumToAddress = new ArrayList<InetAddress>();
	private static Map<InetAddress, Integer> addressToPlayerNum = new HashMap<InetAddress, Integer>();

	static int addPlayer(InetAddress device) {
		playerNumToAddress.add(device);
		addressToPlayerNum.put(device, playerNumToAddress.size() - 1);
		return playerNumToAddress.size() - 1;
	}

	static int numPlayers() {
		return playerNumToAddress.size();
	}

	static void requestDataFromClient(int playerNum, DeltaForceRequest reqNotStr) {
		try {
			Socket toDevice = new Socket(playerNumToAddress.get(playerNum), DeltaForceConfig.PORT);
			PrintWriter out = new PrintWriter(toDevice.getOutputStream());
			out.println(reqNotStr);
			out.flush();
		}
		catch(IOException exException) {}
	}

	private static class ClientThread extends Thread {
		// dat security
		private Socket client;
		
		public ClientThread(Socket client) {
			this.client = client;
		}
		
		public void run() {
			if(!playerNumToAddress.contains(client.getInetAddress())) {
				System.err.println("WHO THE FUCK HANDED ME AN INVALID CLIENT ADDRESS");
				return;
			}
			
			int playerNum = playerNumToAddress.indexOf(client.getInetAddress());
			try {
				// What the fuck
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); // prepare to read ... IF IT IS EVER PERFORMED!!1
				String inLine;
				while((inLine = in.readLine()) != null) {
					DeltaForceRequest deconstruction = new DeltaForceRequest(inLine);
					Registrar.invokeListener(playerNum, deconstruction.sensorType, deconstruction.deltaVectors);
					System.out.printf("Player %d says: %s\n", playerNum, inLine); // TODO don't *ship* with this
				}
			} catch(IOException e) {
				
			}
		}
	}

	private static class ServerThread extends Thread {
		public void run() {
			try {
				ServerSocket server = new ServerSocket(DeltaForceConfig.PORT);
				while(true) {
					Socket client = server.accept();
					
					ClientThread clientThread = new ClientThread(client);
					clientThread.start();
				}
			} catch(IOException e) {
				
			}
		}
	}
}
