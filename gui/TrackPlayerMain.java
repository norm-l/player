package player.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import player.logic.model.DataModel;

public class TrackPlayerMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        FXMLLoader trackPlayerLoader = new FXMLLoader(getClass().getResource("TrackPlayer.fxml"));
        root.setCenter(trackPlayerLoader.load());
        TrackPlayerController trackPlayerController = trackPlayerLoader.getController();

        DataModel model = new DataModel();
        trackPlayerController.initData(model);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
