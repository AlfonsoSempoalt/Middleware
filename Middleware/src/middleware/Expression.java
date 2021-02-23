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
    private Context context;
    
    public Expression(Context context) {
        this.context=context;
    }

    public Context interpret(){
        String [] values = aString(context.getInput());
        this.context.setOutput(toJSON(values));
        return this.context;
    }
    
    public String[] aString(String str) {
        String[] values = str.split(",");
        System.out.println(values);
        return values;
    }
    
    public JSONObject toJSON(String [] values){
        int i = 0;
        int fin = values.length;
        JSONObject obj = new JSONObject();
        while(i<fin){
            try {
                obj.put(values[i], values[i+1]);
                i+=2;
            } catch (JSONException ex) {
                return null;
            }
        }
        return obj;
    }
    
    
}
