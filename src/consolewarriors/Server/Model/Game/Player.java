/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import java.util.HashMap;
import Characters.Character;
import consolewarriors.Server.Model.Connection.ServerThread;

/**
 *
 * @author rshum
 */
public class Player {
    
    private ServerThread clientThread;
    private HashMap<String, Character> characters;

    public Player(ServerThread clientThread, HashMap<String, Character> characters) {
        this.clientThread = clientThread;
        this.characters = characters;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    // Handmade with love.
    public int getPlayerID(){
        return clientThread.getID();
    }
    
    public ServerThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ServerThread clientThread) {
        this.clientThread = clientThread;
    }

    public HashMap<String, Character> getCharacters() {
        return characters;
    }

    public void setCharacters(HashMap<String, Character> characters) {
        this.characters = characters;
    }    
    
    // </editor-fold>


}
