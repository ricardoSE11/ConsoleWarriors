/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common.Shared;


import Weapons.Weapon;
import consolewarriors.Common.CharacterType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author rshum
 */
public class WarriorWeapon extends Weapon implements Serializable{
    
    private HashMap<CharacterType,Integer> attackValueMatrix;
    private boolean wasUsed;

    public WarriorWeapon(String name, String imagePath) {
        super(name, imagePath);
        this.attackValueMatrix = new HashMap<>();
        this.wasUsed = false;
        setAttackMatrixValues();
    }

    public WarriorWeapon() {
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    public String getName() {
        return super.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<CharacterType, Integer> getAttackValueMatrix() {
        return attackValueMatrix;
    }

    public void setAttackValueMatrix(HashMap<CharacterType, Integer> attackValueMatrix) {
        this.attackValueMatrix = attackValueMatrix;
    }

    public boolean isWasUsed() {
        return wasUsed;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }
    
    public int getDamageForType(CharacterType characterType){
        return attackValueMatrix.get(characterType);
    }
    
    
    // </editor-fold>
    
    public void setAttackMatrixValues(){
        int maxValue = 100;
        int minValue = 20;
        Random random = new Random();
        CharacterType[] characterTypes = CharacterType.values();
        for (int i = 0 ; i < characterTypes.length ; i++){
            int randomNumber = random.nextInt((maxValue - minValue) + 1) + minValue;
            attackValueMatrix.put(characterTypes[i], randomNumber);
        }
    }
    
    public void printAttackMatrixValues(){
        CharacterType[] characterTypes = CharacterType.values();
        for (int i = 0 ; i < attackValueMatrix.size() ; i++){
            System.out.println("Type: " + characterTypes[i] + " -> " + attackValueMatrix.get(characterTypes[i]));
        }
    }

    @Override
    public String getImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setImage(String image) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
