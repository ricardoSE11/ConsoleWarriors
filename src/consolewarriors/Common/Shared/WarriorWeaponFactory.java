/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Shared;

import Weapons.Weapon;
import Weapons.WeaponCollection;
import Weapons.WeaponFactory;
import java.util.ArrayList;

/**
 *
 * @author ss
 */
public class WarriorWeaponFactory extends WeaponFactory{

    private WeaponDepot weapon_collection;
    
    public WarriorWeaponFactory(){
        super();
        weapon_collection = new WeaponDepot();
    }
    
    public boolean existsWeapon(String name){
        return weapon_collection.existsWeapon(name);
    }
    
    public void insertWeapon(Weapon weapon){
        this.weapon_collection.insert(weapon);
    }
    
    public Weapon createWarriorWeapon(String weaponName, String image){
        return new WarriorWeapon(weaponName , image); // FIXME
    }
    
    @Override
    public void printWeaponCollection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Weapon getIWeapon(String key) {
        return this.weapon_collection.getWeapon(key);
    }

    @Override
    public WeaponCollection getWeaponCollection() {
        return this.weapon_collection;
    }
    
    public ArrayList<String> getAllWeaponsName(){
        return this.weapon_collection.getAllWeaponsName();
    }
    
    public void deleteWeapon(String weapon_name){
        this.weapon_collection.deleteWeapon(weapon_name);
    }
    
}
