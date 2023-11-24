/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import util.reflect.ObjectDB;

/**
 *
 * @author itu
 */
public class Etat_stock {
    String id_produit;
    String id_magasin;
    Timestamp dateD;
    Timestamp dateF;

    public String getId_produit() {
        return id_produit;
    }

    public void setId_produit(String id_produit) {
        this.id_produit = id_produit;
    }

    
    public String getId_magasin() {
        return id_magasin;
    }

    public void setId_magasin(String id_magasin) {
        this.id_magasin = id_magasin;
    }

    public Timestamp getDateD() {
        return dateD;
    }

    public void setDateD(Timestamp dateD) {
        this.dateD = dateD;
    }
    public void setDateD(String date) throws Exception {
        // Définir le format de la chaîne de caractères de date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             // Analyser la chaîne de caractères en un objet Date
            java.util.Date utilDate = dateFormat.parse(date);
            // Convertir java.util.Date en java.sql.Date
            Timestamp d = new Timestamp(utilDate.getTime());
            this.setDateD(d);
        } catch (ParseException e) {
            e.printStackTrace(); // À adapter selon vos besoins
        }
    }
    
    public void setDateF(String date) throws Exception {
        // Définir le format de la chaîne de caractères de date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             // Analyser la chaîne de caractères en un objet Date
            java.util.Date utilDate = dateFormat.parse(date);
            // Convertir java.util.Date en java.sql.Date
            Timestamp d = new Timestamp(utilDate.getTime());
            this.setDateF(d);
        } catch (ParseException e) {
            e.printStackTrace(); // À adapter selon vos besoins
        }
    }
    public Timestamp getDateF() {
        return dateF;
    }

    public void setDateF(Timestamp dateF) {
        this.dateF = dateF;
    }

    public Etat_stock() {
    }

    public Etat_stock(String id_produit, String id_magasin, Timestamp dateD, Timestamp dateF) {
        this.setId_produit(id_produit);
        this.setId_magasin(id_magasin);
        this.setDateD(dateD);
        this.setDateF(dateF);
    }
    
    public Etat_stock[] getObject(Vector<Object> list) {
        Etat_stock[] entree = new Etat_stock[list.size()];
        for(int i=0; i<list.size(); i++) {
            entree[i] = (Etat_stock)list.get(i);
        }
        return entree;
    }
    
    public Article[] getArticle(String code) throws Exception {
        Article article = new Article();
        Vector<Object> articles = article.select("id_categorie", code); // id_produit
        return article.getObject(articles);
    }
    
}
