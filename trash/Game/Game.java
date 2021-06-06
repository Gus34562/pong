package graficos;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

//I changed this line

public class Game extends Canvas implements Runnable{

	public static JFrame frame;
	private final int WIDTH = 160;
	private final int HEIGHT = 120;
	private final int SCALE = 4;
	
	private boolean isRunning;
	
	private Thread thread;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		//Specify the canvas size
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
	}
	
	public void initFrame() {
		//You can add the name of the window as well
		frame = new JFrame("Game #1");
				
		//Add a reference to this object (canvas) to the jframe object
		frame.add(this);
				
		//I don't want the player to be able to change the size of the window
		frame.setResizable(false);
				
		//Calculate the canvas dimensions
		frame.pack();
				
		//Set the location of the window to the center of the screen
		frame.setLocationRelativeTo(null);
				
		//When the user closes the window, the game finishes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Makes the window visible
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {}
	
	public void tick() { 
		//System.out.println("Tick"); 
	}
	
	public void render() { 
		//System.out.println("Render"); 
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		
		//Frames per second (fps)
		double amountOfTicks = 60.0;
		
		//Time between each frame (in nanoseconds)
		double ns = 1000000000 / amountOfTicks;
		
		double delta = 0;
	
		int frames = 0;
		
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			//1 second passed since the run() method was called
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS : " + frames);
				frames = 0;
				timer += 1000;
			}
		}
	}
}
