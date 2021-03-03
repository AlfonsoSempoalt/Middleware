package middleware;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MSI GF63
 */
public class HiloSocket extends Thread {

    private MiddlewareSocket servidor;
    private Socket socket;
    private String identificador;
    private boolean first = true;
    private int i = 0;
    private int aux;
    private int nextByte;
    private String str;
    private ByteArrayOutputStream messageBuffer;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    @Override
    public void run() {
        try {
            OutputStream out = null;
            InputStream in = null;
            String mensaje = null;
            String inputLine, outputLine;
            in = socket.getInputStream();
            dataInputStream = new DataInputStream(in);
            out = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(out);
            /*
             * En dado caso de que escuche algo
             * //dataInputStream.readUTF()
             */
            messageBuffer = new ByteArrayOutputStream();
            while (true) {
                if (first == true) {
                    nextByte = dataInputStream.readByte();
                    messageBuffer.write(nextByte);
                    str = new String(messageBuffer.toByteArray());
                    System.out.println(str);
                    this.identificar(str);
                    //limpiar
                    messageBuffer.reset();
                    first = false;
                    System.out.println(this.identificador);
                } else {
                    nextByte = dataInputStream.readByte();
                    messageBuffer.write(nextByte);
                    str = new String(messageBuffer.toByteArray());
                    aux = esFijo(str);
                    System.out.println(aux);
                    if (aux != -1) {
                        leerFijo();                        
                    } else {
                        leerDelimitado();
                    }
                }
            }
//            out.close();
//            in.close();
//            socket.close();
        } catch (IOException ex) {
            // Logger.getLogger(HiloSocket.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HiloSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void identificar(String valor) {
        if (valor.equals("0")) {
            this.identificador = "D";
        } else if (valor.equals("1")) {
            this.identificador = "F";
        } else {
            this.identificador = "J";
        }
    }

    public void leerDelimitado() {
        while (true) {
            try {
                nextByte = dataInputStream.readByte();
                messageBuffer.write(nextByte);
                str = new String(messageBuffer.toByteArray());
                if (str.contains("/")) {
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(HiloSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(str);
        messageBuffer.reset();
        this.crearContextoDelimitador();
    }

    public void leerFijo() {
        int num = numero();        
        for (int j = 0; j < num; j++) {
            try {
                nextByte = dataInputStream.readByte();
                messageBuffer.write(nextByte);
                str = new String(messageBuffer.toByteArray());                
            } catch (IOException ex) {
                Logger.getLogger(HiloSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        System.out.println(str);
        messageBuffer.reset();
        crearContextoFijo(new String(num+"").length()+1);
    }

    public int esFijo(String texto) {
        texto.substring(0, 0);
        if (texto.matches("[0-9]+")) {
            return Integer.parseInt(texto);
        } else {
            return -1;
        }
    }

    public void crearContextoDelimitador() {
        //asignar id al conectar
        String emisor = this.identificador;
        String receptor =str.substring(str.length()-2, str.length()-1);
        String mensaje = str.substring(0, str.length() - 3);
        Context context = new Context(mensaje, emisor, receptor);
        System.out.println(context.toString());
        servidor.ajustarContexto(context);
    }
    
        public void crearContextoFijo(int numFijo) {
        //asignar id al conectar
        
        String emisor = this.identificador;
        String receptor =str.substring(str.length()-1, str.length());
        String mensaje = str.substring(numFijo, str.length() - 2);
        Context context = new Context(mensaje, emisor, receptor);
        System.out.println(context.toString());
        servidor.ajustarContexto(context);
    }

    public HiloSocket(Socket socket, MiddlewareSocket servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    public String getIndentificador() {
        return identificador;
    }

    public void setIndentificador(String indentificador) {
        this.identificador = indentificador;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
