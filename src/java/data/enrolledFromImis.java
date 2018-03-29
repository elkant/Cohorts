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
import org.json.JSONObject;

/**
 *
 * @author EKaunda
 */
public class enrolledFromImis extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          String subpartnerID="";
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
        
          subpartnerID=fc.split("_")[1];
            
          loadcohort lc= new loadcohort();
          
            JSONObject jo= new JSONObject();
            
            
          //lc.getyearmonth(year,month,-deductvalue); reme cm is cohort month in formart 3m,6m,9m,12m,24m,36m,60m
          String ym=lc.getyearmonth(new Integer(yr), new Integer(mn), new Integer("-"+cm.replace("m","")));
          
          
          
          String newmn=ym.split("_")[1];
            if(new Integer(newmn)>=10){
          year=new Integer(ym.split("_")[0])+1;
          }
          else {
             year=new Integer(ym.split("_")[0]);
          }
          
          
         id=year+"_"+newmn+"_"+subpartnerID;//*******************************
          
            System.out.println("ID is  "+id);
            
            String sql="";
            //out.println("</html>");
            
            dbConn conn= new dbConn();
            
            if(!mn.equals("")&& !yr.equals("") &&! fc.equals("")){
            //get and return data
            if(ct.equalsIgnoreCase("art")){
            
            sql="select  IFNULL((HV0323 + HV0324),0) as adult ,  IFNULL((HV0321 + HV0322),0) as children,  IFNULL((HV0323 + HV0324+HV0321 + HV0322),0) as ttl  ";
           
            }
            
            
            else {
            
            sql="select  IFNULL((HV0205),0) as kp ,  IFNULL((HV0206),0) as np,  IFNULL((HV0205 + HV0206),0) as ttl";
            
            }
            
            sql+=" from internal_system.moh731 where id='"+id+"'";
            
                System.out.println(""+sql);
            conn.rs=conn.st0.executeQuery(sql);
            
            if (conn.rs.next()){
            
             
            jo.put("val1", conn.rs.getInt(1));
        jo.put("val2", conn.rs.getInt(2));
        jo.put("tl", conn.rs.getInt(3));
            }
            //if no values in IMIS
            else {
       jo.put("val1", "");
        jo.put("val2", "");
        jo.put("tl", "");
    
            
            }
            
            }
            
            
             out.println(jo);
            
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
