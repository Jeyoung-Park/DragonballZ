package com.example.dragonballz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BtnRecordActivity extends Activity {

    TextView TextView_VsCom;
    Button btn_record_reset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        final SharedPreferences flag=getSharedPreferences("flag", MODE_PRIVATE);
        final MediaPlayer sound_click=MediaPlayer.create(BtnRecordActivity.this, R.raw.sound_btn_click1);

        WinLoseRecord lastVisit=(WinLoseRecord) getApplication(); //최근 방문한 페이지 선언
        WinLoseRecord win=(WinLoseRecord) getApplication();
       // int win_record=win.getWin();
        WinLoseRecord lose=(WinLoseRecord) getApplication();
       // int lose_record=lose.getLose();
        WinLoseRecord total=(WinLoseRecord) getApplication();
      //  int total_record=total.getTotal();
        WinLoseRecord win_percentage=(WinLoseRecord) getApplication();
      //  double win_percentage_record=win_percentage.getWin_percentage();

        TextView_VsCom=findViewById(R.id.TextView_vsCom);
//string.format에서 %%로 %표시
        //TextView_VsCom.setText(String.format("VS COM \n%d전   %d승  %d패\n승률: %.2f%%", total.getTotal(), win.getWin(), lose.getLose(), win_percentage.getWin_percentage()));

        SharedPreferences record=getSharedPreferences("record", MODE_PRIVATE); //SharedPreferenced record를 얻는다
        final SharedPreferences.Editor editor=record.edit();//SharedPreferences.Editor 인스턴스를 받아온다.
        //editor.putint("KEY", data) : data를 KEY라는 이름의 파일에 저장
        // 저장한 데이터를 가져올 떄도 동일한 파일명 사용
        if(lastVisit.getLastVisit()==1){
            editor.putInt("Win", record.getInt("Win", win.getWin())+win.getWin());
            editor.putInt("Lose", record.getInt("Lose", lose.getLose())+lose.getLose());
            editor.putInt("Total", record.getInt("Total", total.getTotal())+total.getTotal());
            editor.putFloat("WinPercentage", (float)(record.getInt("Win", win.getWin())+win.getWin())/(float)(record.getInt("Total", total.getTotal())+total.getTotal())*100);
            editor.commit(); //commit 메서드를 작성해야지만 데이터 저장
        }

        TextView_VsCom.setText(String.format("VS COM \n%d전   %d승  %d패\n승률: %.2f%%", record.getInt("Total", total.getTotal()), record.getInt("Win", win.getWin()), record.getInt("Lose", lose.getLose()), record.getFloat("WinPercentage", (float)win_percentage.getWin_percentage())));

        //최근 방문한 페이지가 2(BtnRecord)임을 표시
        lastVisit.setLastVisit(2);

        btn_record_reset=findViewById(R.id.btn_record_reset);

        btn_record_reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();

                //원래는 edit.clear() 쓰면 초기화 -> 이상함 -> 원인 찾아보기
                editor.putInt("Win", 0);
                editor.putInt("Lose", 0);
                editor.putInt("Total", 0);
                editor.putFloat("WinPercentage", 0);

                editor.commit();
                //페이지 새로고침
                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }
        });

    }
}
