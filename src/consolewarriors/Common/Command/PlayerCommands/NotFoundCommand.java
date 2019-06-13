/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command.PlayerCommands;

import consolewarriors.Common.Command.ICommand;

/**
 *
 * @author rshum
 */
public class NotFoundCommand implements ICommand {

    private final String commandName = "NOT FOUND";

    public NotFoundCommand() {
    }
    
    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public void execute(String arguments) {
        System.out.println("Command not found");
    }

    @Override
    public void execute() {
        System.out.println("Command not found");
    }
    
}
