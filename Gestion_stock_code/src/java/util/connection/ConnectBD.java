/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.connection;

import java.sql.*;
import java.util.Vector;
/**
 *
 * @author itu
 */
public class ConnectBD {
    String DB_DRIVER = "org.postgresql.Driver";
    String DB_URL = "jdbc:postgresql://localhost:5432/gestion_stock_code";
    String DB_USERNAME;
    String DB_PASSWORD;

    public String getDB_USERNAME() {
        return DB_USERNAME;
    }

    public String getDB_PASSWORD() {
        return DB_PASSWORD;
    }

    public void setDB_USERNAME(String DB_USERNAME) {
        this.DB_USERNAME = DB_USERNAME;
    }

    public void setDB_PASSWORD(String DB_PASSWORD) {
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public ConnectBD() {
    }

    public ConnectBD(String DB_USERNAME, String DB_PASSWORD) {
        this.setDB_USERNAME(DB_USERNAME);
        this.setDB_PASSWORD(DB_PASSWORD);
    }

    public Connection getOnConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = (Connection) DriverManager.getConnection(DB_URL, this.getDB_USERNAME(),
                    this.getDB_PASSWORD());

        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
}
