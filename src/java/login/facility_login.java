/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package login;

import General.IdGenerator2;
import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
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
public class facility_login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException, SQLException {
        
        HttpSession session;
String username,password,phone,mname,lname,userid,level,pass,fullname,status,nextPage,redirectpg = null,ipv4ad;
MessageDigest m;
String userAccess;
String active;
    

IdGenerator2 ig= new IdGenerator2();

      session=request.getSession();
          dbConn conn = new dbConn();
         //conn.st.executeUpdate("Set GLOBAL  max_connections=6000");
          
          username=password=phone=mname=lname=userid=level=pass=fullname=status=nextPage=active=ipv4ad="";
          userAccess=",";
          
           if(request.getParameter("ipv4ad")!=null){ ipv4ad=request.getParameter("ipv4ad").trim();}
           if(request.getParameter("username")!=null){ username=request.getParameter("username").trim();}
           if(request.getParameter("password")!=null){ pass=request.getParameter("password").trim();}
           if(request.getParameter("rd")!=null){ redirectpg=request.getParameter("rd").trim();}
          
	
            if(request.getParameter("ipv4ad")!=null){ ipv4ad=request.getParameter("ipv4ad").trim();}
          
		  
		  
		  
		  if(isMaxLimitsReached(conn, username, 5)){// denie user login
    
    
     status="<font color='red'><b>Failed:</b> You have entered incorrect Password too many times. Please Try login again after 5 minutes!.</font>";
             nextPage="index.jsp";
             session.setAttribute("login", status);
}

else {
    //proceed
        
		
		
		
      
         
         String email="";
         String usertype="";
          System.out.println("username : "+username+" password : "+pass);
          m = MessageDigest.getInstance("MD5");
       m.update(pass.getBytes(), 0, pass.length());
       password = new BigInteger(1, m.digest()).toString(16);
        System.out.println("facility_login username : "+username+" password : "+password);  
        String logger="SELECT userid,fullname,phone,email,"
       + "assigned_modules,cluster,level,active,usertype" 
                + " FROM internal_system.users_patient_level WHERE email=? && password=?" ;
        
        System.out.println("Logger:"+logger);
        conn.pst=conn.connect.prepareStatement(logger);
        conn.pst.setString(1, username);
        conn.pst.setString(2, password);
         conn.rs=conn.pst.executeQuery();
         if(conn.rs.next()==true){
             userid=conn.rs.getString(1);
             fullname=conn.rs.getString("fullname");
             phone=conn.rs.getString("phone");             
             level=conn.rs.getString("level");
             email=conn.rs.getString("email");
             active=conn.rs.getString("active");
             usertype=conn.rs.getString("usertype");
             
             
             session.setAttribute("userid", userid);
             session.setAttribute("fullname", fullname);
             session.setAttribute("level", level);
             session.setAttribute("phone", phone);             
             session.setAttribute("username", phone);
             
             
             
             HashMap<String, String> kd= new HashMap<>();

             kd.put("userid", userid);
             kd.put("fullname", fullname);
             kd.put("level", level);
             kd.put("phone", phone);            
             kd.put("mname", mname);
             kd.put("lname", lname);
             kd.put("email", email);
             kd.put("usertype", usertype);
             
           session.setAttribute("kd_session", kd);
           
           
           
            updateuserlogin(conn,username, ipv4ad ,session.getId(), 0,""+ig.timestamp());
           
         }
            
             
            
             
             
           session.setAttribute("userAccess", userAccess);  
          status="success"; 
          
          if(active.equals("0"))
          {
          nextPage="index.jsp";
          
          status="<font color='red'><b>Failed:</b> User account Not Activated. Please login.</font>";
           updateuserlogin(conn,username, ipv4ad ,session.getId(), 1,"");
         }
          
          else  if(active.equals("1"))
          {
              if(!redirectpg.equals(""))
              {
                  
                  nextPage=redirectpg;
                  
              } else {
          nextPage="clinicalhome.jsp";    
              }
          
         }
          else {
               updateuserlogin(conn,username, ipv4ad ,session.getId(), 1,"");
             status="<font color='red'><b>Failed:</b> Wrong username and password combination.</font>";
             nextPage="index.jsp";
             session.setAttribute("login", status);
                }
          
          
          
          
    }
          
          
          
          
         System.out.println("STATUS IS :  "+status);
        
         
         if(conn.rs!=null){
         conn.rs.close();
         }
         
         if(conn.pst!=null){
         conn.pst.close();
         }
          if(conn.connect!=null){
 conn.connect.close();
         }
         
         response.sendRedirect(nextPage);
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
    } catch (NoSuchAlgorithmException ex) {
        Logger.getLogger(facility_login.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(facility_login.class.getName()).log(Level.SEVERE, null, ex);
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
    } catch (NoSuchAlgorithmException ex) {
        Logger.getLogger(facility_login.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(facility_login.class.getName()).log(Level.SEVERE, null, ex);
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

    
    
    
      public void updateuserlogin(dbConn conn,String uname, String ipadress ,String sessionid, int wrong_attempt_no,String succesfullogin ) throws SQLException
   {
       
       
       IdGenerator2 ig= new IdGenerator2();
       
       String tm=ig.timestamp();
       
       
       String loginattemptqry=" select wrongattempts,lastsuccesfullogin from internal_system.loginlogs where username='"+uname+"'" ;
       
       int totalwrongattempts=0;
       int prev_attempt_no=0;
       String lastsuccesfullogin="";
       
       conn.rs4=conn.st4.executeQuery(loginattemptqry);
       
       if(conn.rs4.next())
       {
       prev_attempt_no=conn.rs4.getInt(1);
       lastsuccesfullogin=conn.rs4.getString(2);
       
       }
       
       
       if(succesfullogin.equals("")){succesfullogin=lastsuccesfullogin;}
       //if the current login is succesful, meaning wrong attempt is 0, user will be categorized as having no worng attempts, regardless of the previous wrong attempts
       
       if(wrong_attempt_no==0){totalwrongattempts=0;} 
       else 
       {           
           //increment previous wrong attempts by 1
           totalwrongattempts=prev_attempt_no+1;
           
       }
       String upd="replace into internal_system.loginlogs (username,wrongattempts,ipadress,lastsuccesfullogin,lastloginattempt) value (?,?,?,?,?)";
       
        conn.pst3=conn.connect.prepareStatement(upd);
        
        conn.pst3.setString(1,uname);
        conn.pst3.setString(2,""+totalwrongattempts);
        conn.pst3.setString(3,ipadress);
        conn.pst3.setString(4,succesfullogin);
        conn.pst3.setString(5,tm);
        
         conn.rs3=conn.pst3.executeQuery();
       
   
   } 
   
   
   public boolean isMaxLimitsReached(dbConn conn, String uname, int wrongattemptsthreshold) throws SQLException
   {
       IdGenerator2 ig= new IdGenerator2();
       
       
       boolean reachedlimits=false;
       
       String maxlimits="select wrongattempts, TIMESTAMPDIFF(MINUTE, substring(lastloginattempt,1,19), '"+ig.timestamp().substring(0, 19)+"') AS minutes from internal_system.loginlogs where username='"+uname+"' and wrongattempts >='"+wrongattemptsthreshold+"' and TIMESTAMPDIFF(MINUTE, lastloginattempt, '"+ig.timestamp()+"') <5 ";
       
       System.out.println("max Limit query: "+maxlimits);
       conn.rs=conn.st.executeQuery(maxlimits);
       
       if(conn.rs.next())
       {
           
         reachedlimits=true;  
           
       }
       
              
    return reachedlimits;
   
       
   
   }
   
}
