/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model;

import Weapons.Weapon;
import consolewarriors.Common.CharacterType;
import consolewarriors.Common.Message;
import consolewarriors.Common.Shared.Warrior;
import consolewarriors.Common.Shared.WarriorWeapon;
import java.util.ArrayList;
import Characters.Character;
import consolewarriors.Common.ClientMessage;

/**
 *
 * @author rshum
 */
public class ServerMessageHandler implements IServerMessageHandler{

    @Override
    public void handleServerMessage(Message message, Client client) {
        // Handle the server message according to the event
        String event = message.getEvent();
        
        switch(event){
            case "ATTACK":{
                Weapon weapon = (Weapon) message.getObjectOfInterest();
                ArrayList<Character> warriors = ((PlayerClient)client).getWarriors();
                
                Integer totalDamageDealt = attackWarriors(warriors, weapon);
                Message attackResponse = new ClientMessage("ATTACK_RESPONSE", client.getId(), totalDamageDealt);
                client.sendMessage(attackResponse);
            }
            break;
            
            
        }
        
        
    }
    
    public int attackWarrior(Character warrior , Weapon weapon){
        Warrior currentWarrior = (Warrior) warrior;
        CharacterType type = currentWarrior.getType();
        WarriorWeapon currentWeapon = (WarriorWeapon)weapon;
        int damageToType = currentWeapon.getDamageForType(type);
        currentWarrior.setLife(currentWarrior.getLife() - damageToType);
        
        return damageToType;
    }
    
    public int attackWarriors(ArrayList<Character> warriors , Weapon weapon){
        int totalDamageDealt = 0;
        for (Character character : warriors) {
            totalDamageDealt += attackWarrior(character, weapon);
        }
        return totalDamageDealt;
    }
    
    
}
