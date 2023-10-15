package com.example.MatchService.delegate;

import com.example.MatchService.model.Match;
import com.example.MatchService.model.Team;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeamServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    String teamServiceUrl = "http://localhost:9001/teams";

    private List<Team> getAllTeams_Fallback() {
        System.out.println("CIRCUIT BREAKER ! TeamService is down, the service will be enabled soon... - " + new Date());
        List<Team> fallbackTeams = new ArrayList<>();
        return fallbackTeams;
    }

    @HystrixCommand(fallbackMethod = "getAllTeams_Fallback")
    public List<Team> getAllTeams() {
        System.out.println("Getting all teams from TeamService");

        ResponseEntity<List<Team>> responseEntity = this.restTemplate.exchange(
                teamServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Team>>() {});

        return responseEntity.getBody();
    }

    private Team getTeam_Fallback(int teamId) {
        System.out.println("CIRCUIT BREAKER ! TeamService is down, the service will be enabled soon... - " + new Date());
        return null;
    }

    @HystrixCommand(fallbackMethod = "getTeam_Fallback")
    public Team getTeam(int teamId) {
        System.out.println("Getting team from TeamService" + teamId);

        ResponseEntity<Team> responseEntity = this.restTemplate.exchange(
                teamServiceUrl + "/" + teamId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Team>() {});

        return responseEntity.getBody();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
