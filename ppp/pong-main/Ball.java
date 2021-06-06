import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Ball {

    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board grid
    private float posx;
    private float posy;
    private float velx;
    private float vely;
    private float speed = 10;
    private int width;
    private int height;
    private Board board;
    // keep track of the player's score

    public Ball(Board board) {
        // load the assets
        loadImage();

        // initialize the state
        width = image.getWidth();
        height = image.getHeight();
        posx = (board.WIDTH - width)/2;
        posy = board.HEIGHT/2 - height/2;
        velx = 5;
        vely = 5;
        fixSpeed();
    }
    private void fixSpeed(){
        float mod = velx*velx + vely*vely;
        mod = (float)Math.sqrt(mod);
        velx = velx/mod * speed;
        vely = vely/mod * speed;
    }
    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("images/ball.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
    public void wallCollision(){
        if(posx + width >= board.WIDTH){
            posx = board.WIDTH-1-width;
            velx *= -1;
        }
        if(posx < 0){
            posx = 0;
            velx *= -1;
        }
        if(posy + height>= board.HEIGHT){
            posy = board.HEIGHT-1 - height;
            vely *= -1;
        }
        if(posy < 0){
            posy = 0;
            vely *= -1;
        }
    }

    public void update(){
        posx += velx;
        posy += vely;
        wallCollision();
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // posx reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
            image, 
            (int)posx, 
            (int)posy, 
            observer
        );
    }
    
    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();
        
        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if (key == KeyEvent.VK_UP) {
            posy+=-10;
        }
        //if (key == KeyEvent.VK_RIGHT) {
        //    pos.translate(10, 0);
        //}
        if (key == KeyEvent.VK_DOWN) {
            posy+=10;
        }
        //if (key == KeyEvent.VK_LEFT) {
        //    pos.translate(-10, 0);
        //}
    }

    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // prevent the player from moving off the edge of the board sideways
        //if (posx < 0) {
        //    posx = 0;
        //} else if (posx >= Board.COLUMNS) {
        //    posx = Board.COLUMNS - 1;
        //}
        //// prevent the player from moving off the edge of the board vertically
        //if (posy < 0) {
        //    posy = 0;
        //} else if (posy >= Board.ROWS) {
        //    posy = Board.ROWS - 1;
        //}
    }

    public void addScore(int amount) {
    }
}
