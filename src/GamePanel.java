import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    Thread gameThread;

    // posição da bola
    int ballX = 100;
    int ballY = 200;

    // tamanho
    int ballSize = 50;

    // física
    double velocityY = 0;
    double gravity = 0.5;
    double jumpStrength = -10;

    // chão
    int groundY = 300;

    boolean onGround = true;

    public GamePanel() {

        this.setPreferredSize(new Dimension(800, 400));

        this.setBackground(Color.BLACK);

        this.addKeyListener(this);

        this.setFocusable(true);

        startGameLoop();
    }

    public void startGameLoop() {

        gameThread = new Thread(this);

        gameThread.start();
    }

    @Override
    public void run() {

        while(true) {

            update();

            repaint();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        // aplica gravidade
        velocityY += gravity;

        // move bola
        ballY += velocityY;

        // colisão com chão
        if(ballY >= groundY) {

            ballY = groundY;

            velocityY = 0;

            onGround = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // chão
        g2.setColor(Color.WHITE);

        g2.fillRect(0, groundY + ballSize, 800, 10);

        // bola
        g2.setColor(Color.GREEN);

        g2.fillOval(ballX, ballY, ballSize, ballSize);

        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE && onGround) {

            velocityY = jumpStrength;

            onGround = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
