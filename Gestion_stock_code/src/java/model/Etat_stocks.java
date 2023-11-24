/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

/**
 *
 * @author itu
 */
public class Etat_stocks {
    String nom_atricle;
    String nom_magasin;
    Timestamp date_debut;
    Timestamp date_fin;
    double quantite_intiale;
    double quantite_finale;
    double prix_unitaire;
    double montant;

    public String getNom_atricle() {
        return nom_atricle;
    }

    public void setNom_atricle(String nom_atricle) {
        this.nom_atricle = nom_atricle;
    }

    public String getNom_magasin() {
        return nom_magasin;
    }

    public void setNom_magasin(String nom_magasin) {
        this.nom_magasin = nom_magasin;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }

    public double getQuantite_intiale() {
        return quantite_intiale;
    }

    public void setQuantite_intiale(double quantite_intiale) {
        this.quantite_intiale = quantite_intiale;
    }

    public double getQuantite_finale() {
        return quantite_finale;
    }

    public void setQuantite_finale(double quantite_finale) {
        this.quantite_finale = quantite_finale;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Etat_stocks(String nom_atricle, String nom_magasin, Timestamp date_debut, Timestamp date_fin, double quantite_intiale, double quantite_finale, double prix_unitaire, double montant) {
        this.setNom_atricle(nom_atricle);
        this.setDate_debut(date_debut);
        this.setNom_magasin(nom_magasin);
        this.setDate_fin(date_fin);
        this.setQuantite_intiale(quantite_intiale);
        this.setQuantite_finale(quantite_finale);
        this.setPrix_unitaire(prix_unitaire);
        this.setMontant(montant);
    }

    public Etat_stocks() {
    }
    
    public Etat_stocks[] getObject(Vector<Object> list) {
        Etat_stocks[] entree = new Etat_stocks[list.size()];
        for(int i=0; i<list.size(); i++) {
            entree[i] = (Etat_stocks)list.get(i);
        }
        return entree;
    }
    
    public double getTotal(Entree[] entree, Sortie[] sortie) {
        double e = 0;
        double s = 0;
        for(int i=0; i<entree.length; i++) {
            e = e+ entree[i].getQuantite();
        }
        for(int k=0; k<sortie.length; k++) {
            s = s+ sortie[k].getQuantite();
        }
        return e-s;
    }
    
    public double getTotal(Entree[] entree) {
        double e = 0;
        for(int i=0; i<entree.length; i++) {
            e = e+ entree[i].getQuantite();
        }
        return e;
    }
    
    public double getQ(Entree[] entree, Sortie[] sortie) {
        if(entree == null) return 0;
        return getTotal(entree,sortie);
    }
    
    public Etat_stocks[] getEtat (Etat_stock etat) throws Exception {
        Etat_stocks e = new Etat_stocks();
        Etat_stocks etat_stocks = new Etat_stocks();
        Article[] articles = etat.getArticle(etat.getId_produit());
        Vector<Object> etats_stocks = new Vector<Object>();
        Entree[] entreeF = null;
        Sortie[] sortieF = null;
        Entree[] entreeD = null;
        Sortie[] sortieD = null;
        Entree ee = new Entree();
        Sortie ss = new Sortie();
        Magasin mag = new Magasin();
        Mouvement mv = new Mouvement();
        double quantite = 0;
        double prix = 0;
        System.out.println("ARTICLE----------------------------------"+articles.length);
        for (int i=0; i<articles.length; i++) {
            entreeF = ee.getList(etat.getId_magasin(), articles[i].getPrimaryKey(), etat.getDateF());
            System.out.println("entreeF----------------------------------"+etat.getDateF());
            sortieF = ss.getList( articles[i].getPrimaryKey(),etat.getId_magasin(), etat.getDateF());
            entreeD = ee.getList(etat.getId_magasin(), articles[i].getPrimaryKey(), etat.getDateD()); 
            sortieD = ss.getList( articles[i].getPrimaryKey(),etat.getId_magasin(), etat.getDateD());
            System.out.println("entreeD----------------------------------"+etat.getDateD());
            e.setNom_atricle(articles[i].getNom());
            e.setQuantite_intiale(getQ(entreeD, sortieD));
            e.setQuantite_finale(getQ(entreeF, sortieF));
            e.setNom_magasin(mag.getMagasin(etat.getId_magasin()).getNom());
            e.setDate_debut(etat.getDateD());
            e.setDate_fin(etat.getDateF());
            if(sortieF.length>0 ){
                mv = mv.getMouvementSortie(sortieF[0].getPrimaryKey());
                entreeF = ee.getListEntree(sortieF[0],etat.getDateF());
                prix = ee.getMontant(entreeF);
                prix = prix + mv.getPrix();
                System.out.println(prix+"----------------------------------------------------------"+e.getQuantite_finale());
            } else {
                prix = ee.getMontant(ee.getList(etat.getId_magasin(), articles[i].getPrimaryKey(), etat.getDateF()));
            }
            System.out.println(ee.getMontant(entreeF)+"-----------------------Prix "+prix+"---------------------"+e.getQuantite_intiale());
            if(prix == 0 && e.getQuantite_finale() == 0){
                e.setPrix_unitaire(0);   
            } else {
                e.setPrix_unitaire((prix/e.getQuantite_finale())); 
            }
            e.setMontant(prix);
            etats_stocks.add(e);
            e = new Etat_stocks();
        }
        return getObject(etats_stocks);
    }
}
