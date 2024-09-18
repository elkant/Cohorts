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
public class getMiLiIndicators extends HttpServlet {

    
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
            Logger.getLogger(getMiLiIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getMiLiIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
        + "<th rowspan='1'><b>Indicator</b></th>"
        + "<th rowspan='1'><b>15-17</b></th>"
        + "<th rowspan='1'><b>18-19</b></th>"
        + "<th rowspan='1'><b>20-24</b></th>"
        + "<th rowspan='1'><b>25-29</b></th>"
        + "<th rowspan='1'><b>30-34</b></th>"
        + "<th rowspan='1'><b>35-39</b></th>"
        + "<th rowspan='1'><b>40-44</b></th>"
        + "<th rowspan='1'><b>45-49</b></th>"
        + "<th rowspan='1'><b>50</b></th>"
        + "<th rowspan='1'>Total</th>"
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
    displaysection="<tr style='background-color:#4b8df8;'><td ><b>"+mainsection+"</b></td><td><b>15-17 Yrs</b></td><td><b>18-19 Yrs</b></td><td><b>20-24 Yrs</b></td><td><b>25-29 Yrs</b></td><td><b>30-34Yrs</b></td><td><b>35-39Yrs</b></td><td><b>40-44Yrs</b></td><td><b>45-49Yrs</b></td><td><b>50+Yrs</b></td><td><b>Total</b></td></tr>";
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
    

String __17="";
String __19="";
String __24="";
String __29="";
String __34="";
String __39="";
String __44="";
String __49="";
String __50="";
String _value="";


  




    try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        //pull data by column
   if(jo.get(nonnumericid)!=null){
        
       JSONObject joage=(JSONObject) jo.get(nonnumericid);
__17=joage.get("_17").toString();
__19=joage.get("_19").toString();
__24=joage.get("_24").toString();
__29=joage.get("_29").toString();
__34=joage.get("_34").toString();
__39=joage.get("_39").toString();
__44=joage.get("_44").toString();
__49=joage.get("_49").toString();
__50=joage.get("_50").toString();
_value=joage.get("value").toString();
         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     //_____________________________This is a section to determine age bands that should be enabled/disbled from entries. This should be done by indicators
     
     
     
String dis__17="";
String dis__19="";
String dis__24="";
String dis__29="";
String dis__34="";
String dis__39="";
String dis__44="";
String dis__49="";
String dis__50="";




if(disablecolumns.contains("_17")){dis__17="readonly='true'";}
if(disablecolumns.contains("_19")){dis__19="readonly='true'";}
if(disablecolumns.contains("_24")){dis__24="readonly='true'";}
if(disablecolumns.contains("_29")){dis__29="readonly='true'";}
if(disablecolumns.contains("_34")){dis__34="readonly='true'";}
if(disablecolumns.contains("_39")){dis__39="readonly='true'";}
if(disablecolumns.contains("_44")){dis__44="readonly='true'";}
if(disablecolumns.contains("_49")){dis__49="readonly='true'";}
if(disablecolumns.contains("_50")){dis__50="readonly='true'";}



     
     //____________________________________end of disabling indicators_______________________________________

     

     String   inputtotal=""
                      
+"<td><input "+dis__17+"  onblur='indicate_changed(\""+id+"__17\",\""+id+"\");'   "+readonly_value+" value='"+__17+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__17' id='"+id+"__17' class='form-control inputs'></td>"
+"<td><input "+dis__19+"  onblur='indicate_changed(\""+id+"__19\",\""+id+"\");'   "+readonly_value+" value='"+__19+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__19' id='"+id+"__19' class='form-control inputs'></td>"
+"<td><input "+dis__24+"  onblur='indicate_changed(\""+id+"__24\",\""+id+"\");'   "+readonly_value+" value='"+__24+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__24' id='"+id+"__24' class='form-control inputs'></td>"
+"<td><input "+dis__29+"  onblur='indicate_changed(\""+id+"__29\",\""+id+"\");'   "+readonly_value+" value='"+__29+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__29' id='"+id+"__29' class='form-control inputs'></td>"
+"<td><input "+dis__34+"  onblur='indicate_changed(\""+id+"__34\",\""+id+"\");'   "+readonly_value+" value='"+__34+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__34' id='"+id+"__34' class='form-control inputs'></td>"
+"<td><input "+dis__39+"  onblur='indicate_changed(\""+id+"__39\",\""+id+"\");'   "+readonly_value+" value='"+__39+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__39' id='"+id+"__39' class='form-control inputs'></td>"
+"<td><input "+dis__44+"  onblur='indicate_changed(\""+id+"__44\",\""+id+"\");'   "+readonly_value+" value='"+__44+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__44' id='"+id+"__44' class='form-control inputs'></td>"
+"<td><input "+dis__49+"  onblur='indicate_changed(\""+id+"__49\",\""+id+"\");'   "+readonly_value+" value='"+__49+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__49' id='"+id+"__49' class='form-control inputs'></td>"
+"<td><input "+dis__50+"  onblur='indicate_changed(\""+id+"__50\",\""+id+"\");'   "+readonly_value+" value='"+__50+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");"+hasautocalc+"' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"__50' id='"+id+"__50' class='form-control inputs'></td>"
+"<td><input  onblur='indicate_changed(\""+id+"_value\");'   readonly='true' value='"+_value+"' placeholder='' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_value' id='"+id+"_value' class='form-control inputs'></td>"
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

int curperiod=new Integer(par.get("date"));
int prevperiod=curperiod-1;
String prd=par.get("date");

if(prd.endsWith("01"))
{
    String prdyr=prd.substring(0,4 );
String prdmn=prd.substring(4,6);

int prevprdyr=new Integer(prdyr)-1;
String prevym=prevprdyr+"12";

 prevperiod=new Integer(prevym);

}


String getdata=" select * from internal_system.micare_liftup_data where yearmonth='"+curperiod+"' and facility_id ='"+par.get("mflcode")+"' and  poptype='"+sdp[0]+"' and indicator_id !='1'   "
        + " union all  "
        + " select * from internal_system.micare_liftup_data where yearmonth='"+prevperiod+"' and facility_id ='"+par.get("mflcode")+"' and  poptype='"+sdp[0]+"' and indicator_id ='1' ";
  
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

hm2.put("_17", conn.rs1.getString("_17"));
hm2.put("_19", conn.rs1.getString("_19"));
hm2.put("_24", conn.rs1.getString("_24"));
hm2.put("_29", conn.rs1.getString("_29"));
hm2.put("_34", conn.rs1.getString("_34"));
hm2.put("_39", conn.rs1.getString("_39"));
hm2.put("_44", conn.rs1.getString("_44"));
hm2.put("_49", conn.rs1.getString("_49"));
hm2.put("_50", conn.rs1.getString("_50"));
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
    
    
String qry="select * from internal_system.micare_liftup_indicators where active='1' and category like '%"+sdp[0]+"%' order by orodha asc";


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
