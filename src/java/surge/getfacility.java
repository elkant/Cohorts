/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surge;

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
public class getfacility extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           response.setContentType("text/html;charset=UTF-8");
    

    
    String subcountyid,current_districts;
    
    
    
    
    
    
    
     subcountyid=request.getParameter("subcounty");
     
     
      String subcounty="(";
        String subcountyar[]=null;
        
       subcountyar=request.getParameter("subcounty").split(","); 
       
       if(request.getParameterValues("subcounty")!=null)
       {
       
       for(int a=0;a<subcountyar.length;a++)
       {
       
           if(a==subcountyar.length-1)
           {
               
            subcounty+="'"+subcountyar[a]+"')";  
            
           }
     else {
               
       subcounty+="'"+subcountyar[a]+"',"; 
       
          }
           
           
       }
           System.out.println(" array length "+subcountyar.length);
    }
     
 
    //   System.out.println(" County:"+ county_name); 
       current_districts="";
       
       String districts="Select `Facility Name`,`mflcode` from aphiaplus_moi.surge_sites where `Sub-county` in "+subcounty+" group by `mflcode`";
       
       dbConn conn=new dbConn();
       
       conn.rs=conn.st.executeQuery(districts);
       
       current_districts="";
       
       while(conn.rs.next()){


current_districts=current_districts+"<option value=\""+conn.rs.getString(2)+"\">"+conn.rs.getString(1)+"</option>";





       }
    
    

    
    
    
    
    
    try {
        out.println(current_districts);
       
    } finally {   
          
               if(conn.rs!=null){ conn.rs.close();}
               if(conn.st!=null){ conn.st.close();}
               if(conn.connect!=null){ conn.connect.close();}
        out.close();
    }
          
        } catch (SQLException ex) {
            Logger.getLogger(getsubcounty.class.getName()).log(Level.SEVERE, null, ex);
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
