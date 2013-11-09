package game;

import java.util.*;


class GameModel extends Observable{

	private ShipData[] theShips;
	public static final int GRID_DIMENSION = 100;

	public GameModel(){
		//add the ships (Android devices)
	}

	public void doAThing(){
		setChanged();
		notifyObservers();
	}
}