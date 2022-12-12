package com.example.androidflutterdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.tappp_panel.TapppPanel;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TapppPanel tapppPanel = new TapppPanel(this);
        FragmentManager fragmentManager = getSupportFragmentManager();

        tapppPanel.loadPanel(fragmentManager,R.id.fragment_container);

        VideoView vv = (VideoView) findViewById(R.id.videoView1);
        vv.setVideoURI(Uri.parse("https://clips-media-aka.warnermediacdn.com/wmce/clips/2020-06/3837-91bd4f74167c4f29bbdcf4217d801af7/ts/hls_master.m3u8"));
        vv.start();

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }
}