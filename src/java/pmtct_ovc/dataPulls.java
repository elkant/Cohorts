/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmtct_ovc;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Emmanuel Kaunda
 */
public class dataPulls extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
          dbConn conn = null;
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            
            HttpSession sess= request.getSession();
            
             conn = new dbConn();
            
            String fac="";
            
            //below variable will tell this servlet what to do
            //it will call various methods and return different values in json format
            
            
            String userregion="";
            
            
            String act="";
            String loadmtrs_sel_val="";
            String fm="";
            String table_docker="";
            //loadmtrs_sel_val,act=loadmothers,fac
            
            if(request.getParameter("act")!=null){act=request.getParameter("act");}
            if(request.getParameter("fac")!=null){fac=request.getParameter("fac");}
           
            if(request.getParameter("loadmtrs_sel_val")!=null){loadmtrs_sel_val=request.getParameter("loadmtrs_sel_val");}
            
            
            if(act.equals("loadmothers")){out.println(buildoptsFromDbResultSet(conn,pullAddedMothers(conn, fac),loadmtrs_sel_val));}
            
             if(request.getParameter("fm")!=null){fm=request.getParameter("fm");}
             if(request.getParameter("table_docker")!=null){table_docker=request.getParameter("table_docker");}
             
             //A table will load both headers and data values dynamically
            if(act.equals("showedits"))
            {               
                
               ResultSet rs1=pullAddedDataPerFormForEditing(conn,fm,fac,"sp_pmtct_ovc_pull_all_editing_data_dynamically");

                out.println(buildDataTable(sess,conn,rs1,table_docker,fm));                                               
    
            }
            
             if(act.equals("showMortalityedits"))
            {               
               
               ResultSet rs1=pullAddedDataPerFormForEditing(conn,fm,fac,"sp_mot_audit_pull_all_editing_data_dynamically");

               String tbl=buildDataTable(sess,conn,rs1,table_docker,fm);               
                //System.out.println("_______"+tbl);
                out.println(tbl);                                               
    
            }
             if(act.equals("showClinicalEdits"))
            {               
               
               ResultSet rs1=pullAddedDataPerFormForEditing(conn,fm,fac,"sp_clinical_form_pull_all_editing_data_dynamically");//edit_stored_procedure

               String tbl=buildDataTable(sess,conn,rs1,table_docker,fm);               
                //System.out.println("_______"+tbl);
                out.println(tbl);                                               
    
            }
             
              if(act.equals("showKPEdits"))
            {               
               
               ResultSet rs1=pullAddedDataPerFormForEditing(conn,fm,fac,"sp_kpvalidation_form_pull_all_editing_data_dynamically");//edit_stored_procedure

               String tbl=buildDataTable(sess,conn,rs1,table_docker,fm);               
                //System.out.println("_______"+tbl);
                out.println(tbl);                                               
    
            }
             
            
            
        }
        
        finally {   
         
                         if (conn.rs != null) {
                conn.rs.close();
            }
            if (conn.rs1 != null) {
                conn.rs1.close();
            }
            if (conn.st != null) {
                conn.st.close();
            }
            if (conn.st1 != null) {
                conn.st1.close();
            }
            if (conn.connect != null) {
                conn.connect.close();
            }
        
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
            Logger.getLogger(dataPulls.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(dataPulls.class.getName()).log(Level.SEVERE, null, ex);
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

    
    public ResultSet pullAddedMothers(dbConn conn, String facilid) throws SQLException 
{
    
    //This method gets data that belongs to a specific form only
    String where="";

    
    
String qry="call internal_system.sp_pmtct_ovc_pull_mothers('"+facilid+"');";

    System.out.println(""+qry);
conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}

    
       public ResultSet pullAddedDataPerFormForEditing(dbConn conn,String form, String facilid,String sp) throws SQLException 
{
    
    //This method gets data that belongs to a specific form only


    
    
String qry="call internal_system."+sp+"('"+facilid+"','"+form+"');";

    System.out.println("_called_query:"+qry);
conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}
       public ResultSet pullFormElementdeadersForEditing(dbConn conn,String form) throws SQLException 
{
    
    //This method gets data that belongs to a specific form only
    String where="";

    
    
String qry="call internal_system.sp_pmtct_ovc_pull_form_headers_for_editing('"+form+"');";

    System.out.println(""+qry);
conn.rs=conn.st.executeQuery(qry);


return conn.rs;

}


public  String buildoptsFromDbResultSet(dbConn cn,ResultSet res, String selectedvalue){

    
     String finalopts="<option value=''>select option</option>";
        try {
            //this method gets data from db and converts it to jsonArray, the from JSON array, get the JSONObjects in place
            
            JSONArray ja=new JSONArray();
            
            ja=toJsonFormatDynamic(res);
            
            
                    
            String opts="";     
                    for(int h=0;h<ja.length();h++){
                        JSONObject jo=ja.getJSONObject(h);
                    for(int i = 0; i<jo.names().length(); i++){
                    opts+=jo.get(jo.names().getString(i))+":";
                    }
                    }
                    
                    
                    
                   
//Yes|Yes:No|No
//System.out.println(""+opts);

String valkey[]=opts.split(":");
            System.out.println("val_key_length:"+valkey.length);

for(int s=0;s<valkey.length;s++){
    
    String valkey_in[]=valkey[s].split(",");
    
    if(valkey[s].contains(",")){
    String selected="";
    if(selectedvalue.equals(valkey_in[0])){selected="selected";}
    
    finalopts+="<option "+selected+" value='"+valkey_in[0]+"'>"+valkey_in[1]+"</option>";
    
    }
}




        } catch (SQLException ex) {
            Logger.getLogger(dataPulls.class.getName()).log(Level.SEVERE, null, ex);
        }
  return finalopts;  
    
}
  



public JSONArray toJsonFormatDynamic(ResultSet res) 
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


//[{"Tablecolumn1":"Row1Value1","Tablecolumn2":"Row1Value2"},{"Tablecolumn1":"Row2Value1","Tablecolumn2":"Row2Value2"},{"Tablecolumn1":"Row2Value1","Tablecolumn2":"Row2Value2"}]



public String buildDataTable(HttpSession ss,dbConn con, ResultSet res, String elementtoappend,String frm) throws SQLException{
    
String finaltbl="";
String hdslist_html="";
String dtlist_html="";





 
int count1=0;

  ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();

         
        int count = count1;
        ArrayList mycolumns = new ArrayList();
    
    

while(res.next())
{
    
     if (count == (count1)) 
     {

                for (int i = 1; i <= columnCount; i++) 
                {
                       mycolumns.add(metaData.getColumnLabel(i));             
                     hdslist_html+="<th>"+metaData.getColumnLabel(i)+"</th>";
                }//end of for loop
                count++;
     }//end of if
     else { }
    
    


for(int c=0;c<mycolumns.size();c++)
{
    
     String id="";
 if(c==0){id="id='"+res.getString(mycolumns.get(c).toString())+"'";   dtlist_html+="<tr "+id+">";}
      dtlist_html+="<td>"+res.getString(mycolumns.get(c).toString())+"</td>";
      
      
      
      String edt="<label onclick='loadExistingClient(\""+res.getString("patient_id")+"\",\""+frm+"\");' class='btn btn-info'>Edit</label>";
      String dlt="<label onclick='dltpt(\""+res.getString("patient_id")+"\");' class='btn btn-danger'>Delete</label>";
      if(ss.getAttribute("kd_session")!=null){
      
      HashMap hm= new HashMap();
      
      hm=(HashMap)ss.getAttribute("kd_session");
      
      String userlevel="";
       userlevel=hm.get("level").toString();
       
       if(!userlevel.equals("3")){dlt="";}
      
      } else {dlt="";edt="";  }
      
      
      
      if(c==mycolumns.size()-1){ dtlist_html+="<td>"+edt+"</td><td>"+dlt+"</td></tr>";}

}
    



count++;
    
}




finaltbl= "<table id='searchtable_"+elementtoappend+"' class='table table-striped table-bordered'><thead><tr>"+hdslist_html+"<th>Edit</th><th>Delete</th></tr></thead><tbody>"+dtlist_html+"</tbody></table>";




return finaltbl;
}
    
}
