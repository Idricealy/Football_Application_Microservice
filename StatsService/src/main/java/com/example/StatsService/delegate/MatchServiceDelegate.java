package com.example.StatsService.delegate;

import com.example.StatsService.model.Match;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
public class MatchServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    String matchesServiceUrl = "http://match-service/matches";

    public List<Match> getMatchBySeasonAndTeam_Fallback(int season, int teamId) {
        System.out.println("CIRCUIT BREAKER ! MatchService is down or data was not initialized, the service will be avaiable soon... - " + new Date());
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "getMatchBySeasonAndTeam_Fallback")
    public List<Match> getMatchBySeasonAndTeam(int season, int teamId) {
        ResponseEntity<List<Match>> responseEntity = this.restTemplate.exchange(
                matchesServiceUrl + "/season/" + season + "/team/" + teamId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Match>>() {}
        );

        return responseEntity.getBody();
    }

    public List<Match> getMatchBySeason_Fallback(int season) {
        System.out.println("CIRCUIT BREAKER ! MatchService is down or data was not initialized, the service will be avaiable soon... - " + new Date());
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "getMatchBySeason_Fallback")
    public List<Match> getMatchBySeason(int season) {
        ResponseEntity<List<Match>> responseEntity = this.restTemplate.exchange(
                matchesServiceUrl + "/season/" + season,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Match>>() {}
        );

        return responseEntity.getBody();
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
