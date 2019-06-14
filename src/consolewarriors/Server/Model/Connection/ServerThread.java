/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Model.Connection;

import consolewarriors.Common.Message;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public class ServerThread extends Thread  {

    private Server server = null;
    private Socket socket = null;
    private int id;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private IClientMessageHandler clientMessageHandler;
    
    private int matchID;

    public ServerThread(Socket socket, int clientID, Server server) {
        this.server = server;
        this.socket = socket;
        this.id = clientID;
    }
    
    public ServerThread(Socket socket, Server server){
        this.server = server;
        this.socket = socket;
    }
    

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public IClientMessageHandler getClientMessageHandler() {
        return clientMessageHandler;
    }

    public void setClientMessageHandler(IClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    
    
    // </editor-fold>

    @Override
    public void run() {
        boolean connected = true;
        try {
            // --- Preparing to send messages to client ---
            OutputStream outputStream = socket.getOutputStream();
            this.writer = new ObjectOutputStream(outputStream);

            // --- Preparing to receive messages from client ---
            InputStream inputStream = socket.getInputStream();
            this.reader = new ObjectInputStream(inputStream);

            // --- We ask the client if its new to the server --- 
            DataInputStream sessionVerifier = new DataInputStream(inputStream);
            String session = sessionVerifier.readUTF();
            
            if (session.equals("NEW")){
                int newClientID = server.getIDForNewClient();
                server.addClient(newClientID, this);
                
                // --- We assign an ID to the client --- 
                DataOutputStream idAssigner = new DataOutputStream(outputStream);
                idAssigner.writeInt(newClientID);
            }
            else{
                // --- Receive their ID otherwise --- 
                DataInputStream idReceiver = new DataInputStream(inputStream);
                int oldClientID = idReceiver.readInt();
                System.out.println("Welcome client " + oldClientID);
            }
            
            while (connected) {
                Message clientMessage = (Message) reader.readObject();
                this.clientMessageHandler.handleClientMessage(clientMessage, server);
            }
        }
        
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("Something went wrong with client with ID:" + this.id);
            //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public void sendMessageToClient(Message message){
        try {
            writer.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
