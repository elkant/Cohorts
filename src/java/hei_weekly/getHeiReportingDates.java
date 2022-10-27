/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hei_weekly;

import db.dbConn;
import hfr.idGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author EKaunda
 */
public class getHeiReportingDates extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          
          
            
  
    
    
        }
    }

  
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
    
    
    
    
    public JSONArray getRepDates(dbConn conn,String startdate) throws SQLException{

        
        JSONArray ja= new JSONArray();
        
    idGenerator ig= new idGenerator();
    
    String leo=ig.toDay();
        
     String gd="select * from internal_system.upi_dates where (edate>='"+startdate+"' and edate <='"+leo+"') and weekday='Friday' order by edate desc";   
     System.out.println("QRY "+gd);
     
   conn.rs=conn.st.executeQuery(gd);
   
   while(conn.rs.next()){
        JSONObject hm= new JSONObject();
       
//       date varchar(25) 
//d_yearmonth varchar(25) 
//week int(11) 
//report_txcurr varchar(25) 
//ym varchar(45) 
//startdate
   
   hm.put("enddate", conn.rs.getString("edate"));
   hm.put("week", conn.rs.getString("weekno"));   
   hm.put("startdate", conn.rs.getString("sdate"));
   
   
   ja.put(hm);
   }
   
    
    return ja;
    
    }
    
    public String toHtmlDates(JSONArray ja){
    String dates="<option data-sdate='' data-edate='' data-wk='' value=''>select date</option>";
    
     
                for(int a=0;a<ja.length();a++)
                {
                
               JSONObject jo=(JSONObject) ja.getJSONObject(a);
               String edate=jo.get("enddate").toString();
               String sdate=jo.get("startdate").toString();
               String week=jo.get("week").toString();
             
               
           dates+="<option data-sdate='"+sdate+"' data-edate='"+edate+"' data-wk='"+week+"' value='"+edate+"'>"+sdate+" to "+edate+" </option>";  
               
               
                
                }
    
    
      return dates;  
    }
    
    

}
