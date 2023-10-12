package com.footballApplication.PlayerService.model;

public class Player {
    private String name;
    private int playerId;
    private int teamId;

    public Player(int playerId, int teamId, String name) {
        this.name = name;
        this.playerId = teamId;
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
