package player.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import player.logic.model.DataModel;
import player.logic.model.Track;

public class TrackSearchController implements Initializable {

    // -----------------------------------------------------
    @FXML
    private TextField searchField;
    @FXML
    private Button add;
    @FXML
    private Button addAll;
    @FXML
    private Button back;
    // -----------------------------------------------------
    @FXML
    private TableView<Track> searchTable;
    @FXML
    private TableColumn<Track, String> searchTitleCol;
    @FXML
    private TableColumn<Track, String> searchArtistCol;
    @FXML
    private TableColumn<Track, String> searchGenreCol;
    @FXML
    private TableColumn<Track, String> searchRunTimeCol;
    // -----------------------------------------------------
    private DataModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchTitleCol.setCellValueFactory(new PropertyValueFactory<>("TrackTitle"));
        searchArtistCol.setCellValueFactory(new PropertyValueFactory<>("TrackArtist"));
        searchGenreCol.setCellValueFactory(new PropertyValueFactory<>("TrackGenre"));
        searchRunTimeCol.setCellValueFactory(new PropertyValueFactory<>("RunTime"));

        searchTable.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    add();
                }
            });
            return row;
        });
    }

    @FXML
    private void add() {
        Track row = searchTable.getSelectionModel().getSelectedItem();
        if (row != null) {
            model.getTrackList().add(row);
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void addAll(ActionEvent e) {
        model.getTrackList().addAll(model.getTrackSearchList());
        searchTable.getSelectionModel().clearSelection();
    }

    void initData(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Data has already been initialized");
        }
        model.loadData();
        this.model = model;

         // Wrap the ObservableList in a FilteredList (initially display all data)
        FilteredList<Track> filteredData = new FilteredList<>(model.getTrackSearchList(), p -> true);

        // Set the filter Predicate whenever the filter changes
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(track -> {
                // If filter text is empty, display all tracks
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare title, artist and genre of every song with filter text
                String lowerCaseFilter = newValue.toLowerCase();

                if (track.getTrackTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches track name
                } else if (track.getTrackArtist().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches track artist
                } else if (track.getTrackGenre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches track genre
                }
                return false; // Does not match
            });
        });

        // Wrap the FilteredList in a SortedList
        SortedList<Track> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(searchTable.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        searchTable.setItems(sortedData);
    }
}
