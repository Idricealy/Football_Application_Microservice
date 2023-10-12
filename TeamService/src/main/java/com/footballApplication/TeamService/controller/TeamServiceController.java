package com.footballApplication.TeamService.controller;

import com.footballApplication.TeamService.model.Team;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class TeamServiceController {

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

    @RequestMapping(value = "/getTeam/{teamId}", method = RequestMethod.GET)
    public List<Team> getTeam(@PathVariable int teamId) {
        System.out.println("Getting Team details for " + teamId);
        List<Team> listTeam = new ArrayList<Team>();

        for (Team team : teamDB) {
            if (team.getTeamId() == teamId) {
                listTeam.add(team);
                return listTeam;
            }
        }

        listTeam.add(new Team(0, "N/A"));
        return listTeam;
    }

    @RequestMapping(value = "/postTeam", method = RequestMethod.POST)
    public void postTeam


}
