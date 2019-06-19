/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model.Command.PlayerCommands;

import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Client.Model.Command.ICommand;

/**
 *
 * @author rshum
 */
public class NotFoundCommand implements ICommand {

    public static final String NAME = "NOT FOUND";
    private PlayerClient player;

    public NotFoundCommand() {
    }
    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute(String arguments) {
        System.out.println("Command not found");
    }

    @Override
    public void execute() {
        System.out.println("Command not found");
    }
    
    @Override
    public void setUpResource(Object object) {
        // Not used.
        //this.player = (PlayerClient) object;
    }
    
}
