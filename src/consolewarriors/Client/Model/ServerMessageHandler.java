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
import consolewarriors.Common.AttackGroup;
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
            case "WRONG_TURN":{
                ((PlayerClient) client).changePlayerGamingStatus("WRONG_TURN");
            }
            break;
            
            // We are receiving an attack
            case "ATTACK":{
                System.out.println("Receiving attack");
                AttackGroup attackParameters =  (AttackGroup) message.getObjectOfInterest();
                ArrayList<Character> warriors = ((PlayerClient)client).getWarriors();
                
                ((PlayerClient) client).setAttackedBy(attackParameters);
                Weapon weapon = (Weapon) attackParameters.getWeapon();
                Warrior warrior = (Warrior) attackParameters.getWarrior();
                
                Integer totalDamageDealt = attackWarriors(warriors, weapon);
                Message attackResponse = new ClientMessage("ATTACK_RESPONSE", client.getId(), totalDamageDealt);
                client.sendMessage(attackResponse);
                
                if (((PlayerClient)client).lostTheMatch()){
                    Message lostMessage = new ClientMessage("LOST", client.getId(), null);
                    client.sendMessage(lostMessage);
                }
            }
            break;
            
            // Receiving the damage we dealt on the attack
            case "ATTACK_RESPONSE":{
                System.out.println("Getting the damage dealt");
                int damageDealt = (int) message.getObjectOfInterest();
                ((PlayerClient) client).setDamageDealtOnAttack(damageDealt);
            }
            break;
            
            // Receiving a message from the enemy
            case "CHAT":{
                System.out.println("Got your message and we are at:" + event);
                String messageText = (String) message.getObjectOfInterest();
                ((PlayerClient) client).addChatMessage("ENEMY-" + messageText);
            }
            break;
            
            // Enemy is proposing a tie
            case "TIE_PROPOSAL":{
                System.out.println("Enemy is proposing a tie");
                ((PlayerClient) client).changePlayerGamingStatus("RESPONDING_TIE_REQUEST");
            }
            break;
            
            // Tie was accepted
            case "TIE_PROPOSSAL_ACCEPTED": {
                System.out.println("Enemy accepted the tie");
                ((PlayerClient) client).changePlayerGamingStatus("GAME_TIED");
            }
            break;
            
            case "TIE_PROPOSSAL_DENIED":{
                System.out.println("Enemy did not accepted the tie");
                ((PlayerClient) client).changePlayerGamingStatus("TIE_DENIED");
            }
            break;
            
            case "VICTORY":{
                System.out.println("Enemy surrendered");
                ((PlayerClient) client).changePlayerGamingStatus("WINNER");
            }
            break;
            
            case "WILDCARD_ATTACK":{
                System.out.println("Receiving wildcard attack");
                AttackGroup attackParameters = (AttackGroup) message.getObjectOfInterest();
                ArrayList<Character> warriors = ((PlayerClient) client).getWarriors();

                ((PlayerClient) client).setAttackedBy(attackParameters);
                Weapon weapon = (Weapon) attackParameters.getWeapon();
                Warrior warrior = (Warrior) attackParameters.getWarrior();

                Integer totalDamageDealt = attackWarriors(warriors, weapon);
                Message attackResponse = new ClientMessage("ATTACK_RESPONSE", client.getId(), totalDamageDealt);
                client.sendMessage(attackResponse);

                if (((PlayerClient) client).lostTheMatch()) {
                    Message lostMessage = new ClientMessage("LOST", client.getId(), null);
                    client.sendMessage(lostMessage);
                }
            }
            break;
            
            case "SECOND_WILDCARD_ATTACK": {
                System.out.println("Receiving attack");
                AttackGroup attackParameters = (AttackGroup) message.getObjectOfInterest();
                ArrayList<Character> warriors = ((PlayerClient) client).getWarriors();

                ((PlayerClient) client).setAttackedBy(attackParameters);
                Weapon weapon = (Weapon) attackParameters.getWeapon();
                Warrior warrior = (Warrior) attackParameters.getWarrior();

                Integer totalDamageDealt = attackWarriors(warriors, weapon);
                Message attackResponse = new ClientMessage("ATTACK_RESPONSE", client.getId(), totalDamageDealt);
                client.sendMessage(attackResponse);

                if (((PlayerClient) client).lostTheMatch()) {
                    Message lostMessage = new ClientMessage("LOST", client.getId(), null);
                    client.sendMessage(lostMessage);
                }
            }
            break;
            
            case "REJECTED_WILDCARD": {
                System.out.println("Wildcard rejected");
                ((PlayerClient) client).changePlayerGamingStatus("REJECTED_WILDCARD");
            }
            break;
            
            case "UNAVAILABLE_WILDCARD": {
                System.out.println("Wildcard rejected");
                ((PlayerClient) client).changePlayerGamingStatus("UNAVAILABLE_WILDCARD");
            }
            break;

        }
        
        
    }
    
    public int attackWarrior(Character warrior , Weapon weapon){
        Warrior currentWarrior = (Warrior) warrior;
        CharacterType type = currentWarrior.getType();
        WarriorWeapon currentWeapon = (WarriorWeapon)weapon;
        
        int damageToType = currentWeapon.getDamageForType(type);
        currentWarrior.setLife((int)currentWarrior.getLife() - damageToType);
        currentWarrior.setDamageReceived(damageToType); // Attribute used to notify the controller that it should update the individual damage received label
        
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
