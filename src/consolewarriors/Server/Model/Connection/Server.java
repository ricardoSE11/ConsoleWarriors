/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Connection;

import consolewarriors.Common.PlayerRanking;
import consolewarriors.Common.PlayerStats;
import consolewarriors.Server.Model.Game.MatchMaker;
import consolewarriors.Server.Utils.Player;
import consolewarriors.Server.Utils.Stats;
import consolewarriors.Server.Utils.StatsParser;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public class Server {

    protected int portNumber;
    protected boolean listening = true;
    protected HashMap<Integer, ServerThread> clients;
    protected IClientMessageHandler clientMessageHandler;
    protected StatsParser stats_parser;
    
    private MatchMaker matchMaker;
    private PlayerRanking ranking;

    public Server(int portnumber, IClientMessageHandler clientMessageHandler) {
        this.portNumber = portnumber;
        this.clientMessageHandler = clientMessageHandler;        
        this.clients = new HashMap<>();
        this.matchMaker = new MatchMaker();
        this.ranking = new PlayerRanking();
        stats_parser = new StatsParser();
        this.putStatsToMemory();
        this.matchMaker.setRanking(ranking);
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public HashMap<Integer, ServerThread> getClients() {
        return clients;
    }

    public void setClients(HashMap<Integer, ServerThread> clients) {
        this.clients = clients;
    }

    public IClientMessageHandler getClientMessageHandler() {
        return clientMessageHandler;
    }

    public void setClientMessageHandler(IClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }

    public MatchMaker getMatchMaker() {
        return matchMaker;
    }

    public void setMatchMaker(MatchMaker matchMaker) {
        this.matchMaker = matchMaker;
    }

    // </editor-fold>
    
    public void run() {        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Started server");
            while (listening) {
                /* If we receive a connection request, and it is successful, we
                *  create Thread and a Socket, and associated them with a user. 
                *  So the server keep listening for more connection requests */
                
                Socket clientSocket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(clientSocket, this);
                serverThread.setClientMessageHandler(clientMessageHandler);
                serverThread.start();
                System.out.println("Got connection request number - Currently attending " + clients.size() + " clients");
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void putStatsToMemory(){
        Stats current_load_stats = loadStats();
        ArrayList<PlayerStats> players_stats = new ArrayList<>();
        for(Player p : current_load_stats.getPlayers()){
            PlayerStats current = new PlayerStats(p.getUsername(), p.getSuccessfulAttacks(),
                p.getFailedAttacks(), p.getKills(), p.getWins(), p.getLoses(), p.getSurrenders());
            players_stats.add(current);
        }
        this.ranking.setRanking(players_stats);
    }
    
    public void putStatsToFile(){
        ArrayList<PlayerStats> players_stats = this.ranking.getRanking();
        List<Player> players = new ArrayList<>();
        for(PlayerStats p: players_stats){
            Player current = new Player(p.getPlayerName(), p.getWins(), p.getLosses(),
                p.getSurrenders(), p.getKills(), p.getSuccesfulAttacks(), p.getFailedAttacks());
            players.add(current);
        }
        Stats stats = new Stats(players);
        this.stats_parser.saveStatsToFile(stats);
    }
    
    public Stats loadStats(){
        return this.stats_parser.getStatsFromFile();
    }
    
    public void addClient(int clientID, ServerThread clientThread){
        this.clients.put(clientID, clientThread);
    }

    public int getIDForNewClient(){
        return clients.size() + 1;
    }
    
    public boolean existsPlayer(String username){
        return this.ranking.existsPlayer(username);
    }
    
    public void createNewPlayerStats(String username){
        ranking.createNewPlayerStats(username);
    }
            
    public void loadPlayerRanking(){
        /*
         * Load player ranking from a file and load it into the PlayerRanking class
         * Then load it into the matchmaker so it can be send to the players
         *              |
         *              V
        */
        matchMaker.setRanking(ranking);
        
    }

    String getPlayerStats(String username) {
        return ranking.getPlayerStats(username);
    }
}
