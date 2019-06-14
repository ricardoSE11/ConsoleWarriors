/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client.Model;

import consolewarriors.Common.Message;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rshum
 */
public abstract class Client {

    protected String hostname;
    protected int portNumber;

    protected String username;
    protected int id;

    protected Socket socket = null;
    protected ObjectInputStream reader;
    protected ObjectOutputStream writer;
    protected IServerMessageHandler serverMessageHandler;

    protected boolean newSession;
    
    public Client(String hostname, int portNumber) {
        this.hostname = hostname;
        this.portNumber = portNumber;
    }
    
    public Client(String hostname , int portNumber , String username , boolean newSession){
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.username = username;
        this.newSession = newSession;
    }
    
    public Client(String hostname, int portNumber, int id) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.id = id;
        this.newSession = false;
    }
    

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">   
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
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

    public IServerMessageHandler getServerMessageHandler() {
        return serverMessageHandler;
    }

    public void setServerMessageHandler(IServerMessageHandler serverMessageHandler) {
        this.serverMessageHandler = serverMessageHandler;
    }

    // </editor-fold>
    
    public void run() {
        try {
            this.socket = new Socket(hostname, portNumber);
            // --- Preparing to send messages to server ---
            OutputStream outputStream = socket.getOutputStream();
            this.writer = new ObjectOutputStream(outputStream);

            // --- Preparing to receive messages from server ---
            InputStream inputStream = socket.getInputStream();
            this.reader = new ObjectInputStream(inputStream);
            
            if(newSession){
                // --- Tell the server is the first time we play and ask for an ID ---
                DataOutputStream sessionIndicator = new DataOutputStream(outputStream);
                sessionIndicator.writeUTF("NEW");
                
                // --- We receive the ID assigned --- 
                DataInputStream idReceiver = new DataInputStream(inputStream);
                int assignedID = idReceiver.readInt();
                this.id = assignedID;
                
                System.out.println("Received my ID: " + id);
            }
            else{
                // --- Send the ID to the server for identification ---
                DataOutputStream idSender = new DataOutputStream(outputStream);
                idSender.writeInt(id);
                
                System.out.println("Confirming ID: " + id);
            }


            ClientThread clientThread = new ClientThread(this, reader); // Thread to listen for server messages
            clientThread.setServerMessageHandler(serverMessageHandler);
            clientThread.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(Message message) {
        try {
            this.writer.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
