/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.administratif.model;

import org.universite.administratif.model.Etudiant;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author PAKI6340
 */
public class EtudiantTest {
    String etudStr;
    Etudiant etudiant=new Etudiant();

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        etudStr = "123;Durand;Alain";
        etudiant.setId(123).setNom("Durand").setPrenom("Alain");
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of fromString method, of class Etudiant.
     */
    @Test
    public void testFromString() throws Exception {
        System.out.println("fromString");
        Etudiant expResult = new Etudiant().setId(123).setNom("Durand").setPrenom("Alain");
        Etudiant result = Etudiant.fromString(etudStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

     /**
     * Test of fromString method, of class Etudiant 
     * teste l'inégalité de 2 étudiants différents.
     */
    @Test
    public void test2FromString() throws Exception {
        System.out.println("fromString");
        Etudiant expResult = new Etudiant().setId(584).setNom("Durand").setPrenom("Pierre");
        Etudiant result = Etudiant.fromString(etudStr);
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    } 
           /**
     * Test of fromString method, of class Etudiant 
     * teste qu'une Exception est levée lorsque l'id n'est pas un entier.
     */
    @Test
    public void test3FromString() throws Exception {
        System.out.println("fromString");
        String etudStr = "az45aze;Durand;Alain";
        try {
            etudiant= Etudiant.fromString(etudStr);
            fail("Devrait lever une exception car identifiant non entier");
        } catch (NumberFormatException ne){
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }   
    
    @Test
    public void test1ToString() {
        System.out.println("toString");
        assertEquals(etudStr, etudiant.toString());
     
    }
}
