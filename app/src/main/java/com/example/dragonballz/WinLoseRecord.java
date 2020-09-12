package com.example.dragonballz;

import android.app.Application;
import android.content.SharedPreferences;

public class WinLoseRecord extends Application {
    private int win;
    private int lose;
    private int total;
    private boolean bgm_visit;
    private int lastVisit;
    private boolean check_vibration;
    private double win_percentage;

    public boolean isBgm_visit() {
        return bgm_visit;
    }

    public void setBgm_visit(boolean bgm_visit) {
        this.bgm_visit = bgm_visit;
    }

    public boolean isCheck_vibration() {
        return check_vibration;
    }

    public void setCheck_vibration(boolean check_vibration) {
        this.check_vibration = check_vibration;
    }

    public int getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(int lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total=total;
    }

    public double getWin_percentage() { return win_percentage;  }

    public void setWin_percentage(double win_percentage) {
        this.win_percentage = win_percentage;
    }
}
