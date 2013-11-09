package game;
import java.lang.String;

public class Player {
	private int curr_x;
	private int curr_y;
	private String player_name;
	private int velocity;
	private int heading;

    public Player(String player_name, int init_x, int init_y) {
    	this.player_name = player_name;
    	this.curr_x = init_x;
    	this.curr_y = init_y;
    	this.velocity = 0;
    	this.heading = 0;
    }

    public void setHeading(int heading) {
    	this.heading = heading;
    }

    public String toString() {
    	return "{" + player_name + ":" + curr_x + "," + curr_y + "}";
    }
}
