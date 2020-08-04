package com.example.ocrugbyapp.fixtures;

public class MensFixtureCard {

    private String date;
    private String firstsFixture;
    private String secondsFixture;
    private String bsFixture;
    private String firstsHA;
    private String secondsHA;
    private String bsHA;

    public MensFixtureCard(String date, String firstsFixture, String secondsFixture, String bsFixture, String firstsHA, String secondsHA, String bsHA) {
        this.date = date;
        this.firstsFixture = firstsFixture;
        this.secondsFixture = secondsFixture;
        this.bsFixture = bsFixture;
        this.firstsHA = firstsHA;
        this.secondsHA = secondsHA;
        this.bsHA = bsHA;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstsFixture() {
        return firstsFixture;
    }

    public void setFirstsFixture(String firstsFixture) {
        this.firstsFixture = firstsFixture;
    }

    public String getSecondsFixture() {
        return secondsFixture;
    }

    public void setSecondsFixture(String secondsFixture) {
        this.secondsFixture = secondsFixture;
    }

    public String getBsFixture() {
        return bsFixture;
    }

    public void setBsFixture(String bsFixture) {
        this.bsFixture = bsFixture;
    }

    public String getFirstsHA() {
        return firstsHA;
    }

    public void setFirstsHA(String firstsHA) {
        this.firstsHA = firstsHA;
    }

    public String getSecondsHA() {
        return secondsHA;
    }

    public void setSecondsHA(String secondsHA) {
        this.secondsHA = secondsHA;
    }

    public String getBsHA() {
        return bsHA;
    }

    public void setBsHA(String bsHA) {
        this.bsHA = bsHA;
    }
}
