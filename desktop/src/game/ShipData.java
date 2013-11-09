package game;
import java.util.*;

public class ShipData {
	private final int MIN_X_COOR = 0;
	private final int MIN_Y_COOR = 0;
	private final int MAX_X_COOR = 100;
	private final int MAX_Y_COOR = 100;
	private ArrayList<Player> players;

    public ShipData(ArrayList<String> players) {
    	int j = 0;
    	for (int i = 0; i < players.size(); i++) {
    		this.players.add(new Player(players.get(i),i*10,j*5));
    		j+=1;
    	}
    	System.out.println(""+this.players);
    }

    public static void main(String args[]) {
    	ArrayList<String> players = new ArrayList<String>();
    	players.add("Eric");
    	players.add("Katie");
    	ShipData ship = new ShipData(players);
    }
}
