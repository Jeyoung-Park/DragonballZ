package com.example.dragonballz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.ColorInt;

import static android.content.ContentValues.TAG;

public class BtnSetActivity extends Activity {
    Button btn_bgm, btn_soundEffect, btn_vibration;
  //  boolean flag_bgm=true, flag_soundEffect=true, flag_vibration=true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        btn_bgm=findViewById(R.id.btn_bgm);
        btn_soundEffect=findViewById(R.id.btn_soundEffect);
        btn_vibration=findViewById(R.id.btn_vibration);
        final WinLoseRecord bgm_visit = (WinLoseRecord) getApplication();
        final WinLoseRecord check_vibration=(WinLoseRecord) getApplication(); //진동 여부 확인
//        Log.d(TAG, "Sibal"+Integer.toString(bgm_visit.getBgm_visit())+flag_bgm);

//        getSharedPreferences: 다른 액티비티에서도 사용가능
//        getPreferences: 한 가지 액티비티에서만 사용 ㄱㄴ
        final SharedPreferences flag=getSharedPreferences("flag", MODE_PRIVATE); //SharedPreferenced record를 얻는다 sharedpreferences는 값을 얻어올 때
        final SharedPreferences.Editor editor=flag.edit();//SharedPreferences.Editor 인스턴스를 받아온다. sharedpreferences.editor는 값을 입력할 때
        final MediaPlayer sound_click=MediaPlayer.create(BtnSetActivity.this, R.raw.sound_btn_click1);

        function_flag(!(flag.getBoolean("flag_bgm", true)), btn_bgm);
        function_flag(!(flag.getBoolean("flag_soundEffect", true)), btn_soundEffect);
        function_flag(!(flag.getBoolean("flag_vibration", true)), btn_vibration);

        Log.d(TAG, "SIBAL_first"+flag.getBoolean("flag_bgm", true));

        //startService(new Intent(BtnSetActivity.this, BackgroundMusic.class));

        btn_bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                editor.putBoolean("flag_bgm", !(flag.getBoolean("flag_bgm", true)));
                function_flag(flag.getBoolean("flag_bgm", true), btn_bgm);
               // bgm_visit.setBgm_visit(flag.getBoolean("flag_bgm", true));
               // Log.d(TAG, "SIBAL_visit"+bgm_visit.isBgm_visit());
                Log.d(TAG, "bgmPleaseStart"+flag.getBoolean("flag_bgm", true)+"/"+bgm_visit.isBgm_visit());
                if(flag.getBoolean("flag_bgm", true)){
                    if(!bgm_visit.isBgm_visit()){
                        startService(new Intent(BtnSetActivity.this, BackgroundMusic.class));
                    }
                }
                else{
                    stopService(new Intent(BtnSetActivity.this, BackgroundMusic.class));
                    bgm_visit.setBgm_visit(false);
                }
                editor.commit();
            }
        });

        btn_soundEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                editor.putBoolean("flag_soundEffect", !(flag.getBoolean("flag_soundEffect", true)));
                function_flag(flag.getBoolean("flag_soundEffect", true), btn_soundEffect);
                editor.commit();
            }
        });

        btn_vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                editor.putBoolean("flag_vibration", !(flag.getBoolean("flag_vibration", true)));
                //check_vibration.setCheck_vibration(!(check_vibration.isCheck_vibration()));
                function_flag(flag.getBoolean("flag_vibration", true), btn_vibration);
                Log.d(TAG, "vibrationSet"+check_vibration.isCheck_vibration());
                editor.commit();
            }
        });
    }

    public void function_flag(boolean flag, Button btn){
        if(flag){
            btn.setBackgroundColor(Color.parseColor("#AAAAAA"));
            btn.setText("켬");
            btn.setTextColor(Color.parseColor("#000000"));
        }
        else{
            btn.setBackgroundColor(Color.parseColor("#666666"));
            btn.setText("끔");
            btn.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

}
