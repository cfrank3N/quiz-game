package serverSide;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLook extends JFrame implements ActionListener {
    private final int frameWidth = 500, frameHeight = 800;
    private final String BACKGROUND_PICTURE = "src/avatars/QuizBackground.jpg";

    //JFrame for choosing subject
    JFrame gameFrame = new JFrame();
    JPanel firstPanel = new BackgroundPanel(BACKGROUND_PICTURE);
    JPanel buttonPanel = new JPanel();
    JPanel categoryPanel = new JPanel();
    JLabel chooseCategory = new JLabel("Choose your category");
    JButton subject1 = new JButton("Subject 1");
    JButton subject2 = new JButton("Subject 2");
    JButton subject3 = new JButton("Subject 3");
    JButton subject4 = new JButton("subject 4");
    JButton emptyButton = new JButton("");

    //JFrame for the rounds
    JFrame roundFrame = new JFrame();
    JPanel mainPanel = new BackgroundPanel(BACKGROUND_PICTURE);
    JPanel thePanelForButton = new JPanel();
    JPanel questionPanel = new JPanel();
    JLabel questionLabel = new JLabel("The question?");
    JButton question1 = new JButton("Question 1");
    JButton question2 = new JButton("question 2");
    JButton question3 = new JButton("Question 3");
    JButton question4 = new JButton("Question 4");
    JButton theEmptyButton = new JButton("");

    //JFrame for the "lobby"
    JPanel headPanel = new BackgroundPanel(BACKGROUND_PICTURE);
    JButton newGame = new JButton("New Game");
    JPanel northPanel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel inNorthPanelCenter = new JPanel();
    JPanel inNorthPanelSouth = new JPanel();
    JButton score = new JButton("Score board");

    ImageIcon profile = new ImageIcon("src/avatars/profilbild.jpg");
    JLabel waitingPlayer = new JLabel("Waiting for second player");
    JLabel picture = new JLabel();
    //Frame för profile
    JFrame profileFrame = new JFrame();
    JPanel picturePanel = new BackgroundPanel(BACKGROUND_PICTURE);

    //Frame player waiting
    JFrame waitingFrame = new JFrame();
    JPanel topPanel = new JPanel();
    JPanel backPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel p1score = new JPanel();
    JPanel p2score = new JPanel();
    JPanel hypen = new JPanel();
    JLabel score1p1 = new JLabel("");
    JLabel score1p2 = new JLabel("");
    JLabel scorehypen1 = new JLabel("-");
    JLabel score2p1 = new JLabel("");
    JLabel score2p2 = new JLabel("");
    JLabel scorehypen2 = new JLabel("-");
    JLabel score3p1 = new JLabel("");
    JLabel score3p2 = new JLabel("");
    JLabel scorehypen3 = new JLabel("-");
    JLabel score4p1 = new JLabel("");
    JLabel score4p2 = new JLabel("");
    JLabel scorehypen4 = new JLabel("-");
    JLabel score5p1 = new JLabel("");
    JLabel score5p2 = new JLabel("");
    JLabel scorehypen5 = new JLabel("-");
    JLabel score6p1 = new JLabel("");
    JLabel score6p2 = new JLabel("");
    JLabel scorehypen6 = new JLabel("-");
    JLabel categories = new JLabel("Categories"); //
    JButton continueButton = new JButton("Continue");
    private String playerName = " ";
    JLabel userName = new JLabel(playerName);


    public GameLook() {
        //setPlayerName();
        //WaitingScreen();
        mainLobby();


//        profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        profileFrame.setTitle("Setup");
//        profileFrame.setSize(frameWidth, frameHeight);
//        profileFrame.setLocationRelativeTo(null);
//
//        profileFrame.add(picturePanel);
//
//        profileFrame.setVisible(true);

    }

    public static void main(String[] args) {
        new GameLook();
    }

    // används ej
    public void WaitingScreen() {
        waitingFrame.setTitle("hejsan");
        waitingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waitingFrame.setSize(frameWidth, frameHeight);
        waitingFrame.setLocationRelativeTo(null);

        backPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout());
        p1score.setLayout(new FlowLayout());
        p2score.setLayout(new FlowLayout());
        hypen.setLayout(new FlowLayout());

        waitingFrame.add(backPanel);
        backPanel.add(topPanel, BorderLayout.NORTH);
        backPanel.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.add(p1score, BorderLayout.WEST);
        bottomPanel.add(hypen, BorderLayout.CENTER);
        bottomPanel.add(p2score, BorderLayout.EAST);
        backPanel.add(inNorthPanelSouth, BorderLayout.SOUTH);
        hypen.add(scorehypen1);
        hypen.add(scorehypen2);
        hypen.add(scorehypen3);
        hypen.add(scorehypen4);
        hypen.add(scorehypen5);
        hypen.add(scorehypen6);


        topPanel.setBackground(Color.CYAN);
        p1score.setBackground(Color.blue);
        hypen.setBackground(Color.red);
        p2score.setBackground(Color.green);
        topPanel.setPreferredSize(new Dimension(500, 250));
        p1score.setPreferredSize(new Dimension(220, 500));
        p2score.setPreferredSize(new Dimension(220, 500));
        scorehypen1.setFont(new Font("Arial", Font.BOLD, 60));
        scorehypen2.setFont(new Font("Arial", Font.BOLD, 60));
        scorehypen3.setFont(new Font("Arial", Font.BOLD, 60));
        scorehypen4.setFont(new Font("Arial", Font.BOLD, 60));
        scorehypen5.setFont(new Font("Arial", Font.BOLD, 60));
        scorehypen6.setFont(new Font("Arial", Font.BOLD, 60));


        waitingFrame.setVisible(true);
    }

    public void mainLobby() {
        // Konfigurera huvudfönstret
        setTitle("Quizkampen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);

        // Layouts och grundfärger
        headPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());
        panel2.setLayout(new FlowLayout());
        inNorthPanelSouth.setLayout(new BoxLayout(inNorthPanelSouth, BoxLayout.Y_AXIS));


        // Anpassa komponenter
        picture.setIcon(profile);
        userName.setFont(new Font("Arial", Font.BOLD, 20));
        waitingPlayer.setFont(new Font("Arial", Font.BOLD, 20));
        score.setFont(new Font("Arial", Font.BOLD, 16));
        newGame.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        waitingPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setOpaque(false);
        inNorthPanelSouth.setOpaque(false);
        northPanel.setOpaque(false);
        inNorthPanelCenter.setOpaque(false);
        newGame.setPreferredSize(new Dimension(225, 100));
        score.setPreferredSize(new Dimension(225, 100));
        waitingPlayer.setPreferredSize(new Dimension(500, 100));
        this.setMaximumSize(new Dimension(frameWidth, frameHeight));
        this.setMinimumSize(new Dimension(frameWidth, frameHeight));
        this.setResizable(false);
//    picture.setPreferredSize(new Dimension(100,100));

        Border backgroundBorder = BorderFactory.createLineBorder(Color.BLACK, 5);
        Border profileBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        headPanel.setBorder(backgroundBorder);
        picture.setBorder(profileBorder);

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

    public void GameStart() {
        //Gör den första Frame osynlig
        this.setVisible(false);

        //Startar en ny frame för spelet.
        gameFrame.setSize(frameWidth, frameHeight);
        gameFrame.setMaximumSize(new Dimension(frameWidth, frameHeight));
        gameFrame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("the quiz game");
        gameFrame.setLocationRelativeTo(null);
        //Panels layout
        firstPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        categoryPanel.setLayout(new BorderLayout());

        //Tilläggning av komponenter
        gameFrame.add(firstPanel);
        firstPanel.add(buttonPanel, BorderLayout.CENTER);
        firstPanel.add(categoryPanel, BorderLayout.NORTH);
        categoryPanel.add(chooseCategory, BorderLayout.SOUTH);
        buttonPanel.add(emptyButton);

        buttonPanel.add(subject1);
        buttonPanel.add(subject2);
        buttonPanel.add(subject3);
        buttonPanel.add(subject4);

        //lägger till action-listener för de olika knapparna
        subject1.addActionListener(this);
        subject2.addActionListener(this);
        subject3.addActionListener(this);
        subject4.addActionListener(this);

        //Anpassning av komponenter och utseende
        emptyButton.setContentAreaFilled(false);
        emptyButton.setBorderPainted(false);
        categoryPanel.setPreferredSize(new Dimension(500, 200));
        chooseCategory.setFont(new Font("Arial", Font.BOLD, 30));
        chooseCategory.setHorizontalAlignment(SwingConstants.CENTER);
        chooseCategory.setVerticalAlignment(SwingConstants.CENTER);
        categoryPanel.setOpaque(false);
        buttonPanel.setOpaque(false);

        emptyButton.setPreferredSize(new Dimension(500, 225));
        subject1.setPreferredSize(new Dimension(225, 150));
        subject2.setPreferredSize(new Dimension(225, 150));
        subject3.setPreferredSize(new Dimension(225, 150));
        subject4.setPreferredSize(new Dimension(225, 150));

        gameFrame.setVisible(true);
    }

    public void RoundStart() {
        //Gör den tidigare JFrame osynlig
        gameFrame.setVisible(false);

        // Setting the roundFrame
        roundFrame.setTitle("Quizkampen");
        roundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        roundFrame.setSize(frameWidth, frameHeight);
        roundFrame.setLocationRelativeTo(null);

        //panels layout
        mainPanel.setLayout(new BorderLayout());
        thePanelForButton.setLayout(new FlowLayout());
        questionPanel.setLayout(new BorderLayout());

        //tillägning av komponenter
        roundFrame.add(mainPanel);
        mainPanel.add(questionPanel, BorderLayout.NORTH);
        mainPanel.add(thePanelForButton, BorderLayout.CENTER);
        questionPanel.add(questionLabel, BorderLayout.SOUTH);
        thePanelForButton.add(theEmptyButton);
        thePanelForButton.add(question1);
        thePanelForButton.add(question2);
        thePanelForButton.add(question3);
        thePanelForButton.add(question4);

        //anpassning av komponenter
        questionPanel.setOpaque(false);
        thePanelForButton.setOpaque(false);
        questionPanel.setPreferredSize(new Dimension(500, 200));
        questionLabel.setFont(new Font("Arial", Font.BOLD, 30));
        theEmptyButton.setContentAreaFilled(false);
        theEmptyButton.setBorderPainted(false);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roundFrame.setMaximumSize(new Dimension(frameWidth, frameHeight));
        roundFrame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        roundFrame.setResizable(false);

        theEmptyButton.setPreferredSize(new Dimension(500, 225));
        question1.setPreferredSize(new Dimension(225, 150));
        question2.setPreferredSize(new Dimension(225, 150));
        question3.setPreferredSize(new Dimension(225, 150));
        question4.setPreferredSize(new Dimension(225, 150));

        //Tilläggning av action-listener till knapparna
        question1.addActionListener(this);
        question2.addActionListener(this);
        question3.addActionListener(this);
        question4.addActionListener(this);

        roundFrame.setVisible(true);
    }

    // skapa frame för frågorna och för är waiting player väntar
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
        if (e.getSource() == subject1) {
            RoundStart();
            //return chosen subject and get the questions
        } else if (e.getSource() == subject2) {
            RoundStart();
            //return chosen subject and get the questions
        } else if (e.getSource() == subject3) {
            RoundStart();
            //return chosen subject and get the questions
        } else if (e.getSource() == subject4) {
            RoundStart();
            //return chosen subject and get the questions
        }
    }
}
