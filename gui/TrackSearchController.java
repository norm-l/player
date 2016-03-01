package player.gui;

import java.net.URL;
import java.util.ResourceBundle;
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
    private Button search;
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField genreField;
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
                    Track rowData = row.getItem();
                    model.getTrackList().add(rowData);
                    searchTable.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
    }

    @FXML
    private void add(ActionEvent e) {
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
        searchTable.setItems(model.getTrackSearchList());
    }
}
