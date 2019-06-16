/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import consolewarriors.Common.PlayerRanking;
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
        
        newMatch = new Match(playerOne, playerTwo, ranking);
        
        // <editor-fold defaultstate="collapsed" desc="Fix me">
        
//        PlayerStats playerOneStats = playerStatsHandler.getPlayerStats(playerOne.getPlayerID());
//        PlayerStats playerTwoStats = playerStatsHandler.getPlayerStats(playerTwo.getPlayerID());
//        
//        ArrayList<PlayerStats> playerStatsForPlayerOne = new ArrayList<>();
//        playerStatsForPlayerOne.add(playerOneStats);
//        playerStatsForPlayerOne.add(playerTwoStats);
//        Message playerOneStatsData = new ServerMessage("PLAYER_STATS", playerStatsForPlayerOne);
//        playerOne.getClientThread().sendMessageToClient(playerOneStatsData);
//        
//        ArrayList<PlayerStats> playerStatsForPlayerTwo = new ArrayList<>();
//        playerStatsForPlayerTwo.add(playerTwoStats);
//        playerStatsForPlayerTwo.add(playerOneStats);
//        Message playerTwoStatsData = new ServerMessage("PLAYER_STATS", playerStatsForPlayerTwo);
//        playerTwo.getClientThread().sendMessageToClient(playerTwoStatsData);
//        
//        PlayerRanking playerRanking = null;
//        // Way in which we load the player rankings
//        Message playerRankingMessage = new ServerMessage("PLAYER_RANKING" , playerRanking);
//        playerOne.getClientThread().sendMessageToClient(playerRankingMessage);
//        playerTwo.getClientThread().sendMessageToClient(playerRankingMessage);
        
	// </editor-fold>
        
        this.matches.add(newMatch);
        return newMatch;
    }
    
    public Match getPlayerMatch(int playerID){
        Match playerMatch = null;
        for (Match currentMatch : matches){
            if (currentMatch.getPlayerOne().getPlayerID() == playerID || currentMatch.getPlayerTwo().getPlayerID() == playerID){
                System.out.println("FOUND THE MATCH");
                playerMatch = currentMatch;
            }
        }
        return playerMatch;
    }
    
    
}
