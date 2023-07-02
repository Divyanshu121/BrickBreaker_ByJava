import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;//game should not play itself after execution
    private int score = 0;//by making them private, only gameplay class can access it.

    private int totalBricks = 21;
    private Timer timer;
    private int delay = 1;//speed of the ball

    private int playerx = 310;//starting position of the slider
    private int ballposx = 120;
    private int ballposy = 350;
    private int ballXdir = -1;//direction
    private int ballydir = -2;

    private MapGenerator map;

    public Gameplay() {//constructor
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);//By setting it to true, the component can receive focus and become interactive when the user clicks on it.
        setFocusTraversalKeysEnabled(false);//This method sets whether or not the component should use focus traversal keys to move the focus among its subcomponents.
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D) g);
        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(0, 0, 3, 592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        // the paddle(moving tile /slider)
        g.setColor(Color.green);
        g.fillRect(playerx, 550, 100, 8);

        //the ball(ball size.shape and color)
        g.setColor(Color.yellow);
        g.fillOval(ballposx, ballposy, 20, 20);

        if(totalBricks<=0){
            play=false;
            ballXdir=0;
            ballydir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over,Scores:",190,300);
        }

        if(ballposy>570){
            play=false;
            ballXdir=0;
            ballydir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Win!!",260,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to restart:",230,350);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
//Intersect ball with the slider: code below shows it.
        if (play) {
            if (new Rectangle(ballposx, ballposy, 20, 20).intersects(new Rectangle(playerx, 550, 100, 8))) {
                ballydir = -ballydir;
            }
            //one map is from gameplay class which is the structure of bricks in row and column
            // and the second map is from mapgenerator class in which a 2dArray is made for making tiles
            A:for(int i=0;i<map.map.length;i++) {//here , A is called to be a labeled name
                for (int j = 0; j < map.map.length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickx = j * map.brickWidth + 80;
                        int bricly = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickx, bricly, brickWidth, brickHeight);
                        Rectangle ballrect = new Rectangle(ballposx, ballposy, 20, 20);
                        Rectangle brickrect = rect;
                        if (ballrect.intersects(brickrect)) {
                            map.setBrickvalue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposx + 19 <= brickrect.x || ballposx + 1 >= brickrect.x + brickrect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballydir = -ballydir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposx += ballXdir;
            ballposy += ballydir;
            if (ballposx < 0) {//for the left border
                ballXdir = -ballXdir;
            }
            if (ballposy < 0) {//for the top border
                ballydir = -ballydir;
            }
            /*for the right border*/if (ballposx > 670) {//pos=position,dir=direction
                ballXdir = -ballXdir;
            }
        }
        repaint();//recall the paint function to draw everything from scratch when this statement called
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {//if right key pressed then
            if (playerx >= 600) {//keep slider in the frame.so that it cannot go outside of it.
                playerx = 600;

            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {//if left key pressed then
            if (playerx < 10) {
                playerx = 10;

            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposx=120;
                ballposy=350;
                ballXdir=-1;//here it is the movement of the ball moving in x and in y direction.
                ballydir=-2;
                playerx=310;
                score=0;
                totalBricks=21;
                map=new MapGenerator(3,7);
                repaint();

            }
        }
    }

    public void moveRight() {
        play = true;
        playerx += 20;//move slider by 20

    }

    public void moveLeft() {
        play = true;
        playerx -= 20;
    }



    @Override
    public void keyReleased(KeyEvent e) {//mouse key pressing
    }
    public static void main (String[]args){

    }
}
