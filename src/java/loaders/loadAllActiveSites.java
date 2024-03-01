/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaders;

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
public class loadAllActiveSites extends HttpServlet {

   HttpSession session;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            session=request.getSession();
            
          
            
            
            
            String getfacils="select CentreSanteId,mdt,SubPartnerID,khisname,SubPartnerNom from internal_system.subpartnera where (rmcah='1' or active='1')";
         
            dbConn conn= new dbConn();
            conn.rs=conn.st.executeQuery(getfacils);
            String facil="<option value=''>Select facility</option>";
           
            
            while(conn.rs.next())
            {
                
                String mflcode=conn.rs.getString("CentreSanteId");
            String subpartnerid=conn.rs.getString("SubPartnerID");
            String facilname=conn.rs.getString("SubPartnerNom");
            
String fac="";
String selectedval="";

if(session.getAttribute("lastsavedfacil")!=null)
{
    
    fac=session.getAttribute("lastsavedfacil").toString();
    //System.out.println("_______________________________:::"+fac);
    if(subpartnerid.equals(fac)){selectedval="selected";} 
}

         
facil+="<option "+selectedval+"  value=\""+mflcode+"\">"+facilname+" - "+mflcode+"</option>";

   


            }
            
         
            out.println(facil);
          if(conn.rs!=null){conn.rs.close();}
            if(conn.st!=null){conn.st.close();}
            if(conn.connect!=null){conn.connect.close();}
            
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try {
           processRequest(request, response);
       } catch (SQLException ex) {
           Logger.getLogger(loadfacility.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try {
           processRequest(request, response);
       } catch (SQLException ex) {
           Logger.getLogger(loadfacility.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
