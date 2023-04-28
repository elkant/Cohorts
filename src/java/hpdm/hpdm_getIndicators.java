/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hpdm;

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
public class hpdm_getIndicators extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            String ym="";
            
            String fc="";
          
           
            
            if(request.getParameter("fc")!=null){fc=request.getParameter("fc");}
            if(request.getParameter("ym")!=null){ym=request.getParameter("ym");}
            
            
            
            
            HashMap params = new HashMap<String, String>();
            
            params.put("yearmonth", ym);
            params.put("facility", fc);
           
            
           dbConn conn = new dbConn();
           if(!fc.equals("")&&!ym.equals("")){
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
            Logger.getLogger(hpdm_getIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(hpdm_getIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
    //0-9	10-14	15-19	20-24	25+

String indicators="<table class='table table-striped table-bordered'  style='width:100%' >"
        + "<thead>"
        + "<tr style='background-color:#0183f1;'>"
        + "<th rowspan='2'>Indicator</th>"
        + "<th style='text-align:center;color:white;' colspan='2'>0-14</th>"
        + "<th style='text-align:center;color:white;' colspan='2'>15-19</th>"
        + "<th style='text-align:center;color:white;' colspan='2'>20-29</th>"
        + "<th style='text-align:center;color:white;' colspan='2'>30-39</th>"
        + "<th style='text-align:center;color:white;' colspan='2'>40-49</th>"
        + "<th style='text-align:center;color:white;' colspan='2'>50+</th>"     
        + "<th style='text-align:center;color:white;' rowspan='2'>Total</th>"
        + "</tr>"
        + "<tr style='background-color:#0183f1;'>"
        + ""
        + "<th style='text-align:center;color:white;' >M</th>"
        + "<th style='text-align:center;color:white;' >F</th>"
        + "<th style='text-align:center;color:white;' >M</th>"
        + "<th style='text-align:center;color:white;' >F</th>"
        + "<th style='text-align:center;color:white;' >M</th>"
        + "<th style='text-align:center;color:white;' >F</th>"
        + "<th style='text-align:center;color:white;' >M</th>"
        + "<th style='text-align:center;color:white;' >F</th>"
        + "<th style='text-align:center;color:white;' >M</th>"
        + "<th style='text-align:center;color:white;' >F</th>"
        + "<th style='text-align:center;color:white;' >M</th>"
        + "<th style='text-align:center;color:white;' >F</th>"
       // + "<th >Total</th>"
        + "</tr>"
        + "</thead><tbody>";

    

 JSONObject jo= getData(conn, pms);
    //Get a resultset of all th e indicators and loop through each as you check if the same data has been entered
int count=1;
ResultSet r=pullIndicators(conn);

while(r.next()){
   
    String showsection=r.getString("show_section");
    
    String section_name=r.getString("section");
    
    String main_section=r.getString("main_section");
String show_main_section=r.getString("show_main_section");
    
    String displaysection="";
       String displaymainsection="";
    
    if(showsection.equals("1"))
    {
    displaysection="<tr style='background-color:#4b8df8;'>"
            + "<td style='text-align:center;color:white;' ><b>"+section_name+"</b></td>"
            + "<td style='text-align:center;color:white;'><b>0-14 M</b></td>"
            + "<td style='text-align:center;color:white;'><b>0-14 F</b></td>"
            
            + "<td style='text-align:center;color:white;'><b>15-19 M</b></td>"
            + "<td style='text-align:center;color:white;'><b>15-19 F</b></td>"
             
            + "<td style='text-align:center;color:white;'><b>20-29 M</b></td>"
            + "<td style='text-align:center;color:white;'><b>20-29 F</b></td>"
             
            + "<td style='text-align:center;color:white;'><b>30-39 M</b></td>"
            + "<td style='text-align:center;color:white;'><b>30-39 F</b></td>"
             
            + "<td style='text-align:center;color:white;'><b>40-49 M</b></td>"
            + "<td style='text-align:center;color:white;'><b>40-49 F</b></td>"
            
             
            + "<td style='text-align:center;color:white;'><b>50+ M</b></td>"
            + "<td style='text-align:center;color:white;'><b>50+ F</b></td>"
          
            + "<td style='text-align:center;color:white;'><b>Total</b></td></tr>";
    }
    else {
    displaysection="";
    }
    
    if(show_main_section.equals("1"))
    {
    displaymainsection="<tr style='background-color:#37383a;'>"
            + "<td colspan='14' style='text-align:center;color:white;' ><b>"+main_section+"</b></td></tr>";
    }
    else {
    displaymainsection="";
    }
    
//  System.out.println("__"+jo.toString());  
    String indic=r.getString("indicatorname");
    String id=r.getString("id");
    

    String m_14="";
    String f_14="";
    String m_19="";
    String f_19="";
    String m_29="";
    String f_29="";
    String m_39="";
    String f_39="";
    String m_49="";
    String f_49="";
    String m_50="";
    String f_50="";
    String ttl="";
    
     try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        //pull data by column
   if(jo.get(id)!=null){
        
       JSONObject joage=(JSONObject) jo.get(id);
         m_14=joage.get("m_14").toString();
         f_14=joage.get("f_14").toString();
         m_19=joage.get("m_19").toString();
         f_19=joage.get("f_19").toString();
         m_29=joage.get("m_29").toString();
         f_29=joage.get("f_29").toString();
         m_39=joage.get("m_39").toString();
         f_39=joage.get("f_39").toString();
         m_49=joage.get("m_49").toString();
         f_49=joage.get("f_49").toString();         
         m_50=joage.get("m_50").toString();
         f_50=joage.get("f_50").toString();         
         ttl=joage.get("ttl").toString();
         
         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     String readonly_value="";

     

     String   inputtotal="<td><input "+readonly_value+" value='"+m_14+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m_14' id='"+id+"_m_14' class='form-control inputs'></td>"
                       +"<td><input "+readonly_value+" value='"+f_14+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f_14' id='"+id+"_f_14' class='form-control inputs'></td>"
                       
                       + "<td><input "+readonly_value+" value='"+m_19+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m_19' id='"+id+"_m_19' class='form-control inputs'></td>" 
                       + "<td><input "+readonly_value+" value='"+f_19+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f_19' id='"+id+"_f_19' class='form-control inputs'></td>" 
                      
                       + "<td><input "+readonly_value+" value='"+m_29+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m_29' id='"+id+"_m_29' class='form-control inputs'></td>"
                       + "<td><input "+readonly_value+" value='"+f_29+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f_29' id='"+id+"_f_29' class='form-control inputs'></td>"
                       
                       + "<td><input "+readonly_value+" value='"+m_39+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m_39' id='"+id+"_m_39' class='form-control inputs'></td>"
                       + "<td><input "+readonly_value+" value='"+f_39+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f_39' id='"+id+"_f_39' class='form-control inputs'></td>"
                       
                       + "<td><input "+readonly_value+" value='"+m_49+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m_49' id='"+id+"_m_49' class='form-control inputs'></td>"
                       + "<td><input "+readonly_value+" value='"+f_49+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f_49' id='"+id+"_f_49' class='form-control inputs'></td>"
                       
                       + "<td><input "+readonly_value+" value='"+m_50+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m_50' id='"+id+"_m_50' class='form-control inputs'></td>"
                       + "<td><input "+readonly_value+" value='"+f_50+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f_50' id='"+id+"_f_50' class='form-control inputs'></td>"
                       
             
             + "<td><input "+readonly_value+" value='"+ttl+"' tabindex='-1'  readonly='true' placeholder='Total '  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_ttl' id='"+id+"_ttl' class='form-control inputs'></td>"; 
   
    //0-9	10-14	15-19	20-24	25+
indicators+=""+displaymainsection+displaysection
        + "<tr>"
        + "<td style='vertical-align: middle;' rowspan='1'> <span class='badge'>"+count+"  </span><b> "+indic+"</b></td>"
        
        + ""+inputtotal+""
       
        + "</tr>";
        

count++;
                     }


return indicators+"</tbody></table>";

}


public JSONObject getData( dbConn conn, HashMap par) throws SQLException{
    JSONArray ja= new JSONArray();
    

int hasdata=0;

String getdata=" select * from internal_system.hpdm_data where yearmonth='"+par.get("yearmonth")+"' and facility ='"+par.get("facility")+"'  ";
  
    System.out.println(""+getdata);
conn.rs1=conn.st1.executeQuery(getdata);
JSONObject lengthobject= new JSONObject();
JSONObject hm= new JSONObject();
while (conn.rs1.next()) 
{
JSONObject hm2= new JSONObject();
hasdata++;
//we want something like this {"KITS Assisted":{"9f":0,"9m":0}}
//indic	9m	9f	14m	14f	19m	19f	24m	24f	25m	25f	ttl

hm2.put("m_14", conn.rs1.getString("m_14"));
hm2.put("f_14", conn.rs1.getString("f_14"));
hm2.put("m_19", conn.rs1.getString("m_19"));
hm2.put("f_19", conn.rs1.getString("f_19"));
hm2.put("m_29", conn.rs1.getString("m_29"));
hm2.put("f_29", conn.rs1.getString("f_29"));
hm2.put("m_39", conn.rs1.getString("m_39"));
hm2.put("f_39", conn.rs1.getString("f_39"));
hm2.put("m_49", conn.rs1.getString("m_49"));
hm2.put("f_49", conn.rs1.getString("f_49"));
hm2.put("m_50", conn.rs1.getString("m_50"));
hm2.put("f_50", conn.rs1.getString("f_50"));

hm2.put("ttl", conn.rs1.getString("ttl"));

hm.put(conn.rs1.getString("indicatorid"), hm2);
//ja.put(hm);
}
        hm.put("length", hasdata);
        //lengthobject.put("length", hasdata);
        
ja.put(lengthobject);
return hm;
}







public ResultSet pullIndicators(dbConn conn) throws SQLException {

String qry="select * from internal_system.hpdm_indicators where active='1' order by orodha asc";


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
String show_section="";
String section_name="";
String main_section="";
String show_main_section="";

    id =res.getString("id");
    indicator_code =res.getString("indicator_code");
    indicatorname =res.getString("indicatorname");
    orodha =res.getString("orodha");
    show_section =res.getString("show_section");
    section_name =res.getString("section");
     main_section =res.getString("main_section");
    show_main_section =res.getString("show_main_section");

    jo.put("id",id);
    jo.put("indicator_code",indicator_code);
    jo.put("indicatorname",indicatorname);
    jo.put("orodha",orodha);
    jo.put("show_section",show_section);
    jo.put("show_section",show_section);
    jo.put("section_name",section_name);
    jo.put("show_main_section",show_main_section);
    jo.put("main_section",main_section);
    jo2.put(jo);
    
    count++;
    
}


    
    
return jo2;    
}


}
