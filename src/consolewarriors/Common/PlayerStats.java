/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common;

/**
 *
 * @author rshum
 */
public class PlayerStats {
    
    private int playerID;
    private String playerName;
    private int attacksTotal;
    private int succesfulAttacks;
    private int failedAttacks;
    private int kills;
    private int wins;
    private int losses;
    private int surrenders;

    public PlayerStats() {
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getAttacksTotal() {
        return attacksTotal;
    }

    public void setAttacksTotal(int attacksTotal) {
        this.attacksTotal = attacksTotal;
    }

    public int getSuccesfulAttacks() {
        return succesfulAttacks;
    }

    public void setSuccesfulAttacks(int succesfulAttacks) {
        this.succesfulAttacks = succesfulAttacks;
    }

    public int getFailedAttacks() {
        return failedAttacks;
    }

    public void setFailedAttacks(int failedAttacks) {
        this.failedAttacks = failedAttacks;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getSurrenders() {
        return surrenders;
    }

    public void setSurrenders(int surrenders) {
        this.surrenders = surrenders;
    }
    
    
    // </editor-fold>

    
}
