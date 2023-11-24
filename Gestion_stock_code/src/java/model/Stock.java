/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import util.reflect.ObjectDB;

/**
 *
 * @author itu
 */
public class Stock extends ObjectDB{
    String id_article;
    String numero_compte;
    String nom_article;
    String id_magasin;
    String nom_magasin;
    String id_type;
    double quantite_entree;
    double quantite_sortie;
    double quantite_restante;
    double montant;

    public String getId_article() {
        return id_article;
    }

    public void setId_article(String id_article) {
        this.id_article = id_article;
    }

    public String getNumero_compte() {
        return numero_compte;
    }

    public void setNumero_compte(String numero_compte) {
        this.numero_compte = numero_compte;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public String getId_magasin() {
        return id_magasin;
    }

    public void setId_magasin(String id_magasin) {
        this.id_magasin = id_magasin;
    }

    public String getNom_magasin() {
        return nom_magasin;
    }

    public void setNom_magasin(String nom_magasin) {
        this.nom_magasin = nom_magasin;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public double getQuantite_entree() {
        return quantite_entree;
    }

    public void setQuantite_entree(double quantite_entree) {
        this.quantite_entree = quantite_entree;
    }

    public double getQuantite_sortie() {
        return quantite_sortie;
    }

    public void setQuantite_sortie(double quantite_sortie) {
        this.quantite_sortie = quantite_sortie;
    }

    public double getQuantite_restante() throws Error {
        if (quantite_restante <= 0) throw new Error("Le stock est insufissant");
        return quantite_restante;
    }
    
    public void setQuantite_restante(double quantite_restante) {
        this.quantite_restante = quantite_restante;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    

    public Stock() {
    }
    
//    public String request(Sortie sortie) {
//        String request = "SELECT "+
//                "a.id AS id_article, "+
//                "CONCAT(c.code, a.reference) AS numero_compte, "+
//                "a.nom AS nom_article, "+
//                "e.id_magasin as id_magasin, "+
//                "m.nom AS nom_magasin, "+
//                "c.id_type as id_type, "+
//                "COALESCE(SUM(DISTINCT e.quantite), 0) AS quantite_entree, "+
//                "COALESCE(SUM(DISTINCT s.quantite), 0) AS quantite_sortie, "+
//                "COALESCE(SUM(DISTINCT e.quantite), 0) - COALESCE(SUM(DISTINCT s.quantite), 0) AS quantite_restante, "+
//                "COALESCE(SUM(DISTINCT e.quantite * e.prix_unitaire), 0) AS montant "+
//            "FROM "+
//                "article a "+
//            "JOIN "+
//                "entree e ON a.id = e.id_article "+
//            "JOIN "+
//                "magasin m ON e.id_magasin = m.id "+
//            "JOIN "+
//                "categorie c ON c.id = a.id_categorie "+
//            "LEFT JOIN "+
//                "sortie s ON a.id = s.id_article AND e.id_magasin = s.id_magasin "+
//            "WHERE " +
//            " e.id_article = '"+sortie.getId_article()+"'and e.id_magasin='"+sortie.getId_magasin()+"' and e.date_entree <= '"+sortie.getDate_validation()+"' and s.etat = 10"+
//            "GROUP BY "+
//                "a.id, a.nom, e.id_magasin, m.nom, c.id "+
//            "ORDER BY "+
//               " a.id";
//        return request;
//    }
    public String request(Sortie sortie) {
        String request = "SELECT "+
                "a.id AS id_article, "+
                "CONCAT(c.code, a.reference) AS numero_compte, "+
                "a.nom AS nom_article, "+
                "e.id_magasin as id_magasin, "+
                "m.nom AS nom_magasin, "+
                "c.id_type as id_type, "+
                "COALESCE(SUM(DISTINCT e.quantite), 0) AS quantite_entree, "+
                "COALESCE(SUM(DISTINCT s.quantite), 0) AS quantite_sortie, "+
                "COALESCE(SUM(DISTINCT e.quantite), 0) - COALESCE(SUM(DISTINCT s.quantite), 0) AS quantite_restante, "+
                "COALESCE(SUM(DISTINCT e.quantite * e.prix_unitaire), 0) AS montant "+
            "FROM "+
                "article a "+
            "JOIN "+
                "entree e ON a.id = e.id_article "+
            "JOIN "+
                "magasin m ON e.id_magasin = m.id "+
            "JOIN "+
                "categorie c ON c.id = a.id_categorie "+
            "LEFT JOIN "+
                "sortie s ON a.id = s.id_article AND e.id_magasin = s.id_magasin "+
            "WHERE " +
            " e.id_article = '"+sortie.getId_article()+"'and e.id_magasin='"+sortie.getId_magasin()+"' and e.date_entree <= '"+sortie.getDate_validation()+"' and s.etat = 10"+
            "GROUP BY "+
                "a.id, a.nom, e.id_magasin, m.nom, c.id "+
            "ORDER BY "+
               " a.id";
        return request;
    }
    
    
    public String request(String id_article,String id_magasin,Date date) {
        String request = "SELECT "+
                "a.id AS id_article, "+
                "CONCAT(c.code, a.reference) AS numero_compte, "+
                "a.nom AS nom_article, "+
                "e.id_magasin as id_magasin, "+
                "m.nom AS nom_magasin, "+
                "c.id_type as id_type, "+
                "COALESCE(SUM(DISTINCT e.quantite), 0) AS quantite_entree, "+
                "COALESCE(SUM(DISTINCT s.quantite), 0) AS quantite_sortie, "+
                "COALESCE(SUM(DISTINCT e.quantite), 0) - COALESCE(SUM(DISTINCT s.quantite), 0) AS quantite_restante, "+
                "COALESCE(SUM(DISTINCT e.quantite * e.prix_unitaire), 0) AS montant "+
            "FROM "+
                "article a "+
            "JOIN "+
                "entree e ON a.id = e.id_article "+
            "JOIN "+
                "magasin m ON e.id_magasin = m.id "+
            "JOIN "+
                "categorie c ON c.id = a.id_categorie "+
            "LEFT JOIN "+
                "sortie s ON a.id = s.id_article AND e.id_magasin = s.id_magasin "+
            "WHERE " +
            " e.id_article = '"+id_article+"'and e.id_magasin='"+id_magasin+"' and e.date_entree <= '"+date+"' and s.etat =10"+ // tsy voatery etat 10 ko anefa fa date asc min -> max
            "GROUP BY "+
                "a.id, a.nom, e.id_magasin, m.nom, c.id "+
            "ORDER BY "+
               " a.id";
        return request;
    }
    
    
    
    public boolean check (Sortie sortie) throws Error, Exception {
        String[] colonne = {"id_article", "id_magasin"};
        String[] value = {sortie.getId_article(), sortie.getId_magasin()};
        String request = request(sortie);
        Stock stock = getStock(sortie);
        Entree e = new Entree();
        if(stock != null) {
            if (stock.getQuantite_restante() >= sortie.getQuantite()) return true;
            throw new Error("Stock insufisant");
        }
        Entree[] entree = e.getList(sortie.getId_magasin(), sortie.getId_article(), sortie.getDate_validation());
        if (entree != null) {
            if ( e.getQt(entree) >= sortie.getQuantite()) return true;
            throw new Error("Stock insufisant");
        } 
        return false;
    }
    
    public Stock getStock (Sortie sortie) throws Exception {
        String request = request(sortie);
        Sortie[] sorties = sortie.getList(sortie.getId_article(), sortie.getId_article(), sortie.getDate_validation());
        Vector<Object> list = this.select(request);
        if (list.size() > 0) {
            return (Stock)list.get(0);
        } 
        return null;
    }   
    
//    public double getInitiale (Etat_stock etat_stock, String id_article) throws Exception {
//        Vector<Object> list = this.select(request);
//        System.out.println("InI: "+etat_stock.getDateF()+" "+id_article+"------------>"+list.size());
//        if (list.size() > 0) {
//            Check_stock stock = (Check_stock)list.get(0);
//            return stock.getQuantite_restante();
//        } else {
//            return 0;
//        }  
//    }
    
//    public double getFinale (Etat_stock etat_stock, String id_article) throws Exception {
//        String request = request(id_article, etat_stock.getId_magasin(), etat_stock.getDateF());
//        Vector<Object> list = this.select(request);
//            System.out.println("FIna: "+etat_stock.getDateF()+" "+id_article+"====================>"+list.size());
//        if (list.size() > 0) {
//            Check_stock stock = (Check_stock)list.get(0);
//            return stock.getQuantite_restante();
//        } else {
//            return 0;
//        }  
//    }
//    
//    public Etat_stocks getEtat_stock (Etat_stock etat_stock,String id_article) throws Exception {
//        String request = request(id_article, etat_stock.getId_magasin(), etat_stock.getDateD());
//        Vector<Object> list = this.select(request);
//        double initiale = getInitiale(etat_stock, id_article);
//        double finale = getFinale(etat_stock, id_article);
//        Etat_stocks etat = new Etat_stocks();
//            System.out.println("SIZE: "+list.size());
//        if (list.size() > 0) {
//            Check_stock check = (Check_stock)list.get(0); 
//            etat.setNom_atricle(check.getNom_article());
//            etat.setNom_magasin(check.getNom_magasin());
//            etat.setDate_debut(etat_stock.getDateD());
//            etat.setDate_fin(etat_stock.getDateF());
//            etat.setQuantite_intiale(finale);
//            System.out.println("model.Check_stock.getEtat_stock()"+etat.getDate_debut());
//            return etat;
//        } else {
//            return etat;
//        }
//        
//        
//    }
    
    @Override
    public void setPrimaryKeyName() {
    }

    @Override
    public void setPrefix() {
    }

    @Override
    public void setLongPK() {
    }

    @Override
    public void setFonction() {
    }

    @Override
    public void setSequence() {
    }
    
  
}
