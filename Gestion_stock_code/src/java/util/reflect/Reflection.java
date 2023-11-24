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

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class Reflection {
    Class<?> classe ;

    public void setClasse(Class<?> classe) {this.classe = classe;}
    public Class<?> getClasse() {return classe;}

    public Reflection(Class<?> classe){this.setClasse(classe);}

    public Vector<Method> all_gets(){
        Field[] all_field = this.getClasse().getDeclaredFields();
        Method[] all_m = this.getClasse().getDeclaredMethods();
        Vector<Method> answer = new Vector<>();
        for(int a = 0;a<all_field.length;a++){
            for (int b = 0;b<all_m.length;b++) {
                if (("get"+(all_field[a].getName())).equalsIgnoreCase(all_m[b].getName()) == true) {
                    answer.add(all_m[b]);
                }
            }
        }
        return answer;
    }
    public Vector<Method> all_sets(){
        Field[] all_field = this.getClasse().getDeclaredFields();
        Method[] all_m = this.getClasse().getDeclaredMethods();
        Vector<Method> answer = new Vector<>();
        for(int a = 0;a<all_field.length;a++){
            for (int b = 0;b<all_m.length;b++) {
                if (("set"+(all_field[a].getName())).equalsIgnoreCase(all_m[b].getName()) == true) {
                    answer.add(all_m[b]);
                }
            }
        }
        return answer;
    }
    public Object createObject(String[] data) throws Exception {
        Constructor constructor = this.getClasse().getConstructor();
        util.reflect.ObjectDB answer = (ObjectDB) constructor.newInstance();
        Vector<Method> all_set = all_sets();
        if(data.length > all_set.size())answer.setPrimaryKey(data[0]);
        int indexData = 0;
        for(int a = 0;a< all_set.size();a++){
            indexData = a;
            if(data.length > all_set.size())indexData += 1;
            if(data[a] == null){
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("String")) {
                all_set.get(a).invoke(answer, to_string(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("int")) {
                all_set.get(a).invoke(answer, to_int(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("double")) {
                all_set.get(a).invoke(answer, to_double(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("float")) {
                all_set.get(a).invoke(answer, to_float(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("Date") || Arrays.toString(all_set.get(a).getParameterTypes()).contains("date") ) {
                all_set.get(a).invoke(answer, to_date(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("Timestamp")) {
                all_set.get(a).invoke(answer, to_Timestamp(data[indexData]));
            }
        }
        return answer;
    }

    public Object createobject(String[] data) throws Exception {
        
        Constructor constructor = this.getClasse().getConstructor();
        util.reflect.ObjectDB answer = (ObjectDB) constructor.newInstance();
        Vector<Method> all_set = all_sets();
        int indexData = 0;
        System.out.println("allset "+all_set.size()+" data "+ data.length);
        for(int a = 0;a< all_set.size();a++){
            indexData = a;
            if(data[a] == null){
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("String")) {
                all_set.get(a).invoke(answer, to_string(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("int")) {
                all_set.get(a).invoke(answer, to_int(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("double")) {
                all_set.get(a).invoke(answer, to_double(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("float")) {
                all_set.get(a).invoke(answer, to_float(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("Date")) {
                all_set.get(a).invoke(answer, to_date(data[indexData]));
            }else if(Arrays.toString(all_set.get(a).getParameterTypes()).contains("Timestamp")) {
                all_set.get(a).invoke(answer, to_Timestamp(data[indexData]));
            }
        }
        return answer;
    }

    public void affiche_vector(Vector<Object> objects)throws Exception{
        Vector<Method> methods = all_gets();
        for (int a = 0; a < this.getClasse().getDeclaredFields().length; a++) {
            System.out.print("          "+methods.get(a).getName());
        }
        System.out.println();
        for(int b = 0 ; b < objects.size() ; b++) {
            for (int a = 0; a < this.getClasse().getDeclaredFields().length; a++) {
                System.out.print("          "+methods.get(a).invoke(objects.get(b))+"   ");
            }
            System.out.println();
        }
    }

    public void affiche(ArrayList<Object> objects,ArrayList<Double> d)throws Exception{
        Vector<Method> methods = all_gets();
       /*for (int a = 0; a < this.getClasse().getDeclaredFields().length; a++) {
            System.out.print("          "+methods.get(a).getName());
        }
        System.out.println();*/
        System.out.println("<table border='1px'><tr>");
        for(int b = 0 ; b < objects.size() ; b++) {
            for (int a = 0; a < this.getClasse().getDeclaredFields().length; a++) {
                System.out.println(" <td><% out.print(         "+methods.get(a).invoke(objects.get(b))+"  ;%></td>  ");
            }
            System.out.println("<td> Note: <% out.print ("+d.get(b)+"); %> </td></tr>");
        }
        System.out.println("</table>");
    }

    public double to_double(String a){
        return Double.parseDouble(a);
    }
    public int to_int(String a){
        return Integer.parseInt(a);
    }
    public float to_float(String a){
        return Float.parseFloat(a);
    }
    public String to_string(String a){
        return a;
    }
    public Date to_date(String date){
        int index_scape = date.lastIndexOf(" ");
        return Date.valueOf( date );
    }
    public Timestamp to_Timestamp(String timestamp) throws ParseException{
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm");
        java.util.Date lFromDate1 = (java.util.Date) datetimeFormatter1.parse(timestamp);
        Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());
        return fromTS1;
    }
}