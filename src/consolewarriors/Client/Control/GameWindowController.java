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
import consolewarriors.Common.Shared.Warrior;


/**
 *
 * @author rshum
 */
public class GameWindowController implements IObserver{
    
    // View
    private GameWindow gameWindow;
    
    // Model
    private PlayerClient player;
    private ArrayList<Characters.Character> warriors;

    public GameWindowController(GameWindow gameWindow, PlayerClient player) {
        this.gameWindow = gameWindow;
        this.player = player;
        this.warriors = player.getWarriors();
        
        gameWindow.addEventListener(new WindowListener());
        gameWindow.setUpWarriorsData(warriors);
        gameWindow.setUpWarriorsImages(warriors);
        
        queueForMatch();
        subscribeToWarriors();
    }
    
    public void queueForMatch() {
        Message readyMessage = new ClientMessage("READY", player.getId(), "NO_OBJECT");
        player.sendMessage(readyMessage);
    }

    public void subscribeToWarriors(){
        for (Character character : warriors){
            Warrior currentWarrior = (Warrior) character;
            currentWarrior.addObserver(this);
        }
    }
    
    @Override
    public void update(Object object) {
        // Act according to the type of object we receive
    }

    class WindowListener implements EventListener {
        
        public void keyPresed(KeyEvent ke){
            if ((ke.getKeyChar() == '\n')) {
                
                gameWindow.writeToConsole("\n" + "Command result", Color.yellow);

//                String[] commandInfo = gameWindow.getLastLine().split("-");
//                if (commandInfo[0].toUpperCase().equals("CHAT")) {
//                    int hyphenIndex = gameWindow.getLastLine().indexOf("-");
//                    commandInfo[1] = gameWindow.getLastLine().substring(hyphenIndex + 1);
//                }
//
//                String commandName = commandInfo[0];
//                String commandArguments = commandInfo[1];
//                CommandManager commandManager = player.getCommandManager();
//                ICommand selectedCommand = commandManager.getCommand(commandName);
//
//                if (selectedCommand instanceof NotFoundCommand) {
//                    gameWindow.writeToConsole("\n" + "Please choose a valid command", Color.yellow);
//                } else {
//                    selectedCommand.execute(commandArguments);
//                }

                //System.out.println("Got a enter with command: " + commandInfo[0] + " and parameters: " + commandInfo[1]);
            }
        }

    }
   
    
    
    
}
