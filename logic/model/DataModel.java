package player.logic.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.logic.db.Database;

public class DataModel {

    private final ObservableList<Track> trackList = FXCollections.observableArrayList(track
            -> new Observable[]{
                track.trackTitleProperty(),
                track.trackArtistProperty(),
                track.trackGenreProperty(),
                track.runTimeProperty(),
                track.filePathProperty()});
    private final ObservableList<Track> trackSearchList = FXCollections.observableArrayList(track
            -> new Observable[]{
                track.trackTitleProperty(),
                track.trackArtistProperty(),
                track.trackGenreProperty(),
                track.runTimeProperty(),
                track.filePathProperty()});
    private final ObjectProperty<Track> currentTrack = new SimpleObjectProperty<>(null);

    public ObjectProperty<Track> currentTrackProperty() {
        return currentTrack;
    }

    public final Track getCurrentTrack() {
        return currentTrackProperty().get();
    }

    public final void setCurrentTrack(Track track) {
        currentTrackProperty().set(track);
    }

    public ObservableList<Track> getTrackList() {
        return trackList;
    }

    public ObservableList<Track> getTrackSearchList() {
        return trackSearchList;
    }

    public void loadData() {
        String query = "SELECT * FROM Tracks";
        trackSearchList.setAll(
                Database.getInstance().SelectTracks(query)
        );
    }

    public void saveData() {
    }
}
