/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upi;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class getUpiIndicators extends HttpServlet {

    
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
            Logger.getLogger(getUpiIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getUpiIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
String indicators="<thead><tr style='background-color:#9f9999;color:white;'><th>Section/Indicator</th><th>Code</th><th>Below 15 Yrs</th><th>Above 15 Yrs</th><th>Total</th></tr></thead><tbody>";

    

 JSONObject jo= getData(conn, reportingdate, facility);
    
int count=1;
ResultSet r=pullIndicators(conn);

while(r.next()){
   
//  System.out.println("__"+jo.toString());  
    
    String showsection=r.getString("showsection");
    String section_name=r.getString("section_name");
    String indic=r.getString("indicator");
    String id=r.getString("id");
    String indicator_code=r.getString("code");
    
    String bl15="";
    String ab15="";
    String ttl="";
    
     try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        
   if(jo.get(id)!=null){
        
       JSONObject joage=(JSONObject) jo.get(id);
        
         bl15=joage.get("bl15").toString();
         ab15=joage.get("ab15").toString();
         ttl=joage.get("ttl").toString();
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     String readonly_b19_m="";
     String readonly_b19_f="";
     String readonly_ttl=" readonly='true' ";
     
    String displaysection="";
    
    if(showsection.equals("1"))
    {
    displaysection="<tr style='background-color:#4b8df8;'><td ><b>"+section_name+"</b></td><td><b>Code</b></td><td><b>< 15 Yrs</b></td><td><b>Above 15 Yrs</b></td><td><b>Total</b></td></tr>";
    }
    else {
    displaysection="";
    }
     
     //Baseline Information	Code	Male 10-19yrs	Female 10-19yrs	Total

     //sumofindicators(sourceindicators,destinationindicator)
     String inputb19_M="<input "+readonly_b19_m+" value='"+bl15+"' onkeyup='sumofindicators(\""+id+"_bl15@"+id+"_ab15\",\""+id+"_ttl\");'  onkeypress='return numbers(event); ' placeholder='< 15 Yr'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_bl15' id='"+id+"_bl15' class='form-control inputs'>"; 
     String inputb19_F="<input "+readonly_b19_f+" value='"+ab15+"' onkeyup='sumofindicators(\""+id+"_ab15@"+id+"_bl15\",\""+id+"_ttl\");' onkeypress='return numbers(event);' placeholder='>= 15 Yr'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_ab15' id='"+id+"_ab15' class='form-control inputs'>"; 
     String inputttl="<input "+readonly_ttl+" value='"+ttl+"' tabindex='-1'  onkeypress='return numbers(event);' placeholder='Total'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_ttl' id='"+id+"_ttl' class='form-control inputs'>"; 
     
    
indicators+=""+displaysection
        + "<tr>"
        + "<td style='vertical-align: middle;' rowspan='1'> <span class='badge'>"+count+"  </span><b> "+indic+"</b></td>"
        + "<td>"+indicator_code+"</td>"
       
        + "<td>"+inputb19_M+"</td>"
        + "<td>"+inputb19_F+"</td>"
        + "<td>"+inputttl+"</td>"
        + "</tr>";
        

count++;
                     }


return indicators+"</tbody>";

}


public JSONObject getData( dbConn conn, String reportingdate, String facilitymfl) throws SQLException{
    JSONArray ja= new JSONArray();
    


int hasdata=0;

String getdata=" select * from internal_system.upi_data where date='"+reportingdate+"' and facility='"+facilitymfl+"'";

conn.rs1=conn.st1.executeQuery(getdata);

   JSONObject lengthobject= new JSONObject();
JSONObject hm= new JSONObject();
        while (conn.rs1.next()) 
        {
 
 JSONObject hm2= new JSONObject();
hasdata++;
  //we want something like this {"HTS_TST":{"bl15_Male":0,"bl15_Female":1,"ab15_Male":2,"ab15_Female":1}}
hm2.put("bl15", conn.rs1.getString("bl15"));
hm2.put("ab15", conn.rs1.getString("ab15"));
hm2.put("ttl", conn.rs1.getString("ttl"));
 hm.put(conn.rs1.getString("indicatorid"), hm2);
//ja.put(hm);
      }
        hm.put("length", hasdata);
        //lengthobject.put("length", hasdata);
        
ja.put(lengthobject);
return hm;
}







public ResultSet pullIndicators(dbConn conn) throws SQLException 
{

String qry="select * from internal_system.upi_indicators where is_active='1' order by order_no asc";


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
String showsection="";
String section_name="";

    id =res.getString("id");
    indicator_code =res.getString("code");
    indicatorname =res.getString("indicator");
    orodha =res.getString("order_no");
    showsection =res.getString("showsection");
    section_name =res.getString("section_name");

    jo.put("id",id);
    jo.put("indicator_code",indicator_code);
    jo.put("indicatorname",indicatorname);
    jo.put("orodha",orodha);
    jo.put("showsection",showsection);
    jo.put("section_name",section_name);
    jo2.put(jo);
    
    count++;
    
}


    
    
return jo2;    
}


}
