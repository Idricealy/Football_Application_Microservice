package com.footballApplication.TeamService.model;

public class Team {
    private String name;

    private int teamId;

    public Team (int teamId, String name) {
        this.name = name;
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
