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
public abstract class Message implements Serializable {

    private String event;
    private Object objectOfInterest;
    
    public Message(String event, Object objectOfInterest) {
        this.event = event;
        this.objectOfInterest = objectOfInterest;
    }    

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
    
    public Object getObjectOfInterest() {
        return objectOfInterest;
    }

    public void setObjectOfInterest(Object objectOfInterest) {
        this.objectOfInterest = objectOfInterest;
    }

    // </editor-fold>

}
