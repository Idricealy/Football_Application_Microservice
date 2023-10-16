package com.footballApplication.TeamService.controller;

import com.footballApplication.TeamService.delegate.PlayerServiceDelegate;
import com.footballApplication.TeamService.model.Team;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.footballApplication.TeamService.model.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "TeamServiceController", description = "REST Apis related to Team Entity.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "Not authorized."),
        @ApiResponse(code = 403, message = "Forbidden."),
        @ApiResponse(code = 404, message = "not Found.") })
@RestController
@RequestMapping("/teams")
public class TeamServiceController {
    @Autowired
    PlayerServiceDelegate playerService;

    Boolean isInit = false;

    private static List<Team> teamDB = new ArrayList<Team>();

    static {
        Team newTeam1 = new Team(1,"Olympique de Marseille");
        Team newTeam2 = new Team(2,"Hassania Agadir");
        Team newTeam3 = new Team(3,"Wydad Football Club");
        Team newTeam4 = new Team(4,"Tottenham Hotspurs");

        teamDB.add(newTeam1);
        teamDB.add(newTeam2);
        teamDB.add(newTeam3);
        teamDB.add(newTeam4);
    }

    @PostMapping("/addPlayersOnTeams")
    @ApiOperation(value = "Init list of teams", tags = "initTeams")
    public void initTeamsWithPlayers() {
        if(!isInit) {
            List<Player> players = playerService.getAllPlayersFromPlayerService();

            if(players.size() > 1) {
                int numTeams = teamDB.size();

                for (int i = 0; i < players.size(); i++) {
                    int teamIndex = i % numTeams;
                    players.get(i).setTeamId(teamDB.get(teamIndex).getTeamId());
                    playerService.updatePlayer(players.get(i));
                    teamDB.get(teamIndex).addPlayer(players.get(i));
                }
                isInit = true;
            }
        }
    }

    @PostMapping
    @ApiOperation(value = "Create new team", tags = "postTeam")
    public ResponseEntity<Team> postTeam(@RequestBody Team team) {
        if (team.getTeamId() == 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        teamDB.add(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }

    @GetMapping("/{teamId}")
    @ApiOperation(value = "Get team by id", tags = "getTeam")
    public ResponseEntity<Team> getTeam(@PathVariable int teamId) {
        Optional<Team> result = teamDB.stream()
                .filter(team -> team.getTeamId() == teamId)
                .findFirst();
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    @ApiOperation(value = "Get all teams", tags = "getAllTeams")
    public ResponseEntity<List<Team>> getTeams() {
        for (Team team : teamDB) {
            List<Player> playersInTeam = playerService.getPlayersByTeam(team.getTeamId());
            team.setPlayers(playersInTeam);
        }
        return ResponseEntity.ok(teamDB);
    }

    @PutMapping("/{teamId}")
    @ApiOperation(value = "Update team", tags = "updateTeam")
    public ResponseEntity<Team> updateTeam(@PathVariable int teamId, @RequestBody Team updatedTeam) {
        Optional<Team> result = teamDB.stream()
                .filter(team -> team.getTeamId() == teamId && teamId != 0)
                .findFirst();
        if (result.isPresent()) {
            Team existingTeam = result.get();
            existingTeam.setName(updatedTeam.getName() == "" ? result.get().getName() : updatedTeam.getName());
            return ResponseEntity.ok(existingTeam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{teamId}")
    @ApiOperation(value = "delete Team", tags = "deleteTeam")
    public ResponseEntity<Void> deleteTeam(@PathVariable int teamId) {
        Optional<Team> result = teamDB.stream()
                .filter(team -> team.getTeamId() == teamId)
                .findFirst();
        if (result.isPresent()) {
            teamDB.remove(result.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{teamId}/players")
    @ApiOperation(value = "Get players by team", tags = "getPlayersByTeam")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable int teamId) {
        Optional<Team> result = teamDB.stream()
                .filter(team -> team.getTeamId() == teamId)
                .findFirst();
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get().getPlayers());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
