package com.example.ocrugbyapp.members;

import android.net.Uri;

public class MembersCard {

    private String name;
    private String nickname;
    private String userID;

    public MembersCard(String name, String nickname, String userID) {
        this.name = name;
        this.nickname = nickname;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
