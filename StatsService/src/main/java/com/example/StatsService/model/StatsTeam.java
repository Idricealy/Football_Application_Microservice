package com.example.StatsService.model;

public class StatsTeam {
    public int goalScored;

    private int season;
    private int teamId;

    public StatsTeam() {}
    public StatsTeam(int goalScored) {
        super();
        this.goalScored = goalScored;
    }

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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
