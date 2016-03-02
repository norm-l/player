package player.logic;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class TrackPlayer {

    private MediaPlayer player;
    private Media track;
    private String filepath;

    private Duration duration;

    public TrackPlayer(String filepath) {
        this.filepath = filepath;
        track = new Media(filepath);
        player = new MediaPlayer(track);

        player.setOnReady(() -> {
            duration = track.getDuration();
            System.out.println("Duration: " + duration);
        });
    }

    public void playSong() {
        System.out.println("Playing song");
        player.play();
    }

    public void pauseSong() {
        System.out.println("Pausing song");
        player.pause();
    }

    public void stopSong() {
        System.out.println("Stopping song");
        player.stop();
    }

    public Status getStatus() {
        return player.getStatus();
    }

    public Duration getDuration() {
        return duration;
    }

    public Duration getCurrentTime() {
        return player.getCurrentTime();
    }

    public Duration getStartTime() {
        return player.getStartTime();
    }

    public void setSeek(Duration duration) {
        player.seek(duration);
    }

    public Media getMedia() {
        return player.getMedia();
    }

    public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
        return player.currentTimeProperty();
    }

    public Duration getTotalDuration() {
        return player.getTotalDuration();
    }
}
