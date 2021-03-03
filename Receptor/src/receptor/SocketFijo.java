/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receptor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
public class SocketFijo extends Thread {

    private Socket socket;
    private LFijoFramer lfijoFramer;
    private InputStream in = null;
    private OutputStream out = null;
    private SendFijo sendString;
    private ByteArrayOutputStream messageBuffer;
    private DataInputStream dataInputStream;
    private String mensaje;
    
    public SocketFijo(SendFijo send) {
        this.sendString = send;
        this.lfijoFramer = new LFijoFramer(in);
        messageBuffer = new ByteArrayOutputStream();
    }

    public void conectar() {
        try {
            // need host and port, we want to connect to the ServerSocket at port 7777
            socket = new Socket("localhost", 7777);
            in = socket.getInputStream();
            dataInputStream = new DataInputStream(in);
            this.out = socket.getOutputStream();
            //0 Para delimitado
            //1 Para fijo
            //2 Json
            out.write("1".getBytes());
            out.flush();
            int nextByte;
            String str = null;
            while (true) {
                messageBuffer = new ByteArrayOutputStream();
                int fin = this.numero();
                for (int j = 1; j < fin; j++) {
                    nextByte = dataInputStream.readByte();
                    messageBuffer.write(nextByte);
                    str = new String(messageBuffer.toByteArray());
                    System.out.println(str);
                    this.mensaje=new String(str);
                }
                 this.notificarUsuario(mensaje);
                 this.messageBuffer.reset();
                
            }
        } catch (IOException ex) {

        }

    }

    public int numero() {
        int nextByte;
        String numero;
        while (true) {
            try {
                nextByte = dataInputStream.readByte();
                messageBuffer.write(nextByte);
                numero = new String(messageBuffer.toByteArray());
                if (numero.contains("-")) {
                    numero = numero.substring(0, numero.length() - 1);
                    return Integer.parseInt(numero);
                }
            } catch (IOException ex) {
                Logger.getLogger(SocketFijo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void notificarUsuario(String str) {
        this.sendString.respuesta(str);
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

    public void sendValues(String values) throws IOException {
        this.out = socket.getOutputStream();
        this.lfijoFramer.frameMsg(values.getBytes(), out);
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
