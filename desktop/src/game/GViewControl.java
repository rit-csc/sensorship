package game;

import javax.swing.*;   // JButton, JFrame

import java.awt.*;  // BorderLayout, Container, Color
import java.awt.event.*; //ActionListener, ActionEvent
import java.util.*; //Observable, Observer

class GViewControl extends JFrame implements Observer{

	private static GameModel model;
	private static GViewControl view;
	//private static Grid grid;

	public GViewControl( GameModel theModel ){
		model = theModel;
		model.addObserver(this);
	}
    
    public void update(Observable t, Object o){
    	//
    }

    public static void main(String[] args){
    	System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

    	model = new GameModel();
    	view = new GViewControl( model );

    	//create outer frame for game display
		JFrame frame = new JFrame("SensorSHIP");
		/*
		frame.setSize(500, 500);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//get content pane of the frame
		Container container = frame.getContentPane();
		
		//set the border layout for the frame
		container.setLayout(new GridLayout(GameModel.GRID_DIMENSION, GameModel.GRID_DIMENSION));
		for(int row = 0; row < GameModel.GRID_DIMENSION; row++){
			for(int col = 0; col < GameModel.GRID_DIMENSION; col++){
				JButton aLabel = new JButton("help");
				aLabel.setBackground(Color.BLUE);
				container.add( aLabel );
			}
		}
		*/
		//display the window
		frame.setVisible(true);
    }
}