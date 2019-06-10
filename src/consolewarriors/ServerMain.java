/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors;

import consolewarriors.Client.View.GameWindow;
import consolewarriors.Server.Model.Connection.ClientMessageHandler;
import consolewarriors.Server.Model.Connection.IClientMessageHandler;
import consolewarriors.Server.Model.Connection.Server;


/**
 *
 * @author rshum
 */
public class ServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
//        IClientMessageHandler messageHandler = new ClientMessageHandler();
//        Server server = new Server(1234, messageHandler);
//        server.run();
        
        GameWindow gm = new GameWindow();
        gm.setVisible(true);
        
    }
    
}
