/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eligiblenotdoneforvl;

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
public class getEligibleIndicators extends HttpServlet {

    
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
            Logger.getLogger(getEligibleIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getEligibleIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
        + "<th class='rotate' rowspan='1'><b>Indicator</b></th>"
        + "<th class='rotate' colspan='1'><b>0-9 Yrs</b></th>"
        + "<th class='rotate' colspan='1'><b>10-14 Yrs</b></th>"
        + "<th class='rotate' colspan='1'><b>15-19 Yrs</b></th>"
        + "<th class='rotate' colspan='1'><b>20-24 Yrs</b></th>"
        + "<th class='rotate' colspan='1'><b>25+ Yrs</b></th>"
        + "<th class='rotate' colspan='1'><b>PMTCT Clients</b></th>"
        + "<th class='rotate' rowspan='1'>Total</th>"
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
    displaysection="<tr  style='background-color:#4b8df8;'><td class='rotate' ><b>"+mainsection+"</b></td><td class='rotate'><b>0-9</b></td><td class='rotate'><b>10-14</b></td><td class='rotate'><b>15-19</b></td>	<td class='rotate'><b>20-24</b></td><td class='rotate'><b>25+</b></td><td class='rotate'><b>PMTCT</b></td><td class='rotate'><b>Total</b></td></tr>";
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
    

String __9="";
String __14="";
String __19="";
String __24="";
String __25="";
String _pbfw="";

String _value="";
 




    try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        //pull data by column
   if(jo.get(nonnumericid)!=null){
        
       JSONObject joage=(JSONObject) jo.get(nonnumericid);
__9=joage.get("_9").toString();
__14=joage.get("_14").toString();
__19=joage.get("_19").toString();
__24=joage.get("_24").toString();
__25=joage.get("_25").toString();
_pbfw=joage.get("pbfw").toString();

_value=joage.get("value").toString();


         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     //_____________________________This is a section to determine age bands that should be enabled/disbled from entries. This should be done by indicators
     
     
     
String dis__9="";
String dis__14="";
String dis__19="";
String dis__24="";
String dis__25="";
String dis_pbfw="";
String dis_value="";




if(disablecolumns.contains("_9")){dis__9="readonly='true'";}
if(disablecolumns.contains("_14")){dis__14="readonly='true'";}
if(disablecolumns.contains("_19")){dis__19="readonly='true'";}
if(disablecolumns.contains("_24")){dis__24="readonly='true'";}
if(disablecolumns.contains("_25")){dis__25="readonly='true'";}
if(disablecolumns.contains("pbfw")){dis_pbfw="readonly='true'";}
if(disablecolumns.contains("value")){dis_value="readonly='true'";}



     
     //____________________________________end of disabling indicators_______________________________________

     

     String   inputtotal=""
+"<td><input "+dis__9+"  onblur='indicate_changed(\""+id+"__9\");'   "+readonly_value+" value='"+__9+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"validatePbfw(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__9' id='"+id+"__9' class='form-control inputs'></td>"
+"<td><input "+dis__14+"  onblur='indicate_changed(\""+id+"__14\");'   "+readonly_value+" value='"+__14+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"validatePbfw(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__14' id='"+id+"__14' class='form-control inputs'></td>"
+"<td><input "+dis__19+"  onblur='indicate_changed(\""+id+"__19\");'   "+readonly_value+" value='"+__19+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"validatePbfw(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__19' id='"+id+"__19' class='form-control inputs'></td>"
+"<td><input "+dis__24+"  onblur='indicate_changed(\""+id+"__24\");'   "+readonly_value+" value='"+__24+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"validatePbfw(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__24' id='"+id+"__24' class='form-control inputs'></td>"
+"<td><input "+dis__25+"  onblur='indicate_changed(\""+id+"__25\");'   "+readonly_value+" value='"+__25+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"validatePbfw(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__25' id='"+id+"__25' class='form-control inputs'></td>"
+"<td><input "+dis_pbfw+"  onblur='indicate_changed(\""+id+"_pbfw\");'   "+readonly_value+" value='"+_pbfw+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"validatePbfw(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_pbfw' id='"+id+"_pbfw' class='form-control inputs'></td>"
+"<td><input "+dis_value+"  onblur='indicate_changed(\""+id+"_value\");'   "+readonly_value+" value='"+_value+"' placeholder='' onkeyup='"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_value' id='"+id+"_value' class='form-control inputs'></td>"
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

String getdata=" select id,date,indicator_id,mflcode,delivery_point,_9,_14,_19,_24,_25,pbfw,value from internal_system.eligibleforvl_data where date='"+par.get("date")+"' and mflcode ='"+par.get("mflcode")+"' and  delivery_point='"+sdp[0]+"' ";
  
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

hm2.put("_9", conn.rs1.getString("_9"));
hm2.put("_14", conn.rs1.getString("_14"));
hm2.put("_19", conn.rs1.getString("_19"));
hm2.put("_24", conn.rs1.getString("_24"));
hm2.put("_25", conn.rs1.getString("_25"));
hm2.put("pbfw", conn.rs1.getString("pbfw"));


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
    
    
String qry="select * from internal_system.eligibleforvl_indicators where active='1' and category like '%"+sdp[0]+"%' order by orodha asc";


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
