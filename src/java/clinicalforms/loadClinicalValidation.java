/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicalforms;

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author EKaunda
 */
public class loadClinicalValidation extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
         
                        
            
            JSONArray arr= new JSONArray();
            
            
            String scid="";
            String fm="";
            String indics_table="";
            
            if(request.getParameter("scid")!=null){   scid=request.getParameter("scid");}
            if(request.getParameter("fm")!=null){   fm=request.getParameter("fm");}
            if(request.getParameter("tbl")!=null){   indics_table=request.getParameter("tbl");}
            
            String validation="";
            
            dbConn conn = new dbConn();
            
            String getList="select * from internal_system.clinical_form_validation where is_active='1' and form='"+fm+"' and table_name='"+indics_table+"'  ";
            
            
            conn.rs=conn.st.executeQuery(getList);
            
            while(conn.rs.next()){
                
                JSONObject obj=new JSONObject();
                
            //(valids, message, iscritical, sectionid)
            
            
            
            obj.put("validation", conn.rs.getString("return_false_if"));
            obj.put("message", conn.rs.getString("message"));
            obj.put("id", conn.rs.getString("id"));
            obj.put("section_name", conn.rs.getString("form"));
            obj.put("highlight_fields", conn.rs.getString("highlight_fields"));
            obj.put("iscritical","1");
            
           arr.add(obj);
            
            
            }
            
          
            if(conn.rs!=null){conn.rs.close();}
            if(conn.st!=null){conn.st.close();}
            
            
            if(!arr.isEmpty()){
            out.println(arr);
            }
            else{
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(loadClinicalValidation.class.getName()).log(Level.SEVERE, null, ex);
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
