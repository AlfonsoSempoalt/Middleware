/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
/**
 *
 * @author Alfon
 */
public class SocketCliente extends Socket{
    private String ip;
    private int port;

    public SocketCliente(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    public void sendJson(String address, int port, JSONObject json) throws IOException{
        Socket s = new Socket(address, port);
        try (OutputStreamWriter out = new OutputStreamWriter(
        s.getOutputStream(), StandardCharsets.UTF_8)) {
        out.write(json.toString());
        }
    }
    
}
