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
    protected JSONObject output;

    public Context(String input ) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public JSONObject getOutput() {
        return output;
    }

    public void setOutput(JSONObject output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Context{" + "input=" + input + ", output=" + output + '}';
    }
    
    
    
}
