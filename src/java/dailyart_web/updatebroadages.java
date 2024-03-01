/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailyart_web;

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
 * @author Emmanuel Kaunda
 */
public class updatebroadages extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            
dbConn conn= new dbConn();

String qr="select id, "
        + "SUM(IFNULL(der_1f,0)) as der_1f,SUM(IFNULL(der_4f,0)) as der_4f,SUM(IFNULL(der_9f,0)) as der_9f,SUM(IFNULL(der_14f,0)) as der_14f,\n" +
"SUM(IFNULL(der_1m,0)) as der_1m,SUM(IFNULL(der_4m,0)) as der_4m,SUM(IFNULL(der_9m,0)) as der_9m,SUM(IFNULL(der_14m,0)) as der_14m,\n" +
"SUM(IFNULL(der_19f,0)) as der_19f,SUM(IFNULL(der_24f,0)) as der_24f,SUM(IFNULL(der_29f,0)) as der_29f,SUM(IFNULL(der_34f,0)) as der_34f,SUM(IFNULL(der_39f,0)) as der_39f,SUM(IFNULL(der_44f,0)) as der_44f,SUM(IFNULL(der_49f,0)) as der_49f,SUM(IFNULL(der_50f,0)) as der_50f,\n" +
"SUM(IFNULL(der_19m,0)) as der_19m,SUM(IFNULL(der_24m,0)) as der_24m,SUM(IFNULL(der_29m,0)) as der_29m,SUM(IFNULL(der_34m,0)) as der_34m,SUM(IFNULL(der_39m,0)) as der_39m,SUM(IFNULL(der_44m,0)) as der_44m,SUM(IFNULL(der_49m,0)) as der_49m,SUM(IFNULL(der_50m,0)) as der_50m from der_rri.der_table_age where `date`  between '2023-12-22' and '2023-12-25'  group by id ";
int rw=0;

            System.out.println(qr+";");
conn.rs=conn.st.executeQuery(qr);

while(conn.rs.next()){
rw++;
    
int f14=0;
int m14=0;
int f15=0;
int m15=0;
String id= conn.rs.getString("id");


f14	=conn.rs.getInt("der_1f")	+ conn.rs.getInt("der_4f")	+ conn.rs.getInt("der_9f")	+ conn.rs.getInt("der_14f");					
m14=	conn.rs.getInt("der_1m")	+ conn.rs.getInt("der_4m")	+ conn.rs.getInt("der_9m")	+ conn.rs.getInt("der_14m");					
f15	=conn.rs.getInt("der_19f")	+ conn.rs.getInt("der_24f")	+ conn.rs.getInt("der_29f")	+ conn.rs.getInt("der_34f")	+ conn.rs.getInt("der_39f")	+ conn.rs.getInt("der_44f")	+ conn.rs.getInt("der_49f")	+ conn.rs.getInt("der_50f")	;
m15=	conn.rs.getInt("der_19m")	+ conn.rs.getInt("der_24m")	+ conn.rs.getInt("der_29m")	+ conn.rs.getInt("der_34m")	+ conn.rs.getInt("der_39m")	+ conn.rs.getInt("der_44m")	+ conn.rs.getInt("der_49m")	+ conn.rs.getInt("der_50m")	;

String uq="update der_rri.der_table_age set f14='"+f14+"',m14='"+m14+"',f15='"+f15+"',m15='"+m15+"' where id='"+id+"';"; 
    System.out.println(rw+"::"+uq);
conn.st1.executeUpdate(uq);




}


 if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}

            out.println("Finished updating");
        } catch (SQLException ex) {
            Logger.getLogger(updatebroadages.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
