package clientSide;

import enums.ESubject;
import enums.States;
import packettosend.Pack;
import serverSide.AudioManager;
import serverSide.GameLook;
import shared.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final AudioManager am = new AudioManager();
    private Board board;
    private User user;
    private JFrame frame;
    private List<JButton> buttons;
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JLabel questionText = new JLabel();
    private JLabel round1 = new JLabel();
    private JLabel round2 = new JLabel();
    private JLabel round3 = new JLabel();
    private JLabel round4 = new JLabel();
    private JLabel round5 = new JLabel();
    private JLabel round6 = new JLabel();

    public Client() {
//        user = new User("Guest","","",0, "src/images/avatars/1.png");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }

    public void startClient() {
//        String newUserName = JOptionPane.showInputDialog("What is your username?");
//        frame = new JFrame();
        buttons = new ArrayList<>(List.of(new JButton(""), new JButton(""), new JButton(""), new JButton("")));

        //Initializes input and outputstreams
        try (Socket socket = new Socket("127.0.0.1", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {

            Object messageFromServer;

            while (true) {
                messageFromServer = in.readObject();
                determineAction(messageFromServer, out);
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void determineAction(Object fromServer, ObjectOutputStream out) throws IOException, InterruptedException {
        Pack packFromServer = (Pack) fromServer;
        switch (packFromServer.header()) {
            case WELCOME:
                System.out.println("Welcome to the game.");
                getWelcomeFrame();
                break;
            case SEND_USER:
//                out.writeObject(new Pack(States.PLAYER_DTO, user));
                getAvatarFrame(out);
                break;
            case PLAYER_DTO:
                PlayerDTO opponentInformation = (PlayerDTO) packFromServer.object();

                System.out.println("Set my avatar path as: " + user.getAvatarPath());
                Player me = new Player(0, user.getUsername(), new ImageIcon(user.getAvatarPath()));
                Player opponent = new Player(0, opponentInformation.name(), new ImageIcon(opponentInformation.avatarPath()));

                board = new Board(me, opponent);
                break;

            case CHOOSE_CATEGORY:
                List<ESubject> catagories = (List<ESubject>) packFromServer.object();
                getCategoryFrame(catagories, out);

                break;
            case WAIT:
                System.out.println(packFromServer.object());
                getWaitFrame();
                break;

            case CURRENT_SCORE:
//                System.out.println("This is your current score: ");
                Scoreboard score = (Scoreboard) packFromServer.object();
//
//                System.out.println(score);

                getScoreFrame(score);
                break;

            case SEND_CORRECT_ANSWER:
                System.out.println(packFromServer.object());
                break;


            case SEND_ANSWER:
                Question question = (Question) packFromServer.object();
                getQuestionFrame(question, out);

                break;
            case SCOREBOARD_DTO:
                //Update
                ScoreboardDTO scoreboardDTO = (ScoreboardDTO) packFromServer.object();
                board.getMe().setPoints(scoreboardDTO.you());
                board.getOpponent().setPoints(scoreboardDTO.opponent());

                //View
                System.out.println("Current score: ");
                System.out.println("You: " + board.getMe().getPoints());
                System.out.println("Opponent: " + board.getOpponent().getPoints());
                break;
            case DETERMINE_WINNER:
                String message = (String) packFromServer.object();
                getWinScreen(message);
                break;
            default:
                break;
        }
    }

    public void getAvatarFrame(ObjectOutputStream out) {
        user = new User("Guest","","",0, "src/images/images/avatars/1.png");
        panel.removeAll();
        panel2.removeAll();

        JLabel l = new JLabel("Username");
        JTextField usernameField = new JTextField();
        JButton setName = new JButton("Set");
        JLabel l2 = new JLabel("Pick Avatar");
        JPanel avatarContainer = new JPanel(new GridLayout(3,3,2,2));

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of("src/images/avatars"))){
            stream.forEach(p -> {
                JButton b = new JButton(new ImageIcon(p.toString()));
                b.setName(p.toString());

                b.addActionListener(e -> {
                    JButton button = (JButton) e.getSource();
                    user.setAvatarPath(button.getName());
                });

                avatarContainer.add(b);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        setName.addActionListener(e -> {
            user.setUsername(usernameField.getText());
        });


        JButton finish = new JButton("Finish");
        finish.addActionListener(e -> {
            try {
                out.writeObject(new Pack(States.USER, user));
                panel.setLayout(new GridLayout(4,4));
                getWaitFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        panel.setLayout(null);

        l.setBounds(215,70,200,30);
        usernameField.setBounds(150,100,200,30);
        setName.setBounds(200,150,100,30);
        l2.setBounds(215,215,200,30);
        avatarContainer.setBounds(70,250,360,360);
        finish.setBounds(200,650,100,30);

        panel.add(l);
        panel.add(usernameField);
        panel.add(setName);
        panel.add(l2);
        panel.add(avatarContainer);
        panel.add(finish);

        panel2.revalidate();
        panel2.repaint();
        panel.revalidate();
        panel.repaint();
    }

    public void getWaitFrame() {
        panel.removeAll();
        panel2.removeAll();

        panel.setLayout(null);

        JLabel label = new JLabel("Waiting");
        label.setBounds(220,10,100,100);
        panel.add(label);

        panel2.revalidate();
        panel2.repaint();
        panel.revalidate();
        panel.repaint();
    }

    public void getWinScreen(String message) {
        panel.removeAll();
        panel2.removeAll();

        panel.add(new JLabel(message));
        panel2.add(new JLabel(message));

        panel2.revalidate();
        panel2.repaint();
        panel.revalidate();
        panel.repaint();
    }

    public void getWelcomeFrame() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("The quiz game");
        frame.setLocationRelativeTo(null);
        panel = new JPanel(new GridLayout(4, 4));
        frame.add(panel, BorderLayout.CENTER); // ha frågorna i center? Jlabel norråt för frågoan i sig.
        panel.add(new JLabel("panel1"));
        frame.add(panel2, BorderLayout.NORTH);
        panel2.add(new JLabel("panel2"));

        frame.setVisible(true);
    }

    public void getQuestionFrame(Question question, ObjectOutputStream out) {

        for (JButton currentButton : buttons) { //Clean up previous action listeners
            for (ActionListener al : currentButton.getActionListeners()) {
                currentButton.removeActionListener(al);
            }
        }

        panel.removeAll();
        panel2.removeAll();
        panel.setLayout(new GridLayout(4, 4));
        panel2.add(new JLabel(question.getQuestion()));

        int counter = 0;
//        JButton correct = new JButton();
        Collections.shuffle(question.getSubjectQuestions());
        for (String q : question.getSubjectQuestions()) {
            JButton b = buttons.get(counter);
            b.setText(q);
            // buttons.get(counter).setOpaque(false);

            buttons.get(counter).setBackground(Color.white);
            panel.add(b);

            b.addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                button.setOpaque(true);

                // Kontrollera svaret och ändra färg
                boolean isCorrect = button.getText().equalsIgnoreCase(question.getCorrectAnswer());
                button.setBackground(isCorrect ? Color.GREEN : Color.RED);

                // plays sounds depending on right or wrong answer
                if (isCorrect) {
                    am.playAudioMoveSucc();
                } else {
                    am.playAudioMoveFail();
                }

                // Skicka resultatet efter en kort fördröjning
                Timer timer = new Timer(700, evt -> {
                    try {
                        out.writeObject(new Pack(States.GUESS, button.getText()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    button.setBackground(Color.white); // TODO fullösning??
                });
                timer.setRepeats(false);
                timer.start();
            });
            counter++;
        }
        panel.revalidate();
        panel.repaint();
        panel2.revalidate();
        panel2.repaint();
    }

    public void getScoreFrame(Scoreboard score) throws InterruptedException {
        panel.removeAll();
        panel2.removeAll();
//        panel2.add(new JLabel("Scoreboard"));

//        JPanel playerInfoPanel = new JPanel(new GridLayout(2,3));
//        panel2.add(playerInfoPanel);

        panel.setLayout(null);

        System.out.println("My name: " + board.getMe().getName());
        System.out.println("My avatar: " + board.getMe().getAvatar().getDescription());

        JLabel clientName = new JLabel(board.getMe().getName());
        JLabel clientAvatar = new JLabel(board.getMe().getAvatar());
        JLabel opponentName = new JLabel(board.getOpponent().getName());
        JLabel opponentAvatar = new JLabel(board.getOpponent().getAvatar());

        clientName.setBounds(20,160,120,30);
        clientAvatar.setBounds(20,30,120,120);
        opponentName.setBounds(360,160,120,30);
        opponentAvatar.setBounds(360,30,120,120);

        panel.add(clientName);
        panel.add(opponentName);
        panel.add(clientAvatar);
        panel.add(opponentAvatar);

        switch (score.getMe().size()) {
            case 3:
                int myScore = 0;
                int opponentScore = 0;
                for (int i : score.getMe()) {
                    myScore += i;
                }
                for (int j : score.getOpponent()) {
                    opponentScore += j;
                }
                round1 = new JLabel("Round 1: " + myScore + " - " + opponentScore);
                round1.setBounds(190,250,120,30);
                panel.add(round1);
                break;
            case 6:
                myScore = 0;
                opponentScore = 0;
                for (int i : score.getMe()) {
                    myScore += i;
                }
                for (int j : score.getOpponent()) {
                    opponentScore += j;
                }
                round2 = new JLabel("Round 2: " + myScore + " - " + opponentScore);
                round2.setBounds(190,250,120,30);
                panel.add(round1);
                panel.add(round2);
                break;
            case 9:
                myScore = 0;
                opponentScore = 0;
                for (int i : score.getMe()) {
                    myScore += i;
                }
                for (int j : score.getOpponent()) {
                    opponentScore += j;
                }
                round3 = new JLabel("Round 3: " + myScore + " - " + opponentScore);
                round3.setBounds(190,290,120,30);
                panel.add(round1);
                panel.add(round2);
                panel.add(round3);
                break;
            case 12:
                myScore = 0;
                opponentScore = 0;
                for (int i : score.getMe()) {
                    myScore += i;
                }
                for (int j : score.getOpponent()) {
                    opponentScore += j;
                }
                round4 = new JLabel("Round 4: " + myScore + " - " + opponentScore);
                round4.setBounds(190,290,120,30);
                panel.add(round1);
                panel.add(round2);
                panel.add(round3);
                panel.add(round4);
                break;
            case 15:
                myScore = 0;
                opponentScore = 0;
                for (int i : score.getMe()) {
                    myScore += i;
                }
                for (int j : score.getOpponent()) {
                    opponentScore += j;
                }
                round5 = new JLabel("Round 5: " + myScore + " - " + opponentScore);
                round5.setBounds(190,330,120,30);
                panel.add(round1);
                panel.add(round2);
                panel.add(round3);
                panel.add(round4);
                panel.add(round5);
                break;
            case 18:
                myScore = 0;
                opponentScore = 0;
                for (int i : score.getMe()) {
                    myScore += i;
                }
                for (int j : score.getOpponent()) {
                    opponentScore += j;
                }
                round6 = new JLabel("Round 6: " + myScore + " - " + opponentScore);
                round3.setBounds(190,330,120,30);
                panel.add(round1);
                panel.add(round2);
                panel.add(round3);
                panel.add(round4);
                panel.add(round5);
                panel.add(round6);
                break;
        }
        panel.revalidate();
        panel.repaint();
        panel2.revalidate();
        panel2.repaint();

        Thread.sleep(5000);

    }

    public void getCategoryFrame(List<ESubject> subjects, ObjectOutputStream out) {

        for (JButton currentButton : buttons) { //Clean up previous action listeners
            for (ActionListener al : currentButton.getActionListeners()) {
                currentButton.removeActionListener(al);
            }
        }

        panel.removeAll();
        panel2.removeAll();

        panel.setLayout(new GridLayout(4, 4));

        panel2.add(new JLabel("Choose Subject"));
        panel2.add(questionText, BorderLayout.NORTH);

        int counter = 0;
        for (ESubject subject : subjects) {
            JButton b = buttons.get(counter);
            b.setText(subject.getNameText());

            panel.add(b);

            b.addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                //hitta motsvarande esubject
                for (ESubject s : subjects) {
                    if (button.getText().equalsIgnoreCase(s.getNameText())) {
                        try {
                            out.writeObject(new Pack(States.CATEGORY, s));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });

            counter++;
        }
        panel.revalidate();
        panel.repaint();
        panel2.revalidate();
        panel2.repaint();
    }
}