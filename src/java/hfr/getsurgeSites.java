/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hfr;

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
public class getsurgeSites extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          dbConn conn= new dbConn();
          
          String category="";
         
          category = request.getParameter("ct");
          
          
          
            try {
                out.println(getSites(category,conn));
            } catch (SQLException ex) {
                Logger.getLogger(getsurgeSites.class.getName()).log(Level.SEVERE, null, ex);
                 out.println("Error in SQL  connection");
                
                
                
            }
          
            if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}
            
            
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(getsurgeSites.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(getsurgeSites.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This servlet loads the list of surge or non surge sites ";
    }// </editor-fold>

    public String getSites(String category,dbConn conn) throws SQLException{
        
    String sites="<option value=''>Select facility</option>";
    String where=getSitewhere(category);
      
    String getdata=" select SubPartnerNom as facility, CentreSanteId as mflcode,datimid ,datimward as ward,datimname  from internal_system.subpartnera where active=1 and "+where+" order by SubPartnerNom ";
    
    conn.rs=conn.st.executeQuery(getdata);
    
    
    while(conn.rs.next())
    {
    //____________________________________
        String facil=conn.rs.getString("facility");
        String ward=conn.rs.getString("ward");
        String mfl=conn.rs.getString("mflcode");
        String datimid=conn.rs.getString("datimid");
        
        
//SubPartnerID 
//SubPartnerNom
//DistrictID 
//CentreSanteId
//organisationunitid
//datimward
//datimid 
//datimname
       
     sites+="<option data-mfl='"+mfl+"' data-ward=\""+ward+"\" data-facil=\""+facil+"\" data-datimid='"+datimid+"' value='"+mfl+"'>"+facil+"</option>";   
     //_____________________________   
     
    }
    
    return sites;
    }
    
    
    public String getSitewhere(String category){
    String where="";
          //get sitetype
          
          
            if (category!= null) {
                

                if (category.equals("non_surge_sites")) {
                    where = " (SurgeSite is null or surgeSite =0 )  ";

                } else {
                    where = " SurgeSite =1 ";

                }

                                  }
    
    
    
    return where;
    
    
    
    }
    
    
    
}
