package pacman;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main {
    private static int SCREEN_WIDTH = 448;
    private static int SCREEN_HEIGHT = 496;
    private static TaskbarPanel taskbarPanel;
    private static GamePlayPanel gamePlayPanel;
    public static String name = "Pacman";

    public Main() {
        JFrame jFrame = new JFrame(name);
        JPanel jPanel = new JPanel();

        gamePlayPanel = new GamePlayPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
        taskbarPanel = new TaskbarPanel(SCREEN_WIDTH,SCREEN_HEIGHT / 15);
        jPanel.add(gamePlayPanel);
        jPanel.add(taskbarPanel);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        jFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/icon.png"))).getImage());
        jFrame.add(jPanel);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);


    }

    public static TaskbarPanel getTaskbarPanel() {
        return taskbarPanel;
    }

    public static GamePlayPanel getGamePlayPanel() {
        return gamePlayPanel;
    }

    public static void main(String[] args) {
        new Main();
    }


}