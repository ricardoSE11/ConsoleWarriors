/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.View;

import consolewarriors.Common.Shared.Warrior;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import Characters.Character;
import Weapons.Weapon;
import consolewarriors.Common.CharacterType;
import consolewarriors.Common.Shared.WarriorWeapon;
import java.awt.event.KeyListener;
import java.util.EventListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
/**
 *
 * @author rshum
 */
public class GameWindow extends javax.swing.JFrame {

    ArrayList<JLabel> warriorsImagesLabels;
    ArrayList<JLabel> warriorsNameLabels;
    ArrayList<JLabel> warriorsHealthLabels;
    ArrayList<JLabel> warriorsDamageReceivedLabels;
    
    private int tieResponse;
    
    public GameWindow() {
        this.warriorsImagesLabels = new ArrayList<>();
        this.warriorsNameLabels = new ArrayList<>();
        this.warriorsHealthLabels = new ArrayList<>();
        this.warriorsDamageReceivedLabels = new ArrayList<>();

        initComponents();
        setUpUiSettings();

    }
    
    public void addEventListener(EventListener eventListener){
        txaConsole.addKeyListener((KeyListener) eventListener);
    }

    public void setUpUiSettings(){
        warriorsImagesLabels.add(lblWarriorOne);
        warriorsImagesLabels.add(lblWarriorTwo);
        warriorsImagesLabels.add(lblWarriorThree);
        warriorsImagesLabels.add(lblWarriorFour);    
        
        warriorsNameLabels.add(lblWarrior1Name);
        warriorsNameLabels.add(lblWarrior2Name);
        warriorsNameLabels.add(lblWarrior3Name);
        warriorsNameLabels.add(lblWarrior4Name);
        
        warriorsHealthLabels.add(lblWarrior1HP);
        warriorsHealthLabels.add(lblWarrior2HP);
        warriorsHealthLabels.add(lblWarrior3HP);
        warriorsHealthLabels.add(lblWarrior4HP);
        
        warriorsDamageReceivedLabels.add(lblDamageToW1);
        warriorsDamageReceivedLabels.add(lblDamageToW2);
        warriorsDamageReceivedLabels.add(lblDamageToW3);
        warriorsDamageReceivedLabels.add(lblDamageToW4);
    }
    
    public void setUpWarriorImage(Character warrior , int warriorIndex){
        Warrior currentWarrior = (Warrior)warrior;
        ImageIcon imageIcon = currentWarrior.getCharacterImage();
        JLabel imageFrame = warriorsImagesLabels.get(warriorIndex);
        
        imageFrame.setIcon(imageIcon);
    }
    
    public void setUpWarriorsImages(ArrayList<Character> warriors){
        for (int i = 0 ; i < warriors.size() ; i++){
            Character currentWarrior = warriors.get(i);
            setUpWarriorImage(currentWarrior, i);
        }
    }
    
    public void setUpWarriorsData(ArrayList<Character> warriors){
        for (int i = 0 ; i < warriors.size() ; i++){
            Warrior currentWarrior = (Warrior) warriors.get(i);
            warriorsNameLabels.get(i).setText(currentWarrior.getName());
            warriorsHealthLabels.get(i).setText("" + currentWarrior.getLife());
            warriorsDamageReceivedLabels.get(i).setText("" + currentWarrior.getDamageReceived());
        }
    }
    
    public void writeToConsole(String message, Color color) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = txaConsole.getDocument().getLength();
        txaConsole.setCaretPosition(len);
        txaConsole.setCharacterAttributes(attributeSet, false);
        txaConsole.replaceSelection(message);

        // Set the color back to white
        attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.WHITE);
        txaConsole.setCharacterAttributes(attributeSet, false);
    }

    public String getTextLastLine(String text) {
        String temp = text;
        int lastEnterIndex = temp.lastIndexOf('\n', temp.length());
        lastEnterIndex++;
        temp = temp.substring(lastEnterIndex);
        return temp;
    }

    public String getLastLine() {
        return getTextLastLine(txaConsole.getText());
    }

    public void setTurnLabelText(String text){
        lblPlayerTurn.setText(text);
    }
    
    public void setDamageDealtLabelText(String text){
        lblDamageDealt.setText(text);
    }
    
    public void setSelectedWarriorLabelText(String text){
        lblSelectedWarriorName.setText(text);
    }
    
    public void setAttackerWarriorLabelText(String text){
        lblAttackedWithWarrior.setText(text);
    }
    
    public void fromWeaponToTableModel(Weapon weapon) {
        WarriorWeapon currentWeapon = (WarriorWeapon) weapon;

        //DefaultTableModel tableModel = (DefaultTableModel) tblWeapons.getModel();
        Object[] weaponData = new Object[11];

        weaponData[0] = currentWeapon.getName();
        CharacterType[] types = CharacterType.values();
        for (int i = 0; i < currentWeapon.getAttackValueMatrix().size(); i++) {
            weaponData[i + 1] = currentWeapon.getAttackValueMatrix().get(types[i]);
        }

        addWeaponModelToTable(weaponData);
    }
    
    public void addWeaponModelToTable(Object[] weapon_data) {
        DefaultTableModel tableModel = (DefaultTableModel) tblWeapons.getModel();
        tableModel.addRow(weapon_data);
    }
    
    public void displayWarriorsWeapons(Warrior warrior){
        System.out.println("Gonna display warrior weapons");
        for (Weapon weapon : warrior.getWeapons().values()){
            fromWeaponToTableModel(weapon);
        }
    }
    
    public int showTieProposalDialog(){
        tieResponse = JOptionPane.showConfirmDialog(null, "Enemy is proposing a Tie. Do you accept?");
        if (tieResponse == JOptionPane.YES_OPTION){
            System.out.println("I accept the tie");
            return tieResponse;
        }
        else{
            System.out.println("Nope, lets keep playing");
            return tieResponse;
        }
    }
    
    public void showMessageDialog(String message){
        JOptionPane.showMessageDialog(null, message);
    }
    
    public void disableConsole(){
        txaConsole.setEditable(false);
    }
    
    public ImageIcon resizeImage(ImageIcon image , JLabel label) {
        Image newImage = image.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedImage = new ImageIcon(newImage);
        return resizedImage;
    }
    
    public void setImageOnLabel(ImageIcon image , JLabel label) {
        ImageIcon resizedImage = resizeImage(image,label);
        label.setIcon(resizedImage);
    }
    
    public void setAttackedWithInformation(Warrior warrior, Weapon weapon) {
        String warriorName = warrior.getName();
        String warriorType = warrior.getType().toString();
        ImageIcon warriorImage = warrior.getCharacterImage();
        
        String weaponName = weapon.getName();
        lblAttackedWithWarrior.setText(warriorName + " [" + warriorType + "]");
        lblAttackerWeapon.setText(weaponName);
        setImageOnLabel(warriorImage, lblAttackerBG);
        
    }
    
    public void setAttackedByInformation(Warrior warrior , Weapon weapon){
        String warriorName = warrior.getName();
        String warriorType = warrior.getType().toString();
        ImageIcon warriorImage = warrior.getCharacterImage();

        String weaponName = weapon.getName();
        lblAttackedBy.setText(warriorName + " [" + warriorType + "]");
        lblAttackedWith.setText(weaponName);
        setImageOnLabel(warriorImage, lblAttackedByBG);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        warriorsPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblWeapons = new javax.swing.JTable();
        lblSelectedWarriorName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblPlayerTurn = new javax.swing.JLabel();
        panelWarriorOne = new javax.swing.JPanel();
        lblWarrior1HP = new javax.swing.JLabel();
        lblWarrior1Name = new javax.swing.JLabel();
        lblWarriorOne = new javax.swing.JLabel();
        panelWarrior2 = new javax.swing.JPanel();
        lblWarrior2HP = new javax.swing.JLabel();
        lblWarrior2Name = new javax.swing.JLabel();
        lblWarriorTwo = new javax.swing.JLabel();
        panelWarrior3 = new javax.swing.JPanel();
        lblWarrior3Name = new javax.swing.JLabel();
        lblWarrior3HP = new javax.swing.JLabel();
        lblWarriorThree = new javax.swing.JLabel();
        panelWarrior4 = new javax.swing.JPanel();
        lblWarrior4Name = new javax.swing.JLabel();
        lblWarrior4HP = new javax.swing.JLabel();
        lblWarriorFour = new javax.swing.JLabel();
        statsPanel = new javax.swing.JPanel();
        lstRanking = new java.awt.List();
        lstEnemyInfo = new java.awt.List();
        lstPlayerInfo = new java.awt.List();
        panelAttackedBy = new javax.swing.JPanel();
        lblDamageToW3 = new javax.swing.JLabel();
        lblWarriorFourInitials = new javax.swing.JLabel();
        lblDamageToW4 = new javax.swing.JLabel();
        lblWarriorTwoInitials = new javax.swing.JLabel();
        lblWarriorThreeInitials = new javax.swing.JLabel();
        lblDamageToW2 = new javax.swing.JLabel();
        lblDamageToW1 = new javax.swing.JLabel();
        lblWarriorOneInitials = new javax.swing.JLabel();
        lblAttackedWith = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblAttackedBy = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblAttackedByBG = new javax.swing.JLabel();
        panelUsedToAttack = new javax.swing.JPanel();
        lblDamageDealt = new javax.swing.JLabel();
        lblAttackedWithWarrior = new javax.swing.JLabel();
        lblAttackerWeapon = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblAttackerBG = new javax.swing.JLabel();
        consolePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaConsole = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);

        mainPanel.setBackground(new java.awt.Color(102, 102, 102));

        warriorsPanel.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Your team");

        tblWeapons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Fire", "Air", "Water", "White magic", "Black magic", "Electricity", "Ice", "Acid", "Spirituality", "Iron"
            }
        ));
        jScrollPane1.setViewportView(tblWeapons);

        lblSelectedWarriorName.setText("Warrior name");

        jLabel4.setText("Selected warrior:");

        lblPlayerTurn.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        lblPlayerTurn.setForeground(new java.awt.Color(255, 204, 51));
        lblPlayerTurn.setText("Turn");

        panelWarriorOne.setBackground(new java.awt.Color(102, 102, 102));
        panelWarriorOne.setLayout(null);

        lblWarrior1HP.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior1HP.setText("H1 health");
        panelWarriorOne.add(lblWarrior1HP);
        lblWarrior1HP.setBounds(20, 210, 90, 16);

        lblWarrior1Name.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior1Name.setText("W1 name");
        panelWarriorOne.add(lblWarrior1Name);
        lblWarrior1Name.setBounds(20, 240, 110, 16);

        lblWarriorOne.setBorder(new javax.swing.border.MatteBorder(null));
        panelWarriorOne.add(lblWarriorOne);
        lblWarriorOne.setBounds(0, 0, 140, 270);

        panelWarrior2.setBackground(new java.awt.Color(102, 102, 102));
        panelWarrior2.setLayout(null);

        lblWarrior2HP.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior2HP.setText("H2 health");
        panelWarrior2.add(lblWarrior2HP);
        lblWarrior2HP.setBounds(20, 210, 80, 16);

        lblWarrior2Name.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior2Name.setText("W2 name");
        panelWarrior2.add(lblWarrior2Name);
        lblWarrior2Name.setBounds(20, 240, 70, 16);

        lblWarriorTwo.setBorder(new javax.swing.border.MatteBorder(null));
        panelWarrior2.add(lblWarriorTwo);
        lblWarriorTwo.setBounds(0, 0, 140, 270);

        panelWarrior3.setBackground(new java.awt.Color(102, 102, 102));
        panelWarrior3.setLayout(null);

        lblWarrior3Name.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior3Name.setText("W3 name");
        panelWarrior3.add(lblWarrior3Name);
        lblWarrior3Name.setBounds(20, 240, 100, 16);

        lblWarrior3HP.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior3HP.setText("W3 health");
        panelWarrior3.add(lblWarrior3HP);
        lblWarrior3HP.setBounds(20, 210, 80, 16);

        lblWarriorThree.setBorder(new javax.swing.border.MatteBorder(null));
        panelWarrior3.add(lblWarriorThree);
        lblWarriorThree.setBounds(0, 0, 140, 270);

        panelWarrior4.setBackground(new java.awt.Color(102, 102, 102));
        panelWarrior4.setForeground(new java.awt.Color(102, 102, 102));
        panelWarrior4.setPreferredSize(new java.awt.Dimension(140, 0));
        panelWarrior4.setLayout(null);

        lblWarrior4Name.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior4Name.setText("W4 name");
        panelWarrior4.add(lblWarrior4Name);
        lblWarrior4Name.setBounds(20, 240, 80, 16);

        lblWarrior4HP.setForeground(new java.awt.Color(255, 255, 255));
        lblWarrior4HP.setText("W4 health");
        panelWarrior4.add(lblWarrior4HP);
        lblWarrior4HP.setBounds(20, 210, 70, 16);

        lblWarriorFour.setBorder(new javax.swing.border.MatteBorder(null));
        panelWarrior4.add(lblWarriorFour);
        lblWarriorFour.setBounds(0, 0, 140, 270);

        javax.swing.GroupLayout warriorsPanelLayout = new javax.swing.GroupLayout(warriorsPanel);
        warriorsPanel.setLayout(warriorsPanelLayout);
        warriorsPanelLayout.setHorizontalGroup(
            warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(warriorsPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(warriorsPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(warriorsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSelectedWarriorName, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(warriorsPanelLayout.createSequentialGroup()
                        .addGroup(warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, warriorsPanelLayout.createSequentialGroup()
                                .addComponent(panelWarriorOne, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelWarrior2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelWarrior3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(warriorsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(368, 368, 368)))
                        .addGroup(warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPlayerTurn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelWarrior4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 23, Short.MAX_VALUE))))
        );
        warriorsPanelLayout.setVerticalGroup(
            warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(warriorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(warriorsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, warriorsPanelLayout.createSequentialGroup()
                        .addComponent(lblPlayerTurn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelWarrior4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelWarrior3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelWarriorOne, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelWarrior2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(warriorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectedWarriorName)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        statsPanel.setBackground(new java.awt.Color(51, 51, 51));

        panelAttackedBy.setBackground(new java.awt.Color(153, 153, 153));
        panelAttackedBy.setLayout(null);

        lblDamageToW3.setForeground(new java.awt.Color(255, 255, 255));
        lblDamageToW3.setText("00%");
        panelAttackedBy.add(lblDamageToW3);
        lblDamageToW3.setBounds(80, 160, 23, 16);

        lblWarriorFourInitials.setForeground(new java.awt.Color(255, 255, 255));
        lblWarriorFourInitials.setText("W4:");
        panelAttackedBy.add(lblWarriorFourInitials);
        lblWarriorFourInitials.setBounds(20, 190, 22, 16);

        lblDamageToW4.setForeground(new java.awt.Color(255, 255, 255));
        lblDamageToW4.setText("00%");
        panelAttackedBy.add(lblDamageToW4);
        lblDamageToW4.setBounds(80, 190, 23, 16);

        lblWarriorTwoInitials.setForeground(new java.awt.Color(255, 255, 255));
        lblWarriorTwoInitials.setText("W2:");
        lblWarriorTwoInitials.setToolTipText("");
        panelAttackedBy.add(lblWarriorTwoInitials);
        lblWarriorTwoInitials.setBounds(20, 130, 30, 16);

        lblWarriorThreeInitials.setForeground(new java.awt.Color(255, 255, 255));
        lblWarriorThreeInitials.setText("W3:");
        panelAttackedBy.add(lblWarriorThreeInitials);
        lblWarriorThreeInitials.setBounds(20, 160, 22, 16);

        lblDamageToW2.setForeground(new java.awt.Color(255, 255, 255));
        lblDamageToW2.setText("00%");
        panelAttackedBy.add(lblDamageToW2);
        lblDamageToW2.setBounds(80, 130, 23, 16);

        lblDamageToW1.setForeground(new java.awt.Color(255, 255, 255));
        lblDamageToW1.setText("00%");
        panelAttackedBy.add(lblDamageToW1);
        lblDamageToW1.setBounds(80, 100, 23, 16);

        lblWarriorOneInitials.setForeground(new java.awt.Color(255, 255, 255));
        lblWarriorOneInitials.setText("W1:");
        panelAttackedBy.add(lblWarriorOneInitials);
        lblWarriorOneInitials.setBounds(20, 100, 22, 16);

        lblAttackedWith.setForeground(new java.awt.Color(255, 255, 255));
        lblAttackedWith.setText("Place holder for weapon");
        panelAttackedBy.add(lblAttackedWith);
        lblAttackedWith.setBounds(90, 50, 150, 16);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Weapon:");
        panelAttackedBy.add(jLabel1);
        jLabel1.setBounds(20, 50, 60, 16);

        lblAttackedBy.setForeground(new java.awt.Color(255, 255, 255));
        lblAttackedBy.setText("Character name [Type]");
        panelAttackedBy.add(lblAttackedBy);
        lblAttackedBy.setBounds(100, 20, 140, 20);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Attacked by:");
        panelAttackedBy.add(jLabel5);
        jLabel5.setBounds(20, 20, 80, 20);
        panelAttackedBy.add(lblAttackedByBG);
        lblAttackedByBG.setBounds(0, 0, 450, 260);

        panelUsedToAttack.setBackground(new java.awt.Color(102, 102, 102));
        panelUsedToAttack.setLayout(null);

        lblDamageDealt.setForeground(new java.awt.Color(204, 0, 51));
        lblDamageDealt.setText("DAMAGE");
        panelUsedToAttack.add(lblDamageDealt);
        lblDamageDealt.setBounds(30, 100, 90, 30);

        lblAttackedWithWarrior.setForeground(new java.awt.Color(255, 255, 255));
        lblAttackedWithWarrior.setText("Attacker [Type]");
        panelUsedToAttack.add(lblAttackedWithWarrior);
        lblAttackedWithWarrior.setBounds(140, 20, 87, 16);

        lblAttackerWeapon.setForeground(new java.awt.Color(255, 255, 255));
        lblAttackerWeapon.setText("Weapon used");
        panelUsedToAttack.add(lblAttackerWeapon);
        lblAttackerWeapon.setBounds(80, 50, 140, 16);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Weapon:");
        panelUsedToAttack.add(jLabel9);
        jLabel9.setBounds(20, 50, 50, 16);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("You attacked with:");
        panelUsedToAttack.add(jLabel10);
        jLabel10.setBounds(20, 20, 104, 16);
        panelUsedToAttack.add(lblAttackerBG);
        lblAttackerBG.setBounds(0, 0, 430, 220);

        javax.swing.GroupLayout statsPanelLayout = new javax.swing.GroupLayout(statsPanel);
        statsPanel.setLayout(statsPanelLayout);
        statsPanelLayout.setHorizontalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lstPlayerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(lstEnemyInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lstRanking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAttackedBy, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addComponent(panelUsedToAttack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        statsPanelLayout.setVerticalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addComponent(lstRanking, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lstEnemyInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lstPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addComponent(panelAttackedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelUsedToAttack, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(warriorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(warriorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        consolePanel.setBackground(new java.awt.Color(102, 102, 102));

        txaConsole.setBackground(new java.awt.Color(0, 0, 0));
        txaConsole.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(txaConsole);

        javax.swing.GroupLayout consolePanelLayout = new javax.swing.GroupLayout(consolePanel);
        consolePanel.setLayout(consolePanelLayout);
        consolePanelLayout.setHorizontalGroup(
            consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consolePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        consolePanelLayout.setVerticalGroup(
            consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consolePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(consolePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consolePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel consolePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAttackedBy;
    private javax.swing.JLabel lblAttackedByBG;
    private javax.swing.JLabel lblAttackedWith;
    private javax.swing.JLabel lblAttackedWithWarrior;
    private javax.swing.JLabel lblAttackerBG;
    private javax.swing.JLabel lblAttackerWeapon;
    private javax.swing.JLabel lblDamageDealt;
    private javax.swing.JLabel lblDamageToW1;
    private javax.swing.JLabel lblDamageToW2;
    private javax.swing.JLabel lblDamageToW3;
    private javax.swing.JLabel lblDamageToW4;
    private javax.swing.JLabel lblPlayerTurn;
    private javax.swing.JLabel lblSelectedWarriorName;
    private javax.swing.JLabel lblWarrior1HP;
    private javax.swing.JLabel lblWarrior1Name;
    private javax.swing.JLabel lblWarrior2HP;
    private javax.swing.JLabel lblWarrior2Name;
    private javax.swing.JLabel lblWarrior3HP;
    private javax.swing.JLabel lblWarrior3Name;
    private javax.swing.JLabel lblWarrior4HP;
    private javax.swing.JLabel lblWarrior4Name;
    private javax.swing.JLabel lblWarriorFour;
    private javax.swing.JLabel lblWarriorFourInitials;
    private javax.swing.JLabel lblWarriorOne;
    private javax.swing.JLabel lblWarriorOneInitials;
    private javax.swing.JLabel lblWarriorThree;
    private javax.swing.JLabel lblWarriorThreeInitials;
    private javax.swing.JLabel lblWarriorTwo;
    private javax.swing.JLabel lblWarriorTwoInitials;
    private java.awt.List lstEnemyInfo;
    private java.awt.List lstPlayerInfo;
    private java.awt.List lstRanking;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelAttackedBy;
    private javax.swing.JPanel panelUsedToAttack;
    private javax.swing.JPanel panelWarrior2;
    private javax.swing.JPanel panelWarrior3;
    private javax.swing.JPanel panelWarrior4;
    private javax.swing.JPanel panelWarriorOne;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JTable tblWeapons;
    private javax.swing.JTextPane txaConsole;
    private javax.swing.JPanel warriorsPanel;
    // End of variables declaration//GEN-END:variables
}
