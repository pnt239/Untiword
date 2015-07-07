/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.main;

import Server.SEditServer;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Untitled25364
 */
public class ServerMain {
    /**
     * The main method that creates and launches the server
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args) {
        try {
            String portNum = "3219";
            
            if (portNum.equals("")) {
                JOptionPane
                .showMessageDialog(null,
                        "Server is not started. Please enter a port number.");
                System.out.println("Server initialization failure!");
                System.exit(0);
            } else {
                int portNumber = Integer.parseInt(portNum);
                SEditServer server = new SEditServer(portNumber);
                server.serve();
            }
        } catch (IOException e) {
            JOptionPane
            .showMessageDialog(null,
                    "Server is not started. Please try entering a different port number.");
            System.out.println("Server initialization failure!");
            System.exit(0);
        }

    }

    
}
