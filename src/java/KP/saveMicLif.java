/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KP;

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
public class saveMicLif extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
          
            dbConn conn = new dbConn();
          String table="internal_system.micare_liftup_data";
          
          String mfl="";
          String sdp="";
          
          if(request.getParameter("facility")!=null){
          mfl=request.getParameter("facility");
          }
          if(request.getParameter("sdp")!=null){
          sdp=request.getParameter("sdp");
          }
          
          String sdpar[]=sdp.split("_");
          
//          if(request.getParameter("tbl")!=null){
//            table="internal_system."+request.getParameter("tbl");
//          }
           //id	yearmonth	facility	indicatorid	9m	9f	14m	14f	19m	19f	24m	24f	25m	25f	ttl	timestamp	lastupdated	userid

 String[] dataelementsarr= {"id","yearmonth","facility_id","indicator_id","sdp","_17","_19","_24","_29","_34","_39","_44","_49","_50","value","userid","_islocked"}; 
 String[] destelementsarr= {"id","yearmonth","facility_id","indicator_id","poptype","_17","_19","_24","_29","_34","_39","_44","_49","_50","value","userid","islocked"}; 
 //String[] orgunitsarr= {"county","`sub-county`"}; 

  //String[] dataelementsarr= {"id","date","facility","indicatorid","sdp","der_1f","der_1m","der_4f","der_4m","der_9f","der_9m","der_14f","der_14m","der_19f","der_19m","der_24f","der_24m","der_29f","der_29m","der_34f","der_34m","der_39f","der_39m","der_44f","der_44m","der_49f","der_49m","der_50f","der_50m","value","userid","date"}; 
 //String[] destelementsarr= {"id","date","mflcode","indicator_id","delivery_point","der_1f","der_1m","der_4f","der_4m","der_9f","der_9m","der_14f","der_14m","der_19f","der_19m","der_24f","der_24m","der_29f","der_29m","der_34f","der_34m","der_39f","der_39m","der_44f","der_44m","der_49f","der_49m","der_50f","der_50m","value","submitted_by","datefromfile"}; 
 
 
 ArrayList al= new ArrayList();
 
            
            
 String insertqr_parta= "replace into "+table+" (";  // finish with )
         String insertqr_partb= " values ("; // finish with )
 
for(int a=0;a<dataelementsarr.length;a++)
{

//build an inster qry
    if(a==dataelementsarr.length-1){
insertqr_parta+=destelementsarr[a]+"";
insertqr_partb+="?";
    }
    else {
    insertqr_parta+=destelementsarr[a]+",";
    insertqr_partb+="?,";
    }
}
//append orgunits
//for(int a=0;a<orgunitsarr.length;a++)
//{
////build an inster qry
//
//insertqr_parta+=","+orgunitsarr[a]+"";
//insertqr_partb+=",?";
//
//}
//last section
insertqr_parta+=")";
insertqr_partb+=")";



//append  

String insertqry=insertqr_parta+insertqr_partb;

            

    //conn.st_2.executeUpdate(updateqr);
    conn.pst1=conn.connect.prepareStatement(insertqry);   
//facilityname.startdate.enddate.hiv_pos_target_child.hiv_pos_target_adult.hiv_pos_target_total.hiv_pos_child.hiv_pos_adult.hiv_pos_total.new_care_child.new_care_adult.new_care_total.new_art_target_child.new_art_target_adult.new_art_target_total.started_art_child.started_art_adult.started_art_total.viral_load_target_child.viral_load_target_adult.viral_load_target_total.viral_load_done_child.viral_load_done_adult.viral_load_done_total.ipt_target_child.ipt_target_adult.ipt_target_total.ipt_child.ipt_adult.ipt_total.testing_target_child.testing_target_adult.testing_target_total.test_child.test_adult.test_total.pmtct_hiv_pos_target.pmtct_hiv_pos.eid_target.eid_done.viral_load_mothers_target.viral_load_mothers_done.user.hiv_pos_yield_perc_child.hiv_pos_yield_perc_adult.hiv_pos_yield_perc_all.hiv_pos_care_perc_child.hiv_pos_care_perc_adult.hiv_pos_care_perc_all.started_art_perc_child.started_art_perc_adult.started_art_perc_all.viral_test_perc_child.viral_test_perc_adult.viral_test_perc_all.ipt_done_perc_child.ipt_done_perc_adult.ipt_done_perc_all.tested_perc_child.tested_perc_adult.tested_perc_all.hiv_pos_yield_cmts.hiv_pos_care_cmts.started_art_cmts.viral_test_cmts.ipt_done_cmts.tested_cmts.viral_load_mothers_perc.eid_done_perc.pmtct_hiv_pos_perc.viral_load_mothers_cmts.eid_done_cmts.pmtct_hiv_pos_cmts

//System.out.println(""+conn.pst1);


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
////get orgunit values in array
//
//String[] org_unit_vl=getOrgunits(mfl,conn,orgunitsarr);
//
//for(int a=0;a<org_unit_vl.length;a++)
//{
//    System.out.println("__ updates 2"+org_unit_vl[a]);
//    
//conn.pst1.setString(rowcount,org_unit_vl[a]);
//
//rowcount++;
//
//}


//______________________________________________________________________________________



            System.out.println("Query ni :"+conn.pst1);
if(conn.pst1.executeUpdate()==1)
{
   out.println("Micare data Saved succesfully ");
}
else {
 out.println(" Data Not saved ");
    
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
            Logger.getLogger(saveMicLif.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(saveMicLif.class.getName()).log(Level.SEVERE, null, ex);
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


public String[] getOrgunits(String mfl, dbConn conn,String [] orgunitsarr) throws SQLException
{
String orgcols=arraytostring(orgunitsarr);
    
    String qr="select "+orgcols+" from internal_system.orgunits_vw where mflcode='"+mfl+"'";

    conn.rs1=conn.st1.executeQuery(qr);
    
   String [] orgunit_values= new String [orgunitsarr.length]; 
        int columnCount = orgunitsarr.length;

       
    
    while(conn.rs1.next())
    {
      
    for (int i = 1; i <= columnCount; i++) 
               {
 orgunit_values[i-1]=conn.rs1.getString(i);
 
                  // System.out.println("   variables "+conn.rs1.getString(i));
 
               //create row header
                }
    
    
    }



return orgunit_values;
}


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
