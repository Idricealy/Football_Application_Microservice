package com.example.MatchService.model;

import java.util.Date;
import java.util.List;

public class Match {
    private int matchId;
    private Team team1;
    private Team team2;
    private int team1Score;
    private int team2Score;
    private Date date;

    private int season;

    private List<Goal> goals;

    public Match() {
    }

    public Match(int matchId, Team team1, Team team2) {
        this.matchId = matchId;
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = 0;
        this.team2Score = 0;
        this.date = new Date();
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getTeam1Score() {
        team1Score = 0;

        for (Goal goal : goals) {
            if (goal.getScoringTeamId() == team1.getTeamId()) {
                team1Score++;
            }
        }

        return team1Score;
    }


    public int getTeam2Score() {
        team2Score = 0;

        for (Goal goal : goals) {
            if (goal.getScoringTeamId() == team2.getTeamId()) {
                team2Score++;
            }
        }

        return team2Score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}
