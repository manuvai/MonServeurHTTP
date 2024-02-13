/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.administratif.model;

import java.util.Objects;
import java.util.StringTokenizer;

/**
 *
 * @author PAKI6340
 */
public class Etudiant {
    int id;
    String nom;
    String prenom;

    public int getId() {
        return id;
    }

    public Etudiant setId(int etudiantId) {
        this.id = etudiantId;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public Etudiant setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getPrenom() {
        return prenom;
    }

    public Etudiant setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }
    
    public static Etudiant fromString(String etudStr) throws Exception {
        Etudiant etudiant=new Etudiant();
        StringTokenizer st=new StringTokenizer(etudStr,";");
        if  ((null==st)  || (st.countTokens()!=3)) {
            throw new Exception("Nombre d'elements incorrects");
        }
        try {
            etudiant.setId(Integer.parseInt(st.nextToken()));
            etudiant.setNom(st.nextToken());
            etudiant.setPrenom(st.nextToken());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Mauvais format : identifiant non entier");
        }
    return etudiant;
    }

    public String toString() {
        String etudStr= this.getId() + ";" + this.getNom() + ";" + this.getPrenom();
        return etudStr;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etudiant other = (Etudiant) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        return true;
    }
    
}
