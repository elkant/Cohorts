/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmtct_ovc;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class save_pmtct_ovc extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
 
          
          
                      dbConn conn = new dbConn();
          String table=" internal_system.pmtct_ovc_data ";
          
          String mfl="";
          
          if(request.getParameter("facility")!=null){
          mfl=request.getParameter("facility");
          
          }
          String pid="";
          if(request.getParameter("patient_id")!=null)
          {
             pid=request.getParameter("patient_id");
          
          }
         
            
 String[] dataelementsarr= {"id","facility_id","linelisting_month","patient_id","indicator_id","value","encounter_id","user_id","is_locked"};
 //String[] orgunitsarr= {"county","`sub-county`"}; 
          
 ArrayList al= new ArrayList();
 
 //This section here saves every field in a div mode i.i every form fied has a row.
  
  //Any time you save data, First delete any existing data for that petient at the start of the operation.          
            
 
  
 String insertqr_parta= "replace into "+table+" (";  // finish with )
         String insertqr_partb= " values ("; // finish with )
 
for(int a=0;a<dataelementsarr.length;a++)
{

//build an inster qry
    if(a==dataelementsarr.length-1){
insertqr_parta+=dataelementsarr[a]+"";
insertqr_partb+="?";
    }
    else {
    insertqr_parta+=dataelementsarr[a]+",";
    insertqr_partb+="?,";
    }
}
//append orgunits

//last section
insertqr_parta+=")";
insertqr_partb+=")";



//append  

String insertqry=insertqr_parta+insertqr_partb;

            //System.out.println(""+insertqry);

 if(!pid.equals(""))
    {
    conn.pst1=conn.connect.prepareStatement(insertqry);   
    
    }



//______________________________________________________________________________________

int rowcount=1;

for(int a=0;a<dataelementsarr.length;a++)
{
    String data="";
    
    if(request.getParameter(""+dataelementsarr[a])!=null)
    {
    data=request.getParameter(""+dataelementsarr[a]);
    }
     else if(request.getParameterValues(""+dataelementsarr[a]+"[]")!=null)
    {
    data=ArrayToString(request.getParameterValues(""+dataelementsarr[a]+"[]"));
        System.out.println(" Data Ni ________"+data);
    }
conn.pst1.setString(rowcount,data);

rowcount++;


}



//______________________________________________________________________________________




if(conn.pst1.executeUpdate()==1)
{
   out.println("Data Saved Successfully");
}
else 
{
 out.println(" Data Not successfully saved ");

}
  

        if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}

          
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
            Logger.getLogger(save_pmtct_ovc.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(save_pmtct_ovc.class.getName()).log(Level.SEVERE, null, ex);
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





private String ArrayToString(String [] arr)
{
     String vals="";
for(int x=0;x<arr.length;x++){vals+=arr[x]+",";}
       


return vals;
}

}
