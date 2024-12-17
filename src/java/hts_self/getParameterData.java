/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hts_self;

import General.IdGenerator;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author EKaunda
 */
public class getParameterData extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            dbConn conn = new dbConn();
            
            
            if(request.getParameter("mod")!=null && request.getParameter("sec")!=null)
            {
                
                String sect= request.getParameter("sec");
                
            out.println(getModalities(conn,sect));
            
            }
           
            
            else   if(request.getParameter("per")!=null){
            
             out.println(getYearMonths(conn));
            }
            
             else   {
            
             out.println(getSections(conn));
            }
          
            
            
            if(conn.rs!=null){conn.rs.close();}
            if(conn.st!=null){conn.st.close();}
            if(conn.connect!=null){conn.connect.close();}
            
            
        } catch (SQLException ex) {
            Logger.getLogger(getParameterData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
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
    
    
    public JSONObject getSections(dbConn conn) throws SQLException{
    
        JSONArray armain = new JSONArray();
        JSONObject jomain = new JSONObject();
        
        
        String qry="select distinct(section) as sec, section_id as secid from internal_system.htsself_modalities";
        
        
        conn.rs= conn.st.executeQuery(qry);
        
        while(conn.rs.next()){
            
          JSONObject jo = new JSONObject();
            jo.put("sectio", conn.rs.getString("sec"));
            jo.put("sectio_id", conn.rs.getString("secid"));
        armain.put(jo);
        }
        
    jomain.put("sections",armain);
        
    return jomain;
    
    }
    
      
    
      public JSONObject getModalities(dbConn conn,String section) throws SQLException{
    
        JSONArray armain = new JSONArray();
        JSONObject jomain = new JSONObject();
        
        
        String qry="select * from internal_system.htsself_modalities where section_id='"+section+"' ";
        
        
        conn.rs= conn.st.executeQuery(qry);
        
        while(conn.rs.next()){
            
          JSONObject jo = new JSONObject();
            jo.put("sectio", conn.rs.getString("section"));
            jo.put("sectio_id", conn.rs.getString("section_id"));
            jo.put("modality", conn.rs.getString("modality"));
            jo.put("modality_id", conn.rs.getString("modality_id"));
        armain.put(jo);
        }
        
    jomain.put("modalities",armain);
        
    return jomain;
    
    }
    
      
      
      
         public JSONObject getYearMonths(dbConn conn) throws SQLException{
    
        JSONArray armain = new JSONArray();
        JSONObject jomain = new JSONObject();
        
        
             IdGenerator ig= new IdGenerator();
        
        
        String qry="select * from internal_system.htsself_period where id <="+ig.CurrentYearMonth()+" order by id desc limit 14";
        
             System.out.println(qry);
        
        conn.rs= conn.st.executeQuery(qry);
        
        while(conn.rs.next()){
            
          JSONObject jo = new JSONObject();
            jo.put("id", conn.rs.getString("id"));
            jo.put("year", conn.rs.getString("year"));
            jo.put("month", conn.rs.getString("month"));
          
        armain.put(jo);
        }
        
    jomain.put("periods",armain);
        
    return jomain;
    
    }
    

}
