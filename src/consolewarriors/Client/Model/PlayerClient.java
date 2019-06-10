/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import Characters.Character;
import consolewarriors.Common.Command.CommandManager;

/**
 *
 * @author rshum
 */
public class PlayerClient extends Client {
    
    private int playerID;
    private String playerName;
    private ArrayList<Character> warriors;
    
    private CommandManager commandManager;
    
    public PlayerClient(String hostname, int portNumber , String playerName , ArrayList<Character> playerWarriors ) {
        super(hostname, portNumber);
        this.playerName = playerName;
        this.warriors = playerWarriors;
        this.commandManager = new CommandManager();
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public IServerMessageHandler getServerMessageHandler() {
        return serverMessageHandler;
    }

    public void setServerMessageHandler(IServerMessageHandler serverMessageHandler) {
        this.serverMessageHandler = serverMessageHandler;
    }
    
    public ArrayList<Character> getWarriors() {
        return warriors;
    }

    public void setWarriors(ArrayList<Character> warriors) {
        this.warriors = warriors;
    }    
    
    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }


    
    // </editor-fold>




    
    
    
}
