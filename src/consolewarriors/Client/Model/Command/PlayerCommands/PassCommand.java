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
public class PassCommand implements ICommand{

    public static final String NAME = "PASS";
    private PlayerClient player;

    public PassCommand() {
    }
    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute() {
        System.out.println("Executing PASS command");
        Message passTurnMessage = new ClientMessage("PASS", player.getId(), null);
        player.sendMessage(passTurnMessage);
        player.changePlayerGamingStatus("ENEMY_TURN");
    }

    @Override
    public void execute(String arguments) {
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
}
