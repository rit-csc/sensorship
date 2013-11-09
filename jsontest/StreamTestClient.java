import java.net.*;
import java.io.*;
import java.util.*;

public class StreamTestClient {
	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			System.err.println("Give addr and port");
			System.exit(1);
		}
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		Scanner scan = new Scanner(System.in);
		
		Socket toOther = new Socket(hostname, port);
		
		PrintWriter out = new PrintWriter(toOther.getOutputStream(), true);
		
		String inLine;
		while((inLine = scan.nextLine()) != null) {
			out.println(inLine);
			System.out.println("Sent: " + inLine);
		}
	}
}
