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
        Scanner scan = new Scanner(System.in);
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


//                getWelcomeFrame();
                break;

            case CHOOSE_CATEGORY:
                //Ta vår frame och måla den med rätt conent
//                frame.add();
//                paincategoryframe

                List<ESubject> catagories = (List<ESubject>)packFromServer.object();
                getCategoryFrame(catagories, out);

//                System.out.println(catagories);
//                String chosenSubject = scan.nextLine();
//                for (ESubject s : catagories) {
//                    if (chosenSubject.equalsIgnoreCase(s.getNameText())) {
//                        out.writeObject(new Pack(States.CATEGORY, s));
//                    }
//                }

                break;
            case WAIT:
                System.out.println(packFromServer.object());
                getWaitFrame();
                break;

            case SEND_CORRECT_ANSWER:
                System.out.println(packFromServer.object());
                break;


            case SEND_ANSWER:
                //access question frame thingy
                Question question = (Question) packFromServer.object();
                getQuestionFrame(question, out);

//                System.out.println("Your turn");
//                Question question = (Question) packFromServer.object();
//                System.out.println(question.getQuestion());
//                System.out.println(question.getSubjectQuestions());
//                System.out.print("Answer: ");
//                String input = scan.nextLine();
//                System.out.println();
//                out.writeObject(new Pack(States.GUESS, input));
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
//        frame.removeAll();

        panel.removeAll();
        panel.add(new JLabel("Waiting"));

        panel.revalidate();
        panel.repaint();
    }

    public void getWelcomeFrame() {
        frame = new JFrame();
        frame.setSize(500,800);
//        frame.setMaximumSize(new Dimension(frameWidth, frameHeight));
//        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
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
        //Startar en ny frame för spelet.

//        frame.setMaximumSize(new Dimension(frameWidth, frameHeight));
//        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
//        if (panel != null) {
//            panel.removeAll();
//        }
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

            panel.add(b);

            b.addActionListener(e -> {
                JButton button = (JButton) e.getSource();

                try {
                    out.writeObject(new Pack(States.GUESS, button.getText()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            counter++;
        }


        panel.revalidate();
        panel.repaint();
    }

    public void getCategoryFrame(List<ESubject> subjects, ObjectOutputStream out) {
//        frame = new JFrame();
//        //Startar en ny frame för spelet.
//        frame.setSize(500,800);
////        frame.setMaximumSize(new Dimension(frameWidth, frameHeight));
////        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
//        frame.setResizable(false);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setTitle("the quiz game");
//        frame.setLocationRelativeTo(null);
//        panel = new JPanel(new GridLayout(4,4));

//        if (panel != null) {
//            panel.removeAll();
//        }
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
//        frame.add(panel);
//        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Client client = new Client();

//        JFrame
//        String newUserName = JOptionPane.showInputDialog("What is your username?");
//        User user = new User(newUserName, "", "", 0, "src/avatars/basic_boy.png");
//        JButton button = new JButton();
//        button.addActionListener(e -> client.startClient());

        client.startClient();
    }
}