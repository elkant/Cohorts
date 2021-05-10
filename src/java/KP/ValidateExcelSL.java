/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KP;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FontFamily;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author EKaunda
 */
public class ValidateExcelSL extends HttpServlet {

   dbConn conn = new dbConn();
    int error_count,warning_count,summarypos; 
    String table_name="";
      HttpSession session=null;
       int tukowapi=0;
        int rowCount=1;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
           
        } finally {
            out.close();
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
    
    
      public JSONObject validate(String[] facilityids, String period[],String table,HttpServletRequest request) throws SQLException{
        JSONObject obj = new JSONObject();
        JSONArray jarray = new JSONArray();
        String facility_id,yearmonth;
        table_name = table;
        
         tukowapi=0;
         rowCount=1;
        
        session=request.getSession();
        
       
        
        
        for(int i=0;i<facilityids.length;i++){
          for( int j=0;j<period.length;j++){
          facility_id = facilityids[i];
          yearmonth = period[j];   
          
          if(data_exist(facility_id,yearmonth)){
              tukowapi=0;
          JSONObject obj_item = validate(facility_id,yearmonth,table,request);
          jarray.add(obj_item);
          }
          System.out.println("facility :"+i+" month : "+j);
          }
          }
      obj.put("output", jarray);
        return obj;
    }
    
    public boolean data_exist(String facil_id, String yearmonth) throws SQLException{
        boolean status= false;
        int validations_ni_ngapi=0;
         String countRules="SELECT count(validation) FROM kp_validation WHERE active=1 AND source='excel'   ";
        
         conn.rs = conn.st.executeQuery(countRules);
     if(conn.rs.next()){
     validations_ni_ngapi=conn.rs.getInt(1);
     }
      rowCount=validations_ni_ngapi;
     if(validations_ni_ngapi>2){
     rowCount=validations_ni_ngapi+1;
     }
     
     
     String count_recs = "SELECT COUNT(id) AS no_recs FROM "+table_name+" WHERE yearmonth="+yearmonth+" AND facility_id="+facil_id+"";
     conn.rs = conn.st.executeQuery(count_recs);
     if(conn.rs.next()){
         if(conn.rs.getInt(1)>0){
             rowCount=validations_ni_ngapi;
             status=true;
         }
     }
     
        return status;
    }
    
    
    public JSONObject validate(String facility_id,String yearmonth, String table,HttpServletRequest request) throws SQLException{
           JSONArray jarray = new JSONArray();
           table_name = table;
         
         tukowapi=0;
         //rowCount=1;
          String age_groups = "";
          //age_groups = "<1 F,<1 M,1-4 F,1-4 M,5-9 F,5-9 M,10-14 F,10-14 M,15-19 F,15-19 M,20-24 F,20-24 M,25-29 F,25-29 M,30-34 F,30-34 M,35-39 F,35-39 M,40-44 F,40-44 M,45-49 F,45-49 M,50+ F,50+ M,Totals";
           
           
           
//           System.out.println("called to validate");
        int error_counter=0,warnings_counter=0;
        String message_type="",message="";
        String lhs,rhs,sign,query="",final_query="";
        String getRules="SELECT section_name,codes,validation,iscritical,message,fine_age,raw_query FROM fas_validation WHERE active=1 AND source='excel' order by section_name  ";
        //System.out.println("rules :"+getRules);
        conn.rs = conn.st.executeQuery(getRules);
        while(conn.rs.next()){
            System.out.println("Tuko wapi value ni "+tukowapi);
         tukowapi++;
            session.setAttribute("kpform1a", "<b>Validating: "+conn.rs.getString("section_name")+" "+tukowapi+"/"+rowCount+"</b>");
        session.setAttribute("kpform1a_count", ((tukowapi)/rowCount)*100);
            
           String basic_columns = "'"+conn.rs.getString("message")+" i.e ["+conn.rs.getString("codes")+"]' AS message,'"+conn.rs.getString("section_name")+"' AS program_area, "
                   + ""+conn.rs.getInt("iscritical")+" AS iscritical,"+conn.rs.getString("fine_age")+" AS fine_age, ";
           
           //error indicators
             String[] Rules = extract_rule(conn.rs.getString("validation"));  
             lhs = Rules[0];
             rhs = Rules[1];
             sign = Rules[2];
             
             if(conn.rs.getString("raw_query")!=null && conn.rs.getString("raw_query").contains("SELECT")){ // for raw queries
             query = conn.rs.getString("raw_query"); 
             query = query.replace("YMONTH", yearmonth);
             query = query.replace("FID", facility_id);
             query = query.replace("TABLE_NAME", table_name);// update the correct table name
             }
             else{
            query = getQuery(conn.rs.getInt("fine_age"));
            query = query.replace("lhs", lhs);
            query = query.replace("rhs", rhs);
            query = query.replace("sign", sign);
            query += "  WHERE yearmonth="+yearmonth+" AND facility_id="+facility_id;  
             }
             
             query = query.replace("SELECT", "SELECT "+basic_columns);
             
              //System.out.println("query is : "+query);
              
                //run the validation query
                
                
              final_query+= query+" UNION ALL "; 
              
        }
        
        final_query = removeLast(final_query, 10);
        
        final_query = "SELECT * FROM ("+final_query+") AS all_data WHERE occurences>0";
        
        //System.out.println("final query :"+final_query);
        conn.rs1 = conn.st1.executeQuery(final_query);
                
                while(conn.rs1.next()){
                    if(conn.rs1.getInt("occurences")>0){
          if(conn.rs1.getString("fine_age").equals("1")){
            age_groups = "<1 F,<1 M,1-4 F,1-4 M,5-9 F,5-9 M,10-14 F,10-14 M,15-19 F,15-19 M,20-24 F,20-24 M,25-29 F,25-29 M,30-34 F,30-34 M,35-39 F,35-39 M,40-44 F,40-44 M,45-49 F,45-49 M,50+ F,50+ M,Totals";
           }
            else if(conn.rs1.getString("fine_age").equals("2")){
            age_groups = "<1 F,<1 M";
           }
            
            else if(conn.rs1.getString("fine_age").equals("0")){
            age_groups = "Totals";
           }
            else{}
           
           String age_group_titles[] = age_groups.split(",");    
                        
                   // check and see the columns that have an issue 
                   for(int i=0;i<age_group_titles.length;i++){
                   JSONObject objerr = new JSONObject();
                    if(conn.rs1.getInt(age_group_titles[i])==1){
                        //System.out.println(""+query);
                        //archive this error
                         if(conn.rs1.getInt("iscritical")==1){
                        message_type = "error";
                        error_counter++;
                        }
                        else{
                          message_type = "warning";
                          warnings_counter++;
                       }
                        
                         //add error to object
                
                   objerr.put("message_type", message_type);
                   objerr.put("message", conn.rs1.getString("message"));
                   objerr.put("program", conn.rs1.getString("program_area"));
                   objerr.put("age_group", age_group_titles[i]);   
                   jarray.add(objerr);
                    }  
                       
                   }
                   
                   
                } 
                }
           
        
        return getFacilityBasics(facility_id,yearmonth,error_counter,warnings_counter,jarray);
    }
    
    private String[] extract_rule(String rule){
      String sign="";
           if(rule.contains("<=")){sign="<=";}
      else if(rule.contains(">=")){sign=">=";}
      else if(rule.contains("!=")){sign="!=";}
      else if(rule.contains("=")){sign="=";}
      else if(rule.contains("<")){sign="<";}
      else if(rule.contains(">")){sign=">";}
      
      rule = rule.replace(sign, "#")+"#"+sign;
      return rule.split("#");
        
    }
    private String getQuery(int fine_age){
                String  query="SELECT\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_1 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_1 end,0))) AS '<1 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_1 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_1 end,0))) AS '<1 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_4 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_4 end,0))) AS '1-4 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_4 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_4 end,0))) AS '1-4 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_9 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_9 end,0))) AS '5-9 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_9 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_9 end,0))) AS '5-9 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_14 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_14 end,0))) AS '10-14 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_14 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_14 end,0))) AS '10-14 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_19 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_19 end,0))) AS '15-19 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_19 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_19 end,0))) AS '15-19 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_24 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_24 end,0))) AS '20-24 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_24 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_24 end,0))) AS '20-24 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_29 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_29 end,0))) AS '25-29 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_29 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_29 end,0))) AS '25-29 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_34 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_34 end,0))) AS '30-34 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_34 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_34 end,0))) AS '30-34 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_39 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_39 end,0))) AS '35-39 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_39 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_39 end,0))) AS '35-39 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_44 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_44 end,0))) AS '40-44 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_44 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_44 end,0))) AS '40-44 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_49 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_49 end,0))) AS '45-49 F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_49 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_49 end,0))) AS '45-49 M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_50 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_50 end,0))) AS '50+ F',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_50 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_50 end,0))) AS '50+ M',\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN total END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN total end,0))) AS 'Totals',\n";
                      
        switch (fine_age) {
            case 1:
                query+= "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_1 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_1 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_1 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_1 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_4 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_4 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_4 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_4 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_9 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_9 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_9 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_9 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_14 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_14 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_14 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_14 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_19 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_19 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_19 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_19 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_24 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_24 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_24 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_24 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_29 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_29 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_29 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_29 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_34 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_34 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_34 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_34 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_39 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_39 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_39 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_39 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_44 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_44 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_44 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_44 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_49 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_49 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_49 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_49 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_50 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_50 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_50 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_50 end,0))) +\n" +
                        "(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN total END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN total end,0))) as occurences ";
                break;
            case 0:
                query +=" (SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN total END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN total end,0))) as occurences ";
                break;
            case 2:
                query+="(SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN f_1 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN f_1 end,0))) + " +
                        " (SUM(IFNULL(CASE WHEN indicator_id IN(lhs) THEN m_1 END,0)) sign SUM(IFNULL(CASE WHEN  indicator_id IN(rhs) THEN m_1 end,0))) as occurences ";
                break;
            default:
                break;
        }
                       query+="FROM "+table_name+" ";
                       
                       
                       return query;
          
    }
    private JSONObject getFacilityBasics(String facilityID,String yearmonth,int errors,int warnings,JSONArray jarray) throws SQLException{
      JSONObject obj = new JSONObject();
      
      String query = 
        "SELECT county.County AS County, district.DistrictNom AS SubCounty, subpartnera.SubPartnerNom AS HealthFacility, subpartnera.CentreSanteId AS MFLCode,\n" +
        "substr('"+yearmonth+"',1,4) AS Year,ifnull(monthname(str_to_date(substr('"+yearmonth+"',5,6),'%m')),'"+yearmonth+"') as Month \n" +
        "FROM subpartnera LEFT JOIN district ON subpartnera.DistrictID=district.DistrictID LEFT JOIN county ON district.CountyID=county.CountyID " +
        "WHERE SubPartnerID="+facilityID;
      conn.rs = conn.st.executeQuery(query);
      ResultSetMetaData metaData = conn.rs.getMetaData();
      int column_count = metaData.getColumnCount(); //number of column
     if(conn.rs.next()){
        
         for(int i=1; i<=column_count;i++){
           obj.put(metaData.getColumnLabel(i), conn.rs.getString(i));
         }
     } 
      
     obj.put("yearmonth", yearmonth);
     obj.put("facility_id", facilityID);
     obj.put("errors", errors);
     obj.put("warnings", warnings);
     obj.put("details", jarray);
     return obj;
    }
    
    public XSSFWorkbook generateExcel(JSONObject obj){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheetSummary = wb.createSheet("Summary");
        XSSFSheet sheetErrors = wb.createSheet("Errors");
        XSSFSheet sheetWarning = wb.createSheet("Early Warning");
  
        sheetErrors.setTabColor(HSSFColor.RED.index);
        sheetWarning.setTabColor(HSSFColor.YELLOW.index);
       // 
    XSSFCellStyle styleHeader = wb.createCellStyle();
    styleHeader.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
    styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    styleHeader.setBorderTop(BorderStyle.THIN);
    styleHeader.setBorderBottom(BorderStyle.THIN);
    styleHeader.setBorderLeft(BorderStyle.THIN);
    styleHeader.setBorderRight(BorderStyle.THIN);
    styleHeader.setAlignment(HorizontalAlignment.CENTER);
    
    XSSFFont fontHeader = wb.createFont();
    fontHeader.setColor(HSSFColor.BLACK.index);
    fontHeader.setBold(true);
    fontHeader.setFamily(FontFamily.MODERN);
    fontHeader.setFontName("Cambria");
    fontHeader.setFontHeight(15);
    styleHeader.setFont(fontHeader);
    styleHeader.setWrapText(true);
    
    XSSFCellStyle stylex = wb.createCellStyle();
    stylex.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    stylex.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    stylex.setBorderTop(BorderStyle.THIN);
    stylex.setBorderBottom(BorderStyle.THIN);
    stylex.setBorderLeft(BorderStyle.THIN);
    stylex.setBorderRight(BorderStyle.THIN);
    stylex.setAlignment(HorizontalAlignment.LEFT);
    
    XSSFFont fontx = wb.createFont();
    fontx.setColor(HSSFColor.BLACK.index);
    fontx.setBold(true);
    fontx.setFamily(FontFamily.MODERN);
    fontx.setFontName("Cambria");
    stylex.setFont(fontx);
    stylex.setWrapText(true);
    
    XSSFCellStyle stborder = wb.createCellStyle();
    stborder.setBorderTop(BorderStyle.THIN);
    stborder.setBorderBottom(BorderStyle.THIN);
    stborder.setBorderLeft(BorderStyle.THIN);
    stborder.setBorderRight(BorderStyle.THIN);
    stborder.setAlignment(HorizontalAlignment.LEFT);
    
    XSSFFont font_cell=wb.createFont();
    font_cell.setColor(HSSFColor.BLACK.index);
    font_cell.setFamily(FontFamily.MODERN);
    font_cell.setFontName("Cambria");
    stborder.setFont(font_cell);
    stborder.setWrapText(true);
    
    //success
    XSSFCellStyle stsuccess = wb.createCellStyle();
    stsuccess.setFillForegroundColor(HSSFColor.GREEN.index);
    stsuccess.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    stsuccess.setBorderTop(BorderStyle.THIN);
    stsuccess.setBorderBottom(BorderStyle.THIN);
    stsuccess.setBorderLeft(BorderStyle.THIN);
    stsuccess.setBorderRight(BorderStyle.THIN);
    stsuccess.setAlignment(HorizontalAlignment.LEFT);
    
    font_cell=wb.createFont();
    font_cell.setColor(HSSFColor.BLACK.index);
    font_cell.setFamily(FontFamily.MODERN);
    font_cell.setFontName("Cambria");
    stsuccess.setFont(font_cell);
    stsuccess.setWrapText(true);
    
    //fail
    XSSFCellStyle stfail = wb.createCellStyle();
     stfail.setFillForegroundColor(HSSFColor.RED.index);
    stfail.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    stfail.setBorderTop(BorderStyle.THIN);
    stfail.setBorderBottom(BorderStyle.THIN);
    stfail.setBorderLeft(BorderStyle.THIN);
    stfail.setBorderRight(BorderStyle.THIN);
    stfail.setAlignment(HorizontalAlignment.LEFT);
    
    font_cell=wb.createFont();
    font_cell.setColor(HSSFColor.BLACK.index);
    font_cell.setFamily(FontFamily.MODERN);
    font_cell.setFontName("Cambria");
    stfail.setFont(font_cell);
    stfail.setWrapText(true);
    
    
          summarypos = error_count =warning_count = 0; 
    
        //add  headers to both
        String headers[] = {"County","Sub County","DIC", "DIC Code","Calendar Year","Month","Year-Month","Program","Message","Age Group"};
        int column_widths[] = {3500,5500,6500, 2500,2500,3500,2500,4500,18000,3500};
        
        String headers_summary[] = {"County","Sub County","DIC", "DIC Code","Calendar Year","Month","Year-Month","No. Errors","No. of Warnings","Upload Status"};
        int column_widths_summary[] = {5500,7500,8500, 4500,4500,5500,4500,6500,6500,7000};
        
        summarypos=0;
        XSSFRow rowSummary = sheetSummary.createRow(summarypos);
        rowSummary.setHeightInPoints(35);
        
        XSSFRow rowError = sheetErrors.createRow(0);
        XSSFRow rowWarning = sheetWarning.createRow(0);
        XSSFCell cell_h_s,cell_h_e,cell_h_w;  
        for(int i=0;i<headers.length;i++){
          cell_h_s = rowSummary.createCell(i);
          cell_h_e = rowError.createCell(i);
          cell_h_w = rowWarning.createCell(i);
          
          cell_h_s.setCellValue(headers_summary[i]);
          cell_h_e.setCellValue(headers[i]);
          cell_h_w.setCellValue(headers[i]);
          
          cell_h_s.setCellStyle(stylex);
          cell_h_e.setCellStyle(stylex);
          cell_h_w.setCellStyle(stylex);
          
          sheetSummary.setColumnWidth(i, column_widths_summary[i]);
          sheetErrors.setColumnWidth(i, column_widths[i]);
          sheetWarning.setColumnWidth(i, column_widths[i]);
         }
        
        //end of headers
        
        
        if(obj.containsKey("output")){ // data from several facilities or several periods
            JSONArray array_facil = (JSONArray)obj.get("output");
            
            for(int facils=0; facils<array_facil.size();facils++){
                
                
             JSONObject facil_info = (JSONObject)array_facil.get(facils);
             
             facil_output(facil_info,sheetSummary,sheetErrors,sheetWarning,stborder,stsuccess,stfail);
             
//           end of looping facilities  
            }
        }
        else{ // for one facility, one period
        facil_output(obj,sheetSummary,sheetErrors,sheetWarning,stborder,stsuccess,stfail);    
        }
        
        return wb;
    }
    
    private void facil_output(JSONObject facil_info,XSSFSheet sheetSummary, XSSFSheet sheetErrors,XSSFSheet sheetWarning, XSSFCellStyle stborder, XSSFCellStyle stsuccess, XSSFCellStyle stfail){
              
        String month,mflcode,facility,subcounty,county,year,yearmonth;
        int warnings,errors;
        String age_group,message_type,program,message,upload_status;
        
        
             System.out.println("facil:"+facil_info);
             month = facil_info.get("Month").toString();  
             facility = facil_info.get("HealthFacility").toString();
             subcounty = facil_info.get("SubCounty").toString();
             county = facil_info.get("County").toString();
             mflcode = facil_info.get("MFLCode").toString();
             year = facil_info.get("Year").toString();
             yearmonth = facil_info.get("yearmonth").toString();
                      
    
             warnings = (Integer)facil_info.get("warnings");
             errors = (Integer)facil_info.get("errors");
             
             if(errors==0){upload_status="Success";}
             else{upload_status="Failed";}
             
            summarypos++;
             
             
             
             XSSFRow row_data_summary = sheetSummary.createRow(summarypos);
             row_data_summary.setHeightInPoints(30);
              //add data to excel
              String data_summary[] = {county,subcounty,facility, mflcode,year,month,yearmonth,""+errors,""+warnings,upload_status};
              
                
        XSSFCell cell_data_summary=null;  
        for(int i=0;i<data_summary.length;i++){
          cell_data_summary = row_data_summary.createCell(i);
          if(isNumeric(data_summary[i])){cell_data_summary.setCellValue(Integer.parseInt(data_summary[i]));}
          else{cell_data_summary.setCellValue(data_summary[i]);}
          cell_data_summary.setCellStyle(stborder);
         }
        
        if(cell_data_summary!=null){
        if(errors==0){
          cell_data_summary.setCellStyle(stsuccess);  
        }
        else{
            cell_data_summary.setCellStyle(stfail);   
        }
        }    
//             end
        
             JSONArray arr_details = (JSONArray) facil_info.get("details");
             
             //loop for errors
             for(int details=0;details<arr_details.size();details++){
                age_group = ((JSONObject)arr_details.get(details)).get("age_group").toString();
                message_type = ((JSONObject)arr_details.get(details)).get("message_type").toString();
                program = ((JSONObject)arr_details.get(details)).get("program").toString();
                message = ((JSONObject)arr_details.get(details)).get("message").toString();

                XSSFRow row_data;
                //write data to excel based on error type
                if(message_type.equalsIgnoreCase("error")){
                  error_count++;
                 row_data = sheetErrors.createRow(error_count);
                }
                else{
                 warning_count++; 
                 row_data = sheetWarning.createRow(warning_count);
                }
             
                //add data to excel
                String data[] = {county,subcounty,facility, mflcode,year,month,yearmonth,program,message,age_group};
              
                
        XSSFCell cell_data;  
        for(int i=0;i<data.length;i++){
          cell_data = row_data.createCell(i);
          if(isNumeric(data[i])){cell_data.setCellValue(Integer.parseInt(data[i]));}
          else{cell_data.setCellValue(data[i]);}
          cell_data.setCellStyle(stborder);
         } 
             }
             
               
    }
    
    public String gel_all_warnings(JSONObject obj){
        String output="";
        
          if(obj.containsKey("output")){ // data from several facilities or several periods
            JSONArray array_facil = (JSONArray)obj.get("output");
            
            for(int facils=0; facils<array_facil.size();facils++){
                
                
             JSONObject facil_info = (JSONObject)array_facil.get(facils);
             
             output+=get_warnings(facil_info); // 
             
//           end of looping facilities  
            }
        }
        else{ // for one facility, one period
        output+=get_warnings(obj);    
        }
      return output;    
    }
    
    
    public String get_warnings(JSONObject facil_info){
        String output="";
        
        String month,mflcode,facility,subcounty,county,year,yearmonth;
        int warnings,errors;
        String age_group,message_type,program,message;
        
        
        
        
             System.out.println("facil:"+facil_info);
             month = facil_info.get("Month").toString();  
             facility = facil_info.get("HealthFacility").toString();
             subcounty = facil_info.get("SubCounty").toString();
             county = facil_info.get("County").toString();
             mflcode = facil_info.get("MFLCode").toString();
             year = facil_info.get("Year").toString();
             yearmonth = facil_info.get("yearmonth").toString();
                      
    
             warnings = (Integer)facil_info.get("warnings");
             errors = (Integer)facil_info.get("errors");
        
             JSONArray arr_details = (JSONArray) facil_info.get("details");
             
              
             //loop for errors
             for(int details=0;details<arr_details.size();details++){
                age_group = ((JSONObject)arr_details.get(details)).get("age_group").toString();
                message_type = ((JSONObject)arr_details.get(details)).get("message_type").toString();
                program = ((JSONObject)arr_details.get(details)).get("program").toString();
                message = ((JSONObject)arr_details.get(details)).get("message").toString();
                //write data to excel based on error type
                if(message_type.equalsIgnoreCase("error")){
                  }
                else{
                 warning_count++; 
                 
                //add data to excel
                String data[] = {county,subcounty,facility, mflcode,year,month,yearmonth,program,message,age_group};
                    output+="<tr>";
                    for (String data1 : data) {
                        output += "<td>" + data1+ "</td>";
                    }
                        output+="</tr>";
          }
                
            
          }
        
        
        return output;
    }
    
    
    public int no_of_errors(JSONObject obj){
     int no_errors=0;
        
          if(obj.containsKey("output")){ // data from several facilities or several periods
            JSONArray array_facil = (JSONArray)obj.get("output");
            
            for(int facils=0; facils<array_facil.size();facils++){
                
                
             JSONObject facil_info = (JSONObject)array_facil.get(facils);
             
             no_errors+=error_per_sheet(facil_info); // 
             
//           end of looping facilities  
            }
        }
        else{ // for one facility, one period
        no_errors+=error_per_sheet(obj);    
        }
          
     return no_errors;
    }
    
    public int error_per_sheet(JSONObject obj){// 1 facil, 1 year month
     return (Integer)obj.get("errors"); 
    }
    
    
    public boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
}
    
           private static String removeLast(String str, int num) {
    return str.substring(0, str.length() - num);
}
    

}
