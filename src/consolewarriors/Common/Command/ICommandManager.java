/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command;

/**
 *
 * @author rshum
 */
public interface ICommandManager {
    
    public ICommand getCommand(String commandName);
    
    public void registerCommand(String commandName, Class<? extends ICommand> command);
    
}
