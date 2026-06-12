import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {


    PlayerModel playerModel;
    private int highScore = 9820;
    private JButton startButton;
    private JButton exitButton;
    private float glowAlpha = 0f;
    private boolean glowIncreasing = true;
    private Timer animTimer;

    // Paleta de cores
    private static final Color BG_DARK      = new Color(8, 5, 20);
    private static final Color BG_MID       = new Color(18, 10, 45);
    private static final Color ACCENT_CYAN  = new Color(0, 230, 210);
    private static final Color ACCENT_PURP  = new Color(140, 60, 255);
    private static final Color TEXT_WHITE   = new Color(230, 230, 255);
    private static final Color BTN_BG       = new Color(25, 15, 60, 200);
    private static final Color BTN_HOVER    = new Color(0, 230, 210, 40);

    public MenuPanel(PlayerModel playerModel) {
        this.playerModel = playerModel;
        setLayout(null);
        setPreferredSize(new Dimension(700, 500));
        setBackground(BG_DARK);

        initButtons();
        startGlowAnimation();
    }

    // ── Botões ────────────────────────────────────────────────────────────────

    private void initButtons() {
        startButton = createStyledButton("▶  START", ACCENT_CYAN);
        exitButton  = createStyledButton("✕  SAIR", ACCENT_PURP);

        startButton.setBounds(250, 240, 200, 52);
        exitButton.setBounds(250, 308, 200, 52);

        startButton.addActionListener(e -> onStart());
        exitButton.addActionListener(e -> onExit());

        add(startButton);
        add(exitButton);
    }

    private JButton createStyledButton(String text, Color accent) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hovered = getModel().isRollover();

                // Fundo
                g2.setColor(hovered ? BTN_HOVER : BTN_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Borda colorida
                g2.setColor(hovered ? accent : accent.darker());
                g2.setStroke(new BasicStroke(hovered ? 2f : 1.2f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 8, 8);

                // Linha decorativa à esquerda
                g2.setColor(accent);
                g2.fillRect(0, 14, 3, getHeight() - 28);

                // Texto
                g2.setColor(hovered ? accent : TEXT_WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };

        btn.setFont(new Font("Monospaced", Font.BOLD, 15));
        btn.setForeground(TEXT_WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── Animação de glow ──────────────────────────────────────────────────────

    private void startGlowAnimation() {
        animTimer = new Timer(40, e -> {
            if (glowIncreasing) {
                glowAlpha += 0.03f;
                if (glowAlpha >= 1f) { glowAlpha = 1f; glowIncreasing = false; }
            } else {
                glowAlpha -= 0.03f;
                if (glowAlpha <= 0f) { glowAlpha = 0f; glowIncreasing = true; }
            }
            repaint();
        });
        animTimer.start();
    }

    // ── Pintura do fundo e título ─────────────────────────────────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // Gradiente de fundo
        GradientPaint bgGrad = new GradientPaint(0, 0, BG_DARK, 0, h, BG_MID);
        g2.setPaint(bgGrad);
        g2.fillRect(0, 0, w, h);

        drawGrid(g2, w, h);
        drawGlowOrb(g2, w, h);
        drawTitle(g2, w);
        drawSubtitle(g2, w);
        drawHighScore(g2, h);
        drawVersion(g2, w, h);
    }

    private void drawGrid(Graphics2D g2, int w, int h) {
        g2.setColor(new Color(60, 40, 120, 35));
        g2.setStroke(new BasicStroke(1f));
        int step = 40;
        for (int x = 0; x < w; x += step) g2.drawLine(x, 0, x, h);
        for (int y = 0; y < h; y += step) g2.drawLine(0, y, w, y);
    }

    private void drawGlowOrb(Graphics2D g2, int w, int h) {
        int cx = w / 2, cy = 145;
        int r = 160;
        float alpha = 0.10f + glowAlpha * 0.12f;

        RadialGradientPaint rg = new RadialGradientPaint(
                new Point(cx, cy), r,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(ACCENT_CYAN.getRed(), ACCENT_CYAN.getGreen(), ACCENT_CYAN.getBlue(), (int)(alpha * 255)),
                        new Color(0, 0, 0, 0)
                }
        );
        g2.setPaint(rg);
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);
    }

    private void drawTitle(Graphics2D g2, int w) {
        String title = "Java Runners";
        Font titleFont = new Font("Monospaced", Font.BOLD, 54);
        g2.setFont(titleFont);
        FontMetrics fm = g2.getFontMetrics();
        int tx = (w - fm.stringWidth(title)) / 2;
        int ty = 130;

        // Sombra com glow púrpura
        g2.setColor(new Color(ACCENT_PURP.getRed(), ACCENT_PURP.getGreen(), ACCENT_PURP.getBlue(),
                (int)(60 + glowAlpha * 80)));
        for (int d = 6; d >= 1; d--) {
            g2.drawString(title, tx + d, ty + d);
            g2.drawString(title, tx - d, ty + d);
        }

        // Gradiente no texto principal
        GradientPaint textGrad = new GradientPaint(
                tx, ty - 50, ACCENT_PURP, tx + fm.stringWidth(title), ty, ACCENT_CYAN
        );
        g2.setPaint(textGrad);
        g2.drawString(title, tx, ty);
    }

    private void drawSubtitle(Graphics2D g2, int w) {
        String sub = "— MAIN MENU —";
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.setColor(new Color(ACCENT_CYAN.getRed(), ACCENT_CYAN.getGreen(), ACCENT_CYAN.getBlue(), 160));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(sub, (w - fm.stringWidth(sub)) / 2, 155);
    }

    private void drawHighScore(Graphics2D g2, int h) {
        int px = 20, py = h - 20;

        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2.setColor(new Color(160, 140, 200, 180));
        g2.drawString("HIGH SCORE", px, py - 18);

        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        GradientPaint scoreGrad = new GradientPaint(px, 0, ACCENT_CYAN, px + 90, 0, ACCENT_PURP);
        g2.setPaint(scoreGrad);
        g2.drawString(String.format("%06d", highScore), px, py);
    }

    private void drawVersion(Graphics2D g2, int w, int h) {
        g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
        g2.setColor(new Color(100, 80, 160, 130));
        String ver = "v1.0.0";
        g2.drawString(ver, w - 50, h - 10);
    }

    // ── Ações ─────────────────────────────────────────────────────────────────

    protected void onStart() {
        animTimer.stop(); // boa prática: para o timer do menu

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        GamePanel gamePanel = new GamePanel(playerModel);
        frame.setContentPane(gamePanel);
        frame.revalidate();
        frame.repaint();

        gamePanel.requestFocusInWindow(); // dá o foco ao GamePanel
    }
    protected void onExit() {
        int confirm = JOptionPane.showConfirmDialog(
                this, "Deseja sair do jogo?", "Sair",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setHighScore(int score) {
        this.highScore = score;
        repaint();
    }


}