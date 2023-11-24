/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.reflect;

/**
 *
 * @author itu
 */
import java.util.Date;

public class GenericUpdate extends ObjectDB{
    String tableName;
    String action;
    Date dateGU;
    String valeur;

    public String getTableName() {return tableName;}
    public String getAction() {return action;}
    public Date getDateGU() {return dateGU;}
    public String getValeur() {return valeur;}
    public void setTableName(String table) {this.tableName = table;}
    public void setAction(String action) {this.action = action;}
    public void setDateGU(Date date) {this.dateGU = date;}
    public void setValeur(String valeur) {this.valeur = valeur;}

    public GenericUpdate() throws Exception{
        super();
    }

    public GenericUpdate(String tableName, String action, String valeur) throws Exception {
        super();
        this.setTableName(tableName);
        this.setAction(action);
        this.setDateGU(new Date());
        this.setValeur(valeur);
        // les fonctions abstraites
        
    }

    @Override
    public void setPrimaryKeyName(){
        this.setPrimaryKeyName("idGU");
    }
    @Override
    public void setPrefix() {this.setPrefix("GU");}
    @Override
    public void setSequence() {this.setSequence("getIdGU");}
    @Override
    public void setLongPK() {this.setLongPK(7);}

    @Override
    public void setFonction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
 

