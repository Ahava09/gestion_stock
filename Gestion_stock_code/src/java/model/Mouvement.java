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
public class Mouvement extends ObjectDB{
    String id_sortie;
    String id_entree;
    double reste;
    double prix; //an'io reste io

    public String getId_sortie() {
        return id_sortie;
    }

    public void setId_sortie(String id_sortie) {
        this.id_sortie = id_sortie;
    }

    public String getId_entree() {
        return id_entree;
    }

    public void setId_entree(String id_entree) {
        this.id_entree = id_entree;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    

    public Mouvement() {
        this.setPrimaryKeyName();
    }

    public Mouvement(String id_sortie, String id_entree, double reste,double prix) {
        super();
        this.setId_entree(id_entree);
        this.setId_sortie(id_sortie);
        this.setReste(reste);
        this.setPrix(prix);
    }
    
    public Mouvement[] getObject(Vector<Object> list) {
        Mouvement[] mouvement = new Mouvement[list.size()];
        for(int i=0; i<list.size(); i++) {
            mouvement[i] = (Mouvement)list.get(i);
        }
        return mouvement;
    }
    
    public Mouvement getMouvementSortie (String id_sortie) throws Exception {
        Mouvement mv = (Mouvement)selectw("id_sortie", id_sortie);
        return mv;
    }
    
    public Mouvement[] getMvSortie (String id_sortie) throws Exception {
        Vector<Object> mv = select("id_sortie", id_sortie);
        return getObject(mv);
    }
    
//    raha roa ihany koa ny type 
    public int checkType (Sortie sortie) throws Exception {
        Article article = new Article();
        String id_type = article.getId_type(sortie.getId_article());
        System.out.println("ID TYPE: "+id_type);
        if(id_type.equalsIgnoreCase("TYPE01") == true) return 1;
        if(id_type.equalsIgnoreCase("TYPE02") == true) return 2;
        return 0;
    }
    
//    toute les liste des entrees raha mbola tsy misy mouvement no ampiasaina ity article an'ilay magasin iny tonga dia trie
    public Entree[] getListesEntree (Sortie sortie) throws Exception {
        int tf = checkType(sortie);
        Entree entree = new Entree();
        if(tf == 1) return entree.getEntreeFifo(sortie);
        if(tf == 2) return entree.getEntreeLifo(sortie);
        return null;
    }
    
//    les listes d'une sortie raha mbola tsy misy mouvement decomposition entree d'une sortie
    public Mouvement[] getEntree (Sortie sortie) throws Exception {
        Entree[] entree = getListesEntree(sortie);
        Vector<Object> mouvements = new Vector<Object>();
        Mouvement mv = new Mouvement();
        double qt = 0;
        for(int i =0; i<entree.length; i++) {
            qt = qt + entree[i].getQuantite();
            System.out.println("QT: "+qt);
            mv = new Mouvement();
            mv.setId_entree(entree[i].getPrimaryKey());
            mv.setId_sortie(sortie.getPrimaryKey());
            if(qt <= sortie.getQuantite()) {
                mv.setReste(0);
                mv.setPrix(0);
                System.out.println("QT: "+qt+" Sortie: "+sortie.getQuantite());
                mouvements.add(mv);
            }  else {
                mv.setReste(qt-sortie.getQuantite());
                mv.setPrix(mv.getReste()*entree[i].getPrix_unitaire());
                System.out.println("QT: "+qt+" Reste: "+mv.getReste());
                mouvements.add(mv);
                break;
            }     
        }
        return getObject(mouvements);
    }
     
    public void affichage (Mouvement[] list) {
        for (int i = 0; i < list.length; i++) {
            Mouvement elementAt = list[i];
            System.out.println(i+"- Entree"+elementAt.getId_entree()+" Reste"+elementAt.getReste());
        }
    }
//    
//    public Mouvement getMax (Vector<Mouvement> mouvements) {
//        if (mouvements.size() > 0) return mouvements.get(mouvements.size()-1);
//        return null;
//    }
//    
    public void insert (Mouvement[] list) throws Exception, Exception, Exception {
        for (int i = 0; i < list.length; i++) {
            list[i].save();
        }
    }
    
    
    public Vector<Object> getEntreeAB (Sortie sortie, Entree[] entree, Vector<Object> list,double qt) {
        Mouvement mv = new Mouvement();
        for(int i =0; i<entree.length; i++) {
            qt = qt + entree[i].getQuantite();
            if(qt <= sortie.getQuantite()) {
                mv.setId_entree(entree[i].getPrimaryKey());
                mv.setId_sortie(sortie.getPrimaryKey());
                mv.setReste(0);
//                mv.setPrix(entreeA.get(i).getQuantite()*entreeA.get(i).getPrix_unitaire());
                mv.setPrix(0);
                list.add(mv);
            }  else {
                mv.setId_entree(entree[i].getPrimaryKey());
                mv.setId_sortie(sortie.getPrimaryKey());
                mv.setReste(qt-sortie.getQuantite());
                mv.setPrix(mv.getReste()*entree[i].getPrix_unitaire());
//                    mv.setPrix(entree.get(i).getQuantite()*entree.get(i).getPrix_unitaire());
                list.add(mv);
                break;
            }     
        }
        return list;
    }
    public double getQT (Sortie sortie, Entree[] entree, Vector<Object> list) {
        Mouvement mv = new Mouvement();
        double qt = 0;
        for(int i =0; i<entree.length; i++) {
            qt = qt + entree[i].getQuantite();
            if(qt > sortie.getQuantite()) break; 
        }
        return qt;
    }
    
    public Mouvement[] getEntree (Sortie sortie, Entree[] entreeA, Entree[] entreeB, Mouvement m) throws Exception {
        Vector<Object> list = new Vector<Object>();
        Mouvement mv = new Mouvement();
        list = getEntreeAB(sortie, entreeA, list,0);
        double qt = getQT(sortie, entreeA, list);
        mv = getMouvement(sortie, m,qt);  
        list.add(mv);      
        qt = qt + m.getReste();
        list = getEntreeAB(sortie, entreeB, list, qt);
        return getObject(list);
    }
   
    public Mouvement[] getMouvementLifo (Sortie sortie,Mouvement mv) throws Exception {
        Entree entreeMin = new Entree();
        entreeMin = (Entree)entreeMin.select(entreeMin.getPrimaryKeyName(), mv.getId_entree()).get(0); //date min misy reste entree
//        System.out.println("ilay entree farany nampiasain'ilay sortie teo aloha: "+entreeMin.getDate_entree());
        Sortie s = sortie.getLastSortie(sortie.getId_article(), sortie.getId_magasin());
        System.out.println("ilay sortie farany : "+s.getDate_validation());
        Entree[] list = entreeMin.getEntreeLifo(sortie, entreeMin.getDate_entree()); // entre an'ilay date farany t@mv sy date farany t@entree
        System.out.println("A Size : "+list.length);
        Entree[] entreeB = entreeMin.getEntreeLifoB(sortie,entreeMin.getDate_entree());
        System.out.println("B Size : "+entreeB.length);
        Mouvement mouvement = new Mouvement();
        Mouvement[] mouvements = getEntree(sortie, list,entreeB,mv);
        return mouvements;
    }
    
    public Mouvement[] getEntree (Sortie sortie,double qt, Entree[] entree) throws Exception {
        Vector<Object> list = new Vector<Object>();
        Mouvement mv = new Mouvement();
        double q = 0;
        for(int i =0; i<entree.length; i++) {
            qt = qt + entree[i].getQuantite();        
            System.out.println("RESTE: "+qt +"--------------"+sortie.getQuantite());

            if(qt <= sortie.getQuantite()) {
                mv.setId_entree(entree[i].getPrimaryKey());
                mv.setId_sortie(sortie.getPrimaryKey());
                mv.setReste(0);
//                mv.setPrix(entree.get(i).getQuantite()*entree.get(i).getPrix_unitaire());
                mv.setPrix(0);
                list.add(mv);
                q = q + entree[i].getQuantite();
            }  else {
                if(qt-q <= entree[i].getQuantite()) {
                    mv.setId_entree(entree[i].getPrimaryKey());
                    mv.setId_sortie(sortie.getPrimaryKey());
                    mv.setReste(qt-sortie.getQuantite());
                    mv.setPrix(mv.getReste()*entree[i].getPrix_unitaire());
                    list.add(mv);
                }
                break;
            }     
        }
        return getObject(list);
    }
    
    public Mouvement getMouvement (Sortie sortie,Mouvement mv) throws Exception{
        double qt = mv.getReste();
        Mouvement mouvement = new Mouvement();
        Entree entree = new Entree();
        entree = (Entree)entree.select(entree.getPrimaryKeyName(), mv.getId_entree()).get(0);
        if(sortie.getQuantite()-qt >= 0) {
            mouvement.setId_sortie(sortie.getPrimaryKey());
            mouvement.setId_entree(entree.getPrimaryKey());
            mouvement.setReste(0);
            mouvement.setPrix(0);
        } else {
            mouvement.setId_sortie(sortie.getPrimaryKey());
            mouvement.setId_entree(entree.getPrimaryKey());
            mouvement.setReste(qt-sortie.getQuantite());
            mouvement.setPrix(mouvement.getReste()*entree.getPrix_unitaire());
        }
        return mouvement;
    }
    
    public Mouvement getMouvement (Sortie sortie,Mouvement mv,double qt) throws Exception{
        qt = qt + mv.getReste();
        Mouvement mouvement = new Mouvement();
        Entree entree = new Entree();
        entree = (Entree)entree.select(entree.getPrimaryKeyName(), mv.getId_entree()).get(0);
        if(sortie.getQuantite()-qt >= 0) {
            mouvement.setId_sortie(sortie.getPrimaryKey());
            mouvement.setId_entree(entree.getPrimaryKey());
            mouvement.setReste(0);
            mouvement.setPrix(0);
        } else {
            mouvement.setId_sortie(sortie.getPrimaryKey());
            mouvement.setId_entree(entree.getPrimaryKey());
            mouvement.setReste(qt-sortie.getQuantite());
            mouvement.setPrix(mouvement.getReste()*entree.getPrix_unitaire());
        }
        return mouvement;
    }
    
//    raha misy mouvement indray
    public Mouvement[] getMouvementFifo (Sortie sortie,Mouvement mv) throws Exception {
        Mouvement mouvement = new Mouvement();
        Vector<Object> mouvements = new Vector<Object>();
        double qt = mv.getReste();
        if ( qt > 0) {
            mouvements.add(getMouvement(sortie, mv));
        }
        Entree entree = new Entree();
        entree = (Entree)entree.select(entree.getPrimaryKeyName(), mv.getId_entree()).get(0);
        Entree[] list = entree.getEntreeFifo(sortie, entree.getDate_entree());
        Mouvement[] mvt = getEntree(sortie, qt, list);
        for (int i = 0; i < mvt.length; i++) {
            mouvements.add(mvt[i]);
        }
        return getObject(mouvements);
    }
    
// liste mouvement d'une sortie
    public Mouvement[] getList (String id_sortie) throws Exception {
        Vector<Object> list = select("id_sortie", id_sortie," order by reste desc");
        return getObject(list);
    }
    
//    Maka mouvement farany t@ilay sortie farany izay misy reste 0 na n
    public Mouvement getMouvement (Sortie sortie) throws Exception {
        Sortie s = sortie.getLastSortie(sortie.getId_article(), sortie.getId_magasin());
        if (getList(s.getPrimaryKey()).length>0) return getList(s.getPrimaryKey())[0];
        return null;
    }
    
//mijery hoe misy sortie ve talohan'io sortie ho traiter-na io     
    public boolean getBoolean(Sortie sortie) throws Exception {
        Sortie s = sortie.getLastSortie(sortie.getId_article(), sortie.getId_magasin());
        if (s != null) return true;
        // Retourne true si la liste des mouvements associés n'est pas vide
        return false;
    }
    
    public Mouvement[]  verification (Sortie sortie) throws Exception {
        boolean tf = getBoolean(sortie);
        System.out.println("TF----"+tf);
        Mouvement[] mouvements = null;
        if (tf == false) {
            System.out.println("TF----"+1);
            mouvements = getEntree(sortie);
        } else {
            int type = checkType(sortie);
            Mouvement mv = getMouvement(sortie);
            System.out.println("MV**********"+mv.getId_sortie());
            if(type == 1) mouvements= getMouvementFifo(sortie, mv); 
            if(type == 2) mouvements = getMouvementLifo(sortie, mv);
        }
        return mouvements;
    }
    
    
    
    
    @Override
    public void setPrimaryKeyName() {
        this.setPrimaryKeyName("id");
    }

    @Override
    public void setPrefix() {
        this.setPrefix("MVT0");
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
        this.setSequence("SeqMouvement");
    }
}
