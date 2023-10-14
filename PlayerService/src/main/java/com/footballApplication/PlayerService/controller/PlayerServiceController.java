package com.footballApplication.PlayerService.controller;

import com.footballApplication.PlayerService.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/players")
public class PlayerServiceController {

    private static List<Player> teamPlayer = new ArrayList<Player>();

    static {
        Player newPlayer1 = new Player("Idricealy Mourtadhoi",1,"Comoros","GK","L");
        Player newPlayer2 = new Player("Pierre Aymerick Aubameyang",2,"Gabon","ST","R");
        Player newPlayer3 = new Player("Vitor Vitinha",3,"Portugal","ST","R");
        Player newPlayer4 = new Player("Steve Mandanda",4,"France","GK","R");
        Player newPlayer5 = new Player("Robert Lewandowski", 5, "Poland", "ST", "R");
        Player newPlayer6 = new Player("Kylian Mbappe", 6, "France", "ST", "R");
        Player newPlayer7 = new Player("Sergio Ramos", 7, "Spain", "CB", "R");
        Player newPlayer8 = new Player("Virgil van Dijk", 8, "Netherlands", "CB", "R");
        Player newPlayer9 = new Player("Luka Modric", 9, "Croatia", "CM", "R");
        Player newPlayer10 = new Player("Mohamed Salah", 10, "Egypt", "RW", "L");
        Player newPlayer11 = new Player("Antoine Griezmann", 11, "France", "CF", "L");
        Player newPlayer12 = new Player("Eden Hazard", 12, "Belgium", "LW", "R");
        Player newPlayer13 = new Player("Harry Kane", 13, "England", "ST", "R");
        Player newPlayer14 = new Player("Joshua Kimmich", 14, "Germany", "CM", "R");
        Player newPlayer15 = new Player("Bruno Fernandes", 15, "Portugal", "CAM", "R");
        Player newPlayer16 = new Player("Sadio Mane", 16, "Senegal", "LW", "R");
        Player newPlayer17 = new Player("Thiago Silva", 17, "Brazil", "CB", "R");
        Player newPlayer18 = new Player("Sergio Busquets", 18, "Spain", "CDM", "R");
        Player newPlayer19 = new Player("Raheem Sterling", 19, "England", "LW", "R");
        Player newPlayer20 = new Player("Karim Benzema", 20, "France", "ST", "R");
        Player newPlayer21 = new Player("Casemiro", 21, "Brazil", "CDM", "R");
        Player newPlayer22 = new Player("Marco Reus", 22, "Germany", "LW", "R");
        Player newPlayer23 = new Player("Gianluigi Donnarumma", 23, "Italy", "GK", "L");
        Player newPlayer24 = new Player("Toni Kroos", 24, "Germany", "CM", "R");
        Player newPlayer25 = new Player("Jan Oblak", 25, "Slovenia", "GK", "R");
        Player newPlayer26 = new Player("Frenkie de Jong", 26, "Netherlands", "CM", "R");
        Player newPlayer27 = new Player("Paul Pogba", 27, "France", "CM", "R");
        Player newPlayer28 = new Player("Alisson Becker", 28, "Brazil", "GK", "L");
        Player newPlayer29 = new Player("Roberto Firmino", 29, "Brazil", "CF", "R");
        Player newPlayer30 = new Player("Thibaut Courtois", 30, "Belgium", "GK", "R");
        Player newPlayer31 = new Player("Vinicius Jr.", 31, "Brazil", "LW", "R");
        Player newPlayer32 = new Player("N'Golo Kanté", 32, "France", "CDM", "R");
        Player newPlayer33 = new Player("Sergio Aguero", 33, "Argentina", "ST", "R");
        Player newPlayer34 = new Player("Erling Haaland", 34, "Norway", "ST", "R");
        Player newPlayer35 = new Player("Kai Havertz", 35, "Germany", "CAM", "R");
        Player newPlayer36 = new Player("Gareth Bale", 36, "Wales", "RW", "L");
        Player newPlayer37 = new Player("Manuel Neuer", 37, "Germany", "GK", "R");
        Player newPlayer38 = new Player("Kalidou Koulibaly", 38, "Senegal", "CB", "R");
        Player newPlayer39 = new Player("Jadon Sancho", 39, "England", "RW", "R");
        Player newPlayer40 = new Player("Ederson Moraes", 40, "Brazil", "GK", "R");


        teamPlayer.add(newPlayer1);
        teamPlayer.add(newPlayer2);
        teamPlayer.add(newPlayer3);
        teamPlayer.add(newPlayer4);
        teamPlayer.add(newPlayer5);
        teamPlayer.add(newPlayer6);
        teamPlayer.add(newPlayer7);
        teamPlayer.add(newPlayer8);
        teamPlayer.add(newPlayer9);
        teamPlayer.add(newPlayer10);
        teamPlayer.add(newPlayer11);
        teamPlayer.add(newPlayer12);
        teamPlayer.add(newPlayer13);
        teamPlayer.add(newPlayer14);
        teamPlayer.add(newPlayer15);
        teamPlayer.add(newPlayer16);
        teamPlayer.add(newPlayer17);
        teamPlayer.add(newPlayer18);
        teamPlayer.add(newPlayer19);
        teamPlayer.add(newPlayer20);
        teamPlayer.add(newPlayer21);
        teamPlayer.add(newPlayer22);
        teamPlayer.add(newPlayer23);
        teamPlayer.add(newPlayer24);
        teamPlayer.add(newPlayer25);
        teamPlayer.add(newPlayer26);
        teamPlayer.add(newPlayer27);
        teamPlayer.add(newPlayer28);
        teamPlayer.add(newPlayer29);
        teamPlayer.add(newPlayer30);
        teamPlayer.add(newPlayer31);
        teamPlayer.add(newPlayer32);
        teamPlayer.add(newPlayer33);
        teamPlayer.add(newPlayer34);
        teamPlayer.add(newPlayer35);
        teamPlayer.add(newPlayer36);
        teamPlayer.add(newPlayer37);
        teamPlayer.add(newPlayer38);
        teamPlayer.add(newPlayer39);
        teamPlayer.add(newPlayer40);
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        return ResponseEntity.ok(teamPlayer);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable int playerId) {
        Player player = findPlayerById(playerId);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/team/{teamId}/players")
    public ResponseEntity<List<Player>> getPlayersByTeamId(@PathVariable int teamId) {
        List<Player> playersInTeam = new ArrayList<>();

        for (Player player : teamPlayer) {
            if (player.getTeamId() == teamId) {
                playersInTeam.add(player);
            }
        }

        if (!playersInTeam.isEmpty()) {
            return ResponseEntity.ok(playersInTeam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (player.getPlayerId() == 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        teamPlayer.add(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<Player> updatePlayer(@PathVariable int playerId, @RequestBody Player updatedPlayer) {
        System.out.println(updatedPlayer.getPlayerId());
        Player existingPlayer = findPlayerById(playerId);
        if (existingPlayer != null) {
            existingPlayer.setName(updatedPlayer.getName() == null ? existingPlayer.getName() : updatedPlayer.getName());
            existingPlayer.setPlayerId(updatedPlayer.getPlayerId() == 0 ? existingPlayer.getPlayerId() : updatedPlayer.getPlayerId());
            existingPlayer.setCountry(updatedPlayer.getCountry() == null ? existingPlayer.getCountry() : updatedPlayer.getCountry());
            existingPlayer.setPosition(updatedPlayer.getPosition() == null ? existingPlayer.getPosition() : updatedPlayer.getPosition());
            existingPlayer.setStrongFeet(updatedPlayer.getStrongFeet() == null ? existingPlayer.getStrongFeet() : updatedPlayer.getStrongFeet());
            existingPlayer.setTeamId(updatedPlayer.getTeamId() == 0 ? existingPlayer.getTeamId() : updatedPlayer.getTeamId());

            System.out.println(existingPlayer.getTeamId() + " for "+ existingPlayer.getName());
            return ResponseEntity.ok(existingPlayer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable int playerId) {
        Player player = findPlayerById(playerId);
        if (player != null) {
            teamPlayer.remove(player);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Player findPlayerById(int playerId) {
        for (Player player : teamPlayer) {
            if (player.getPlayerId() == playerId) {
                return player;
            }
        }
        return null;
    }
}
