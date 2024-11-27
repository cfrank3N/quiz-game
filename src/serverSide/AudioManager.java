package serverSide;

public class AudioManager {

    private Audio audioThemeSong;
    private Audio audioMoveSucc;
    private Audio audioMoveFail;
    private Audio audioCleared;

    public AudioManager() {
        try {
            audioThemeSong = new Audio("quizThemeSong.wav", true);
            audioMoveSucc = new Audio("move_succ.wav", false);
            audioMoveFail = new Audio("move_fail.wav",false);
//            audioCleared = new Audio("audio_cleared.wav",false);
//            audioThemeSong.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playThemeSong() {audioThemeSong.play();}
    public void stopThemeSong() {audioThemeSong.stop();}
    public void playAudioMoveSucc() {audioMoveSucc.play();}
    public void playAudioMoveFail() {audioMoveFail.play();}
    public void playAudioCleared() {audioCleared.play();}
    public void toggleThemeMute() {audioThemeSong.mute();}
}