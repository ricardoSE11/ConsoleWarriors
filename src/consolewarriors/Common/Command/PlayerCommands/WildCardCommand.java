/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Command.PlayerCommands;

import Weapons.Weapon;
import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Common.AttackGroup;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Common.Command.ICommand;
import consolewarriors.Common.Message;
import consolewarriors.Common.Shared.Warrior;
import consolewarriors.Common.Shared.WarriorWeapon;

/**
 *
 * @author rshum
 */
public class WildCardCommand implements ICommand{

    public static final String NAME = "WILDCARD";
    private PlayerClient player;
    
    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute(String arguments) {
        System.out.println("Trying to execute the WildCard command");
        String[] wildcardArguments = arguments.split("-");
        if (wildcardArguments.length == 3){
            doubleWeaponWildCard(wildcardArguments);
        }
        else if (wildcardArguments.length == 4){
            doubleWarriorWildCard(wildcardArguments);
        }
        else{
            System.out.println("Wrong parameter number for WildCard command");
        }
        
    }

    @Override
    public void setUpResource(Object object) {
        this.player = (PlayerClient) object;
    }
    
    public void individualAttack(String warriorName, String weaponName, boolean secondAttack){
        System.out.println("Wildcard attacking with:");
        System.out.println("Warrior name: " + warriorName);
        System.out.println("Weapon name: " + weaponName);

        Warrior choosenWarrior = (Warrior) player.getWarriorByName(warriorName);
        if (choosenWarrior != null) {
            Weapon choosenWeapon = choosenWarrior.getWeaponByName(weaponName);

            if (choosenWeapon == null) {
                player.changePlayerGamingStatus("NOT_SUCH_WEAPON");
                System.out.println("Null weapon");
            }

            if (((WarriorWeapon) choosenWeapon).wasUsedBy(warriorName)) {
                player.changePlayerGamingStatus("USED_WEAPON");
                System.out.println("Weapon was already used");
            } else {
                ((WarriorWeapon) choosenWeapon).addUser(warriorName);
                AttackGroup attackParameters = new AttackGroup(choosenWarrior, choosenWeapon);
                player.setAttackedWith(attackParameters);
                
                if (!secondAttack) {
                    Message attackMessage = new ClientMessage("WILDCARD_ATTACK", player.getId(), attackParameters);
                    player.sendMessage(attackMessage);
                }
                else{
                    Message attackMessage = new ClientMessage("SECOND_WILDCARD_ATTACK", player.getId(), attackParameters);
                    player.sendMessage(attackMessage);                    
                }
                
                
            }

        } else {
            player.changePlayerGamingStatus("NO_SUCH_WARRIOR");
            System.out.println("Got a null warrior");
        }
    }
    
    public void doubleWeaponWildCard(String[] warriorAndWeapons){
        String warriorName = warriorAndWeapons[0];
        String weaponOneName = warriorAndWeapons[1];
        String weaponTwoName = warriorAndWeapons[2];
        
        individualAttack(warriorName, weaponOneName , false);
        
        individualAttack(warriorName, weaponTwoName , true);
    }
    
    public void doubleWarriorWildCard(String[] warriors){
        String warriorName = warriors[0];
        String weaponOneName = warriors[1];
        
        individualAttack(warriorName, weaponOneName , false);
        
        String warriorNameTwo = warriors[2];
        String weaponTwoName = warriors[3];
        
        individualAttack(warriorNameTwo, weaponTwoName , true);
    }
    
}
