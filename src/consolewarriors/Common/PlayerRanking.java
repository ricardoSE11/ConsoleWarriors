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
    
    
    
    
}
