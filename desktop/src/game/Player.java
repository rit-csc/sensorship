package game;

/**
 *   Player class for representing a player in-game
 */
public class Player {
	private static final int VELOCITY_REDUCTION_FACTOR = 2;

	private int id;
	private int curr_x;
	private int curr_y;
	private double direction;
	private int velocity;

    /**
     *   Constructor
     */
    public Player(int id, int init_x, int init_y) {
    	this.id = id;
    	this.curr_x = init_x;
    	this.curr_y = init_y;
    	this.direction = 0;
        this.velocity = 0;
    }

    /**
     *   sets the velocities of a player
     */
    public void updateVelocityVectorPolar(double dd, int nv) {
		direction += dd;
		direction %= 2*3.14;
		velocity = nv/2;
		System.out.println(direction+" "+velocity);
    }

    /**
     *   retrieves a velocity for a player
     */
    private int[] getCartesianVect() {
        return new int[] {(int)(velocity*Math.cos(direction)), (int)(velocity*Math.sin(direction))};
    }

    /**
     *    gets the position of a player
     */
    public int[] getCartesianPos() {
        int pos[] = {this.curr_x,this.curr_y};
        return pos;
    }

	public void advance() {
		int[] deltas = getCartesianVect();
		System.out.println(java.util.Arrays.toString(deltas));
		curr_x += deltas[0];
		curr_x %= GViewControl.WINDOW_WIDTH;
		curr_y += deltas[1];
		curr_y %= GViewControl.WINDOW_HEIGHT;
	}

    /**
     *     used for printing a character object to the screen.
     */
    public String toString() {
    	return "{" + id + ":" + curr_x + "," + curr_y + "}";
    }
}
