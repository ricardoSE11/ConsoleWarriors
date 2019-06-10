/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Connection;

import consolewarriors.Common.Message;

/**
 *
 * @author rshum
 */
public interface IClientMessageHandler {

    public void handleClientMessage(Message message, Server server);

}
