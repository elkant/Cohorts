/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rri_gaps;

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
public class loadindicators extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
            String ym="";
            
            String fc="";
          
            String scid="";
            
            if(request.getParameter("fc")!=null){fc=request.getParameter("fc");}
            if(request.getParameter("ym")!=null){ym=request.getParameter("ym");}
            if(request.getParameter("scid")!=null){scid=request.getParameter("scid");}
  
            
            
            
            
            HashMap params = new HashMap<String, String>();
            
            params.put("yearmonth", ym);
            params.put("facility", fc);
           
            
           dbConn conn = new dbConn();
           if(!fc.equals("")&&!ym.equals("")){
           //return tables
           out.println(getHtmlTable(conn,params));
           
           }
            else if(!scid.equals("")) {
           //return indicators
           ResultSet r=pullIndicators(conn,scid);
           
           out.println(toJson(r));
          
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
            Logger.getLogger(loadindicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(loadindicators.class.getName()).log(Level.SEVERE, null, ex);
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

    
    
String indicators="";
       

 JSONObject jo= getData(conn, pms);
    //Get a resultset of all th e indicators and loop through each as you check if the same data has been entered
int count=1;
ResultSet r=pullIndicators(conn);


String id="";
String scn="";
String scnid="";
String scnidcopy="";

String ind="";
String cd="";
String lv1="";
String lv2="";
String rdo="";
String dtp="";
String ac="";
String ov="";
String os="";
String lsr="";
String dbn="";
String ord="";

String clsp="";
String eos="";
String scncode="";

while(r.next()){
    
    
    //check if indicator has changed
id=r.getString("id");
scnid=r.getString("section");
scn=r.getString("section_name");
ind=r.getString("indicator");
cd=r.getString("code");
lv1=r.getString("level1");
lv2=r.getString("level2");
rdo=r.getString("readonl");
dtp=r.getString("datatp");
ac=r.getString("Auto_Calculate");
ov=r.getString("options_value");
os=r.getString("options_show");
lsr=r.getString("lastspanrow");
dbn=r.getString("database_name");
ord=r.getString("orodha");
clsp=r.getString("colspan");
eos=r.getString("endofsection");
scncode=r.getString("section_code");

 String value="";
 String prd="";
    
     try
     { 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
    //pull data by column
   if(jo.get(id)!=null)
   {
        
         JSONObject joage=(JSONObject) jo.get(id);
         value=joage.get("value").toString();
         prd=joage.get("period").toString();
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }


if(!scnid.equals(scnidcopy))
    {
        scnidcopy=scnid;
        String showsection="style='display:none;'";
        String hasdata="style='color:red;width:100%;text-align:left;'";
        
        if(!value.equals("")){showsection="";hasdata="style='display:none;'";}
        
    //create the card
    indicators+= "<h4 class='btn grey' "+hasdata+" id='hasdata'><i class='glyphicon glyphicon-close'></i><b>No Gaps to account under "+scn+"</b></h4><div "+showsection+" class='card'>";
    indicators+= "<div class='card-header' >"
    +"<h5 class='mb-0'>";
    indicators+="<button class='btn blue collapsed' id='section"+scnid+"' type='button' data-toggle='collapse' data-target='#collapse"+scnid+"' aria-expanded='false' aria-controls='collapse"+scnid+"' style='width:100%; text-align:left;background-color:#0394ff;font-weight: bolder;'>" +
"["+scnid+"]. "+" "+scn.toUpperCase()+
"</button>" +
"</h5>" +
"</div>"
+ "<div id='collapse"+scnid+"' class='collapse' aria-labelledby='heading"+scnid+"' data-parent='#form1a_accordion'>" +
"<div class='card-body'>" +
"<div id='table' style='margin-right:0%'>" +
"<fieldset class='formatter' style='margin:20px; color:black;'>" +
"<legend class='formatter'>" +
"<b style='text-align:center;color:#0394ff;'>"+scn+"</b>" +

"</legend>" +
"<form action='#' id='"+dbn+"' method='post' class='form-horizontal'>"
+ " <table border='1px;' class='table table-condensed'>" +
"<thead>" +
"<tr style='font-weight:bold; background:#40454a;color:white;'>" +
//"<th>#</th>" +
"<th>Indicator Code</th>" +
"<th>Sub-Section</th>" +
"<th>Indicator</th>" +
"<th>Value</th>" +
"</tr>" +
"</thead>"+
 "<tbody>";
   }
    
   
// ___________________Now inside the other rows____________

indicators+="<tr>" +
        "<td>"+cd+"<input type='hidden' id='indic_pos_"+id+"' name='indic_pos_"+id+"' value='"+id+"'/></td>" ;

if(!clsp.equals("0"))
{
indicators+="<td rowspan='"+clsp+"'>"+lv1+"</td>";
}


  String finalprd="";
  
  if(!prd.equals("") && !prd.equals("null") ){finalprd="<br/><font color='orange'><b>["+prd+"]</b></font>";}
     
indicators+="<td>"+ind+" "+finalprd+"</td>";



// build autocalculate

String hasautocalc="";

if(ac!=null && !ac.equals("") && !ac.equals("null") ){

hasautocalc="buildAutocalculate('"+ac+"','"+scnid+"');";

}

        
   //create a data input field or select field
  if(dtp.equals("select"))
  {
  indicators+="<td><select onblur=\"indicate_changed('"+id+"');section_changed('"+scnid+"');\" id='"+id+"' name='"+id+"'>"+buildSelectOptions(os, ov, value)+"</select></td>";
  }   
    
  else {
indicators+="<td><input  "+rdo+" onkeyup=\"removeFirstZero('"+id+"');"+hasautocalc+"\" type='text' name='"+id+"' id='"+id+"' value='"+value+"' onblur=\"indicate_changed('"+id+"');section_changed('"+scnid+"');\"  class='data-cell' data-toggle='tooltip' data-placement='right' autocomplete='off' maxlength='5' onkeypress='return numbers(event);'></td>" ;
  }
indicators+="</tr>";

    if(eos.equals("1") )
    {
        //System.out.println(scnid+" vs "+scnidcopy2);
        

 indicators+="</tbody></table><input type='hidden' name='num_indicators' id='num_indicators' value='"+count+"'>" +
"<input type='hidden' name='table_name' id='table_name' value='"+dbn+"'>" +
"<div class='form-actions' style='text-align:right;'>" +
"<label id='msg"+scnid+"' class='feedback' style='text-align:left;color:red;'></label>" +
"<button  type='button' class='btn blue' data-save_1='SAVE' onclick=\"loadValidation('"+dbn+"','"+scnid+"');\" name='validate_"+scnid+"' id='validate_"+scnid+"' style=' font-size:14px; width:20%;'>Save "+scncode+"</button>" +
"</div>" +
"</form>" +
"</fieldset>" +
"</div>" +
"</div>" +
"</div>"+
"</div>";
    }
     
 String readonly_value="";


        

count++;

                     }


   // System.out.println(""+indicators);

return indicators;

}

//get data values for editing purpose
public JSONObject getData( dbConn conn, HashMap par) throws SQLException {
    JSONArray ja= new JSONArray();
    

int hasdata=0;

String getdata=" select * from internal_system.rri_gaps_data where yearmonth='"+par.get("yearmonth")+"' and facility_id='"+par.get("facility")+"' and indicator_id not in (1,10,13,16,40,41,42,43) "
        + " UNION ALL "
        + " select * from internal_system.rri_gaps_baseline where yearmonth='"+par.get("yearmonth")+"' and facility_id='"+par.get("facility")+"' ;";
  

conn.rs1=conn.st1.executeQuery(getdata);

JSONObject lengthobject= new JSONObject();

JSONObject hm= new JSONObject();

while (conn.rs1.next()) 
{
JSONObject hm2= new JSONObject();

hasdata++;
//we want something like this {"KITS Assisted":{"value":0}}
hm2.put("value", conn.rs1.getString("value"));
hm2.put("period", conn.rs1.getString("period"));

hm.put(conn.rs1.getString("indicator_id"), hm2);
//ja.put(hm);
}
        hm.put("length", hasdata);
        //lengthobject.put("length", hasdata);
        
ja.put(lengthobject);
return hm;
}






//getIndicators done
public ResultSet pullIndicators(dbConn conn) throws SQLException {

String qry="select * from internal_system.rri_gaps_indicators where is_active='1' order by orodha asc";
    System.out.println(""+qry);

conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}
public ResultSet pullIndicators(dbConn conn, String Sectionid) throws SQLException {

String qry="select * from internal_system.rri_gaps_indicators where is_active='1' and section='"+Sectionid+"' order by orodha asc";
    System.out.println(""+qry);

conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}

//done
public JSONArray toJson(ResultSet res) throws SQLException{

    
    
JSONArray jo2 = new JSONArray();
int count=0;

while(res.next())
{
    
JSONObject jo = new JSONObject(); 

String id="";
String section_name="";
String section="";
String indicator="";
String code="";
String level1="";
String level2="";
String readonl="";
String datatp="";
String Auto_Calculate="";
String options_value="";
String options_show="";
String is_active="";
String lastspanrow="";
String database_name="";
String orodha="";
String colspan="";
String endofsection="";
String section_code="";

    id =res.getString("id");
    section_name =res.getString("section_name");
    section =res.getString("section");
    indicator =res.getString("indicator");
    code =res.getString("code");
    level1 =res.getString("level1");
    level2 =res.getString("level2");
    readonl =res.getString("readonl");
    datatp =res.getString("datatp");
    Auto_Calculate =res.getString("Auto_Calculate");
    options_value =res.getString("options_value");
    options_show =res.getString("options_show");
    is_active =res.getString("is_active");
    lastspanrow =res.getString("lastspanrow");
    database_name =res.getString("database_name");
    orodha =res.getString("orodha");
    colspan =res.getString("colspan");
    endofsection =res.getString("endofsection");
    section_code =res.getString("section_code");

    jo.put("id",id);
    jo.put("section_name",section_name);
    jo.put("section",section);
    jo.put("indicator",indicator);
    jo.put("code",code);
    jo.put("level1",level1);
    jo.put("level2",level2);
    jo.put("readonl",readonl);
    jo.put("datatp",datatp);
    jo.put("Auto_Calculate",Auto_Calculate);
    jo.put("options_value",options_value);
    jo.put("options_show",options_show);
    jo.put("is_active",is_active);
    jo.put("lastspanrow",lastspanrow);
    jo.put("database_name",database_name);
    jo.put("orodha",orodha);
    jo.put("colspan",colspan);
    jo.put("endofsection",endofsection);
    jo.put("section_code",section_code);
    jo2.put(jo);
    
    count++;
    
}


    
    
return jo2;    
}


public String buildSelectOptions(String opt, String val, String selected){
   
    String optsarr[]=opt.split(",");
    String valsarr[]=val.split(",");

    String fin="<option value=''>--option--</option>";
    
    for(int a=0;a<optsarr.length;a++){
        String selectedval="";
        // System.out.println("selected ni:"+selected+": vs "+valsarr[a]);
        if(valsarr[a].equals(selected))
        {
        selectedval="selected";
        }
        
    fin+="<option "+selectedval+" value='"+valsarr[a]+"'>"+optsarr[a]+"</option>";
    
    }
    
return fin;
}

}
