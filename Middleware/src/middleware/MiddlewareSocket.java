/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import Framer.DelimFramer;
import Framer.FijoFramer;
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
import java.util.ArrayList;
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
    private Expression expression;
    private DelimFramer delimFramer;
    private FijoFramer fijoFramer;
    private ArrayList <HiloSocket> sockets;
    
    public static void main(String[] args) throws IOException {
        MiddlewareSocket md = new MiddlewareSocket();
        
        md.conect();
    }
    
    public MiddlewareSocket(){
        this.sockets= new ArrayList<>();
        this.expression= new Expression();
        
    }
            
    public void conect() {
        // don't need to specify a hostname, it will be the current machine
        try {
            this.ss = new ServerSocket(7777);
            System.out.println("ServerSocket awaiting connections...");
            while (true) {
                socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
                System.out.println("Connection from " + socket + "!");
                //quitar izquierda
                HiloSocket hilo = new HiloSocket(socket, this);
                hilo.start();
                sockets.add(hilo);
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

            //this.enviarJsonString(this.obtenerStringJSon(message));
            System.out.println("Closing sockets.");
            ss.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void ajustarContexto(Context context){
        System.out.println(context.toString());
        this.expression.interpret(context);
        //Context contextEnviar = 
        try {
            this.enviar(context);
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviar(Context context) throws IOException{
        Socket soc = obtenerSocket(context.getReceptor()).getSocket();
        InputStream inputStream= soc.getInputStream();
        OutputStream out = soc.getOutputStream();
        this.fijoFramer= new FijoFramer(inputStream);
        if(context.receptor.equalsIgnoreCase("F")){
            this.fijoFramer.frameMsg(context.getOutput(), out);
        }
        if(context.receptor.equalsIgnoreCase("D")){
            this.delimFramer.frameMsg(context.getOutput(), out);
        }
        if(context.receptor.equalsIgnoreCase("J")){
            
        }
    }
    
    public HiloSocket obtenerSocket(String emisor){
        for (HiloSocket soc : sockets) {
            if(soc.getIndentificador().equalsIgnoreCase(emisor)){
                return soc;
            }
        }
        return null;
    }

}
