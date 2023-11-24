/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Sortie;

/**
 *
 * @author itu
 */
@WebServlet(name = "Traitement_liste", urlPatterns = {"/Traitement_liste"})
public class Traitement_liste extends HttpServlet {
    public Sortie sortie = new Sortie();
    
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
            out.println("<title>Servlet Traitement_liste</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Traitement_liste at " + request.getContextPath() + "</h1>");
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
        String id_article = request.getParameter("id_article");
        String id_magasin = request.getParameter("id_magasin");
        PrintWriter out = response.getWriter();
        try {
            Sortie[] sorties = null;

            if (id_article.equalsIgnoreCase("ART000") && id_magasin.equalsIgnoreCase("MAG000")) {
                
                out.println("ID ARTICLE1 " + id_article);
                // Obtenez les sorties non validées en fonction de votre logique
                sorties = sortie.getListSortieNonValide();
            } else {
                out.println("ID ARTICLE1 " + id_article);
                if (!id_article.equalsIgnoreCase("ART000") && !id_magasin.equalsIgnoreCase("MAG000")) sorties = sortie.getListSortieNonValide(id_article, id_magasin);
                if (!id_article.equalsIgnoreCase("ART000") && id_magasin.equalsIgnoreCase("MAG000")) sorties = sortie.getListSortieNonValideAS("id_article", id_article);
                if (!id_magasin.equalsIgnoreCase("MAG000") && id_article.equalsIgnoreCase("ART000")) sorties = sortie.getListSortieNonValideAS("id_magasin", id_magasin);
            }
            // Assurez-vous que sorties n'est pas nul avant de l'ajouter à la requête
            if (sorties != null) {
                out.println("ID ARTICLE is not null " + sorties.length);
                request.setAttribute("sorties", sorties);
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex);
        }

        // Vous appelez Sorties.getList(request, response) sans utiliser le résultat.
        // Je suppose que cela doit être appelé après la manipulation des sorties.
        Sorties.getList(request, response);
        RequestDispatcher dispat = request.getRequestDispatcher("/liste.jsp");
        dispat.forward(request, response);
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
