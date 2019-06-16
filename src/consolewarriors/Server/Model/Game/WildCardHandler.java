/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import java.util.Random;
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
        int minutes = 1;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // After minutes...
                isWildCardReady = true;
                System.out.println("After " + minutes + " minutes, I generated the Wildcard");
            }
        }, minutes * 60 * 1000, minutes * 60 * 1000);
    }
    
    @Override
    public boolean grantedWildCard() {
        int maxValue = 5;
        int minValue = -5;
        Random random = new Random();
        int randomNumber = random.nextInt((maxValue - minValue) + 1) + minValue;
        System.out.println("Random number was: " + randomNumber);
        return (randomNumber > 0);
    }

    @Override
    public boolean isWildCardReady() {
        return this.isWildCardReady;
    }
    
}
