/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package see;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
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
public class loadSeeIndicators extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            String dt="";
            
            String fc="";
            String pid="";
            String wr="";
        
            
if(request.getParameter("fc")!=null){fc=request.getParameter("fc");}
if(request.getParameter("dt")!=null){dt=request.getParameter("dt");}
if(request.getParameter("pid")!=null){pid=request.getParameter("pid");}
if(request.getParameter("wr")!=null){wr=request.getParameter("wr");}
            
            
//below parameters are used when you click on to get indicators

  String fm="";
            
if(request.getParameter("fm")!=null){fm=request.getParameter("fm");}
            
           dbConn conn = new dbConn();
           if(!fc.equals("")&&!dt.equals(""))
           {
           //return tables
           out.println(getHtmlTable(conn,dt,fc,pid,wr));
           
           }
           else if (!fm.equals("")) {
           //return indicator id and 
           ResultSet r=pullElementsBySection(conn, fm);
           
           out.println(toJsonDynamic(r));
          
           }
           else {
           //return indicators
           ResultSet r=pullIndicators(conn, pid);
           
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
            Logger.getLogger(loadSeeIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(loadSeeIndicators.class.getName()).log(Level.SEVERE, null, ex);
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

public String getHtmlTable(dbConn conn, String reportingdate, String facility, String patientId, String where) throws SQLException{
//String indicators="<thead><tr style='background-color:#9f9999;color:white;'><th>Section/Indicator</th><th>Code</th><th>10-19 M</th><th>10-19 F</th><th>Total</th></tr></thead><tbody>";
String indicators="<tbody><tr><td>";

JSONObject jo=getData(conn, reportingdate, facility,patientId);
    
int count=1;
ResultSet r=pullIndicators(conn,where);

while(r.next()){
 
    String showsection=r.getString("show_section");
    String section_name=r.getString("section");
    String indic=r.getString("label");
    String id=r.getString("element_id");
    String date=r.getString("indicator_id");
   // String indicator_code=r.getString("code");
    
   
   //_____________________________________
   
String indicator_id=r.getString("indicator_id");
String Form=r.getString("Form");
String section=r.getString("section");
String label=r.getString("label");
String guide=r.getString("guide");
String element_id=r.getString("element_id");
String js_class=r.getString("js_class");
String field_type=r.getString("field_type");
String is_future_date=r.getString("is_future_date");
String readonly=r.getString("readonly");
String required=r.getString("required");
String is_hidden=r.getString("is_hidden");
String options=r.getString("options");
String condition=r.getString("condition");
String onchange=r.getString("onchange");
String timestamp=r.getString("timestamp");
String is_active=r.getString("is_active");
String order_no=r.getString("order_no");
String show_section=r.getString("show_section");
  
   //_____________________________________
   
   
    String val="";
    
    try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0"))
    {
        System.out.println("ID ni "+id);
        System.out.println(""+jo.toString());
    if(jo.get(id)!=null)
    {
       
        JSONObject joage=(JSONObject) jo.get(id);
        
         val=joage.get("value").toString();
        System.out.println("Value ya "+id+" ni "+val);
    }
        
    
    }
        }
        catch(NoSuchElementException ex)
        {
        
        }
     
    
 
     
    
   
  if(field_type.equals("input"))
  {
  indicators+=""+buildInputField(field_type, is_future_date, element_id, val, label, readonly, label, is_hidden, required, js_class, guide, condition, show_section, section, onchange, options);
  }
  else if(field_type.equals("date"))
  {
  indicators+=""+buildInputField(field_type, is_future_date, element_id, val, label, readonly, label, is_hidden, required, js_class, guide, condition, show_section, section, onchange, options);
  }
  else if(field_type.equals("select"))
  {
  indicators+=""+buildSelectField(field_type, is_future_date, element_id, val, label, readonly, label, is_hidden, required, js_class, guide, condition, show_section, section, onchange, options);
  }
  else if(field_type.equals("text_area"))
  {
  indicators+=""+buildTextField(field_type, is_future_date, element_id, val, label, readonly, label, is_hidden, required, js_class, guide, condition, show_section, section, onchange, options);
  }
   else 
  {
  indicators+="<div style='color:red;'>Element id "+element_id+" is not well defined</div>";
  }
    

        

count++;
        }


return indicators+"</td></tr></tbody>";

}


public JSONObject getData( dbConn conn, String reportingdate, String facilitymfl,String patientId) throws SQLException{
    JSONArray ja= new JSONArray();
    


int hasdata=0;

String getdata=" select * from internal_system.see_data where linelisting_month='"+reportingdate+"' and patient_id='"+patientId+"'";

conn.rs1=conn.st1.executeQuery(getdata);

   JSONObject lengthobject= new JSONObject();
JSONObject hm= new JSONObject();
        while (conn.rs1.next()) 
        {
 
 JSONObject hm2= new JSONObject();
hasdata++;
//we want something like this {"6":{"value":0,"bl15_Female":1,"ab15_Male":2,"ab15_Female":1}}
hm2.put("value", conn.rs1.getString("value"));
hm2.put("indicator_id", conn.rs1.getString("indicator_id"));
hm2.put("patient_id", conn.rs1.getString("patient_id"));
hm2.put("linelisting_month", conn.rs1.getString("linelisting_month"));
hm2.put("user_id", conn.rs1.getString("user_id"));
hm2.put("last_updated", conn.rs1.getString("last_updated"));
hm2.put("timestamp", conn.rs1.getString("timestamp"));
hm2.put("facility_id", conn.rs1.getString("facility_id"));
hm.put(conn.rs1.getString("indicator_id"), hm2);
//ja.put(hm);
      }
        hm.put("length", hasdata);
        //lengthobject.put("length", hasdata);
        
ja.put(lengthobject);
return hm;
}







public ResultSet pullIndicators(dbConn conn, String where) throws SQLException 
{

    if(where.equals("")){where="1=1";}
    
String qry="select "
        + ""
        + "ifnull(indicator_id,'') as indicator_id, \n" +
"ifnull(Form,'') as Form, \n" +
"ifnull(section,'') as section, \n" +
"ifnull(label,'') as label,\n" +
" ifnull(guide,'') as guide,\n" +
" ifnull(element_id,'') as element_id, \n" +
" ifnull(js_class,'') as js_class, \n" +
" ifnull(field_type,'') as field_type, \n" +
" ifnull(is_future_date,'') as is_future_date, \n" +
" ifnull(readonly,'') as readonly, \n" +
" ifnull(required,'') as required, \n" +
" ifnull(is_hidden,'') as is_hidden,\n" +
" ifnull(`options`,'') as `options`,\n" +
" ifnull(`condition`,'') as `condition`, \n" +
" ifnull(onchange,'') as onchange, \n" +
" ifnull(`timestamp`,'') as `timestamp`, \n" +
" ifnull(is_active,'') as is_active, \n" +
" ifnull(order_no,'') as order_no, \n" +
" ifnull(show_section,'') as show_section"
        + " from internal_system.see_indicators where is_active='1' and "+where+"  ";

//    System.out.println(""+qry);
conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}


public JSONArray toJson(ResultSet res) 
        throws SQLException
{

    
    
    
    
JSONArray jo2 = new JSONArray();
int count=0;

while(res.next())
{
    
JSONObject jo = new JSONObject(); 
String  indicator_id="";
String  Form="";
String  section="";
String  label="";
String  guide="";
String  id="";
String  js_class="";
String  field_type="";
String  readonly="";
String  required="";
String  is_hidden="";
String  options="";
String  condition="";
String  onchange="";
String  show_section="";

indicator_id=res.getString("indicator_id");
Form=res.getString("Form");
section=res.getString("section");
label=res.getString("label");
guide=res.getString("guide");
id=res.getString("id");
js_class=res.getString("js_class");
field_type=res.getString("field_type");
readonly=res.getString("readonly");
required=res.getString("required");
is_hidden=res.getString("is_hidden");
options=res.getString("options");
condition=res.getString("condition");
onchange=res.getString("onchange");
show_section=res.getString("show_section");

jo.put("indicator_id",indicator_id);
jo.put("Form",Form);
jo.put("section",section);
jo.put("label",label);
jo.put("guide",guide);
jo.put("id",id);
jo.put("js_class",js_class);
jo.put("field_type",field_type);
jo.put("readonly",readonly);
jo.put("required",required);
jo.put("is_hidden",is_hidden);
jo.put("options",options);
jo.put("condition",condition);
jo.put("onchange",onchange);
jo.put("show_section",show_section);

jo2.put(jo);
    
count++;
    
}
    
return jo2;    
}
public JSONArray toJsonDynamic(ResultSet res) 
        throws SQLException
{

    
int count1=0;

  ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();

         
        int count = count1;
        ArrayList mycolumns = new ArrayList();
    
    
    
JSONArray jo2 = new JSONArray();




while(res.next())
{
    
     if (count == (count1)) 
     {

                for (int i = 1; i <= columnCount; i++) {
                    mycolumns.add(metaData.getColumnLabel(i));                    
                  
                }//end of for loop
                count++;
            }//end of if
    
    
    
JSONObject jo = new JSONObject(); 

for(int c=0;c<mycolumns.size();c++){
    
jo.put(mycolumns.get(c).toString(),res.getString(mycolumns.get(c).toString()));


}



jo2.put(jo);
    
count++;
    
}
    
return jo2;    
}


public String buildInputField(String field_type, String is_future_date, String id, String Value, String label, String readonly, String placeholder, String is_hidden, String required,String js_class, String guide, String condition, String show_section,String section,String onchange,String opts){
String finalelement="";
//___Required attribute
String req_asterick="";
String req_elem="";
if(required.equalsIgnoreCase("yes")){
req_asterick="<font color='red'>*</font>";
req_elem="required";
}


//___Read only___
String readonly_elem="";
if(readonly.equals("yes")){
readonly_elem="readonly='true'";
}

//___date element

String isdate="";
String isdateclass="";
if(field_type.equals("date")){
isdate="dates";

}

if(is_future_date.equals("0")&& field_type.equals("date")){
isdateclass="data-date-end-date='0d'";
}
//___future date element

String showstatus="";
if(is_hidden.equals("yes")){showstatus="display:none;";}


String section_n="";
if(show_section.equals("1")){section_n="<br/><div class='form-group col-md-12' style='background-color:#4b8df8;text-align:center;padding-top:2px;padding-bottom:2px;'><b>"+section+"</b></div><br/>";}else{section_n="";}


finalelement=""+section_n
        + "<div class='form-group col-md-3 "+js_class+"' style="+showstatus+">" +
"<label>" +
req_asterick+"<b>"+label+"</b>\n" +
"</label>\n" +
"<input "+req_elem+" "+readonly_elem+" value='"+Value+"' onchange='"+onchange+"' placeholder='"+guide+"' title='"+guide+"'  autocomplete='off' "+isdateclass+"  class='form-control "+isdate+" "+js_class+"' type='text' name='"+id+"' id='"+id+"' />" +
"</div>"
        + ""
        + "";



return finalelement;


}

public String buildTextField(String field_type, String is_future_date, String id, String Value, String label, String readonly, String placeholder, String is_hidden, String required,String js_class, String guide, String condition, String show_section,String section,String onchange,String opts){
String finalelement="";
//___Required attribute
String req_asterick="";
String req_elem="";
if(required.equalsIgnoreCase("yes")){
req_asterick="<font color='red'>*</font>";
req_elem="required";
}


//___Read only___
String readonly_elem="";
if(readonly.equals("Yes")){
readonly_elem="readonly='true'";
}

//___date element

String isdate="";
String isdateclass="";
if(field_type.equals("date")){
isdate="dates";

}

if(is_future_date.equals("0")&& field_type.equals("date")){
isdateclass="data-date-end-date='0d'";
}
//___future date element

String showstatus="";
if(is_hidden.equals("yes")){showstatus="display:none;";}


String section_n="";
if(show_section.equals("1")){section_n="<br/><div class='form-group col-md-12' style='background-color:#4b8df8;text-align:center;padding-top:2px;padding-bottom:2px;'><b>"+section+"</b></div><br/>";}else{section_n="";}


finalelement=""+section_n
        + "<div class='form-group col-md-3 "+js_class+"' style="+showstatus+">" +
"<label>" +
req_asterick+"<b>"+label+"</b>\n" +
"</label>\n" +
"<textarea "+req_elem+" "+readonly_elem+"  placeholder='"+guide+"' title='"+guide+"' onchange='"+onchange+"' rows='1' cols='50'   autocomplete='off' "+isdateclass+"  class='form-control "+js_class+"'  name='"+id+"' id='"+id+"' >"+Value+"</textarea>" +
"</div>"
        + ""
        + "";



return finalelement;


}


public String buildSelectField(String field_type, String is_future_date, String id, String Value, String label, String readonly, String placeholder, String is_hidden, String required,String js_class, String guide, String condition, String show_section,String section,String onchange,String opts){
String finalelement="";
//___Required attribute
String req_asterick="";
String req_elem="";
if(required.equalsIgnoreCase("yes")){
req_asterick="<font color='red'>*</font>";
req_elem="required";
}


//___Read only___
String readonly_elem="";
if(readonly.equals("Yes")){
readonly_elem="readonly='true'";
}

//___date element

String isdate="";
String isdateclass="";
if(field_type.equals("date")){
isdate="dates";

}

if(is_future_date.equals("0")&& field_type.equals("date")){
isdateclass="data-date-end-date='0d'";
}
//___future date element

String showstatus="";
if(is_hidden.equals("yes")){showstatus="display:none;";}


String section_n="";
if(show_section.equals("1")){section_n="<br/><div class='form-group col-md-12' style='background-color:#4b8df8;text-align:center;padding-top:2px;padding-bottom:2px;'><b>"+section+"</b></div><br/>";}else{section_n="";}

//String conditionfun="";
//String changefunction="";
//if(condition!=null){
//conditionfun="isShowOnlyIf(\'"+condition+"\');";
//}
//if(onchange!=null){
//changefunction="isToggleDisplay(\'"+onchange+"\');";
//}

finalelement=""+section_n
        + "<div class='form-group col-md-3 "+js_class+"' style="+showstatus+">" +
"<label>" +
req_asterick+"<b>"+label+"</b>\n" +
"</label>\n" +
"<select "+req_elem+" "+readonly_elem+"  onchange='"+onchange+"'  title='"+guide+"'     class='form-control "+js_class+"' type='text' name='"+id+"' id='"+id+"' >"
        +buildopts(opts, Value)
        +"</select>" +
"</div>"
        + ""
        + "";



return finalelement;


}

public  String buildopts(String opts, String value){

String finalopts="<option value=''>select option</option>";
//Yes|Yes:No|No
//System.out.println(""+opts);

String valkey[]=opts.split(":");
for(int s=0;s<valkey.length;s++){

  String valkey_in[]=valkey[s].split("\\|");  
   
  
   String selected="";
  if(value.equals(valkey_in[0])){selected="selected";}
   
 finalopts+="<option "+selected+" value='"+valkey_in[0]+"'>"+valkey_in[1]+"</option>";
    //System.out.println("valkey_in 1"+valkey_in[0]);
    //System.out.println("valkey_in 2"+valkey_in[1]);

}



return finalopts;
    
    
}
    
public int getRandNo(int start, int end ){
        Random random = new Random();
        long fraction = (long) ((end - start + 1 ) * random.nextDouble());
        return ((int)(fraction + start));
    }
  

public ResultSet pullElementsBySection(dbConn conn, String Sectionname) throws SQLException 
{
    
    //This method gets data that belongs to a specific form only
    String where="";

    if(Sectionname.equals("")){where="";} else {where="and  Form in ('"+Sectionname+"')";}
    
String qry="select  ifnull(element_id,'') as element_id,client_identifier_field from internal_system.see_indicators where is_active='1' "+where+" ";

    System.out.println(""+qry);
conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}



}
