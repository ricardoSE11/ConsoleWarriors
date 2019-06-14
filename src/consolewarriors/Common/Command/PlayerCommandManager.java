/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command;

import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.Command.PlayerCommands.AttackCommand;
import consolewarriors.Common.Command.PlayerCommands.ChatCommand;
import consolewarriors.Common.Command.PlayerCommands.NotFoundCommand;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public class PlayerCommandManager implements ICommandManager{

    
    public PlayerClient player; // ask if is the right way to go
    private HashMap<String, Class<? extends ICommand>> commands;
    
    public PlayerCommandManager() {
        commands = new HashMap<>();
        setUpPlayerCommands();
    }

    public PlayerClient getPlayer() {
        return player;
    }

    public void setPlayer(PlayerClient player) {
        this.player = player;
    }
    
    public ICommand getCommand(String commandName){
        ICommand requestedCommand = null;
        if (commands.containsKey(commandName)){
            try {
                ICommand selectedCommand = commands.get(commandName).newInstance();
                selectedCommand.setUpResource(player);
                return selectedCommand;
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(PlayerCommandManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return requestedCommand;
    }
    
    public void registerCommand(String commandName, Class<? extends ICommand> command){
        commands.put(commandName, command);
    }
    
    public void setUpPlayerCommands(){
        registerCommand(NotFoundCommand.NAME , NotFoundCommand.class);
        registerCommand(AttackCommand.NAME, AttackCommand.class);
        registerCommand(ChatCommand.NAME , ChatCommand.class);
    }
    
}
