package picle.view;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import picle.Main;

import java.io.File;

public class IntroController {

    @FXML
    private MediaView mediaView;

    /**
     * Initialize the intro.
     */
    public void initialize() {

        String path = "client/src/main/resources/videos/Intro.mp4";

        Media media = new Media(new File(path).toURI().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setAutoPlay(true);

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                Main.activateScreen("login.fxml");
            }
        });

    }



}
