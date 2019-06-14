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
            gameWindow.setTurnLabelText("Your turn");
        }
        else{
            gameWindow.setTurnLabelText("Enemy's turn");
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

                //gameWindow.writeToConsole("\n" + "Command result", Color.yellow);

                String[] commandInfo = gameWindow.getLastLine().split("-");
                
                System.out.println("Wrote: " + gameWindow.getLastLine());
                
                if (commandInfo[0].toUpperCase().equals("CHAT")) {
                    System.out.println("Got a enter with command: " + commandInfo[0] + " and parameters: " + commandInfo[1]);
                    int hyphenIndex = gameWindow.getLastLine().indexOf("-");
                    commandInfo[1] = gameWindow.getLastLine().substring(hyphenIndex + 1);
                }

                String commandName = commandInfo[0].toUpperCase();
                String commandArguments = commandInfo[1];
                ICommandManager commandManager = player.getCommandManager();
                System.out.println("Command name:" + commandName);
                ICommand selectedCommand = commandManager.getCommand(commandName);

                if (selectedCommand instanceof NotFoundCommand) {
                    gameWindow.writeToConsole("\n" + "Please choose a valid command", Color.yellow);
                } 
                
                else if (selectedCommand == null){
                    System.out.println("Got a null command");
                }
                
                else {
                    selectedCommand.execute(commandArguments);
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
    }
    
    public void handleStringUpdates(String updateString){
        if (updateString.startsWith("STATUS")) {
            int hyphenIndex = updateString.indexOf("-");
            String status = updateString.substring(hyphenIndex + 1);
            
            switch (status){
                case "WRONG_TURN":{
                    this.gameWindow.writeToConsole("\n" + "Error: Is not your turn" + "\n", Color.RED);
                }
                break;
                
            }
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
