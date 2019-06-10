/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model;

import consolewarriors.Common.Message;

/**
 *
 * @author rshum
 */
public interface IServerMessageHandler {
    
    public void handleServerMessage(Message message);
    
}
