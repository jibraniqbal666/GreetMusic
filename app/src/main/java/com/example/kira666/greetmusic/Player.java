package com.example.kira666.greetmusic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Kira666 on 7/16/2017.
 */

public class Player {
    public static String path = "/nopath/";
    private static Player myObj;
    MediaPlayer mediaPlayer;

    private Player() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public static Player getInstance() {
        if (myObj == null) {
            myObj = new Player();
        }
        return myObj;
    }

    public boolean play(String path, boolean buttonClicked) {
        if (!Player.path.equals(path)) {
            try {
                Player.path = path;
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

            } catch (IOException exp) {
                Log.d("Player:play:", "Can't Start Playing Media");
                Log.d("Player:play:message", exp.getMessage());
                Log.d("Player:play:stacktrace", exp.getStackTrace().toString());
                Log.d("Player:play:cause", exp.getCause().toString());
                return false;
            }
            return true;
        } else {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                return true;
            } else if (buttonClicked) {
                mediaPlayer.pause();
                return false;
            } else {
                return true;
            }
        }
    }

    public void seekMusic(int progress) {
        //int mediaDuration = mediaPlayer.getDuration();
        mediaPlayer.seekTo(progress);
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

}
