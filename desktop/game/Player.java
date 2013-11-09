package game;

public class Player {
	private int curr_x;
	private int curr_y;
	private String player_name;

    public Player(String player_name, int init_x, int init_y) {
    	this.player_name = player_name;
    	this.curr_x = init_x;
    	this.curr_y = init_y;
    }
}
