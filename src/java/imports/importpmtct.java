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


  public class importpmtct extends HttpServlet {
   
 
  
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
String kp_3m="";
String np_3m="";
String tl_3m="";
String kp_6m="";
String np_6m="";
String tl_6m="";
String kp_9m="";
String np_9m="";
String tl_9m="";
String kp_12m="";
String np_12m="";
String tl_12m="";
String kp_24m="";
String np_24m="";
String tl_24m="";
String mflcode="";
String reportingyear="";
String reportingmonth="";
String yearmonth="";

 
 
    String serialnumber="";
    
     String dbname="pmtct_cohort";
  
     
     session=request.getSession();
     dbConn conn = new dbConn();
     nextpage="importpmtct.jsp";
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
     }
     if(!fileName.endsWith(".xlsx")){
         nextpage="importpmtct.jsp";
         session.setAttribute("upload_success", "<font color=\"red\">Failed to load the excel file. Please choose a .xlsx excel file .</font>");
     }
     else{
         
         full_path=fileSaveDir.getAbsolutePath()+"\\"+fileName;
         
         System.out.println("the saved file directory is  :  "+full_path);
// GET DATA FROM THE EXCEL AND AND OUTPUT IT ON THE CONSOLE..................................
         
         FileInputStream fileInputStream = new FileInputStream(full_path);
         XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
         
         int totalsheets=workbook.getNumberOfSheets();
         
        
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
                         mflcode =cellmfl.getStringCellValue();
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
                     
                     XSSFCell kp_3mcell = rowi.getCell((short) 2);
                     
                     if(kp_3mcell.getCellType()==0){
                         //numeric
                         kp_3m =""+(int)kp_3mcell.getNumericCellValue();
                     }
                     else if(kp_3mcell.getCellType()==1){
                         kp_3m =kp_3mcell.getStringCellValue();
                     }
                     else {
                     
                       kp_3m =""+(int)kp_3mcell.getNumericCellValue();
                     }
                     
                     if(kp_3m.trim().equals("")){kp_3m="";}
                     
                     
                     //np_3m
                     
                     XSSFCell np_3mcell = rowi.getCell((short) 3);
                     
                     if(np_3mcell.getCellType()==0){
                         //numeric
                         np_3m =""+(int)np_3mcell.getNumericCellValue();
                     }
                     else if(np_3mcell.getCellType()==1){
                         np_3m =np_3mcell.getStringCellValue();
                     }
                     
                     else {
                      np_3m =""+(int)np_3mcell.getNumericCellValue();
                     }
                     
                     if(np_3m.trim().equals("")){np_3m="";}
                     
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
                     
                     
                     if(tl_3m.trim().equals("")){tl_3m="";}
                     //kp_6m
                     
                     XSSFCell kp_6mcell = rowi.getCell((short) 5);
                     
                     if(kp_6mcell.getCellType()==0){
                         //numeric
                         kp_6m =""+(int)kp_6mcell.getNumericCellValue();
                     }
                     else if(kp_6mcell.getCellType()==1){
                         kp_6m =kp_6mcell.getStringCellValue();
                     }
                     else {
                        kp_6m =""+(int)kp_6mcell.getNumericCellValue();
                     }
                     if(kp_6m.trim().equals("")){kp_6m="";}
                     
                     //np_6m
                     
                     XSSFCell np_6mcell = rowi.getCell((short) 6);
                     
                     if(np_6mcell.getCellType()==0){
                         //numeric
                         np_6m =""+(int)np_6mcell.getNumericCellValue();
                     }
                     else if(np_6mcell.getCellType()==1){
                         np_6m =np_6mcell.getStringCellValue();
                     }
                     else {
                         
                      np_6m =""+(int)np_6mcell.getNumericCellValue();
                     
                     }
                     
                     if(np_6m.trim().equals("")){np_6m="";}
                     
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
                     if(tl_6m.trim().equals("")){tl_6m="";}
                     
                     //kp_9m
                     
                     XSSFCell kp_9mcell = rowi.getCell((short) 8);
                     
                     if(kp_9mcell.getCellType()==0){
                         //numeric
                         kp_9m =""+(int)kp_9mcell.getNumericCellValue();
                     }
                     else if(kp_9mcell.getCellType()==1){
                         kp_9m =kp_9mcell.getStringCellValue();
                     }
                     else {
                       kp_9m =""+(int)kp_9mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //np_9m
                     
                     XSSFCell np_9mcell = rowi.getCell((short) 9);
                     
                     if(np_9mcell.getCellType()==0){
                         //numeric
                         np_9m =""+(int)np_9mcell.getNumericCellValue();
                     }
                     else if(np_9mcell.getCellType()==1){
                         np_9m =np_9mcell.getStringCellValue();
                     }
                     else {
                     
                       np_9m =""+(int)np_9mcell.getNumericCellValue();
                     
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
                     
                     if(kp_9m.trim().equals("")){kp_9m="";}
                     if(np_9m.trim().equals("")){np_9m="";}
                     if(tl_9m.trim().equals("")){tl_9m="";}
                     
                     //kp_12m
                     
                     XSSFCell kp_12mcell = rowi.getCell((short) 11);
                     
                     if(kp_12mcell.getCellType()==0){
                         //numeric
                         kp_12m =""+(int)kp_12mcell.getNumericCellValue();
                     }
                     else if(kp_12mcell.getCellType()==1){
                         kp_12m =kp_12mcell.getStringCellValue();
                     }
                     else {
                     
                     kp_12m =""+(int)kp_12mcell.getNumericCellValue();
                     }
                     
                     
                     //np_12m
                     
                     XSSFCell np_12mcell = rowi.getCell((short) 12);
                     
                     if(np_12mcell.getCellType()==0){
                         //numeric
                         np_12m =""+(int)np_12mcell.getNumericCellValue();
                     }
                     else if(np_12mcell.getCellType()==1){
                         np_12m =np_12mcell.getStringCellValue();
                     }
                     else {
                     np_12m =""+(int)np_12mcell.getNumericCellValue();
                     
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
                     
                     if(kp_12m.trim().equals("")){kp_12m="";}
                     if(np_12m.trim().equals("")){np_12m="";}
                     if(tl_12m.trim().equals("")){tl_12m="";}
                     
                     //kp_24m
                     
                     XSSFCell kp_24mcell = rowi.getCell((short) 14);
                     
                     if(kp_24mcell.getCellType()==0){
                         //numeric
                         kp_24m =""+(int)kp_24mcell.getNumericCellValue();
                     }
                     else if(kp_24mcell.getCellType()==1){
                         kp_24m =kp_24mcell.getStringCellValue();
                     }
                     else {
                      kp_24m =""+(int)kp_24mcell.getNumericCellValue();
                     
                     }
                     
                     
                     //np_24m
                     
                     XSSFCell np_24mcell = rowi.getCell((short) 15);
                     
                     if(np_24mcell.getCellType()==0){
                         //numeric
                         np_24m =""+(int)np_24mcell.getNumericCellValue();
                     }
                     else if(np_24mcell.getCellType()==1){
                         np_24m =np_24mcell.getStringCellValue();
                     }
                     else {
                     np_24m =""+(int)np_24mcell.getNumericCellValue();
                     
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
                     
                     if(kp_24m.trim().equals("")){kp_24m="";}
                     if(np_24m.trim().equals("")){np_24m="";}
                     if(tl_24m.trim().equals("")){tl_24m="";}
                     
                     
                     
                 }//end of cell values
                 
                 
                 if(reportingmonth.length()==1){  reportingmonth="0"+reportingmonth; }
                 
                 yearmonth=reportingyear+""+reportingmonth;
                 
                 
                 
                 
                 
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
                     
                   
                  
                     
                     if(checker==0){
                         
                         //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
                         
                         String inserter="INSERT INTO "+dbname+" ( id,indicator,kp_3m,np_3m,tl_3m,kp_6m,np_6m,tl_6m,kp_9m,np_9m,tl_9m,kp_12m,np_12m,tl_12m,kp_24m,np_24m,tl_24m,mflcode,reportingyear,reportingmonth,yearmonth) "
                                 + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                         conn.pst=conn.connect.prepareStatement(inserter);
                         conn.pst.setString(1,id);
                         conn.pst.setString(2,indicatorid);
                         conn.pst.setString(3,kp_3m);
                         conn.pst.setString(4,np_3m);
                         conn.pst.setString(5,tl_3m);
                         conn.pst.setString(6,kp_6m);
                         conn.pst.setString(7,np_6m);
                         conn.pst.setString(8,tl_6m);
                         conn.pst.setString(9,kp_9m);
                         conn.pst.setString(10,np_9m);
                         conn.pst.setString(11,tl_9m);
                         conn.pst.setString(12,kp_12m);
                         conn.pst.setString(13,np_12m);
                         conn.pst.setString(14,tl_12m);
                         conn.pst.setString(15,kp_24m);
                         conn.pst.setString(16,np_24m);
                         conn.pst.setString(17,tl_24m);
                         conn.pst.setString(18,mflcode);
                         conn.pst.setString(19,reportingyear);
                         conn.pst.setString(20,reportingmonth);
                         conn.pst.setString(21,yearmonth);
                         conn.pst.executeUpdate();
                         
                         
                         
                         
                         
                         
                         
                         added++;
                         
                     }
                     else {
                         //id,SubPartnerID,Year,Quarter,Mflcode,Sex ,age,agebracket,SubPartnerNom,dateoftesting,patientccc,batchno,supporttype
                         String inserter=" UPDATE "+dbname+" SET id=?,indicator=?,kp_3m=?,np_3m=?,tl_3m=?,kp_6m=?,np_6m=?,tl_6m=?,kp_9m=?,np_9m=?,tl_9m=?,kp_12m=?,np_12m=?,tl_12m=?,kp_24m=?,np_24m=?,tl_24m=?,mflcode=?,reportingyear=?,reportingmonth=?,yearmonth=?"
                                 + " WHERE id=?";
//
                         conn.pst=conn.connect.prepareStatement(inserter);
                         
                         conn.pst.setString(1,id);
                         conn.pst.setString(2,indicatorid);
                         conn.pst.setString(3,kp_3m);
                         conn.pst.setString(4,np_3m);
                         conn.pst.setString(5,tl_3m);
                         conn.pst.setString(6,kp_6m);
                         conn.pst.setString(7,np_6m);
                         conn.pst.setString(8,tl_6m);
                         conn.pst.setString(9,kp_9m);
                         conn.pst.setString(10,np_9m);
                         conn.pst.setString(11,tl_9m);
                         conn.pst.setString(12,kp_12m);
                         conn.pst.setString(13,np_12m);
                         conn.pst.setString(14,tl_12m);
                         conn.pst.setString(15,kp_24m);
                         conn.pst.setString(16,np_24m);
                         conn.pst.setString(17,tl_24m);
                         conn.pst.setString(18,mflcode);
                         conn.pst.setString(19,reportingyear);
                         conn.pst.setString(20,reportingmonth);
                         conn.pst.setString(21,yearmonth);
                         conn.pst.setString(22,id);
                          conn.pst.executeUpdate();
                         updated++;
                     }
                     
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

     }//end of checking if excel file is valid
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
    String sessionText="<br/><b> "+added+ "</b> New data added <br/> <b> "+updated+"</b> updated facilities<br> <br> <b>"+missing+"</b> sites not in Imis Facilities List ";    
    session.setAttribute("upload_success", sessionText);
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
