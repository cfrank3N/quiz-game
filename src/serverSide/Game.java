package serverSide;

import database.QuestionRepository;
import enums.ESubject;
import packettosend.Pack;
import enums.GameState;
import enums.States;
import shared.*;

import java.io.IOException;
import java.util.*;

import static enums.GameState.*;


public class Game extends Thread {
    private final Player p1;
    private final Player p2;
    private Player currentPlayer;
    private final QuestionRepository db = new QuestionRepository();
    private GameState status = SETUP;


    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;

    }

    public void run() {

        try {
            Object messageFromClient;
            p1.sendToClient(new Pack(States.WELCOME, null)); //Acknowledged
            p2.sendToClient(new Pack(States.WELCOME, null));

            //Sets opponent as each other.
            p1.setOpponent(p2);
            p2.setOpponent(p1);

            while (status != FINISHED) {
                determineAction();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void determineAction() {
        try {
            switch (status) {
                case SETUP:
                    //Retrieve p1 user and send player dto to p2
                    currentPlayer.sendToClient(new Pack(States.SEND_USER, null));
                    User user1 = (User) ((Pack) currentPlayer.receiveFromClient()).object();
                    currentPlayer.setUser(user1);

//                    currentPlayer.getOpponent().sendToClient(new Pack(States.PLAYER_DTO, new PlayerDTO(user.getUsername(), user.getAvatarPath())));
                    //Retrieve p2 user and send player dto to p1
                    currentPlayer.getOpponent().sendToClient(new Pack(States.SEND_USER, null));
                    User user2 = (User) ((Pack) currentPlayer.getOpponent().receiveFromClient()).object();
                    currentPlayer.getOpponent().setUser(user2);

                    currentPlayer.sendToClient(new Pack(States.PLAYER_DTO, new PlayerDTO(user2.getUsername(), user2.getAvatarPath())));
                    currentPlayer.getOpponent().sendToClient(new Pack(States.PLAYER_DTO, new PlayerDTO(user1.getUsername(), user1.getAvatarPath())));

                    status = FIRST_STEP;
                    break;
                case FIRST_STEP:
                    String roundsProperty = Utility.properties.getProperty("rounds");
                    int rounds = Integer.parseInt(roundsProperty);

                    for (int i = 0; i < rounds; i++) {
                        currentPlayer.getOpponent().sendToClient(new Pack(States.WAIT, "Wait for player"));
                        currentPlayer.sendToClient(new Pack(States.CHOOSE_CATEGORY, generateCategory()));
                        ESubject subject = (ESubject) (((Pack) currentPlayer.receiveFromClient()).object()); //Wait for subject

//                        List<Question> currentQuestions = db.threebySubject(subject); //Pick a question
                        int nrOfQuestions = Integer.parseInt(Utility.properties.getProperty("questions"));
                        List<Question> currentQuestions = db.nrOfQuestionsBySubject(subject, nrOfQuestions);
                        //TODO WORKS GETTING DESIRED AMOUNT OF QUESTIONS BUT BREAKS IN CLIENT GUI BECAUSE USING SWITCH CASE SO ROUND 1 DOES NOT APPEAR

                        loopQAndA(currentQuestions);
                        currentPlayer.sendToClient(new Pack(States.WAIT, "Wait for player"));
                        currentPlayer.getOut().reset();
                        currentPlayer.sendToClient(new Pack(States.CURRENT_SCORE, new Scoreboard(currentPlayer.getResult(), currentPlayer.getOpponent().getResult(), i)));
                        currentPlayer = currentPlayer.getOpponent();//Switch to other participant
                        System.out.println("switched player");
                        loopQAndA(currentQuestions);
                        currentPlayer.getOut().reset();
                        currentPlayer.sendToClient(new Pack(States.CURRENT_SCORE, new Scoreboard(currentPlayer.getResult(), currentPlayer.getOpponent().getResult(), i)));
                        currentPlayer.getOpponent().getOut().reset();
                        currentPlayer.getOpponent().sendToClient(new Pack(States.CURRENT_SCORE, new Scoreboard(currentPlayer.getOpponent().getResult(), currentPlayer.getResult(), i)));


//                        String scoreUpdate = "Current scores: " +
//                            currentPlayer.getUser().getUsername() + " (" + currentPlayer.getPoint() + ") - " +
//                            currentPlayer.getOpponent().getUser().getUsername() + " (" + currentPlayer.getOpponent().getPoint() + ")";
//                            currentPlayer.sendToClient(new Pack(States.WAIT, scoreUpdate));
//                            currentPlayer.getOpponent().sendToClient(new Pack(States.WAIT, scoreUpdate));

                        //Tell players to update views
                        //ScoreboardDTO scoreboardDTOp1 = new ScoreboardDTO(currentPlayer.getPoint(), currentPlayer.getOpponent().getPoint());
                        //ScoreboardDTO scoreboardDTOp2 = new ScoreboardDTO(currentPlayer.getOpponent().getPoint(), currentPlayer.getPoint());
                        //currentPlayer.sendToClient(new Pack(States.SCOREBOARD_DTO, scoreboardDTOp1));
                        //currentPlayer.getOpponent().sendToClient(new Pack(States.SCOREBOARD_DTO, scoreboardDTOp2));
                    }
                    status = SECOND_STEP;
                    break;
                case SECOND_STEP:

                    int p1Points = p1.getPoint();
                    int p2Points = p2.getPoint();
                    String winnerMessage;
                    String loserMessage;
                    if (p1Points > p2Points) {
                        winnerMessage = "YOU WIN! Total points: " + p1Points;
                        loserMessage = "YOU LOSE! Total points: " + p2Points;
                        p1.getOut().reset();
                        p1.sendToClient(new Pack(States.DETERMINE_WINNER, winnerMessage));
                        p2.getOut().reset();
                        p2.sendToClient(new Pack(States.DETERMINE_WINNER, loserMessage));
                    } else if (p2Points > p1Points) {
                        winnerMessage = "YOU WIN! Total points: " + p2Points;
                        loserMessage = "YOU LOSE! Total points: " + p1Points;
                        p1.getOut().reset();
                        p1.sendToClient(new Pack(States.DETERMINE_WINNER, loserMessage));
                        p2.getOut().reset();
                        p2.sendToClient(new Pack(States.DETERMINE_WINNER, winnerMessage));
                    } else {
                        winnerMessage = "Its a tie! Both players have " + p1Points + " point!";
                        p1.getOut().reset();
                        p1.sendToClient(new Pack(States.DETERMINE_WINNER, winnerMessage));
                        p2.getOut().reset();
                        p2.sendToClient(new Pack(States.DETERMINE_WINNER, winnerMessage));
                    }


                    status = GameState.FINISHED;
                    break;
                case FINISHED:

                    break;

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isCorrectAnswer(Question q, String s) {
        return q.getCorrectAnswer().equalsIgnoreCase(s);
    }

    public void loopQAndA(List <Question> questions) throws IOException, ClassNotFoundException {
        for (Question q : questions) {
//            System.out.println("Inside q loop");
            currentPlayer.sendToClient(new Pack(States.SEND_ANSWER, q)); //Ask for answer from p1
//            System.out.println("Send question");
            String answer = (String) ((Pack) currentPlayer.receiveFromClient()).object();
            if (isCorrectAnswer(q, answer)) {
                currentPlayer.incrementPoint();
                currentPlayer.getResult().add(1);
                currentPlayer.sendToClient(new Pack(States.SEND_CORRECT_ANSWER, "Correct"));
            } else {
                currentPlayer.getResult().add(0);
                currentPlayer.sendToClient(new Pack(States.SEND_CORRECT_ANSWER, "Incorrect"));
            }
        }
    }

    public List<ESubject> generateCategory(){

        List<ESubject>categories=new ArrayList<>(List.of(ESubject.values()));
        Collections.shuffle(categories);
        return new ArrayList<>(categories.subList(0, 3));
    }
}
