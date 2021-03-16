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
import java.util.ArrayList;
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
public class multisave extends HttpServlet {
HttpSession session;


String error;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String columnName,cohortmonths,yr,mn,ct,facil,mflcode = null,tablename;
           
            columnName=cohortmonths=yr=mn=ct=facil=tablename="";
            
           session=request.getSession();
           
           dbConn conn = new dbConn();
           
 
  
       
        
        ct=request.getParameter("cohortttype");
        yr=request.getParameter("year");
        mn=request.getParameter("month");
        cohortmonths=request.getParameter("cohortmonth");
        facil=request.getParameter("facilityname");//split this 
        if(!request.getParameter("facilityname").equals("")){
        mflcode=facil.split("_")[0];
        }
        
       session.setAttribute("ct", ct);
       session.setAttribute("mn", mn);
       session.setAttribute("yr", yr);
       session.setAttribute("facil", facil);
        
        
        
            //System.out.println("mflcode is:"+mflcode);
        
      
        if(ct.equalsIgnoreCase("art")){tablename="art_cohort";} 
        else if(ct.equalsIgnoreCase("pmtct")){tablename="pmtct_cohort";}
        else {tablename="";}
        
   

//    year="";
    if(tablename.equals("") || facil.equals("")){error="<font color=\"red\">ERROR : Please select all the filter parameters .</font>";}
    else{
        error="success";
    String indicator="";
    
   
    String yearmonth="";
    
  
    //id=yearstring+"_"+monthstring+"_"+mflcode+"_"+indicatorid;
    
    //
     //String artparameters[]={"1_adult","1_child","1_tl","2_adult","2_child","2_tl","3_adult","3_child","3_tl","4_adult","4_child","4_tl","5_adult","5_child","5_tl","6_adult","6_child","6_tl","7_adult","7_child","7_tl","8_adult","8_child","8_tl","9_adult","9_child","9_tl","10_adult","10_child","10_tl","11_adult","11_child","11_tl","12_adult","12_child","12_tl"};
   //String pmtctparameters[]={"1_kp","1_np","1_tl","2_kp","2_np","2_tl","3_kp","3_np","3_tl","4_kp","4_np","4_tl","5_kp","5_np","5_tl","6_kp","6_np","6_tl","7_kp","7_np","7_tl","8_kp","8_np","8_tl","9_kp","9_np","9_tl","10_kp","10_np","10_tl","11_kp","11_np","11_tl","12_kp","12_np","12_tl"};
   
    //note art indicators start from 13 to 23
     String artparameters[]={"1_adult","1_child","1_tl","2_adult","2_child","2_tl","3_adult","3_child","3_tl","4_adult","4_child","4_tl","5_adult","5_child","5_tl","6_adult","6_child","6_tl","7_adult","7_child","7_tl","8_adult","8_child","8_tl","9_adult","9_child","9_tl","10_adult","10_child","10_tl","11_adult","11_child","11_tl"};
     String artparameters2[]={"13_adult","13_child","13_tl","14_adult","14_child","14_tl","15_adult","15_child","15_tl","16_adult","16_child","16_tl","17_adult","17_child","17_tl","18_adult","18_child","18_tl","19_adult","19_child","19_tl","20_adult","20_child","20_tl","21_adult","21_child","21_tl","22_adult","22_child","22_tl","23_adult","23_child","23_tl"};
   String pmtctparameters[]={"1_kp","1_np","1_tl","2_kp","2_np","2_tl","3_kp","3_np","3_tl","4_kp","4_np","4_tl","5_kp","5_np","5_tl","6_kp","6_np","6_tl","7_kp","7_np","7_tl","8_kp","8_np","8_tl","9_kp","9_np","9_tl","10_kp","10_np","10_tl","11_kp","11_np","11_tl"};
    
ArrayList artvals=new ArrayList();
ArrayList pmtctvals=new ArrayList();
if(tablename.equals("art_cohort")){
for(int a=0;a<artparameters.length;a++){
  String val="0";
  if( !request.getParameter(artparameters[a]).equals("")){
      val=request.getParameter(artparameters[a]);
      
  }
  else {
      val="0";
  }
artvals.add(val);

//further processing here
insertorUpdateData(val,tablename,artparameters[a].split("_")[1]+"_"+cohortmonths,conn, yr, mn, mflcode,artparameters2[a].split("_")[0]);
}
}
else if(tablename.equals("pmtct_cohort")) {

    
    for(int a=0;a<pmtctparameters.length;a++){
  String val="0";
  if( !request.getParameter(pmtctparameters[a]).equals("")){
      val=request.getParameter(pmtctparameters[a]);
      
  }
  else {
      val="0";
  }
pmtctvals.add(val);

//further processing here
insertorUpdateData(val,tablename,pmtctparameters[a].split("_")[1]+"_"+cohortmonths,conn, yr, mn, mflcode,pmtctparameters[a].split("_")[0]);

}
    
    
}
}

    
  
     
     if(conn.rs!=null){conn.rs.close();}
     if(conn.rs1!=null){conn.rs1.close();}
     if(conn.rs2!=null){conn.rs2.close();}
     if(conn.st_3!=null){conn.st_3.close();}
     if(conn.st_1!=null){conn.st_1.close();}
     if(conn.st_2!=null){conn.st_2.close();}
     if(conn.connect!=null){conn.connect.close();}
    
     
System.out.println("error : "+error);
 out.println(error);
  response.sendRedirect("dataentry.jsp");
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

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    public String insertorUpdateData(String value,String tablename,String columnName,dbConn conn,String reportingyear,String reportingmonth,String mflcode,String indicator){
    
    
    try {
        reportingyear=reportingyear;
        reportingmonth=reportingmonth;
        mflcode=mflcode;
        indicator=indicator;
        String yearmonth=reportingyear+reportingmonth;
        String id=reportingyear+"_"+reportingmonth+"_"+mflcode+"_"+indicator;
        
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
        
    } catch (SQLException ex) {
        Logger.getLogger(multisave.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "done";
    }
}
