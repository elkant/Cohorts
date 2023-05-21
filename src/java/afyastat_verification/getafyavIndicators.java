/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afyastat_verification;

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
public class getafyavIndicators extends HttpServlet {

    
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
           out.println(getHtmlTableMobile(conn,dt,fc));
           
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
            Logger.getLogger(getafyavIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getafyavIndicators.class.getName()).log(Level.SEVERE, null, ex);
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
String indicators="<thead><tr style='background-color:#9f9999;color:white;'><th>Indicator</th>"
        + "<th>Form_1A</th>"
        + "<th>KHIS</th>"
        + "<th>NDWH</th>"
        + "<th>KenyaEMR</th>"
        + "<th> MOH_731 </th>"
        + "<th>Registers</th>"
       // + "<th>Variance</th>"
        + "</tr></thead><tbody>";

    


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
    
    
    
String imis="";
String datim="";
String khis="";
String ndwh="";
String emr="";
String moh731="";
String register="";
String variance="";

    
    
     try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        
   if(jo.get(id)!=null){
        
       JSONObject joage=(JSONObject) jo.get(id);
        
        
imis=joage.get("imis").toString();
datim=joage.get("datim").toString();
khis=joage.get("khis").toString();
ndwh=joage.get("ndwh").toString();
emr=joage.get("emr").toString();
moh731=joage.get("moh731").toString();
register=joage.get("register").toString();
variance=joage.get("variance").toString();

         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     
     String readonly_variance=" readonly='true' ";
     
    String displaysection="";
    
    if(showsection.equals("1"))
    {
    displaysection="<tr style='background-color:#4b8df8;'><td ><b>"+section_name+"</b></td>"
            + "<td>Form 1a</td>"
            + "<td>KHIS</td>"
            + "<td>NDWH</td>"
            + "<td>EMR</td>"
            + "<td>MOH 731</td>"
            + "<td>Registers</td>"
           // + "<td>Variance</td></tr>"
            + "";
    }
    else {
    displaysection="";
    }
     
     //Baseline Information	Code	Male 10-19yrs	Female 10-19yrs	Total
//onkeyup='subofindicators(\""+id+"_register@"+id+"_emr\",\""+id+"_variance\");'
     //sumofindicators(sourceindicators,destinationindicator)
     String inputimis="<input "+readonly_variance+" value='"+imis+"'   onkeypress='return numbers(event); ' placeholder='IMIS'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_imis' id='"+id+"_imis' class='form-control inputs'>"; 
     String inputdatim="<input  value='"+datim+"'   onkeypress='return numbers(event); ' placeholder='Datim'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_datim' id='"+id+"_datim' class='form-control inputs'>"; 
     String inputkhis="<input "+readonly_variance+"  value='"+khis+"'   onkeypress='return numbers(event); ' placeholder='KHIS'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_khis' id='"+id+"_khis' class='form-control inputs'>"; 
     String inputndwh="<input "+readonly_variance+" value='"+ndwh+"'   onkeypress='return numbers(event); ' placeholder='NDWH'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_ndwh' id='"+id+"_ndwh' class='form-control inputs'>"; 
     String inputemr="<input  value='"+emr+"'   onkeypress='return numbers(event); ' placeholder='EMR'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_emr' id='"+id+"_emr' class='form-control inputs'>"; 
     String inputmoh731="<input  value='"+moh731+"'   onkeypress='return numbers(event); ' placeholder='MOH 731'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_moh731' id='"+id+"_moh731' class='form-control inputs'>"; 
     String inputregister="<input  value='"+register+"'   onkeypress='return numbers(event); ' placeholder='Register'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_register' id='"+id+"_register' class='form-control inputs'>"; 
     String inputvariance="<input "+readonly_variance+" tabindex='-1'   value='"+variance+"'   onkeypress='return numbers(event); ' placeholder='Variance'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_variance' id='"+id+"_variance' class='form-control inputs'>"; 
     
    //imis	datim	khis	ndwh	emr	moh731	register	variance
indicators+=""+displaysection
        + "<tr>"
        + "<td style='vertical-align: middle;' rowspan='1'> <span class='badge'>"+count+"  </span><b> "+indic+"</b></td>"
        //+ "<td>"+indicator_code+"</td>"
       
        + "<td>"+inputimis+"</td>"
        + "<td>"+inputkhis+"</td>"
        + "<td>"+inputndwh+"</td>"
        + "<td>"+inputemr+"</td>"
        + "<td>"+inputmoh731+"</td>"
        + "<td>"+inputregister+"</td>"
        //+ "<td>"+inputvariance+"</td>"
        + "</tr>";
        

count++;
                     }


return indicators+"</tbody>";

}


public JSONObject getData( dbConn conn, String reportingdate, String facilitymfl) throws SQLException{
    JSONArray ja= new JSONArray();
    


int hasdata=0;

String getdata=" select `id`,\n" +
"    `yearmonth`,\n" +
"    `facility`,\n" +
"    `indicatorid`,\n" +
"    ifnull(`imis`,'') as imis,\n" +
"    ifnull(`datim`,'') as datim,\n" +
"    ifnull(`khis`,'') as khis,\n" +
"    ifnull(`ndwh`,'') as ndwh,\n" +
"    ifnull(`emr`,'') as emr,\n" +
"    ifnull(`moh731`,'') as moh731,\n" +
"    ifnull(`register`,'') as register,\n" +
"    ifnull(`variance`,'') as variance,\n" +
"    `timestamp`,\n" +
"    `lastupdated`,\n" +
"    `userid` from internal_system.afyav_data where yearmonth='"+reportingdate+"' and facility='"+facilitymfl+"'";

conn.rs1=conn.st1.executeQuery(getdata);

   JSONObject lengthobject= new JSONObject();
JSONObject hm= new JSONObject();
        while (conn.rs1.next()) 
        {
 
 JSONObject hm2= new JSONObject();
hasdata++;
  //we want something like this {"HTS_TST":{"bl15_Male":0,"bl15_Female":1,"ab15_Male":2,"ab15_Female":1}}
  
  String imis="";
String datim="";
String khis="";
String ndwh="";
String emr="";
String moh731="";
String register="";
String variance="";


hm2.put("imis", conn.rs1.getString("imis"));
hm2.put("datim", conn.rs1.getString("datim"));
hm2.put("khis", conn.rs1.getString("khis"));
hm2.put("ndwh", conn.rs1.getString("ndwh"));
hm2.put("emr", conn.rs1.getString("emr"));
hm2.put("moh731", conn.rs1.getString("moh731"));
hm2.put("register", conn.rs1.getString("register"));
hm2.put("variance", conn.rs1.getString("variance"));
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

String qry="select * from internal_system.afyav_indicators where is_active='1' order by order_no asc";


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




public String getHtmlTableMobile(dbConn conn, String reportingdate, String facility) throws SQLException{
String indicators="<thead><tr style='background-color:#9f9999;color:white;'><th>Indicator</th><th>Data Source</th><th>Data Value</th>"
       
        + "</tr></thead><tbody>";

    


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
    
    
    
String imis="";
String datim="";
String khis="";
String ndwh="";
String emr="";
String moh731="";
String register="";
String variance="";

    
    
     try{ 
    //if length is greater than 0
    if(!jo.get("length").toString().equals("0")){
      
        
   if(jo.get(id)!=null){
        
       JSONObject joage=(JSONObject) jo.get(id);
        
        
imis=joage.get("imis").toString();
datim=joage.get("datim").toString();
khis=joage.get("khis").toString();
ndwh=joage.get("ndwh").toString();
emr=joage.get("emr").toString();
moh731=joage.get("moh731").toString();
register=joage.get("register").toString();
variance=joage.get("variance").toString();

         
   }
        
    
    }
        }
        catch(NoSuchElementException ex){
        
        }
     
     
     String readonly_variance=" readonly='true' ";
     
    String displaysection="";
    
    if(showsection.equals("1"))
    {
    displaysection="<tr style='background-color:#4b8df8;'><td ><b>"+section_name+"</b></td>"
            + "<td>Data Source</td>"
            + "<td>Reported Value</td>"
//            + "<td>NDWH</td>"
//            + "<td>EMR</td>"
//            + "<td>MOH 731</td>"
//            + "<td>Registers</td>"
           // + "<td>Variance</td></tr>"
            + "";
    }
    else {
    displaysection="";
    }
     
     //Baseline Information	Code	Male 10-19yrs	Female 10-19yrs	Total
//onkeyup='subofindicators(\""+id+"_register@"+id+"_emr\",\""+id+"_variance\");'
     //sumofindicators(sourceindicators,destinationindicator)
     String inputimis="<input "+readonly_variance+" value='"+imis+"'   onkeypress='return numbers(event); ' placeholder='IMIS'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_imis' id='"+id+"_imis' class='form-control inputs'>"; 
     String inputdatim="<input  value='"+datim+"'   onkeypress='return numbers(event); ' placeholder='Datim'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_datim' id='"+id+"_datim' class='form-control inputs'>"; 
     String inputkhis="<input "+readonly_variance+"  value='"+khis+"'   onkeypress='return numbers(event); ' placeholder='KHIS'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_khis' id='"+id+"_khis' class='form-control inputs'>"; 
     String inputndwh="<input "+readonly_variance+" value='"+ndwh+"'   onkeypress='return numbers(event); ' placeholder='NDWH'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_ndwh' id='"+id+"_ndwh' class='form-control inputs'>"; 
     String inputemr="<input  value='"+emr+"'   onkeypress='return numbers(event); ' placeholder='EMR'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_emr' id='"+id+"_emr' class='form-control inputs'>"; 
     String inputmoh731="<input  value='"+moh731+"'   onkeypress='return numbers(event); ' placeholder='MOH 731'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_moh731' id='"+id+"_moh731' class='form-control inputs'>"; 
     String inputregister="<input  value='"+register+"'   onkeypress='return numbers(event); ' placeholder='Register'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_register' id='"+id+"_register' class='form-control inputs'>"; 
     String inputvariance="<input "+readonly_variance+" tabindex='-1'   value='"+variance+"'   onkeypress='return numbers(event); ' placeholder='Variance'  type='tel' maxlength='4' min='0' max='9999' name='"+id+"_variance' id='"+id+"_variance' class='form-control inputs'>"; 
     
    //imis	datim	khis	ndwh	emr	moh731	register	variance
indicators+=""+displaysection
        + "<tr>"
        + "<td style='vertical-align: middle;' rowspan='6'> <span class='badge'>"+count+"  </span><b> "+indic+"</b></td><td>Form 1a</td> <td>"+inputimis+"</td></tr>"
        //+ "<td>"+indicator_code+"</td>"
       
       
        + "<tr><td>KHIS</td><td>"+inputkhis+"</td></tr>"
        + "<tr><td>NDWH</td><td>"+inputndwh+"</td></tr>"
        + "<tr><td>KenyaEMR</td><td>"+inputemr+"</td></tr>"
        + "<tr><td>MOH 731</td><td>"+inputmoh731+"</td></tr>"
        + "<tr><td>Registers <br/>(All HTS registers)</td><td>"+inputregister+"</td></tr>"
        //+ "<td>"+inputvariance+"</td>"
        + "";
        

count++;
                     }


return indicators+"</tbody>";

}



}
