import javax.swing.JFrame;

public class main {


    private static JFrame window;

    public static void main(String[] args) {

        initWindow();

        MenuPanel menuPanel = startGame();

        window.setVisible(true);

        menuPanel.requestFocusInWindow();
    }

    private static void initWindow() {

        window = new JFrame("JavaRunner");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
    }

    private static MenuPanel startGame() {

        PlayerModel playerModel = new PlayerModel();
        MenuPanel menuPanel = new MenuPanel(playerModel);


        window.add(menuPanel);

        window.pack();

        window.setLocationRelativeTo(null);

        return menuPanel;
    }
}
