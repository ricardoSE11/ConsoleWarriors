/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

/**
 *
 * @author rshum
 */
public interface IWildCardHandler {
    
    public void startTimer();
    
    public boolean grantedWildCard();
    
    public boolean isWildCardReady();
    
}
