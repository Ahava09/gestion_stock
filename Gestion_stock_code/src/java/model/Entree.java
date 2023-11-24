/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import util.reflect.ObjectDB;

/**
 *
 * @author itu
 */
public class Entree extends ObjectDB{
    String id_article;
    String id_magasin;
    Timestamp date_entree;
    Timestamp date_expiration ;
    double quantite;
    double prix_unitaire;
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

    public Timestamp getDate_entree() {
        return date_entree;
    }

    public void setDate_entree(Timestamp date_entree) {
        this.date_entree = date_entree;
    }
    
    public void setDate_entreee(String date_entree) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
             // Analyser la chaîne de caractères en un objet Date
            java.util.Date utilDate = dateFormat.parse(date_entree);
            
            // Convertir java.util.Date en java.sql.Date
            Timestamp d = new Timestamp(utilDate.getTime());
            this.setDate_entree(d);
        } catch (ParseException e) {
            // Gérer l'exception en cas d'échec de la conversion
            e.printStackTrace(); // À adapter selon vos besoins
        }
    }

    public Timestamp getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(Timestamp date_expiration) {
        this.date_expiration = date_expiration;
    }
    
    public void setDate_expirationn(String date_entree) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
             // Analyser la chaîne de caractères en un objet Date
            java.util.Date utilDate = dateFormat.parse(date_entree);
            
            // Convertir java.util.Date en java.sql.Date
            Timestamp d = new Timestamp(utilDate.getTime());
            this.setDate_expiration(d);
        } catch (ParseException e) {
            // Gérer l'exception en cas d'échec de la conversion
            e.printStackTrace(); // À adapter selon vos besoins
        }
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    
    public void setQuantiteC(String quantite) {
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
            throw new Error(" Tsy misy");
        }
    }
    
    public void setPrix(String prix) {
        try {
            double qt = Double.parseDouble(prix);
            UniteE uniteE =  new UniteE();
            uniteE = uniteE.getUniteE(id_article, id_unite);
            double p = (qt/uniteE.getQuantite());
            this.setPrix_unitaire(p);
        } catch (NumberFormatException e) {
            throw  new Error("Impossible de convertir votre prix");
        }
    }
    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
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

    
    public Entree() {
        this.setPrimaryKeyName();
    }

    public Entree(String id_article, String id_magasin, Timestamp date_entree, Timestamp date_expiration, double quantite, double prix_unitaire, String id_unite) {
        super();
        this.setId_article(id_article);
        this.setId_magasin(id_magasin);
        this.setDate_entree(date_entree);
        this.setDate_expiration(date_expiration);
        this.setQuantite(quantite);
        this.setPrix_unitaire(prix_unitaire);
        this.setId_unite(id_unite);
    }
    
     public double getQuantite (Entree[] entree) {
        double qt = 0;
//        if (entree.length > 0){
            for(int i=0; i<entree.length; i++) {
                qt = qt + entree[i].getQuantite();
            }
//        }
        return qt;
    }
     
    public double getMontant (Entree[] entree) {
        double prix =0;
        if (entree != null){
            System.out.println("M-----"+prix);
            for(int i=0; i<entree.length; i++) {
                prix = entree[i].getPrix_unitaire() * entree[i].getQuantite();
            }
        }
        System.out.println("M++++++"+prix);
        return prix;
    }
    
    public Entree[] getObject(Vector<Object> list) {
        Entree[] entree = new Entree[list.size()];
        for(int i=0; i<list.size(); i++) {
            entree[i] = (Entree)list.get(i);
        }
        return entree;
    }
    
    public Entree[] getList(String id_magasin, String id_article,Date date) throws Exception {
        Vector<Object> list = select("id_article", id_article, " and id_magasin = '"+id_magasin+"' and date_entree <= '"+date+"'");
        return getObject(list);
    }
    
    public Entree getEntree(String id) throws Exception {
        return (Entree)selectw(this.getPrimaryKeyName(), id);
    }
//    Entree tsotra
    public Entree[] getEntreeFifo(Sortie sortie) throws Exception {
        Vector<Object> list = select("id_article", sortie.getId_article(), " and id_magasin = '"+sortie.getId_magasin()+"' and date_entree <= '"+sortie.getDate_validation()+"' order by date_entree asc");
        return getObject(list);
    } 
   
    public Entree[] getEntreeLifo(Sortie sortie) throws Exception {
        Vector<Object> list = select("id_article", sortie.getId_article(), " and id_magasin = '"+sortie.getId_magasin()+"'  and date_entree <= '"+sortie.getDate_validation()+"' order by date_entree desc");
        return getObject(list);
    }
// verification avec une autre date pour l'etat entre deux dates   
    public Entree[] getEntreeFifo(Sortie sortie, Timestamp date) throws Exception {
        Vector<Object> list = select("id_article", sortie.getId_article(), " and id_magasin = '"+sortie.getId_magasin()+"' and date_entree <= '"+sortie.getDate_validation()+"' and date_entree > '"+date+"' and id not in (select id_entree from mouvement)  order by date_entree asc");
        return getObject(list);
    } 

    public Entree[] getEntreeLifo(Sortie sortie, Timestamp date) throws Exception {
        Vector<Object> list = select("id_article", sortie.getId_article(), " and id_magasin = '"+sortie.getId_magasin()+"' and date_entree <= '"+sortie.getDate_validation()+"' and date_entree > '"+date+"' and id not in (select id_entree from mouvement) order by date_entree desc");
        return getObject(list);
    }
// entree B raha lany ny A pour LIfo 
    public Entree[] getEntreeLifoB(Sortie sortie, Timestamp date) throws Exception {
        Vector<Object> list = select("id_article", sortie.getId_article(), " and id_magasin = '"+sortie.getId_magasin()+"' and date_entree < '"+date+"' and id not in (select id_entree from mouvement) order by date_entree desc");
        return getObject(list);
    }
    
    public Entree[] getListEntree(Sortie sortie,Timestamp date) throws Exception {
        Vector<Object> list = select("id_article", sortie.getId_article(), " and id_magasin = '"+sortie.getId_magasin()+"' and date_entree <= '"+date+"' and id not in (select id_entree from mouvement m join sortie s on m.id_sortie = s.id where s.date_validation <='"+date+"') order by date_entree desc");
        return getObject(list);
    }
    
// Afaka atao requete kay ity    
    public double getQt(Entree[] entree) throws Exception {
        double q=0;
        for (int i=0; i<entree.length; i++) {
            q = entree[i].getQuantite();
        }
        return q;
    }
    
    public Entree inserer() {
        System.out.println("---------------------------->>>>>>>"+this.getId_unite());
        Entree entrees = new Entree();
        UniteE uniteE = new UniteE();
        uniteE = uniteE.getUniteE(this.getId_article(), this.getId_unite());
        System.out.println(this.getId_unite());
        double q = this.getQuantite()/uniteE.getQuantite();
        entrees.setQuantite(q);
        System.out.println();
        double p = this.getQuantite()*this.getPrix_unitaire();
        entrees.setPrix_unitaire(p);
        return entrees;
    }
    
    public void insert (Entree entree) throws Exception {
        entree.save();
    }
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("ENT00");
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
        this.setSequence("SeqEntree");
    }   
}
