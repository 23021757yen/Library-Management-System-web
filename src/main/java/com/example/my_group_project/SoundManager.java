package com.example.my_group_project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SoundManager {
    public static void playSound(String soundFilePath) {
        Media sound = new Media(new File(soundFilePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
