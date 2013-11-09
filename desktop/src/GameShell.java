import java.io.*;
import java.net.*;
import java.util.*;

import edu.rit.cs.csc.sensorship.deltaforce.DeltaForceConfig;

class ClientThread extends Thread {
	// dat security
	private Socket client;
	
	public ClientThread(Socket client) {
		this.client = client;
	}
	
	public void run() {
		if(!GameShell.playerNumToAddress.contains(client.getInetAddress())) {
			System.err.println("WHO THE FUCK HANDED ME AN INVALID CLIENT ADDRESS");
			return;
		}
		
		int playerNum = GameShell.playerNumToAddress.indexOf(client.getInetAddress());
		try {
			// What the fuck
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String inLine;
			while((inLine = in.readLine()) != null) {
				System.out.printf("Player %d says: %s\n", playerNum, inLine);
			}
		} catch(IOException e) {
			
		}
	}
}

class ServerThread extends Thread {
	public ServerThread() {
		
	}
	
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

public class GameShell {
	static List<InetAddress> playerNumToAddress = new ArrayList<InetAddress>();
		
	static Map<InetAddress, Integer> addressToPlayerNum = new HashMap<InetAddress, Integer>();
	
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		
		// Spin up the server thread to hear our tablets
		ServerThread server = new ServerThread();
		server.start();
		
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
				
				playerNumToAddress.add(device);
				addressToPlayerNum.put(device, playerNumToAddress.size() - 1);
			} else if(tok[0].equals("request")) {
				int playerNum = Integer.parseInt(tok[1]);
				System.out.print("\nEnter request string: ");
				String reqStr = scan.nextLine();
				
				Socket toDevice = new Socket(playerNumToAddress.get(playerNum), DeltaForceConfig.PORT);
				
				PrintWriter out = new PrintWriter(toDevice.getOutputStream());
				
				out.println(reqStr);
				out.flush();
			}
		}
	}
}
