/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.administratif;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.universite.administratif.model.Etudiant;
import org.universite.administratif.model.EtudiantFactory;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author akriks
 */
public class EtudiantsHandler implements HttpHandler, Const {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if ("GET".equals(method)) {
            handleGet(httpExchange);
        } else if ("POST".equals(method)) {
            handlePost(httpExchange);
        }
    }

    private void handlePost(HttpExchange httpExchange) throws IOException {

        Map<String, String> bodyParameters = fetchBodyParameters(httpExchange);

        String etudStr = fetchEtudiantString(bodyParameters);
        Etudiant nouvelEtudiant;
        try {
            nouvelEtudiant = Etudiant.fromString(etudStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        EtudiantFactory.getEtudiants().put(nouvelEtudiant.getId(), nouvelEtudiant);

        OutputStream os = httpExchange.getResponseBody();
        OutputStreamWriter osw = new OutputStreamWriter(os);

        String response = "";

        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        httpExchange.sendResponseHeaders(HTTP_OK_CREATED, response.getBytes(CHAR_SET).length);

        osw.write(response);
        osw.close();

    }

    private String fetchEtudiantString(Map<String, String> bodyParameters) {
        String etudStr = "";

        if (Objects.nonNull(bodyParameters)) {
            String id = bodyParameters.get("id");
            String nom = bodyParameters.get("nom");
            String prenom = bodyParameters.get("prenom");

            etudStr = String.join(";", Arrays.asList(
                id,
                nom,
                prenom
            ));
        }

        return etudStr;
    }

    private Map<String, String> fetchBodyParameters(HttpExchange httpExchange) throws IOException {
        Map<String, String> map = new HashMap<>();

        if (Objects.nonNull(httpExchange)) {
            InputStream is = httpExchange.getRequestBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuilder reqBody = new StringBuilder();
            while ((line = in.readLine()) != null) {
                reqBody.append(line);
            }

            List<String> params = List.of(reqBody.toString().split("&"));

            params.forEach(param -> {
                String[] parts = param.split("=");
                String key = parts[0];
                String value = parts[1];

                map.put(key, value);
            });

        }

        return map;
    }

    public void handleGet(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();

        Integer id = recupererIdentifiant(uri);
        Map<Integer, Etudiant> etudiants = EtudiantFactory.getEtudiants();

        List<Etudiant> etudiantsReponse = id != null
                ? Collections.singletonList(etudiants.get(id))
                : etudiants.values()
                        .stream()
                        .collect(Collectors.toList());

        List<String> etudiantsString = etudiantsReponse
                .stream()
                .filter(Objects::nonNull)
                .map(Etudiant::getRepresentation)
                .collect(Collectors.toList());

        if (etudiantsString.isEmpty()) {
            OutputStream os = httpExchange.getResponseBody();
            OutputStreamWriter osw = new OutputStreamWriter(os);

            String response = "NOT FOUND";

            httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            httpExchange.sendResponseHeaders(HTTP_KO_NOTFOUND, "NOT FOUND".getBytes(CHAR_SET).length);

            osw.write(response);
            osw.close();

        } else {
            OutputStream os = httpExchange.getResponseBody();
            OutputStreamWriter osw = new OutputStreamWriter(os);

            String response = String.join("<br/>", etudiantsString);

            httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            httpExchange.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes(CHAR_SET).length);

            osw.write(response);
            osw.close();

        }

    }

    private Integer recupererIdentifiant(URI uri) {
        String query = uri.getPath();
        String[] parts = query.split("/");

        String lastPart = parts.length == 0
                ? null
                : parts[parts.length - 1];

        Integer id;
        try {
            id = Integer.parseInt(lastPart);
        } catch (NumberFormatException e) {
            id = null;
        }

        return id;
    }

    public void handleOld(HttpExchange httpE) throws IOException {
        
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
