/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author itu
 */
public abstract class Object {
    String primaryKeyName;
    String prefix;
    int longPK;
    String fonction; // to get the sequence values
    String sequence;
    String primaryKey;

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLongPK() {
        return longPK;
    }

    public String getFonction() {
        return fonction;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getSequence() {
        return sequence;
    }

    public abstract void setPrimaryKeyName();

    public abstract void setPrefix();

    public abstract void setLongPK();

    public abstract void setFonction();

    public abstract void setSequence();

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setLongPK(int longPK) {
        this.longPK = longPK;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public Connection getConnection() {
        ConnectBD connectionJDBC = new ConnectBD("postgres", "root");
        return connectionJDBC.getOnConnection();
    }

    public Statement createStatement() throws Exception {
        Connection connection = this.getConnection();
        Statement stm;
        stm = connection.createStatement();
        return stm;
    }

    // construction de pk
    public String constructPK() throws Exception {
        String request = "SELECT nextval('" + this.getSequence() + "')";
        int idNumber = this.executeGetInteger(request); /// verifier la syntaxe
        String primaryKey = this.getPrefix() + this.identification(idNumber);
        return primaryKey;
    }

    public String identification(int idNumber) {
        int n = this.getLongPK() - this.getPrefix().length() - String.valueOf(idNumber).length();
        String answer = "";
        for (int a = 0; a < n; a++) {
            answer += "0";
        }
        return answer + idNumber;
    }

    public Statement createStatement(Connection connection) {
        Statement stm ;
        try {
            stm = connection.createStatement();
            return stm;

        } catch (Exception e) {
            throw new Error("Connexion interrompu");
        }
        
    }
    
    public int executeGetInteger(String request) throws Exception {
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        Statement statement = this.createStatement(connection);
        int a = -1;
        try {
            ResultSet resultSet = statement.executeQuery(request);
            resultSet.next();
            a = resultSet.getInt(1);

        } catch (Exception e) {
            connection.rollback();
        } finally {
            this.commitOrClose(connection, statement, true);
            System.out.println("ok");
            return a;
        }
    }
    
    public void commitOrClose(Connection connection, Statement statement, boolean canCommit) throws Exception {
        try {
            if (connection != null && canCommit == true)
                connection.commit();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "    commit or/and close error");
        }
    }
    
    public Object() {
        this.setPrimaryKeyName();
        this.setPrefix();
        this.setFonction();
        this.setLongPK();
        this.setSequence();
    }
}
