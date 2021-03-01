package Framer;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  Esta clase trabaja con delimitador utilizando el caracter / 
 * @author Alfon
 */
public class FijoFramer implements Framer {

    private InputStream in;
    private static final byte DELIMITER = '/';
    public FijoFramer(InputStream in) {
        this.in = in;
    }
    
    /**
     * Método utizado para enviar la cadena de bytes que conforman los valores
     * @param message Mesaje 
     * @param out Salida
     * @throws IOException 
     */
    public void frameMsg(byte[] message, OutputStream out) throws IOException {
//        for (byte b:message) {
//            if (b == DELIMITER) {
//                throw new IOException("message ontains delimiter");
//            }
//        }
        System.out.println("Aqui framer fijo enviando");
        out.write(message);
        //out.write(DELIMITER);
        out.flush();
    }
        
    @Override
    public byte[] nextMsg() throws IOException {
       ByteArrayOutputStream messageBuffer= new ByteArrayOutputStream();
       int nextByte;
       
       while((nextByte=in.read())!=DELIMITER){
           if (nextByte==-1) {
               if (messageBuffer.size()==0) {
                   return null;
               }else{
                   throw new EOFException("non empty message without delimiter");
               }
           }
        messageBuffer.write(nextByte);
       }    
       return messageBuffer.toByteArray();
    }

}
