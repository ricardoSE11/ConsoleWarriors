/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common;

import java.util.ArrayList;

/**
 *
 * @author rshum
 */
public class PlayerRanking {
    //it is better a HashMap than ArrayList here :v
    private ArrayList<PlayerStats> ranking;

    public PlayerRanking() {
        this.ranking = new ArrayList<>();
    }

    public ArrayList<PlayerStats> getRanking() {
        return ranking;
    }

    public void setRanking(ArrayList<PlayerStats> ranking) {
        this.ranking = ranking;
    }
    
    public boolean existsPlayer(String username){
        for(PlayerStats p: ranking){
            if(p.getPlayerName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void createNewPlayerStats(String username) {
        PlayerStats new_player = new PlayerStats(username, 0, 0, 0, 0, 0, 0);
        ranking.add(new_player);
    }

    public String getPlayerStats(String username) {
        String result = "";
        for(PlayerStats p: ranking){
            if(p.getPlayerName().equals(username)){
                result += "The stats of the player "+p.getPlayerName()+" are:\n";
                result += "Wins: " + Integer.toString(p.getWins()) + ".\n";
                result += "Losses: " + Integer.toString(p.getLosses()) + ".\n";
                result += "Kills: " + Integer.toString(p.getKills()) + ".\n";
                result += "Surrenders: " + Integer.toString(p.getSurrenders()) + ".\n";
                result += "Successful attacks: " + Integer.toString(p.getSuccesfulAttacks()) + ".\n";
                result += "Failed attacks: " + Integer.toString(p.getFailedAttacks()) + ".\n";
                break;
            }
        }
        return result;
    }
    
    //This is used for update stats
    public PlayerStats getPlayer(String username){
        PlayerStats player = null;
        for(PlayerStats p: ranking){
            if(p.getPlayerName().equals(username)){
                player = p;
                break;
            }
        }
        return player;
    }
    
    
    
    
}
