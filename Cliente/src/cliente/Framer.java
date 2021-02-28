/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Alfon
 */
public interface Framer {
    void frameMsg(byte[] message,OutputStream out) throws IOException;
    byte[] nextMsg()throws IOException;
}
