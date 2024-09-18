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
public class activateUser extends HttpServlet {

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
        
        
        HttpSession sess= request.getSession();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
 
          
          
                      dbConn conn = new dbConn();
          String table=" internal_system.users_patient_level ";
          
          String status="";
          String userid="";
          
          if(request.getParameter("st")!=null){
          status=request.getParameter("st");
          
          }
          if(request.getParameter("ui")!=null){
          userid=request.getParameter("ui");
          
          }
          
         String getdetails="select userid,fullname,email,assigned_modules from `internal_system`.`users_patient_level` where userid='"+userid+"' ";
          
         
         conn.rs1=conn.st1.executeQuery(getdetails);
         
         
         String un="";
         String em="";
         String mdls="";
         while(conn.rs1.next()){
         un=conn.rs1.getString("fullname");
         em=conn.rs1.getString("email");
         mdls=conn.rs1.getString("assigned_modules");
         }
         
          String qry="update `internal_system`.`users_patient_level` set active='"+status+"' where userid='"+userid+"';";
            conn.st3.executeUpdate(qry);
            System.out.println("Update Status:"+qry);
           
                System.out.println("Content is module:"+mdls+",Email:"+em+",Username:"+un+",userid:"+userid+",status:"+status);
                SendApprovalRequest(mdls, em,un,userid,status);
                
            
    
    
      String msg="User Account approved succesfully!";
    
    if(status.equals("2")){msg="User Account Rejected !";} 
  
      

       
    
    
    
    //send a notification to the user to let them know that the account has now been activated
//facilityname.startdate.enddate.hiv_pos_target_child.hiv_pos_target_adult.hiv_pos_target_total.hiv_pos_child.hiv_pos_adult.hiv_pos_total.new_care_child.new_care_adult.new_care_total.new_art_target_child.new_art_target_adult.new_art_target_total.started_art_child.started_art_adult.started_art_total.viral_load_target_child.viral_load_target_adult.viral_load_target_total.viral_load_done_child.viral_load_done_adult.viral_load_done_total.ipt_target_child.ipt_target_adult.ipt_target_total.ipt_child.ipt_adult.ipt_total.testing_target_child.testing_target_adult.testing_target_total.test_child.test_adult.test_total.pmtct_hiv_pos_target.pmtct_hiv_pos.eid_target.eid_done.viral_load_mothers_target.viral_load_mothers_done.user.hiv_pos_yield_perc_child.hiv_pos_yield_perc_adult.hiv_pos_yield_perc_all.hiv_pos_care_perc_child.hiv_pos_care_perc_adult.hiv_pos_care_perc_all.started_art_perc_child.started_art_perc_adult.started_art_perc_all.viral_test_perc_child.viral_test_perc_adult.viral_test_perc_all.ipt_done_perc_child.ipt_done_perc_adult.ipt_done_perc_all.tested_perc_child.tested_perc_adult.tested_perc_all.hiv_pos_yield_cmts.hiv_pos_care_cmts.started_art_cmts.viral_test_cmts.ipt_done_cmts.tested_cmts.viral_load_mothers_perc.eid_done_perc.pmtct_hiv_pos_perc.viral_load_mothers_cmts.eid_done_cmts.pmtct_hiv_pos_cmts


  

        if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}

           sess.setAttribute("user_approval",msg);
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
            Logger.getLogger(activateUser.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(activateUser.class.getName()).log(Level.SEVERE, null, ex);
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


    
    private void SendApprovalRequest1(String module, String email,String username,String userid, String accessstatus) throws MessagingException {
        String toAddress = "";

        dhisconfig dc = new dhisconfig();
        

        String stat="";
        
    String access_word="approved by IMIS System Admin!";

        IdGenerator gn = new IdGenerator();
        String detailedbody=" To login into the system, please enter your email address and password 12345 to login into the system. \n Remember to change the password after login";
        if(accessstatus.equals("2"))
        {
            access_word="rejected by IMIS System Admininstrator";
        detailedbody="If you feel like this action was unexpected and should be reconsidered, Please reach out to the USAID Tujenge Jamii Data Management team for further support.";
            
        }

        String textBody = "Hi "+username+",\n your request to access IMIS has been "+access_word+" on Date "+ gn.toDay() + " .\n"
                + "\n  \n "+detailedbody
                + "\n \n *******This is a system autogenerated message*****";
        
        
        toAddress = email;
        String host = "smtp.office365.com";
        String Password = dc.getGmail_pass();
        String from = dc.getGmail_account();
        
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

        message.setSubject(module.toUpperCase()+" Account Approval status");

       BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setText(textBody);

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        ///messageBodyPart = new MimeBodyPart();

      
        //messageBodyPart.setFileName("Form1a_"+facility+"_"+yearmonth+".xlsx");
        //messageBodyPart.setFileName(finalfilename);
        //multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

      
            javax.mail.Transport tr = session.getTransport("smtps");
            tr.connect(host, from, Password);
            tr.sendMessage(message, message.getAllRecipients());

            tr.close();

       
    }
    
     private void SendApprovalRequest(String module, String email, String username, String accessstatus, String status) throws MessagingException {
        String toAddress = "";
    String access_word="approved by IMIS System Admin!";

        dhisconfig dc = new dhisconfig();
        String stat="";
  
        IdGenerator gn = new IdGenerator();

        String detailedbody=" To login into the system, use your email address and password 12345 to login into the system .\n Please Remember to change the password after login";
        if(accessstatus.equals("2"))
        {
            access_word="rejected by IMIS System Admininstrator";
        detailedbody="If you feel like this action was unexpected and should be reconsidered, Please reach out to the USAID Tujenge Jamii Data Management team for further support.";
            
        }

        String textBody = "Hi "+username+",\n your request to access IMIS has been "+access_word+" on Date "+ gn.toDay() + " .\n"
                + "\n  \n "+detailedbody
                + "\n \n *******This is a system autogenerated message*****";
        
        toAddress = email;
       String host = "smtp.office365.com";        
        String Password = dc.getUtj_pass();
        String from = dc.getUtj_email();
        // toAddress = "aphiapluschwsattendance@gmail.com";  filled above...

        //Get system properties
        Properties props = System.getProperties();
       props.put("mail.smtps.auth", "true"); // Enable authentication
              
       props.put("mail.smtp.host", "smtp.office365.com"); // Microsoft 365 SMTP server
        props.put("mail.smtp.port", "587"); // TLS port
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        Session session = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        message.setRecipients(Message.RecipientType.TO, toAddress);

        message.setSubject(module.toUpperCase()+" Account Approval status");

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

            tr.close();

        } catch (SendFailedException sfe) {

            System.out.println(sfe);
        }
    }




        
        
}
