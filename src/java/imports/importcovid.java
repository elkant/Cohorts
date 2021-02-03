/*
Notes: This raw data is for positive EID. The data doesnt have age and sex
Age and sex should be gotten from the eid tested raw data during the importing of the raw data positives into the eid_datim_output table.

 */

package imports;


import db.dbConn;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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


  public class importcovid extends HttpServlet {
   

 
  HttpSession session;
  private static final long serialVersionUID = 205242440643911308L;
  private static final String UPLOAD_DIR = "uploads";
      
 

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
     
      String full_path="";
  String fileName="";
  String fileNameCopy="";
  int checker_dist,checker_hf;
  
   String nextpage="";
  String quarterName,facilityName="",facilityID,id="",missingFacility,NocontactsFacility;
    int nomflcodesites,nocontactsmodalitysites; 
     
  String sessionText="";
    
      int year,quarter,checker,missing = 0,added = 0,updated = 0;
      String county_name,county_id, district_name,district_id,hf_name,hf_id;
     
      session=request.getSession();
        
      nomflcodesites=0; 
      nocontactsmodalitysites=0; 
      missing=0; 
      added=0; 
      updated=0; 
      missingFacility=""; 
      NocontactsFacility=""; 
      fileNameCopy="";
      fileName="";
      checker_dist=0;
      checker_hf=0;
      File file_source;

     
id=""; 
String indicator="";
String indicatorid="";
String weekstart="";
String weekend="";

String delivery_point="";
String datefromfile="";


weekstart=request.getParameter("weekstart");
  weekend=request.getParameter("weekend");

if(!weekstart.equals("")){
 session.setAttribute("weekstart",weekstart );
 session.setAttribute("weekend",weekend );
                         }

String reportingyear="";
String reportingmonth="";


//-----------year-----------------------



reportingyear=weekend.substring(0,4);

reportingmonth=weekend.substring(5,7);


String updatedfacil="";
String insertedfacil="";
String missingwithdatafacil="";
String missingcontwithdatafacil="";
String unimporteddata="";
String updateddata="";
String newdaata="";

  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
  

String f14="";
String m14="";
String f24="";
String m24="";
String f25="";
String m25="";
String total="0";

String der_1f = "";
String der_1m = "";
String der_4f = "";
String der_4m = "";
String der_9f = "";
String der_9m = "";
String der_14f = "";
String der_14m = "";
String der_19f = "";
String der_19m = "";
String der_24f = "";
String der_24m = "";
String der_29f = "";
String der_29m = "";
String der_34f = "";
String der_34m = "";
String der_39f = "";
String der_39m = "";
String der_44f = "";
String der_44m = "";
String der_49f = "";
String der_49m = "";
String der_50f = "";
String der_50m = "";


String submitted_by="";
String Designation="";


String yearmonth="";

String mflcode="";

 
    String serialnumber="";
    
     String dbname="der_rri.der_table_age_covid";
  
   
     dbConn conn = new dbConn();
     
     nextpage="importcovid.jsp";
     
     String applicationPath = request.getServletContext().getRealPath("");
     String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
     session=request.getSession();
     
     File fileSaveDir = new File(uploadFilePath);
     
     if (!fileSaveDir.exists()) {
         fileSaveDir.mkdirs();
                                }
    // System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
   
      missing=0; 
      added=0; 
      updated=0; 
      for (Part part : request.getParts()) {
         
           nomflcodesites=0; 
           nocontactsmodalitysites=0; 
     
      missingFacility="";
      NocontactsFacility="";
          
            if(!getFileName(part).equals("")){
                
           fileName = getFileName(part);
           
           fileNameCopy+=fileName+",";
            part.write(uploadFilePath + File.separator + fileName);
            
              if(!fileName.endsWith(".xlsx")){
         nextpage="importcovid.jsp";
         sessionText="<font color=\"red\">Failed to load a .xls excel file. Please open the file, go to file> options > save as , then save as .xlsx </font>";
                                             }
            
            }
        //}
     
     if(!fileName.endsWith(".xlsx"))
     {
       
         nextpage="importcovid.jsp";
     }
     else {
         
               try {
full_path=fileSaveDir.getAbsolutePath()+"/"+fileName; //end of checking if excel file is valid

FileInputStream fileInputStream = new FileInputStream(full_path);

XSSFWorkbook workbook = (XSSFWorkbook) ReadExcel(full_path);

if(1==2){
    
    
    
    
    
    
    
    
}
int totalsheets=workbook.getNumberOfSheets();
DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
int rowsngapi=1;
int rowCount=34;
//for(int a=0;a<totalsheets;a++){
for(int a=0;a<1;a++){
    
    XSSFSheet worksheet = workbook.getSheet("COVID 19");
    
    String hasdata="no";
    
  //  System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
    
    String sheetname =  "COVID 19";
    
//_______

//if(1==1){
//skip pivot PNS
if(!sheetname.equals("data") && !sheetname.equals("SiteSetUp") && !sheetname.equals("SurgeSites") && !sheetname.equals("indicator definition")  ){
    
    Iterator rowIterator = worksheet.rowIterator();
    
    
    
    int row=3,col=0;
    
    //static number of rows
   
    
//______________________________________________________________________
//-----------facility name-----------------------
XSSFCell cellfacil = worksheet.getRow(4).getCell((short) 1);

if(cellfacil.getCellType()==0){
    //numeric
    facilityName =""+(int)cellfacil.getNumericCellValue();
}
else if(cellfacil.getCellType()==1){
    facilityName =cellfacil.getStringCellValue();
}
System.out.println("\n\nART Upload for:: "+facilityName+" \n Date:: "+weekstart+"  \n File name:: "+fileName);

//-----------mfl-----------------------
XSSFCell cellmfl = worksheet.getRow(4).getCell((short) 2);

if(cellmfl.getCellType()==0)
{
    //numeric
    mflcode =""+(int)cellmfl.getNumericCellValue();
}
else if(cellmfl.getCellType()==1)
{
    mflcode =cellmfl.getStringCellValue();
}
else if(cellmfl.getCellType()==2)
{
    mflcode =cellmfl.getRawValue();
   
}

else 
{
  //  System.out.println("mflcode__"+mflcode);
    mflcode =cellmfl.getRawValue();
    
}
//System.out.println("Mfl code is "+mflcode);




String printval="";
     
     boolean isprinting=false;
     XSSFCell printingcell= worksheet.getRow(1).getCell((short) 0);
     
if(printingcell.getCellType()==0){
    //numeric
    printval =""+(int)printingcell.getNumericCellValue();
}
else if(printingcell.getCellType()==1){
    printval =printingcell.getStringCellValue();
}

  if(printval.equals("ADF V 5.0.0")){ isprinting=true;  }   
     
      //__________________________version row________________
    
     String versionno="";
     
   // System.out.println(""+printval); 
     
 if(isprinting){


    
    //    __________________Date Tested_____________________________
    

    XSSFCell cellcontactsfrom= worksheet.getRow(5).getCell((short)2);
    delivery_point =cellcontactsfrom.getStringCellValue();
    
      //   if(delivery_point.equals("CCC"))  { delivery_point="1";}
    //else if(delivery_point.equals("PMTCT")){ delivery_point="2";}
     if(delivery_point.equals("COVID 19")){ delivery_point="3";}
    
    
    
    
      XSSFCell celldesignation=worksheet.getRow(35).getCell((short)2);
//    Designation =celldesignation.getStringCellValue(); 
    
    if(celldesignation.getCellType()==0){
    //numeric
    Designation =""+(int)celldesignation.getNumericCellValue();
}
else if(celldesignation.getCellType()==1)
{
    Designation =celldesignation.getStringCellValue();
}
    
    
    
    String dd="";
    String mm="";
    String yyyy="";
    
    
    
    XSSFCell celldd= worksheet.getRow(4).getCell((short) 23);
    XSSFCell cellmm= worksheet.getRow(4).getCell((short) 24);
    XSSFCell cellyyyy= worksheet.getRow(4).getCell((short) 25);
    
    dd=celldd.getRawValue();
    mm=cellmm.getRawValue();
    yyyy=cellyyyy.getRawValue();
    
    datefromfile=yyyy+"-"+mm+"-"+dd;   
                
   
    
    
     
     
XSSFCell cellversion= worksheet.getRow(1).getCell((short) 0);

if(cellversion.getCellType()==0){
    //numeric
    versionno =""+(int)cellversion.getNumericCellValue();
}
else if(cellversion.getCellType()==1)
{
    versionno =cellversion.getStringCellValue();
}

  
   
        
    if(versionno.equals("ADF V 5.0.0") ){
        
        
        //_________submitted by and Designation___________
      XSSFCell cellsubmitter=worksheet.getRow(35).getCell((short)1);
    
    if(cellsubmitter.getCellType()==0)
    {
    //numeric
    submitted_by =""+(int)cellsubmitter.getNumericCellValue();
    }
else if(cellsubmitter.getCellType()==1)
    {
    submitted_by =cellsubmitter.getStringCellValue();
    }
    
        
        while(row<=33){
            
            rowsngapi++;
        session.setAttribute("covidpos", "<b>"+rowsngapi+"/"+rowCount+"</b>");
        session.setAttribute("covidpos_count", (rowsngapi*100)/rowCount);
        
            try {
               // System.out.println(" row number "+row);
                XSSFRow rowi = worksheet.getRow(row);
                if( rowi==null )
                {
                    
                    break;
                }
                
                
                if( row>=9 && row<=33 ) {
                    
                    
// _______________________________________________________________


//elements

//indicator

XSSFCell indiccell = rowi.getCell((short)27);

if(indiccell.getCellType()==0)
{
    //numeric
    indicatorid =""+(int)indiccell.getNumericCellValue();
}
else if(indiccell.getCellType()==1)
{
    indicatorid =indiccell.getStringCellValue();
}
else 
{
indicatorid =indiccell.getRawValue();
}

if(!"A,B,C,D,E,F,G,H".contains(indicatorid) && !(indicatorid.equals("17") && delivery_point.equals("2")))
 {
                
    /*<1 */
    
    XSSFCell der_1fcell  = rowi.getCell((short) 2);
   
    
    switch (der_1fcell.getCellType())
{
   
    case 1:
        der_1f =der_1fcell.getStringCellValue();
        break;
    default:
     der_1f =der_1fcell.getRawValue();
     break;
}
     if(der_1f==null){der_1f="0";}
     
       der_1f=der_1f.replaceAll("[^\\d.]", ""); 
    if(der_1f.trim().equals(""))   { der_1f="0"; }    
   
    
    XSSFCell der_1mcell  = rowi.getCell((short) 3);
    switch (der_1mcell.getCellType())
{
   
    case 1:
        der_1m =der_1mcell.getStringCellValue();
        break;
    default:
     der_1m =der_1mcell.getRawValue();
     break;
}
    if(der_1m==null){der_1m="0";}
      // System.out.println(" Raw "+der_1m); 
       der_1m=der_1m.replaceAll("[^\\d.]", ""); 
    if(der_1m.trim().equals(""))   { der_1m="0"; }   
 
  
    
    /* 1-4 */
    XSSFCell der_4fcell  = rowi.getCell((short) 4);
   switch (der_4fcell.getCellType())
{
   
    case 1:
        der_4f =der_4fcell.getStringCellValue();
        break;
    default:
     der_4f =der_4fcell.getRawValue();
     break;
}
       if(der_4f==null){der_4f="0";}
       der_4f=der_4f.replaceAll("[^\\d.]", ""); 
    if(der_4f.trim().equals(""))   { der_4f="0"; }  
    
    XSSFCell der_4mcell  = rowi.getCell((short) 5);
      switch (der_4mcell.getCellType())
{
   
    case 1:
        der_4m =der_4mcell.getStringCellValue();
        break;
    default:
     der_4m =der_4mcell.getRawValue();
     break;
}
        if(der_4m==null){der_4m="0";}
       der_4m=der_4m.replaceAll("[^\\d.]", ""); 
    if(der_4m.trim().equals(""))   { der_4m="0"; }  
    
  
      /* 5-9 */
    XSSFCell der_9fcell  = rowi.getCell((short) 6);
  switch (der_9fcell.getCellType())
{
   
    case 1:
        der_9f =der_9fcell.getStringCellValue();
        break;
    default:
     der_9f =der_9fcell.getRawValue();
     break;
}
      if(der_9f==null){der_9f="0";}
       der_9f=der_9f.replaceAll("[^\\d.]", ""); 
    if(der_9f.trim().equals(""))   { der_9f="0"; }  
    
    XSSFCell der_9mcell  = rowi.getCell((short) 7);
   switch (der_9mcell.getCellType())
{
   
    case 1:
        der_9m =der_9mcell.getStringCellValue();
        break;
    default:
     der_9m =der_9mcell.getRawValue();
     break;
}
       if(der_9m==null){der_9m="0";}
       der_9m=der_9m.replaceAll("[^\\d.]", ""); 
    if(der_9m.trim().equals(""))   { der_9m="0"; }  
    
    
  /* 10-14 */
    XSSFCell der_14fcell  = rowi.getCell((short) 8);
   switch (der_14fcell.getCellType())
{
   
    case 1:
        der_14f =der_14fcell.getStringCellValue();
        break;
    default:
     der_14f =der_14fcell.getRawValue();
     break;
}
       if(der_14f==null){der_14f="0";}
       der_14f=der_14f.replaceAll("[^\\d.]", ""); 
    if(der_14f.trim().equals(""))   { der_14f="0"; }  
    
    XSSFCell der_14mcell  = rowi.getCell((short) 9);
   switch (der_14mcell.getCellType())
{
   
    case 1:
        der_14m =der_14mcell.getStringCellValue();
        break;
    default:
     der_14m =der_14mcell.getRawValue();
     break;
}
       if(der_14m==null){der_14m="0";}
       der_14m=der_14m.replaceAll("[^\\d.]", ""); 
    if(der_14m.trim().equals(""))   { der_14m="0"; }     
   
    
/* 15-19 */
    XSSFCell der_19fcell  = rowi.getCell((short) 10);
  switch (der_19fcell.getCellType())
{
   
    case 1:
        der_19f =der_19fcell.getStringCellValue();
        break;
    default:
     der_19f =der_19fcell.getRawValue();
     break;
}
       if(der_19f==null){der_19f="0";}
       der_19f=der_19f.replaceAll("[^\\d.]", ""); 
    if(der_19f.trim().equals(""))   { der_19f="0"; }  
    
    XSSFCell der_19mcell  = rowi.getCell((short) 11);
   switch (der_19mcell.getCellType())
{
   
    case 1:
        der_19m =der_19mcell.getStringCellValue();
        break;
    default:
     der_19m =der_19mcell.getRawValue();
     break;
}
       if(der_19m==null){der_19m="0";}
       der_19m=der_19m.replaceAll("[^\\d.]", ""); 
    if(der_19m.trim().equals(""))   { der_19m="0"; }       
 
    
    
/* 20-24 */
    XSSFCell der_24fcell  = rowi.getCell((short) 12);
   switch (der_24fcell.getCellType())
{
   
    case 1:
        der_24f =der_24fcell.getStringCellValue();
        break;
    default:
     der_24f =der_24fcell.getRawValue();
     break;
}
       if(der_24f==null){der_24f="0";}
       der_24f=der_24f.replaceAll("[^\\d.]", ""); 
    if(der_24f.trim().equals(""))   { der_24f="0"; }  
    
    XSSFCell der_24mcell  = rowi.getCell((short) 13);
   switch (der_24mcell.getCellType())
{
   
    case 1:
        der_24m =der_24mcell.getStringCellValue();
        break;
    default:
     der_24m =der_24mcell.getRawValue();
     break;
}
       if(der_24m==null){der_24m="0";}
       der_24m=der_24m.replaceAll("[^\\d.]", ""); 
    if(der_24m.trim().equals(""))   { der_24m="0"; }   
    
 /* 25-29 */
    XSSFCell der_29fcell  = rowi.getCell((short) 14);
   switch (der_29fcell.getCellType())
{
   
    case 1:
        der_29f =der_29fcell.getStringCellValue();
        break;
    default:
     der_29f =der_29fcell.getRawValue();
     break;
}
       if(der_29f==null){der_29f="0";}
       der_29f=der_29f.replaceAll("[^\\d.]", ""); 
    if(der_29f.trim().equals(""))   { der_29f="0"; }  
    
    XSSFCell der_29mcell  = rowi.getCell((short) 15);
   switch (der_29mcell.getCellType())
{
   
    case 1:
        der_29m =der_29mcell.getStringCellValue();
        break;
    default:
     der_29m =der_29mcell.getRawValue();
     break;
}
       if(der_29m==null){der_29m="0";}
       der_29m=der_29m.replaceAll("[^\\d.]", ""); 
    if(der_29m.trim().equals(""))   { der_29m="0"; }       
    
   
  /* 30-34 */
    XSSFCell der_34fcell  = rowi.getCell((short) 16);
  switch (der_34fcell.getCellType())
{
   
    case 1:
        der_34f =der_34fcell.getStringCellValue();
        break;
    default:
     der_34f =der_34fcell.getRawValue();
     break;
}
       if(der_34f==null){der_34f="0";}
       der_34f=der_34f.replaceAll("[^\\d.]", ""); 
    if(der_34f.trim().equals(""))   { der_34f="0"; }  
    
    XSSFCell der_34mcell  = rowi.getCell((short) 17);
  switch (der_34mcell.getCellType())
{
   
    case 1:
        der_34m =der_34mcell.getStringCellValue();
        break;
    default:
     der_34m =der_34mcell.getRawValue();
     break;
}
       if(der_34m==null){der_34m="0";}
       der_34m=der_34m.replaceAll("[^\\d.]", ""); 
    if(der_34m.trim().equals(""))   { der_34m="0"; }    
   
    
/* 35-39 */
    XSSFCell der_39fcell  = rowi.getCell((short) 18);
   switch (der_39fcell.getCellType())
{
   
    case 1:
        der_39f =der_39fcell.getStringCellValue();
        break;
    default:
     der_39f =der_39fcell.getRawValue();
     break;
}
       if(der_39f==null){der_39f="0";}
       der_39f=der_39f.replaceAll("[^\\d.]", ""); 
    if(der_39f.trim().equals(""))   { der_39f="0"; }  
    
    XSSFCell der_39mcell  = rowi.getCell((short) 19);
   switch (der_39mcell.getCellType())
{
   
    case 1:
        der_39m =der_39mcell.getStringCellValue();
        break;
    default:
     der_39m =der_39mcell.getRawValue();
     break;
}
       if(der_39m==null){der_39m="0";}
       der_39m=der_39m.replaceAll("[^\\d.]", ""); 
    if(der_39m.trim().equals(""))   { der_39m="0"; }     
    
    
    
  
  /* 40-44 */
    XSSFCell der_44fcell  = rowi.getCell((short) 20);
   switch (der_44fcell.getCellType())
{
   
    case 1:
        der_44f =der_44fcell.getStringCellValue();
        break;
    default:
     der_44f =der_44fcell.getRawValue();
     break;
}
       if(der_44f==null){der_44f="0";}
       der_44f=der_44f.replaceAll("[^\\d.]", ""); 
    if(der_44f.trim().equals(""))   { der_44f="0"; }  
    
    XSSFCell der_44mcell  = rowi.getCell((short) 21);
  switch (der_44mcell.getCellType())
{
   
    case 1:
        der_44m =der_44mcell.getStringCellValue();
        break;
    default:
     der_44m =der_44mcell.getRawValue();
     break;
}
      if(der_44m==null){der_44m="0";}
       der_44m=der_44m.replaceAll("[^\\d.]", ""); 
    if(der_44m.trim().equals(""))   { der_44m="0"; }    
   
    
/* 45-49 */
    XSSFCell der_49fcell  = rowi.getCell((short) 22);
  switch (der_49fcell.getCellType())
{
   
    case 1:
        der_49f =der_49fcell.getStringCellValue();
        break;
    default:
     der_49f =der_49fcell.getRawValue();
     break;
}
       if(der_49f==null){der_49f="0";}
       der_49f=der_49f.replaceAll("[^\\d.]", ""); 
    if(der_49f.trim().equals(""))   { der_49f="0"; }  
    
    XSSFCell der_49mcell  = rowi.getCell((short) 23);
   switch (der_49mcell.getCellType())
{
   
    case 1:
        der_49m =der_49mcell.getStringCellValue();
        break;
    default:
     der_49m =der_49mcell.getRawValue();
     break;
}
       if(der_49m==null){der_49m="0";}
       der_49m=der_49m.replaceAll("[^\\d.]", ""); 
    if(der_49m.trim().equals(""))   { der_49m="0"; }      
  
    
    
/* 50 */
  XSSFCell der_50fcell  = rowi.getCell((short) 24);
   switch (der_50fcell.getCellType())
{
   
    case 1:
        der_50f =der_50fcell.getStringCellValue();
        break;
    default:
     der_50f =der_50fcell.getRawValue();
     break;
}
       if(der_50f==null){der_50f="0";}
       der_50f=der_50f.replaceAll("[^\\d.]", ""); 
    if(der_50f.trim().equals(""))   { der_50f="0"; }    
  
    
  XSSFCell der_50mcell  = rowi.getCell((short) 25);
   switch (der_50mcell.getCellType())
{
   
    case 1:
        der_50m =der_50mcell.getStringCellValue();
        break;
    default:
     der_50m =der_50mcell.getRawValue();
     break;
}
       if(der_50m==null){der_50m="0";}
       der_50m=der_50m.replaceAll("[^\\d.]", ""); 
    if(der_50m.trim().equals(""))   { der_50m="0"; }      
  
    
 
    try
    {

//total=""+(new Integer(der_1f)+new Integer(der_1m)+new Integer(der_4f)+new Integer(der_4m)+new Integer(der_9f)+new Integer(der_9m)+new Integer(der_14f)+new Integer(der_14m)+new Integer(der_19f)+new Integer(der_19m)+new Integer(der_24f)+new Integer(der_24m)+new Integer(der_29f)+new Integer(der_29m)+new Integer(der_34f)+new Integer(der_34m)+new Integer(der_39f)+new Integer(der_39m)+new Integer(der_44f)+new Integer(der_44m)+new Integer(der_49f)+new Integer(der_49m)+new Integer(der_50f)+new Integer(der_50m));
//total=;


  XSSFCell der_tcell  = rowi.getCell((short) 26);
   switch (der_tcell.getCellType())
{
   
    case 1:
        total =der_tcell.getStringCellValue();
        break;
    default:
     total =der_tcell.getRawValue();
     break;
}
       if(total==null){total="0";}
       total=total.replaceAll("[^\\d.]", ""); 
    if(total.trim().equals(""))   { total="0"; }  



f14=""+(new Integer(der_1f)+new Integer(der_4f)+new Integer(der_9f)+new Integer(der_14f));
m14=""+(new Integer(der_1m)+new Integer(der_4m)+new Integer(der_9m)+new Integer(der_14m));
f24=""+(new Integer(der_19f)+new Integer(der_24f));
m24=""+(new Integer(der_19m)+new Integer(der_24m));
f25=""+(new Integer(der_29f)+new Integer(der_34f)+new Integer(der_39f)+new Integer(der_44f)+new Integer(der_49f)+new Integer(der_50f));
m25=""+(new Integer(der_29m)+new Integer(der_34m)+new Integer(der_39m)+new Integer(der_44m)+new Integer(der_49m)+new Integer(der_50m));

    } catch (NumberFormatException e) 
    {
       // System.out.println("Number Format Exception"+e);
    }
if(!total.equals(""))
{
if(new Integer(total)>0){ hasdata="yes"; }
}




                
                if(reportingmonth.length()==1){  reportingmonth="0"+reportingmonth; }
                
                yearmonth=weekend.replace("-", "").substring(0,6);
                
                
                //================================continue from here========================================
                
                //get indicator id from list of indicators
                //create an id consisting of year_month_mflcode_indicator
                
                
                
//                String getindicator="SELECT id,indicator FROM pmtct_art_cohort.pns_indicators where id ='"+indicator+"'";
//                //dbConn conn= new dbConn();
//                //System.out.println(" Qry "+getindicator);
//                conn.rs=conn.state.executeQuery(getindicator);
//                while(conn.rs.next())
//                {
//                    
//                    indicatorid=conn.rs.getString("id");
//                    
//                }
                
                
                
                 id=weekend.replace("-","_")+"_"+mflcode+"_"+indicatorid+"_"+delivery_point;
                
                
                
               // System.out.println("test__"+id+"  "+indicator);
                
                
                
                
                facilityID="";
                checker=0;
                
                //
               // String get_id="SELECT mflcode FROM "+dbname+" WHERE id like ? ";
                //conn.pst1=conn.connect.prepareStatement(get_id);
               //conn.pst1.setString(1,"%"+id+"%");
                
                //conn.rs=conn.pst1.executeQuery();
               // if(conn.rs.next()==true)
               // {
                   // facilityID=conn.rs.getString(1);
                    
                 //   checker=1;
                    
                //}
                if(!mflcode.equals("") && !delivery_point.equals("")) {
//                        DISTRICT FOUND ADD THE HF TO THE SYSTEM.........................

// if(new Integer(yearmonth)>=201710 && new Integer(yearmonth)<=201712 ){

if(1==1){
    //System.out.println("** Inserting data ");
    //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
    
    String inserter="Replace INTO "+dbname+" (id, date, delivery_point, year, month, indicator_id, mflcode, value, f14, m14, f24, m24, f25, m25, datekey,  datefromfile, submitted_by, Designation,der_1f, der_1m, der_4f, der_4m, der_9f, der_9m, der_14f, der_14m, der_19f, der_19m, der_24f, der_24m, der_29f, der_29m, der_34f, der_34m, der_39f, der_39m, der_44f, der_44m, der_49f, der_49m, der_50f, der_50m) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    conn.pst=conn.connect.prepareStatement(inserter);
    conn.pst.setString(1,id);
    conn.pst.setString(2,weekend);
    conn.pst.setString(3,delivery_point);
    conn.pst.setString(4,reportingyear);
    conn.pst.setString(5,reportingmonth);
    conn.pst.setString(6,indicatorid);
    conn.pst.setString(7,mflcode);
    conn.pst.setString(8,total);
    conn.pst.setString(9,f14);
    conn.pst.setString(10,m14);
    conn.pst.setString(11,f24);
    conn.pst.setString(12,m24);
    conn.pst.setString(13,f25);
    conn.pst.setString(14,m25);
    conn.pst.setString(15,weekend.replace("-", ""));
    conn.pst.setString(16,datefromfile);
    conn.pst.setString(17,submitted_by);
    conn.pst.setString(18,Designation);
    conn.pst.setString(19,der_1f);
conn.pst.setString(20,der_1m);
conn.pst.setString(21,der_4f);
conn.pst.setString(22,der_4m);
conn.pst.setString(23,der_9f);
conn.pst.setString(24,der_9m);
conn.pst.setString(25,der_14f);
conn.pst.setString(26,der_14m);
conn.pst.setString(27,der_19f);
conn.pst.setString(28,der_19m);
conn.pst.setString(29,der_24f);
conn.pst.setString(30,der_24m);
conn.pst.setString(31,der_29f);
conn.pst.setString(32,der_29m);
conn.pst.setString(33,der_34f);
conn.pst.setString(34,der_34m);
conn.pst.setString(35,der_39f);
conn.pst.setString(36,der_39m);
conn.pst.setString(37,der_44f);
conn.pst.setString(38,der_44m);
conn.pst.setString(39,der_49f);
conn.pst.setString(40,der_49m);
conn.pst.setString(41,der_50f);
conn.pst.setString(42,der_50m);

    
    
    
    
    
    //conn.pst.setString(38,versionno);
    
    //System.out.println(""+conn.pst);
    
    conn.pst.executeUpdate();
   
    
    //added++;
    
    if(!insertedfacil.contains(facilityName))
    {
        insertedfacil+=facilityName.replace("'", "")+",";
        added++;
    }
    
}
//for now do replace without deleting the facility name
if(1==2){

if(1==1) {
    
    // System.out.println("**Updating data ");
    //id,SubPartnerID,Year,Quarter,Mflcode,Sex ,age,agebracket,SubPartnerNom,dateoftesting,patientccc,batchno,supporttype
    String inserter=" UPDATE "+dbname+" SET date=? , delivery_point=? , year=? , month=? , indicator_id=? , mflcode=? , value=? , f14=? , m14=? , f24=? , m24=? , f25=? , m25=? , datekey=? ,  datefromfile=? , submitted_by=? , Designation=?,der_1f=?, der_1m=?, der_4f=?, der_4m=?, der_9f=?, der_9m=?, der_14f=?, der_14m=?, der_19f=?, der_19m=?, der_24f=?, der_24m=?, der_29f=?, der_29m=?, der_34f=?, der_34m=?, der_39f=?, der_39m=?, der_44f=?, der_44m=?, der_49f=?, der_49m=?, der_50f=?, der_50m=?"
            + " WHERE id=?";
//
conn.pst=conn.connect.prepareStatement(inserter);

    
    conn.pst.setString(1,weekend);
    conn.pst.setString(2,delivery_point);
    conn.pst.setString(3,reportingyear);
    conn.pst.setString(4,reportingmonth);
    conn.pst.setString(5,indicatorid);
    conn.pst.setString(6,mflcode);
    conn.pst.setString(7,total);
    conn.pst.setString(8,f14);
    conn.pst.setString(9,m14);
    conn.pst.setString(10,f24);
    conn.pst.setString(11,m24);
    conn.pst.setString(12,f25);
    conn.pst.setString(13,m25);
    conn.pst.setString(14,weekend.replace("-", ""));
    conn.pst.setString(15,datefromfile);
    conn.pst.setString(16,submitted_by);
    conn.pst.setString(17,Designation);
    
    conn.pst.setString(18,der_1f);
conn.pst.setString(19,der_1m);
conn.pst.setString(20,der_4f);
conn.pst.setString(21,der_4m);
conn.pst.setString(22,der_9f);
conn.pst.setString(23,der_9m);
conn.pst.setString(24,der_14f);
conn.pst.setString(25,der_14m);
conn.pst.setString(26,der_19f);
conn.pst.setString(27,der_19m);
conn.pst.setString(28,der_24f);
conn.pst.setString(29,der_24m);
conn.pst.setString(30,der_29f);
conn.pst.setString(31,der_29m);
conn.pst.setString(32,der_34f);
conn.pst.setString(33,der_34m);
conn.pst.setString(34,der_39f);
conn.pst.setString(35,der_39m);
conn.pst.setString(36,der_44f);
conn.pst.setString(37,der_44m);
conn.pst.setString(38,der_49f);
conn.pst.setString(39,der_49m);
conn.pst.setString(40,der_50f);
conn.pst.setString(41,der_50m);

    
    conn.pst.setString(42,id);
    
   //System.out.println(""+conn.pst);

conn.pst.executeUpdate();


if(!updatedfacil.contains(facilityName))
{
    updatedfacil+=facilityName.replace("'", "")+",";
    updated++;
}

}
                }//end of commented code
//}
                }
                
                 else if(delivery_point.equals("") && hasdata.equals("yes"))
                 {
                    
                    NocontactsFacility+="facility  : "+facilityName+" mfl code : "+mflcode+" in sheet "+sheetname+" has no service delivery indicated <br>";
                    
                   // System.out.println(sheetname+ " has no service delivery point but has data.");
                    
                    if(!missingcontwithdatafacil.contains(facilityName+"_"+sheetname))
                    { 
                        
                        missingcontwithdatafacil+=facilityName+"_"+sheetname+"("+fileNameCopy+"),";
                        nocontactsmodalitysites++;
                        
                    }
                    
                }
                
                else if(mflcode.equals("") && !total.equals("0")){
                    
                    missingFacility+="File  : "+fileNameCopy+"("+sheetname+")"+" has no mflcode selected yet has data<br>";
                   // System.out.println(sheetname+ " has no mflcode but has data.");
                    
                    if(!missingwithdatafacil.contains(sheetname))
                    { 
                        
                        missingwithdatafacil+=sheetname+"("+fileNameCopy+"),";
                        nomflcodesites++;
                        
                    }
                    
                }
                
                else if(mflcode.equals("") && total.equals("0"))
                {
                    
                    // missingFacility+="facility  : "+sheetname+" mfl code : "+mflcode+" not in system "+row+"<br>";
                   // System.out.println(sheetname+ " blank sheet");
                }
                
           else {
                    
                missing++;
              //missing facilities
missingFacility+="facility  : "+sheetname+" mfl code : "+mflcode+" not in system "+row+"<br>";
//System.out.println(sheetname+ " uncaptured scenario");
                }
                
                
                
            }}
                
                
                
            } //end of iterator
            catch (SQLException ex) {
                Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
            }
            row++;
            //System.out.println(" eeend of while looop");
            
        }
        
    }//end of correct version  

    else {
        
        sessionText="<h2><font color=\"red\">Note: Data was uploaded using Wrong Templete version. Click here to <a class=\"btn btn-success\" href=\"pns/covid19_impact_form.xlsx\">download correct template</a></font><h2>";
        
         }
    
    }//end of skipping printable sheet
 
  else {
        
        sessionText="<h2><font color=\"red\">Note: Data was uploaded using Wrong Template version. Click here to <a class=\"btn btn-success\" href=\"pns/covid19_impact_form.xlsx\">download template for Covid 19 indiactors only</a></font><h2>";
        
         }
}
}//end of worksheets loop
rowsngapi=34;
               } catch (InvalidFormatException ex) {
                   Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
               }

     }
    
     
     if(nomflcodesites>0){
          if(nomflcodesites==1)
          {
              
      unimporteddata="<br/> <font color=\"red\"> Data for <b>"+nomflcodesites+"</b> Sheet <b>("+missingwithdatafacil+")</b> skipped because it has no MFLCode</font>";
          
          }
      
       else
      {
      
      
      unimporteddata="<br/> <font color=\"red\"> Data for <b>"+nomflcodesites+"</b> Sheets  <i>("+missingwithdatafacil+")</i>  skipped because no MflCode </font>";
      
     
      }
      } 
     if(nocontactsmodalitysites>0){
          if(nocontactsmodalitysites==1){
      unimporteddata="<br/><font color=\"red\"> Data for <b>"+nocontactsmodalitysites+"</b> Sheet <b>("+missingcontwithdatafacil+")</b> skipped because it has non Contacts from indicated in Cell B1 </font>";
          }
      
       else
      {
      
      
      unimporteddata="<br/><font color=\"red\"> Data for <b>"+nomflcodesites+"</b> Sheets  <i>("+missingcontwithdatafacil+")</i>  skipped because it has non Contacts from indicated in Cell B1</font>";
      
     
      }
      } 
    
     
      if(updated>0)
      {
       updateddata=" <br/> <font color=\"green\">  Data for <b>"+updated+" </b> sites <i>("+updatedfacil+")</i> updated </font><br> ";
      }
      
      
      if(added>0)
      {
      newdaata=" <font color=\"green\"> Data for <b>"+added+ " </b> sites <i>("+insertedfacil+")</i> imported </font> ";
      }
      
      
     
     
     
 }//end of for loop
     
     if(conn.rs!=null){try {
         conn.rs.close();
          } catch (SQLException ex) 
          {
           Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.st!=null){
      try {
          conn.st.close();
          } catch (SQLException ex) 
          {
              Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.pst!=null){try {
          conn.pst.close();
          } catch (SQLException ex) 
          {
              Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      
  
            if(conn.connect!=null){try 
            {
                conn.connect.close();
            } catch (SQLException ex) {
          Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
      }
}
     
      
       if(conn.connect!=null){
           try {
          conn.connect.close();
               } catch (SQLException ex) {
              Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      
      
      
     // pullHTS hts= new pullHTS();
    // hts.hts_pns(yearmonth, yearmonth, "");
      
      
    sessionText+="<br/>"+newdaata+"   "+updateddata+" "+unimporteddata+" ";    
   // System.out.println(""+sessionText);
    session.setAttribute("uploadedart","<ul><b>Last Data Upload Results:</b></ul><br/>  File(s) : <b>"+fileNameCopy+".</b> "+ sessionText);
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
    //    System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
      
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                file_name = token.substring(token.indexOf("=") + 2, token.length()-1);
              break;  
            }
            
        }
        // System.out.println("content-disposition final : "+file_name);
        return file_name;
    }
    
    
    
    public  Workbook ReadExcel(String excelpath) throws IOException, InvalidFormatException {
        Workbook wb=null;
InputStream inputStream = null;
   try {
        inputStream = new FileInputStream(new File(excelpath));
         wb = WorkbookFactory.create(inputStream);
        int numberOfSheet = wb.getNumberOfSheets();

        for (int i = 0; i < numberOfSheet; i++) {
             Sheet sheet = wb.getSheetAt(i);
             //.... Customize your code here
             // To get sheet name, try -> sheet.getSheetName()
        }
   }  catch (FileNotFoundException ex) { 
          Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
      } 
   
   return wb;
}
    
    
}


