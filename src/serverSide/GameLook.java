package serverSide;

import javax.swing.*;
import java.awt.*;

public class GameLook extends JFrame {
    JPanel headPanel = new JPanel();
    JButton newGame = new JButton("NewGame");
    JPanel northPanel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel inNorthPanelCenter = new JPanel();
    JPanel inNorthPanelSouth = new JPanel();
    JButton score = new JButton("Score board?");

    ImageIcon profile = new ImageIcon("src/serverSide/image/profilbild.jpg");
    JLabel userName = new JLabel("Username");
    JLabel waitingPlayer = new JLabel("Waiting for player two");
    JLabel picture = new JLabel();

    public GameLook() {
        // Konfigurera huvudfönstret
        setTitle("Quizkampen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null);

        // Layouts och grundfärger
        headPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());
        panel2.setLayout(new FlowLayout());
        panel2.setBackground(Color.CYAN);
        inNorthPanelCenter.setBackground(Color.CYAN);
        inNorthPanelSouth.setBackground(Color.CYAN);
        headPanel.setBackground(Color.CYAN);

        // Anpassa komponenter
        picture.setIcon(profile);
        userName.setFont(new Font("Arial", Font.BOLD, 16));

        newGame.setFont(new Font("Arial", Font.BOLD, 16));


        // Lägg till komponenter
        panel2.add(newGame);
        panel2.add(score);
        inNorthPanelCenter.add(picture);
        inNorthPanelSouth.add(userName);

        // Lägg till paneler
        headPanel.add(northPanel, BorderLayout.CENTER);
        headPanel.add(panel2, BorderLayout.SOUTH);
        northPanel.add(inNorthPanelCenter, BorderLayout.NORTH);
        northPanel.add(inNorthPanelSouth, BorderLayout.CENTER);
        add(headPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GameLook();
    }
}
