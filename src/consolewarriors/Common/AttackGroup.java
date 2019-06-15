/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common;

import Weapons.Weapon;
import consolewarriors.Common.Shared.Warrior;
import java.io.Serializable;

/**
 *
 * @author rshum
 */
public class AttackGroup implements Serializable{
    
    private Warrior warrior;
    private Weapon weapon;

    public AttackGroup(Warrior warrior, Weapon weapon) {
        this.warrior = warrior;
        this.weapon = weapon;
    }

    public AttackGroup() {
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    
    
    
}
