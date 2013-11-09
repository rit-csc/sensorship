package game;

import api.Listener;
import api.Registrar;
import api.SensorConstants;

/**
 *   Player class for representing a player in-game
 */
public class Player implements Listener {
	private static final int VELOCITY_MAGNIFICATION_FACTOR = 3;
	private static final float GRAV_TOLERANCE_X = 0.25f;
	private static final float GRAV_TOLERANCE_Z = 0.25f;

	private float id;
	private float curr_x;
	private float curr_y;
	private double direction;
	private int velocity;

	public synchronized void TEMP_CLEAR_DIRECTION() {
		direction=0.0;
		System.out.println("REALLIGNED");
	}

    /**
     *   Constructor
     */
    public Player(int id, int init_x, int init_y) {
    	this.id = id;
    	this.curr_x = init_x;
    	this.curr_y = init_y;
    	this.direction = 0;
        this.velocity = 0;
		Registrar.registerListener(id, SensorConstants.TYPE_GRAVITY, new float[][] {new float[] {GRAV_TOLERANCE_X, 0, GRAV_TOLERANCE_Z}}, this);
    }

    /**
     *   sets the velocities of a player
     */
    public synchronized void updateVelocityVectorPolar(double dd, int nv) {
		direction += dd;
		direction %= 2*3.14;
		velocity = nv*VELOCITY_MAGNIFICATION_FACTOR;
		System.out.println(direction+" "+velocity);
    }

    /**
     *   retrieves a velocity for a player
     */
    private synchronized int[] getCartesianVect() {
        return new int[] {(int)(velocity*Math.cos(direction)), (int)(velocity*Math.sin(direction))};
    }

    /**
     *    gets the position of a player
     */
    public synchronized int[] getCartesianPos() {
        int pos[] = {(int)this.curr_x,(int)this.curr_y};
        return pos;
    }

	public synchronized void advance() {
		int[] deltas = getCartesianVect();
		System.out.println(java.util.Arrays.toString(deltas));
		curr_x += deltas[0]+GViewControl.WINDOW_WIDTH;
		curr_x %= GViewControl.WINDOW_WIDTH;
		curr_y += deltas[1]+GViewControl.WINDOW_HEIGHT;
		curr_y %= GViewControl.WINDOW_HEIGHT;
	}

    /**
     *     used for printing a character object to the screen.
     */
    public synchronized String toString() {
    	return "{" + id + ":" + curr_x + "," + curr_y + "}";
    }

	public void sensorUpdated(int player, int sensor, float[] incoming) {
		updateVelocityVectorPolar(-incoming[0], (int)incoming[2]); //direction is x, magnitude is z
	}
}
