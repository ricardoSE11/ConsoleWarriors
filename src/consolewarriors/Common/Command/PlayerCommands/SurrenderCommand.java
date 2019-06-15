/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command.PlayerCommands;

import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Common.Command.ICommand;
import consolewarriors.Common.Message;

/**
 *
 * @author rshum
 */
public class SurrenderCommand implements ICommand{

    public static final String NAME = "SURRENDER";
    private PlayerClient player;

    public SurrenderCommand() {
    }
    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute() {
        System.out.println("Executing the SURRENDER command");
        Message surrenderMessage = new ClientMessage("SURRENDER", player.getPlayerID() , null);
        player.sendMessage(surrenderMessage);
        
        player.changePlayerGamingStatus("DEFEATED");
    }

    @Override
    public void execute(String arguments) {
        
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
}
