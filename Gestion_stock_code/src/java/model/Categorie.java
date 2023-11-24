/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;
import util.reflect.ObjectDB;
/**
 *
 * @author itu
 */
public class Categorie extends ObjectDB{
    String nom;
    String code;
    String id_type;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public Categorie( String nom, String code, String id_type) {
        super();
        this.setNom(nom);
        this.setCode(code);
        this.setId_type(id_type);
    }

    public Categorie() {
        this.setPrimaryKeyName();
    }
    
    public Categorie[] getObject(Vector<Object> list) {
        Categorie[] categorie = new Categorie[list.size()];
        for(int i=0; i<list.size(); i++) {
            categorie[i] = (Categorie)list.get(i);
        }
        return categorie;
    }
    
    public Categorie[] getList () {
        try {
            Vector<Object> list_vector = this.find();
            Categorie[] list = this.getObject(list_vector);
            return list;
        } catch (Exception e) {
            throw new Error("Acun liste de categorie");
        }
    }
    
    public String getType(String id_categorie) throws Exception {
        Categorie categorie = (Categorie)this.selectw(this.getPrimaryKeyName(), id_categorie);
        System.out.println("CATEGORIE: "+categorie.getId_type());
        return categorie.getId_type();
    }
    
    public Categorie getCategorie(String id_categorie) throws Exception {
        Categorie categorie = (Categorie)this.selectw(this.getPrimaryKeyName(), id_categorie);
        return categorie;
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("CAT0");
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
        this.setSequence("SeqCategorie");
    }
}
