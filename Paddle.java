import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Paddle {

    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board grid
    private Point pos;
    // keep track of the player's score
    private int score;
    
    private Board board;
    public Paddle(Board board, int playernum, int boardwidth, int boardheight) {
        this.board = board;
        // load the assets
        loadImage();
        
        // initialize the state
        pos = new Point(playernum*(boardwidth - image.getWidth()),boardheight/2 - image.getHeight()/2);
        score = 0;
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("images/paddle.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
    public void update(){
        //System.out.print(board.KEYBOARD[KeyEvent.VK_UP]);
        if(board.KEYBOARD[KeyEvent.VK_UP]){
            pos.translate(0, -3);
        }
        if(board.KEYBOARD[KeyEvent.VK_DOWN]){
            pos.translate(0, 3);
        }
    }
    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
            image, 
            pos.x, 
            pos.y, 
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
            pos.translate(0, -10);
        }
        //if (key == KeyEvent.VK_RIGHT) {
        //    pos.translate(10, 0);
        //}
        if (key == KeyEvent.VK_DOWN) {
            pos.translate(0, 10);
        }
        //if (key == KeyEvent.VK_LEFT) {
        //    pos.translate(-10, 0);
        //}
    }

    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // prevent the player from moving off the edge of the board sideways
        //if (pos.x < 0) {
        //    pos.x = 0;
        //} else if (pos.x >= Board.COLUMNS) {
        //    pos.x = Board.COLUMNS - 1;
        //}
        //// prevent the player from moving off the edge of the board vertically
        //if (pos.y < 0) {
        //    pos.y = 0;
        //} else if (pos.y >= Board.ROWS) {
        //    pos.y = Board.ROWS - 1;
        //}
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public Point getPos() {
        return pos;
    }

}
