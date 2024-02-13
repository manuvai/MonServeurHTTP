/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.administratif;

import org.universite.administratif.model.EtudiantFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PAKI6340
 */
public class MonServeurHTTP {
    
    private static final int HTTP_OK_STATUS = 200;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        //Creation du serveur http avec un maximum de 10 requêtes en attente sur la connexion
	HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(9090), 10);
            server.createContext("/bonjour", new BonjourHandler());

            //un executor par défaut (serveur s'execute dans le thread courant)
            //server.setExecutor(null);

            //un executor utilisant un cache (réutilisation du thread)
            server.setExecutor(Executors.newCachedThreadPool());

            server.start();

        } catch (IOException ex) {
            Logger.getLogger(MonServeurHTTP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
