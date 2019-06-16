/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Shared;

import Characters.Character;
import Characters.CharacterFactory;
import consolewarriors.Common.CharacterType;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ss
 */
public class WarriorFactory extends CharacterFactory{

    public WarriorFactory(){
        super();
        charactersHash = new HashMap<>();
    }
    
    @Override
    public void insert(Character character) {
        if (!existsCharacter(character.getName())){
            charactersHash.put(character.getName(), character);
        }
    }
    
    public Character createWarrior(String warriorName, CharacterType warriorType,
            String lastImageUsedPath, int life){
        return new Warrior(warriorName, warriorType , lastImageUsedPath  , life);
    }

    @Override
    public boolean existsCharacter(String name) {
        return charactersHash.containsKey(name);
    }

    @Override
    public Character getCharacter(String key) {
         return (Warrior) charactersHash.get(key);
    }
    
    public int getWeaponsSizeOf(String warrior_name){
        return ((Warrior)charactersHash.get(warrior_name)).getWeaponsSize();
    }

    @Override
    public ArrayList<String> getAllCharacters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void deleteWeaponOnWarriors(String weapon_name){
        charactersHash.forEach((String k,Character c) -> {
            Warrior current_warrior = (Warrior) c;
            if(current_warrior.hasWeapon(weapon_name)){
                current_warrior.deleteWeapon(weapon_name);
            }
        });
    }
    
}
