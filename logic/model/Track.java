package player.logic.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Track {

    public Track(String trackTitle, String trackArtist, String trackGenre, String runTime, String filePath) {
        setTrackTitle(trackTitle);
        setTrackArtist(trackArtist);
        setTrackGenre(trackGenre);
        setRunTime(runTime);
        setFilePath(filePath);
    }
    // -----------------------------------------------------
    private final StringProperty trackTitle = new SimpleStringProperty();

    public final StringProperty trackTitleProperty() {
        return this.trackTitle;
    }

    public final String getTrackTitle() {
        return this.trackTitleProperty().get();
    }

    public final void setTrackTitle(final String trackTitle) {
        this.trackTitleProperty().set(trackTitle);
    }
    // -----------------------------------------------------
    private final StringProperty trackArtist = new SimpleStringProperty();

    public final StringProperty trackArtistProperty() {
        return this.trackArtist;
    }

    public final String getTrackArtist() {
        return this.trackArtistProperty().get();
    }

    public final void setTrackArtist(final String trackArtist) {
        this.trackArtistProperty().set(trackArtist);
    }
    // -----------------------------------------------------
    private final StringProperty trackGenre = new SimpleStringProperty();

    public final StringProperty trackGenreProperty() {
        return this.trackGenre;
    }

    public final String getTrackGenre() {
        return this.trackGenreProperty().get();
    }

    public final void setTrackGenre(final String trackGenre) {
        this.trackGenreProperty().set(trackGenre);
    }
    // -----------------------------------------------------
    private final StringProperty runTime = new SimpleStringProperty();

    public final StringProperty runTimeProperty() {
        return this.runTime;
    }

    public final String getRunTime() {
        return this.runTimeProperty().get();
    }

    public final void setRunTime(final String runTime) {
        this.runTimeProperty().set(runTime);
    }
    // -----------------------------------------------------
    private final StringProperty filePath = new SimpleStringProperty();

    public final StringProperty filePathProperty() {
        return this.filePath;
    }

    public final String getFilePath() {
        return this.filePathProperty().get();
    }

    public final void setFilePath(final String filePath) {
        this.filePathProperty().set(filePath);
    }
}
