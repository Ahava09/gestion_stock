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
import model.Mouvement;
import model.Sortie;
import model.Stock;

/**
 *
 * @author itu
 */
@WebServlet(name = "Validation", urlPatterns = {"/Validation"})
public class Validation extends HttpServlet {
    public Sortie sortie = new Sortie();
    public Mouvement mouvement = new Mouvement();
    private Stock stock = new Stock();
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
            out.println("<title>Servlet Validation</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Validation at " + request.getContextPath() + "</h1>");
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
        String id_sortie = request.getParameter("id_sortie");
        String date_validation = request.getParameter("date")+" 05:00:00";
        out.println("ID: "+date_validation);
        try {
            sortie = sortie.getSortie(id_sortie);
            sortie.setDate_Validation1(date_validation);
            boolean tf = stock.check(sortie);
            out.println("Date de validation: "+sortie.getDate_validation());
            Mouvement[] liste = mouvement.verification(sortie);
            for (int i =0; i<liste.length; i++) {
                out.println("Entree: "+liste[i].getId_entree()+" Sortie: "+liste[i].getId_sortie()+" Reste: "+liste[i].getReste());
            }            
            sortie.update();
            mouvement.insert(liste);
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            out.print("Exception "+ex);
        }catch (Error e) {
            request.setAttribute("error", "Impossible de faire une sortie");
            out.print("Error: "+e.getMessage());
        }
//        RequestDispatcher dispat = request.getRequestDispatcher("/Liste");
//        dispat.forward(request, response);
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
