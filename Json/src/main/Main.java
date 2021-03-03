/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import json.SendJSON;

/**
 *
 * @author Alfon
 */
public class Main {
    public static void main(String[] args) {
        SendJSON json = SendJSON.getInstance();
        json.setVisible(true);
    }
}
