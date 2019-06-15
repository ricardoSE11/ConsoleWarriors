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
import java.util.EventListener;
import Characters.Character;
import consolewarriors.Client.Model.IServerMessageHandler;
import consolewarriors.Client.Model.ServerMessageHandler;
import consolewarriors.Common.Command.ICommand;
import consolewarriors.Common.Command.ICommandManager;
import consolewarriors.Common.Command.PlayerCommandManager;
import consolewarriors.Common.Command.PlayerCommands.NotFoundCommand;
import consolewarriors.Common.Shared.Warrior;
import java.awt.event.KeyListener;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;


/**
 *
 * @author rshum
 */
public class GameWindowController implements IObserver{
    
    // View
    private GameWindow gameWindow;
    
    // Model
    private PlayerClient player;
    private ArrayList<Character> warriors;

    public GameWindowController(GameWindow gameWindow, PlayerClient player) {
        this.gameWindow = gameWindow;
        
        IServerMessageHandler messageHandler = new ServerMessageHandler();
        this.player = player;
        player.setServerMessageHandler(messageHandler);
        ICommandManager commandManager = new PlayerCommandManager();
        ((PlayerCommandManager)commandManager).setPlayer(player);
        player.setCommandManager(commandManager);
        
        this.warriors = player.getWarriors();
        
        gameWindow.addEventListener(new WindowListener());
        gameWindow.setUpWarriorsData(warriors);
        gameWindow.setUpWarriorsImages(warriors);
        
        this.player.run(); // Order here is important 
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
        public void keyTyped(KeyEvent ke) {
        }

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
        public void keyReleased(KeyEvent ke) {
        }

    }
   
    @Override
    public void update(Object object) {
        if (object instanceof String){
            handleStringUpdates((String) object);
        }
        
        else if (object instanceof Integer){
            
        }
    }
    
    public void handleStatusUpdates(String statusString){
        if (statusString.startsWith("WRONG_TURN")) {
            this.gameWindow.writeToConsole("\n" + "Error: Is not your turn" + "\n", Color.RED);
        } 
        
        else if (statusString.startsWith("SELECTED_WARRIOR")) {
            int hyphIndex = statusString.indexOf("-");
            String warriorName = statusString.substring(hyphIndex + 1);
            Warrior choosenWarrior = (Warrior) player.getWarriorByName(warriorName);
            if (choosenWarrior != null) {
                gameWindow.displayWarriorsWeapons(choosenWarrior);
                gameWindow.setSelectedWarriorLabelText(warriorName);
            } else {
                System.out.println("Null warrior bruh, didnt find: " + warriorName);
            }
        }
        
        else if (statusString.equals("RESPONDING_TIE_REQUEST")){
            System.out.println("WAT: " + statusString);
            int tieRespone = 0;
            //gameWindow.showTieProposalDialog();
            if (tieRespone == JOptionPane.YES_OPTION){
                Message acceptTieMessage = new ClientMessage("TIE_ACCEPTED", player.getPlayerID(), null);
                player.sendMessage(acceptTieMessage);
            }
            else{
                Message denyTieMessage = new ClientMessage("TIE_DENIED", player.getPlayerID(), null);
                player.sendMessage(denyTieMessage);                
            }
        }
        
        else if (statusString.startsWith("GAME_TIED")){
            gameWindow.showMessageDialog("Game was tied");
        }
    }
    
    public void handleStringUpdates(String updateString){
        if (updateString.startsWith("STATUS")) {
            int hyphenIndex = updateString.indexOf("-");
            String status = updateString.substring(hyphenIndex + 1);  
            handleStatusUpdates(status);
        } 
        
        else if (updateString.startsWith("DAMAGE_DEALT")){
            int hyphenIndex = updateString.indexOf("-");
            gameWindow.setDamageDealtLabelText(updateString.substring(hyphenIndex + 1));
        }
        
        else {
            if (updateString.startsWith("ENEMY")) {
                int hyphenIndex =  updateString.indexOf("-");
                this.gameWindow.writeToConsole("\n" + "Chat[enemy]: " + updateString.substring(hyphenIndex + 1) + "\n", Color.GREEN);
            } else {
                this.gameWindow.writeToConsole("\n" + "Chat[you]: " + updateString, Color.GREEN);
            }

        }
    }
    
    
}
