package com.example.StatsService.model;

public class StatsPlayer {
    public int goalScored;
    private int season;
    private int playerId;

    public StatsPlayer() {}

    public int getGoalScored() {
        return goalScored;
    }

    public void setGoalScored(int goalScored) {
        this.goalScored = goalScored;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }


}
