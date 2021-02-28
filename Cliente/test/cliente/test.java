/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alfon
 */
public class test {

    public static void main(String[] args) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("nombre", "juan");
        json.put("apellido", "López");
        json.put("Edad", "12");
        System.out.println(JSONFijo(json.toString()));
        String fijo = "F-nombre-juan-apellido-lopez-edad-12";
        fijo = fijo.length() + "-" + fijo;
        String delimitado = "D-nombre-juan-apellido-lopez-edad-12-F";
        System.out.println(delimitadorFijo(delimitado));
        System.out.println(fijoDelmitador(fijo.substring(0)));
    }

    public static String delimitadorFijo(String s) {
        String fijo = s.substring(2, s.length() - 2);
        fijo = fijo.length() + "-" + fijo;
        return fijo;
    }
    
    public static String JSONFijo(String s) {
        String json= s.substring(1, s.length()-1);
        json= json.replaceAll(":","-");
        json= json.replace("\"", "");
        json= json.length()+"-"+json;
        return json;
    }
    public static String fijoDelmitador(String s) {
        String fijo = s.substring(2, s.length())+"/";
        return fijo;
    }

}
