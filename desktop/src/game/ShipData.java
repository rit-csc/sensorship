package game;
import java.util.*;
import java.io.*;

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
    }

    public ArrayList<Player> getPlayers() {
    	return this.players;
    }

    public void updateHeading(Player player, int heading) {
    	player.setHeading(heading);
    }

    public static void main(String[] args) {
    	ArrayList<String> players = new ArrayList<String>();
    	players.add("Eric");
    	players.add("Katie");
    	ShipData game = new ShipData(players);
    	System.out.println(""+game.getPlayers());
    	game.updateHeading(game.getPlayers().get(0),10);
    	System.out.println(""+game.getPlayers());
    }
}
