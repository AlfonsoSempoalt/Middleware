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
        if (context.receptor.equalsIgnoreCase("D")) {
            this.toDelimitador(context);
        } else if (context.receptor.equalsIgnoreCase("F")) {
            this.toFijo(context);
        } else if (context.receptor.equalsIgnoreCase("J")) {
            this.toJSON(context);
        }
    }
    
    private void toFijo(Context context) {
        String s = context.getInput().toString();
        s = s.length() + 1 + "-" + s;
        context.setOutput(s.getBytes());
        //return context;
    }
    
    private void toDelimitador(Context context) {
        String s = context.getInput();
        String fijo = context.input + "/";
        context.setOutput(fijo.getBytes());
    }

    /*
    * name-daniel-age-20
     */
    public void toJSON(Context context) {
        String s = context.getInput();
        String[] values = s.split("-", 0);
        int i = 0;
        int fin = values.length;
        JSONObject obj = new JSONObject();
        while (i < fin) {
            try {
                obj.put(values[i], values[i + 1]);
                i += 2;
            } catch (JSONException ex) {
                System.out.println("Error al convertir a Json");
            }
        }
        context.setOutput(obj.toString().getBytes());
    }
    
}
