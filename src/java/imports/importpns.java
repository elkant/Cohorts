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


  public class importpns extends HttpServlet {
   
 
  
  String full_path="";
  String fileName="";
  int checker_dist,checker_hf;
  File file_source;
  HttpSession session;
  private static final long serialVersionUID = 205242440643911308L;
  private static final String UPLOAD_DIR = "uploads";
  String nextpage="";
  String quarterName,facilityName,facilityID,id,missingFacility;
          
 

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    
      int year,quarter,checker,missing = 0,added = 0,updated = 0;
      String county_name,county_id, district_name,district_id,hf_name,hf_id;
     
        session=request.getSession();
        
       
      
     /***  
id
mflcode
year
month
weekstart
weekend
indicator
pns_ukf
pns_ukm
pns_1
pns_9
pns_14f
pns_14m
pns_19m
pns_19f
pns_24m
pns_24f
pns_29m
pns_29f
pns_49m
pns_49f
pns_50f
pns_50m
pns_t
yearmonth
      ***/
     
  id=""; 
String indicator="";
String indicatorid="";
String weekstart="";
String weekend="";

weekstart=request.getParameter("weekstart");
weekend=request.getParameter("weekend");

if(!weekstart.equals("")){
 session.setAttribute("weekstart",weekstart );
 session.setAttribute("weekend",weekend );
}

String pns_ukf="";
String pns_ukm="";
String pns_1="";
String pns_9="";
String pns_14f="";
String pns_14m="";
String pns_19m="";
String pns_19f="";
String pns_24m="";
String pns_24f="";
String pns_29m="";
String pns_29f="";
String pns_49m="";
String pns_49f="";
String pns_50f="";
String pns_50m="";
String pns_t="";
String yearmonth="";

String mflcode="";
String reportingyear="";
String reportingmonth="";


 
 
    String serialnumber="";
    
     String dbname="pns";
  
     
   
     dbConn conn = new dbConn();
     nextpage="importpns.jsp";
     String applicationPath = request.getServletContext().getRealPath("");
     String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
     session=request.getSession();
     File fileSaveDir = new File(uploadFilePath);
     if (!fileSaveDir.exists()) {
         fileSaveDir.mkdirs();
     }
     System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
   
     
      for (Part part : request.getParts()) {
            if(!getFileName(part).equals("")){
           fileName = getFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
            }
        }
     
     if(!fileName.endsWith(".xlsx")){
         nextpage="importpns.jsp";
         session.setAttribute("upload_success", "<font color=\"red\">Failed to load the excel file. Please choose a .xlsx excel file .</font>");
     }
     else{
         
         full_path=fileSaveDir.getAbsolutePath()+"\\"+fileName;
         
         System.out.println("the saved file directory is  :  "+full_path);
// GET DATA FROM THE EXCEL AND AND OUTPUT IT ON THE CONSOLE..................................
         
         FileInputStream fileInputStream = new FileInputStream(full_path);
         XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
         
         int totalsheets=workbook.getNumberOfSheets();
         DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale


        
         for(int a=0;a<totalsheets;a++){
         
         XSSFSheet worksheet = workbook.getSheetAt(a);
         
         
          System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
         
          String sheetname =  workbook.getSheetName(a);
          
//_______        
         
         //if(1==1){
         //skip pivot PNS
         if(!sheetname.equals("Pivot PNS")){
         
         Iterator rowIterator = worksheet.rowIterator();
             
        
         
         int row=3,col=0;
         
         //static number of rows
         while(row<=9){
             try {
                System.out.println(" row number "+row);
                 XSSFRow rowi = worksheet.getRow(row);
                 if( rowi==null )
                 {
                     
                     break;
                 }
     
                 
                 if( row>=3 && row<=9 ) {
// _______________________________________________________________
                 
               
//______________________________________________________________________
                     //-----------facility name-----------------------
                     XSSFCell cellfacil = worksheet.getRow(row).getCell((short) 2);
                     
                     if(cellfacil.getCellType()==0){
                         //numeric
                         facilityName =""+(int)cellfacil.getNumericCellValue();
                     }
                     else if(cellfacil.getCellType()==1){
                         facilityName =cellfacil.getStringCellValue();
                     }
                     
                     
                     //-----------mfl-----------------------
                     XSSFCell cellmfl = worksheet.getRow(row).getCell((short) 3);
                     
                     if(cellmfl.getCellType()==0){
                         //numeric
                         mflcode =""+(int)cellmfl.getNumericCellValue();
                     }
                     else if(cellmfl.getCellType()==1){
                         mflcode =cellmfl.getStringCellValue();
                     }
                     
                     //-----------year-----------------------
                     XSSFCell cellyear = worksheet.getRow(row).getCell((short) 4);
                     
                     if(cellyear.getCellType()==0){
                         //numeric
                         reportingyear =""+(int)cellyear.getNumericCellValue();
                     }
                     else if(cellyear.getCellType()==1){
                         reportingyear =cellyear.getStringCellValue();
                     }
                     
                   
                     
                     //-----------month-----------------------
                     XSSFCell cellmonth = worksheet.getRow(row).getCell((short) 5);
                     
                     if(cellmonth.getCellType()==0){
                         //numeric
                         reportingmonth =""+(int)cellmonth.getNumericCellValue();
                     }
                     else if(cellmonth.getCellType()==1){
                         reportingmonth =cellmonth.getStringCellValue();
                     }
                     
                     //-----------weekstart-----------------------
                     XSSFCell cellws = worksheet.getRow(row).getCell((short) 6);
                     
                     System.out.println(" Date type is"+cellws.getCellType());
                     if(cellws.getCellType()==0){
                         //numeric
                       //  weekstart =""+(int)cellws.getNumericCellValue();
                     }
                     else if(cellws.getCellType()==1){
                         //weekstart =cellws.getRawValue();
                     } 
                     else if(cellws.getCellType()==2){
                         //weekstart =""+cellws.getRawValue();
                     }
                      else {
                      
                         //weekstart =""+cellws.getRawValue();
                     }
                 
         //-----------weekend-----------------------
                     XSSFCell cellwe = worksheet.getRow(row).getCell((short) 7);
                     
                     if(cellwe.getCellType()==0){
                         //numeric
                         //weekend =""+(int)cellwe.getNumericCellValue();
                     }
                     else if(cellwe.getCellType()==1){
                         //weekend =cellwe.getRawValue();
                     } 
                     else if(cellwe.getCellType()==2){
                         //weekend =""+cellwe.getRawValue();
                     }
                     else {
                      
                         //weekend =""+cellwe.getRawValue();
                     }
                     
                     
                     //elements
                     
                     //indicator
                     
                     XSSFCell indiccell = rowi.getCell((short)8);
                     
                     if(indiccell.getCellType()==0){
                         //numeric
                         indicator =""+(int)indiccell.getNumericCellValue();
                     }
                     else if(indiccell.getCellType()==1){
                         indicator =indiccell.getStringCellValue();
                     }
                     
 
                     
                     //adult_3m
                     
                     XSSFCell pns_ukfcell  = rowi.getCell((short) 9);
                     
                     if(pns_ukfcell.getCellType()==0){
                         //numeric
                         pns_ukf =""+(int)pns_ukfcell.getNumericCellValue();
                     }
                     else if(pns_ukfcell.getCellType()==1){
                         pns_ukf =pns_ukfcell.getStringCellValue();
                     }
                   
                     else {
                         
                         
                     System.out.println("cell type "+pns_ukfcell.getCellType());
                       pns_ukf =""+(int)pns_ukfcell.getNumericCellValue();
                     }
                     
                     if(pns_ukf.trim().equals("")){pns_ukf="0";}
                     
                     
                     //child_3m
                     
                     XSSFCell pns_ukmcell = rowi.getCell((short) 10);
                     
                     if(pns_ukmcell.getCellType()==0){
                         //numeric
                         pns_ukm =""+(int)pns_ukmcell.getNumericCellValue();
                     }
                     else if(pns_ukmcell.getCellType()==1){
                         pns_ukm =pns_ukmcell.getStringCellValue();
                     }
                     
                     else {
                      pns_ukm =""+(int)pns_ukmcell.getNumericCellValue();
                     }
                     
                     if(pns_ukm.trim().equals("")){pns_ukm="0";}
                     
                     //
                     
                     XSSFCell pns_1cell = rowi.getCell((short) 11);
                     
                     if(pns_1cell.getCellType()==0){
                         //numeric
                         pns_1 =""+(int)pns_1cell.getNumericCellValue();
                     }
                     else if(pns_1cell.getCellType()==1){
                         pns_1 =pns_1cell.getStringCellValue();
                     }
                      else {
                      
                         pns_1 =""+(int)pns_1cell.getNumericCellValue();
                     }
                     
                     if(pns_1.trim().equals("")){pns_1="0";}
                     


//adult_6m
                     
                     XSSFCell pns_9cell = rowi.getCell((short) 12);
                     
                     if(pns_9cell.getCellType()==0){
                         //numeric
                         pns_9 =""+(int)pns_9cell.getNumericCellValue();
                     }
                     else if(pns_9cell.getCellType()==1){
                         pns_9 =pns_9cell.getStringCellValue();
                     }
                     else {
                        pns_9 =""+(int)pns_9cell.getNumericCellValue();
                     }
                     if(pns_9.trim().equals("")){pns_9="0";}
                     
                     //child_6m
                     
                     XSSFCell pns_14fcell = rowi.getCell((short) 13);
                     
                     if(pns_14fcell.getCellType()==0){
                         //numeric
                         pns_14f =""+(int)pns_14fcell.getNumericCellValue();
                     }
                     else if(pns_14fcell.getCellType()==1){
                         pns_14f =pns_14fcell.getStringCellValue();
                     }
                     else {
                         
                      pns_14f =""+(int)pns_14fcell.getNumericCellValue();
                     
                     }
                     
                     if(pns_14f.trim().equals("")){pns_14f="0";}
                     
                     //tl_7m
                     
                     XSSFCell pns_14mcell = rowi.getCell((short) 14);
                     
                     if(pns_14mcell.getCellType()==0){
                         //numeric
                         pns_14m =""+(int)pns_14mcell.getNumericCellValue();
                     }
                     else if(pns_14mcell.getCellType()==1){
                         pns_14m =pns_14mcell.getStringCellValue();
                     }
                     else {
                     pns_14m =""+(int)pns_14mcell.getNumericCellValue();
                     
                     }
                     if(pns_14m.trim().equals("")){pns_14m="0";}
                     
                     //adult_9m
                     
                     XSSFCell pns_19fcell = rowi.getCell((short) 15);
                     
                     if(pns_19fcell.getCellType()==0){
                         //numeric
                         pns_19f =""+(int)pns_19fcell.getNumericCellValue();
                     }
                     else if(pns_19fcell.getCellType()==1){
                         pns_19f =pns_19fcell.getStringCellValue();
                     }
                     else {
                       pns_19f =""+(int)pns_19fcell.getNumericCellValue();
                     
                     }
                     
                     
                     XSSFCell pns_19mcell = rowi.getCell((short) 16);
                     
                     if(pns_19mcell.getCellType()==0){
                         //numeric
                         pns_19m =""+(int)pns_19mcell.getNumericCellValue();
                     }
                     else if(pns_19mcell.getCellType()==1){
                         pns_19m =pns_19mcell.getStringCellValue();
                     }
                     else {
                       pns_19m =""+(int)pns_19mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //pns_24f
                     
                     XSSFCell pns_24fcell = rowi.getCell((short) 17);
                     
                     if(pns_24fcell.getCellType()==0){
                         //numeric
                         pns_24f =""+(int)pns_24fcell.getNumericCellValue();
                     }
                     else if(pns_24fcell.getCellType()==1){
                         pns_24f =pns_24fcell.getStringCellValue();
                     }
                     else {
                     
                       pns_24f =""+(int)pns_24fcell.getNumericCellValue();
                     
                     }
                     
                     
                     //tl_9m
                     
                     XSSFCell pns_24mcell = rowi.getCell((short) 18);
                     
                     if(pns_24mcell.getCellType()==0){
                         //numeric
                         pns_24m =""+(int)pns_24mcell.getNumericCellValue();
                     }
                     else if(pns_24mcell.getCellType()==1){
                         pns_24m =pns_24mcell.getStringCellValue();
                     }
                     
                     else {
                     
                     pns_24m =""+(int)pns_24mcell.getNumericCellValue();
                     
                     }
                     
                     if(pns_19f.trim().equals("")){pns_19f="0";}
                     if(pns_24f.trim().equals("")){pns_24f="0";}
                     if(pns_24m.trim().equals("")){pns_24m="0";}
                     
                     //adult_12m
                     
                     XSSFCell pns_29fcell = rowi.getCell((short) 19);
                     
                     if(pns_29fcell.getCellType()==0){
                         //numeric
                         pns_29f =""+(int)pns_29fcell.getNumericCellValue();
                     }
                     else if(pns_29fcell.getCellType()==1){
                         pns_29f =pns_29fcell.getStringCellValue();
                     }
                     else {
                     
                     pns_29f =""+(int)pns_29fcell.getNumericCellValue();
                     }
                     
                     
                     //pns_29m
                     
                     XSSFCell pns_29mcell = rowi.getCell((short) 20);
                     
                     if(pns_29mcell.getCellType()==0){
                         //numeric
                         pns_29m =""+(int)pns_29mcell.getNumericCellValue();
                     }
                     else if(pns_29mcell.getCellType()==1){
                         pns_29m =pns_29mcell.getStringCellValue();
                     }
                     else {
                     pns_29m =""+(int)pns_29mcell.getNumericCellValue();
                     
                     }
                     
                     //pns_49f
                     
                     XSSFCell pns_49fcell = rowi.getCell((short) 21);
                     
                     if(pns_49fcell.getCellType()==0){
                         //numeric
                         pns_49f =""+(int)pns_49fcell.getNumericCellValue();
                     }
                     else if(pns_49fcell.getCellType()==1){
                         pns_49f =pns_49fcell.getStringCellValue();
                     }
                     else {
                       pns_49f =""+(int)pns_49fcell.getNumericCellValue();
                     
                     }
                     
                     if(pns_29f.trim().equals("")){pns_29f="0";}
                     if(pns_29m.trim().equals("")){pns_29m="0";}
                     if(pns_49f.trim().equals("")){pns_49f="0";}
                     
                     //pns_49m
                     
                     XSSFCell pns_49mcell = rowi.getCell((short) 22);
                     
                     if(pns_49mcell.getCellType()==0){
                         //numeric
                         pns_49m =""+(int)pns_49mcell.getNumericCellValue();
                     }
                     else if(pns_49mcell.getCellType()==1){
                         pns_49m =pns_49mcell.getStringCellValue();
                     }
                     else {
                      pns_49m =""+(int)pns_49mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //pns_50f
                     
                     XSSFCell pns_50fcell = rowi.getCell((short) 23);
                     
                     if(pns_50fcell.getCellType()==0){
                         //numeric
                         pns_50f =""+(int)pns_50fcell.getNumericCellValue();
                     }
                     else if(pns_50fcell.getCellType()==1){
                         pns_50f =pns_50fcell.getStringCellValue();
                     }
                     else {
                     pns_50f =""+(int)pns_50fcell.getNumericCellValue();
                     
                     }
                     
                     //pns_50m
                     
                     XSSFCell pns_50mcell = rowi.getCell((short) 24);
                     
                     if(pns_50mcell.getCellType()==0){
                         //numeric
                         pns_50m =""+(int)pns_50mcell.getNumericCellValue();
                     }
                     else if(pns_50mcell.getCellType()==1){
                         pns_50m =pns_50mcell.getStringCellValue();
                     }
                     else {
                         
                      pns_50m =""+(int)pns_50mcell.getNumericCellValue();
                     
                     }
                     
                     if(pns_49m.trim().equals("")){pns_49m="0";}
                     if(pns_50f.trim().equals("")){pns_50f="0";}
                     if(pns_50m.trim().equals("")){pns_50m="0";}
                     
                     //adult_36m
                     
                     XSSFCell pns_tcell = rowi.getCell((short) 25);
                     
                     if(pns_tcell.getCellType()==0){
                         //numeric
                         pns_t =""+(int)pns_tcell.getNumericCellValue();
                     }
                     else if(pns_tcell.getCellType()==1){
                         pns_t =pns_tcell.getStringCellValue();
                     }
                     
                     else {
                     pns_t =""+(int)pns_tcell.getNumericCellValue();
                     
                     }
                     
                      if(pns_t.trim().equals("")){pns_t="0";}
                     
                     
                     
                     
                 }//end of cell values
                 
                 
                 if(reportingmonth.length()==1){  reportingmonth="0"+reportingmonth; }
                 
                 yearmonth=reportingyear+""+reportingmonth;
                 
                 
                 //================================continue from here========================================
                 
                 //get indicator id from list of indicators
                 //create an id consisting of year_month_mflcode_indicator
                 
                 
                 
                 String getindicator="SELECT id,indicator FROM pmtct_art_cohort.pns_indicators where indicator like '%"+indicator+"'";
                 //dbConn conn= new dbConn();
                 //System.out.println(" Qry "+getindicator);     
                 conn.rs=conn.state.executeQuery(getindicator);
                 while(conn.rs.next()){
                     
                     indicatorid=conn.rs.getString("id");
                     
                 }
                 
                
                 
                 String id=reportingyear+"_"+reportingmonth+"_"+weekend.replace("-","_")+"_"+mflcode+"_"+indicatorid;
                 
                 
                 
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
                          System.out.println("**inserting data ");
                         //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
                         
                         String inserter="INSERT INTO "+dbname+" (id,mflcode,year,month,weekstart,weekend,indicator,pns_ukf,pns_ukm,pns_1,pns_9,pns_14f,pns_14m,pns_19m,pns_19f,pns_24m,pns_24f,pns_29m,pns_29f,pns_49m,pns_49f,pns_50f,pns_50m,pns_t,yearmonth) "
                                 + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                         conn.pst=conn.connect.prepareStatement(inserter);
                         conn.pst.setString(1,id);
                         conn.pst.setString(2,mflcode);
                         conn.pst.setString(3,reportingyear);
                         conn.pst.setString(4,reportingmonth);
                         conn.pst.setString(5,weekstart);
                         conn.pst.setString(6,weekend);
                         conn.pst.setString(7,indicatorid);
                         conn.pst.setString(8,pns_ukf);
                         conn.pst.setString(9,pns_ukm);
                         conn.pst.setString(10,pns_1);
                         conn.pst.setString(11,pns_9);
                         conn.pst.setString(12,pns_14f);
                         conn.pst.setString(13,pns_14m);
                         conn.pst.setString(14,pns_19f);
                         conn.pst.setString(15,pns_19m);
                         conn.pst.setString(16,pns_24f);
                         conn.pst.setString(17,pns_24m);
                         conn.pst.setString(18,pns_29f);
                         conn.pst.setString(19,pns_29m);
                         conn.pst.setString(20,pns_49f);
                         conn.pst.setString(21,pns_49m);
                         conn.pst.setString(22,pns_50f);
                         conn.pst.setString(23,pns_50m);
                         conn.pst.setString(24,pns_t);
                         conn.pst.setString(25,yearmonth);
                         
                         conn.pst.executeUpdate();
                          System.out.println(""+conn.pst);
                         
                         added++;
                         
                     }
                     else {
                         
                         System.out.println("**Updating data ");
                         //id,SubPartnerID,Year,Quarter,Mflcode,Sex ,age,agebracket,SubPartnerNom,dateoftesting,patientccc,batchno,supporttype
                         String inserter=" UPDATE "+dbname+" SET id=?,mflcode=?,year=?,month=?,weekstart=?,weekend=?,indicator=?,pns_ukf=?,pns_ukm=?,pns_1=?,pns_9=?,pns_14f=?,pns_14m=?,pns_19m=?,pns_19f=?,pns_24m=?,pns_24f=?,pns_29m=?,pns_29f=?,pns_49m=?,pns_49f=?,pns_50f=?,pns_50m=?,pns_t=?,yearmonth=?"
                                 + " WHERE id=?";
//
                         conn.pst=conn.connect.prepareStatement(inserter);
                         
                         conn.pst.setString(1,id);
                         conn.pst.setString(2,mflcode);
                         conn.pst.setString(3,reportingyear);
                         conn.pst.setString(4,reportingmonth);
                         conn.pst.setString(5,weekstart);
                         conn.pst.setString(6,weekend);
                         conn.pst.setString(7,indicatorid);
                         conn.pst.setString(8,pns_ukf);
                         conn.pst.setString(9,pns_ukm);
                         conn.pst.setString(10,pns_1);
                         conn.pst.setString(11,pns_9);
                         conn.pst.setString(12,pns_14f);
                         conn.pst.setString(13,pns_14m);
                         conn.pst.setString(14,pns_19f);
                         conn.pst.setString(15,pns_19m);
                         conn.pst.setString(16,pns_24f);
                         conn.pst.setString(17,pns_24m);
                         conn.pst.setString(18,pns_29f);
                         conn.pst.setString(19,pns_29m);
                         conn.pst.setString(20,pns_49f);
                         conn.pst.setString(21,pns_49m);
                         conn.pst.setString(22,pns_50f);
                         conn.pst.setString(23,pns_50m);
                         conn.pst.setString(24,pns_t);
                         conn.pst.setString(25,yearmonth);
                         conn.pst.setString(26,id);
                         conn.pst.executeUpdate();
                         updated++;
                     }
                     
                 //}
             }
                 else{
                     missing++; 
//                        missing facilities
                     missingFacility+="facility  : "+facilityName+" mfl code : "+mflcode+" not in system "+row+"<br>";
                     System.out.println(facilityName+ " has no mflcode.");
                 }
                 
                 } //end of iterator
             catch (SQLException ex) {
                 Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
             }
           row++;  
             System.out.println(" eeend of while looop");
           
         }
         
        
         }
         }//end of worksheets loop

     }//end of checking if excel file is valid
     if(conn.rs!=null){try {
         conn.rs.close();
          } catch (SQLException ex) {
              Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.st!=null){try {
          conn.st.close();
          } catch (SQLException ex) {
              Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.pst!=null){try {
          conn.pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
    String sessionText="<br/><b> "+added+ "</b> New data added <br/> <b> "+updated+"</b> updated facilities<br> <br> <b>"+missing+"</b> sites not in Imis Facilities List ";    
    session.setAttribute("uploadedpns"," File name is "+fileName+". "+ sessionText);
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
            String file_name="";
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
      
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                file_name = token.substring(token.indexOf("=") + 2, token.length()-1);
              break;  
            }
            
        }
         System.out.println("content-disposition final : "+file_name);
        return file_name;
    }
    
}
