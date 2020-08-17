package com.example.ocrugbyapp.fixtures;

public class MensFixtureCard {

    private String date, fixtureNum;
    private String firstsFixture, firstsHA, firstsLC, firstsKO, firstsMeet, firstsAddress, firstsPostcode;
    private String secondsFixture, secondsHA, secondsLC, secondsKO, secondsMeet, secondsAddress, secondsPostcode;
    private String bsFixture, bsHA, bsLC, bsKO, bsMeet, bsAddress, bsPostcode;
    private boolean isExpandable;

    public MensFixtureCard(String fixtureNum, String date, String firstsFixture, String firstsHA, String firstsLC, String firstsKO, String firstsMeet, String firstsAddress, String firstsPostcode, String secondsFixture, String secondsHA, String secondsLC, String secondsKO, String secondsMeet, String secondsAddress, String secondsPostcode, String bsFixture, String bsHA, String bsLC, String bsKO, String bsMeet, String bsAddress, String bsPostcode) {
        this.fixtureNum = fixtureNum;
        this.date = date;
        this.firstsFixture = firstsFixture;
        this.firstsHA = firstsHA;
        this.firstsLC = firstsLC;
        this.firstsKO = firstsKO;
        this.firstsMeet = firstsMeet;
        this.firstsAddress = firstsAddress;
        this.firstsPostcode = firstsPostcode;
        this.secondsFixture = secondsFixture;
        this.secondsHA = secondsHA;
        this.secondsLC = secondsLC;
        this.secondsKO = secondsKO;
        this.secondsMeet = secondsMeet;
        this.secondsAddress = secondsAddress;
        this.secondsPostcode = secondsPostcode;
        this.bsFixture = bsFixture;
        this.bsHA = bsHA;
        this.bsLC = bsLC;
        this.bsKO = bsKO;
        this.bsMeet = bsMeet;
        this.bsAddress = bsAddress;
        this.bsPostcode = bsPostcode;
        this.isExpandable = false;
    }

    public String getFixtureNum() {
        return fixtureNum;
    }

    public void setFixtureNum(String fixtureNum) {
        this.fixtureNum = fixtureNum;
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

    public String getFirstsHA() {
        return firstsHA;
    }

    public void setFirstsHA(String firstsHA) {
        this.firstsHA = firstsHA;
    }

    public String getFirstsLC() {
        return firstsLC;
    }

    public void setFirstsLC(String firstsLC) {
        this.firstsLC = firstsLC;
    }

    public String getFirstsKO() {
        return firstsKO;
    }

    public void setFirstsKO(String firstsKO) {
        this.firstsKO = firstsKO;
    }

    public String getFirstsMeet() {
        return firstsMeet;
    }

    public void setFirstsMeet(String firstsMeet) {
        this.firstsMeet = firstsMeet;
    }

    public String getFirstsAddress() {
        return firstsAddress;
    }

    public void setFirstsAddress(String firstsAddress) {
        this.firstsAddress = firstsAddress;
    }

    public String getFirstsPostcode() {
        return firstsPostcode;
    }

    public void setFirstsPostcode(String firstsPostcode) {
        this.firstsPostcode = firstsPostcode;
    }

    public String getSecondsFixture() {
        return secondsFixture;
    }

    public void setSecondsFixture(String secondsFixture) {
        this.secondsFixture = secondsFixture;
    }

    public String getSecondsHA() {
        return secondsHA;
    }

    public void setSecondsHA(String secondsHA) {
        this.secondsHA = secondsHA;
    }

    public String getSecondsLC() {
        return secondsLC;
    }

    public void setSecondsLC(String secondsLC) {
        this.secondsLC = secondsLC;
    }

    public String getSecondsKO() {
        return secondsKO;
    }

    public void setSecondsKO(String secondsKO) {
        this.secondsKO = secondsKO;
    }

    public String getSecondsMeet() {
        return secondsMeet;
    }

    public void setSecondsMeet(String secondsMeet) {
        this.secondsMeet = secondsMeet;
    }

    public String getSecondsAddress() {
        return secondsAddress;
    }

    public void setSecondsAddress(String secondsAddress) {
        this.secondsAddress = secondsAddress;
    }

    public String getSecondsPostcode() {
        return secondsPostcode;
    }

    public void setSecondsPostcode(String secondsPostcode) {
        this.secondsPostcode = secondsPostcode;
    }

    public String getBsFixture() {
        return bsFixture;
    }

    public void setBsFixture(String bsFixture) {
        this.bsFixture = bsFixture;
    }

    public String getBsHA() {
        return bsHA;
    }

    public void setBsHA(String bsHA) {
        this.bsHA = bsHA;
    }

    public String getBsLC() {
        return bsLC;
    }

    public void setBsLC(String bsLC) {
        this.bsLC = bsLC;
    }

    public String getBsKO() {
        return bsKO;
    }

    public void setBsKO(String bsKO) {
        this.bsKO = bsKO;
    }

    public String getBsMeet() {
        return bsMeet;
    }

    public void setBsMeet(String bsMeet) {
        this.bsMeet = bsMeet;
    }

    public String getBsAddress() {
        return bsAddress;
    }

    public void setBsAddress(String bsAddress) {
        this.bsAddress = bsAddress;
    }

    public String getBsPostcode() {
        return bsPostcode;
    }

    public void setBsPostcode(String bsPostcode) {
        this.bsPostcode = bsPostcode;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
