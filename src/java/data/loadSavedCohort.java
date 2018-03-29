/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author EKaunda
 */
public class loadSavedCohort extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          String mflcode="";
          String yr="";
          String mn="";
          String id="";
          String ct="";
          String cm="";
          String fc="";
          int year=0;
          cm=request.getParameter("cm");
          ct=request.getParameter("ct");
          mn=request.getParameter("mn");
          yr=request.getParameter("yr");
          fc=request.getParameter("fc");
        
          mflcode=fc.split("_")[0];
            
          loadcohort lc= new loadcohort();
          
          
            
            JSONArray ar= new JSONArray();
            
            String tablename="";
            
               if(ct.equals("art")){
                   
               tablename="art_cohort";
               
               }
            else {
                   
               tablename="pmtct_cohort";
               
               }
            
       
          
        // id=year+"_"+newmn+"_"+subpartnerID;//*******************************
          
            String sql="";
            //out.println("</html>");
            
            dbConn conn= new dbConn();
            
            if(!mn.equals("")&& !yr.equals("") &&! fc.equals("")){
            //get and return data
            if(ct.equalsIgnoreCase("art")){
            
            sql="select id, indicator, IFNULL(adult_"+cm+",'0') as adult_"+cm+" , IFNULL(child_"+cm+",'0') as child_"+cm+", IFNULL(tl_"+cm+",'0') as tl_"+cm+"  ";
            }
            
            
            else {
            
            sql="select  id, indicator ,IFNULL(kp_"+cm+",'') as kp_"+cm+" , IFNULL(np_"+cm+",'') as np_"+cm+" ,IFNULL(tl_"+cm+",'') as tl_"+cm+" ";
            
            }
            
            sql+=" from "+tablename+" where mflcode='"+mflcode+"' and yearmonth='"+yr+mn+"' and (indicator!='12' ) and (indicator!='24' ) order by indicator ";
            
            
            conn.rs=conn.st0.executeQuery(sql);
                System.out.println(""+sql);
            
            while (conn.rs.next()){
                
             JSONObject jo= new JSONObject(); 
        jo.put("indicator", conn.rs.getString(2));
        //always show 0's for LTFU 3m
             if( cm.equalsIgnoreCase("3m") && (conn.rs.getString(2).equals("6") || conn.rs.getString(2).equals("18") )){
        jo.put("val1", 0);
        jo.put("val2", 0);
        jo.put("val3", 0);
        System.out.println("Indicator "+conn.rs.getInt(2)+" no LTFU");
             }
              //no values for vl done and suppressed Np 3m
             else if(ct.equalsIgnoreCase("pmtct") && cm.equalsIgnoreCase("3m") && (conn.rs.getString(2).equals("10")|| conn.rs.getString(2).equals("11") ) ){
        jo.put("val1",  conn.rs.getString(3));
        jo.put("val2", 0);
        jo.put("val3", conn.rs.getString(5));
        System.out.println("Indicator "+conn.rs.getString(2)+" if 2------");
             }
             //no values for vl done and suppressed
             else if(  cm.equals("9m") && (conn.rs.getString(2).equals("10")|| conn.rs.getString(2).equals("11") ) ){
        jo.put("val1", 0);
        jo.put("val2", 0);
        jo.put("val3", 0);
        System.out.println("Indicator "+conn.rs.getString(2)+" if 3------");
             }
             else {
        jo.put("val1", conn.rs.getString(3));
        jo.put("val2", conn.rs.getString(4));
        jo.put("val3", conn.rs.getString(5));
             
             }
         
        ar.put(jo);
            
            }
            
            }
            
            
             out.println(ar);
            
        }
        
      
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(enrolledFromImis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(enrolledFromImis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
