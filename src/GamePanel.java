import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // ===== THREAD =====
    private Thread gameThread;

    // ===== PLAYER (antes variáveis soltas) =====
    private float playerX;
    private float playerY;
    private float velocityY;

    private float width = 50;
    private float height = 50;

    private float gravity = 0.5f;
    private float jumpStrength = -10;

    private boolean onGround;

    // ===== CHÃO =====
    private float groundY = 300;

    public GamePanel() {

        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.BLACK);

        this.setFocusable(true);
        this.addKeyListener(this);

        spawnPlayer(100, 200);

        startGameLoop();
    }

    // ===== "spawn" estilo Enemy =====
    private void spawnPlayer(float startX, float startY) {
        this.playerX = startX;
        this.playerY = startY;
        this.velocityY = 0;
        this.onGround = false;
    }

    // ===== GAME LOOP =====
    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (true) {

            update();

            repaint();

            try {
                Thread.sleep(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ===== UPDATE (igual Enemy.update) =====
    private void update() {
        applyPhysics();
        checkGroundCollision();
    }

    // ===== FÍSICA =====
    private void applyPhysics() {
        velocityY += gravity;
        playerY += velocityY;
    }

    // ===== COLISÃO COM CHÃO =====
    private void checkGroundCollision() {

        if (playerY >= groundY) {
            playerY = groundY;
            velocityY = 0;
            onGround = true;
        }
    }

    // ===== PULO =====
    private void jump() {
        if (onGround) {
            velocityY = jumpStrength;
            onGround = false;
        }
    }

    // ===== RENDER =====
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // chão
        g2.setColor(Color.WHITE);
        g2.fillRect(0, (int) groundY + (int) height, 800, 10);

        // player
        g2.setColor(Color.GREEN);
        g2.fillOval(
                (int) playerX,
                (int) playerY,
                (int) width,
                (int) height
        );

        g2.dispose();
    }

    // ===== INPUT =====
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
