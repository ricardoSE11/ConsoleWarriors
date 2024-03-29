/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Connection;

import consolewarriors.Server.Model.Game.MatchMaker;
import consolewarriors.Server.Model.Game.Player;
import consolewarriors.Common.Message;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Server.Model.Game.Match;

/**
 *
 * @author rshum
 */
public class ClientMessageHandler implements IClientMessageHandler{
    
    public ClientMessageHandler() {

    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    // </editor-fold>
 
    
    @Override
    public void handleClientMessage(Message message, Server server) {
        String event = message.getEvent();
        if (event.equals("READY")){
            int clientID = ((ClientMessage)message).getClientID();
            System.out.println("Player with ID: " + clientID + " is ready for a match");
            ServerThread clientThread = server.getClients().get(clientID); 
            
            Player readyPlayer = new Player(clientThread);

            MatchMaker matchMaker = server.getMatchMaker();
            matchMaker.addPlayerToQueue(readyPlayer);
            System.out.println("Added player with ID:" + readyPlayer.getPlayerID() + " to queue");
        }
        else{
            MatchMaker matchMaker = server.getMatchMaker();
            ClientMessage clientMessage = (ClientMessage) message;
            
            System.out.println("Looking the match for id:" + clientMessage.getClientID() );
            
            Match playerMatch = matchMaker.getPlayerMatch(clientMessage.getClientID());
            if (playerMatch == null){
                System.out.println("I am null bro");
            }
            playerMatch.handlePlayerMessage(message);
        }
    }
    
}
