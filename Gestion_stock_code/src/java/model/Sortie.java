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
public class Sortie extends ObjectDB{
    String id_article;
    String id_magasin; 
    Timestamp date_insertion;
    double quantite;
    int etat = 0;
    Timestamp date_validation = new Timestamp(new java.util.Date().getTime());
    String id_unite;

    public String getId_article() {
        return id_article;
    }

    public void setId_article(String id_article) {
        this.id_article = id_article;
    }

    public String getId_magasin() {
        return id_magasin;
    }

    public void setId_magasin(String id_magasin) {
        this.id_magasin = id_magasin;
    }

    public Timestamp getDate_insertion() {
        return date_insertion;
    }

    public void setDate_insertion(Timestamp date_insertion){
        System.out.println("DATE: "+date_insertion);
        this.date_insertion = date_insertion;
    }

    public Timestamp getDate_validation() {
        return date_validation;
    }

    public String getId_unite() {
        return id_unite;
    }

    public void setId_unite(String id_unite) {
        this.id_unite = id_unite;
    }
    
    public void setId_unites(String id_unite) {
        UniteE unite = new UniteE();
        if (!unite.getTf(this.getId_article(), id_unite)) throw new Error ("Unite incompatible");
        this.setId_unite(id_unite);
    }
    
    public void setDate_Validation1(String date) throws ParseException, Exception,Error {
        // Formatez la date actuelle au format "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date times = dateFormat.parse(date);
        Timestamp timestamp = new Timestamp(times.getTime());
        Sortie sortie = getLastSortie(this.getId_article(), this.getId_magasin());
        if (sortie != null && sortie.getDate_validation().compareTo(this.getDate_validation()) <= 0){        
            System.out.println("D1: "+sortie.getDate_validation()+" D2: "+this.getDate_validation());
            throw  new Error("Verifier la date de validation");
        }
        this.setDate_validation(timestamp);
    }
    
    public void setDate_Insertion1(String date) throws ParseException, Exception,Error {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
             // Analyser la chaîne de caractères en un objet Date
            java.util.Date utilDate = dateFormat.parse(date);
            // Convertir java.util.Date en java.sql.Date
            Timestamp d = new Timestamp(utilDate.getTime());
            this.setDate_insertion(d);
        } catch (ParseException e) {
            // Gérer l'exception en cas d'échec de la conversion
            e.printStackTrace(); // À adapter selon vos besoins
        }
    }
    
    public void setDate_validation(Timestamp currentTimestamp) throws Exception {
        this.date_validation = currentTimestamp;
    }


    public void setDate(String date) throws Exception {
        // Définir le format de la chaîne de caractères de date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
             // Analyser la chaîne de caractères en un objet Date
            java.util.Date utilDate = dateFormat.parse(date);
            
            // Convertir java.util.Date en java.sql.Date
            Timestamp d = new Timestamp(utilDate.getTime());
            this.setDate_insertion(d);
        } catch (ParseException e) {
            // Gérer l'exception en cas d'échec de la conversion
            e.printStackTrace(); // À adapter selon vos besoins
        }
    }

    public double getQuantite() {
        return quantite;
    }
    
    public void setQuantite(double quantite) {
        if(quantite<0)  throw new Error("Votre quantite est negative");
        this.quantite = quantite;
    }

    public void setQuantites(String quantite) {
        try {
            double qt = Double.parseDouble(quantite);
            UniteE uniteE =  new UniteE();
            uniteE = uniteE.getUniteE(id_article, id_unite);
            System.out.println("/   //////"+uniteE.getQuantite());
            if (uniteE != null)  {
                double q = qt*uniteE.getQuantite();
                this.setQuantite(q);
            }
        }catch (NumberFormatException e) {
            throw new Error("Impossible de convertir votre quantite");
        }
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    
    
    public Sortie() {
        this.setPrimaryKeyName();
    }

    public Sortie(String id_article, String id_magasin, Timestamp date_insertion, double quantite,int etat,String id_unite) {
        super();
        this.setId_article(id_article);
        this.setId_magasin(id_magasin);
        this.setDate_insertion(date_insertion);
        this.setQuantite(quantite);
        this.setEtat(etat);
        this.setId_unite(id_unite);
    }
    
    public Sortie[] getObject(Vector<Object> list) {
        Sortie[] sortie = new Sortie[list.size()];
        for(int i=0; i<list.size(); i++) {
            sortie[i] = (Sortie)list.get(i);
            System.out.println("VALIDATION: "+sortie[i].getDate_validation());
        }
        return sortie;
    }
    
    public Sortie getSortie (String id_sortie) throws Exception {
        return (Sortie)selectw(this.getPrimaryKeyName(), id_sortie);
    }
    
    public Sortie getLast() throws Exception {
        Sortie sortie = (Sortie)selectw(" where etat = 10 order by date_validation desc");
        return sortie;
    }
    
    public Sortie getLastSortie(String id_article, String id_magasin) throws Exception {
        String[] col = {"id_article", "id_magasin"};
        String[] value = {id_article, id_magasin};
        Vector<Object> etat = select(col, value," and etat = 10 order by date_validation desc");
        if(etat.size() > 0) return (Sortie)etat.get(0);
        return null;
    }
    
    public Sortie[] getListSortieNonValide (String id_article, String id_magasin) throws Exception {
        String[] col = {"id_article", "id_magasin"};
        String[] value = {id_article, id_magasin};
        Vector<Object> etat = select(col, value," and etat = 0 order by date_validation asc");
        return getObject(etat);
    }
    
    public Sortie[] getListSortieNonValideAS (String nom, String value) throws Exception {
        Vector<Object> etat = select(nom, value," and etat = 0 order by date_validation asc");
        return getObject(etat);
    }
    
    public Sortie[] getListSortieNonValide () throws Exception {
        Vector<Object> etat = select(" select * from sortie where etat = 0 order by date_validation asc");
        return getObject(etat);
    }
    
    public Sortie getSortie(String id_article, String id_magasin,Timestamp date) throws Exception { //AVEC DATE
        String[] col = {"id_article", "id_magasin"};
        String[] value = {id_article, id_magasin};
        Vector<Object> etat = select(col, value," where date_validation= '"+date+"'  and etat = 10 order by date_validation desc");
        if(etat.size() > 0) return (Sortie)etat.get(0);
        return null;
    }
    
    public Sortie getLastSortieTwoDate (String id_article, String id_magasin, Timestamp date1, Timestamp date2) throws Exception {
        String[] col = {"id_article", "id_magasin"};
        String[] value = {id_article, id_magasin};
        Vector<Object> etat = select(col, value," and date_validation <='"+date1+"' and date_insertion>='"+date2+"' and etat = 10 order by date_validation desc");
        if(etat.size() > 0) return (Sortie)etat.get(0);
        return null;
    }
    
    public Sortie[] getList (String id_article, String id_magasin, Timestamp date) throws Exception {
        String[] col = {"id_article", "id_magasin"};
        String[] value = {id_article, id_magasin};
        Vector<Object> etat = select(col, value," and date_validation <='"+date+"' and etat = 10 order by date_validation desc");
        return getObject(etat);
    }
    
    public Sortie[] getListCloture (String id_article, String id_magasin, Timestamp date) throws Exception {
        String[] col = {"id_article", "id_magasin"};
        String[] value = {id_article, id_magasin};
        Vector<Object> etat = select(col, value," and date_validation <='"+date+"' and etat = 0 order by date_validation asc");
        return getObject(etat);
    }
    
    public void update () throws Exception {
//        this.setDate_validation10();
        Sortie[] list = getListCloture(this.getId_article(), this.getId_magasin(), this.getDate_insertion());
        String request = "update sortie set etat = 10 , date_validation ='"+this.getDate_validation()+"' where id = '"+this.getPrimaryKey()+"'";
        this.update(request);
    } 
    
    public Sortie inserer() {
        Sortie sortie = new Sortie();
        UniteE uniteE = new UniteE();
        uniteE = uniteE.getUniteE(this.getId_article(), this.getId_unite());
        double q = this.getQuantite()/uniteE.getQuantite();
        sortie.setQuantite(q);
        return sortie;
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("SRT0");
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
        this.setSequence("SeqSortie");
    }
    
}
