package es.burgueses.presentacion.utils;
import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ReproductorViewmodel {
    private final ObjectProperty<MediaPlayer> mediaPlayer = new SimpleObjectProperty<>();
    private final StringProperty currentTrack = new SimpleStringProperty();

    public void play(String filePath) {
        Media media = new Media(new File(filePath).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        mediaPlayer.set(player);
        currentTrack.set(filePath);
        player.play();
    }

    public void setSong(String filePath) {
        if (mediaPlayer.get() != null) {
            this.mediaPlayer.get().stop();

            this.mediaPlayer.get().dispose();
        }
        Media media = new Media(new File(filePath).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        mediaPlayer.set(player);
        currentTrack.set(filePath);
        mediaPlayer.get().setVolume(1.0);

    }

    public void play() {
        if (mediaPlayer.get() != null && currentTrack.get() != null && mediaPlayer.get().getMedia() != null) {
            var status = mediaPlayer.get().getStatus();
            if (mediaPlayer.get().getStatus().equals(MediaPlayer.Status.PAUSED )
                    || status.equals(MediaPlayer.Status.READY)
                    || status.equals(MediaPlayer.Status.UNKNOWN)) {
                mediaPlayer.get().play();

            } else if (mediaPlayer.get().getStatus() == MediaPlayer.Status.STOPPED) {
                mediaPlayer.get().seek(Duration.ZERO);
                mediaPlayer.get().play();

            }

        }
    }
    public void toogle(){
        if(mediaPlayer.get().getStatus() == MediaPlayer.Status.STOPPED || mediaPlayer.get().getStatus() == MediaPlayer.Status.PAUSED
        || mediaPlayer.get().getStatus() == MediaPlayer.Status.READY){
            mediaPlayer.get().play();
        }else{
            mediaPlayer.get().pause();
        }
    }
    public MediaPlayer.Status getStatus() {
        if (this.mediaPlayer.get() != null)
            return this.mediaPlayer.get().getStatus();
        else
            return null;
    }

    public void pause() {
        if (mediaPlayer.get() != null) mediaPlayer.get().pause();
    }

    public void stop() {
        if (mediaPlayer.get() != null) mediaPlayer.get().stop();
    }

    public StringProperty currentTrackProperty() {
        return currentTrack;
    }

    public ObjectProperty<MediaPlayer> mediaPlayerProperty() {
        return mediaPlayer;
    }

    public void dispose() {
        if (this.mediaPlayer != null && this.mediaPlayer.get() != null) {
            this.mediaPlayer.get().stop();
            this.mediaPlayer.get().dispose();
        }
    }
 
}
