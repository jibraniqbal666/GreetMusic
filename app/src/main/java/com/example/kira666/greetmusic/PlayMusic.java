package com.example.kira666.greetmusic;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

public class PlayMusic extends AppCompatActivity {
    //declaration of music
    ImageButton playPauseButton;
    ImageButton nextButton;
    ImageButton previousButton;

    ImageView albumArt;
    TextView titleText;
    TextView artistText;
    TextView titleBottomText;
    TextView durationBottomText;

    FloatingActionButton share;

    Player player;

    SeekBar seekMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        //getIntentExtras
        final String path = getIntent().getExtras().getString(TabFragment.EXTRAS_MUSIC_PATH);
        final String title = getIntent().getExtras().getString(TabFragment.EXTRAS_MUSIC_TITLE);
        final String artist = getIntent().getExtras().getString(TabFragment.EXTRAS_MUSIC_ARTIST);
        final String albumId = getIntent().getExtras().getString(TabFragment.EXTRAS_MUSIC_ALBUM_ID);

        //player
        player = Player.getInstance();

        //Music initialization
        titleText = (TextView) findViewById(R.id.playerTitleText);
        artistText = (TextView) findViewById(R.id.playerArtistText);
        titleBottomText = (TextView) findViewById(R.id.playerTitleBottom);
        durationBottomText = (TextView) findViewById(R.id.playerDuration);

        albumArt = (ImageView) findViewById(R.id.playerAlbumArt);

        playPauseButton = (ImageButton) findViewById(R.id.playerPlayButton);
        nextButton = (ImageButton) findViewById(R.id.playerNextButton);
        previousButton = (ImageButton) findViewById(R.id.playerNextButton);

        seekMusic = (SeekBar) findViewById(R.id.playerSeekBar);
        seekMusic.setThumbOffset(0);
        // set style, just once
        seekMusic.setProgress(0);
        seekMusic.setPadding(0, 0, 0, 0);


        //onclick
        if (title != null)
            titleText.setText(title);

        if (artist != null)
            artistText.setText(artist);

        if (title != null && artist != null)
            titleBottomText.setText(title + " ( " + artist + " ) ");

        MusicLibrary musicLibrary = new MusicLibrary(getApplicationContext());

        Glide.with(this)
                .load(musicLibrary.getAlbumArtPath(albumId))
                .into(albumArt);


        if (player.play(path, false))
            playPauseButton.setImageResource(R.drawable.ic_pause_circle_filled_white_36dp);
        else
            playPauseButton.setImageResource(R.drawable.ic_play_circle_filled_white_36dp);


        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.play(path, true))
                    playPauseButton.setImageResource(R.drawable.ic_pause_circle_filled_white_36dp);
                else
                    playPauseButton.setImageResource(R.drawable.ic_play_circle_filled_white_36dp);
            }
        });
        seekMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("MusicPlay:OnstartTrac", String.valueOf(seekBar.getProgress()));
                player.seekMusic(seekBar.getProgress() * 1000);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("MusicPlay:OnstartTrac", String.valueOf(seekBar.getProgress()));
                player.seekMusic(seekBar.getProgress() * 1000);
            }
        });
        final Handler mHandler = new Handler();
        //Make sure you update Seekbar on UI thread
        PlayMusic.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (player != null) {
                    int mCurrentPosition = player.getCurrentPosition();
                    seekMusic.setProgress((int) ((float) mCurrentPosition / player.getDuration() * 100));
                    String remainingMin = String.valueOf(((player.getDuration() - player.getCurrentPosition()) / (1000 * 60)) % 60);
                    String remainingSec = String.valueOf(((player.getDuration() - player.getCurrentPosition()) / (1000)) % 60);
                    durationBottomText.setText(remainingMin + ":" + remainingSec);

                }
                mHandler.postDelayed(this, 1000);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(path);
            }
        });
    }

}
