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
public class UniteE extends ObjectDB{
    String id_article;
    String id_unite; 
    double quantite;
    

    public UniteE() {
        this.setPrimaryKeyName();
    }

    public UniteE(String id_article, String id_unite,double quantite) {
        super();
        this.setId_article(id_article);
        this.setId_unite(id_unite);
        this.setQuantite(quantite);
    }

    public String getId_article() {
        return id_article;
    }

    public void setId_article(String id_article) {
        this.id_article = id_article;
    }

    public String getId_unite() {
        return id_unite;
    }

    public void setId_unite(String id_unite) {
        this.id_unite = id_unite;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    
    public UniteE[] getObject(Vector<Object> list) {
        UniteE[] article = new UniteE[list.size()];
        for(int i=0; i<list.size(); i++) {
            article[i] = (UniteE)list.get(i);
        }
        return article;
    }
    
    public UniteE[] getList() throws Error{
        try {
            Vector<Object> list = this.find();
            return getObject(list);
        } catch (Exception e) {
            System.out.println(e);
            throw new Error("Aucun liste dans la base");
        }
    }
    
    public UniteE[] getList(String id_article) throws Error{
        try {
            Vector<Object> list = this.select("id_article", id_article);
            return getObject(list);
        } catch (Exception e) {
            System.out.println(e);
            throw new Error("---------------- unite invalide");
        }
    }
    
    public boolean getTf (String id_article, String id_unite) {
        UniteE[] unite = getList(id_article);
        if( unite != null ) {
            for (int i = 0; i < unite.length; i++) {
                if(unite[i].getId_unite().equalsIgnoreCase(id_unite)) return true;
            }
        }
        return false;
    }
    
    public UniteE getUniteE (String id_article, String id_unite) {
        UniteE[] unite = getList(id_article);
        if( unite != null ) {
            for (int i = 0; i < unite.length; i++) {
                System.out.println(unite[i].getId_unite()+"999999999999999"+id_unite);
                if(unite[i].getId_unite().equalsIgnoreCase(id_unite)) return unite[i];
            }
        }
        return null;
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("UNTE0");
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
        this.setSequence("SeqUniteE");
    }

    
}
