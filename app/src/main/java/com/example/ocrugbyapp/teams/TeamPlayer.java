package com.example.ocrugbyapp.teams;

public class TeamPlayer {

    private String userID;
    private String team;
    private String position;

    public TeamPlayer(String userID, String team, String position) {
        this.userID = userID;
        this.team = team;
        this.position = position;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
