/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package KP;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class kp_login extends HttpServlet {
HttpSession session;
String username,password,fname,mname,lname,userid,level,pass,fullname,status,nextPage,lip;
MessageDigest m;
String userAccess;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException, SQLException {
      session=request.getSession();
          dbConn conn = new dbConn();
         //conn.st.executeUpdate("Set GLOBAL  max_connections=6000");
          
          username=password=fname=mname=lname=userid=level=pass=fullname=status=nextPage="";
          userAccess=",";
          username=request.getParameter("username").trim();
          pass=request.getParameter("pass").trim();
         
          System.out.println("username : "+username+" password : "+pass);
          m = MessageDigest.getInstance("MD5");
       m.update(pass.getBytes(), 0, pass.length());
       password = new BigInteger(1, m.digest()).toString(16);
        System.out.println("username : "+username+" password : "+password);  
        String logger="SELECT userid,fname,mname,lname,level,lip FROM internal_system.kp_user WHERE username=? && password=?" ;
        conn.pst=conn.connect.prepareStatement(logger);
        conn.pst.setString(1, username);
        conn.pst.setString(2, password);
         conn.rs=conn.pst.executeQuery();
         if(conn.rs.next()==true){
             userid=conn.rs.getString(1);
             fname=conn.rs.getString(2);
             mname=conn.rs.getString(3);
             lname=conn.rs.getString(4);
             level=conn.rs.getString(5);
             lip=conn.rs.getString(6);
             fullname=fname+" "+mname+" "+lname;
             session.setAttribute("userid", userid);
             session.setAttribute("fullname", fullname);
             session.setAttribute("level", level);
             
             session.setAttribute("username", username);
             session.setAttribute("fname", fname);
             session.setAttribute("mname", mname);
             session.setAttribute("lname", lname);
             session.setAttribute("lip", lip);
             session.setAttribute("liplist", "<option value='"+lip+"'>"+lip+"</option>");
             
            
             
           //session.setAttribute("userAccess", userAccess);  
          status="success"; 
          nextPage="kp_main.jsp";
          
          //System.out.println("access rights : "+session.getAttribute("userAccess"));
         }
         else{
             status="<font color=\"red\"><b>Failed:</b> Wrong username and password combination.</font>";
             nextPage="kp_index.jsp";
             session.setAttribute("login", status);
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
        Logger.getLogger(kp_login.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(kp_login.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(kp_login.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(kp_login.class.getName()).log(Level.SEVERE, null, ex);
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
