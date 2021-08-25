/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockverification;

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
 * @author Administrator
 */
public class loadSavedData extends HttpServlet 

{
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
           
            
            dbConn conn= new dbConn();
            
            //be default if no id provided, print the list of all entries
            if(request.getParameter("id")!=null){
                String id=request.getParameter("id");
                out.println(getEnteredStockDataById(conn,id));
            }
            else 
            {
            out.println(getEnteredStockData(conn));
            }
           
          
        if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}
            
           
            
            
        } catch (SQLException ex) {
            Logger.getLogger(loadSavedData.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    public JSONObject getEnteredStockDataById(dbConn conn, String id) throws SQLException{
    
    String qry=" select * from internal_system.stocks_data sd left join internal_system.stocks_commodities sc on sd.commodity= sc.id left join internal_system.subpartnera sa on sa.SubPartnerID = sd.facility where sd.id='"+id+"'  ";
            
            conn.rs= conn.st.executeQuery(qry);
            
      
            JSONObject jo1 = new JSONObject();
            JSONArray ja = new JSONArray();
            while(conn.rs.next())
            {
                  JSONObject jo = new JSONObject();
            jo.put("id", conn.rs.getString("sd.id"));            
            jo.put("facility", conn.rs.getString("facility"));         
            jo.put("commodity", conn.rs.getString("sd.commodity"));
            jo.put("packsize", conn.rs.getString("sd.packsize"));
            jo.put("delnoteno", conn.rs.getString("delnoteno"));
            jo.put("batchno", conn.rs.getString("batchno"));
            jo.put("delnoteqty", conn.rs.getString("delnoteqty"));
            jo.put("qtyrec", conn.rs.getString("qtyrec"));
            jo.put("daterec", conn.rs.getString("daterec"));
            jo.put("expdate", conn.rs.getString("expdate"));
            jo.put("cmts", conn.rs.getString("cmts"));
            jo.put("timestamp", conn.rs.getString("timestamp"));
            jo.put("contacts", conn.rs.getString("contacts"));
            jo.put("docdate", conn.rs.getString("docdate"));
            jo.put("contact_name", conn.rs.getString("contact_name"));
            jo.put("facilityname", conn.rs.getString("SubPartnerNom"));
            jo.put("code", conn.rs.getString("sc.code"));  
            jo.put("reportingdate", conn.rs.getString("reportingdate"));  
            
              ja.put(jo);
            
            }
    
    jo1.put("commodity", ja);
    return jo1;
            
    
    
    }
    

        public JSONObject getEnteredStockData(dbConn conn) throws SQLException{
    
    String qry=" select ifnull(reportingdate,'') as reportingdate, sd.id as id, sd.facility as facility, sa.SubPartnerNom as SubPartnerNom,batchno  , contacts , ifnull(contact_name,'Not indicated') as contact_name ,sc.code as code,sd.packsize as packsize, delnoteno, qtyrec from internal_system.stocks_data sd left join internal_system.stocks_commodities sc on sd.commodity= sc.id left join internal_system.subpartnera sa on sa.SubPartnerID = sd.facility   ";
            
            conn.rs= conn.st.executeQuery(qry);
            
            
            JSONObject jo1 = new JSONObject();
             JSONArray ja = new JSONArray();
            while(conn.rs.next())
            {
                JSONObject jo = new JSONObject();
            jo.put("id", conn.rs.getString("id"));            
            jo.put("facility", conn.rs.getString("facility"));
            jo.put("facilityname", conn.rs.getString("SubPartnerNom"));
            jo.put("contact_name", conn.rs.getString("contact_name"));
            jo.put("contact", conn.rs.getString("contact_name"));
            jo.put("commodity", conn.rs.getString("code")+", "+conn.rs.getString("packsize"));
            jo.put("delnoteno", conn.rs.getString("delnoteno"));
            jo.put("qtyrec", conn.rs.getString("qtyrec"));
            jo.put("batchno", conn.rs.getString("batchno"));
            jo.put("reportingdate", conn.rs.getString("reportingdate"));
         
            
            
                ja.put(jo);
            
                        
            
            }
    
    jo1.put("commodity",ja);
    return jo1;
    
    }
   
    
}
