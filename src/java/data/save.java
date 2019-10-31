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
import javax.servlet.http.HttpSession;


/**
 *
 * @author Geofrey Nyabuto
 */
public class save extends HttpServlet {
HttpSession session;


String error;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String columnName,value,id,cohort,tablename;
           
            tablename=cohort=id="";
           session=request.getSession();
           dbConn conn = new dbConn();
          String columnarray[]=request.getParameter("col").split("-");
  
        columnName=columnarray[0];
        cohort=request.getParameter("cohort").trim();
        value=request.getParameter("value").trim();

        
        if(value.equals("")){value="0";}
        if(cohort.equalsIgnoreCase("art")){tablename="art_cohort";} 
        else if(cohort.equalsIgnoreCase("pmtct")){tablename="pmtct_cohort";}
        else {tablename="";}
        
      id=request.getParameter("id").trim();
      
   // System.out.println("col : "+columnName+" value : "+value+" id "+id+" cohort "+cohort);


//    year="";
    if(id.equals("") || tablename.equals("")){error="<font color=\"red\">ERROR : Please select all the filter parameters .</font>";}
    else{
        error="success";
    String indicator="";
    String mflcode="";
    String reportingyear="";
    String reportingmonth="";
    String yearmonth="";
    
    String arr[]=id.split("_");
    
    //id=yearstring+"_"+monthstring+"_"+mflcode+"_"+indicatorid;
    
    reportingyear=arr[0];
    reportingmonth=arr[1];
    mflcode=arr[2];
    indicator=arr[3];
    yearmonth=reportingyear+reportingmonth;

String Insertqr= "insert into "+tablename+" (id,indicator,"+columnName+",mflcode,reportingyear,reportingmonth,yearmonth) values (?,?,?,?,?,?,?)";


String updateqr="update "+tablename+" set indicator=?,"+columnName+"=?,mflcode=?,reportingyear=?,reportingmonth=?,yearmonth=? where id='"+id+"'";
//check whether data for that month, year and facility has been saved






String checker="select id from "+tablename+" where id='"+id+"'";     
       // System.out.println(""+checker);
conn.rs=conn.st_1.executeQuery(checker);

if(conn.rs.next()){
  
    //conn.st_2.executeUpdate(updateqr);
    conn.pst1=conn.connect.prepareStatement(updateqr);   
//facilityname.startdate.enddate.hiv_pos_target_child.hiv_pos_target_adult.hiv_pos_target_total.hiv_pos_child.hiv_pos_adult.hiv_pos_total.new_care_child.new_care_adult.new_care_total.new_art_target_child.new_art_target_adult.new_art_target_total.started_art_child.started_art_adult.started_art_total.viral_load_target_child.viral_load_target_adult.viral_load_target_total.viral_load_done_child.viral_load_done_adult.viral_load_done_total.ipt_target_child.ipt_target_adult.ipt_target_total.ipt_child.ipt_adult.ipt_total.testing_target_child.testing_target_adult.testing_target_total.test_child.test_adult.test_total.pmtct_hiv_pos_target.pmtct_hiv_pos.eid_target.eid_done.viral_load_mothers_target.viral_load_mothers_done.user.hiv_pos_yield_perc_child.hiv_pos_yield_perc_adult.hiv_pos_yield_perc_all.hiv_pos_care_perc_child.hiv_pos_care_perc_adult.hiv_pos_care_perc_all.started_art_perc_child.started_art_perc_adult.started_art_perc_all.viral_test_perc_child.viral_test_perc_adult.viral_test_perc_all.ipt_done_perc_child.ipt_done_perc_adult.ipt_done_perc_all.tested_perc_child.tested_perc_adult.tested_perc_all.hiv_pos_yield_cmts.hiv_pos_care_cmts.started_art_cmts.viral_test_cmts.ipt_done_cmts.tested_cmts.viral_load_mothers_perc.eid_done_perc.pmtct_hiv_pos_perc.viral_load_mothers_cmts.eid_done_cmts.pmtct_hiv_pos_cmts

conn.pst1.setString(1,indicator);
conn.pst1.setString(2,value);
conn.pst1.setString(3,mflcode);
conn.pst1.setString(4,reportingyear);
conn.pst1.setString(5,reportingmonth);
conn.pst1.setString(6,yearmonth);
if(conn.pst1.executeUpdate()==1){
error="success";
} 
}
else {

    conn.pst1=conn.connect.prepareStatement(Insertqr);   
//facilityname.startdate.enddate.hiv_pos_target_child.hiv_pos_target_adult.hiv_pos_target_total.hiv_pos_child.hiv_pos_adult.hiv_pos_total.new_care_child.new_care_adult.new_care_total.new_art_target_child.new_art_target_adult.new_art_target_total.started_art_child.started_art_adult.started_art_total.viral_load_target_child.viral_load_target_adult.viral_load_target_total.viral_load_done_child.viral_load_done_adult.viral_load_done_total.ipt_target_child.ipt_target_adult.ipt_target_total.ipt_child.ipt_adult.ipt_total.testing_target_child.testing_target_adult.testing_target_total.test_child.test_adult.test_total.pmtct_hiv_pos_target.pmtct_hiv_pos.eid_target.eid_done.viral_load_mothers_target.viral_load_mothers_done.user.hiv_pos_yield_perc_child.hiv_pos_yield_perc_adult.hiv_pos_yield_perc_all.hiv_pos_care_perc_child.hiv_pos_care_perc_adult.hiv_pos_care_perc_all.started_art_perc_child.started_art_perc_adult.started_art_perc_all.viral_test_perc_child.viral_test_perc_adult.viral_test_perc_all.ipt_done_perc_child.ipt_done_perc_adult.ipt_done_perc_all.tested_perc_child.tested_perc_adult.tested_perc_all.hiv_pos_yield_cmts.hiv_pos_care_cmts.started_art_cmts.viral_test_cmts.ipt_done_cmts.tested_cmts.viral_load_mothers_perc.eid_done_perc.pmtct_hiv_pos_perc.viral_load_mothers_cmts.eid_done_cmts.pmtct_hiv_pos_cmts
conn.pst1.setString(1,id);
conn.pst1.setString(2,indicator);
conn.pst1.setString(3,value);
conn.pst1.setString(4,mflcode);
conn.pst1.setString(5,reportingyear);
conn.pst1.setString(6,reportingmonth);
conn.pst1.setString(7,yearmonth);

if(conn.pst1.executeUpdate()==1){
error="success";
}
else {
    
    conn.pst2=conn.connect.prepareStatement(updateqr);   
//facilityname.startdate.enddate.hiv_pos_target_child.hiv_pos_target_adult.hiv_pos_target_total.hiv_pos_child.hiv_pos_adult.hiv_pos_total.new_care_child.new_care_adult.new_care_total.new_art_target_child.new_art_target_adult.new_art_target_total.started_art_child.started_art_adult.started_art_total.viral_load_target_child.viral_load_target_adult.viral_load_target_total.viral_load_done_child.viral_load_done_adult.viral_load_done_total.ipt_target_child.ipt_target_adult.ipt_target_total.ipt_child.ipt_adult.ipt_total.testing_target_child.testing_target_adult.testing_target_total.test_child.test_adult.test_total.pmtct_hiv_pos_target.pmtct_hiv_pos.eid_target.eid_done.viral_load_mothers_target.viral_load_mothers_done.user.hiv_pos_yield_perc_child.hiv_pos_yield_perc_adult.hiv_pos_yield_perc_all.hiv_pos_care_perc_child.hiv_pos_care_perc_adult.hiv_pos_care_perc_all.started_art_perc_child.started_art_perc_adult.started_art_perc_all.viral_test_perc_child.viral_test_perc_adult.viral_test_perc_all.ipt_done_perc_child.ipt_done_perc_adult.ipt_done_perc_all.tested_perc_child.tested_perc_adult.tested_perc_all.hiv_pos_yield_cmts.hiv_pos_care_cmts.started_art_cmts.viral_test_cmts.ipt_done_cmts.tested_cmts.viral_load_mothers_perc.eid_done_perc.pmtct_hiv_pos_perc.viral_load_mothers_cmts.eid_done_cmts.pmtct_hiv_pos_cmts

conn.pst1.setString(1,indicator);
conn.pst1.setString(2,value);
conn.pst1.setString(3,mflcode);
conn.pst1.setString(4,reportingyear);
conn.pst1.setString(5,reportingmonth);
conn.pst1.setString(6,yearmonth);

if(conn.pst2.executeUpdate()==1){
error="success";
} 

}

}

    }
    if(conn.st_3!=null){conn.st_3.close();}
     if(conn.st_1!=null){conn.st_1.close();}
     if(conn.st_2!=null){conn.st_2.close();}
     
     if(conn.rs!=null){conn.rs.close();}
     if(conn.rs1!=null){conn.rs1.close();}
     if(conn.rs2!=null){conn.rs2.close();}
     if(conn.connect!=null){conn.connect.close();}
    
     
System.out.println("error : "+error);
 out.println(error);
        } finally {
            out.close();
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
        Logger.getLogger(save.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(save.class.getName()).log(Level.SEVERE, null, ex);
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

}
