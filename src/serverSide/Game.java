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

import static enums.ESubject.SUBJECT3;
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
                    p1.sendToClient(new Pack(States.SEND_USER, null));
                    User user = (User) ((Pack) p1.receiveFromClient()).object();

                    p2.sendToClient(new Pack(States.PLAYER_DTO, new PlayerDTO(user.getUsername(), user.getAvatarPath())));
                    //Retrieve p2 user and send player dto to p1
                    p2.sendToClient(new Pack(States.SEND_USER, null));
                    user = (User) ((Pack) p2.receiveFromClient()).object();

                    p1.sendToClient(new Pack(States.PLAYER_DTO, new PlayerDTO(user.getUsername(), user.getAvatarPath())));

                    status = FIRST_STEP;
                    break;
                case FIRST_STEP:
                    currentPlayer.getOpponent().sendToClient(new Pack(States.WAIT, "Waiting for opponent"));
                    currentPlayer.sendToClient(new Pack(States.CHOOSE_CATEGORY, null));
                    ESubject subject = (ESubject) (((Pack) currentPlayer.receiveFromClient()).object()); //Wait for subject

                    Question q = db.oneBySubject(subject); //Pick a question

                    currentPlayer.sendToClient(new Pack(States.SEND_ANSWER, q)); //Ask for answer from p1
                    String p1Answer = (String) ((Pack) currentPlayer.receiveFromClient()).object();

                    currentPlayer = currentPlayer.getOpponent(); //Switch to other participant

                    currentPlayer.getOpponent().sendToClient(new Pack(States.WAIT, "Waiting for opponent"));
                    currentPlayer.sendToClient(new Pack(States.SEND_ANSWER, q)); //Ask for answer from p2
                    String p2Answer = (String) ((Pack) currentPlayer.receiveFromClient()).object();

                    //Update scores
                    if (isCorrectAnswer(q, p1Answer)) {
                        p1.incrementPoint();
                    }
                    if (isCorrectAnswer(q, p2Answer)) {
                        p2.incrementPoint();
                    }

                    //Tell players to update views
                    ScoreboardDTO scoreboardDTOp1 = new ScoreboardDTO(p1.getPoint(), p2.getPoint());
                    ScoreboardDTO scoreboardDTOp2 = new ScoreboardDTO(p2.getPoint(), p1.getPoint());
                    p1.sendToClient(new Pack(States.SCOREBOARD_DTO, scoreboardDTOp1));
                    p2.sendToClient(new Pack(States.SCOREBOARD_DTO, scoreboardDTOp2));

                    status = SECOND_STEP;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isCorrectAnswer(Question q, String s) {
        return q.getCorrectAnswer().equals(s);
    }
}
