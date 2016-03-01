package player.logic.db;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.logic.model.Track;

public class Database {

    private Connection conn = null;
    private static final Database INSTANCE = new Database();

    private Database() {
        String dbPath = "src\\player\\logic\\db\\tracks.db";
        String dbType = "jdbc:sqlite";
        String dbURL = dbType + ":" + dbPath;

        try {
            conn = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed!", e);
        }
    }

    public ObservableList<Track> SelectTracks(String selectFrom) {

        int dbTimeout = 30;
        ObservableList<Track> data = FXCollections.observableArrayList();

        try {
            Statement stmt = conn.createStatement();
            try {
                stmt.setQueryTimeout(dbTimeout);
                ResultSet rs = stmt.executeQuery(selectFrom);
                try {
                    while (rs.next()) {
                        //String TrackID = rs.getString("TrackID");
                        String song_name = rs.getString("song_name");
                        String artist_name = rs.getString("artist_name");
                        String song_genre = rs.getString("song_genre");
                        String run_time = rs.getString("run_time");
                        String file_path = rs.getString("file_path");

                        Track row = new Track(song_name, artist_name, song_genre, run_time, file_path);
                        data.add(row);
                    }
                } finally {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } finally {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public static Database getInstance() {
        return INSTANCE;
    }
}
