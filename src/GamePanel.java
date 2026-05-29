import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread gameThread;

    private Player player;

    private final float gravity = 0.5f;
    private final float groundY = 300;

    // ===== chão =====
    private int groundOffset = 0;
    private final int groundSpeed = 4;

    // ===== nuvens =====
    private class Cloud {
        float x, y;
        float speed;

        Cloud(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }
    }

    private ArrayList<Cloud> clouds = new ArrayList<>();
    private Random random = new Random();

    public GamePanel(PlayerModel playerModel) {

        player = new Player(playerModel);

        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);

        spawnPlayer(100);

        spawnClouds();

        startGameLoop();
    }

    // ===== player =====
    private void spawnPlayer(float startX) {

        player.x = startX;
        player.y = groundY - player.height;

        player.velocitY = 0;
        player.isOnGround = true;
    }

    // ===== nuvens iniciais =====
    private void spawnClouds() {

        for (int i = 0; i < 5; i++) {

            clouds.add(new Cloud(
                    random.nextInt(800),
                    random.nextInt(120),
                    1 + random.nextFloat() * 1.5f
            ));
        }
    }

    // ===== loop =====
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

    // ===== update =====
    private void update() {

        applyPhysics();
        checkGroundCollision();

        // ===== chão =====
        groundOffset -= groundSpeed;
        if (groundOffset <= -40) {
            groundOffset = 0;
        }

        // ===== nuvens =====
        for (Cloud c : clouds) {

            c.x -= c.speed;

            if (c.x < -60) {
                c.x = 800 + random.nextInt(200);
                c.y = random.nextInt(120);
                c.speed = 1 + random.nextFloat() * 1.5f;
            }
        }
    }

    // ===== física =====
    private void applyPhysics() {

        player.y += player.velocitY;
        player.velocitY += gravity;
    }

    // ===== colisão =====
    private void checkGroundCollision() {

        if (player.y + player.height >= groundY) {

            player.y = groundY - player.height;
            player.velocitY = 0;
            player.isOnGround = true;
        }
    }

    // ===== render =====
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // ===== céu =====
        g2.setColor(new Color(135, 206, 235));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // ===== nuvens =====
        g2.setColor(Color.WHITE);

        for (Cloud c : clouds) {

            g2.fillOval((int) c.x, (int) c.y, 40, 20);
        }

        // ===== chão =====
        g2.setColor(new Color(80, 180, 80));
        g2.fillRect(0, (int) groundY, getWidth(), getHeight() - (int) groundY);

        // linhas do chão
        g2.setColor(new Color(50, 120, 50));

        for (int x = groundOffset; x < getWidth(); x += 40) {

            g2.drawLine(x, (int) groundY, x, getHeight());
        }

        // ===== player =====
        g2.setColor(Color.GREEN);

        g2.fillOval(
                (int) player.x,
                (int) player.y,
                (int) player.width,
                (int) player.height
        );
    }

    // ===== input =====
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            player.Jump();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
