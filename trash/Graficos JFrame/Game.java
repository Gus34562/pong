package graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	public static JFrame frame;
	private final int WIDTH = 240;
	private final int HEIGHT = 160;
	private final int SCALE = 3;
	
	private boolean isRunning;
	
	private Thread thread;
	
	private BufferedImage image;
	
	private Spritesheet sheet;
	//private BufferedImage player;
	private BufferedImage[] player;
	private int x = 0;
	private int frames = 0;
	private int maxFrames = 10; //Velocidade da animação
	private int curAnimation = 0; //Current animation (for the image vector)
	private int maxAnimation = 4; //Number of images
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		//Specify the canvas size
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		sheet = new Spritesheet("/spritesheet.png");
		//player = sheet.getSprite(0, 0, 16, 16);
		player = new BufferedImage[4];
		player[0] = sheet.getSprite(0, 0, 16, 16);
		player[1] = sheet.getSprite(16, 0, 16, 16);
		player[2] = sheet.getSprite(32, 0, 16, 16);
		player[3] = sheet.getSprite(48, 0, 16, 16);
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
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() { 
		//System.out.println("Tick");
		x++;
		if (x>WIDTH) x=-16;
		frames++;
		if (frames>maxFrames) {
			frames = 0;
			curAnimation++;
			if (curAnimation>=maxAnimation) curAnimation = 0;
		}
	}
	
	public void render() { 
		//System.out.println("Render"); 
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		
		//////////Renderização do jogo///////////////
		
		//Desenha o fundo da tela
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Desenha um retângulo na tela
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 20, 20);
		
		//Desenha um oval na tela
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, 50, 40);
		
		//Desenha uma string na tela
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.WHITE);
		g.drawString("Hello World", 10, 80);
		
		//Desenha uma linha na tela
		g.setColor(Color.ORANGE);
		g.drawLine(20, 120, 200, 80);
		
		//Desenha o player na tela andando em loop
		g.drawImage(player[curAnimation], x, 20, null);
		
		//Desenha o player parado
		g.drawImage(player[0], 20, 50, null);
		
		//Adiciona um layer escuro (Sombra)
		g.setColor(new Color(0,0,0,100));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Rotaciona o player
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(Math.toRadians(90), 90+8, 90+8); //graus pra rotacionar (em radianos), seguido pelas coordenadas (x,y) do ponto de rotação (x+8,y+8 para ficar no centro do sprite 16x16 do personagem)
		g.drawImage(player[0], 50, 90, null);
		
		////////////////////////////////////////////
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
		
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
		
		stop();
	}
}
