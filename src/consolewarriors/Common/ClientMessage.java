/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common;

import java.io.Serializable;

/**
 *
 * @author rshum
 */
public class ClientMessage extends Message implements Serializable {

    // The client events are going to be the Server commands
    private int clientID; 

    public ClientMessage(String event, int clientID , Object objectOfInterest) {
        super(event, objectOfInterest);
        this.clientID = clientID;
    }
    


    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    
    
    // </editor-fold>

}
