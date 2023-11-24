/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Entree;
import static servlet.index.getList;

/**
 *
 * @author itu
 */
@WebServlet(name = "Traitement_entree", urlPatterns = {"/Traitement_entree"})
public class Traitement_entree extends HttpServlet {
    Entree entree = new Entree();
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
            out.println("<title>Servlet Traitement_entree</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Traitement_entree at " + request.getContextPath() + "</h1>");
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
        Sorties.getList(request, response);
        entree.setId_article(request.getParameter("id_article"));
        entree.setId_magasin(request.getParameter("id_magasin"));
        out.println("ID MAGASIN "+request.getParameter("id_magasin"));
        entree.setId_unite(request.getParameter("id_unite"));
        try {
            String date_entree = request.getParameter("dateEntree")+" 05:00:00";
            entree.setDate_entreee(date_entree);
            String date_expiration = request.getParameter("dateExpiration")+" 05:00:00";
            entree.setDate_expirationn(date_entree);
            entree.setQuantiteC(request.getParameter("quantite"));
            out.println("QUANTITE "+entree.getQuantite());
            entree.setPrix(request.getParameter("prix"));
            out.println("QUANTITE "+entree.getPrix_unitaire());
            out.println("QUANTITE "+entree.getId_unite());
            entree.save();
            request.setAttribute("avant", entree);
            request.setAttribute("apres", entree.inserer());
            RequestDispatcher dispat = request.getRequestDispatcher("/affichage.jsp");
            dispat.forward(request,response);
        } catch (Error e) {
            request.setAttribute("error", e);
        }catch (Exception ex) {
            request.setAttribute("error", ex);
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
