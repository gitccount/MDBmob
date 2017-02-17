package com.example.drawerlayout_fragment.JavaBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/25.
 */

public class GameScore extends BmobObject {
    private String playerName;
    private Integer score;
    private Boolean cheatMode;
    private String message;

//    public GameScore() {
//        this.setTableName("T_a_b");
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getCheatMode() {
        return cheatMode;
    }

    public void setCheatMode(Boolean cheatMode) {
        this.cheatMode = cheatMode;
    }
}
