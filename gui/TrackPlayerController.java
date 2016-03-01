package player.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import player.logic.TrackPlayer;
import player.logic.model.DataModel;
import player.logic.model.Track;

public class TrackPlayerController implements Initializable {

    // -----------------------------------------------------
    @FXML
    private Button remove;
    @FXML
    private Button removeAll;
    @FXML
    private Button search;
    // -----------------------------------------------------
    @FXML
    private TableView<Track> playingTable;
    @FXML
    private TableColumn<Track, String> playingTitleCol;
    @FXML
    private TableColumn<Track, String> playingArtistCol;
    @FXML
    private TableColumn<Track, String> playingGenreCol;
    @FXML
    private TableColumn<Track, String> playingRunTimeCol;
    // -----------------------------------------------------
    @FXML
    private Button play;
    @FXML
    private Button pause;
    @FXML
    private Button rw;
    @FXML
    private Button ff;
    @FXML
    private Button reset;
    // -----------------------------------------------------
    @FXML
    private Label runTime;
    @FXML
    private Slider timeSlider;
    // -----------------------------------------------------
    private DataModel model;
    private Track track;
    private Duration duration;
    private TrackPlayer player;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        playingTitleCol.setCellValueFactory(new PropertyValueFactory<>("TrackTitle"));
        playingArtistCol.setCellValueFactory(new PropertyValueFactory<>("TrackArtist"));
        playingGenreCol.setCellValueFactory(new PropertyValueFactory<>("TrackGenre"));
        playingRunTimeCol.setCellValueFactory(new PropertyValueFactory<>("RunTime"));

        playingTable.setRowFactory(tv -> { // Function for double-click to play (load)
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    play();
                }
            });
            return row;
        });
        
        //player.currentTimeProperty().addListener((Observable ov) -> {
        //    updateValues();
        //});
        //player.setOnReady(() -> {
        //    duration = player.getMedia().getDuration();
        //    updateValues();
        //});
    }

    protected void updateValues() {
        if (runTime != null) {
            Platform.runLater(() -> {
                Duration currentTime = player.getCurrentTime();
                runTime.setText(formatTime(currentTime, duration));
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else if (elapsedHours > 0) {
            return String.format("%d:%02d:%02d", elapsedHours,
                    elapsedMinutes, elapsedSeconds);
        } else {
            return String.format("%02d:%02d", elapsedMinutes,
                    elapsedSeconds);
        }
    }

    @FXML
    private void play() {
        play.setText("Pause");
        if (player == null) {
            track = playingTable.getSelectionModel().getSelectedItem();
            player = new TrackPlayer(track.getFilePath());
            player.playSong();
        } else if (player.getStatus() != null) {
            switch (player.getStatus()) {
                case PAUSED:
                    player.playSong();
                    break;
                case PLAYING:
                    player.pauseSong();
                    play.setText("Play");
                    break;
            }
        }
    }

    @FXML
    private void reset(ActionEvent e) {
        player.setSeek(player.getStartTime());
    }

    @FXML
    private void remove(ActionEvent e) {
        Track row = playingTable.getSelectionModel().getSelectedItem();
        if (row != null) {
            model.getTrackList().remove(row);
            playingTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void removeAll(ActionEvent e) {
        model.getTrackList().removeAll(model.getTrackList());
        playingTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void search(ActionEvent e) throws IOException {
        BorderPane root = new BorderPane();
        FXMLLoader trackSearchLoader = new FXMLLoader(getClass().getResource("TrackSearch.fxml"));
        root.setRight(trackSearchLoader.load());
        TrackSearchController trackSearchController = trackSearchLoader.getController();

        trackSearchController.initData(model);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Search");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    void initData(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Data has already been initialised!");
        }
        this.model = model;
        playingTable.setItems(model.getTrackList());
    }
}
