/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model.Command.PlayerCommands;

import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Client.Model.Command.ICommand;
import consolewarriors.Common.Message;

/**
 *
 * @author rshum
 */
public class ProposeTieCommand implements ICommand {

    public static final String NAME = "TIE";
    private PlayerClient player;

    public ProposeTieCommand() {
    }
    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute() {
        System.out.println("Executing TIE command");
        // Possible fix
        this.player.changePlayerGamingStatus("PROPOSING_TIE");
        Message proposeTieMessage = new ClientMessage("PROPOSING_TIE", player.getId(), null);
        player.sendMessage(proposeTieMessage);
        this.player.changePlayerGamingStatus("ENEMY_TURN");
    }

    @Override
    public void execute(String arguments) {
        
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
}
