/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command.PlayerCommands;

import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.Command.ICommand;

/**
 *
 * @author rshum
 */
public class SelectWarriorCommand implements ICommand{

    public static final String NAME = "SELECT";
    private PlayerClient player;

    public SelectWarriorCommand() {
    }
    
    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void execute(String arguments){
        System.out.println("Executing SELECT command");
        String warriorName = arguments;
        this.player.changePlayerGamingStatus("SELECTED_WARRIOR-" + warriorName);
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
}
