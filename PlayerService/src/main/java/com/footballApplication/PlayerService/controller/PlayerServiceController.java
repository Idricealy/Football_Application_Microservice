package com.footballApplication.PlayerService.controller;

import com.footballApplication.PlayerService.model.Player;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class PlayerServiceController {

    private static List<Player> teamPlayer = new ArrayList<Player>();

    static {
        Player newPlayer1 = new Player(1,1,"Idricealy Mourtadhoi");
        Player newPlayer2 = new Player(2,1,"Pierre Aymerick Aubameyang");
        Player newPlayer3 = new Player(3,2,"Vitor Vitinha");
        Player newPlayer4 = new Player(4,2,"Steve Mandanda");

        teamPlayer.add(newPlayer1);
        teamPlayer.add(newPlayer2);
        teamPlayer.add(newPlayer3);
        teamPlayer.add(newPlayer4);
    }

    @RequestMapping(value = "/getPlayer/{playerId}", method = RequestMethod.GET)
    public List<Player> getPlayer(@PathVariable int playerId) {
        System.out.println("Getting Player details for " + playerId);
        List<Player> listPlayer = new ArrayList<Player>();

        for (Player team : teamPlayer) {
            if (team.getTeamId() == playerId) {
                listPlayer.add(team);
                return listPlayer;
            }
        }

        listPlayer.add(new Player(0,0, "N/A"));
        return listPlayer;
    }
}
