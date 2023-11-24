/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Stock;
import model.Mouvement;
import model.Etat_stock;
import model.Etat_stocks;
import model.Categorie;

/**
 *
 * @author itu
 */
@WebServlet(name = "Traitement_etat", urlPatterns = {"/Traitement_etat"})
public class Traitement_etat extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Traitement_etat</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Traitement_etat at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Etat_stock etat_stock = new Etat_stock();
        Etat_stocks etat_stocks = new Etat_stocks();
        String dateF = request.getParameter("dateF")+" 05:00:00";
        String dateD = request.getParameter("dateD")+" 05:00:00";
        etat_stock.setId_produit(request.getParameter("id_produit"));
        etat_stock.setId_magasin(request.getParameter("id_magasin"));
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String quantite = request.getParameter("quantite");
        Categorie p = new Categorie();
        try {
//            java.util.Date utilDateD = dateFormat.parse(dateD); // Obtenir la date et l'heure actuelles en java.util.Date
//            java.sql.Timestamp sqlDateD = new java.sql.Timestamp(utilDateD.getTime()); // Convertir en java.sql.Date
            etat_stock.setDateD(dateD);
//            java.util.Date utilDateF = dateFormat.parse(dateF); // Obtenir la date et l'heure actuelles en java.util.Date
//            java.sql.Timestamp sqlDateF = new java.sql.Timestamp(utilDateF.getTime()); // Convertir en java.sql.Date
            etat_stock.setDateF(dateF);
            out.print(etat_stock.getDateD()+"********"+etat_stock.getDateF());
            Etat_stocks[] stocks = etat_stocks.getEtat(etat_stock);
            request.setAttribute("stocks", stocks);
            request.setAttribute("produit", p.getCategorie(etat_stock.getId_produit()));
            RequestDispatcher dispat = request.getRequestDispatcher("/etat_stock.jsp");
            dispat.forward(request,response);
        } catch (Error e) {
            request.setAttribute("error", "Impossible de faire une sortie");
//            Sorties.getList(request,response);
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            out.println(ex.getMessage());
//            Sorties.getList(request,response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
