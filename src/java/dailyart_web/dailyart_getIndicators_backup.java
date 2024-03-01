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
public class dailyart_getIndicators_backup extends HttpServlet {

    
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
        + "<th colspan='2'><b><1</b></th>"
        + "<th colspan='2'><b>1-4</b></th>"
        + "<th colspan='2'><b>5-9</b></th>"
        + "<th colspan='2'><b>10-14</b></th>"
        + "<th colspan='2'><b>15-19</b></th>"
        + "<th colspan='2'><b>20-24</b></th>"
        + "<th colspan='2'><b>25-29</b></th>"
        + "<th colspan='2'><b>30-34</b></th>"
        + "<th colspan='2'><b>35-39</b></th>"
        + "<th colspan='2'><b>40-44</b></th>"
        + "<th colspan='2'><b>45-49</b></th>"
        + "<th colspan='2'><b>50</b></th>"
        + "<th rowspan='2'>Total</th>"
        + "</tr>"
        + "<tr>"
        + ""
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
        + "<th><b>F</b></th>"
        + "<th><b>M</b></th>"
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
    displaysection="<tr style='background-color:#4b8df8;'><td ><b>"+mainsection+"</b></td><td><b><1 F</b></td>	<td><b><1 M</b></td>	<td><b>1-4 F</b></td>	<td><b>1-4 M</b></td>	<td><b>5-9 F</b></td>	<td><b>5-9 M</b></td>	<td><b>10-14 F</b></td>	<td><b>10-14 M</b></td>	<td><b>15-19 F</b></td>	<td><b>15-19 M</b></td>	<td><b>20-24 F</b></td>	<td><b>20-24 M</b></td>	<td><b>25-29 F</b></td>	<td><b>25-29 M</b></td>	<td><b>30-34 F</b></td>	<td><b>30-34 M</b></td>	<td><b>35-39 F</b></td>	<td><b>35-39 M</b></td>	<td><b>40-44 F</b></td>	<td><b>40-44 M</b></td>	<td><b>45-49 F</b></td>	<td><b>45-49 M</b></td>	<td><b>50+ F</b></td>	<td><b>50+ M</b></td><td><b>Total Total</b></td></tr>";
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
    

    String _der_1f="";
String _der_1m="";
String _der_4f="";
String _der_4m="";
String _der_9f="";
String _der_9m="";
String _der_14f="";
String _der_14m="";
String _der_19f="";
String _der_19m="";
String _der_24f="";
String _der_24m="";
String _der_29f="";
String _der_29m="";
String _der_34f="";
String _der_34m="";
String _der_39f="";
String _der_39m="";
String _der_44f="";
String _der_44m="";
String _der_49f="";
String _der_49m="";
String _der_50f="";
String _der_50m="";
String _value="";

  




    try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        //pull data by column
   if(jo.get(nonnumericid)!=null){
        
       JSONObject joage=(JSONObject) jo.get(nonnumericid);
        _der_1f=joage.get("der_1f").toString();
_der_1m=joage.get("der_1m").toString();
_der_4f=joage.get("der_4f").toString();
_der_4m=joage.get("der_4m").toString();
_der_9f=joage.get("der_9f").toString();
_der_9m=joage.get("der_9m").toString();
_der_14f=joage.get("der_14f").toString();
_der_14m=joage.get("der_14m").toString();
_der_19f=joage.get("der_19f").toString();
_der_19m=joage.get("der_19m").toString();
_der_24f=joage.get("der_24f").toString();
_der_24m=joage.get("der_24m").toString();
_der_29f=joage.get("der_29f").toString();
_der_29m=joage.get("der_29m").toString();
_der_34f=joage.get("der_34f").toString();
_der_34m=joage.get("der_34m").toString();
_der_39f=joage.get("der_39f").toString();
_der_39m=joage.get("der_39m").toString();
_der_44f=joage.get("der_44f").toString();
_der_44m=joage.get("der_44m").toString();
_der_49f=joage.get("der_49f").toString();
_der_49m=joage.get("der_49m").toString();
_der_50f=joage.get("der_50f").toString();
_der_50m=joage.get("der_50m").toString();
_value=joage.get("value").toString();

         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     //_____________________________This is a section to determine age bands that should be enabled/disbled from entries. This should be done by indicators
     
     
     
String dis_der_1f=""; 
String dis_der_1m="";
String dis_der_4f="";
String dis_der_4m="";
String dis_der_9f="";
String dis_der_9m="";
String dis_der_14f="";
String dis_der_14m="";
String dis_der_19f="";
String dis_der_19m="";
String dis_der_24f="";
String dis_der_24m="";
String dis_der_29f="";
String dis_der_29m="";
String dis_der_34f="";
String dis_der_34m="";
String dis_der_39f="";
String dis_der_39m="";
String dis_der_44f="";
String dis_der_44m="";
String dis_der_49f="";
String dis_der_49m="";
String dis_der_50f="";
String dis_der_50m="";


if(disablecolumns.contains("der_1f")){dis_der_1f="readonly='true'";}
if(disablecolumns.contains("der_1m")){dis_der_1m="readonly='true'";}
if(disablecolumns.contains("der_4f")){dis_der_4f="readonly='true'";}
if(disablecolumns.contains("der_4m")){dis_der_4m="readonly='true'";}
if(disablecolumns.contains("der_9f")){dis_der_9f="readonly='true'";}
if(disablecolumns.contains("der_9m")){dis_der_9m="readonly='true'";}
if(disablecolumns.contains("der_14f")){dis_der_14f="readonly='true'";}
if(disablecolumns.contains("der_14m")){dis_der_14m="readonly='true'";}
if(disablecolumns.contains("der_19f")){dis_der_19f="readonly='true'";}
if(disablecolumns.contains("der_19m")){dis_der_19m="readonly='true'";}
if(disablecolumns.contains("der_24f")){dis_der_24f="readonly='true'";}
if(disablecolumns.contains("der_24m")){dis_der_24m="readonly='true'";}
if(disablecolumns.contains("der_29f")){dis_der_29f="readonly='true'";}
if(disablecolumns.contains("der_29m")){dis_der_29m="readonly='true'";}
if(disablecolumns.contains("der_34f")){dis_der_34f="readonly='true'";}
if(disablecolumns.contains("der_34m")){dis_der_34m="readonly='true'";}
if(disablecolumns.contains("der_39f")){dis_der_39f="readonly='true'";}
if(disablecolumns.contains("der_39m")){dis_der_39m="readonly='true'";}
if(disablecolumns.contains("der_44f")){dis_der_44f="readonly='true'";}
if(disablecolumns.contains("der_44m")){dis_der_44m="readonly='true'";}
if(disablecolumns.contains("der_49f")){dis_der_49f="readonly='true'";}
if(disablecolumns.contains("der_49m")){dis_der_49m="readonly='true'";}
if(disablecolumns.contains("der_50f")){dis_der_50f="readonly='true'";}
if(disablecolumns.contains("der_50m")){dis_der_50m="readonly='true'";}

     
     //____________________________________end of disabling indicators_______________________________________

     

     String   inputtotal=""
                      
+"<td><input "+dis_der_1f+"  onblur='indicate_changed(\""+id+"_der_1f\");'   "+readonly_value+" value='"+_der_1f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_1f' id='"+id+"_der_1f' class='form-control inputs'></td>"
+"<td><input "+dis_der_1m+"  onblur='indicate_changed(\""+id+"_der_1m\");'   "+readonly_value+" value='"+_der_1m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_1m' id='"+id+"_der_1m' class='form-control inputs'></td>"
+"<td><input "+dis_der_4f+"  onblur='indicate_changed(\""+id+"_der_4f\");'   "+readonly_value+" value='"+_der_4f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_4f' id='"+id+"_der_4f' class='form-control inputs'></td>"
+"<td><input "+dis_der_4m+"  onblur='indicate_changed(\""+id+"_der_4m\");'   "+readonly_value+" value='"+_der_4m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_4m' id='"+id+"_der_4m' class='form-control inputs'></td>"
+"<td><input "+dis_der_9f+"  onblur='indicate_changed(\""+id+"_der_9f\");'   "+readonly_value+" value='"+_der_9f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_9f' id='"+id+"_der_9f' class='form-control inputs'></td>"
+"<td><input "+dis_der_9m+"  onblur='indicate_changed(\""+id+"_der_9m\");'   "+readonly_value+" value='"+_der_9m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_9m' id='"+id+"_der_9m' class='form-control inputs'></td>"
+"<td><input "+dis_der_14f+"  onblur='indicate_changed(\""+id+"_der_14f\");'   "+readonly_value+" value='"+_der_14f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_14f' id='"+id+"_der_14f' class='form-control inputs'></td>"
+"<td><input "+dis_der_14m+"  onblur='indicate_changed(\""+id+"_der_14m\");'   "+readonly_value+" value='"+_der_14m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_14m' id='"+id+"_der_14m' class='form-control inputs'></td>"
+"<td><input "+dis_der_19f+"  onblur='indicate_changed(\""+id+"_der_19f\");'   "+readonly_value+" value='"+_der_19f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_19f' id='"+id+"_der_19f' class='form-control inputs'></td>"
+"<td><input "+dis_der_19m+"  onblur='indicate_changed(\""+id+"_der_19m\");'   "+readonly_value+" value='"+_der_19m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_19m' id='"+id+"_der_19m' class='form-control inputs'></td>"
+"<td><input "+dis_der_24f+"  onblur='indicate_changed(\""+id+"_der_24f\");'   "+readonly_value+" value='"+_der_24f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_24f' id='"+id+"_der_24f' class='form-control inputs'></td>"
+"<td><input "+dis_der_24m+"  onblur='indicate_changed(\""+id+"_der_24m\");'   "+readonly_value+" value='"+_der_24m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_24m' id='"+id+"_der_24m' class='form-control inputs'></td>"
+"<td><input "+dis_der_29f+"  onblur='indicate_changed(\""+id+"_der_29f\");'   "+readonly_value+" value='"+_der_29f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_29f' id='"+id+"_der_29f' class='form-control inputs'></td>"
+"<td><input "+dis_der_29m+"  onblur='indicate_changed(\""+id+"_der_29m\");'   "+readonly_value+" value='"+_der_29m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_29m' id='"+id+"_der_29m' class='form-control inputs'></td>"
+"<td><input "+dis_der_34f+"  onblur='indicate_changed(\""+id+"_der_34f\");'   "+readonly_value+" value='"+_der_34f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_34f' id='"+id+"_der_34f' class='form-control inputs'></td>"
+"<td><input "+dis_der_34m+"  onblur='indicate_changed(\""+id+"_der_34m\");'   "+readonly_value+" value='"+_der_34m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_34m' id='"+id+"_der_34m' class='form-control inputs'></td>"
+"<td><input "+dis_der_39f+"  onblur='indicate_changed(\""+id+"_der_39f\");'   "+readonly_value+" value='"+_der_39f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_39f' id='"+id+"_der_39f' class='form-control inputs'></td>"
+"<td><input "+dis_der_39m+"  onblur='indicate_changed(\""+id+"_der_39m\");'   "+readonly_value+" value='"+_der_39m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_39m' id='"+id+"_der_39m' class='form-control inputs'></td>"
+"<td><input "+dis_der_44f+"  onblur='indicate_changed(\""+id+"_der_44f\");'   "+readonly_value+" value='"+_der_44f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_44f' id='"+id+"_der_44f' class='form-control inputs'></td>"
+"<td><input "+dis_der_44m+"  onblur='indicate_changed(\""+id+"_der_44m\");'   "+readonly_value+" value='"+_der_44m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_44m' id='"+id+"_der_44m' class='form-control inputs'></td>"
+"<td><input "+dis_der_49f+"  onblur='indicate_changed(\""+id+"_der_49f\");'   "+readonly_value+" value='"+_der_49f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_49f' id='"+id+"_der_49f' class='form-control inputs'></td>"
+"<td><input "+dis_der_49m+"  onblur='indicate_changed(\""+id+"_der_49m\");'   "+readonly_value+" value='"+_der_49m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_49m' id='"+id+"_der_49m' class='form-control inputs'></td>"
+"<td><input "+dis_der_50f+"  onblur='indicate_changed(\""+id+"_der_50f\");'   "+readonly_value+" value='"+_der_50f+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_50f' id='"+id+"_der_50f' class='form-control inputs'></td>"
+"<td><input "+dis_der_50m+"  onblur='indicate_changed(\""+id+"_der_50m\");'   "+readonly_value+" value='"+_der_50m+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_der_50m' id='"+id+"_der_50m' class='form-control inputs'></td>"
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

String getdata=" select * from der_rri.der_table_age where date='"+par.get("date")+"' and mflcode ='"+par.get("mflcode")+"' and  delivery_point='"+sdp[0]+"' ";
  
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

hm2.put("der_1f", conn.rs1.getString("der_1f"));
hm2.put("der_1m", conn.rs1.getString("der_1m"));
hm2.put("der_4f", conn.rs1.getString("der_4f"));
hm2.put("der_4m", conn.rs1.getString("der_4m"));
hm2.put("der_9f", conn.rs1.getString("der_9f"));
hm2.put("der_9m", conn.rs1.getString("der_9m"));
hm2.put("der_14f", conn.rs1.getString("der_14f"));
hm2.put("der_14m", conn.rs1.getString("der_14m"));
hm2.put("der_19f", conn.rs1.getString("der_19f"));
hm2.put("der_19m", conn.rs1.getString("der_19m"));
hm2.put("der_24f", conn.rs1.getString("der_24f"));
hm2.put("der_24m", conn.rs1.getString("der_24m"));
hm2.put("der_29f", conn.rs1.getString("der_29f"));
hm2.put("der_29m", conn.rs1.getString("der_29m"));
hm2.put("der_34f", conn.rs1.getString("der_34f"));
hm2.put("der_34m", conn.rs1.getString("der_34m"));
hm2.put("der_39f", conn.rs1.getString("der_39f"));
hm2.put("der_39m", conn.rs1.getString("der_39m"));
hm2.put("der_44f", conn.rs1.getString("der_44f"));
hm2.put("der_44m", conn.rs1.getString("der_44m"));
hm2.put("der_49f", conn.rs1.getString("der_49f"));
hm2.put("der_49m", conn.rs1.getString("der_49m"));
hm2.put("der_50f", conn.rs1.getString("der_50f"));
hm2.put("der_50m", conn.rs1.getString("der_50m"));
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
