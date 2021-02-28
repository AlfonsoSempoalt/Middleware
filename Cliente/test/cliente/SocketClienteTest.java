/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alfon
 */
public class SocketClienteTest {
    
    public SocketClienteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of conectar method, of class SocketCliente.
     */
    @Test
    public void testConectar() {
        System.out.println("conectar");
        SocketCliente instance = new SocketCliente();
        instance.conectar();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendString method, of class SocketCliente.
     */
    @Test
    public void testSendString() throws Exception {
        System.out.println("sendString");
        String values = "";
        SocketCliente instance = new SocketCliente();
        instance.sendString(values);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
