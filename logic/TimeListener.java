package player.logic;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
import player.gui.TrackPlayerController;

public class TimeListener implements ChangeListener<Duration> {

    @Override
    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
        //TrackPlayerController.setTime(newValue.toString());
    }
}
