package com.example.MatchService.model;

public class Player {
    private String name;
    private int playerId;

    private String country;

    private String position;

    private String strongFeet;

    private int teamId;

    public Player() {};

    public Player(String name, int playerId, String country, String position, String strongFeet) {
        this.name = name;
        this.playerId = playerId;
        this.country = country;
        this.position = position;
        this.strongFeet = strongFeet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStrongFeet() {
        return strongFeet;
    }

    public void setStrongFeet(String strongFeet) {
        this.strongFeet = strongFeet;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
