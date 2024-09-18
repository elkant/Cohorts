/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmtct_ovc;

import DHIS2.dhisconfig;
import General.IdGenerator;
import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
public class registeruser extends HttpServlet {

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
            throws ServletException, IOException, SQLException, MessagingException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
 HttpSession session= request.getSession(true);
          
          
                      dbConn conn = new dbConn();
          String table=" internal_system.users_patient_level ";
          
          String mfl="";
          String mdl="";
          String eml="";
          String fn="";
          String ui="";
          String ut="";
          
          if(request.getParameterValues("facility_id")!=null){
          mfl=ArrayToString(request.getParameterValues("facility_id"));
          
          }
          if(request.getParameter("assigned_modules")!=null){
          mdl=request.getParameter("assigned_modules");
          
          }
          if(request.getParameter("email")!=null){
          eml=request.getParameter("email");
          
          }
          if(request.getParameter("fullname")!=null){
          fn=request.getParameter("fullname");
          
          }
          if(request.getParameter("userid")!=null){
          ui=request.getParameter("userid");
          
          }
          if(request.getParameter("usertype")!=null){
          ut=request.getParameter("usertype");
          
          }
          
         
            
 String[] dataelementsarr= {"userid","fullname","phone","password","email","facility_id","level","assigned_modules","usertype"};
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
    else if(request.getParameterValues(""+dataelementsarr[a])!=null)
    {
    data=ArrayToString(request.getParameterValues(""+dataelementsarr[a]));
    }
    
    
    
conn.pst1.setString(rowcount,data);

rowcount++;


}



//______________________________________________________________________________________

String rtn="Account Created successfully. Please check your email in the next 5 minutes for approval status and login Details";


if(conn.pst1.executeUpdate()==1)
{
   //out.println("Data Saved Successfully");
   rtn="Account Created successfully. Please check your email in the next 5 minutes for approval status and login Details";
}
else 
{
 //out.println(" Data Not successfully saved ");

}
  String fname=getFacilityNames(conn,mfl);
  
            SendApprovalRequest(fname, "1",  mdl, "emaingi@usaidtujengejamii.org", fn, ui);

        if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}

        session.setAttribute("pmtct_ovc_register",rtn);
            response.sendRedirect("clinical_registration.jsp");
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
        } catch (MessagingException ex) {
            Logger.getLogger(registeruser.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (MessagingException ex) {
            Logger.getLogger(registeruser.class.getName()).log(Level.SEVERE, null, ex);
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
        
     


 private void SendApprovalRequest(String facility, String status,  String module, String email,String username,String userid) throws MessagingException {
        String toAddress = "";


        dhisconfig dc = new dhisconfig();
        String stat="";
        
        if(!status.equals("")){stat=" Below are the upload results:\n "+status;}

        IdGenerator gn = new IdGenerator();

        String textBody = "Hi "+username+",\n A new user by the name  "+username+" has requested to access "+module+" within IMIS to allow him/her make data records under facilities  " + facility + " on Date "+ gn.toDay() + " .\n"
                
                + "\n \n To Approve the request, click the link https://usaidtujengejamii.org:8443/Cohorts/activateUser?ui="+userid+"&st=1 "
                
                
                + "\n \n To Reject the request, click the link https://usaidtujengejamii.org:8443/Cohorts/activateUser?ui="+userid+"&st=2 "
                
                + "\n  \n *******This is a system autogenerated message*****";
        toAddress = email;
       //String host = "smtp.gmail.com";
        // String Password = dc.getGmail_pass();
        //String from = dc.getGmail_account();
       
        String host = "smtp.office365.com";        
        String Password = dc.getUtj_pass();
        String from = dc.getUtj_email();
       
        // toAddress = "aphiapluschwsattendance@gmail.com";  filled above...

        //Get system properties
        Properties props = System.getProperties();

        props.put("mail.smtps.auth", "true"); // Enable authentication
        //props.put("mail.smtp.auth", "true");
       
        props.put("mail.smtp.host", "smtp.office365.com"); // Microsoft 365 SMTP server
        props.put("mail.smtp.port", "587"); // TLS port
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        
             
        Session session = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        message.setRecipients(Message.RecipientType.TO, toAddress);

        message.setSubject("A new User "+username + " Has requested to Access  " + module);

        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setText(textBody);

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        ///messageBodyPart = new MimeBodyPart();

      
        //messageBodyPart.setFileName("Form1a_"+facility+"_"+yearmonth+".xlsx");
        //messageBodyPart.setFileName(finalfilename);
        //multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        try {
            javax.mail.Transport tr = session.getTransport("smtp");
            tr.connect(host, from, Password);
            tr.sendMessage(message, message.getAllRecipients());
           
 session.setDebug(true);  
            tr.close();

        } catch (SendFailedException sfe) {

            System.out.println(sfe);
        }
    }


 
 private String getFacilityNames(dbConn conn, String ids){
      String facs="";
        try {
           
            
            StringBuffer sb= new StringBuffer(ids);
//invoking the method
sb.deleteCharAt(sb.length()-1);

String qry="select group_concat(SubPartnerNom) as facility from internal_system.subpartnera where SubPartnerID in ("+sb+")";

conn.rs_3=conn.st.executeQuery(qry);

while(conn.rs_3.next())
{
    
facs+=conn.rs_3.getString(1);
}


        } catch (SQLException ex) {
            Logger.getLogger(registeruser.class.getName()).log(Level.SEVERE, null, ex);
        }
 
 
 return facs;

}
}