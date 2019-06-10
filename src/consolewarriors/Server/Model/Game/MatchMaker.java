/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import consolewarriors.Common.Message;
import consolewarriors.Common.PlayerRanking;
import consolewarriors.Common.PlayerStats;
import java.util.ArrayList;

/**
 *
 * @author rshum
 */
public class MatchMaker {

    
    private PlayerStatsHandler playerStatsHandler;
    private ArrayList<Match> matches;
    private ArrayList<Player> queuedPlayers;
    
    private PlayerRanking ranking;
    
    public MatchMaker() {
        this.playerStatsHandler = new PlayerStatsHandler();
        this.matches = new ArrayList<>();
        this.queuedPlayers = new ArrayList<>();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public PlayerStatsHandler getPlayerStatsHandler() {
        return playerStatsHandler;
    }

    public void setPlayerStatsHandler(PlayerStatsHandler playerStatsHandler) {
        this.playerStatsHandler = playerStatsHandler;
    }

    public ArrayList<Player> getQueuedPlayers() {
        return queuedPlayers;
    }

    public void setQueuedPlayers(ArrayList<Player> queuedPlayers) {
        this.queuedPlayers = queuedPlayers;
    }

    public PlayerRanking getRanking() {
        return ranking;
    }

    public void setRanking(PlayerRanking ranking) {
        this.ranking = ranking;
    }

    
    
    // </editor-fold>
    
    public void addPlayerToQueue(Player player){
        if (queuedPlayers.size() == 1){
            Player queuedPlayer = queuedPlayers.get(0);
            createMatch(queuedPlayer, player);
            queuedPlayers.clear();
        }
        else{
            queuedPlayers.add(player);
        }
        
    }
    
    public Match createMatch(Player playerOne, Player playerTwo){
        System.out.println("Creating a new match for players: " + playerOne.getPlayerID() + " and " + playerTwo.getPlayerID());
        Match newMatch = null;
        
        PlayerStats playerOneStats = playerStatsHandler.getPlayerStats(playerOne.getPlayerID());
        PlayerStats playerTwoStats = playerStatsHandler.getPlayerStats(playerTwo.getPlayerID());
        
        ArrayList<PlayerStats> playersStats = new ArrayList<>();
        playersStats.add(playerOneStats);
        playersStats.add(playerTwoStats);
        
       //Message playerOneStatsData = new Message("PLAYER_STATS", command, queuedPlayers)
        
        
        return newMatch;
    }
    
    
    
    public void handlePlayerMessage(Message message){
        
    }
    
}
