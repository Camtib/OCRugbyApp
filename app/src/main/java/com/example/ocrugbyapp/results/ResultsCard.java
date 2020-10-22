package com.example.ocrugbyapp.results;

public class ResultsCard {

    private String date, homeTeam, awayTeam, homeScore, awayScore;
    private int fixtureNum;

    public ResultsCard(int fixtureNum, String date, String homeTeam, String awayTeam, String homeScore, String awayScore) {
        this.fixtureNum = fixtureNum;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getFixtureNum() {
        return fixtureNum;
    }

    public void setFixtureNum(int fixtureNum) {
        this.fixtureNum = fixtureNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }
}
