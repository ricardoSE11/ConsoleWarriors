/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import Weapons.Weapon;
import consolewarriors.Common.AttackGroup;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Common.Message;
import consolewarriors.Common.ServerMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public class Match {
    
    private Player playerOne;
    private Player playerTwo;
    
    private IMatchLogger matchLogger;
    private IScoreRecorder scoreRecorder;
    
    private Player winner;
    private boolean ended;
    private int turn;

    public Match(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.ended = false;
        this.turn = 1;
        
        // Missing: match logger and score recorder initialization 
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public IMatchLogger getMatchLogger() {
        return matchLogger;
    }

    public void setMatchLogger(IMatchLogger matchLogger) {
        this.matchLogger = matchLogger;
    }

    public IScoreRecorder getScoreRecorder() {
        return scoreRecorder;
    }

    public void setScoreRecorder(IScoreRecorder scoreRecorder) {
        this.scoreRecorder = scoreRecorder;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    
    // </editor-fold>
    
    // Method to send the ranking and the stats of each player to both players
    public void sendPlayersRankingData(){
        
    }
    
    public void nextTurn(){
        this.turn++;
    }
    
    public void endMatch(Player winner){
        this.winner = winner;
        this.ended = true;
        
        try {
            playerTwo.getClientThread().getSocket().close();
            playerOne.getClientThread().getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public Player getPlayerByID(int playerID){
        if (playerOne.getPlayerID() == playerID){
            return playerOne;
        }
        
        return playerTwo;
    }
    
    public Player getEnemyOfPlayer(int playerID){
        if (playerOne.getPlayerID() == playerID){
            return playerTwo;
        }
        return playerOne;
    }

    public boolean isPlayersTurn(int playerID){
        if (playerTwo.getPlayerID() == playerID) {
            // Is not his turn
            if (turn % 2 != 0) {
                Message notYourTurnMessage = new ServerMessage("WRONG_TURN", "Not your turn");
                playerTwo.getClientThread().sendMessageToClient(notYourTurnMessage);
                return false;
            }
            return true;
        } 
        
        else {
            if (playerOne.getPlayerID() == playerID) {
                // Not his turn
                if (turn % 2 == 0) {
                    Message notYourTurnMessage = new ServerMessage("WRONG_TURN", "Not your turn");
                    playerOne.getClientThread().sendMessageToClient(notYourTurnMessage);
                    return false;
                }
            }
            return true;
        }
    }
    
    public void handlePlayerMessage(Message message){
        ClientMessage clientMessage = (ClientMessage) message;
        int playerID = clientMessage.getClientID();
        
        System.out.println("Got a message from client: " + playerID);
        
        String[] commandInfo = clientMessage.getEvent().split("-");
        String commandName = commandInfo[0];
        
        // CHAT is out because it does not depend on the turn
        if (commandName.equals("CHAT")){
            String messageText = (String) message.getObjectOfInterest();
            Message chatMessage = new ServerMessage("CHAT", messageText);
            Player enemy = getEnemyOfPlayer(playerID);
            enemy.getClientThread().sendMessageToClient(chatMessage);
            return;
        }

        if (isPlayersTurn(playerID) && !ended){
            // Area for improvemente
            switch (commandName) {
                case "ATTACK": {
                    // Receive the weapon
                    System.out.println("Player " + playerID + " is attacking");
                    AttackGroup attackParameters = (AttackGroup) message.getObjectOfInterest();

                    // Send the weapon to the enemy
                    Message attackMessage = new ServerMessage("ATTACK", attackParameters);
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(attackMessage);

                    nextTurn();
                }
                break;

                case "ATTACK_RESPONSE": {
                    System.out.println("Getting attack response");
                    Integer damageDealt = (Integer) message.getObjectOfInterest();
                    Message attackResponse = new ServerMessage("ATTACK_RESPONSE", damageDealt);
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(attackResponse);

                }
                break;
                
                // Send by a player who lost after an attack
                case "LOST":{
                    
                }
                break;

                case "PASS": {
                    Message passMessage = new ServerMessage("PASS", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(passMessage);
                    nextTurn();
                }
                break;

                case "PROPOSING_TIE": {
                    System.out.println("Player:" + playerID + " is proposing a tie");
                    Message tieMessage = new ServerMessage("TIE_PROPOSAL", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(tieMessage);
                    nextTurn();
                }
                break;

                case "TIE_ACCEPTED": {
                    Message tieAcceptedMessage = new ServerMessage("TIE_PROPOSSAL_ACCEPTED", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(tieAcceptedMessage);
                    endMatch(null);
                }
                break;

                case "TIE_DENIED": {
                    Message tieDeniedMessage = new ServerMessage("TIE_DENIED", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(tieDeniedMessage);
                    nextTurn();
                }
                break;

                case "SURRENDER": {
                    Message victoryMessage = new ServerMessage("VICTORY", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(victoryMessage);
                    endMatch(enemy);
                }
                break;

                case "RELOAD": {
                }
                break;

            }
        }
        else{
            System.out.println("Not player " + playerID + " 's turn");
        }
        
    }
    
}
