/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cliente.SendDelimitado;

/**
 *
 * @author Alfon
 */
public class Main {
    public static void main(String[] args) {
        SendDelimitado frame= SendDelimitado.getInstance();
        frame.setVisible(true);
    }
}
