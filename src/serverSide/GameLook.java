package serverSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLook extends JFrame implements ActionListener {
    //JFrame for the game
    JFrame gameFrame = new JFrame();
    JPanel firstPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel categoryPanel = new JPanel();
    JLabel chooseCategory = new JLabel("Choose your category");
    JButton subject1 = new JButton("Subject 1");
    JButton subject2 = new JButton("Subject 2");
    JButton subject3 = new JButton("Subject 3");
    JButton subject4 = new JButton("subject 4");
    JButton emtybutton = new JButton("");


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
        userName.setFont(new Font("Arial", Font.BOLD, 20));
        waitingPlayer.setFont(new Font("Arial", Font.BOLD, 20));
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

        //Startar en ny frame för spelet.
        gameFrame.setSize(500, 650);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("the quiz game");
        gameFrame.setLocationRelativeTo(null);

        firstPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        categoryPanel.setLayout(new BorderLayout());

        gameFrame.add(firstPanel);
        firstPanel.add(buttonPanel, BorderLayout.CENTER);
        firstPanel.add(categoryPanel, BorderLayout.NORTH);

        categoryPanel.add(chooseCategory, BorderLayout.CENTER);
        buttonPanel.add(emtybutton);

        buttonPanel.add(subject1);
        buttonPanel.add(subject2);
        buttonPanel.add(subject3);
        buttonPanel.add(subject4);


        emtybutton.setContentAreaFilled(false);
        emtybutton.setBorderPainted(false);


        buttonPanel.setBackground(Color.CYAN);
        categoryPanel.setBackground(Color.red);
        categoryPanel.setPreferredSize(new Dimension(500, 200));
        chooseCategory.setFont(new Font("Arial", Font.BOLD, 16));
        chooseCategory.setHorizontalAlignment(SwingConstants.CENTER);
        chooseCategory.setVerticalAlignment(SwingConstants.CENTER);


        emtybutton.setPreferredSize(new Dimension(500, 50));
        subject1.setPreferredSize(new Dimension(225, 150));
        subject2.setPreferredSize(new Dimension(225, 150));
        subject3.setPreferredSize(new Dimension(225, 150));
        subject4.setPreferredSize(new Dimension(225, 150));

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
            JOptionPane.showMessageDialog(this, "The scoreboard does not exits right now");
        }
    }
}
