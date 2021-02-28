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
import java.net.Socket;
import java.util.logging.Level;

/**
 *
 * @author MSI GF63
 */
public class HiloSocket extends Thread {

    private Socket socket;

    @Override
    public void run() {

        try {
            OutputStream out = null;
            InputStream in = null;
            String mensaje = null;
            String inputLine, outputLine;
            in = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(in);
            out = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            /*
             * En dado caso de que escuche algo
             *
             */
            while ((inputLine = dataInputStream.readUTF()) != null) {
                //outputLine = kkp.processInput(inputLine);
                //out.println(outputLine);
                //if (outputLine.equals("Bye.")) {
                //    break;
                /// }
                System.out.println(inputLine);
                dataOutputStream.writeUTF(inputLine);
                dataOutputStream.flush();

            }

//            out.close();
//            in.close();
//            socket.close();

        } catch (IOException ex) {
            // Logger.getLogger(HiloSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public HiloSocket(Socket socket) {
        this.socket = socket;
    }
}
