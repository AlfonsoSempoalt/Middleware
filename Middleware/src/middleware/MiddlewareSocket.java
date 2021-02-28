/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alfon
 */
public class MiddlewareSocket {

    private ServerSocket ss;
    private Socket socket;

    public static void main(String[] args) throws IOException {
        MiddlewareSocket md = new MiddlewareSocket();
        md.conect();
    }

    public void conect() {
        // don't need to specify a hostname, it will be the current machine
        try {
            this.ss = new ServerSocket(7777);
            System.out.println("ServerSocket awaiting connections...");
            while (true) {
                socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
                System.out.println("Connection from " + socket + "!");
                new HiloSocket(socket).start();
                //this.listen();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listen() {
        // get the input stream from the connected socket
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            // read the message from the socket
            String message = dataInputStream.readUTF();
            System.out.println("The message sent from the socket was: " + message);

            this.enviarJsonString(this.obtenerStringJSon(message));

            System.out.println("Closing sockets.");
            ss.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String obtenerStringJSon(String message) {
        Context context = new Context(message);
        Expression exp = new Expression(context);
        return exp.interpret().getOutput().toString();
    }

    public void enviarJsonString(String json) throws IOException {
        Socket socket = new Socket("localhost", 4444);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        System.out.println("Sending string to the ServerSocket");

        System.out.println(json);
        // write the message we want to send
        dataOutputStream.writeUTF(json);
        dataOutputStream.flush(); // send the message
        dataOutputStream.close(); // close the output stream when we're done.

        System.out.println("Closing socket and terminating program.");
        socket.close();
    }

}
