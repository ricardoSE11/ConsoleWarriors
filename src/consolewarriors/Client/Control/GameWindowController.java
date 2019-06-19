/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Control;

import consolewarriors.Client.IObserver;
import consolewarriors.Client.Model.PlayerClient;
import consolewarriors.Client.View.GameWindow;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Common.Message;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Characters.Character;
import Weapons.Weapon;
import consolewarriors.Common.AttackGroup;
import consolewarriors.Client.Model.Command.ICommand;
import consolewarriors.Client.Model.Command.ICommandManager;
import consolewarriors.Client.Model.Command.PlayerCommandManager;
import consolewarriors.Client.Model.Command.PlayerCommands.NotFoundCommand;
import consolewarriors.Common.Shared.Warrior;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;


/**
 *
 * @author rshum
 */
public class GameWindowController implements IObserver {
    
    // View
    private GameWindow gameWindow;
    
    // Model
    private PlayerClient player;
    private ArrayList<Character> warriors;

    public GameWindowController(GameWindow gameWindow, PlayerClient player) {
        this.gameWindow = gameWindow;
        this.player = player;
        this.warriors = player.getWarriors();
        
        ICommandManager commandManager = new PlayerCommandManager();
        ((PlayerCommandManager)commandManager).setPlayer(player);
        player.setCommandManager(commandManager);
        
        gameWindow.addEventListener(new WindowListener());
        gameWindow.setUpWarriorsData(warriors);
        gameWindow.setUpWarriorsImages(warriors);
                
        gameWindow.setTitle(player.getPlayerName() + "'s session");
       if(this.player.getId() % 2 != 0){
            gameWindow.setTurnLabelText("You go first");
        }
        else{
            gameWindow.setTurnLabelText("Enemy plays first");
        }
        
        queueForMatch();
        subscribeToObservableResources();
        
        if (player.getPlayerStatus().equals("WAITING_FOR_MATCH")){
            JOptionPane.showMessageDialog(gameWindow, "Waiting for match");
        }
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
    
    public void queueForMatch() {
        Message readyMessage = new ClientMessage("READY", player.getId(), "NO_OBJECT");
        player.sendMessage(readyMessage);
    }

    public void subscribeToObservableResources(){
        // Area for improvement
        this.player.addObserver(this);
        for (Character character : warriors){
            Warrior currentWarrior = (Warrior) character;
            currentWarrior.addObserver(this);
        }
    }

    class WindowListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent ke) {
            if ((ke.getKeyChar() == '\n')) {
                // --- Commands with arguments ---
                try{
                    String[] commandInfo = gameWindow.getLastLine().split("-");
                    System.out.println("Parsing: " + gameWindow.getLastLine());
                    
                    int hyphenIndex = gameWindow.getLastLine().indexOf("-");
                    commandInfo[1] = gameWindow.getLastLine().substring(hyphenIndex + 1);
                    
                    String commandName = commandInfo[0].toUpperCase();
                    String commandArguments = commandInfo[1];
                    ICommandManager commandManager = player.getCommandManager();
                    System.out.println("Command name:" + commandName);
                    ICommand selectedCommand = commandManager.getCommand(commandName);

                    if (selectedCommand instanceof NotFoundCommand) {
                        gameWindow.writeToConsole("\n" + "Please type a valid command", Color.yellow);
                    } else if (selectedCommand == null) {
                        System.out.println("Got a null command");
                    } else {
                        selectedCommand.execute(commandArguments);
                    }
                }
                
                catch(Exception e){
                    
                    //e.printStackTrace();
                    
                    // --- Commands with no arguments ---
                    String noParameterCommand = gameWindow.getLastLine().toUpperCase();
                    ICommandManager commandManager = player.getCommandManager();
                    System.out.println("Try the (possible)no-argument command:" + noParameterCommand);
                    ICommand selectedCommand = commandManager.getCommand(noParameterCommand);
                    
                    if (selectedCommand instanceof NotFoundCommand) {
                        gameWindow.writeToConsole("\n" + "Please type a valid command", Color.yellow);
                    } else if (selectedCommand == null) {
                        System.out.println("Got a null command");
                        gameWindow.writeToConsole("\n" + "Please type a valid command", Color.yellow);
                    } else {
                        selectedCommand.execute();
                    }
                }
            }
        }
        
        @Override
        public void keyTyped(KeyEvent ke) {}

        @Override
        public void keyReleased(KeyEvent ke) {}

    }
   
    @Override
    public void update(Object object) {
        if (object instanceof String){
            handleStringUpdates((String) object);
        }
        
        else if (object instanceof Integer){
            // Updating the warrios data
            gameWindow.setUpWarriorsData(warriors);
        }
    }
    
    public void handleStatusUpdates(String statusString){
        if (statusString.equals("PLAYING")) {
            JOptionPane.showMessageDialog(gameWindow, "Game started");
        }
        
        if (statusString.equals("YOUR_STATS")) {
            gameWindow.setUpPlayerStats(player.getPlayer_stats(), false);
        }        
        
        if (statusString.equals("ENEMY_STATS")) {
            gameWindow.setUpPlayerStats(player.getEnemy_stats(), true);
        }
        
        if (statusString.equals("WRONG_TURN")) {
            this.gameWindow.writeToConsole("Error: Is not your turn" + "\n", Color.RED);
        } 
        
        if (statusString.equals("ENEMY_TURN")) {
            this.gameWindow.setTurnLabelText("Enemy's turn");
        }
        
        if (statusString.equals("MY_TURN")) {
            this.gameWindow.setTurnLabelText("Your turn");
        }
        
        else if (statusString.equals("NO_SUCH_WARRIOR")){
            this.gameWindow.writeToConsole("\n" + "Error: Warrior does not exist" + "\n", Color.RED);
        }
        
        else if (statusString.equals("NO_SUCH_WEAPON")) {
            this.gameWindow.writeToConsole("\n" + "Error: That weapon does not exist" + "\n", Color.RED);
        }
        
        else if (statusString.equals("USED_WEAPON")) {
            this.gameWindow.writeToConsole("\n" + "Error: Weapon was already used" + "\n", Color.RED);
        }
        
        else if (statusString.equals("RELOADING")) {
            this.gameWindow.writeToConsole("\n" + "Message: Reloaded weapons" + "\n", Color.CYAN);
        }
        
        else if (statusString.equals("UNVALID_RELOAD")) {
            this.gameWindow.writeToConsole("\n" + "Error: You still have usable weapons" + "\n", Color.RED);
        }
        
        else if (statusString.equals("REJECTED_WILDCARD")) {
            this.gameWindow.writeToConsole("\n" + "Message: You can not use the wildcard" + "\n", Color.RED);
        }
        
        else if (statusString.equals("UNAVAILABLE_WILDCARD")) {
            this.gameWindow.writeToConsole("\n" + "Message: You can not use the wildcard" + "\n", Color.RED);
        }
        
        
        else if (statusString.startsWith("SELECTED_WARRIOR")) {
            int hyphIndex = statusString.indexOf("-");
            String warriorName = statusString.substring(hyphIndex + 1);
            Warrior choosenWarrior = (Warrior) player.getWarriorByName(warriorName);
            if (choosenWarrior != null) {
                gameWindow.displayWarriorsWeapons(choosenWarrior);
                gameWindow.setSelectedWarriorLabelText(warriorName);
            } else {
                System.out.println("Null warrior, didnt find: " + warriorName);
            }
        }
        
        else if (statusString.startsWith("RECEIVING_LOG")){
            int hyphIndex = statusString.indexOf("-");
            String matchLog = statusString.substring(hyphIndex + 1);
            gameWindow.writeToConsole("\n " + "-GAME LOG-" + "\n", Color.LIGHT_GRAY);
            gameWindow.writeToConsole("\n " + matchLog + "\n", Color.LIGHT_GRAY);
        }
        
        else if (statusString.equals("RESPONDING_TIE_REQUEST")){
            System.out.println("Responding tie request");
            int tieAnswer = JOptionPane.showConfirmDialog(gameWindow, "Enemy is proposing a Tie. Do you accept?");
            if (tieAnswer == JOptionPane.YES_OPTION){
                // Send a message to indicate the end of the game.
                Message acceptTieMessage = new ClientMessage("TIE_ACCEPTED", player.getPlayerID(), null);
                player.sendMessage(acceptTieMessage);
                gameWindow.writeToConsole(" --- TIE: GAME ENDED --- ", Color.BLUE);
            }
            else{
                Message denyTieMessage = new ClientMessage("TIE_DENIED", player.getPlayerID(), null);
                player.sendMessage(denyTieMessage);                
            }
        }
        
        else if (statusString.equals("TIE_DENIED")){
            this.gameWindow.writeToConsole("\n" + "Message: Enemy denied the tie proposal" + "\n", Color.CYAN);
        }
        
        else if (statusString.equals("GAME_TIED")){
            gameWindow.writeToConsole(" --- TIE: GAME ENDED --- ", Color.BLUE);
            player.leaveMatch();
            gameWindow.disableConsole();
        }
        
        else if (statusString.equals("DEFEATED")){
            gameWindow.writeToConsole("\n" + " --- DEFEATED: GAME ENDED --- ", Color.RED);
            player.leaveMatch();
            gameWindow.disableConsole();
        }
        
        else if (statusString.equals("WINNER")){
            gameWindow.writeToConsole(" --- VICTORY: GAME ENDED --- ", Color.GREEN);
            player.leaveMatch();
            gameWindow.disableConsole();
        }
        
        
    }
    
    public void handleStringUpdates(String updateString){
        if (updateString.startsWith("STATUS")) {
            int hyphenIndex = updateString.indexOf("-");
            String status = updateString.substring(hyphenIndex + 1);  
            handleStatusUpdates(status);
        } 
        
        else if (updateString.equals("ATTACKED_ENEMY")){
            AttackGroup attackParameters = player.getAttackedWith();
            Warrior attackingWarrior = (Warrior) attackParameters.getWarrior();
            Weapon attackingWeapon = (Weapon) attackParameters.getWeapon();
            gameWindow.setAttackedWithInformation(attackingWarrior, attackingWeapon);
            
        }
        
        else if (updateString.equals("RECEIVED_ATTACK")) {
            AttackGroup attackParameters = player.getAttackedBy();
            Warrior attackingWarrior = (Warrior) attackParameters.getWarrior();
            Weapon attackingWeapon = (Weapon) attackParameters.getWeapon();
            gameWindow.setAttackedByInformation(attackingWarrior, attackingWeapon);
        }
        
        else if (updateString.startsWith("DAMAGE_DEALT")){
            int hyphenIndex = updateString.indexOf("-");
            gameWindow.setDamageDealtLabelText(updateString.substring(hyphenIndex + 1));
        }
        
        else {
            if (updateString.startsWith("ENEMY")) {
                int hyphenIndex =  updateString.indexOf("-");
                //"\n" +
                this.gameWindow.writeToConsole( "Chat [enemy]: " + updateString.substring(hyphenIndex + 1) + "\n", Color.GRAY);
            } else {
                this.gameWindow.writeToConsole("\n" + "Chat [you]: " + updateString, Color.GRAY);
            }
        }
        
    }
    
    
}
