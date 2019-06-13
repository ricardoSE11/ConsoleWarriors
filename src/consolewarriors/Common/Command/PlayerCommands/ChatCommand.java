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
public class ChatCommand implements ICommand {

    private final String NAME = "CHAT";
    private PlayerClient player;

    public ChatCommand(PlayerClient player) {
        this.player = player;
    }

    public PlayerClient getPlayer() {
        return player;
    }

    public void setPlayer(PlayerClient player) {
        this.player = player;
    }
    
    @Override
    public String getCommandName() {
        return this.NAME;
    }

    @Override
    public void execute() {
    }

    @Override
    public void execute(String arguments) {
        Message chatMessage = new ClientMessage("CHAT", player.getId(), arguments);
        player.sendMessage(chatMessage);
    }
    
}
