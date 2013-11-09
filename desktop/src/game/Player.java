package game;

/**
 *   Player class for representing a player in-game
 */
public class Player {
	private int curr_x;
	private int curr_y;
	private String player_name;
	private int x_velocity;
        private int y_velocity;

    /**
     *   Constructor
     */
    public Player(String player_name, int init_x, int init_y) {
    	this.player_name = player_name;
    	this.curr_x = init_x;
    	this.curr_y = init_y;
    	this.x_velocity = 0;
        this.y_velocity = 0;
    }

    /**
     *   sets the velocities of a player
     */
    public void setVelocity(int x, int y) {
        this.x_velocity = x;
        this.y_velocity = y;
    }

    /**
     *   retrieves a velocity for a player
     */
    public int[] getVelocity() {
        int velocity[] = {this.x_velocity,this.y_velocity};
        return velocity;
    }

    /**
     *   sets a new position for a player
     */
    public void setPos(int x, int y) {
        this.curr_x = x;
        this.curr_y = y;
    }

    /**
     *    gets the position of a player
     */
    public int[] getPos() {
        int pos[] = {this.curr_x,this.curr_y};
        return pos;
    }

    /**
     *     used for printing a character object to the screen.
     */
    public String toString() {
    	return "{" + player_name + ":" + curr_x + "," + curr_y + "}";
    }
}
