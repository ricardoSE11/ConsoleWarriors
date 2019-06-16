/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Shared;

import java.util.HashMap;
import Characters.Character;
import Weapons.Weapon;
import consolewarriors.Client.IObservable;
import consolewarriors.Client.IObserver;
import consolewarriors.Common.CharacterType;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author rshum
 */
public class Warrior extends Character implements Serializable , IObservable{
    
    private CharacterType type;
    private HashMap<String,Weapon> weapons;
    //private Image characterImage; //Not serializable
    private ImageIcon characterImage;
            
    private transient ArrayList<IObserver> observers; // TRANSIENT word SUPER IMPORTANT.
    private int damageReceived;
    
    
    public Warrior(String name, CharacterType type, String imagePath, int life) {
        super(name, imagePath, life);
        this.type = type;
        this.weapons = new HashMap<>();
        
        this.observers = new ArrayList<>();
    }

    public Warrior() {
    }
    
    public int getWeaponsSize(){
        return weapons.size();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    public HashMap<String, Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(HashMap<String, Weapon> weapons) {
        this.weapons = weapons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    
    public CharacterType getType() {
        return type;
    }

    public void setType(CharacterType type) {
        this.type = type;
    }
    
    public void setImagePath(String imagePath){
        this.image = imagePath;
    }
    
    public String getImagePath(){
        return this.image;
    }

    public ImageIcon getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(ImageIcon characterImage) {
        this.characterImage = characterImage;
    }
    
    public Weapon getWeaponByName(String weaponName){
        Weapon choosenWeapon = null;
        System.out.println("Looking for weapon: " + weaponName);
        if (weapons.containsKey(weaponName)){
            choosenWeapon = weapons.get(weaponName);
        }
        return choosenWeapon;
    }

    public int getDamageReceived() {
        return damageReceived;
    }

    public void setDamageReceived(int damageReceived) {
        this.damageReceived = damageReceived;
        notify(damageReceived);
    }
    
    
    // </editor-fold>

    // Right now, we cant have we same weapon more than one time.
    
    public void addWeapon(Weapon weapon){
        WarriorWeapon currentWeapon = (WarriorWeapon) weapon;
        if (weapons.size() < 5){
            weapons.put(currentWeapon.getName(), weapon);
        }
        else{
            System.out.println("Trying to add more than 5 weapons");
        }
    }
    
    public void deleteWeapon(String weapon_name){
        this.weapons.remove(weapon_name);
    }
    
    public boolean hasWeapon(String weapon_name){
        return this.weapons.containsKey(weapon_name);
    }

    @Override
    public String getInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notify(Object object) {
        for (IObserver observer : observers){
            observer.update(object);
        }
    }
    
    public boolean isOutOfWeapons(){
        for (Weapon currentWeapon : weapons.values()){
            WarriorWeapon weapon = (WarriorWeapon) currentWeapon;
            // If the warrior was a weapon that has not been used
            if (!weapon.wasUsedBy(this.name)){
                return false;
            }
        }
        return true;
    }
    
    public void reloadWeapons(){
        for (Weapon currentWeapon : weapons.values()) {
            WarriorWeapon weapon = (WarriorWeapon) currentWeapon;
            weapon.reload();
        }
    }
    
    
}
