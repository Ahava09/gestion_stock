/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.reflect.ObjectDB;

/**
 *
 * @author itu
 */
public class Magasin extends ObjectDB{
    String nom;
    String adresse;
    String contact;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Magasin() {
        this.getPrimaryKeyName();
    }

    public Magasin(String nom, String adresse, String contact) {
        super();
        this.setNom(nom);
        this.setAdresse(adresse);
        this.setContact(contact);
    }
    
    public Magasin[] getObject(Vector<Object> list) {
        Magasin[] magasin = new Magasin[list.size()];
        for(int i=0; i<list.size(); i++) {
            magasin[i] = (Magasin)list.get(i);
        }
        return magasin;
    }
    
    public Magasin[] getList() {
        Vector<Object> list = new Vector<Object>();
        try {
            list = this.find();
            Magasin[] magasin = this.getObject(list);
            return magasin;
        } catch (Exception ex) {
            throw  new Error("Aucun Magasin");
        }
    }
    
    public Magasin getMagasin (String id) throws Exception {
        return (Magasin)selectw(getPrimaryKeyName(), id);
    }
    
    public String getNomMagasin (String id) throws Exception {
        return getMagasin(id).getNom();
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("MAG00");
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
        this.setSequence("SeqMagasin");
    }
    
}
