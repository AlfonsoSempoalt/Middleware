/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Alfon
 */
public class SocketCliente extends Thread {
 private Socket socket;
    private InputStream in = null;
    private OutputStream out = null;
    private SendString respuesta;
   
    public SocketCliente(SendString send) {
        this.respuesta=send;
    }

    public void conectar() {
        try {
            // need host and port, we want to connect to the ServerSocket at port 7777
            socket = new Socket("localhost", 7777);
            in = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(in);
            String fromUser; 
            System.out.println("Hola");
            while(true){
               String mensaje;
               if((mensaje = dataInputStream.readUTF())!=null){
                   this.respuesta.respuesta(mensaje);
               }
            }
        } catch (IOException ex) {
            
        }

    }

    public void sendString(String values) throws IOException {

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        System.out.println("Sending string to the ServerSocket");

        System.out.println(values);
        // write the message we want to send
        dataOutputStream.writeUTF(values);
        dataOutputStream.flush(); // send the message
        //dataOutputStream.close(); // close the output stream when we're done.

        System.out.println("Closing socket and terminating program.");
        //socket.close();
    }

    @Override
    public void run() {
         try {
            this.conectar();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
