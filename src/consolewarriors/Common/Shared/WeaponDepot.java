/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Shared;

import Weapons.Weapon;
import Weapons.WeaponCollection;
import java.util.ArrayList;

/**
 *
 * @author ss
 */
public class WeaponDepot extends WeaponCollection{
    
    public WeaponDepot(){
        super();
    }
    
    @Override
    public boolean existsWeapon(String name){
        return weapons.containsKey(name);
    }
    
    @Override
    public void insert(Weapon weapon){
        weapons.put(weapon.getName(), weapon);
    }
    
    @Override
    public Weapon getWeapon(String key){
        return (Weapon) weapons.get(key);
    }
    
    public ArrayList<String> getAllWeaponsName(){
        ArrayList<String> result = new ArrayList<>();
        weapons.forEach((String k,Weapon c) -> {
            result.add(k);
        });
        return result;
    }
    
    public void deleteWeapon(String weapon_name){
        weapons.remove(weapon_name);
    }
}
