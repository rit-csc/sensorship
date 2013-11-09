package game;
import java.util.*;

/**
 *  Data model for our ship game.
 */
public class ShipData {
	private final int MIN_X_COOR = 0;
	private final int MIN_Y_COOR = 0;
	private final int MAX_X_COOR = 100;
	private final int MAX_Y_COOR = 100;
	private ArrayList<Player> players;

    /**
     *  Constructor
     */
    public ShipData(ArrayList<String> players) {
    	this.players = new ArrayList<Player>();
        int j = 0;
    	for (int i = 0; i < players.size(); i++) {
    		this.players.add(new Player(players.get(i),i*10,j*5));
    		j+=1;
    	}
    }

    /**
     *  Returns the list of players currently playing.
     */
    public ArrayList<Player> getPlayers() {
    	return this.players;
    }

    /**
     *   Updates the velocity of a specified player
     */
    public void updateVelocity(Player player, int x, int y) {
        player.setVelocity(x,y);
    }

    /**
     *   Takes all player and updates their positions
     *   based on x and y velocities.
     */
    public void updateAllPos() {
        for (Player p : this.players) {
            int x;
            int y;
            x = p.getPos()[0] + p.getVelocity()[0];
            if (x < MIN_X_COOR) {
                x = MAX_X_COOR + x;
            }
            else if (x > MAX_X_COOR) {
                x = x % MAX_X_COOR;
            }
            y = p.getPos()[1] + p.getVelocity()[1];
            if (y < MIN_Y_COOR) {
                y = MAX_Y_COOR + y;
            }
            else if (y > MAX_Y_COOR) {
                y = y % MAX_Y_COOR;
            }
            p.setPos(x,y);
        }
    }
}
