package game;

import javax.swing.*;   // JButton, JFrame

import java.awt.*;  // BorderLayout, Container, Color
//import java.awt.event.*; //ActionListener, ActionEvent
import java.util.*; //Observable, Observer

public class GViewControl {
	private static final long SLEEP_TIME_MS = 100;
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 500;
	private static final int SHIP_RAD = 30;

	private static ArrayList<Player> ships = new ArrayList<Player>();
	private static Graphics drawable = null;

    public static void main(String[] args) {
    	//System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

    	//create outer frame for game display
		JFrame frame = new JFrame("SensorSHIP");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//get content pane of the frame
		frame.setContentPane(new JPanel() {
			@Override
			public void paintComponent(Graphics drawable) {
				super.paintComponent(drawable);

				setBackground(Color.BLACK);
				drawable.setColor(Color.WHITE);
				for(Player each : ships) {
					int[] coords = each.getCartesianPos();
					//float[] coords = {(float)Math.random()*WINDOW_WIDTH, (float)Math.random()*WINDOW_HEIGHT};
					drawable.fillOval(coords[0], coords[1], SHIP_RAD, SHIP_RAD);
					each.advance();
				}
			}
		});
		Container container = frame.getContentPane();
		//System.out.println(String.valueOf(frame.getGraphics()));
		drawable = container.getGraphics();
		frame.setVisible(true);
		container.setVisible(true);

		ships.add(new Player(0, 0, 0));
		//ships.get(0).updateVelocityVectorPolar(1.8, 5);

		try {
			while(true) {
				frame.repaint();
				Thread.sleep(SLEEP_TIME_MS);
			}
		}
		catch(InterruptedException ex) {}
    }
}
