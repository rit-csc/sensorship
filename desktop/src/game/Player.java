package game;

import api.Listener;
import api.Registrar;
import api.SensorConstants;

/**
 *   Player class for representing a player in-game
 */
public class Player implements Listener {
    public static final int PADDLE_WIDTH = 10;
    public static final int PADDLE_HEIGHT = 80;
    
    private static final float DONT_CARE = 0.0f;
	private static final float GRAV_TOLERANCE_Z = 0.1f;
    
    public int id;
    public float x;
    public float y;

	public float gravity;

    /**
     *   Constructor
     */
    public Player(int id) {
        if(id == 0)
            this.x = 0.0f;
        else if(id == 1)
            this.x = GViewControl.WINDOW_WIDTH - PADDLE_WIDTH;
        
        this.y = GViewControl.WINDOW_HEIGHT/2 + PADDLE_HEIGHT/2;
    	this.id = id;
		Registrar.registerListener(id, SensorConstants.TYPE_GRAVITY, new float[][] {new float[] {DONT_CARE, DONT_CARE, GRAV_TOLERANCE_Z}}, this);
    }

    /**
     *     used for printing a character object to the screen.
     */
    public synchronized String toString() {
    	return "{}";
    }
    
    public synchronized float[] xAndY() {
        return new float[] {x, y};
    }

	public void sensorUpdated(int player, int sensor, float[] incoming) {
        // Why
		float tentativeY = (incoming[2] + 10.0f)*(GViewControl.WINDOW_HEIGHT - PADDLE_HEIGHT)/20.0f;
        
        if(tentativeY < 0) this.y = 0;
        else if(tentativeY > GViewControl.WINDOW_HEIGHT - PADDLE_HEIGHT) this.y = GViewControl.WINDOW_HEIGHT - PADDLE_HEIGHT;
        else this.y = tentativeY;
        
        System.out.println("New Y: " + this.y);
	}
}
