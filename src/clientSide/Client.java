package clientSide;

import enums.ESubject;
import enums.States;
import packettosend.Pack;
import serverSide.GameLook;
import shared.Question;
import shared.PlayerDTO;
import shared.ScoreboardDTO;
import shared.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private Board board;
    private User user;
    private JFrame frame;
    private List<JButton> buttons;
    private JPanel panel = new JPanel();

    public void startClient() {
//        String newUserName = JOptionPane.showInputDialog("What is your username?");
        user = new User("newUserName", "", "", 0, "src/avatars/basic_boy.png");
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

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void determineAction(Object fromServer, ObjectOutputStream out) throws IOException {
        Pack packFromServer = (Pack) fromServer;
        switch (packFromServer.header()) {
            case WELCOME:
                System.out.println("Welcome to the game.");
                getWelcomeFrame();
                break;
            case SEND_USER:
                out.writeObject(new Pack(States.PLAYER_DTO, user));
                break;
            case PLAYER_DTO:
                PlayerDTO opponentInformation = (PlayerDTO) packFromServer.object();

                Player me = new Player(0, user.getUsername(), new ImageIcon(user.getAvatarPath()));
                Player opponent = new Player(0, opponentInformation.name(), new ImageIcon(opponentInformation.avatarPath()));

                board = new Board(me, opponent);
                break;

            case CHOOSE_CATEGORY:
                List<ESubject> catagories = (List<ESubject>)packFromServer.object();
                getCategoryFrame(catagories, out);

                break;
            case WAIT:
                System.out.println(packFromServer.object());
                getWaitFrame();
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
            default:
                break;
        }
    }



    public void getWaitFrame() {
        panel.removeAll();
        panel.add(new JLabel("Waiting"));

        panel.revalidate();
        panel.repaint();
    }

    public void getWelcomeFrame() {
        frame = new JFrame();
        frame.setSize(500,800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("the quiz game");
        frame.setLocationRelativeTo(null);
        panel = new JPanel(new GridLayout(4,4));
        frame.add(panel);
        panel.add(new JLabel("Hello"));

        frame.setVisible(true);
    }

    public void getQuestionFrame(Question question, ObjectOutputStream out) {
        panel.removeAll();

        for(JButton currentButton: buttons) { //Clean up previous action listeners
            for( ActionListener al : currentButton.getActionListeners() ) {
                currentButton.removeActionListener( al );
            }
        }

        int counter = 0;
        for (String q : question.getSubjectQuestions()) {
            JButton b = buttons.get(counter);
            b.setText(q);
            b.setOpaque(false);
//            b.setBackground(Color.BLUE);

            panel.add(b);

            b.addActionListener(e -> {
                JButton button = (JButton) e.getSource();

                button.setBackground(Color.BLUE);

                try {
                    if (button.getText().equalsIgnoreCase(question.getCorrectAnswer())) {
                        System.out.println("Right");
                        button.setBackground(Color.GREEN);
//                        button.setForeground(Color.GREEN);
                    } else {
                        System.out.println("sWrong");
                        button.setBackground(Color.RED);
//                        button.setForeground(Color.RED);
                    }

                    Thread.sleep(5000);
                    button.setBackground(Color.LIGHT_GRAY);

                    out.writeObject(new Pack(States.GUESS, button.getText()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });

            counter++;
        }


        panel.revalidate();
        panel.repaint();
    }

    public void getCategoryFrame(List<ESubject> subjects, ObjectOutputStream out) {
        panel.removeAll();

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
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }
}