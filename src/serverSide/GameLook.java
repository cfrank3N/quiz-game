package serverSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLook extends JFrame implements ActionListener {
    //JFrame for the game
    JFrame gameFrame = new JFrame();
    JPanel firstPanel = new JPanel();

    //JFrame for the "lobby"
    JPanel headPanel = new JPanel();
    JButton newGame = new JButton("NewGame");
    JPanel northPanel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel inNorthPanelCenter = new JPanel();
    JPanel inNorthPanelSouth = new JPanel();
    JButton score = new JButton("Score board?");

    ImageIcon profile = new ImageIcon("src/serverSide/image/profilbild.jpg");
    JLabel waitingPlayer = new JLabel("Waiting for second player");
    JLabel picture = new JLabel();
    private String playerName = " ";
    JLabel userName = new JLabel(playerName);


    public GameLook() {
        setPlayerName();
        // Konfigurera huvudfönstret
        setTitle("Quizkampen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null);

        // Layouts och grundfärger
        headPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());
        panel2.setLayout(new FlowLayout());
        inNorthPanelSouth.setLayout(new BoxLayout(inNorthPanelSouth, BoxLayout.Y_AXIS));
        panel2.setBackground(Color.CYAN);
        inNorthPanelCenter.setBackground(Color.CYAN);
        inNorthPanelSouth.setBackground(Color.CYAN);
        headPanel.setBackground(Color.CYAN);


        // Anpassa komponenter
        picture.setIcon(profile);
        userName.setFont(new Font("Arial", Font.BOLD, 16));
        score.setFont(new Font("Arial", Font.BOLD, 16));
        newGame.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        waitingPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Lägg till komponenter
        panel2.add(newGame);
        panel2.add(score);
        inNorthPanelCenter.add(picture);
        inNorthPanelSouth.add(userName);
        inNorthPanelSouth.add(waitingPlayer);

        // Lägg till paneler
        headPanel.add(northPanel, BorderLayout.CENTER);
        headPanel.add(panel2, BorderLayout.SOUTH);
        northPanel.add(inNorthPanelCenter, BorderLayout.NORTH);
        northPanel.add(inNorthPanelSouth, BorderLayout.CENTER);
        add(headPanel);

        newGame.addActionListener(this);
        score.addActionListener(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GameLook();
    }

    public void GameStart() {
        //gör den första Frame osynlig
        this.setVisible(false);

        gameFrame.setSize(500, 800);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("the quiz game");
        gameFrame.setLocationRelativeTo(null);

        firstPanel.setLayout(new BorderLayout());

        gameFrame.add(firstPanel);


        gameFrame.setVisible(true);
    }

    public void setPlayerName() {
        String newUserName = JOptionPane.showInputDialog("What is your username?");
        userName.setText(newUserName);
        //ifall man vill spara userName
        this.playerName = newUserName;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            GameStart();
        }
        if (e.getSource() == score) {
            JOptionPane.showMessageDialog(this, "the scoreboard does not exits right now");
        }
    }
}
