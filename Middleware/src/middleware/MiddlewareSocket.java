/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alfon
 */
public class MiddlewareSocket {
    private ServerSocket serverSocket;
    private int port;
    public static int clients = 0;


    public void establish(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        System.out.println("JSONServer has been established on port " + port);

    }


    public void accept() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Runnable r = new MyThreadHandler(socket);
            Thread t = new Thread(r);
            t.start();
        }
    }

    private static class MyThreadHandler implements Runnable {
        private Socket socket;

        MyThreadHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            clients++;
            System.out.println(clients + " JSONClient(s) connected on port: " + socket.getPort());

            try {
                // For JSON Protocol
                JSONObject jsonObject = receiveJSON();
                sendJSON(jsonObject);

            } catch (IOException e) {

            } finally {
                try {
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void closeSocket() throws IOException {
            socket.close();
        }


        /**
         * use the JSON Protocol to receive a json object as
         * String from the client and reconstructs that object
         * @return JSONObejct with the same state (data) as
         * the JSONObject the client sent as a String msg.
         * @throws IOException
         */
        public JSONObject receiveJSON() throws IOException {
            InputStream in = socket.getInputStream();
            ObjectInputStream i = new ObjectInputStream(in);
            JSONObject line = null;
            try {
                line = (JSONObject) i.readObject();
                
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                 e.printStackTrace();
            
            }
            
            JSONObject jsonObject = new JSONObject(line);
            System.out.println("Got from client on port " + socket.getPort() + " " + jsonObject.get("key").toString());
            return jsonObject;
        }


        public void sendJSON(JSONObject jsonObject) throws IOException {
               JSONObject jsonObject2 = new JSONObject();
             jsonObject2.put("key", new Paper(250,369));
             
             OutputStream out = socket.getOutputStream();
              ObjectOutputStream o = new ObjectOutputStream(out);
             o.writeObject(jsonObject2);
              out.flush();
              System.out.println("Sent to server: " + " " + jsonObject2.get("key").toString());
    }
    }

    public void start(int port) throws IOException{
        establish(port);
        accept();
    }

    public static void main(String[] args) {
        JSONServer server = new JSONServer();

        try {
            server.start(7777);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e);
        }
    }
    
    public static void main(String[] args) {
        

    }
}