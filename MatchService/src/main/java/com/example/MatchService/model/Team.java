package com.example.MatchService.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {
    private String name;

    private int teamId;

    private List<Player> players;

    public Team() {};

    public Team(int teamId, String name) {
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

    public int getRandomPlayerId() {
        if (players != null && !players.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(players.size());
            Player randomPlayer = players.get(randomIndex);
            return randomPlayer.getPlayerId();
        }
        return -1;
    }

}
