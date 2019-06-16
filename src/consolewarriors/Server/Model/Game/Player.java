/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import consolewarriors.Server.Model.Connection.ServerThread;

/**
 *
 * @author rshum
 */
public class Player {
    
    private ServerThread clientThread;
    private String my_stats;
    private String my_enemy_stats;

    public Player(ServerThread clientThread) {
        this.clientThread = clientThread;
        this.my_stats = "";
        this.my_enemy_stats = "";
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    // Handmade with love.
    public int getPlayerID(){
        return clientThread.getID();
    }
    
    public String getUsername(){
        return clientThread.getUsername();
    }
    
    public ServerThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ServerThread clientThread) {
        this.clientThread = clientThread;
    }
    
    public String getMy_stats() {
        return my_stats;
    }

    public void setMy_stats(String my_stats) {
        this.my_stats = my_stats;
    }

    public String getMy_enemy_stats() {
        return my_enemy_stats;
    }

    public void setMy_enemy_stats(String my_enemy_stats) {
        this.my_enemy_stats = my_enemy_stats;
    }
    
    // </editor-fold>
}
