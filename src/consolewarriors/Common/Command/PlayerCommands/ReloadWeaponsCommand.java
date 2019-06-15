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
public class ReloadWeaponsCommand implements ICommand{

    public static final String NAME = "RELOAD";
    private PlayerClient player;
    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute() {
        System.out.println("Executing the RELOAD command");
        if (player.isOutOfWeapons()){
            System.out.println("Reloading weapons");
            player.changePlayerGamingStatus("RELOADING");
            player.reloadWarriorWeapons();
        }
        else{
            player.changePlayerGamingStatus("UNVALID_RELOAD");
        }

    }

    @Override
    public void execute(String arguments) {
        
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
}
