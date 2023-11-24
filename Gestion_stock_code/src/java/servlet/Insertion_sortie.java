/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
@WebServlet(name = "Insertion_sortie", urlPatterns = {"/Insertion_sortie"})
public class Insertion_sortie extends HttpServlet {
    private Sortie sortie = new Sortie();
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
            out.println("<title>Servlet Insertion_sortie</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Insertion_sortie at " + request.getContextPath() + "</h1>");
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
        sortie.setId_article(request.getParameter("id_article"));
        sortie.setId_magasin(request.getParameter("id_magasin"));
        sortie.setId_unites(request.getParameter("id_unite"));
        out.println("ID MAGASIN "+request.getParameter("id_magasin"));
        try {
            String date_insertion = request.getParameter("dates")+" 05:00:00";
            sortie.setDate_Insertion1(date_insertion);
            out.println("DATE "+sortie.getDate_insertion());
            sortie.setQuantites(request.getParameter("quantite"));
            out.println("DATE "+sortie.getDate_validation());
            out.println("QUANTITE "+sortie.getQuantite());
            sortie.save();
            request.setAttribute("avant", sortie);
            request.setAttribute("apres", sortie.inserer());
            RequestDispatcher dispat = request.getRequestDispatcher("/affiche.jsp");
            dispat.forward(request,response);
        } catch (Error e) {
            request.setAttribute("error", e);
            out.println(e);
        }catch (ParseException per) {
            request.setAttribute("error", per);
            out.println(per);
        } catch (Exception ex) {
            request.setAttribute("error", ex);
            out.println(ex);
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
