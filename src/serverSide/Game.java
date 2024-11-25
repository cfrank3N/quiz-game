package serverSide;

import database.QuestionRepository;
import enums.ESubject;
import packettosend.Pack;
import enums.GameState;
import enums.States;
import shared.PlayerDTO;
import shared.Question;
import shared.ScoreboardDTO;
import shared.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        currentPlayer = p1;
    }

    public void run() {

        try {
            Object messageFromClient;
            p1.sendToClient(new Pack(States.WELCOME, null)); //Acknowledged
            p2.sendToClient(new Pack(States.WELCOME, null));

            //Sets opponent as each other.
            p1.setOpponent(p2);
            p2.setOpponent(p1);

            while (true) {
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
                    User user = (User) ((Pack) currentPlayer.receiveFromClient()).object();

                    currentPlayer.getOpponent().sendToClient(new Pack(States.PLAYER_DTO, new PlayerDTO(user.getUsername(), user.getAvatarPath())));
                    //Retrieve p2 user and send player dto to p1
                    currentPlayer.getOpponent().sendToClient(new Pack(States.SEND_USER, null));
                    user = (User) ((Pack) currentPlayer.getOpponent().receiveFromClient()).object();

                    currentPlayer.sendToClient(new Pack(States.PLAYER_DTO, new PlayerDTO(user.getUsername(), user.getAvatarPath())));

                    status = FIRST_STEP;
                    break;
                case FIRST_STEP:
                    for (int i = 0; i < 3; i++) {
                        currentPlayer.getOpponent().sendToClient(new Pack(States.WAIT, "Waiting for opponent"));
                        currentPlayer.sendToClient(new Pack(States.CHOOSE_CATEGORY, generateCategory()));
                        ESubject subject = (ESubject) (((Pack) currentPlayer.receiveFromClient()).object()); //Wait for subject

                        List<Question> currentQuestions = db.threebySubject(subject); //Pick a question

                        loopQAndA(currentQuestions);
                        currentPlayer.sendToClient(new Pack(States.WAIT, "Waiting for opponent"));
                        currentPlayer = currentPlayer.getOpponent();//Switch to other participant
                        loopQAndA(currentQuestions);

                        //Tell players to update views
                        ScoreboardDTO scoreboardDTOp1 = new ScoreboardDTO(currentPlayer.getPoint(), currentPlayer.getOpponent().getPoint());
                        ScoreboardDTO scoreboardDTOp2 = new ScoreboardDTO(currentPlayer.getOpponent().getPoint(), currentPlayer.getPoint());
                        currentPlayer.sendToClient(new Pack(States.SCOREBOARD_DTO, scoreboardDTOp1));
                        currentPlayer.getOpponent().sendToClient(new Pack(States.SCOREBOARD_DTO, scoreboardDTOp2));

                    }
                    status = SECOND_STEP;
                    break;
                case SECOND_STEP:

                default:
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

            currentPlayer.sendToClient(new Pack(States.SEND_ANSWER, q)); //Ask for answer from p1
            String answer = (String) ((Pack) currentPlayer.receiveFromClient()).object();
            if (isCorrectAnswer(q, answer)) {
                currentPlayer.incrementPoint();
                currentPlayer.sendToClient(new Pack(States.SEND_CORRECT_ANSWER, "Correct"));
            } else {
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
