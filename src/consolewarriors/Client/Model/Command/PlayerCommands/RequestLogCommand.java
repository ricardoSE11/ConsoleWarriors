/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model.Command.PlayerCommands;

import consolewarriors.Client.Model.Command.ICommand;
import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Common.Message;

/**
 *
 * @author rshum
 */
public class RequestLogCommand implements ICommand{
    
    public static final String NAME = "LOG";
    private PlayerClient player;

    public RequestLogCommand() {
    }

    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute() {
        System.out.println("Executing the LOG command");
        Message requestLogMessage = new ClientMessage("LOG_REQUEST", player.getPlayerID(), null);
        player.sendMessage(requestLogMessage);
    }

    @Override
    public void execute(String arguments) {
        
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
    
    
}
