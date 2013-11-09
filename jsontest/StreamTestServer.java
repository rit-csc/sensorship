import java.net.*;
import java.io.*;
import java.util.*;

public class StreamTestServer {
	public static void main(String[] aaaaaaaarghs) throws IOException {
		if(aaaaaaaarghs.length != 1) {
			System.err.println("Argument should be a port number");
			System.exit(1);
		}
		
		int port = Integer.parseInt(aaaaaaaarghs[0]);
		
		ServerSocket serverSocket = new ServerSocket(Integer.parseInt(aaaaaaaarghs[0]));
		
		// Block waiting for client
		Socket clientSocket = serverSocket.accept();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		String inLine;
		while((inLine = in.readLine()) != null) {
			System.out.println(inLine);
		}
	}
}
