package com.scriptsbundle.adforest.ad_detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.scriptsbundle.adforest.R;

import java.util.ArrayList;

public class FullScreenVideoActivity extends AppCompatActivity {

    VideoView vvPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_full_screen_video);

        Intent i = getIntent();
        String videoUrl = i.getStringExtra("videoUrl");

        vvPlayer = findViewById(R.id.vvPlayer);
        vvPlayer.setVideoURI(Uri.parse(videoUrl));
    }
}