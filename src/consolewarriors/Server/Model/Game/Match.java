/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Game;

import consolewarriors.Common.AttackGroup;
import consolewarriors.Common.ClientMessage;
import consolewarriors.Common.Message;
import consolewarriors.Common.PlayerRanking;
import consolewarriors.Common.PlayerStats;
import consolewarriors.Common.ServerMessage;
import consolewarriors.Server.Utils.TextFileManager;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public class Match {
    
    private Player playerOne;
    private Player playerTwo;
    
    //private IMatchLogger matchLogger;
    private IScoreRecorder scoreRecorder;
    
    private IWildCardHandler wildCardHandler;
    private Player winner;
    private boolean ended;
    private int turn;
    private String match_log;
    private PlayerRanking ranking;
    private boolean currentWildCardAccepted;
    private final Date start_date;
    private final SimpleDateFormat formatter;
    private TextFileManager text_manager;

    public Match(Player playerOne, Player playerTwo, PlayerRanking ranking) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.ended = false;
        this.turn = 1;
        this.match_log = "";
        this.ranking = ranking;
        this.wildCardHandler = new WildCardHandler();
        wildCardHandler.startTimer();
        start_date = new Date();
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        text_manager = new TextFileManager();
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

    public String getMatch_log() {
        return match_log;
    }

    public void setMatch_log(String match_log) {
        this.match_log = match_log;
    }

    public PlayerRanking getRanking() {
        return ranking;
    }

    public void setRanking(PlayerRanking ranking) {
        this.ranking = ranking;
    }

    
    /*public IMatchLogger getMatchLogger() {
        return matchLogger;
    }

    public void setMatchLogger(IMatchLogger matchLogger) {
        this.matchLogger = matchLogger;
    }*/

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
    
    public void saveLogToFile(){
        String initial_date = formatter.format(start_date);
        String file_name = playerOne.getUsername() + "vs" +playerTwo.getUsername() + initial_date;
        this.text_manager.writeToFile(match_log, file_name);
    }
    
    public void handlePlayerMessage(Message message){
        ClientMessage clientMessage = (ClientMessage) message;
        int playerID = clientMessage.getClientID();
        
        System.out.println("Got a message from client: " + playerID);
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
        String[] commandInfo = clientMessage.getEvent().split("-");
        String commandName = commandInfo[0];
        
        // CHAT is out because it does not depend on the turn
        if (commandName.equals("CHAT")){
            String messageText = (String) message.getObjectOfInterest();
            Message chatMessage = new ServerMessage("CHAT", messageText);
            Player current = getPlayerByID(playerID);
            Player enemy = getEnemyOfPlayer(playerID);
            match_log += "Player "+current.getUsername()+" sent chat message '" +
                    messageText +"' to " + enemy.getUsername() + "at "+strDate+ "\n";
            enemy.getClientThread().sendMessageToClient(chatMessage);
            return;
        }
        
        // Moved out of the switch because it can be called at any given time
        else if (commandName.equals("SURRENDER")){
            Message victoryMessage = new ServerMessage("VICTORY", null);
            Player current = getPlayerByID(playerID);
            match_log += "Player "+current.getUsername()+" just surrended at " +
                strDate + "\n";
            Player enemy = getEnemyOfPlayer(playerID);
            
            PlayerStats enemy_stats = ranking.getPlayer(enemy.getUsername());
            enemy_stats.setWins(enemy_stats.getWins() + 1);
            
            PlayerStats current_stats = ranking.getPlayer(current.getUsername());
            current_stats.setSurrenders(current_stats.getSurrenders() + 1);
            
            enemy.getClientThread().sendMessageToClient(victoryMessage);
            endMatch(enemy);
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
                    Player current = getPlayerByID(playerID);
                    match_log += "Player "+current.getUsername()+" attacked to" +
                        enemy.getUsername() + " with the warrior "+ attackParameters.getWarrior().getName() +
                        " and weapon " +  attackParameters.getWeapon().getName()+ " at " + strDate +"\n";
                    enemy.getClientThread().sendMessageToClient(attackMessage);
                    nextTurn();
                }
                break;

                case "ATTACK_RESPONSE": {
                    System.out.println("Getting attack response");
                    Integer damageDealt = (Integer) message.getObjectOfInterest();
                    Message attackResponse = new ServerMessage("ATTACK_RESPONSE", damageDealt);
                    Player enemy = getEnemyOfPlayer(playerID);
                    match_log += "Player "+ enemy.getUsername()+" dealt a total of damage of " +
                        Integer.toString(damageDealt) + " at " +strDate +"\n";
                    //enemy was who attacked
                    PlayerStats enemy_stats = ranking.getPlayer(enemy.getUsername());
                    if(damageDealt > 100){
                        enemy_stats.setSuccesfulAttacks(enemy_stats.getSuccesfulAttacks() + 1);
                    }else{
                        enemy_stats.setFailedAttacks(enemy_stats.getFailedAttacks() + 1);
                    }
                    enemy.getClientThread().sendMessageToClient(attackResponse);

                }
                break;
                
                // Send by a player who lost after an attack
                case "LOST":{
                    
                }
                break;

                case "PASS": {
                    Message passMessage = new ServerMessage("PASS", null);
                    Player current = getPlayerByID(playerID);
                    match_log += "Player "+current.getUsername()+" just passed the turn at " +
                        strDate +"\n";
                    Player enemy = getEnemyOfPlayer(playerID);
                    enemy.getClientThread().sendMessageToClient(passMessage);
                    nextTurn();
                }
                break;

                case "PROPOSING_TIE": {
                    System.out.println("Player:" + playerID + " is proposing a tie");
                    Message tieMessage = new ServerMessage("TIE_PROPOSAL", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    Player current = getPlayerByID(playerID);
                    match_log += "Player "+current.getUsername()+" proposed a tie to" +
                    enemy.getUsername() + " at " + strDate +"\n";
                    enemy.getClientThread().sendMessageToClient(tieMessage);
                    nextTurn();
                }
                break;

                case "TIE_ACCEPTED": {
                    Message tieAcceptedMessage = new ServerMessage("TIE_PROPOSSAL_ACCEPTED", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    Player current = getPlayerByID(playerID);
                    match_log += "Player "+current.getUsername()+" accepted the tie to" +
                        enemy.getUsername() + " at " + strDate +"\n";
                    enemy.getClientThread().sendMessageToClient(tieAcceptedMessage);
                    endMatch(null);
                }
                break;

                case "TIE_DENIED": {
                    Message tieDeniedMessage = new ServerMessage("TIE_PROPOSSAL_DENIED", null);
                    Player enemy = getEnemyOfPlayer(playerID);
                    Player current = getPlayerByID(playerID);
                    match_log += "Player "+current.getUsername()+" denied the tie to" +
                        enemy.getUsername() + " at " + strDate +"\n";
                    enemy.getClientThread().sendMessageToClient(tieDeniedMessage);
                    nextTurn();
                }
                break;

                case "WILDCARD_ATTACK": {
                    if(wildCardHandler.isWildCardReady()){
                        if (wildCardHandler.grantedWildCard()){
                            this.currentWildCardAccepted = true;
                            AttackGroup attackParameters = (AttackGroup) message.getObjectOfInterest();
                            Message wildCardAttack = new ServerMessage("WILDCARD_ATTACK", attackParameters);
                            Player enemy = getEnemyOfPlayer(playerID);
                            enemy.getClientThread().sendMessageToClient(wildCardAttack);
                        }
                        
                        else{
                            Message rejectedWildCardMessage = new ServerMessage("REJECTED_WILDCARD", null);
                            Player player = getPlayerByID(playerID);
                            player.getClientThread().sendMessageToClient(rejectedWildCardMessage);
                        }
                    }
                    
                    else{
                        Message rejectedWildCardMessage = new ServerMessage("UNAVAILABLE_WILDCARD", null);
                        Player player = getPlayerByID(playerID);
                        player.getClientThread().sendMessageToClient(rejectedWildCardMessage);
                    }
                }
                break;
                
                case "SECOND_WILDCARD_ATTACK":{
                    if (currentWildCardAccepted){
                        AttackGroup attackParameters = (AttackGroup) message.getObjectOfInterest();
                        Message wildCardAttack = new ServerMessage("SECOND_WILDCARD_ATTACK", attackParameters);
                        Player enemy = getEnemyOfPlayer(playerID);
                        enemy.getClientThread().sendMessageToClient(wildCardAttack);
                        nextTurn();
                    }
                }
                break;

            }
        }
        else{
            System.out.println("Not player " + playerID + " 's turn");
        }
        
    }
    
}
