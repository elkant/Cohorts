/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockverification;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
public class loadComodities extends HttpServlet {

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
          
            dbConn conn = new dbConn();            
            
            
            out.println(toJson(pullIndicators(conn)));
            
            
        if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}
            
        } catch (SQLException ex) {
            Logger.getLogger(loadComodities.class.getName()).log(Level.SEVERE, null, ex);
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

    
    public JSONArray toJson(ResultSet res) throws SQLException{

    
    
JSONArray jo2 = new JSONArray();
int count=0;

while(res.next())
{
    
JSONObject jo = new JSONObject(); 

String id="";
String commodity="";
String packs="";
String category="";
String code="";
String line="";
String active="";


    id =res.getString("id");
    commodity =res.getString("commodity");
    packs =res.getString("packs");
    category =res.getString("category");
    code =res.getString("code");
    line =res.getString("line");
    active =res.getString("active");
   

    jo.put("id",id);
    jo.put("commodity",commodity);
    jo.put("packs",packs);
    jo.put("category",category);
    jo.put("code",code);
    jo.put("line",line);
    jo.put("active",active);
    
    jo2.put(jo);
    
    count++;
    
}


    
    
return jo2;    
}


    
    public ResultSet pullIndicators(dbConn conn) throws SQLException 
    {

String qry="select * from internal_system.stocks_commodities where active='1' ";
   
System.out.println(""+qry);

conn.rs=conn.st.executeQuery(qry);


return conn.rs;

   }
}
