/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

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
public class SocketJson extends Thread {

    private Socket socket;
    private JSONFramer jsonFramer;
    private InputStream in = null;
    private OutputStream out = null;
    private SendJSON sendString;
    private ByteArrayOutputStream messageBuffer;
    private DataInputStream dataInputStream;
    private String mensaje;
    
    public SocketJson(SendJSON send) {
        this.sendString = send;
        this.jsonFramer = new JSONFramer(in);
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
            out.write("2".getBytes());
            out.flush();
            int nextByte;
            String str = null;
            //Escuchar
            while (true) {
                nextByte = dataInputStream.readByte();
                messageBuffer.write(nextByte);
                str = new String(messageBuffer.toByteArray());
                System.out.println(str);
                this.mensaje = new String(str);
                if (mensaje.contains("}")) {
                    mensaje = mensaje.substring(0, mensaje.length());
                    this.mensaje = mensaje;
                    crearContextoJSON(mensaje);   
                    messageBuffer.reset();
                    this.mensaje = null;
                }
            }
        } catch (IOException ex) {

        }

    }

    public void notificarUsuario(String str) {
        this.sendString.respuesta(str);
    }
    
    public void crearContextoJSON(String str) {
        str = str.replaceAll(":", "-");
        str = str.replaceAll(",", "-");
        str = str.replaceAll("\"", "");
        str = str.substring(1, str.length() - 1);
        String[] valores = str.split("-", 0);
        String mensaje = obtenerMensaje(valores);
        this.notificarUsuario(str);
    }

    public String obtenerMensaje(String[]valores) {
        String mensaje = "";
        for (int i = 0; i < valores.length; i++) {
            if (i > 1) {
                mensaje += "-" + valores[i];
            }
        }
        return mensaje.substring(1, mensaje.length());
    }
    
    

    public void sendValues(String values) throws IOException {
        this.out = socket.getOutputStream();
        this.jsonFramer.frameMsg(values.getBytes(), out);
    }

    public void sleep(){
        try {
            this.socket.close();
            System.out.println("A mimir");
        } catch (IOException ex) {
            Logger.getLogger(SocketJson.class.getName()).log(Level.SEVERE, null, ex);
        }
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
