/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Alfon
 */
public class DelimFramer implements Framer {

    private InputStream in;
    private static final byte DELIMITER = '/';
    public DelimFramer(InputStream in) {
        this.in = in;
    }

    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        for (byte b:message) {
            if (b==DELIMITER) {
                throw new IOException("message ontains delimiter");
            }
        }
        out.write(message);
        out.write(DELIMITER);
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
