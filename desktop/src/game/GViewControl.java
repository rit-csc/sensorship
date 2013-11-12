package game;

import javax.swing.*;   // JButton, JFrame

import java.awt.*;  // BorderLayout, Container, Color
//import java.awt.event.*; //ActionListener, ActionEvent
import java.util.*; //Observable, Observer

import api.Registrar;

class Ball {
	public int x;
	public int y;
	public double xVel;
	public double yVel;
	
	public Ball(int x, int y, double xVel, double yVel) {
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
	}
}

public class GViewControl {
        private static final int SCALE_FACTOR = 2;
	private static final long SLEEP_TIME_MS = 25;
	public static final int WINDOW_WIDTH = 500*SCALE_FACTOR;
	public static final int WINDOW_HEIGHT = 500*SCALE_FACTOR;
	private static final int BALL_RAD = 30*SCALE_FACTOR;
	
	public static final double BALL_SPEED = 8.0;
	
	// 75 degrees
	public static final double MAX_BOUNCE_ANGLE = 5 * Math.PI/12;

	private static ArrayList<Player> ships = new ArrayList<Player>();
	private static Graphics drawable = null;

    public static void main(String[] args) {
   		
    	//create outer frame for game display
		JFrame frame = new JFrame("SensorSHIP");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		final Ball ball = new Ball(WINDOW_WIDTH/2, WINDOW_HEIGHT/2, (float)(BALL_SPEED), 0.0f);
		
		
		//get content pane of the frame
		frame.setContentPane(new JPanel() {
			@Override
			public void paintComponent(Graphics drawable) {
				super.paintComponent(drawable);

				setBackground(Color.BLACK);
				drawable.setColor(Color.WHITE);
				for(Player each : ships) {
					//float[] coords = {(float)Math.random()*WINDOW_WIDTH, (float)Math.random()*WINDOW_HEIGHT};
					// drawable.fillOval(coords[0], coords[1], SHIP_RAD, SHIP_RAD);
					float[] xAndY = each.xAndY();
					drawable.drawRect((int)xAndY[0], (int)xAndY[1], Player.PADDLE_WIDTH, Player.PADDLE_HEIGHT);
					
				}
				
				ball.x = (int)(float)(ball.x + ball.xVel);
				ball.y = (int)(float)(ball.y + ball.yVel);
				
				if(ball.y < 0 + BALL_RAD || ball.y > WINDOW_HEIGHT - BALL_RAD) {
					ball.yVel = -ball.yVel;
				}
				
				if((ball.x - BALL_RAD/2) < Player.PADDLE_WIDTH) {
					float[] p0coords = ships.get(0).xAndY();
					double p0middle = (p0coords[1] + Player.PADDLE_HEIGHT/2);
					
					double distFromP0Middle = (double)ball.y - p0middle;
					
					if(Math.abs(distFromP0Middle) < Player.PADDLE_HEIGHT/2) {
						System.out.println("WE HIT THE PADDLE (p0)!");
						// We hit the paddle
						double normalizedRelativeIntersectionY = (distFromP0Middle/(Player.PADDLE_HEIGHT/2));
						double bounceAngle = normalizedRelativeIntersectionY * MAX_BOUNCE_ANGLE;
						
						ball.xVel = BALL_SPEED * Math.cos(bounceAngle);
						ball.yVel = BALL_SPEED * -Math.sin(bounceAngle);
						
						System.out.printf("Set new xvel, yvel to %f, %f\n", ball.xVel, ball.yVel);
					}
				}
				
				if((ball.x + BALL_RAD/2) > (WINDOW_WIDTH - Player.PADDLE_WIDTH)) {
					float[] p1coords = ships.get(1).xAndY();
					double p1middle = (p1coords[1] + Player.PADDLE_HEIGHT/2);
					
					double distFromP1Middle = (double)ball.y - p1middle;
					
					if(Math.abs(distFromP1Middle) < Player.PADDLE_HEIGHT/2) {
						System.out.println("WE HIT THE PADDLE (p1)!");
						// We hit the paddle
						double normalizedRelativeIntersectionY = (distFromP1Middle/(Player.PADDLE_HEIGHT/2));
						double bounceAngle = normalizedRelativeIntersectionY * MAX_BOUNCE_ANGLE;
						
						ball.xVel = BALL_SPEED * -Math.cos(bounceAngle);
						ball.yVel = BALL_SPEED * -Math.sin(bounceAngle);
						
						System.out.printf("Set new xvel, yvel to %f, %f\n", ball.xVel, ball.yVel);
					}
				}
				
				drawable.fillOval(ball.x, ball.y, BALL_RAD, BALL_RAD);
			}
		});
		Container container = frame.getContentPane();
		//System.out.println(String.valueOf(frame.getGraphics()));
		drawable = container.getGraphics();
		frame.setVisible(true);
		container.setVisible(true);
		
		// ships.add(new Player(0));
		
		for(int i = 0; i < Registrar.numPlayers(); ++i) {
			ships.add(new Player(i));
		}

		try {
			while(true) {
				frame.repaint();
				Thread.sleep(SLEEP_TIME_MS);
			}
		}
		catch(InterruptedException ex) {}
    }
}
