package com.footballApplication.TeamService.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;

    private int teamId;

    private List<Player> players;

    public Team() {};

    public Team (int teamId, String name) {
        this.name = name;
        this.teamId = teamId;
        this.players = new ArrayList();
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

}
