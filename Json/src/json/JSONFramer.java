/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alfon
 */
public class JSONFramer implements Framer {

    private InputStream in;
    private static final int LONGITUD = 0;

    public JSONFramer(InputStream in) {
        this.in = in;
    }

    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        out.write(message);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
        int nextByte;
        DataInputStream dataInputStream = new DataInputStream(in);
        nextByte = dataInputStream.readByte();
        messageBuffer.write(nextByte);
        String str = new String(messageBuffer.toByteArray());
        int fin = Integer.parseInt(str);
        System.out.println(fin);
        for (int j = 1; j < fin; j++) {
            nextByte = dataInputStream.readByte();
            messageBuffer.write(nextByte);
            str = new String(messageBuffer.toByteArray());
        }
        return messageBuffer.toByteArray();
    }

}
