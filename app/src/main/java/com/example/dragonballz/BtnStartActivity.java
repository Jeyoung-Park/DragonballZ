package com.example.dragonballz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class BtnStartActivity extends Activity {
    ImageView imageView_enemy;
    ImageButton imagebtn_gi, imagebtn_pa, imagebtn_shield, imagebtn_reset;
    TextView[] gi_stacks=new TextView[20];
    TextView startText;
            //gi_stack1, gi_stack2, gi_stack3, gi_stack4, gi_stack5, gi_stack6, gi_stack7, gi_stack8, gi_stack9, gi_stack10;
    int my_gi_stack=0, enemy_gi_stack=0, myChoice=3, EnemyChoice=3, gameReset=0; //choice에서 0이 막기, 1이 파, 2가 기
    int images[]={R.drawable.enemy_shield, R.drawable.enemy_pa1, R.drawable.enemy_gi, R.drawable.enemy_tossil};
    public int my_win=0, my_lose=0, my_total=0;
    public double my_win_percentage=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        imageView_enemy=findViewById(R.id.imageView_enemy);
        imagebtn_gi=findViewById(R.id.imagebtn_gi);
        imagebtn_pa=findViewById(R.id.imagebtn_pa);
        imagebtn_shield=findViewById(R.id.imagebtn_shield);
        imagebtn_reset=findViewById(R.id.imagebtn_reset);
        gi_stacks[0]=findViewById(R.id.gi_stack1);
        gi_stacks[1]=findViewById(R.id.gi_stack2);
        gi_stacks[2]=findViewById(R.id.gi_stack3);
        gi_stacks[3]=findViewById(R.id.gi_stack4);
        gi_stacks[4]=findViewById(R.id.gi_stack5);
        gi_stacks[5]=findViewById(R.id.gi_stack6);
        gi_stacks[6]=findViewById(R.id.gi_stack7);
        gi_stacks[7]=findViewById(R.id.gi_stack8);
        gi_stacks[8]=findViewById(R.id.gi_stack9);
        gi_stacks[9]=findViewById(R.id.gi_stack10);

        //객체 변수에 final로 선언하면 그 변수에 다른 참조 값을 지정할 수 없습니다.
        // 원시 타입과 동일하게 한번 쓰여진 변수는 재변경 불가합니다. 단, 객체 자체가 immutable하다는 의미는 아닙니다.
        // 객체의 속성은 변경 가능합니다.
        final WinLoseRecord win= (WinLoseRecord) getApplication();
        win.setWin(my_win);
        final WinLoseRecord lose= (WinLoseRecord) getApplication();
        lose.setLose(my_lose);
        final WinLoseRecord total= (WinLoseRecord) getApplication();
        total.setTotal(my_total);
        final WinLoseRecord win_percentage= (WinLoseRecord) getApplication();
        win_percentage.setWin_percentage(my_win_percentage);

        final SharedPreferences flag=getSharedPreferences("flag", MODE_PRIVATE);
        final MediaPlayer sound_click=MediaPlayer.create(BtnStartActivity.this, R.raw.sound_btn_click1);

        startText=findViewById(R.id.textView_startText);

        /*imagebtn_gi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gi_stack++;
            }
        });
        imagebtn_pa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imagebtn_shield.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imagebtn_reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        Button.OnClickListener listener=new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                startText.setVisibility(View.INVISIBLE);
                switch(v.getId()){
                    case R.id.imagebtn_gi:
                        if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                        myChoice=2;
                        my_gi_stack++;
                        function_set_visible(my_gi_stack);
                        EnemyChoice=rnd.nextInt(3);
                        function_EnemyChoice(EnemyChoice);
                        function_record(win, lose, total, win_percentage);
                        break;
                    case R.id.imagebtn_pa:
                        if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                        if(check_gi_stack(my_gi_stack)){
                            myChoice=1;
                            my_gi_stack--;
                            function_set_visible(my_gi_stack);
                            EnemyChoice=rnd.nextInt(3);
                            function_EnemyChoice(EnemyChoice);
                        }
                        else Toast.makeText(BtnStartActivity.this, "기가 부족합니다.", Toast.LENGTH_LONG).show();
                        function_record(win, lose, total, win_percentage);
                        break;
                    case R.id.imagebtn_shield:
                        if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                        myChoice=0;
                        EnemyChoice=rnd.nextInt(3);
                        function_EnemyChoice(EnemyChoice);
                        function_record(win, lose, total, win_percentage);
                        break;
                    case R.id.imagebtn_reset:
                        if(!flag.getBoolean("flag_soundEffect", true)) sound_click.start();
                        function_reset();
                        break;
                }
            }
        };

        imagebtn_gi.setOnClickListener(listener);
        imagebtn_pa.setOnClickListener(listener);
        imagebtn_reset.setOnClickListener(listener);
        imagebtn_shield.setOnClickListener(listener);

        // 최근 방문한 페이지를 1(BtnStart)로 표시
        WinLoseRecord lastVisit=(WinLoseRecord) getApplication();
        lastVisit.setLastVisit(1);

        //Log.d(TAG, "기"+gi_stack+" / myChoce"+myChoice+" / enemyChoice"+EnemyChoice+" / enemu 기 스택"+enemy_gi_stack);

        /*if(gameReset==1){
            gi_stack=0;
            enemy_gi_stack=0;
            //토스트 메시지 출력
            Toast.makeText(this, "게임이 리셋되었습니다.", Toast.LENGTH_LONG).show();
            gameReset=0;
        }*/
    }

    Random rnd=new Random();

    public void function_EnemyChoice(int EnemyChoice){
        Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);//진동 생성자 Vibrator
        SharedPreferences flag=getSharedPreferences("flag", MODE_PRIVATE);

        if(EnemyChoice==0) imageView_enemy.setImageResource(images[0]);
        else if(EnemyChoice==1) {
            if (!(check_gi_stack(enemy_gi_stack))) {//기가 있므면 정상적으로, 없으면 function_EnemyChoice() 다시 호출
                int newEnemyChoice;
                while (true) {
                    newEnemyChoice = rnd.nextInt(3);
                    if (newEnemyChoice % 2 == 0) {
                        break;
                    }
                }
                function_EnemyChoice(newEnemyChoice);
            }
            else{
                enemy_gi_stack--;
                imageView_enemy.setImageResource(images[1]);
                if(myChoice==2){
                    Toast.makeText(this, "게임에서 패배하셨습니다.", Toast.LENGTH_LONG).show();
                    function_btns_enabled_false();
                    //Log.d(TAG, "vibrationStart"+check_vibration.isCheck_vibration());
                    if(!flag.getBoolean("flag_vibration", true))
                        vibrator.vibrate(1000); //1초동안 진동
                    my_lose++;
                }
            }
        }
        else if(EnemyChoice==2) {
            enemy_gi_stack++;
            imageView_enemy.setImageResource(images[2]);
            if (myChoice == 1) {
                Toast.makeText(this, "게임에서 승리하셨습니다.", Toast.LENGTH_LONG).show();
                function_btns_enabled_false();
               // Log.d(TAG, "vibrationStart"+check_vibration.isCheck_vibration());

                if(!flag.getBoolean("flag_vibration", true))
                    vibrator.vibrate(1000); //1초동안 진동
                my_win++;
            }
        }
    }
    // 기가 0보다 작거나 같으면 false, else true
    public boolean check_gi_stack(int gi){
        if(gi<=0) { return false; }
        return true;
    }
    //초기화 함수
    public void function_reset(){
        for(int i=0;i<my_gi_stack;i++)
            gi_stacks[i].setVisibility(View.INVISIBLE);
        function_btns_enabled_true();
        startText.setVisibility(View.VISIBLE);

        my_gi_stack=0;
        enemy_gi_stack=0;
        //myChoice와 EnemyChoice가 3이면 아무 상태도 일어나지 않음
        myChoice=3;
        EnemyChoice=3;
        //배토실 이미지로 다시 복귀
        imageView_enemy.setImageResource(images[3]);
        //토스트 메시지 출력
        Toast.makeText(this, "게임이 리셋되었습니다.", Toast.LENGTH_LONG).show();

       /* new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
                imageView_enemy.setImageResource(images[3]);
            }
        }, 1000);// 0.5초 정도 딜레이를 준 후 시작*/
    }
// 모든 버튼 비활성화(reset 버튼 제외)
    public void function_btns_enabled_false(){
        imagebtn_gi.setEnabled(false);
        imagebtn_pa.setEnabled(false);
        imagebtn_shield.setEnabled(false);
    }
// 모든 버튼 활성화(reset 버튼 제외)
    public void function_btns_enabled_true(){
        imagebtn_gi.setEnabled(true);
        imagebtn_pa.setEnabled(true);
        imagebtn_shield.setEnabled(true);
    }

    public void function_set_visible(int num){
        if(num>10){
            for(int i=0;i<10;i++){
                gi_stacks[i].setVisibility(View.VISIBLE);
            }
            return;
        }
        for(int i=0;i<num;i++){
            gi_stacks[i].setVisibility(View.VISIBLE);
        }
        for(int i=num;i<10;i++){
            gi_stacks[i].setVisibility(View.INVISIBLE);
        }
    }

    public void function_record(WinLoseRecord win, WinLoseRecord lose, WinLoseRecord total, WinLoseRecord win_percentage){
        my_total=my_win+my_lose;
        //if(my_total!=0) my_win_percentage=(double)my_win/(double)my_total*100;
        //else my_win_percentage=0;
        win.setWin(my_win);
        lose.setLose(my_lose);
        total.setTotal(my_total);
        win_percentage.setWin_percentage(my_win_percentage);
    }

}

