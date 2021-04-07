/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hts_self;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;
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
public class getHtsSelfIndicators extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            String ym="";
            
            String fc="";
          
            String mod="";
            
            if(request.getParameter("fc")!=null){fc=request.getParameter("fc");}
            if(request.getParameter("ym")!=null){ym=request.getParameter("ym");}
            if(request.getParameter("mod")!=null){mod=request.getParameter("mod");}
            
            
            
            
            HashMap params = new HashMap<String, String>();
            
            params.put("yearmonth", ym);
            params.put("facility", fc);
            params.put("modality", mod);
            
           dbConn conn = new dbConn();
           if(!fc.equals("")&&!ym.equals("")&&!mod.equals("")){
           //return tables
           out.println(getHtmlTable(conn,params));
           
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
            Logger.getLogger(getHtsSelfIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getHtsSelfIndicators.class.getName()).log(Level.SEVERE, null, ex);
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

public String getHtmlTable(dbConn conn, HashMap pms) throws SQLException{
String indicators="<thead><tr><th>Indicator</th><th>Total</th></tr></thead><tbody>";

    

 JSONObject jo= getData(conn, pms);
    //Get a resultset of all th e indicators and loop through each as you check if the same data has been entered
int count=1;
ResultSet r=pullIndicators(conn);

while(r.next()){
   
//  System.out.println("__"+jo.toString());  
    String indic=r.getString("indicatorname");
    String id=r.getString("id");
    

    String value="";
    
     try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        //pull data by column
   if(jo.get(id)!=null){
        
       JSONObject joage=(JSONObject) jo.get(id);
         value=joage.get("value").toString();
         
         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     String readonly_value="";

     
     
//     if(id.contains("VMMC")){
//       fsw="readonly='true' style='background-color:#bcc6cc;'  tabindex='-1'";
//       readonly_b15_f="readonly='true' style='background-color:#bcc6cc;' tabindex='-1'";
//     }
//     else  if(id.contains("PMTCT")){
//       readonly_ab15_m="readonly='true' style='background-color:#bcc6cc;'  tabindex='-1'";
//       readonly_b15_m="readonly='true' style='background-color:#bcc6cc;' tabindex='-1'";
//     }
     
     String inputtotal="<input "+readonly_value+" value='"+value+"'  onkeypress='return numbers(event);' placeholder='total'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_total' id='"+id+"_total' class='form-control inputs'>"; 
   
    
indicators+="<tr>"
        + "<td style='vertical-align: middle;' rowspan='1'> <span class='badge'>"+count+"  </span><b> "+indic+"</b></td>"
        
        + "<td>"+inputtotal+"</td>"
       
        + "</tr>";
        

count++;
                     }


return indicators+"</tbody>";

}


public JSONObject getData( dbConn conn, HashMap par) throws SQLException{
    JSONArray ja= new JSONArray();
    

int hasdata=0;

String getdata=" select * from internal_system.htsself_data where yearmonth='"+par.get("yearmonth")+"' and facility ='"+par.get("facility")+"' and  modality ='"+par.get("modality")+"' ";
  

conn.rs1=conn.st1.executeQuery(getdata);
JSONObject lengthobject= new JSONObject();
JSONObject hm= new JSONObject();
while (conn.rs1.next()) 
{
JSONObject hm2= new JSONObject();
hasdata++;
//we want something like this {"KITS Assisted":{"value":0}}
hm2.put("value", conn.rs1.getString("value"));

hm.put(conn.rs1.getString("indicatorid"), hm2);
//ja.put(hm);
}
        hm.put("length", hasdata);
        //lengthobject.put("length", hasdata);
        
ja.put(lengthobject);
return hm;
}







public ResultSet pullIndicators(dbConn conn) throws SQLException {

String qry="select * from internal_system.htsself_indicators where active='1' order by orodha asc";


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
