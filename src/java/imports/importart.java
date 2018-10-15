/*
Notes: This raw data is for positive EID. The data doesnt have age and sex
Age and sex should be gotten from the eid tested raw data during the importing of the raw data positives into the eid_datim_output table.

 */

package imports;

import db.dbConn;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
@MultipartConfig(fileSizeThreshold=1024*1024*20, 	// 20 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100) 

/**
 *
 * @author Emmanuel Kaunda
 */


  public class importart extends HttpServlet {
   
 
  
  String full_path="";
  String fileName="";
  int checker_dist,checker_hf;
  File file_source;
  HttpSession session;
  private static final long serialVersionUID = 205242440643911308L;
  private static final String UPLOAD_DIR = "uploads";
  String nextpage="";
  String quarterName,facilityName,facilityID,id,missingFacility;
          
  String fileNames="";

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
     String nomflsheets="";
     
      int year,quarter,checker,missing = 0,added = 0,updated = 0;
      String county_name,county_id, district_name,district_id,hf_name,hf_id;
     fileNames="";
     fileName="";
     /***  
      id	1
indicator	A. Enrolled into cohort
adult_3m	22
child_3m	3
tl_3m	25
adult_6m	18
child_6m	1
tl_6m	19
adult_9m	15
child_9m	4
tl_9m	19
adult_12m	56
child_12m	11
tl_12m	67
adult_24m	0
child_24m	0
tl_24m	0
adult_48m	0
child_48m	0
tl_48m	0
adult_60m	0
child_60m	0
tl_60m	0
mflcode	15212
reportingyear	2015
reportingmonth	10
yearmonth	201510


      
      ***/
     
     
     
  id=""; 
String indicator="";
String indicatorid="";
String adult_3m="";
String child_3m="";
String tl_3m="";
String adult_6m="";
String child_6m="";
String tl_6m="";
String adult_9m="";
String child_9m="";
String tl_9m="";
String adult_12m="";
String child_12m="";
String tl_12m="";
String adult_24m="";
String child_24m="";
String tl_24m="";
String adult_36m="";
String child_36m="";
String tl_36m="";
String adult_48m="0";
String child_48m="0";
String tl_48m="0";
String adult_60m="0";
String child_60m="0";
String tl_60m="0";
String mflcode="";
String reportingyear="";
String reportingmonth="";
String yearmonth="";

 
 
    String serialnumber="";
    
     String dbname="art_cohort";
  
     
     session=request.getSession();
     dbConn conn = new dbConn();
     nextpage="importart.jsp";
     String applicationPath = request.getServletContext().getRealPath("");
     String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
     session=request.getSession();
     File fileSaveDir = new File(uploadFilePath);
     if (!fileSaveDir.exists()) {
         fileSaveDir.mkdirs();
     }
     System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
     for (Part part : request.getParts()) {
         fileName = getFileName(part);
         part.write(uploadFilePath + File.separator + fileName);
         System.out.println("file name is  :  "+fileName);
          if(!fileName.endsWith(".xlsx")){
         nextpage="importart.jsp";
         session.setAttribute("upload_success", "<font color=\"red\">Failed to load the excel file. Please choose a .xlsx excel file .</font>");
     }
     else{
           fileNames+=fileName+", ";
         
         full_path=fileSaveDir.getAbsolutePath()+"/"+fileName;
         
         System.out.println("the saved file directory is  :  "+full_path);
// GET DATA FROM THE EXCEL AND AND OUTPUT IT ON THE CONSOLE..................................
         
         FileInputStream fileInputStream = new FileInputStream(full_path);
         XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
         
         int totalsheets=workbook.getNumberOfSheets();
         DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale


        
         for(int a=0;a<totalsheets;a++){
         
         XSSFSheet worksheet = workbook.getSheetAt(a);
         
         
          System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
         
            
//______________________________________________________________________
                 
                 if(1==1) {
//______________________________________________________________________
                     //-----------facility name-----------------------
                     XSSFCell cellfacil = worksheet.getRow(0).getCell((short) 1);
                     
                     if(cellfacil.getCellType()==0){
                         //numeric
                         facilityName =""+(int)cellfacil.getNumericCellValue();
                     }
                     else if(cellfacil.getCellType()==1){
                         facilityName =cellfacil.getStringCellValue();
                     }
                     
                     
                     //-----------facility name-----------------------
                     XSSFCell cellmfl = worksheet.getRow(0).getCell((short) 3);
                     
                     if(cellmfl.getCellType()==0){
                         //numeric
                         mflcode =""+(int)cellmfl.getNumericCellValue();
                     }
                     else if(cellmfl.getCellType()==1){
                         mflcode =cellmfl.getStringCellValue().trim();
                     }
                     
                     
                     if(mflcode==null || mflcode.equals("")){
                     
                     nomflsheets+=workbook.getSheetName(a)+"("+fileName+") ,";
                     
                     }
                     
                     
                     //-----------year-----------------------
                     XSSFCell cellyear = worksheet.getRow(0).getCell((short) 7);
                     
                     if(cellyear.getCellType()==0){
                         //numeric
                         reportingyear =""+(int)cellyear.getNumericCellValue();
                     }
                     else if(cellyear.getCellType()==1){
                         reportingyear =cellyear.getStringCellValue();
                     }
                     
                     if(reportingyear.equals("2016")){ 
                     reportingyear="2017";
                 }
                     
                     //-----------month-----------------------
                     XSSFCell cellmonth = worksheet.getRow(0).getCell((short) 5);
                     
                     if(cellmonth.getCellType()==0){
                         //numeric
                         reportingmonth =""+(int)cellmonth.getNumericCellValue();
                     }
                     else if(cellmonth.getCellType()==1){
                         reportingmonth =cellmonth.getStringCellValue();
                     }
                     
                     
                 }
                 
         
         
         
         Iterator rowIterator = worksheet.rowIterator();
             
         
         
         
         
         
         
         int i=4,y=0;
         
         //static number of rows
         while(i<=14){
             try {
                                           System.out.println(" row number "+i);
                 XSSFRow rowi = worksheet.getRow(i);
                 if( rowi==null )
                 {
                     
                     break;
                 }
                 
//                 y++;
//                 
//                if( y==26)
//                 {
//                     y=0;
//                     break;
//                 }
               
                 
                 if(i>=3 && i<=14) {
                     
                     //elements
                     
                     //indicator
                     
                     XSSFCell indiccell = rowi.getCell((short) 1);
                     
                     if(indiccell.getCellType()==0){
                         //numeric
                         indicator =""+(int)indiccell.getNumericCellValue();
                     }
                     else if(indiccell.getCellType()==1){
                         indicator =indiccell.getStringCellValue();
                     }
                     
                     
                     //adult_3m
                     
                     XSSFCell adult_3mcell = rowi.getCell((short) 2);
                     
                     if(adult_3mcell.getCellType()==0){
                         //numeric
                         adult_3m =""+(int)adult_3mcell.getNumericCellValue();
                     }
                     else if(adult_3mcell.getCellType()==1){
                         adult_3m =adult_3mcell.getStringCellValue();
                     }
                   
                     else {
                         
                         
                     System.out.println("cell type "+adult_3mcell.getCellType());
                       adult_3m =""+(int)adult_3mcell.getNumericCellValue();
                     }
                     
                     if(adult_3m.trim().equals("")){adult_3m="0";}
                     
                     
                     //child_3m
                     
                     XSSFCell child_3mcell = rowi.getCell((short) 3);
                     
                     if(child_3mcell.getCellType()==0){
                         //numeric
                         child_3m =""+(int)child_3mcell.getNumericCellValue();
                     }
                     else if(child_3mcell.getCellType()==1){
                         child_3m =child_3mcell.getStringCellValue();
                     }
                     
                     else {
                      child_3m =""+(int)child_3mcell.getNumericCellValue();
                     }
                     
                     if(child_3m.trim().equals("")){child_3m="0";}
                     
                     //tl_3m
                     
                     XSSFCell tl_3mcell = rowi.getCell((short) 4);
                     
                     if(tl_3mcell.getCellType()==0){
                         //numeric
                         tl_3m =""+(int)tl_3mcell.getNumericCellValue();
                     }
                     else if(tl_3mcell.getCellType()==1){
                         tl_3m =tl_3mcell.getStringCellValue();
                     }
                      else {
                      
                         tl_3m =""+(int)tl_3mcell.getNumericCellValue();
                     }
                     
                     
                     if(tl_3m.trim().equals("")){tl_3m="0";}
                     //adult_6m
                     
                     XSSFCell adult_6mcell = rowi.getCell((short) 5);
                     
                     if(adult_6mcell.getCellType()==0){
                         //numeric
                         adult_6m =""+(int)adult_6mcell.getNumericCellValue();
                     }
                     else if(adult_6mcell.getCellType()==1){
                         adult_6m =adult_6mcell.getStringCellValue();
                     }
                     else {
                        adult_6m =""+(int)adult_6mcell.getNumericCellValue();
                     }
                     if(adult_6m.trim().equals("")){adult_6m="0";}
                     
                     //child_6m
                     
                     XSSFCell child_6mcell = rowi.getCell((short) 6);
                     
                     if(child_6mcell.getCellType()==0){
                         //numeric
                         child_6m =""+(int)child_6mcell.getNumericCellValue();
                     }
                     else if(child_6mcell.getCellType()==1){
                         child_6m =child_6mcell.getStringCellValue();
                     }
                     else {
                         
                      child_6m =""+(int)child_6mcell.getNumericCellValue();
                     
                     }
                     
                     if(child_6m.trim().equals("")){child_6m="0";}
                     
                     //tl_7m
                     
                     XSSFCell tl_6mcell = rowi.getCell((short) 7);
                     
                     if(tl_6mcell.getCellType()==0){
                         //numeric
                         tl_6m =""+(int)tl_6mcell.getNumericCellValue();
                     }
                     else if(tl_6mcell.getCellType()==1){
                         tl_6m =tl_6mcell.getStringCellValue();
                     }
                     else {
                     tl_6m =""+(int)tl_6mcell.getNumericCellValue();
                     
                     }
                     if(tl_6m.trim().equals("")){tl_6m="0";}
                     
                     //adult_9m
                     
                     XSSFCell adult_9mcell = rowi.getCell((short) 8);
                     
                     if(adult_9mcell.getCellType()==0){
                         //numeric
                         adult_9m =""+(int)adult_9mcell.getNumericCellValue();
                     }
                     else if(adult_9mcell.getCellType()==1){
                         adult_9m =adult_9mcell.getStringCellValue();
                     }
                     else {
                       adult_9m =""+(int)adult_9mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //child_9m
                     
                     XSSFCell child_9mcell = rowi.getCell((short) 9);
                     
                     if(child_9mcell.getCellType()==0){
                         //numeric
                         child_9m =""+(int)child_9mcell.getNumericCellValue();
                     }
                     else if(child_9mcell.getCellType()==1){
                         child_9m =child_9mcell.getStringCellValue();
                     }
                     else {
                     
                       child_9m =""+(int)child_9mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //tl_9m
                     
                     XSSFCell tl_9mcell = rowi.getCell((short) 10);
                     
                     if(tl_9mcell.getCellType()==0){
                         //numeric
                         tl_9m =""+(int)tl_9mcell.getNumericCellValue();
                     }
                     else if(tl_9mcell.getCellType()==1){
                         tl_9m =tl_9mcell.getStringCellValue();
                     }
                     
                     else {
                     
                     tl_9m =""+(int)tl_9mcell.getNumericCellValue();
                     
                     }
                     
                     if(adult_9m.trim().equals("")){adult_9m="0";}
                     if(child_9m.trim().equals("")){child_9m="0";}
                     if(tl_9m.trim().equals("")){tl_9m="0";}
                     
                     //adult_12m
                     
                     XSSFCell adult_12mcell = rowi.getCell((short) 11);
                     
                     if(adult_12mcell.getCellType()==0){
                         //numeric
                         adult_12m =""+(int)adult_12mcell.getNumericCellValue();
                     }
                     else if(adult_12mcell.getCellType()==1){
                         adult_12m =adult_12mcell.getStringCellValue();
                     }
                     else {
                     
                     adult_12m =""+(int)adult_12mcell.getNumericCellValue();
                     }
                     
                     
                     //child_12m
                     
                     XSSFCell child_12mcell = rowi.getCell((short) 12);
                     
                     if(child_12mcell.getCellType()==0){
                         //numeric
                         child_12m =""+(int)child_12mcell.getNumericCellValue();
                     }
                     else if(child_12mcell.getCellType()==1){
                         child_12m =child_12mcell.getStringCellValue();
                     }
                     else {
                     child_12m =""+(int)child_12mcell.getNumericCellValue();
                     
                     }
                     
                     //tl_12m
                     
                     XSSFCell tl_12mcell = rowi.getCell((short) 13);
                     
                     if(tl_12mcell.getCellType()==0){
                         //numeric
                         tl_12m =""+(int)tl_12mcell.getNumericCellValue();
                     }
                     else if(tl_12mcell.getCellType()==1){
                         tl_12m =tl_12mcell.getStringCellValue();
                     }
                     else {
                       tl_12m =""+(int)tl_12mcell.getNumericCellValue();
                     
                     }
                     
                     if(adult_12m.trim().equals("")){adult_12m="0";}
                     if(child_12m.trim().equals("")){child_12m="0";}
                     if(tl_12m.trim().equals("")){tl_12m="0";}
                     
                     //adult_24m
                     
                     XSSFCell adult_24mcell = rowi.getCell((short) 14);
                     
                     if(adult_24mcell.getCellType()==0){
                         //numeric
                         adult_24m =""+(int)adult_24mcell.getNumericCellValue();
                     }
                     else if(adult_24mcell.getCellType()==1){
                         adult_24m =adult_24mcell.getStringCellValue();
                     }
                     else {
                      adult_24m =""+(int)adult_24mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //child_24m
                     
                     XSSFCell child_24mcell = rowi.getCell((short) 15);
                     
                     if(child_24mcell.getCellType()==0){
                         //numeric
                         child_24m =""+(int)child_24mcell.getNumericCellValue();
                     }
                     else if(child_24mcell.getCellType()==1){
                         child_24m =child_24mcell.getStringCellValue();
                     }
                     else {
                     child_24m =""+(int)child_24mcell.getNumericCellValue();
                     
                     }
                     
                     //tl_24m
                     
                     XSSFCell tl_24mcell = rowi.getCell((short) 16);
                     
                     if(tl_24mcell.getCellType()==0){
                         //numeric
                         tl_24m =""+(int)tl_24mcell.getNumericCellValue();
                     }
                     else if(tl_24mcell.getCellType()==1){
                         tl_24m =tl_24mcell.getStringCellValue();
                     }
                     else {
                         
                      tl_24m =""+(int)tl_24mcell.getNumericCellValue();
                     
                     }
                     
                     if(adult_24m.trim().equals("")){adult_24m="0";}
                     if(child_24m.trim().equals("")){child_24m="0";}
                     if(tl_24m.trim().equals("")){tl_24m="0";}
                     
                     //adult_36m
                     
                     XSSFCell adult_36mcell = rowi.getCell((short) 17);
                     
                     if(adult_36mcell.getCellType()==0){
                         //numeric
                         adult_36m =""+(int)adult_36mcell.getNumericCellValue();
                     }
                     else if(adult_36mcell.getCellType()==1){
                         adult_36m =adult_36mcell.getStringCellValue();
                     }
                     
                     else {
                     adult_36m =""+(int)adult_36mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //child_36m
                     
                     XSSFCell child_36mcell = rowi.getCell((short) 18);
                     
                     if(child_36mcell.getCellType()==0){
                         //numeric
                         child_36m =""+(int)child_36mcell.getNumericCellValue();
                     }
                     else if(child_36mcell.getCellType()==1){
                         child_36m =child_36mcell.getStringCellValue();
                     }
                     else {
                     child_36m =""+(int)child_36mcell.getNumericCellValue();
                     
                     }
                     
                     //tl_36m
                     
                     XSSFCell tl_36mcell = rowi.getCell((short) 19);
                     
                     if(tl_36mcell.getCellType()==0){
                         //numeric
                         tl_36m =""+(int)tl_36mcell.getNumericCellValue();
                     }
                     else if(tl_36mcell.getCellType()==1){
                         tl_36m =tl_36mcell.getStringCellValue();
                         
                     }
                     else {
                     
                     tl_36m =""+(int)tl_36mcell.getNumericCellValue();
                     }
                     
                     if(adult_36m.trim().equals("")){adult_36m="0";}
                     if(child_36m.trim().equals("")){child_36m="0";}
                     if(tl_36m.trim().equals("")){tl_36m="0";}
                     
                     
                     //adult_48m
                     
//                     XSSFCell adult_48mcell = rowi.getCell((short) 20);
//                     
//                     adult_48mcell.setCellType(adult_48mcell.CELL_TYPE_STRING);
//                     
//                     
//                     
//                     if(adult_48mcell.getCellType()==0){
//                         //numeric
//                         adult_48m =""+(int)adult_48mcell.getNumericCellValue();
//                     }
//                     else if(adult_48mcell.getCellType()==1){
//                         
//                         adult_48m =adult_48mcell.getStringCellValue();
//                         
//                     
//                     }
//                   
//                   
//                     
//                     //child_48m
//                     
//                     XSSFCell child_48mcell = rowi.getCell((short) 21);
//              child_48mcell.setCellType(child_48mcell.CELL_TYPE_STRING);
//                     
//                     if(child_48mcell.getCellType()==0){
//                         //numeric
//                         child_48m =""+(int)child_48mcell.getNumericCellValue();
//                     }
//                     else if(child_48mcell.getCellType()==1){
//                         child_48m =child_48mcell.getStringCellValue();
//                     }
//                    
//                     
//                     //tl_48m
//                     
//                     XSSFCell tl_48mcell = rowi.getCell((short) 22);
//                    tl_48mcell.setCellType(tl_48mcell.CELL_TYPE_STRING);
//                     if(tl_48mcell.getCellType()==0){
//                         //numeric
//                         tl_48m =""+(int)tl_48mcell.getNumericCellValue();
//                     }
//                     else if(tl_48mcell.getCellType()==1){
//                         tl_48m =tl_48mcell.getStringCellValue();
//                         
//                     }
//                    
                     
                     if(adult_48m.trim().equals("")){adult_48m="0";}
                     if(child_48m.trim().equals("")){child_48m="0";}
                     if(tl_48m.trim().equals("")){tl_48m="0";}
                     
                     
                     
                     
                     
                     //adult_60m
                     
//                     XSSFCell adult_60mcell = rowi.getCell((short) 23);
//                        adult_60mcell.setCellType(adult_60mcell.CELL_TYPE_STRING);
//                     if(adult_60mcell.getCellType()==0){
//                         //numeric
//                         adult_60m =""+(int)adult_60mcell.getNumericCellValue();
//                     }
//                     else if(adult_60mcell.getCellType()==1){
//                         adult_60m =adult_60mcell.getStringCellValue();
//                     }
//                    
//                     
//                     
//                     //child_60m
//                     
//                     XSSFCell child_60mcell = rowi.getCell((short) 24);
//                     child_60mcell.setCellType(child_60mcell.CELL_TYPE_STRING);
//                     if(child_60mcell.getCellType()==0){
//                         //numeric
//                         child_60m =""+(int)child_60mcell.getNumericCellValue();
//                     }
//                     else if(child_60mcell.getCellType()==1){
//                         child_60m =child_60mcell.getStringCellValue();
//                     }
//                    
//                     
//                     
//                     //tl_60m
//                     
//                     XSSFCell tl_60mcell = rowi.getCell((short) 25);
//                     
//                     tl_60mcell.setCellType(tl_60mcell.CELL_TYPE_STRING);
//                     
//                     if(tl_60mcell.getCellType()==0){
//                         //numeric
//                         tl_60m =""+(int)tl_60mcell.getNumericCellValue();
//                     }
//                     else if(tl_60mcell.getCellType()==1){
//                         tl_60m =tl_60mcell.getStringCellValue();
//                         
//                     }
//                    
                     
                     if(adult_60m.trim().equals("")){adult_60m="0";}
                     if(child_60m.trim().equals("")){child_60m="0";}
                     if(tl_60m.trim().equals("")){tl_60m="0";}
                     
                     
                 }//end of cell values
                 
                 
                 if(reportingmonth.length()==1){  reportingmonth="0"+reportingmonth; }
                 
                 yearmonth=reportingyear+""+reportingmonth;
                 
                 System.out.println("yearmonth is "+yearmonth); 
                 //================================continue from here========================================
                 
                 //get indicator id from list of indicators
                 //create an id consisting of year_month_mflcode_indicator
                 
                 
                 
                 String getindicator="SELECT indicators_id,id,indicator FROM pmtct_art_cohort.indicators where cohort='art' and indicator like '%"+indicator+"'";
                 //dbConn conn= new dbConn();
                 //System.out.println(" Qry "+getindicator);     
                 conn.rs=conn.state.executeQuery(getindicator);
                 while(conn.rs.next()){
                     
                     indicatorid=conn.rs.getString("indicators_id");
                     
                 }
                 
                 
                 
                 
                 
                 
                 

                 
                 String id=reportingyear+"_"+reportingmonth+"_"+mflcode+"_"+indicatorid;
                 
                 
                 
                 System.out.println("test__"+id+"  "+indicator);
                 
                 
                 
                 
                 
                 
                 facilityID="";
                 checker=0;
                 
                 //
                 String get_id="SELECT id FROM "+dbname+" WHERE id like ? ";
                 conn.pst1=conn.connect.prepareStatement(get_id);
                 conn.pst1.setString(1,"%"+id+"%");
                 
                 conn.rs=conn.pst1.executeQuery();
                 if(conn.rs.next()==true)
                 {
                     facilityID=conn.rs.getString(1);
                     //supporttype=conn.rs.getString("ART_Support");
                     //mflcode=conn.rs.getInt(3);
                        checker=1;
                     //if(supporttype==null){supporttype=conn.rs.getString("HTC_Support1");}
                     //if(supporttype==null){supporttype=conn.rs.getString("PMTCT_Support");}
                     //if(supporttype==null){supporttype="";}
                 }
                 if(!mflcode.equals("")) {
//                        DISTRICT FOUND ADD THE HF TO THE SYSTEM.........................
                     
                  // if(new Integer(yearmonth)>=201710 && new Integer(yearmonth)<=201712 ){
                  
                     
                     if(checker==0){
                         
                         //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
                         
                         String inserter="INSERT INTO "+dbname+" ( id,indicator,adult_3m,child_3m,tl_3m,adult_6m,child_6m,tl_6m,adult_9m,child_9m,tl_9m,adult_12m,child_12m,tl_12m,adult_24m,child_24m,tl_24m,adult_36m,child_36m,tl_36m,adult_48m,child_48m,tl_48m,adult_60m,child_60m,tl_60m,mflcode,reportingyear,reportingmonth,yearmonth) "
                                 + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                         conn.pst=conn.connect.prepareStatement(inserter);
                         conn.pst.setString(1,id);
                         conn.pst.setString(2,indicatorid);
                         conn.pst.setString(3,adult_3m);
                         conn.pst.setString(4,child_3m);
                         conn.pst.setString(5,tl_3m);
                         conn.pst.setString(6,adult_6m);
                         conn.pst.setString(7,child_6m);
                         conn.pst.setString(8,tl_6m);
                         conn.pst.setString(9,adult_9m);
                         conn.pst.setString(10,child_9m);
                         conn.pst.setString(11,tl_9m);
                         conn.pst.setString(12,adult_12m);
                         conn.pst.setString(13,child_12m);
                         conn.pst.setString(14,tl_12m);
                         conn.pst.setString(15,adult_24m);
                         conn.pst.setString(16,child_24m);
                         conn.pst.setString(17,tl_24m);
                         conn.pst.setString(18,adult_36m);
                         conn.pst.setString(19,child_36m);
                         conn.pst.setString(20,tl_36m);
                         conn.pst.setString(21,adult_48m);
                         conn.pst.setString(22,child_48m);
                         conn.pst.setString(23,tl_48m);
                         conn.pst.setString(24,adult_60m);
                         conn.pst.setString(25,child_60m);
                         conn.pst.setString(26,tl_60m);
                         conn.pst.setString(27,mflcode);
                         conn.pst.setString(28,reportingyear);
                         conn.pst.setString(29,reportingmonth);
                         conn.pst.setString(30,yearmonth);
                         conn.pst.executeUpdate();
                          
                         
                         added++;
                         
                     }
                     else {
                         //id,SubPartnerID,Year,Quarter,Mflcode,Sex ,age,agebracket,SubPartnerNom,dateoftesting,patientccc,batchno,supporttype
                         String inserter=" UPDATE "+dbname+" SET id=?,indicator=?,adult_3m=?,child_3m=?,tl_3m=?,adult_6m=?,child_6m=?,tl_6m=?,adult_9m=?,child_9m=?,tl_9m=?,adult_12m=?,child_12m=?,tl_12m=?,adult_24m=?,child_24m=?,tl_24m=?,adult_36m=?,child_36m=?,tl_36m=?,adult_48m=?,child_48m=?,tl_48m=?,adult_60m=?,child_60m=?,tl_60m=?,mflcode=?,reportingyear=?,reportingmonth=?,yearmonth=?"
                                 + " WHERE id=?";
//
                         conn.pst=conn.connect.prepareStatement(inserter);
                         
                         conn.pst.setString(1,id);
                         conn.pst.setString(2,indicatorid);
                         conn.pst.setString(3,adult_3m);
                         conn.pst.setString(4,child_3m);
                         conn.pst.setString(5,tl_3m);
                         conn.pst.setString(6,adult_6m);
                         conn.pst.setString(7,child_6m);
                         conn.pst.setString(8,tl_6m);
                         conn.pst.setString(9,adult_9m);
                         conn.pst.setString(10,child_9m);
                         conn.pst.setString(11,tl_9m);
                         conn.pst.setString(12,adult_12m);
                         conn.pst.setString(13,child_12m);
                         conn.pst.setString(14,tl_12m);
                         conn.pst.setString(15,adult_24m);
                         conn.pst.setString(16,child_24m);
                         conn.pst.setString(17,tl_24m);
                         conn.pst.setString(18,adult_36m);
                         conn.pst.setString(19,child_36m);
                         conn.pst.setString(20,tl_36m);
                         conn.pst.setString(21,adult_48m);
                         conn.pst.setString(22,child_48m);
                         conn.pst.setString(23,tl_48m);
                         conn.pst.setString(24,adult_60m);
                         conn.pst.setString(25,child_60m);
                         conn.pst.setString(26,tl_60m);
                         conn.pst.setString(27,mflcode);
                         conn.pst.setString(28,reportingyear);
                         conn.pst.setString(29,reportingmonth);
                         conn.pst.setString(30,yearmonth);
                         conn.pst.setString(31,id);
                          conn.pst.executeUpdate();
                         updated++;
                     }
                     
                 //}
             }
                 else{
                     missing++; 
//                        missing facilities
                     missingFacility+="facility  : "+facilityName+" mfl code : "+mflcode+" not in system "+i+"<br>";
                     System.out.println(facilityName+ " has no mflcode.");
                 }
                 i++;
                 } //end of iterator
             catch (SQLException ex) {
                 Logger.getLogger(importart.class.getName()).log(Level.SEVERE, null, ex);
             }
             
         }
         
         }//end of worksheets loop

     
          }
         
     }
    //end of checking if excel file is valid
     if(conn.rs!=null){try {
         conn.rs.close();
          } catch (SQLException ex) {
              Logger.getLogger(importart.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.st!=null){try {
          conn.st.close();
          } catch (SQLException ex) {
              Logger.getLogger(importart.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.pst!=null){try {
          conn.pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(importart.class.getName()).log(Level.SEVERE, null, ex);
          }
}
    
            if(conn.connect!=null){try {
                conn.connect.close();
         } catch (SQLException ex) {
             Logger.getLogger(importart.class.getName()).log(Level.SEVERE, null, ex);
         }
}
      
       String nomflcode="";
      if(!nomflsheets.equals("")){
      
      nomflcode="<b> "+nomflsheets+"</b> have no mflcodes ";
      }
      
     String sessionText="<br/><b> "+added+ "</b> New data added <br/> <b> "+updated+"</b> updated facilities<br> <br> <b>"+nomflcode+"</b>";    
     session.setAttribute("uploadedart"," Workbooks: "+fileNames+". "+ sessionText);
    
 
  
    
    response.sendRedirect(nextpage);  
 

 
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
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//     try {
//         processRequest(request, response);
//     } catch (SQLException ex) {
//         Logger.getLogger(loadPMTCT_FO.class.getName()).log(Level.SEVERE, null, ex);
//     }
//    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//     try {
//         processRequest(request, response);
//     } catch (SQLException ex) {
//         Logger.getLogger(loadPMTCT_FO.class.getName()).log(Level.SEVERE, null, ex);
//     }
//    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
      
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return contentDisp;
    }
    

    
}
