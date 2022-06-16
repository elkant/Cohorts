/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me_matrix;

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
 * @author EKaunda
 */
public class save_me_matrix extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          
            dbConn conn = new dbConn();
          String table=" internal_system.me_mat_data ";
          
          String mfl="";
          
          if(request.getParameter("facility")!=null){
          mfl=request.getParameter("facility");
          
          }
          
          /**
           *
           *  id:identifier,              
               yearmonth:yearmonth,
               facility:""+facility,
               indicatorid:indicatorid,
               19m:bl19_Male,
               19f:bl19_Female,
               ttl:ttl,
               userid:'909090'
           *
           */
            
 String[] dataelementsarr= {"id","yearmonth","facility","indicatorid","ttl","userid"}; 
 //String[] orgunitsarr= {"county","`sub-county`"}; 
          
 ArrayList al= new ArrayList();
 
            
            
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

            System.out.println(""+insertqry);

    //conn.st_2.executeUpdate(updateqr);
    conn.pst1=conn.connect.prepareStatement(insertqry);   
//facilityname.startdate.enddate.hiv_pos_target_child.hiv_pos_target_adult.hiv_pos_target_total.hiv_pos_child.hiv_pos_adult.hiv_pos_total.new_care_child.new_care_adult.new_care_total.new_art_target_child.new_art_target_adult.new_art_target_total.started_art_child.started_art_adult.started_art_total.viral_load_target_child.viral_load_target_adult.viral_load_target_total.viral_load_done_child.viral_load_done_adult.viral_load_done_total.ipt_target_child.ipt_target_adult.ipt_target_total.ipt_child.ipt_adult.ipt_total.testing_target_child.testing_target_adult.testing_target_total.test_child.test_adult.test_total.pmtct_hiv_pos_target.pmtct_hiv_pos.eid_target.eid_done.viral_load_mothers_target.viral_load_mothers_done.user.hiv_pos_yield_perc_child.hiv_pos_yield_perc_adult.hiv_pos_yield_perc_all.hiv_pos_care_perc_child.hiv_pos_care_perc_adult.hiv_pos_care_perc_all.started_art_perc_child.started_art_perc_adult.started_art_perc_all.viral_test_perc_child.viral_test_perc_adult.viral_test_perc_all.ipt_done_perc_child.ipt_done_perc_adult.ipt_done_perc_all.tested_perc_child.tested_perc_adult.tested_perc_all.hiv_pos_yield_cmts.hiv_pos_care_cmts.started_art_cmts.viral_test_cmts.ipt_done_cmts.tested_cmts.viral_load_mothers_perc.eid_done_perc.pmtct_hiv_pos_perc.viral_load_mothers_cmts.eid_done_cmts.pmtct_hiv_pos_cmts




//______________________________________________________________________________________

int rowcount=1;

for(int a=0;a<dataelementsarr.length;a++)
{
    String data="";
    
    if(request.getParameter(""+dataelementsarr[a])!=null)
    {
    data=request.getParameter(""+dataelementsarr[a]);
    }
conn.pst1.setString(rowcount,data);

rowcount++;


}



//______________________________________________________________________________________




if(conn.pst1.executeUpdate()==1)
{
   out.println("M AND E Matrix Data Saved succesfully ");
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
            Logger.getLogger(save_me_matrix.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(save_me_matrix.class.getName()).log(Level.SEVERE, null, ex);
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




public String arraytostring(String [] ar){
String vl="";
int cnt=0;
for(String a:ar){
if(cnt==0)    { vl+=""+a;}
else          { vl+=","+a;}
cnt++;
}


return vl;
}

}
