package com.example.StatsService.controller;

import com.example.StatsService.delegate.MatchServiceDelegate;
import com.example.StatsService.model.Goal;
import com.example.StatsService.model.Match;
import com.example.StatsService.model.StatsPlayer;
import com.example.StatsService.model.StatsTeam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "StatsServiceController", description = "REST Apis related to Stats Entity.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "Not authorized."),
        @ApiResponse(code = 403, message = "Forbidden."),
        @ApiResponse(code = 404, message = "not Found.") })
@RequestMapping("/stats")
public class StatsServiceController {

    @Autowired
    MatchServiceDelegate matchServiceDelegate;

    @GetMapping("/season/{season}/team/{teamId}")
    @ApiOperation(value = "Get Goal scored by team and season", tags = "getGoalScoredByTeamAndSeason")
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
    @ApiOperation(value = "Get Goal scored by player and season", tags = "getGoalScoredByPlayerAndSeason")
    public ResponseEntity<StatsPlayer> getGoalScoredByPlayerAndSeason(@PathVariable int season, @PathVariable int playerId){
        List<Match> getMatchBySeason = matchServiceDelegate.getMatchBySeason(season);

        StatsPlayer stats = new StatsPlayer();
        int totalGoals = 0;

        if(getMatchBySeason.size() > 1){
            for (Match match : getMatchBySeason) {
                if(match.getGoals().size() > 1){
                    for (Goal goal : match.getGoals()) {
                        if (goal.getScorerPlayerId() == playerId) {
                            totalGoals++;
                        }
                    }
                }
            }
        }

        stats.setSeason(season);
        stats.setPlayerId(playerId);
        stats.setGoalScored(totalGoals);

        return ResponseEntity.ok(stats);
    }

}
