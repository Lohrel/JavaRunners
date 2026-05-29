import javax.swing.JFrame;

public class main {

    private static JFrame window;

    public static void main(String[] args) {

        initWindow();

        GamePanel gamePanel = startGame();

        window.setVisible(true);

        gamePanel.requestFocusInWindow();
    }

    private static void initWindow() {

        window = new JFrame("JavaRunner");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
    }

    private static GamePanel startGame() {

        PlayerModel playerModel = new PlayerModel();

        GamePanel gamePanel = new GamePanel(playerModel);

        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);

        return gamePanel;
    }
}
