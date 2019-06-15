/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author rshum
 */
public class WildCardHandler implements IWildCardHandler{

    private boolean isWildCardReady;

    public WildCardHandler() {
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public boolean isIsWildCardReady() {
        return isWildCardReady;
    }

    public void setIsWildCardReady(boolean isWildCardReady) {
        this.isWildCardReady = isWildCardReady;
    }    
    
    // </editor-fold>
    
    
    @Override
    public void startTimer() {
        Timer timer = new Timer();
        int minutes = 2;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                System.out.println("After " + minutes + " minutes, I generated the Wildcard");
            }
        }, minutes * 60 * 1000, minutes * 60 * 1000);
    }
    
    @Override
    public boolean grantedWildCard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isWildCardReady() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
