package com.example.StatsService.model;

public class Goal {
    private static int goalIdCounter = 1;

    private int goalId;
    private int scorerPlayerId;
    private int scoringTeamId;

    public Goal() {
        this.goalId = goalIdCounter;
        goalIdCounter++;
    }

    public Goal(int scorerPlayerId, int scoringTeamId) {
        super();
        this.scorerPlayerId = scorerPlayerId;
        this.scoringTeamId = scoringTeamId;
    }

    public static int getGoalIdCounter() {
        return goalIdCounter;
    }

    public int getScorerPlayerId() {
        return scorerPlayerId;
    }

    public void setScorerPlayerId(int scorerPlayerId) {
        this.scorerPlayerId = scorerPlayerId;
    }

    public int getScoringTeamId() {
        return scoringTeamId;
    }

    public void setScoringTeamId(int scoringTeamId) {
        this.scoringTeamId = scoringTeamId;
    }
}


