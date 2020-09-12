package com.example.dragonballz;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class BackgroundMusic extends Service {

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override//서비스가 시작될 떄
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        player = MediaPlayer.create(this, R.raw.cockroach_on_toast);
        player.setLooping(true); //  setLooping(true) -> 무한 루프
        player.start();
        return START_NOT_STICKY; //START_NOT_STICKY : 서비스를 종료하면 다시 생성 x (pendingIntent 가 없는 경우)
                                //START_STICKY : 서비스를 종료해도 이후 서비스를 재생성할 때 OnStartCommand() 실행
    }

    @Override//서비스가 종료될 때-
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
