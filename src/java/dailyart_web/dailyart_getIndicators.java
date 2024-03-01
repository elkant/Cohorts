/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailyart_web;

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
public class dailyart_getIndicators extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            String dt="";
            
            String fc="";
            String sdp="";
          
           
            
            if(request.getParameter("fc")!=null){fc=request.getParameter("fc");}
            if(request.getParameter("ym")!=null){dt=request.getParameter("ym");}
            if(request.getParameter("sdp")!=null){sdp=request.getParameter("sdp");}
            
            
            
            
            HashMap params = new HashMap<String, String>();
            
            params.put("date", dt);
            params.put("mflcode", fc);
            params.put("sdp", sdp);
           
            
           dbConn conn = new dbConn();
           if(!fc.equals("")&&!dt.equals("")){
           //return tables
           out.println(getHtmlTable(conn,params));
           
           }
           else {
           //return indicators
           ResultSet r=pullIndicators(conn,params);
           
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
            Logger.getLogger(dailyart_getIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(dailyart_getIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
        + "<tr>"
        + "<th rowspan='2'><b>Indicator</b></th>"
        + "<th colspan='2'><b><15 Yrs</b></th>"
        + "<th colspan='2'><b>15+ Yrs</b></th>"
        + "<th rowspan='2'>Total</th>"
        + "</tr>"
        + "<tr>"
        + ""
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"        
       // + "<th >Total</th>"
        + "</tr>"
        + "</thead><tbody>";

    

 JSONObject jo= getData(conn, pms);
    //Get a resultset of all th e indicators and loop through each as you check if the same data has been entered
int count=1;
ResultSet r=pullIndicators(conn,pms);

while(r.next()){
   
    String showsection=r.getString("show_section");
    
    String mainsection=r.getString("mainsection");
    
    String displaysection="";
    
    if(showsection.equals("1"))
    {
    displaysection="<tr style='background-color:#4b8df8;'><td ><b>"+mainsection+"</b></td><td><b><15 F</b></td>	<td><b><15 M</b></td>	<td><b>15+ F</b></td>	<td><b>15+ M</b></td><td><b>Total Total</b></td></tr>";
    }
    else {
    displaysection="";
    }
    String Auto_Calculate=r.getString("Auto_Calculate");
    
        String hasautocalc="";

if(Auto_Calculate!=null && !Auto_Calculate.equals("") && !Auto_Calculate.equals("null") ){

hasautocalc="buildAutocalculate(\""+Auto_Calculate+"\");";

}
    String readonly_value="";
    
    
    String readonl=r.getString("readonl");
    
     if(readonl==null || readonl.equals("null") || readonl.equals("")){readonly_value="";}else {readonly_value=readonl;}
    
    
    String disablecolumns=r.getString("disablecolumns");
    
//  System.out.println("__"+jo.toString());  
    String indic=r.getString("indicatorname");
    String id=r.getString("indicators_id");//NUmeric
    String nonnumericid=r.getString("id");//NUmeric
    

String _f14="";
String _m14="";
String _f15="";
String _m15="";
String _value="";
 




    try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        //pull data by column
   if(jo.get(nonnumericid)!=null){
        
       JSONObject joage=(JSONObject) jo.get(nonnumericid);
_f14=joage.get("f14").toString();
_m14=joage.get("m14").toString();
_f15=joage.get("f15").toString();
_m15=joage.get("m15").toString();
_value=joage.get("value").toString();


         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     //_____________________________This is a section to determine age bands that should be enabled/disbled from entries. This should be done by indicators
     
     
     
String dis_f14="";
String dis_m14="";
String dis_f15="";
String dis_m15="";




if(disablecolumns.contains("f14")){dis_f14="readonly='true'";}
if(disablecolumns.contains("f14")){dis_m14="readonly='true'";}
if(disablecolumns.contains("f15")){dis_f15="readonly='true'";}
if(disablecolumns.contains("m15")){dis_m15="readonly='true'";}

     
     //____________________________________end of disabling indicators_______________________________________

     

     String   inputtotal=""
                      
+"<td><input "+dis_f14+"  onblur='indicate_changed(\""+id+"_f14\");'   "+readonly_value+" value='"+_f14+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f14' id='"+id+"_f14' class='form-control inputs'></td>"
+"<td><input "+dis_m14+"  onblur='indicate_changed(\""+id+"_m14\");'   "+readonly_value+" value='"+_m14+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m14' id='"+id+"_m14' class='form-control inputs'></td>"
+"<td><input "+dis_f15+"  onblur='indicate_changed(\""+id+"_f15\");'   "+readonly_value+" value='"+_f15+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_f15' id='"+id+"_f15' class='form-control inputs'></td>"
+"<td><input "+dis_m15+"  onblur='indicate_changed(\""+id+"_m15\");'   "+readonly_value+" value='"+_m15+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_m15' id='"+id+"_m15' class='form-control inputs'></td>"
+"<td><input  tabindex='-1'  readonly='true' value='"+_value+"' placeholder=''  onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_value' id='"+id+"_value' class='form-control inputs'></td>"
; 
   
    //0-9	10-14	15-19	20-24	25+
indicators+=""+displaysection
        + "<tr>"
        + "<td style='vertical-align: middle;' rowspan='1'> <span class='badge'>"+count+"  </span><b> "+indic+"</b></td>"
        
        + ""+inputtotal+""
       
        + "</tr>";
        

count++;
                     }


return indicators+"</tbody></table>";

}


public JSONObject getData( dbConn conn, HashMap<String,String> par) throws SQLException{
    JSONArray ja= new JSONArray();
    
String sdp[]=par.get("sdp").split("_");
int hasdata=0;

String getdata=" select id,date,indicator_id,mflcode,delivery_point,f14,m14,f15,m15,value from der_rri.der_table_age where date='"+par.get("date")+"' and mflcode ='"+par.get("mflcode")+"' and  delivery_point='"+sdp[0]+"' ";
  
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

hm2.put("f14", conn.rs1.getString("f14"));
hm2.put("m14", conn.rs1.getString("m14"));
hm2.put("f15", conn.rs1.getString("f15"));
hm2.put("m15", conn.rs1.getString("m15"));

hm2.put("value", conn.rs1.getString("value"));


hm.put(conn.rs1.getString("indicator_id"), hm2);
//ja.put(hm);
}
        hm.put("length", hasdata);
        //lengthobject.put("length", hasdata);
        
ja.put(lengthobject);
return hm;
}







public ResultSet pullIndicators(dbConn conn,HashMap<String,String> hm) throws SQLException {
//we assume that all service delivery points are in the format 1_CCC, where 1 is the service delivery id that is saved on the data table
    String sdp[]=hm.get("sdp").split("_");
    
    
String qry="select * from der_rri.dailyart_indicators where active='1' and category like '%"+sdp[0]+"%' order by orodha asc";


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
String mainsection="";
String sectionrowspan="";
String category="";
String indicators_id="";
String disablecolumns="";
String readonl="";
String Auto_Calculate="";
//note, the id i am pulling is the one that has a underscore
    id =res.getString("id");
    indicator_code =res.getString("indicator_code");
    indicatorname =res.getString("indicatorname");
    orodha =res.getString("orodha");
    show_section =res.getString("show_section");
    mainsection =res.getString("mainsection");
    sectionrowspan =res.getString("sectionrowspan");
    category =res.getString("category");
    indicators_id =res.getString("indicators_id");
    readonl =res.getString("readonl");
    Auto_Calculate =res.getString("Auto_Calculate");
    disablecolumns =res.getString("disablecolumns");

    jo.put("id",id);
    jo.put("indicator_code",indicator_code);
    jo.put("indicatorname",indicatorname);
    jo.put("orodha",orodha);
    jo.put("show_section",show_section);
    jo.put("sectionrowspan",sectionrowspan);
    jo.put("mainsection",mainsection);
    jo.put("category",category);
    jo.put("indicators_id",indicators_id);
    jo.put("readonl",readonl);
    jo.put("Auto_Calculate",Auto_Calculate);
    jo.put("disablecolumns",disablecolumns);
    jo2.put(jo);
    
    count++;
    
}


    
    
return jo2;    
}


}
