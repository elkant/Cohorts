/*
Notes: This raw data is for positive EID. The data doesnt have age and sex
Age and sex should be gotten from the eid tested raw data during the importing of the raw data positives into the eid_datim_output table.

 */

package imports;

import dashboards.pullHTS;
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


  public class importpnsv3 extends HttpServlet {
   
 
  
  String full_path="";
  String fileName="";
  String fileNameCopy="";
  int checker_dist,checker_hf;
 
  HttpSession session;
  private static final long serialVersionUID = 205242440643911308L;
  private static final String UPLOAD_DIR = "uploads";
  String nextpage="";
  String quarterName,facilityName,facilityID,id,missingFacility,NocontactsFacility;
    int nomflcodesites,nocontactsmodalitysites;      
 

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
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

String contacts_modality="";
String contacts_modality_id="";
String datefromfile="";


weekstart=request.getParameter("weekstart");
weekend=request.getParameter("weekend");

if(!weekstart.equals("")){
 session.setAttribute("weekstart",weekstart );
 session.setAttribute("weekend",weekend );
                         }


String updatedfacil="";
String insertedfacil="";
String missingwithdatafacil="";
String missingcontwithdatafacil="";
String unimporteddata="";
String updateddata="";
String newdaata="";

  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
  

String  pns_ukf="0";
String     pns_ukm="0";
String     pns_1f="0";
String     pns_1m="0";
String     pns_1="0";
String     pns_4f="0";
String     pns_4m="0";
String     pns_9f="0";
String     pns_9m="0";
String     pns_9="0";
String     pns_14f="0";
String     pns_14m="0";
String     pns_19f="0";
String     pns_19m="0";
String     pns_24f="0";
String     pns_24m="0";
String     pns_29f="0";
String     pns_29m="0";
String     pns_34f="0";
String     pns_34m="0";
String     pns_39f="0";
String     pns_39m="0";
String     pns_44f="0";
String     pns_44m="0";
String     pns_49f="0";
String     pns_49m="0";
String     pns_50f="0";
String     pns_50m="0";
String     pns_t="0";


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
         //System.out.println("___ hii sio ile file tunataka ");
         
         nextpage="importpns.jsp";
         sessionText="<font color=\"red\">Failed to load a .xls excel file. Please open the file, go to file> options > save as , then save as .xlsx </font>";
     }
            
            }
        //}
     
     if(!fileName.endsWith(".xlsx")){
       
         nextpage="importpns.jsp";
     }
     else {
         
               try {
                   full_path=fileSaveDir.getAbsolutePath()+"/"+fileName; //end of checking if excel file is valid
                  // System.out.println("the saved file directory is  :  "+full_path);
// GET DATA FROM THE EXCEL AND AND OUTPUT IT ON THE CONSOLE..................................
FileInputStream fileInputStream = new FileInputStream(full_path);
XSSFWorkbook workbook = (XSSFWorkbook) ReadExcel(full_path);
if(1==2){
    
    
    
    
    
    
    
    
}
int totalsheets=workbook.getNumberOfSheets();
DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
for(int a=0;a<totalsheets;a++){
    
    XSSFSheet worksheet = workbook.getSheetAt(a);
    
    String hasdata="no";
    
   // System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
    
    String sheetname =  workbook.getSheetName(a);
    
//_______

//if(1==1){
//skip pivot PNS
if(!sheetname.equals("Pivot PNS") && !sheetname.equals("do not delete1")  ){
    
    Iterator rowIterator = worksheet.rowIterator();
    
    
    
    int row=3,col=0;
    
    //static number of rows
   
    
    
    XSSFCell cellcontactsfrom= worksheet.getRow(0).getCell((short) 1);
    contacts_modality =cellcontactsfrom.getStringCellValue();
    
    if(contacts_modality.equals("PNS")){ contacts_modality="Other NP/KP Contacts"; }
    
    
    XSSFCell celluserdate= worksheet.getRow(0).getCell((short) 3);
    if(celluserdate.getCellType()==1){
    datefromfile =celluserdate.getStringCellValue();
    }
    else
    {
              try {
              datefromfile = dateformat.format(celluserdate.getDateCellValue()); 
                  }
                catch(Exception e)
                {
                 datefromfile=celluserdate.getRawValue();   
                }
    }
    
    
    
     String printval="";
     
     boolean isprinting=false;
     XSSFCell printingcell= worksheet.getRow(0).getCell((short) 25);
     
if(printingcell.getCellType()==0){
    //numeric
    printval =""+(int)printingcell.getNumericCellValue();
}
else if(printingcell.getCellType()==1){
    printval =printingcell.getStringCellValue();
}

  if(printval.equals("PNS Template V 4.0.0")){ isprinting=true;  }   
     
      //__________________________version row________________
    
     String versionno="";
     
     
     
 if(!isprinting){
     
XSSFCell cellversion= worksheet.getRow(0).getCell((short) 6);

if(cellversion.getCellType()==0){
    //numeric
    versionno =""+(int)cellversion.getNumericCellValue();
}
else if(cellversion.getCellType()==1)
{
    versionno =cellversion.getStringCellValue();
}







   // System.out.println(" Version no ni :"+versionno);
    
    
    int hasnegatives=0;
   
        
    if(versionno.equals("PNS Template V 4.0.0") ){
        
        while(row<=9){
            try {
               // System.out.println(" row number "+row);
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
if(row==3){
System.out.println("\n\nPNS Upload for:: "+facilityName+" \nDate:: "+weekend+"\n Filename::" +fileName+"\n");
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
else if(cellmfl.getCellType()==2){
    mflcode =cellmfl.getRawValue();
    mflcode =cellmfl.getRawValue();
}
else {
   // System.out.println("mflcode__"+mflcode);
    mflcode =cellmfl.getRawValue();
    
}
//System.out.println("Mfl code is "+mflcode);
//-----------year-----------------------
XSSFCell cellyear = worksheet.getRow(row).getCell((short) 4);

if(cellyear.getCellType()==0){
    //numeric
    reportingyear =""+(int)cellyear.getNumericCellValue();
}

else if(cellyear.getCellType()==1){
    reportingyear =cellyear.getStringCellValue();
}

reportingyear=weekend.substring(0,4);


//-----------month-----------------------
XSSFCell cellmonth = worksheet.getRow(row).getCell((short) 5);

if(cellmonth.getCellType()==0){
    //numeric
    reportingmonth =""+(int)cellmonth.getNumericCellValue();
}
else if(cellmonth.getCellType()==1){
    reportingmonth =cellmonth.getStringCellValue();
}

reportingmonth=weekend.substring(5,7);

//-----------weekstart-----------------------
XSSFCell cellws = worksheet.getRow(row).getCell((short) 6);

//System.out.println(" Date type is"+cellws.getCellType());
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

                    switch (pns_ukfcell.getCellType()) 
                    {
                        case 0:
                            //numeric
                            pns_ukf =""+(int)pns_ukfcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_ukf =pns_ukfcell.getStringCellValue();
                            break;
                        default:
                           // System.out.println("cell type "+pns_ukfcell.getCellType());
                            pns_ukf =""+(int)pns_ukfcell.getNumericCellValue();
                            break;
                    }

if(pns_ukf.trim().equals("")){pns_ukf="0";}
if(pns_ukf.contains("-")){hasnegatives++;}

//child_3m

XSSFCell pns_ukmcell = rowi.getCell((short) 10);

                    switch (pns_ukmcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_ukm =""+(int)pns_ukmcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_ukm =pns_ukmcell.getStringCellValue();
                            break;
                        default:
                            pns_ukm =""+(int)pns_ukmcell.getNumericCellValue();
                            break;
                    }

if(pns_ukm.trim().equals("")){pns_ukm="0";}
if(pns_ukm.contains("-")){hasnegatives++;}

//_____________________________1f___________________

XSSFCell pns_1fcell = rowi.getCell((short) 11);

                    switch (pns_1fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_1f =""+(int)pns_1fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_1f =pns_1fcell.getStringCellValue();
                            break;
                        default:
                            pns_1f =""+(int)pns_1fcell.getNumericCellValue();
                            break;
                    }

if(pns_1f.trim().equals("")){pns_1f="0";}
if(pns_1f.contains("-")){hasnegatives++;}

//we dont expect any under1

pns_1f="0";

//_____________________________1m___________________

XSSFCell pns_1mcell = rowi.getCell((short) 12);

                    switch (pns_1mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_1m =""+(int)pns_1mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_1m =pns_1mcell.getStringCellValue();
                            break;
                        default:
                            pns_1m =""+(int)pns_1mcell.getNumericCellValue();
                            break;
                    }

if(pns_1m.trim().equals("")){pns_1m="0";}
if(pns_1m.contains("-")){hasnegatives++;}

//we dont expect any under1 testing

pns_1m="0";

//_____________________________4f___________________

XSSFCell pns_4fcell = rowi.getCell((short) 13);

                    switch (pns_4fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_4f =""+(int)pns_4fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_4f =pns_4fcell.getStringCellValue();
                            break;
                        default:
                            pns_4f =""+(int)pns_4fcell.getNumericCellValue();
                            break;
                    }

if(pns_4f.trim().equals("")){pns_4f="0";}
if(pns_4f.contains("-")){hasnegatives++;}


//_____________________________4m___________________

XSSFCell pns_4mcell = rowi.getCell((short) 14);

                    switch (pns_4mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_4m =""+(int)pns_4mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_4m =pns_4mcell.getStringCellValue();
                            break;
                        default:
                            pns_4m =""+(int)pns_4mcell.getNumericCellValue();
                            break;
                    }

if(pns_4m.trim().equals("")){pns_4m="0";}
if(pns_4m.contains("-")){hasnegatives++;}


//_____________________________5-9f___________________

XSSFCell pns_9fcell = rowi.getCell((short) 15);

                    switch (pns_9fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_9f =""+(int)pns_9fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_9f =pns_9fcell.getStringCellValue();
                            break;
                        default:
                            pns_9f =""+(int)pns_9fcell.getNumericCellValue();
                            break;
                    }

if(pns_9f.trim().equals("")){pns_9f="0";}
if(pns_9f.contains("-")){hasnegatives++;}

//_____________________________5-9m___________________

XSSFCell pns_9mcell = rowi.getCell((short) 16);

                    switch (pns_9mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_9m =""+(int)pns_9mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_9m =pns_9mcell.getStringCellValue();
                            break;
                        default:
                            pns_9m =""+(int)pns_9mcell.getNumericCellValue();
                            break;
                    }

if(pns_9m.trim().equals("")){pns_9m="0";}
if(pns_9m.contains("-")){hasnegatives++;}


XSSFCell pns_14fcell = rowi.getCell((short) 17);

                    switch (pns_14fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_14f =""+(int)pns_14fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_14f =pns_14fcell.getStringCellValue();
                            break;
                        default:
                            pns_14f =""+(int)pns_14fcell.getNumericCellValue();
                            break;
                    }

if(pns_14f.trim().equals("")){pns_14f="0";}
if(pns_14f.contains("-")){hasnegatives++;}

//tl_7m

XSSFCell pns_14mcell = rowi.getCell((short) 18);

                    switch (pns_14mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_14m =""+(int)pns_14mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_14m =pns_14mcell.getStringCellValue();
                            break;
                        default:
                            pns_14m =""+(int)pns_14mcell.getNumericCellValue();
                            break;
                    }
if(pns_14m.trim().equals("")){pns_14m="0";}
if(pns_14m.contains("-")){hasnegatives++;}

//adult_9m

XSSFCell pns_19fcell = rowi.getCell((short) 19);

                    switch (pns_19fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_19f =""+(int)pns_19fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_19f =pns_19fcell.getStringCellValue();
                            break;
                        default:
                            pns_19f =""+(int)pns_19fcell.getNumericCellValue();
                            break;
                    }

if(pns_19f.contains("-")){hasnegatives++;}
XSSFCell pns_19mcell = rowi.getCell((short) 20);


                    switch (pns_19mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_19m =""+(int)pns_19mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_19m =pns_19mcell.getStringCellValue();
                            break;
                        default:
                            pns_19m =""+(int)pns_19mcell.getNumericCellValue();
                            break;
                    }
if(pns_19m.contains("-")){hasnegatives++;}
//pns_24f

XSSFCell pns_24fcell = rowi.getCell((short) 21);

                    switch (pns_24fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_24f =""+(int)pns_24fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_24f =pns_24fcell.getStringCellValue();
                            break;
                        default:
                            pns_24f =""+(int)pns_24fcell.getNumericCellValue();
                            break;
                    }
if(pns_24f.contains("-")){hasnegatives++;}

//tl_9m

XSSFCell pns_24mcell = rowi.getCell((short) 22);

                    switch (pns_24mcell.getCellType()) 
                    {
                        case 0:
                            //numeric
                            pns_24m =""+(int)pns_24mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_24m =pns_24mcell.getStringCellValue();
                            break;
                        default:
                            pns_24m =""+(int)pns_24mcell.getNumericCellValue();
                            break;
                    }

if(pns_19f.trim().equals("")){pns_19f="0";}
if(pns_24f.trim().equals("")){pns_24f="0";}
if(pns_24m.trim().equals("")){pns_24m="0";}
if(pns_24m.contains("-")){hasnegatives++;}

//adult_12m

XSSFCell pns_29fcell = rowi.getCell((short) 23);

                    switch (pns_29fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_29f =""+(int)pns_29fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_29f =pns_29fcell.getStringCellValue();
                            break;
                        default:
                            pns_29f =""+(int)pns_29fcell.getNumericCellValue();
                            break;
                    }

if(pns_29f.contains("-")){hasnegatives++;}
//pns_29m

XSSFCell pns_29mcell = rowi.getCell((short) 24);

                    switch (pns_29mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_29m =""+(int)pns_29mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_29m =pns_29mcell.getStringCellValue();
                            break;
                        default:
                            pns_29m =""+(int)pns_29mcell.getNumericCellValue();
                            break;
                    }

if(pns_29m.contains("-")){hasnegatives++;}
//----------------------34f--------

XSSFCell pns_34fcell = rowi.getCell((short) 25);

                    switch (pns_34fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_34f =""+(int)pns_34fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_34f =pns_34fcell.getStringCellValue();
                            break;
                        default:
                            pns_34f =""+(int)pns_34fcell.getNumericCellValue();
                            break;
                    }
                    if(pns_34f.contains("-")){hasnegatives++;}

//----------------------34m--------
XSSFCell pns_34mcell = rowi.getCell((short) 26);

                    switch (pns_34mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_34m =""+(int)pns_34mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_34m =pns_34mcell.getStringCellValue();
                            break;
                        default:
                            pns_34m =""+(int)pns_34mcell.getNumericCellValue();
                            break;
                    }

if(pns_34m.contains("-")){hasnegatives++;}

//----------------------39f----------------------------

XSSFCell pns_39fcell = rowi.getCell((short) 27);

                    switch (pns_39fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_39f =""+(int)pns_39fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_39f =pns_39fcell.getStringCellValue();
                            break;
                        default:
                            pns_39f =""+(int)pns_39fcell.getNumericCellValue();
                            break;
                    }

if(pns_39f.contains("-")){hasnegatives++;}

//----------------------39m---------------------------

XSSFCell pns_39mcell = rowi.getCell((short) 28);

                    switch (pns_39mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_39m =""+(int)pns_39mcell.getNumericCellValue();
                            break;
//---------------------------------------------------------
                        case 1:
                            pns_39m =pns_39mcell.getStringCellValue();
                            break;
                        default:
                            pns_39m =""+(int)pns_39mcell.getNumericCellValue();
                            break;
                    }

if(pns_39m.contains("-")){hasnegatives++;}


XSSFCell pns_44fcell = rowi.getCell((short) 29);

                    switch (pns_44fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_44f =""+(int)pns_44fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_44f =pns_44fcell.getStringCellValue();
                            break;
                        default:
                            pns_44f =""+(int)pns_44fcell.getNumericCellValue();
                            break;
                    }

if(pns_44f.contains("-")){hasnegatives++;}

//pns_44m

XSSFCell pns_44mcell = rowi.getCell((short) 30);

                    switch (pns_44mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_44m =""+(int)pns_44mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_44m =pns_44mcell.getStringCellValue();
                            break;
                        default:
                            pns_44m =""+(int)pns_44mcell.getNumericCellValue();
                            break;
                    }
if(pns_44m.contains("-")){hasnegatives++;}


//_______________________________pns_49f_______________________

XSSFCell pns_49fcell = rowi.getCell((short) 31);

                    switch (pns_49fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_49f =""+(int)pns_49fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_49f =pns_49fcell.getStringCellValue();
                            break;
                        default:
                            pns_49f =""+(int)pns_49fcell.getNumericCellValue();
                            break;
                    }
if(pns_49f.contains("-")){hasnegatives++;}


//pns_49m

XSSFCell pns_49mcell = rowi.getCell((short) 32);

                    switch (pns_49mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_49m =""+(int)pns_49mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_49m =pns_49mcell.getStringCellValue();
                            break;
                        default:
                            pns_49m =""+(int)pns_49mcell.getNumericCellValue();
                            break;
                    }
                    if(pns_49m.contains("-")){hasnegatives++;}

if(pns_29f.trim().equals("")){pns_29f="0";}
if(pns_29m.trim().equals("")){pns_29m="0";}
if(pns_49f.trim().equals("")){pns_49f="0";}

//pns_50f

XSSFCell pns_50fcell = rowi.getCell((short) 33);

                    switch (pns_50fcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_50f =""+(int)pns_50fcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_50f =pns_50fcell.getStringCellValue();
                            break;
                        default:
                            pns_50f =""+(int)pns_50fcell.getNumericCellValue();
                            break;
                    }
if(pns_50f.contains("-")){hasnegatives++;}
//pns_50m

XSSFCell pns_50mcell = rowi.getCell((short) 34);

                    switch (pns_50mcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_50m =""+(int)pns_50mcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_50m =pns_50mcell.getStringCellValue();
                            break;
                        default:
                            pns_50m =""+(int)pns_50mcell.getNumericCellValue();
                            break;
                    }
                    if(pns_50m.contains("-")){hasnegatives++;}

if(pns_49m.trim().equals("")){pns_49m="0";}
if(pns_50f.trim().equals("")){pns_50f="0";}
if(pns_50m.trim().equals("")){pns_50m="0";}

//adult_36m

XSSFCell pns_tcell = rowi.getCell((short) 35);

                    switch (pns_tcell.getCellType()) {
                        case 0:
                            //numeric
                            pns_t =""+(int)pns_tcell.getNumericCellValue();
                            break;
                        case 1:
                            pns_t =pns_tcell.getStringCellValue();
                            break;
                        default:
                            pns_t =""+(int)pns_tcell.getNumericCellValue();
                            break;
                    }

 if(pns_t.contains("-")){hasnegatives++;}
 
if(pns_t.trim().equals("")){pns_t="0";}

if(new Integer(pns_t)>0){hasdata="yes";}



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
                
                
                
                String id=reportingyear+"_"+reportingmonth+"_"+weekend.replace("-","_")+"_"+mflcode+"_"+indicatorid+"_"+contacts_modality;
                
                
                
               // System.out.println("test__"+id+"  "+indicator);
                
                
                
                
                facilityID="";
                checker=0;
                String oldid="";
                //
                String get_id="SELECT id FROM "+dbname+" WHERE weekend='"+weekend+"' and mflcode='"+mflcode+"' and indicator='"+indicatorid+"' and contacts_modality like ? ";
                conn.pst1=conn.connect.prepareStatement(get_id);
                conn.pst1.setString(1,"%"+contacts_modality+"%");
                
                conn.rs=conn.pst1.executeQuery();
                if(conn.rs.next()==true)
                {
                    oldid=conn.rs.getString(1);
                    
                    checker=1;
                    
                }
                if(!mflcode.equals("") && !mflcode.equals("Enter MFL Code") && !contacts_modality.equals("")) {
//                        DISTRICT FOUND ADD THE HF TO THE SYSTEM.........................

// if(new Integer(yearmonth)>=201710 && new Integer(yearmonth)<=201712 ){

if(checker==0){
    //System.out.println("** Inserting data ");
    //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
    
    String inserter="INSERT INTO "+dbname+" (id,mflcode,year,month,weekstart,weekend,indicator,pns_ukf,pns_ukm,pns_1f,pns_1m,pns_4f,pns_4m,pns_9f,pns_9m,pns_14f,pns_14m,pns_19f,pns_19m,pns_24f,pns_24m,pns_29f,pns_29m,pns_34f,pns_34m,pns_39f,pns_39m,pns_44f,pns_44m,pns_49f,pns_49m,pns_50f,pns_50m,pns_t,yearmonth,contacts_modality,datefromfile,templateversion) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
    conn.pst.setString(10,pns_1f);
    conn.pst.setString(11,pns_1m);
    conn.pst.setString(12,pns_4f);
    conn.pst.setString(13,pns_4m);
    conn.pst.setString(14,pns_9f);
    conn.pst.setString(15,pns_9m);
    conn.pst.setString(16,pns_14f);
    conn.pst.setString(17,pns_14m);
    conn.pst.setString(18,pns_19f);
    conn.pst.setString(19,pns_19m);
    conn.pst.setString(20,pns_24f);
    conn.pst.setString(21,pns_24m);
    conn.pst.setString(22,pns_29f);
    conn.pst.setString(23,pns_29m);
    conn.pst.setString(24,pns_34f);
    conn.pst.setString(25,pns_34m);
    conn.pst.setString(26,pns_39f);
    conn.pst.setString(27,pns_39m);
    conn.pst.setString(28,pns_44f);
    conn.pst.setString(29,pns_44m);
    conn.pst.setString(30,pns_49f);
    conn.pst.setString(31,pns_49m);
    conn.pst.setString(32,pns_50f);
    conn.pst.setString(33,pns_50m);
    conn.pst.setString(34,pns_t);
    conn.pst.setString(35,yearmonth);
    conn.pst.setString(36,contacts_modality);
    conn.pst.setString(37,datefromfile);
    conn.pst.setString(38,versionno);
    
    // System.out.println(""+conn.pst);
    
    conn.pst.executeUpdate();
   
    
    //added++;
    
    if(!insertedfacil.contains(facilityName))
    {
        insertedfacil+=facilityName.replace("'", "")+",";
        added++;
    }
    
}
else {
    
    // System.out.println("**Updating data ");
    //id,SubPartnerID,Year,Quarter,Mflcode,Sex ,age,agebracket,SubPartnerNom,dateoftesting,patientccc,batchno,supporttype
    String inserter=" UPDATE "+dbname+" SET id=?,mflcode=?,year=?,month=?,weekstart=?,weekend=?,indicator=?,pns_ukf=?,pns_ukm=?,pns_1f=?,pns_1m=?,pns_4f=?,pns_4m=?,pns_9f=?,pns_9m=?,pns_14f=?,pns_14m=?,pns_19f=?,pns_19m=?,pns_24f=?,pns_24m=?,pns_29f=?,pns_29m=?,pns_34f=?,pns_34m=?,pns_39f=?,pns_39m=?,pns_44f=?,pns_44m=?,pns_49f=?,pns_49m=?,pns_50f=?,pns_50m=?,pns_t=?,yearmonth=?,contacts_modality=?,datefromfile=?,templateversion=?"
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
conn.pst.setString(10,pns_1f);
conn.pst.setString(11,pns_1m);
conn.pst.setString(12,pns_4f);
conn.pst.setString(13,pns_4m);
conn.pst.setString(14,pns_9f);
conn.pst.setString(15,pns_9m);
conn.pst.setString(16,pns_14f);
conn.pst.setString(17,pns_14m);
conn.pst.setString(18,pns_19f);
conn.pst.setString(19,pns_19m);
conn.pst.setString(20,pns_24f);
conn.pst.setString(21,pns_24m);
conn.pst.setString(22,pns_29f);
conn.pst.setString(23,pns_29m);
conn.pst.setString(24,pns_34f);
conn.pst.setString(25,pns_34m);
conn.pst.setString(26,pns_39f);
conn.pst.setString(27,pns_39m);
conn.pst.setString(28,pns_44f);
conn.pst.setString(29,pns_44m);
conn.pst.setString(30,pns_49f);
conn.pst.setString(31,pns_49m);
conn.pst.setString(32,pns_50f);
conn.pst.setString(33,pns_50m);
conn.pst.setString(34,pns_t);
conn.pst.setString(35,yearmonth);
conn.pst.setString(36,contacts_modality);
conn.pst.setString(37,datefromfile);
conn.pst.setString(38,versionno);
conn.pst.setString(39,oldid);
   // System.out.println(""+conn.pst);

conn.pst.executeUpdate();


if(!updatedfacil.contains(facilityName))
{
    updatedfacil+=facilityName.replace("'", "")+",";
    updated++;
}
}

//}
                }
                
                 else if(contacts_modality.equals("") && hasdata.equals("yes")){
                    
                    NocontactsFacility+="facility  : "+facilityName+" mfl code : "+mflcode+" in sheet "+sheetname+" has no contacts modality indicated <br>";
                 //   System.out.println(sheetname+ " has no conacts but has data.");
                    
                    if(!missingcontwithdatafacil.contains(facilityName+"_"+sheetname))
                    { 
                        
                        missingcontwithdatafacil+=facilityName+"_"+sheetname+"("+fileNameCopy+"),";
                        nocontactsmodalitysites++;
                        
                    }
                    
                }
                
                else if(mflcode.equals("Enter MFL Code") && !pns_t.equals("0")){
                    
                    missingFacility+="facility  : "+sheetname+" mfl code : "+mflcode+" not in system "+row+"<br>";
                   // System.out.println(sheetname+ " has no mflcode but has data.");
                    
                    if(!missingwithdatafacil.contains(sheetname))
                    { 
                        
                        missingwithdatafacil+=sheetname+"("+fileNameCopy+"),";
                        nomflcodesites++;
                        
                    }
                    
                }
                
                else if(mflcode.equals("Enter MFL Code") && pns_t.equals("0"))
                {
                    
                    // missingFacility+="facility  : "+sheetname+" mfl code : "+mflcode+" not in system "+row+"<br>";
                  //  System.out.println(sheetname+ " has no data.");
                }
                
                else{
                    
                    missing++;
//                        missing facilities
missingFacility+="facility  : "+sheetname+" mfl code : "+mflcode+" not in system "+row+"<br>";
//System.out.println(sheetname+ " has no mflcode.");
                }
                
            } //end of iterator
            catch (SQLException ex) {
                Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
            }
            row++;
            //System.out.println(" eeend of while looop");
            
        }
        
    }//end of correct version  

    else {
        
        System.out.println("Veeersion ni_"+versionno+"_"+sheetname);
        if("Other KP or NP contacts,Pmtct_contacts,STF_contacts,TB_contacts,Discordant_Couple".contains(sheetname)){
        sessionText="<h2><font color=\"red\">Note: Data was uploaded using Wrong Templete version. Click here to <a class=\"btn btn-success\" href=\"pns/PNS_Daily_Template_20190514_V_4.xlsx\">download correct template</a></font><h2>";
        
        }
    }
    
    }//end of skipping printable sheet
}
}//end of worksheets loop
               } catch (InvalidFormatException ex) {
                   Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
               }

     }
    
     
     if(nomflcodesites>0){
          if(nomflcodesites==1){
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
    
     
      if(updated>0){
       updateddata=" <br/> <font color=\"green\">  Data for <b>"+updated+" </b> sites <i>("+updatedfacil+")</i> updated </font><br> ";
      }
      
      
      if(added>0){
      
      newdaata=" <font color=\"green\"> Data for <b>"+added+ " </b> sites  newly imported </font> ";
      }
      
      
     
     
     
 }//end of for loop
     
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
      
  
            if(conn.connect!=null){try {
                conn.connect.close();
      } catch (SQLException ex) {
          Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
      }
}
     
      
       if(conn.connect!=null){try {
          conn.connect.close();
          } catch (SQLException ex) {
              Logger.getLogger(importpns.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      
      
      
     // pullHTS hts= new pullHTS();
    // hts.hts_pns(yearmonth, yearmonth, "");
      
      
     sessionText+="<br/>"+newdaata+"   "+updateddata+" "+unimporteddata+" ";    
    // System.out.println(""+sessionText);
    session.setAttribute("uploadedpns","<ul><b>Last Data Upload Results:</b></ul><br/><br/><br/>  File(s) Name : <b>"+fileNameCopy+".</b> "+ sessionText);
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
       // System.out.println("content-disposition header= "+contentDisp);
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


