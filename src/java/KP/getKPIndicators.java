/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KP;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author EKaunda
 */
public class getKPIndicators extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            String dt="";
            
            String fc="";
          
            
            if(request.getParameter("fc")!=null){fc=request.getParameter("fc");}
            if(request.getParameter("dt")!=null){dt=request.getParameter("dt");}
            
            
            
            
           dbConn conn = new dbConn();
           if(!fc.equals("")&&!dt.equals("")){
           //return tables
           out.println(getHtmlTable(conn,dt,fc));
           
           }
           else {
           //return indicators
           ResultSet r=pullIndicators(conn);
           
           out.println(toJson(r));
          
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
            Logger.getLogger(getKPIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getKPIndicators.class.getName()).log(Level.SEVERE, null, ex);
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

public String getHtmlTable(dbConn conn, String reportingdate, String facility) throws SQLException{
String indicators="<thead><tr><th>Indicator</th><th>FSW</th><th>MSM</th></tr></thead><tbody>";

    

 JSONObject jo= getData(conn, reportingdate, facility);
    
int count=1;
ResultSet r=pullIndicators(conn);

while(r.next()){
   
//  System.out.println("__"+jo.toString());  
    String indic=r.getString("indicatorname");
    String id=r.getString("id");
    
    String msm="";
    String fsw="";
    
     try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        
   if(jo.get(id)!=null){
        
       JSONObject joage=(JSONObject) jo.get(id);
         fsw=joage.get("fsw").toString();
         msm=joage.get("msm").toString();
         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     String readonly_msm="";
     String readonly_fsw="";
     
     
//     if(id.contains("VMMC")){
//       fsw="readonly='true' style='background-color:#bcc6cc;'  tabindex='-1'";
//       readonly_b15_f="readonly='true' style='background-color:#bcc6cc;' tabindex='-1'";
//     }
//     else  if(id.contains("PMTCT")){
//       readonly_ab15_m="readonly='true' style='background-color:#bcc6cc;'  tabindex='-1'";
//       readonly_b15_m="readonly='true' style='background-color:#bcc6cc;' tabindex='-1'";
//     }
     
     String inputb15_F="<input "+readonly_fsw+" value='"+fsw+"'  onkeypress='return numbers(event);' placeholder='fsw'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_fsw' id='"+id+"_fsw' class='form-control inputs'>"; 
     
     String inputb15_M="<input "+readonly_msm+" value='"+msm+"'  onkeypress='return numbers(event);' placeholder='msm'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_msm' id='"+id+"_msm' class='form-control inputs'>"; 
     
    
indicators+="<tr>"
        + "<td style='vertical-align: middle;' rowspan='1'> <span class='badge'>"+count+"  </span><b> "+indic+"</b></td>"
        
        + "<td>"+inputb15_F+"</td>"
        + "<td>"+inputb15_M+"</td>"
        + "</tr>";
        

count++;
                     }


return indicators+"</tbody>";

}


public JSONObject getData( dbConn conn, String reportingdate, String facilitymfl) throws SQLException{
    JSONArray ja= new JSONArray();
    

int hasdata=0;

String getdata=" select * from aphiaplus_moi.kp_daily where date='"+reportingdate+"' and dic ='"+facilitymfl+"' ";
  

conn.rs1=conn.st1.executeQuery(getdata);
JSONObject lengthobject= new JSONObject();
JSONObject hm= new JSONObject();
while (conn.rs1.next()) 
{
JSONObject hm2= new JSONObject();
hasdata++;
//we want something like this {"HTS_TST":{"bl15_Male":0,"bl15_Female":1,"ab15_Male":2,"ab15_Female":1}}
hm2.put("fsw", conn.rs1.getString("fsw"));
hm2.put("msm", conn.rs1.getString("msm"));
hm.put(conn.rs1.getString("indicator"), hm2);
//ja.put(hm);
}
        hm.put("length", hasdata);
        //lengthobject.put("length", hasdata);
        
ja.put(lengthobject);
return hm;
}







public ResultSet pullIndicators(dbConn conn) throws SQLException {

String qry="select * from aphiaplus_moi.kp_indicators_daily where active='1' order by orodha asc";


conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}


public JSONArray toJson(ResultSet res) throws SQLException{

    
    
JSONArray jo2 = new JSONArray();
int count=0;

while(res.next())
{
    
JSONObject jo = new JSONObject(); 

String id="";
String indicator_code="";
String active="";
String indicatorname="";
String orodha="";

    id =res.getString("id");
    indicator_code =res.getString("indicator_code");
    indicatorname =res.getString("indicatorname");
    orodha =res.getString("orodha");

    jo.put("id",id);
    jo.put("indicator_code",indicator_code);
    jo.put("indicatorname",indicatorname);
    jo.put("orodha",orodha);
    jo2.put(jo);
    
    count++;
    
}


    
    
return jo2;    
}


}
