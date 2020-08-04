package com.example.ocrugbyapp.members;

import android.net.Uri;

public class MembersCard {

    private String name;
    private String nickname;
    private Uri profileUri;

    public MembersCard(String name, String nickname, Uri profileUri) {
        this.name = name;
        this.nickname = nickname;
        this.profileUri = profileUri;
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

    public Uri getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(Uri profileUri) {
        this.profileUri = profileUri;
    }
}
