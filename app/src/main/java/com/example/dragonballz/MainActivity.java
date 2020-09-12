package com.example.dragonballz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_start, btn_set, btn_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start=findViewById(R.id.btn_start);
        btn_set=findViewById(R.id.btn_set);
        btn_record=findViewById(R.id.btn_record);

        //  soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        final WinLoseRecord bgm_visit=(WinLoseRecord) getApplication(); //이거는 onCreate 안에 해줘야 함
        final MediaPlayer sound_click=MediaPlayer.create(MainActivity.this, R.raw.sound_btn_click1);
        final SharedPreferences flag=getSharedPreferences("flag", MODE_PRIVATE);

        if(!flag.getBoolean("flag_bgm", true))   {
            startService(new Intent(MainActivity.this, BackgroundMusic.class));
            bgm_visit.setBgm_visit(true);
        }
        /*if(bgm_visit.isBgm_visit()||first_bgm.getBoolean("first_bgm", true)){
            startService(new Intent(MainActivity.this, BackgroundMusic.class));
            if(first_bgm.getBoolean("first_bgm", true))
                first_bgm_editor.putBoolean("first_bgm", false);
        }
        if(bgm_visit.isBgm_visit())
            startService(new Intent(MainActivity.this, BackgroundMusic.class));*/
        Log.d(TAG, "mainbgm"+flag.getBoolean("flag_bgm", true));

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
             //   btn_start.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                Intent intent=new Intent(MainActivity.this, BtnStartActivity.class);
                startActivity(intent);
            }
        });
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                //  btn_record.setLayoutParams(new LinearLayout.LayoutParams(75, 75));
                Intent intent=new Intent(MainActivity.this, BtnRecordActivity.class);
                startActivity(intent);
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
               // btn_set.setLayoutParams(new LinearLayout.LayoutParams(75, 75));
                Intent intent=new Intent(MainActivity.this, BtnSetActivity.class);
                startActivity(intent);
                Log.d(TAG, "mainbgm2"+flag.getBoolean("flag_bgm", true));
            }
        });
    }
/*
    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(MainActivity.this, BackgroundMusic.class));
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(MainActivity.this, BackgroundMusic.class));
    }
    */

}
