/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command.PlayerCommands;

import Weapons.Weapon;
import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Common.Command.ICommand;
import consolewarriors.Common.Message;
import consolewarriors.Common.Shared.Warrior;

/**
 *
 * @author rshum
 */
public class AttackCommand implements ICommand {
    
    public static final String NAME = "ATTACK";
    private PlayerClient player; 

    public AttackCommand(PlayerClient player) {
        this.player = player;
    }
    
    public AttackCommand(){
        
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
        System.out.println("Attack command executing");
        String[] attackInfo = arguments.split("-");
        String warriorName = attackInfo[0];
        String weaponName = attackInfo[1];
        
        System.out.println("Warrior name: " + warriorName); 
        System.out.println("Weapon name: " + weaponName);
        
        Warrior choosenWarrior = (Warrior) player.getWarriorByName(warriorName);
        if (choosenWarrior != null){
            Weapon choosenWeapon = choosenWarrior.getWeaponByName(weaponName);
            
            if (choosenWeapon == null){
                System.out.println("Null weapon .-. ");
            }
            
            Message attackMessage = new ClientMessage("ATTACK", player.getId(), choosenWeapon);
            player.sendMessage(attackMessage);
        }
        else{
            System.out.println("Got a null warrior");
        }
        
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
}
