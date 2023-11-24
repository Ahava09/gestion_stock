package util.reflect;

/**
 *
 * @author itu
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Vector;
import util.connection.ConnectBD;

public abstract class ObjectDB {
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

    // Supposons qu`il a des attributs
    // des fonctions GET et SET en norme generale
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

    public Statement createStatement(Connection connection) throws Exception {
        Statement stm;
        stm = connection.createStatement();
        return stm;
    }

    public Vector<Object> find(Connection connection) throws Exception {
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Vector<Object> answer = new Vector<>();
        try {
            result = statement.executeQuery(this.requestFind());
            while (result.next()) {
                String[] data = new String[classe.getDeclaredFields().length];
                for (int a = 1; a <= data.length; a++) {
                    data[a - 1] = result.getString(a);
                }
                answer.add(reflect.createObject(data));
            }
        } catch (Exception e) {
            throw new Exception( " Erreur lors de la recuperation de donnes --->>> " + e.getMessage() );
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }

    public Vector<Object> find() throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Vector<Object> answer = new Vector<>();
        try {
            result = statement.executeQuery(this.requestFind());
            System.out.println(this.requestFind());
            System.out.println(this.tableName());
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {
                    data[a - 1] = result.getString(a);
                }
                answer.add(reflect.createObject(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }

    public Vector<Object> select(String name, String sexe) throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Vector<Object> answer = new Vector<>();
        String request;
        request = "SELECT *  FROM " + this.tableName() + " WHERE " + name + " ='" + sexe + "'";
        System.out.println(request);
        try {
            result = statement.executeQuery(request);
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {

                    data[a - 1] = result.getString(a);
                    System.out.println(data[a - 1]);
                }
                answer.add(reflect.createObject(data));
            }
        } catch (Exception e) {
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }
    public Vector<Object> select(String name, String sexe,String suite) throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Vector<Object> answer = new Vector<>();
        String request;
        request = "SELECT *  FROM " + this.tableName() + " WHERE " + name + " ='" + sexe + "' "+suite;
        System.out.println(request);
        try {
            result = statement.executeQuery(request);
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {

                    data[a - 1] = result.getString(a);
                    System.out.println(data[a - 1]);
                }
                answer.add(reflect.createObject(data));
            }
        } catch (Exception e) {
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }
    
    public Vector<Object> select(String request) throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Vector<Object> answer = new Vector<>();
        System.out.println(request);
        try {
            result = statement.executeQuery(request);
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {

                    data[a - 1] = result.getString(a);
                    System.out.println(data[a - 1]);
                }
                answer.add(reflect.createObject(data));
            }
        } catch (Exception e) {
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }
    public Object selectw(String name, String n) throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Object answer = this.getClass().getConstructor().newInstance();
        String request;
        request = "SELECT *  FROM " + classe.getSimpleName() + " WHERE " + name + "='" + n + "'";
        System.out.println("----->" + request);
        try {
            result = statement.executeQuery(request);
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {

                    data[a - 1] = result.getString(a);
                    System.out.println(data[a - 1]);
                }
                answer = reflect.createObject(data);
            }
        } catch (Exception e) {
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }
    public Object selectw(String suite) throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Object answer = this.getClass().getConstructor().newInstance();
        String request;
        request = "SELECT *  FROM " +classe.getSimpleName()+ " " + suite;
        System.out.println("----->" + request);
        try {
            result = statement.executeQuery(request);
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {

                    data[a - 1] = result.getString(a);
                    System.out.println(data[a - 1]);
                }
                answer = reflect.createObject(data);
            }
        } catch (Exception e) {
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }

    public Vector<Object> selectS(String[] string, Object[] sexe) throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Vector<Object> answer = new Vector<>();
        String request;
        request = "SELECT *  FROM " + this.tableName() + " WHERE " + string[0] + "='" + (String) sexe[0] + "'and "
                + string[1] + "= '" + (String) sexe[1] + "'";
        System.out.println(request);
        try {
            result = statement.executeQuery(request);
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {

                    data[a - 1] = result.getString(a);
                    System.out.println(data[a - 1]);
                }
                answer.add(reflect.createObject(data));
            }
        } catch (Exception e) {
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }
    public Vector<Object> select(String[] string, Object[] sexe,String suite) throws Exception {
        Connection connection = this.getConnection();
        Class<?> classe = this.getClass();
        ResultSet result;
        Statement statement = createStatement(connection);
        Reflection reflect = new Reflection(classe);
        Vector<Object> answer = new Vector<>();
        String request;
        request = "SELECT *  FROM " + this.tableName() + " WHERE " + string[0] + "='" + (String) sexe[0] + "'and "
                + string[1] + "= '" + (String) sexe[1] + "' "+ suite;
        System.out.println(request);
        try {
            result = statement.executeQuery(request);
            int length = classe.getDeclaredFields().length;
            if (this.getPrimaryKeyName() != null)
                length += 1;
            while (result.next()) {
                String[] data = new String[length];
                for (int a = 1; a <= data.length; a++) {

                    data[a - 1] = result.getString(a);
                    System.out.println(data[a - 1]);
                }
                answer.add(reflect.createObject(data));
            }
        } catch (Exception e) {
        } finally {
            this.commitOrClose(connection, statement, false);
        }
        return answer;
    }

    public void save() throws Exception {
        if(this.getPrimaryKeyName() != null){
            this.setPrimaryKey("");
            this.setPrimaryKey(this.constructPK());
        }
        
        this.executeError();
        this.executeOneRequest(this.requestInsert());
        System.out.println(this.requestInsert());
        System.out.print(this.requestInsert());
    }

    public void update(int[] columnNumber, int[] where, String[] whereValue) throws Exception {
        this.executeError();
        this.executeOneRequest(this.requestUpdate(columnNumber, where, whereValue));
    }

    public void update(int[] columnNumber, String[] values, int[] where, String[] whereValue) throws Exception {
        this.executeError();
        this.executeOneRequest(this.requestUpdate(columnNumber, values, where, whereValue));
        System.out.println(this.requestUpdate(columnNumber, values, where, whereValue));
    }

    public void update() throws Exception {
        GenericUpdate genericUpdate = new GenericUpdate(this.tableName(), "Update", this.historicValue());
        genericUpdate.setPrimaryKey(genericUpdate.constructPK());
        genericUpdate.save();
        this.executeError();
        this.executeOneRequest(this.requestUpdate());
    }

    public void delete() throws Exception {
        GenericUpdate genericUpdate = new GenericUpdate(this.tableName(), "DELETE", this.historicValue());
        genericUpdate.setPrimaryKey(genericUpdate.constructPK());
        genericUpdate.save();
        this.executeError();
        this.executeOneRequest(this.requestDelete());
    }

    private void executeError() throws Exception {
        if (this.getPrimaryKeyName() == null || this.getPrimaryKey() == null) {
            if (this.getPrimaryKeyName() != null)
                throw new Exception("What is the primary key");
        }
    }

    public void executeOneRequest(String request) throws Exception {
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        Statement statement = this.createStatement(connection);

        try {
            System.out.println(request);
            statement.executeUpdate(request);
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            this.commitOrClose(connection, statement, true);
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

    public void transaction(String[] requests) throws Exception {
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        Statement statement = this.createStatement(connection);
        try {
            for (int a = 0; a < requests.length; a++) {
                statement.execute(requests[a]);
            }
        } catch (Exception e) {
            connection.rollback();
        } finally {
            this.commitOrClose(connection, statement, true);
        }
    }

    public String tableName() {
        String table = this.getClass().getName();
        return table.substring(table.lastIndexOf(".") + 1);
    }

    public String requestFind() {
        return "SELECT * FROM " + this.tableName();
        
    }

    public String[] allValues() throws Exception { // prend en compte si il ya ou pas de clé primaire
        Field[] fields = this.getClass().getDeclaredFields(); // dans la classe qui hérites
        int length = fields.length;
        if (this.getPrimaryKeyName() != null)
            length += 1;
        String[] answer = new String[length];
        Vector<Method> allGet = this.allGet();
        String temp;
        int marge = 0;
        for (int a = 0; a < fields.length; a++) {
            if (fields[a].getType() == Date.class)
                temp = ObjectDB.formaterDate((Date) allGet.get(a).invoke(this, null));
            else
                temp = String.valueOf(allGet.get(a).invoke(this, null));
            if (this.getPrimaryKeyName() != null)
                marge = a + 1;
            if (this.getPrimaryKeyName() != null && a == 0)
                answer[0] = "'" + String.valueOf(this.getPrimaryKey()) + "'";
            if (this.isNumberType(fields[a].getType()) == true)
                answer[marge] = temp; // nombres
            else
                System.out.println(fields[a].getName()+"NAME"+temp);
                answer[marge] = "'" + temp + "'"; // non nombres
        }
        return answer;
    }

    public String valuesInsert() throws Exception {
        String answer = "";
        String[] values = this.allValues();
         System.out.println(values[0]+"NAME"+values[1]);
        for (int a = 0; a < values.length; a++) {
            if (a == 0)
                answer += values[a]; // cle primaire en lettres
            else
                answer += "," + values[a];
        }
        return answer;
    }

    public String requestInsert() throws Exception {
        String answer = "INSERT INTO " + this.tableName() + " VALUES (";
        answer += this.valuesInsert();
        answer += ")";
        return answer;
    }

    public String valuesUpdates(int[] columnNumber) throws Exception { // you can specify the column here
        String answer = "";
        Field[] fields = this.getClass().getDeclaredFields();
        int indexField = 0;
        String[] values = this.allValues();
        for (int a = 0; a < values.length; a++) {
            if (this.getPrimaryKeyName() != null && a == 0)
                a++; // en n` enregistre plus la cle Primaire
            if (this.isInTable(indexField, columnNumber) == true || columnNumber == null) {
                if (answer.length() == 0)
                    answer += fields[indexField].getName() + "=" + values[a];
                else
                    answer += "," + fields[indexField].getName() + "=" + values[a];
            }
            indexField++;
        }
        return answer;
    }

    public String valuesUpdates(int[] columnNumber, String[] values) throws Exception { // you can specify the column
                                                                                        // here
        String answer = "";
        Field[] fields = this.getClass().getDeclaredFields();
        int indexField = 0;
        for (int a = 0; a < values.length; a++) {
            if (this.getPrimaryKeyName() != null && a == 0)
                a++; // en n` enregistre plus la cle Primaire
            if (this.isInTable(indexField, columnNumber) == true || columnNumber == null) {
                if (answer.length() == 0)
                    answer += fields[indexField].getName() + "=" + values[a];
                else
                    answer += "," + fields[indexField].getName() + "=" + values[a];
            }
            indexField++;
        }
        return answer;
    }

    public String where(int[] where, String[] whereValues) throws Exception { // where < 0 signifie cle Primaire
        String answer = "";
        Field[] fields = this.getClass().getDeclaredFields();
        String temp;
        if (where == null || whereValues == null)
            return this.getPrimaryKeyName() + "=" + this.getPrimaryKey();
        for (int a = 0; a < where.length; a++) {
            if (where[a] > 0 && this.isNumberType(fields[where[a]].getType()) == false)
                whereValues[a] = "'" + whereValues[a] + "'";
            if (where[a] < 0)
                temp = this.getPrimaryKeyName() + "=" + whereValues[a]; // clé primaire
            else
                temp = fields[where[a]].getName() + "=" + whereValues[a];
            if (answer.length() == 0)
                answer += temp;
            else
                answer += " AND " + temp;
        }
        return answer;
    }

    public String requestUpdate(int[] columnNumber, int[] where, String[] whereValues) throws Exception {
        String answer = "UPDATE " + this.tableName() + " SET ";
        answer += this.valuesUpdates(columnNumber);
        answer += " WHERE " + this.where(where, whereValues);
        return answer;
    }
    
    public void update (String request) throws Exception {
        this.executeError();
        this.executeOneRequest(request);
        System.out.println(request);
    }

    public String requestUpdate(int[] columnNumber, String[] values, int[] where, String[] whereValues)
            throws Exception {
        String answer = "UPDATE " + this.tableName() + " SET ";
        answer += this.valuesUpdates(columnNumber, values);
        answer += " WHERE " + this.where(where, whereValues);
        return answer;
    }

    public String requestUpdate() throws Exception {
        String answer = "UPDATE " + this.tableName() + " SET ";
        answer += this.valuesUpdates(null);
        answer += " WHERE " + this.getPrimaryKeyName() + "='" + this.getPrimaryKey() + "'";
        return answer;
    }

    public String requestUpdate(int[] number, String[] values) throws Exception {
        String answer = "UPDATE " + this.tableName() + " SET ";
        answer += this.valuesUpdates(number, values);
        answer += " WHERE " + this.getPrimaryKeyName() + "='" + this.getPrimaryKey() + "'";
        return answer;
    }

    public String requestDelete() throws Exception {
        String answer = "DELETE FROM " + this.tableName();
        answer += " WHERE " + this.getPrimaryKeyName() + "='" + this.getPrimaryKey() + "'";
        return answer;
    }

    public Vector<Method> allGet() { // de haut en bas (les attributs)
        Method[] methods = this.getClass().getDeclaredMethods();
        Field[] fields = this.getClass().getDeclaredFields();
        Vector<Method> allGet = new Vector<>();
        for (int a = 0; a < fields.length; a++) { // de haut en bas
            for (int b = 0; b < methods.length; b++) { // en desordre
                if (methods[b].getName().equalsIgnoreCase("get" + fields[a].getName())) {
                    allGet.add(methods[b]);
                    break;
                }
            }
        }
        return allGet;
    }

    public boolean isNumberType(Type type) {
        if (type == int.class || type == double.class || type == float.class)
            return true;
        if (type == Integer.class || type == Double.class || type == Float.class || type == Number.class)
            return true;
        return false;
    }

    public static String formaterDate(Date date) {
        DateFormat obj = new SimpleDateFormat("dd/MM/yyyy");
        return obj.format(date);
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

    public boolean isInTable(int number, int[] table) {
        if (table == null)
            return false;
        for (int a = 0; a < table.length; a++) {
            if (table[a] == number)
                return true;
        }
        return false;
    }

    public String historicValue() throws Exception {
        String answer = "";
        Connection connection = this.getConnection();
        Statement statement = this.createStatement(connection);
        connection.setAutoCommit(false);
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            String request = "SELECT *FROM " + this.tableName() + " WHERE " + this.getPrimaryKeyName() + "='"
                    + this.getPrimaryKey() + "'";
            ResultSet result = statement.executeQuery(request);
            while (result.next()) {
                for (int a = 0; a < fields.length; a++) {
                    if (a == 0 && this.getPrimaryKey() != null)
                        answer += this.getPrimaryKeyName() + ":" + result.getString(a + 1);
                    else
                        answer += ";" + fields[a].getName() + ":" + result.getString(a + 1);
                }
            }
        } catch (Exception e) {
            connection.rollback();
        } finally {
            this.commitOrClose(connection, statement, true);
        }
        return answer;
    }

    public ObjectDB() {
        this.setPrimaryKeyName();
        this.setPrefix();
        this.setFonction();
        this.setLongPK();
        this.setSequence();
    }
}
