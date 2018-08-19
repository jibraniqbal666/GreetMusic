package com.example.kira666.greetmusic;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.bumptech.glide.request.RequestOptions;

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
        final Uri albumArtStr = (Uri) getIntent().getExtras().get(TabFragment.EXTRAS_MUSIC_ALBUM_ART);


        //player
        player = Player.getInstance();

        //Music initialization
        titleText = findViewById(R.id.playerTitleText);
        artistText = findViewById(R.id.playerArtistText);
        titleBottomText = findViewById(R.id.playerTitleBottom);
        durationBottomText = findViewById(R.id.playerDuration);

        albumArt = findViewById(R.id.playerAlbumArt);

        playPauseButton = findViewById(R.id.playerPlayButton);
        nextButton = findViewById(R.id.playerNextButton);
        previousButton = findViewById(R.id.playerNextButton);

        share = findViewById(R.id.floatingActionButton);


        seekMusic = findViewById(R.id.playerSeekBar);
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


        Glide.with(this)
                .load(MusicLibrary.getAlbumArtPath(Long.parseLong(albumId)))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.no_album_1)
                        .error(R.drawable.no_album_1)
                )
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
                File input = new File(path);
                File output = new File(getBaseContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), Uri.fromFile(new File(path)).getLastPathSegment().split(".")[0] + "_cut.mp3");
                new CutAudio().cutAudio(input, output);
                Log.i(this.getClass().getName(), output.getAbsolutePath());
            }
        });
    }

}
