package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.example.movieapp.R;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

public class PlayerActivity extends AppCompatActivity {

    private String VIDEO_URL;
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getSupportActionBar().hide();

        VIDEO_URL = getIntent().getExtras().getString("linkMovie");
        if(VIDEO_URL == null) {
            VIDEO_URL = "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1920_18MG.mp4";
        }

        playerView = findViewById(R.id.exo_player);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(simpleExoPlayer);

        DataSource.Factory dataFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "appname"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataFactory).createMediaSource(Uri.parse(VIDEO_URL));
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}
