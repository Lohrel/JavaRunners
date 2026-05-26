import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePanel extends JPanel implements Runnable, KeyListener {




    // ===== THREAD =====
    private Thread gameThread;

    private Player player;
    private float gravity = 0.5f;


    private boolean onGround;

    // ===== CHÃO =====
    private float groundY = 300;

    public GamePanel(PlayerModel playerModel) {

        this.player = new Player(playerModel);
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.BLACK);

        this.setFocusable(true);
        this.addKeyListener(this);

        spawnPlayer(100, 200);

        startGameLoop();
    }

    // ===== "spawn" estilo Enemy =====
    private void spawnPlayer(float startX, float startY) {
        player.x = startX;
        player.y = startY;
        player.velocitY = 0;
        player.isOnGround = false;
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

        player.y += player.velocitY;
        player.velocitY += gravity;
    }

    // ===== COLISÃO COM CHÃO =====
    private void checkGroundCollision() {

        if (player.y >= groundY) {
            player.y = groundY;
            player.velocitY = 0;
            onGround = true;
        }
    }
    // ===== RENDER =====
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // chão
        g2.setColor(Color.WHITE);
        g2.fillRect(0, (int) groundY + (int) player.height, 800, 10);

        // player
        g2.setColor(Color.GREEN);
        g2.fillOval(
                (int) player.x,
                (int) player.y,
                (int) player.width,
                (int) player.height
        );

        g2.dispose();
    }

    // ===== INPUT =====
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.Jump();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
