/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public class CommandManager {

    private HashMap<String, Class<? extends ICommand>> commands;
    
    public CommandManager() {
        commands = new HashMap<>();
                
    }
    
    public ICommand getCommand(String commandName){
        ICommand requestedCommand = null;
        if (commands.containsKey(commandName)){
            try {
                return commands.get(commandName).newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(CommandManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return requestedCommand;
    }
    
    public void registerCommand(String commandName, Class<? extends ICommand> command){
        commands.put(commandName, command);
    }
    
    public void setUpPlayerCommands(){}
    
    public void setUpServerCommands(){}
    
}
