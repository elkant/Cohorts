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


  public class importder extends HttpServlet {
   
 
  
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
date 
delivery_point 
year 
month 
indicator_id 
mflcode 
value 
f14 
m14 
f24 
m24 
f25 
m25 
datekey
***/
     
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
String total="";

String submitted_by="";
String Designation="";


String yearmonth="";

String mflcode="";

 
    String serialnumber="";
    
     String dbname="der_rri.der_table_age";
  
   
     dbConn conn = new dbConn();
     
     nextpage="importderv1.jsp";
     
     String applicationPath = request.getServletContext().getRealPath("");
     String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
     session=request.getSession();
     
     File fileSaveDir = new File(uploadFilePath);
     if (!fileSaveDir.exists()) {
         fileSaveDir.mkdirs();
                                }
     System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
   
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
         System.out.println("___ hii sio ile file tunataka ");
         
         nextpage="importderv1.jsp";
         sessionText="<font color=\"red\">Failed to load a .xls excel file. Please open the file, go to file> options > save as , then save as .xlsx </font>";
     }
            
            }
        //}
     
     if(!fileName.endsWith(".xlsx")){
       
         nextpage="importderv1.jsp";
     }
     else {
         
               try {
                   full_path=fileSaveDir.getAbsolutePath()+"/"+fileName; //end of checking if excel file is valid
                   System.out.println("the saved file directory is  :  "+full_path);
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
    
    System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
    
    String sheetname =  workbook.getSheetName(a);
    
//_______

//if(1==1){
//skip pivot PNS
if(!sheetname.equals("data") && !sheetname.equals("SiteSetUp") && !sheetname.equals("SurgeSites")  ){
    
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
    System.out.println("mflcode__"+mflcode);
    mflcode =cellmfl.getRawValue();
    
}
System.out.println("Mfl code is "+mflcode);
    
    //    __________________Date Tested_____________________________
    

    XSSFCell cellcontactsfrom= worksheet.getRow(5).getCell((short)2);
    delivery_point =cellcontactsfrom.getStringCellValue();
    
         if(delivery_point.equals("CCC"))  { delivery_point="1";}
    else if(delivery_point.equals("PMTCT")){ delivery_point="2";}
    
    
    //_________submitted by and Designation___________
      XSSFCell cellsubmitter=worksheet.getRow(36).getCell((short)1);
    submitted_by =cellsubmitter.getStringCellValue(); 
    
      XSSFCell celldesignation=worksheet.getRow(36).getCell((short)2);
    Designation =celldesignation.getStringCellValue(); 
    
    
    
    String dd="";
    String mm="";
    String yyyy="";
    
    
    
    XSSFCell celldd= worksheet.getRow(4).getCell((short) 5);
    XSSFCell cellmm= worksheet.getRow(4).getCell((short) 6);
    XSSFCell cellyyyy= worksheet.getRow(4).getCell((short) 7);
    
    dd=celldd.getRawValue();
    mm=cellmm.getRawValue();
    yyyy=cellyyyy.getRawValue();
    
    datefromfile=yyyy+"-"+mm+"-"+dd;   
                
    
    
    
    
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

  if(printval.equals("ADF V 1.0.0")){ isprinting=true;  }   
     
      //__________________________version row________________
    
     String versionno="";
     
    System.out.println(""+printval); 
     
 if(isprinting){
     
XSSFCell cellversion= worksheet.getRow(1).getCell((short) 0);

if(cellversion.getCellType()==0){
    //numeric
    versionno =""+(int)cellversion.getNumericCellValue();
}
else if(cellversion.getCellType()==1)
{
    versionno =cellversion.getStringCellValue();
}







    System.out.println(" Version no ni :"+versionno);
    
    
    
   
        
    if(versionno.equals("ADF V 1.0.0") ){
        
        while(row<=36){
            try {
                System.out.println(" row number "+row);
                XSSFRow rowi = worksheet.getRow(row);
                if( rowi==null )
                {
                    
                    break;
                }
                
                
                if( row>=9 && row<=34 ) {
                    
                    
// _______________________________________________________________


//elements

//indicator

XSSFCell indiccell = rowi.getCell((short)0);

if(indiccell.getCellType()==0)
{
    //numeric
    indicatorid =""+(int)indiccell.getNumericCellValue();
}
else if(indiccell.getCellType()==1)
{
    indicatorid =indiccell.getStringCellValue();
}
else {
indicatorid =indiccell.getRawValue();
}

if(!"A,B,C,D".contains(indicatorid))
 {
                
    //adult_3m
    
    XSSFCell f14cell  = rowi.getCell((short) 2);
    
    switch (f14cell.getCellType())
    {
        case 0:
            //numeric
            f14 =""+(int)f14cell.getNumericCellValue();
            break;
        case 1:
            f14 =f14cell.getStringCellValue();
            break;
        default:
            System.out.println("cell type "+f14cell.getCellType());
            f14 =""+(int)f14cell.getNumericCellValue();
            break;
    }
    
    if(f14.trim().equals("")){f14="0";}
    
    
//child_3m

XSSFCell m14cell = rowi.getCell((short) 3);

switch (m14cell.getCellType())
{
    case 0:
        //numeric
        m14 =""+(int)m14cell.getNumericCellValue();
        break;
    case 1:
        m14 =m14cell.getStringCellValue();
        break;
    default:
        m14 =""+(int)m14cell.getNumericCellValue();
        break;
}

if(m14.trim().equals("")){m14="0";}

//_____________________________1f___________________

XSSFCell f24cell = rowi.getCell((short) 4);

switch (f24cell.getCellType())
{
    case 0:
        //numeric
        f24 =""+(int)f24cell.getNumericCellValue();
        break;
    case 1:
        f24 =f24cell.getStringCellValue();
        break;
    default:
        f24 =""+(int)f24cell.getNumericCellValue();
        break;
}

if(f24.trim().equals("")){f24="0";}



//_____________________________1m___________________

XSSFCell m24cell = rowi.getCell((short) 5);

switch (m24cell.getCellType())
{
    case 0:
        //numeric
        m24=""+(int)m24cell.getNumericCellValue();
        break;
    case 1:
        m24 =m24cell.getStringCellValue();
        break;
    default:
        m24 =""+(int)m24cell.getNumericCellValue();
        break;
}

if(m24.trim().equals("")){m24="0";}


//_____________________________4f___________________

XSSFCell f25cell = rowi.getCell((short) 6);

switch (f25cell.getCellType())
{
    case 0:
        //numeric
        f25 =""+(int)f25cell.getNumericCellValue();
        break;
    case 1:
        f25 =f25cell.getStringCellValue();
        break;
    default:
        f25 =""+(int)f25cell.getNumericCellValue();
        break;
}

if(f25.trim().equals("")){f25="0";}


//_____________________________4m___________________

XSSFCell m25cell = rowi.getCell((short) 7);

switch (m25cell.getCellType())
{
    case 0:
        //numeric
        m25 =""+(int)m25cell.getNumericCellValue();
        break;
    case 1:
        m25 =m25cell.getStringCellValue();
        break;
    default:
        m25 =""+(int)m25cell.getNumericCellValue();
        break;
}

if(m25.trim().equals("")){ m25="0";}





total=""+(new Integer(f14)+new Integer(m14)+new Integer(f24)+new Integer(m24)+new Integer(f25)+new Integer(m25));


if(new Integer(total)>0){ hasdata="yes"; }






                
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
                
                
                
                String id=weekend.replace("-","_")+"_"+mflcode+"_"+indicatorid+"_"+delivery_point;
                
                
                
                System.out.println("test__"+id+"  "+indicator);
                
                
                
                
                facilityID="";
                checker=0;
                
                //
                String get_id="SELECT mflcode FROM "+dbname+" WHERE id like ? ";
                conn.pst1=conn.connect.prepareStatement(get_id);
                conn.pst1.setString(1,"%"+id+"%");
                
                conn.rs=conn.pst1.executeQuery();
                if(conn.rs.next()==true)
                {
                    facilityID=conn.rs.getString(1);
                    
                    checker=1;
                    
                }
                if(!mflcode.equals("") && !delivery_point.equals("")) {
//                        DISTRICT FOUND ADD THE HF TO THE SYSTEM.........................

// if(new Integer(yearmonth)>=201710 && new Integer(yearmonth)<=201712 ){

if(checker==0){
    //System.out.println("** Inserting data ");
    //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
    
    String inserter="INSERT INTO "+dbname+" (id, date, delivery_point, year, month, indicator_id, mflcode, value, f14, m14, f24, m24, f25, m25, datekey,  datefromfile, submitted_by, Designation) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
    //conn.pst.setString(38,versionno);
    
     System.out.println(""+conn.pst);
    
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
    String inserter=" UPDATE "+dbname+" SET date=? , delivery_point=? , year=? , month=? , indicator_id=? , mflcode=? , value=? , f14=? , m14=? , f24=? , m24=? , f25=? , m25=? , datekey=? ,  datefromfile=? , submitted_by=? , Designation=?"
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
    conn.pst.setString(18,id);
    
    System.out.println(""+conn.pst);

conn.pst.executeUpdate();


if(!updatedfacil.contains(facilityName))
{
    updatedfacil+=facilityName.replace("'", "")+",";
    updated++;
}

}

//}
                }
                
                 else if(delivery_point.equals("") && hasdata.equals("yes"))
                 {
                    
                    NocontactsFacility+="facility  : "+facilityName+" mfl code : "+mflcode+" in sheet "+sheetname+" has no service delivery indicated <br>";
                    
                    System.out.println(sheetname+ " has no service delivery point but has data.");
                    
                    if(!missingcontwithdatafacil.contains(facilityName+"_"+sheetname))
                    { 
                        
                        missingcontwithdatafacil+=facilityName+"_"+sheetname+"("+fileNameCopy+"),";
                        nocontactsmodalitysites++;
                        
                    }
                    
                }
                
                else if(mflcode.equals("") && !total.equals("0")){
                    
                    missingFacility+="File  : "+fileNameCopy+"("+sheetname+")"+" has no mflcode selected yet has data<br>";
                    System.out.println(sheetname+ " has no mflcode but has data.");
                    
                    if(!missingwithdatafacil.contains(sheetname))
                    { 
                        
                        missingwithdatafacil+=sheetname+"("+fileNameCopy+"),";
                        nomflcodesites++;
                        
                    }
                    
                }
                
                else if(mflcode.equals("") && total.equals("0"))
                {
                    
                    // missingFacility+="facility  : "+sheetname+" mfl code : "+mflcode+" not in system "+row+"<br>";
                    System.out.println(sheetname+ " blank sheet");
                }
                
           else {
                    
                missing++;
              //missing facilities
missingFacility+="facility  : "+sheetname+" mfl code : "+mflcode+" not in system "+row+"<br>";
System.out.println(sheetname+ " uncaptured scenario");
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
        
        sessionText="<h2><font color=\"red\">Note: Data was uploaded using Wrong Templete version. Click here to <a class=\"btn btn-success\" href=\"pns/ART_DAILY_FORM_Final 2211.xlsx\">download correct template</a></font><h2>";
        
         }
    
    }//end of skipping printable sheet
}
}//end of worksheets loop
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
      newdaata=" <font color=\"green\"> Data for <b>"+added+ " </b> sites newly imported </font> ";
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
    System.out.println(""+sessionText);
    session.setAttribute("uploadedpns","<ul><b>Last Data Upload Results:</b></ul><br/><br/><br/>  File(s) : <b>"+fileNameCopy+".</b> "+ sessionText);
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


