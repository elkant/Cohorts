/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retention;

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
public class ret_getIndicators extends HttpServlet {

    
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
            Logger.getLogger(ret_getIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ret_getIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
        + "<th rowspan='2'>Indicator</th>"
        + "<th colspan='2'>0-9</th>"
        + "<th colspan='2'>10-14</th>"
        + "<th colspan='2'>15-19</th>"
        + "<th colspan='2'>20-24</th>"
        + "<th colspan='2'>25+</th>"
        + "<th rowspan='2'>Total</th>"
        + "</tr>"
        + "<tr>"
        + ""
        + "<th>M</th>"
        + "<th>F</th>"
        + "<th>M</th>"
        + "<th>F</th>"
        + "<th>M</th>"
        + "<th>F</th>"
        + "<th>M</th>"
        + "<th>F</th>"
        + "<th>M</th>"
        + "<th>F</th>"
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
    
    String displaysection="";
    
    if(showsection.equals("1"))
    {
    displaysection="<tr style='background-color:#4b8df8;'><td ><b>"+section_name+"</b></td><td><b>0-9 M</b></td><td><b>0-9 F</b></td><td><b>10-14 M</b></td><td><b>10-14 F</b></td><td><b>15-19 M</b></td><td><b>15-19 F</b></td><td><b>20-24 M</b></td><td><b>20-24 F</b></td><td><b>25+ M</b></td><td><b>25+ F</b></td><td><b>Total</b></td></tr>";
    }
    else {
    displaysection="";
    }
    
    
//  System.out.println("__"+jo.toString());  
    String indic=r.getString("indicatorname");
    String id=r.getString("id");
    

    String _9m="";
    String _9f="";
    String _14m="";
    String _14f="";
    String _19m="";
    String _19f="";
    String _24m="";
    String _24f="";
    String _25m="";
    String _25f="";
    String _ttl="";
    
     try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        //pull data by column
   if(jo.get(id)!=null){
        
       JSONObject joage=(JSONObject) jo.get(id);
         _9m=joage.get("9m").toString();
         _9f=joage.get("9f").toString();
         _14m=joage.get("14m").toString();
         _14f=joage.get("14f").toString();
         _19m=joage.get("19m").toString();
         _19f=joage.get("19f").toString();
         _24m=joage.get("24m").toString();
         _24f=joage.get("24f").toString();
         _25m=joage.get("25m").toString();
         _25f=joage.get("25f").toString();
         _ttl=joage.get("ttl").toString();
         
         
         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     String readonly_value="";

     

     String   inputtotal="<td><input "+readonly_value+" value='"+_9m+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_9m' id='"+id+"_9m' class='form-control inputs'></td>"
                        +"<td><input "+readonly_value+" value='"+_9f+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_9f' id='"+id+"_9f' class='form-control inputs'></td>"
                       + "<td><input "+readonly_value+" value='"+_14m+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_14m' id='"+id+"_14m' class='form-control inputs'></td>" 
                       + "<td><input "+readonly_value+" value='"+_14f+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_14f' id='"+id+"_14f' class='form-control inputs'></td>" 
                       + "<td><input "+readonly_value+" value='"+_19m+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_19m' id='"+id+"_19m' class='form-control inputs'></td>"
                       + "<td><input "+readonly_value+" value='"+_19f+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_19f' id='"+id+"_19f' class='form-control inputs'></td>"
                       + "<td><input "+readonly_value+" value='"+_24m+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_24m' id='"+id+"_24m' class='form-control inputs'></td>" 
                       + "<td><input "+readonly_value+" value='"+_24f+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_24f' id='"+id+"_24f' class='form-control inputs'></td>" 
                       + "<td><input "+readonly_value+" value='"+_25m+"' onkeyup='sum_indicators(\""+id+"\");' onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_25m' id='"+id+"_25m' class='form-control inputs'></td>" 
                       + "<td><input "+readonly_value+" value='"+_25f+"' onkeyup='sum_indicators(\""+id+"\");'  onkeypress='return numbers(event);'   type='tel' maxlength='4' min='0' max='9999' name='"+id+"_25f' id='"+id+"_25f' class='form-control inputs'></td>" 
                       + "<td><input "+readonly_value+" value='"+_ttl+"' tabindex='-1'  readonly='true' placeholder='Total '  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_ttl' id='"+id+"_ttl' class='form-control inputs'></td>"; 
   
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


public JSONObject getData( dbConn conn, HashMap par) throws SQLException{
    JSONArray ja= new JSONArray();
    

int hasdata=0;

String getdata=" select * from internal_system.ret_data where yearmonth='"+par.get("yearmonth")+"' and facility ='"+par.get("facility")+"'  ";
  
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

hm2.put("9m", conn.rs1.getString("9m"));
hm2.put("9f", conn.rs1.getString("9f"));
hm2.put("14m", conn.rs1.getString("14m"));
hm2.put("14f", conn.rs1.getString("14f"));
hm2.put("19m", conn.rs1.getString("19m"));
hm2.put("19f", conn.rs1.getString("19f"));
hm2.put("24m", conn.rs1.getString("24m"));
hm2.put("24f", conn.rs1.getString("24f"));
hm2.put("25m", conn.rs1.getString("25m"));
hm2.put("25f", conn.rs1.getString("25f"));
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

String qry="select * from internal_system.ret_indicators where active='1' order by orodha asc";


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

    id =res.getString("id");
    indicator_code =res.getString("indicator_code");
    indicatorname =res.getString("indicatorname");
    orodha =res.getString("orodha");
    show_section =res.getString("show_section");
    section_name =res.getString("section");

    jo.put("id",id);
    jo.put("indicator_code",indicator_code);
    jo.put("indicatorname",indicatorname);
    jo.put("orodha",orodha);
    jo.put("show_section",show_section);
    jo.put("show_section",show_section);
    jo.put("section_name",section_name);
    jo2.put(jo);
    
    count++;
    
}


    
    
return jo2;    
}


}
