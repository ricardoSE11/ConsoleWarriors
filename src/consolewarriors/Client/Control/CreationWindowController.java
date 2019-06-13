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
import consolewarriors.Common.Shared.WarriorWeapon;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ss
 */
public class CreationWindowController {
    //I like this kind of name variables
    //instead of switching between upper and lower
    //My english is not good. :P
    private CreationWindow creation_window;
    
    private ArrayList<Weapon> createdWeapons;
    private ArrayList<Characters.Character> createdWarriors;
    String lastImageUsedPath = "";
    
    public CreationWindowController(){
        creation_window = new CreationWindow();
        createdWeapons = new ArrayList<>();
        createdWarriors = new ArrayList<>();
        creation_window.setVisible(true);
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
        createdWeapons.add(weapon);
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
        createdWarriors.add(warrior);
        Warrior currentWarrior = (Warrior) warrior;
        Object[] warriorData = new Object[7];
        warriorData[0] = currentWarrior.getName();
        warriorData[1] = currentWarrior.getType().stringRepresentationOfCharacterType();

        creation_window.addWarrior(warriorData);
    }
    
    private void clearWeapons() {                                                
        creation_window.clearWeapons();
        createdWeapons.clear();
                
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
        CharacterType warriorType = CharacterType.getCharacterTypeValue(creation_window.getCmbxWarriorTypeSelectedItem().toString().toUpperCase());
        
        ImageIcon warriorIcon = creation_window.getWarriorImageIcon();
        Image warriorImage = warriorIcon.getImage();
        
        Characters.Character newCharacter = new Warrior(warriorName, warriorType , lastImageUsedPath  , 100); //FIXME
        ((Warrior)newCharacter).setCharacterImage(warriorImage);
        addWarrior(newCharacter);
        
        creation_window.setTxfWarriorName("");
    }
    
    public void createWeapon(){
        System.out.println("Hola");
        String weaponName = creation_window.getWeaponName();
        Weapon newWeapon = new WarriorWeapon(weaponName , "DUMMY_STRING"); // FIXME
        this.addWeapon(newWeapon);
        creation_window.setTxfWeaponName("");
    }
    
    public void assignWeapon(){
        int weaponIndex = creation_window.getWeaponIndex();
        int warriorIndex = creation_window.getWarriorIndex();        
        if (warriorIndex == -1){
            creation_window.notifyMessageError("No weapon selected");
        }
        else{
            if (weaponIndex == -1){
                creation_window.notifyMessageError("Please select a weapon");
            }
            else{
                Weapon selectedWeapon = createdWeapons.get(weaponIndex);
                Warrior selectedWarrior = (Warrior) createdWarriors.get(warriorIndex);
                this.addWeaponToWarrior(selectedWarrior, warriorIndex, selectedWeapon);
            }
        }
    }
    
    public void playerIsReady(){
        String username = creation_window.getUsername();
        //port should not be static, but is not too important right now
        PlayerClient player = new PlayerClient("localhost", 1234, username , createdWarriors);
        player.run();
        
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        
        GameWindowController gmc = new GameWindowController(gameWindow, player);
        
        this.creation_window.setVisible(false);
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
