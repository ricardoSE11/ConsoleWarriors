/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model.Command.PlayerCommands;

import Weapons.Weapon;
import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.AttackGroup;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Client.Model.Command.ICommand;
import consolewarriors.Common.Message;
import consolewarriors.Common.Shared.Warrior;
import consolewarriors.Common.Shared.WarriorWeapon;

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
                player.changePlayerGamingStatus("NOT_SUCH_WEAPON");
                System.out.println("Null weapon");
            }
            
            if (((WarriorWeapon)choosenWeapon).wasUsedBy(warriorName)){
                player.changePlayerGamingStatus("USED_WEAPON");
                System.out.println("Weapon was already used");
            }
            else{
                ((WarriorWeapon) choosenWeapon).addUser(warriorName);
                AttackGroup attackParameters = new AttackGroup(choosenWarrior, choosenWeapon);
                player.setAttackedWith(attackParameters);
                Message attackMessage = new ClientMessage("ATTACK", player.getId(), attackParameters);
                player.sendMessage(attackMessage);
            }

        }
        else{
            player.changePlayerGamingStatus("NO_SUCH_WARRIOR");
            System.out.println("Got a null warrior");
        }
        
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
}
