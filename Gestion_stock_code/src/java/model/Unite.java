/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Vector;
import util.reflect.ObjectDB;

/**
 *
 * @author itu
 */
public class Unite extends ObjectDB{
    String nom;
    String abreviation; 

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public Unite() {
        this.setPrimaryKeyName();
    }

    public Unite(String nom, String abreviation) {
        super();
        this.setNom(nom);
        this.setAbreviation(abreviation);
    }
    
    public Unite[] getObject(Vector<Object> list) {
        Unite[] article = new Unite[list.size()];
        for(int i=0; i<list.size(); i++) {
            article[i] = (Unite)list.get(i);
        }
        return article;
    }
    
    public Unite[] getList() throws Error{
        try {
            Vector<Object> list = this.find();
            return getObject(list);
        } catch (Exception e) {
            System.out.println(e);
            throw new Error("Aucun liste dans la base");
        }
        
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("UNT00");
    }

    @Override
    public void setLongPK() {
        this.setLongPK(7);
    }

    @Override
    public void setFonction() {
    }

    @Override
    public void setSequence() {
        this.setSequence("SeqUnite");
    }

    
}
