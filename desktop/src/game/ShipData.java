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
    	this.players = new ArrayList<Player>();
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

    public void updateVelocity(Player player, int velocity) {
        player.setVelocity(velocity);
    }

    public void updateAllPos() {
        for (Player p : this.players) {
            int x;
            int y;
            x = p.getVelocity()*(int)Math.floor(Math.cos(p.getHeading()));
            if (x < MIN_X_COOR) {
                x = MAX_X_COOR - x;
            }
            else if (x > MAX_X_COOR) {
                x = x % MAX_X_COOR;
            }
            y = p.getVelocity()*(int)Math.floor(Math.sin(p.getHeading()));
            if (y < MIN_Y_COOR) {
                y = MAX_Y_COOR - y;
            }
            else if (y > MAX_Y_COOR) {
                y = y % MAX_Y_COOR;
            }
            System.out.println(""+x+","+y);
            p.setPos(x,y);
        }
    }

    public static void main(String[] args) {
    	ArrayList<String> players = new ArrayList<String>();
    	players.add("Eric");
    	players.add("Katie");
    	ShipData game = new ShipData(players);
        game.updateAllPos();
    	System.out.println(""+game.getPlayers());
    	game.updateHeading(game.getPlayers().get(0),90);
        game.updateVelocity(game.getPlayers().get(0),10);
        game.updateAllPos();
    	System.out.println(""+game.getPlayers());
    }
}
