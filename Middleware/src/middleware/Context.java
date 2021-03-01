/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import org.json.JSONObject;

/**
 *
 * @author MSI GF63
 */
public class Context {

    protected String input;
    protected String emisor;
    protected String receptor;
    protected byte[] output;

    public Context(String input, String emisor, String receptor) {
        this.input = input;
        this.emisor = emisor;
        this.receptor = receptor;
    }


    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public byte[] getOutput() {
        return output;
    }

    public void setOutput(byte[] output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Context{" + "input=" + input + ", emisor=" + emisor + ", receptor=" + receptor + '}';
    }
      
}
