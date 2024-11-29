package com.example.my_group_project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlay {
    public static void playSound(String soundFilePath) {
        try {
            String path = SoundPlay.class.getResource(soundFilePath).toExternalForm();
            Media sound = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}