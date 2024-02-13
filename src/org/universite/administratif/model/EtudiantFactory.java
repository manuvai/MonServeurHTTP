/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.administratif.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PAKI6340
 */
public class EtudiantFactory {
    
    private static Map<Integer,Etudiant> etudiants;
        
    static {
        etudiants=new HashMap();
        Etudiant etud1 = new Etudiant();
        etud1.setId(123).setNom("Simon").setPrenom("Philippe");
        etudiants.put(etud1.getId(), etud1);
        Etudiant etud2 = new Etudiant();
        etud2.setId(456).setNom("Artugras").setPrenom("Julien");
        etudiants.put(etud2.getId(), etud2);
        Etudiant etud3 = new Etudiant();
        etud3.setId(789).setNom("Nicolut").setPrenom("Gérard");
        etudiants.put(etud3.getId(), etud3);
    }

    public static Map<Integer, Etudiant> getEtudiants() {
        return etudiants;
    }
    
    
}
