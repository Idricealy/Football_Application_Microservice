package com.footballApplication.TeamService.delegate;

import com.footballApplication.TeamService.model.Player;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    String playerServiceUrl = "http://localhost:9002/players";

    private List<Player> getPlayersByTeam_Fallback(int teamId) {
        System.out.println("CIRCUIT BREAKER ! PlayerService is down, the service will be enabled soon... - " + new Date());
        List<Player> fallbackPlayers = new ArrayList<>();
        return fallbackPlayers;
    }

    @HystrixCommand(fallbackMethod = "getPlayersByTeam_Fallback")
    public List<Player> getPlayersByTeam(int teamId) {
        System.out.println("Getting all players by team from PlayerService");

        ResponseEntity<List<Player>> responseEntity = this.restTemplate.exchange(
                playerServiceUrl + "/team/{teamId}/players",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Player>>() {},
                teamId
        );

        return responseEntity.getBody();
    }


    private List<Player> getAllPlayersFromPlayerService_Fallback() {
        System.out.println("CIRCUIT BREAKER ! PlayerService is down, the service will be enabled soon... - " + new Date());
        List<Player> fallbackPlayers = new ArrayList<>();
        return fallbackPlayers;
    }
    @HystrixCommand(fallbackMethod = "getAllPlayersFromPlayerService_Fallback")
    public List<Player> getAllPlayersFromPlayerService() {
        System.out.println("Getting all players from PlayerService");
        ResponseEntity<List<Player>> responseEntity = this.restTemplate.exchange(
                playerServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Player>>() {});

        return responseEntity.getBody();
    }

    private void updatePlayer_Fallback(Player player) {
        System.out.println("CIRCUIT BREAKER ! PlayerService is down, the service will be enabled soon... - " + new Date());
    }
    @HystrixCommand(fallbackMethod = "updatePlayer_Fallback")
    public void updatePlayer(Player player) {
        System.out.println("Update team for player " + player.getName());

        ResponseEntity<Player> updatedPlayerResponse = this.restTemplate.exchange(
                playerServiceUrl + '/' + player.getPlayerId(),
                HttpMethod.PUT,
                new HttpEntity<>(player),
                new ParameterizedTypeReference<Player>() {
                });
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
