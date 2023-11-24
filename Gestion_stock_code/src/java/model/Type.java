/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.reflect.ObjectDB;

/**
 *
 * @author itu
 */
public class Type extends ObjectDB{
    String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Type() {
        this.setPrimaryKeyName();
    }

    public Type(String nom) {
        super();
        this.setNom(nom);
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("TYPE0");
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
        this.setSequence("SeqType");
    }
}
