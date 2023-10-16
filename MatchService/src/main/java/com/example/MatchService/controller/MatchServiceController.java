package com.example.MatchService.controller;

import com.example.MatchService.delegate.TeamServiceDelegate;
import com.example.MatchService.model.Goal;
import com.example.MatchService.model.Match;
import com.example.MatchService.model.Team;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(value = "MatchServiceController", description = "REST Apis related to Match Entity.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "Not authorized."),
        @ApiResponse(code = 403, message = "Forbidden."),
        @ApiResponse(code = 404, message = "not Found.") })
@RestController
@RequestMapping("/matches")
public class MatchServiceController {

    private static Map<Integer, List<Match>> matchDB = new HashMap<>();
    Boolean isInit = false;
    private static int matchIdCounter = 1;

    @Autowired
    TeamServiceDelegate teamServiceDelegate;

    @PostMapping("/initMatches")
    @ApiOperation(value = "Init list of matches", tags = "initMatches")
    public void initMatches() {
        int year = 2023;

        if (!isInit) {
            List<Team> teams = teamServiceDelegate.getAllTeams();
            Random random = new Random();

            for (Team team1 : teams) {
                for (Team team2 : teams) {
                    if (team1.getTeamId() != team2.getTeamId()) {
                        Match match = new Match();
                        match.setMatchId(matchIdCounter);
                        matchIdCounter++;
                        match.setTeam1(team1);
                        match.setTeam2(team2);
                        match.setDate(new Date());
                        match.setSeason(year);

                        match.setGoals(new ArrayList<>());

                        int team1Goals = random.nextInt(6);
                        int team2Goals = random.nextInt(6);

                        for (int i = 0; i < team1Goals; i++) {
                            Goal goal = new Goal(team1.getRandomPlayerId(), team1.getTeamId());
                            match.getGoals().add(goal);
                        }

                        for (int i = 0; i < team2Goals; i++) {
                            Goal goal = new Goal(team2.getRandomPlayerId(), team2.getTeamId());
                            match.getGoals().add(goal);
                        }

                        List<Match> matches = matchDB.computeIfAbsent(year, k -> new ArrayList<>());
                        matches.add(match);
                    }
                }
            }
            isInit = true;
        }
    }


    @PostMapping
    @ApiOperation(value = "Create match entity", response = Match.class, tags = "postMatch")
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        int season = match.getSeason();
        List<Match> matches = matchDB.get(season);

        if (matches == null) {
            matches = new ArrayList<>();
            matchDB.put(season, matches);
        }

        match.setMatchId(generateUniqueMatchId());
        match.setTeam1(teamServiceDelegate.getTeam(match.getTeam1().getTeamId()));
        match.setTeam2(teamServiceDelegate.getTeam(match.getTeam2().getTeamId()));
        match.setDate(new Date());

        if (match.getGoals() == null) {
            match.setGoals(new ArrayList<>());
        }

        matches.add(match);
        return match.getTeam1() == null && match.getTeam2() == null ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null) :
                ResponseEntity.status(HttpStatus.CREATED).body(match);
    }

    @GetMapping("/{matchId}")
    @ApiOperation(value = "Get match entity", response = Match.class, tags = "getMatch")
    public ResponseEntity<Match> getMatch(@PathVariable int matchId) {
        for (List<Match> matches : matchDB.values()) {
            Optional<Match> match = matches.stream()
                    .filter(m -> m.getMatchId() == matchId)
                    .findFirst();

            if (match.isPresent()) {
                Match foundMatch = match.get();

                Team team1 = teamServiceDelegate.getTeam(foundMatch.getTeam1().getTeamId());
                Team team2 = teamServiceDelegate.getTeam(foundMatch.getTeam2().getTeamId());

                foundMatch.setTeam1(team1);
                foundMatch.setTeam2(team2);

                return ResponseEntity.ok(foundMatch);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/season/{season}")
    @ApiOperation(value = "Get match by season", response = Match.class, tags = "getMatchBySeason")
    public ResponseEntity<List<Match>> getMatchesBySeason(@PathVariable int season) {
        if (matchDB.containsKey(season)) {
            List<Match> matchesForSeason = matchDB.get(season);
            return ResponseEntity.ok(matchesForSeason);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/season/{season}/team/{teamId}")
    @ApiOperation(value = "Get match by season and team", response = Match.class, tags = "getMatchBySeasonAndTeam")
    public ResponseEntity<List<Match>> getMatchesBySeasonAndTeam(@PathVariable int season, @PathVariable int teamId) {
        List<Match> matchesForSeasonAndTeam = new ArrayList<>();

        for (Match match : matchDB.getOrDefault(season, new ArrayList<>())) {
            if (match.getTeam1().getTeamId() == teamId || match.getTeam2().getTeamId() == teamId) {
                matchesForSeasonAndTeam.add(match);
            }
        }

        if (!matchesForSeasonAndTeam.isEmpty()) {
            return ResponseEntity.ok(matchesForSeasonAndTeam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{matchId}/goal")
    @ApiOperation(value = "Create goal", response = Match.class, tags = "postGoal")
    public ResponseEntity<Goal> createGoal(@PathVariable int matchId, @RequestBody Goal goal) {
        for (List<Match> matches : matchDB.values()) {
            Optional<Match> result = matches.stream()
                    .filter(match -> match.getMatchId() == matchId)
                    .findFirst();
            if (result.isPresent()) {
                Match existingMatch = result.get();

                goal.setScorerPlayerId(goal.getScorerPlayerId());
                goal.setScoringTeamId(goal.getScoringTeamId());

                existingMatch.getGoals().add(goal);
                return ResponseEntity.status(HttpStatus.CREATED).body(goal);
            }
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping
    @ApiOperation(value = "Get all matches", tags = "getAllMatches")
    public ResponseEntity<Map<Integer, List<Match>>> getMatches() {
        return ResponseEntity.ok(matchDB);
    }

    @PutMapping("/{matchId}")
    @ApiOperation(value = "Update match",tags = "UpdateMatch")
    public ResponseEntity<Match> updateMatch(@PathVariable int matchId, @RequestBody Match updatedMatch) {
        for (List<Match> matches : matchDB.values()) {
            Optional<Match> result = matches.stream()
                    .filter(match -> match.getMatchId() == matchId)
                    .findFirst();
            if (result.isPresent()) {
                Match existingMatch = result.get();
                existingMatch.setTeam1(updatedMatch.getTeam1() == null ? existingMatch.getTeam1() : updatedMatch.getTeam1());
                existingMatch.setTeam2(updatedMatch.getTeam2() == null ? existingMatch.getTeam2() : updatedMatch.getTeam2());
                existingMatch.setDate(updatedMatch.getDate() == null ? existingMatch.getDate() : updatedMatch.getDate());
                return ResponseEntity.ok(existingMatch);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{matchId}")
    @ApiOperation(value = "Delete match", tags = "deleteMatch")
    public ResponseEntity<Void> deleteMatch(@PathVariable int matchId) {
        for (List<Match> matches : matchDB.values()) {
            Optional<Match> result = matches.stream()
                    .filter(match -> match.getMatchId() == matchId)
                    .findFirst();
            if (result.isPresent()) {
                matches.remove(result.get());
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    private int generateUniqueMatchId() {
        int newMatchId = matchIdCounter;
        matchIdCounter++;
        return newMatchId;
    }
}
