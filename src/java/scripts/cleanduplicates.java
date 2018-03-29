/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author EKaunda
 */
public class cleanduplicates extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            out.println("</html>");
            
            
            dbConn conn = new dbConn();
                    
                    String getduplicates="SELECT ID ,NewID,Year,Month,MFLCODE,IndicatorID,Facility,Subcounty,County,Indicatorname,kp_3m,np_3m,tl_3m,kp_6m,np_6m,tl_6m,kp_9m,np_9m,tl_9m,kp_12m,np_12m,tl_12m,kp_24m,np_24m,tl_24m,mflcode2,reportingyear,reportingmonth,yearmonth,hesabu,isduplicate FROM pmtct_art_cohort.mca where isduplicate=1";
           conn.rs= conn.st.executeQuery(getduplicates);
           
           while(conn.rs.next()){
           
           //compare which ones have data 
           String uniqueidmain=conn.rs.getString("ID");
           String newid=conn.rs.getString("NewID");
           int sum=conn.rs.getInt("hesabu");
           
           String getother=" select ID,NewID, hesabu from mca where NewID='"+newid+"' and hesabu < "+sum+" ";
           
           conn.rs1=conn.st1.executeQuery(getother);
           if(conn.rs1.next()){
           //delete this
           String delete=" delete from mca where ID='"+conn.rs1.getString("ID")+"'";
               System.out.println(delete);
               System.out.println(" Compare ");
           //conn.st2.executeUpdate(delete);
           
           
           }
           
           }
            
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(cleanduplicates.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(cleanduplicates.class.getName()).log(Level.SEVERE, null, ex);
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
