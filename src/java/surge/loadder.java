/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surge;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author EKaunda
 */
public class loadder extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            
            String tarehe="";
            String facility="";
            
            String indicator="";
            String indicator_string="";
            
            String deliverypoint="";
            String deliverypointid="";
            
            String section="";
            String section_string="";
            
            String sectioncopy="";
            String sectioncopy_string="";
            
            String letter="";
            String letter_string="";
            
            String id="";
            String id_string="";
            
            String orodha="";
            String orodha_string="";
            
            
            String getdeliveries="SELECT * FROM der_rri.delivery_point where active=1 " ;
            
            dbConn conn = new dbConn();
            conn.rs=conn.st.executeQuery(getdeliveries);
            
            while(conn.rs.next()){
                
            deliverypoint+=""+conn.rs.getString("name")+"_";
            deliverypointid+=""+conn.rs.getString("id")+"_";
                
                                 }
            
           String deliverypointarr[]=deliverypoint.split("_");
           String deliverypointidarr[]=deliverypointid.split("_");
            
           String data="";
            
          
            
           //Identifier 
           
           String columns[] = {"der_1f","der_1m","der_4f","der_4m","der_9f","der_9m","der_14f","der_14m","der_19f","der_19m","der_24f","der_24m","der_29f","der_29m","der_34f","der_34m","der_39f","der_39m","der_44f","der_44m","der_49f","der_49m","der_50f","der_50m","total"};
           
          
           
      //_________
     
      
      for(int p=0;p<deliverypointarr.length;p++)
          
      {
          
           data+="<fieldset class='formatter' style='margin:20px; color:black;'><legend class='formatter'><b style='text-align:center;'>"+deliverypointarr[p]+"</b></legend> ";
                   data+= " <form action='saveder' id='der_table_age' method='post' class='form-horizontal'> <table class='responsive_table' border='1px;' >";
     
      String indicatorsarr[]=null;    
      String letterarr[]=null;    
      String orodhaarr[]=null;    
      String sectionarr[]=null;    
      String idarr[]=null;    
          
      String getindicators ="SELECT id, letter, indicator, mainsection, orodha FROM der_rri.excel_indicators order by mainsection,orodha";
      
      conn.rs=conn.st.executeQuery(getindicators);
    
      while(conn.rs.next())
      {
       sectioncopy_string+=conn.rs.getString("mainsection")+",";
       indicator_string+=conn.rs.getString("indicator")+","; 
       letter_string+=conn.rs.getString("letter")+","; 
       orodha_string+=conn.rs.getString("orodha")+",";  
       id_string+=conn.rs.getString("id")+",";  
      }
      
      
      indicatorsarr=indicator.split(",");
      letterarr=letter.split(",");
      orodhaarr=orodha.split(",");
      sectionarr=section.split(",");
      idarr=id.split(",");
      
      for(int y=0;y<indicatorsarr.length;y++){
          
       sectioncopy=sectionarr[y];
       indicator=indicatorsarr[y]; 
       letter=letterarr[y]; 
       orodha=orodhaarr[y]; 
      
          if(!section.equals(sectioncopy))
          {
          section=sectioncopy;
         //write a new row
data+="<thead><tr style=\"font-weight:bold; background:#a9c7e4;\"><th>"+letter+"</th><th colspan='26'>"+section+"</th></tr>";
          
          
data += "<tr style=\"font-weight:bold; background:#a9c7e4;\">"
                    + "<th rowspan='2' colspan='2'>Indicator</th>"
                    + "<th colspan='2'><1</th>"
                    + "<th colspan='2'>1-4</th>"
                    + "<th colspan='2'>5-9</th>"
                    + "<th colspan='2'>10-14</th>"
                    + "<th colspan='2'>15-19</th>"
                    + "<th colspan='2'>20-24</th>"
                    + "<th colspan='2'>25-29</th>"
                    + "<th colspan='2'>30-34</th>"
                    + "<th colspan='2'>35-39</th>"
                    + "<th colspan='2'>40-44</th>"
                    + "<th colspan='2'>45-49</th>"
                    + "<th colspan='2'>50+</th>"
                    + "<th></th>"
                    + "</tr>";
            data += "<tr  style=\"font-weight:bold; background:#a9c7e4;\">"
                    + "<th>F</th><th>M</th><th>F</th><th>M</th>"
                    + "<th>F</th><th>M</th><th>F</th><th>M</th>"
                    + "<th>F</th><th>M</th><th>F</th><th>M</th>"
                    + "<th>F</th><th>M</th><th>F</th><th>M</th>"
                    + "<th>F</th><th>M</th><th>F</th><th>M</th>"
                    + "<th>F</th><th>M</th><th>F</th><th>M</th>"
                    + "<th>Total</th>"
                    + "</tr></thead><tbody>";
        
          }
          else {
              
          data+="<tr><td>"+orodha+"</td><td>"+indicator+"</td>";
                  
                  for(int a=0;a<columns.length;a++)
                  {
 data+="<td><input type='text' style='width:50px;border-color:blue;' onkeyup=\"sum_indicators('"+indicator+"');\" onblur=\"indicate_changed('" +columns[a]+ "_" + indicator + "');\"  name='"+columns[a]+"_"+id+"_"+deliverypointidarr[p]+"' id='"+columns[a]+"_"+id+"_"+deliverypointidarr[p]+"' value='' autocomplete='off' maxLength='5'  onkeypress='return numbers(event);' /></td>";
                  }
                  
          data+="</tr>";
          
               }
          
      
      }
      
      
           data+="</tbody></table></form>";
           data+="</fieldset>";
           
           
        }
           
            
            out.println(data);
        } catch (SQLException ex) {
            Logger.getLogger(loadder.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
        processRequest(request, response);
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

}
