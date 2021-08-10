/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rri_gaps;

import General.IdGenerator2;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.dbConn;
import java.sql.SQLException;

/**
 *
 * @author EKaunda
 */
public class refresh_rri_baselines extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
           String sd="";
           String ed="";
           String rd="";
            IdGenerator2 ig= new IdGenerator2();
           
           sd=ig.LastMonthEndDate();
         String  Strodeproc=""; 
           
            if(request.getParameter("sp")!=null){                
            Strodeproc=request.getParameter("sp");
            }
            
            if (request.getParameter("sd") != null) {
                sd = request.getParameter("sd");
            }

            if (request.getParameter("ed") != null) {
                ed = request.getParameter("ed");
            }
            if (request.getParameter("rd") != null) {
                rd = request.getParameter("rd");
            }
            
        
        dbConn conn = new dbConn();
         
        String sp="call "+Strodeproc+"('"+sd+"','"+ed+"','"+rd+"');";
        
        conn.st.executeQuery(sp);
            
            
        out.println(" Refreshed successfully "+sp);
            
        } catch (SQLException ex) {
            out.println(" Refreshed ended with error "+ex);
        } finally {
            out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
