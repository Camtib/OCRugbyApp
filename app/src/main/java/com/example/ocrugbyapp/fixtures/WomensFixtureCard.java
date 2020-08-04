package com.example.ocrugbyapp.fixtures;

public class WomensFixtureCard {

    private String date;
    private String womensFixture;
    private String womensKOTime;
    private String womensHA;

    public WomensFixtureCard(String date, String womensFixture, String womensKOTime, String womensHA) {

        this.date = date;
        this.womensFixture = womensFixture;
        this.womensKOTime = womensKOTime;
        this.womensHA = womensHA;

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWomensFixture() {
        return womensFixture;
    }

    public void setWomensFixture(String womensFixture) {
        this.womensFixture = womensFixture;
    }

    public String getWomensKOTime() {
        return womensKOTime;
    }

    public void setWomensKOTime(String womensKOTime) {
        this.womensKOTime = womensKOTime;
    }

    public String getWomensHA() {
        return womensHA;
    }

    public void setWomensHA(String womensHA) {
        this.womensHA = womensHA;
    }

}
