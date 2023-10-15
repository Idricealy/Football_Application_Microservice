package com.example.StatsService.controller;

import com.example.StatsService.delegate.MatchServiceDelegate;
import com.example.StatsService.model.Goal;
import com.example.StatsService.model.Match;
import com.example.StatsService.model.StatsPlayer;
import com.example.StatsService.model.StatsTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsServiceController {

    @Autowired
    MatchServiceDelegate matchServiceDelegate;

    @GetMapping("/season/{season}/team/{teamId}")
    public ResponseEntity<StatsTeam> getGoalScoredByTeamAndSeason(@PathVariable int season, @PathVariable int teamId){
        List<Match> getMatchBySeasonAndTeam = matchServiceDelegate.getMatchBySeasonAndTeam(season, teamId);

        int totalGoals = 0;
        StatsTeam statsTeam = new StatsTeam();

        for (Match match : getMatchBySeasonAndTeam) {
            if (match.getTeam1().getTeamId() == teamId || match.getTeam2().getTeamId() == teamId) {
                List<Goal> goals = match.getGoals();
                for (Goal goal : goals) {
                    if (goal.getScoringTeamId() == teamId) {
                        totalGoals++;
                    }
                }
            }
        }

        statsTeam.setSeason(season);
        statsTeam.setTeamId(teamId);
        statsTeam.setGoalScored(totalGoals);

        return ResponseEntity.ok(statsTeam);
    }

    @GetMapping("/season/{season}/player/{playerId}")
    public ResponseEntity<StatsPlayer> getGoalScoredByPlayerAndSeason(@PathVariable int season, @PathVariable int playerId){
        List<Match> getMatchBySeason = matchServiceDelegate.getMatchBySeason(season);

        StatsPlayer stats = new StatsPlayer();
        int totalGoals = 0;

        for (Match match : getMatchBySeason) {
            for (Goal goal : match.getGoals()) {
                if (goal.getScorerPlayerId() == playerId) {
                    totalGoals++;
                }
            }
        }

        stats.setSeason(season);
        stats.setPlayerId(playerId);
        stats.setGoalScored(totalGoals);

        return ResponseEntity.ok(stats);
    }

}
