/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receptor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alfon
 */
public class Receptor {

    private ServerSocket ss;
    private Socket socket;

    /**
     * @param args the command line arguments
     */
    public String escuchar() throws IOException {
        this.conectar();
        // get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        // read the message from the socket
        String message = dataInputStream.readUTF();
        System.out.println("Closing sockets.");
        ss.close();
        socket.close();
        return message;
    }

    public void conectar() {
        try {
            ss = new ServerSocket(4444);
            System.out.println("ServerSocket awaiting connections...");
            socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Connection from " + socket + "!");
        } catch (IOException ex) {
            Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
