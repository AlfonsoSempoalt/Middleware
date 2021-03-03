/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MSI GF63
 */
public class Expression {

    public Expression() {

    }

    public void interpret(Context context) {
//        String [] values = aString(context.getInput());
//        this.context.setOutput(toJSON(values));
//        return this.context;  
        if (context.emisor.equalsIgnoreCase("F")) {
            if (context.receptor.equalsIgnoreCase("D")) {
                this.fijoToDelimitado(context);
            } else {

            }
        } else if (context.emisor.equalsIgnoreCase("D")) {
            if (context.receptor.equalsIgnoreCase("F")) {
                this.delimitadoToFijo(context);
            } else {

            }
        }
        if (context.emisor.equalsIgnoreCase("J")) {

        }
        //return ull;
    }

    private void delimitadoToFijo(Context context) {
        String s = context.getInput().toString();
        s = s.length()+1 + "-" + s;
        System.out.println(s);
        context.setOutput(s.getBytes());
        //return context;
    }

    private void fijoToDelimitado(Context context) {
        String s = context.getInput();
        String fijo = context.input+"/";
        context.setOutput(fijo.getBytes());        
    }

    private void convertFijotoJSON(Context context) {

    }

    public Context JSONtoFijo(Context context) {
        String s = context.getInput();
        String json = s.substring(1, s.length() - 1);
        json = json.replaceAll(":", "-");
        json = json.replace("\"", "");
        json = json.length() + "-" + json;
        context.setOutput(json.getBytes());
        return context;
    }

    public String[] aString(String str) {
        String[] values = str.split(",");
        System.out.println(values);
        return values;
    }

    public JSONObject toJSON(String[] values) {
        int i = 0;
        int fin = values.length;
        JSONObject obj = new JSONObject();
        while (i < fin) {
            try {
                obj.put(values[i], values[i + 1]);
                i += 2;
            } catch (JSONException ex) {
                return null;
            }
        }
        return obj;
    }

}
