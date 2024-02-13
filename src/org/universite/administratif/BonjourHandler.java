/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.administratif;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akriks
 */
public class BonjourHandler implements HttpHandler,Const {
     
     

    @Override
    public void handle(HttpExchange httpE) throws IOException {
        
        System.out.println("Receive a call :"+httpE.getRequestURI());

        String response="";
        OutputStream os = httpE.getResponseBody();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        
        //Sur la ressource Bonjour, on ne veut autoriser que GET ou POST
        String method = httpE.getRequestMethod().toUpperCase();
        if (method.equals("GET")||method.equals("POST")) {
        } else {
            response="Ressource manipulable uniquement via GET et POST";
            httpE.sendResponseHeaders(HTTP_KO_NOTALLOWED,
                    response.getBytes(CHAR_SET).length);
            osw.write(response);
            osw.close();
            throw new IOException();
        }

        // On récupère les informations transmises dans les en-têtes de la requete
        StringBuilder reqHeaders=new StringBuilder("<H1>Les entêtes de la requête</H1>");
        //for (Map.Entry<String, List<String>> entry : httpE.getRequestHeaders().entrySet())
        httpE.getRequestHeaders().entrySet().forEach((entry) -> {
            reqHeaders.append(entry.getKey()).append("=").append(entry.getValue()).append("<br>");
        });
 

        // On récupère les informations transmises dans l'uri de la requête : path + queryParams
        StringBuilder uri=new StringBuilder("<H1>Les infos dans l'URI</H1>");
        uri.append("uri=").append(httpE.getRequestURI().toString()).append("<br>");
        uri.append("path=").append(httpE.getRequestURI().getPath()).append("<br>");
   
        try {
            String[] params = httpE.getRequestURI().getQuery().split("&");
            uri.append("Les paramètres de type query: <br>");
            for (String param : params) {
                uri.append(param).append("<br>");
            } 
        } catch (NullPointerException npe) {
            uri.append("Pas de paramètres de type query");
        }
        // On récupère les informations transmises dans le corps de la requête
        String line;
        InputStream is = httpE.getRequestBody();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder reqBody = new StringBuilder();
        while ((line = in.readLine()) != null) {
            reqBody.append(line);
        }

        response = "<HTML><H1>Bonjour</H1> <br> vous avez invoqué une méthode :  "+method+"<br>";
        response=response+reqBody.toString()+reqHeaders.toString()+uri.toString();
        response=response+"</HTML>";
        httpE.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        httpE.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes(CHAR_SET).length);

        osw.write(response);
        osw.close();
    }



}
