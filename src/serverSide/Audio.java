package serverSide;

import javax.sound.sampled.*;
import java.io.InputStream;

public class Audio implements LineListener {

    private Clip clip;
    private boolean isMuted = false;
    private int songPosition = 0;
    private final boolean loopAudio;

    public Audio(String fileName, boolean loopAudio) throws UnsupportedAudioFileException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("audio/" + fileName);
        if (inputStream == null) {
            throw new UnsupportedAudioFileException("Audio file " + fileName + " not found.");
        }

        this.loopAudio = loopAudio;

        try (AudioInputStream originalStream = AudioSystem.getAudioInputStream(inputStream)) {
            AudioFormat baseFormat = originalStream.getFormat();
            AudioFormat compatibleFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(), false);

            // Convert the audio stream to a compatible format
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(compatibleFormat, originalStream);

            DataLine.Info info = new DataLine.Info(Clip.class, compatibleFormat);
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(convertedStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Plays sounds from beginning (frame 0)
            if (loopAudio) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } else {
                clip.start();
            }
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void mute() {
        if (isMuted) {
            clip.setFramePosition(songPosition); // Sets song position
            if (loopAudio) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        } else {
            songPosition = clip.getFramePosition();
            clip.stop();
        }
        isMuted = !isMuted;
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            clip.stop();
        }
    }
}