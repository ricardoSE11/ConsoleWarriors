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
import consolewarriors.Client.IObservable;
import consolewarriors.Client.IObserver;
import consolewarriors.Common.Command.ICommandManager;
import consolewarriors.Common.Command.PlayerCommandManager;

/**
 *
 * @author rshum
 */
public class PlayerClient extends Client implements IObservable {
    
    private ArrayList<Character> warriors;
    private ICommandManager commandManager;
    
    private ArrayList<IObserver> observers;
    private ArrayList<String> chatMessages;
    private String playerStatus = ""; // attribute for the SURRENDER , and TIE commands
    
    public PlayerClient(String hostname, int portNumber , String playerName , ArrayList<Character> playerWarriors ) {
        super(hostname, portNumber);
        this.warriors = playerWarriors;
        this.commandManager = new PlayerCommandManager();
        this.chatMessages = new ArrayList<>();
    }

    public PlayerClient(String hostname, int portNumber, String username, boolean newSession) {
        super(hostname, portNumber, username, newSession);
        this.playerStatus = "WAITING_FOR_MATCH";
        this.observers = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
    }

    public PlayerClient(String hostname, int portNumber, int id) {
        super(hostname, portNumber, id);
        this.playerStatus = "WAITING_FOR_MATCH";
        this.observers = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    public int getPlayerID() {
        return super.id;
    }

    public void setPlayerID(int playerID) {
        super.id = playerID;
    }

    public String getPlayerName() {
        return super.username;
    }

    public void setPlayerName(String playerName) {
        super.username = playerName;
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
    
    public ICommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(ICommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Character getWarriorByName(String warriorName){
        Character choosenCharacter = null;
        for (Character character : warriors){
            if (character.name.equals(warriorName)){
                return choosenCharacter;
            }
        }
        return choosenCharacter;
    }

    public void addChatMessage(String chatMessage){
        this.chatMessages.add(chatMessage);
        notify(chatMessage);
    }
    
    public ArrayList<String> getChatMessages() {
        return chatMessages;
    }

    public String getPlayerStatus() {
        return playerStatus;
    }
    
    public void changePlayerGamingStatus(String status){
        this.playerStatus = status;
        notify("STATUS-" + status);
    }
    
    // </editor-fold>

    @Override
    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notify(Object object) {
        for (IObserver observer : observers) {
            observer.update(object);
        }
    }




    
    
    
}
