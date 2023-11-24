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
public class Article extends ObjectDB{
    String nom;
    String reference;
    String id_categorie;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(String id_categorie) {
        this.id_categorie = id_categorie;
    }

    public Article() {
        this.setPrimaryKeyName();
    }

    public Article(String nom, String reference, String id_categorie) {
        super();
        this.setNom(nom);
        this.setReference(reference);
        this.setId_categorie(id_categorie);
    }
    
    public Article[] getObject(Vector<Object> list) {
        Article[] article = new Article[list.size()];
        for(int i=0; i<list.size(); i++) {
            article[i] = (Article)list.get(i);
        }
        return article;
    }
    
    public String getId_type(String id_article) throws Exception {
        Article article = (Article)selectw(this.getPrimaryKeyName(), id_article);
        Categorie categorie = new Categorie();
        return categorie.getType(article.getId_categorie());
    }
    
    public String getNomArticle(String id_article) throws Exception {
        Article article = (Article)selectw(this.getPrimaryKeyName(), id_article);
        return article.getNom();
    }
    
    public Article[] getList() {
        try {
            Vector<Object> list = this.find();
            Article[] articles = this.getObject(list);
            return articles;
        } catch (Exception ex) {
            throw  new Error("Aucun Article");
        }
    }

    public Article getAtricle (String id) throws Exception {
        return (Article)selectw(getPrimaryKeyName(), id);
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("ART00");
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
        this.setSequence("SeqArticle");
    } 
}
