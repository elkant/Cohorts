/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts;

import data.loadcohort;
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
public class addbirthyearmonth extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          
            
            String getdata="select * from pmtct_cohort";
            
            dbConn conn= new dbConn();
            conn.rs=conn.st.executeQuery(getdata);
            while(conn.rs.next()){
            
                String year=conn.rs.getString("reportingyear");
                String month=conn.rs.getString("reportingmonth");
            String bym3,bym6,bym9,bym12,bym24;
            
            bym3=bym6=bym9=bym12=bym24="";
            
            
             loadcohort lc= new loadcohort();
            
String id=conn.rs.getString("id");
            
            bym3=lc.getyearmonthkey(new Integer(year),new Integer(month), -3);
            bym6=lc.getyearmonthkey(new Integer(year),new Integer(month), -6);
            bym9=lc.getyearmonthkey(new Integer(year),new Integer(month), -9);
            bym12=lc.getyearmonthkey(new Integer(year),new Integer(month), -12);
            bym24=lc.getyearmonthkey(new Integer(year),new Integer(month), -24);
            
                System.out.println(" yearmonth: "+conn.rs.getString("yearmonth")+" | 3  birthyearmonth:"+bym3+" | 6  birthyearmonth:"+bym6+" | 9  birthyearmonth:"+bym9+" | 12  birthyearmonth:"+bym12+" | 24  birthyearmonth:"+bym24);
              
            String updatecode="update pmtct_cohort set birthyearmonth3m='"+bym3+"', birthyearmonth6m='"+bym6+"',birthyearmonth9m='"+bym9+"', birthyearmonth12m='"+bym12+"',birthyearmonth24m='"+bym24+"' where id='"+id+"'";
                
            System.out.println(""+updatecode);
            
           conn.st2.executeUpdate(updatecode);
            }
            
            
            out.println("</html>");
            if(conn.rs!=null){conn.rs.close();}
            if(conn.rs1!=null){conn.rs1.close();}
            if(conn.st!=null){conn.st.close();}            
            if(conn.st2!=null){conn.st2.close();}
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
            Logger.getLogger(addbirthyearmonth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(addbirthyearmonth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
