/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Control;

import Weapons.Weapon;
import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Client.View.CreationWindow;
import consolewarriors.Client.View.GameWindow;
import consolewarriors.Common.CharacterType;
import consolewarriors.Common.Shared.Warrior;
import consolewarriors.Common.Shared.WarriorFactory;
import consolewarriors.Common.Shared.WarriorWeapon;
import consolewarriors.Common.Shared.WarriorWeaponFactory;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ss
 */
public class CreationWindowController {
    //I like this kind of name variables
    //instead of switching between upper and lower
    //My english is not good. :P
    private CreationWindow creation_window;
    private WarriorFactory warriorF;
    private WarriorWeaponFactory weaponF;
    private PlayerClient player;
    //private 
    private ArrayList<Weapon> createdWeapons;
    private ArrayList<Characters.Character> createdWarriors;
    String lastImageUsedPath = "";
    
    public CreationWindowController(PlayerClient player){
        this.player = player;
        this.creation_window = new CreationWindow();
        this.warriorF = new WarriorFactory();
        this.weaponF = new WarriorWeaponFactory();
        this.createdWeapons = new ArrayList<>();
        this.createdWarriors = new ArrayList<>();
        this.creation_window.setVisible(true);
        setListeners();
    }
    
    public void addWeaponToWarrior(Characters.Character warrior, int warriorPosition, Weapon weapon){
        Warrior current_warrior = (Warrior) warrior;
        WarriorWeapon current_weapon = (WarriorWeapon) weapon;
        current_warrior.addWeapon(weapon);
        
        int weaponNumber = current_warrior.getWeapons().size();
        
        creation_window.addWeaponToWarrior(current_weapon.getName(), warriorPosition, weaponNumber + 1);
    }
    
    public void addWeapon(Weapon weapon){
        // Add the weapon to the arraylist of the created weapons
        //createdWeapons.add(weapon);
        WarriorWeapon currentWeapon = (WarriorWeapon) weapon;
        //DefaultTableModel tableModel = (DefaultTableModel) tblWeapons.getModel();
        Object[] weaponData = new Object[11];
        
        weaponData[0] = currentWeapon.getName();
        CharacterType[] types = CharacterType.values();
        for (int i = 0; i < currentWeapon.getAttackValueMatrix().size(); i++) {
            weaponData[i + 1] = currentWeapon.getAttackValueMatrix().get(types[i]);
        }

        creation_window.addWeapon(weaponData);
    }
    
    public void addWarrior(Characters.Character warrior) {
        // Add the warrior to the created warriors
        //createdWarriors.add(warrior);
        Warrior currentWarrior = (Warrior) warrior;
        Object[] warriorData = new Object[7];
        warriorData[0] = currentWarrior.getName();
        warriorData[1] = currentWarrior.getType().stringRepresentationOfCharacterType();

        creation_window.addWarrior(warriorData);
    }
   
    
    private void clearWeapons() {
        ArrayList<String> weapons = this.weaponF.getAllWeaponsName();
        for(String weapon_name : weapons){
            this.warriorF.deleteWeaponOnWarriors(weapon_name);
            this.weaponF.deleteWeapon(weapon_name);
        }
        creation_window.clearWeapons();
        creation_window.clearWeaponsOnWarriors();
                
    }
    
    private void loadImage() {                                             
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("*.Images", "jpg" , "gif" , "png");
        fileChooser.addChoosableFileFilter(extensionFilter);
        int result = fileChooser.showSaveDialog(null);
        
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            
            lastImageUsedPath = filePath;
            
            ImageIcon imageIcon = new ImageIcon(filePath);
            Image image = imageIcon.getImage();
       
            creation_window.setWarriorImage(image);
        }
        
        else if (result == JFileChooser.CANCEL_OPTION){
            System.out.println("No file selected");
        }
    }

    public void createWarrior(){
        String warriorName = creation_window.getWarriorName();
        boolean result_insert = this.warriorF.existsCharacter(warriorName);
        if(!result_insert){
            CharacterType warriorType = CharacterType.getCharacterTypeValue(creation_window.getCmbxWarriorTypeSelectedItem().toString().toUpperCase());
            ImageIcon warriorImage = creation_window.getWarriorImageIcon();
            Characters.Character newCharacter = this.warriorF.createWarrior(warriorName, warriorType, lastImageUsedPath, 100);
            ((Warrior)newCharacter).setCharacterImage(warriorImage);
            this.warriorF.insert(newCharacter);
            addWarrior(newCharacter);
            creation_window.setTxfWarriorName("");
        }
        
    }
    
    public void createWeapon(){
        String weaponName = creation_window.getWeaponName();
        boolean result_exists = this.weaponF.existsWeapon(weaponName);
        if(!result_exists){
            Weapon newWeapon = this.weaponF.createWarriorWeapon(weaponName, "DUMMY_STRING");//FIXME
            this.weaponF.insertWeapon(newWeapon);
            this.addWeapon(newWeapon);
            creation_window.setTxfWeaponName("");
        }
        
    }
    
    public void assignWeapon(){
        int weaponRowIndex = creation_window.getWeaponRowIndex();
        int warriorRowIndex = creation_window.getWarriorRowIndex();
        
        if (warriorRowIndex == -1){
            creation_window.notifyMessageError("No warrior selected");
        }
        else{
            if (weaponRowIndex == -1){
                creation_window.notifyMessageError("Please select a weapon");
            }
            else{
                String weapon_name = creation_window.getSelectedWeaponName(weaponRowIndex, 0);
                String warrior_name = creation_window.getSelectedWarriorName(warriorRowIndex, 0);
                if(this.warriorF.existsCharacter(warrior_name) && this.weaponF.existsWeapon(weapon_name)){
                    Weapon selectedWeapon = this.weaponF.getIWeapon(weapon_name);
                    Warrior selectedWarrior = (Warrior) this.warriorF.getCharacter(warrior_name);
                    if(!selectedWarrior.hasWeapon(weapon_name)){
                        this.addWeaponToWarrior(selectedWarrior, warriorRowIndex, selectedWeapon);
                    }
                    
                }
            }
        }
    }
    
    public boolean setWarriorsOfTheGame(ArrayList<String> warriors_names){
        //I know this can be set on the same cycle, but
        //what will happen if one warrior don't supply our demands?
        //we have to clear memory (Yes, Garbage Collector does, but it take
        //some time.
        for(String i : warriors_names){
            int current_size = this.warriorF.getWeaponsSizeOf(i);
            if(current_size != 5){
                return false;
            }
        }
        ArrayList<Characters.Character> new_created_warriors = new ArrayList<>();
        for(String i : warriors_names){
            Characters.Character selected_warrior = this.warriorF.getCharacter(i);
            new_created_warriors.add(selected_warrior);
        }
        createdWarriors = new_created_warriors;
        return true;
    }
    
    public void playerIsReady() {
        //port should not be static, but is not too important right now
        //PlayerClient player = new PlayerClient("localhost", 1234, username , createdWarriors);

        ArrayList<String> warriors_names = creation_window.getNamesOfWarriors();
        if (setWarriorsOfTheGame(warriors_names)) {
            this.player.setWarriors(createdWarriors);
            //this.player.run();

            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);

            GameWindowController gmc = new GameWindowController(gameWindow, player);

            this.creation_window.setVisible(false);
        }
    }
    
    public final void setListeners(){
        creation_window.addListeners(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadImage();
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createWarrior();
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createWeapon();
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playerIsReady();
                }
            },
            
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearWeapons();
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    assignWeapon();
                }
            }
        );
    }
}
