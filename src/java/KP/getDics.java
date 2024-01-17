/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KP;

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
import javax.servlet.http.HttpSession;

/**
 *
 * @author EKaunda
 */
public class getDics extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           response.setContentType("text/html;charset=UTF-8");
    
           HttpSession session = request.getSession();
           
String dics="";
    
    String lip="";
    
    if(request.getParameter("lip")!=null)
    {    
     lip=request.getParameter("lip");
     
    }

       String getdics="Select * from internal_system.dic where `lip` in ('"+lip+"') and active='1' ";
       
            System.out.println("___"+getdics);
       
       dbConn conn=new dbConn();
       
       conn.rs=conn.st.executeQuery(getdics);
       
  
       
       while(conn.rs.next())
       {
           
           String fac="";
String selectedval="";

if(session.getAttribute("lastsaveddic")!=null)
{
    
    fac=session.getAttribute("lastsaveddic").toString();
    //System.out.println("_______________________________:::"+fac);
    if((conn.rs.getString("dic_id")).equals(fac)){selectedval="selected";} 
}

dics+="<option "+selectedval+" data-ward_name='"+conn.rs.getString("ward_name")+"' data-ward_id='"+conn.rs.getString("ward_id")+"' data-supported_kp='"+conn.rs.getString("supported_kp")+"' value=\""+conn.rs.getString("dic_id")+"\">"+conn.rs.getString("dic_name")+"</option>";

       }
    
    

    try {
        out.println(dics);
       
    } finally {   
         
               if(conn.rs!=null){ conn.rs.close();}
               if(conn.st!=null){ conn.st.close();}
                if(conn.connect!=null){ conn.connect.close();}
        out.close();
    }
          
        } catch (SQLException ex) {
            Logger.getLogger(getDics.class.getName()).log(Level.SEVERE, null, ex);
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
