/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import consolewarriors.Client.Model.Command.ICommand;

/**
 *
 * @author rshum
 */
public interface IMatchLogger {
    
    public void logCommand(ICommand command);
            
}
