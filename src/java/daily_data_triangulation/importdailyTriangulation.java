/*
Notes: This raw data is for positive EID. The data doesnt have age and sex
Age and sex should be gotten from the eid tested raw data during the importing of the raw data positives into the eid_datim_output table.

 */

package daily_data_triangulation;


import db.dbConn;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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


  public class importdailyTriangulation extends HttpServlet {
   
 
  
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


***/
     
String id="";
String date="";
String indicator_id="";
String mflcode="";
String value="";
String datefromfile="";
String submitted_by="";
String Designation="";

id=""; 
String indicator="";
String indicatorid="";
String weekstart="";





weekstart=request.getParameter("weekstart");


if(!weekstart.equals("")){
 session.setAttribute("weekstart",weekstart );
 
                         }


//-----------year-----------------------


date=weekstart;

String updatedfacil="";
String insertedfacil="";
String missingwithdatafacil="";
String missingcontwithdatafacil="";
String unimporteddata="";
String updateddata="";
String newdaata="";

  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

 
    String serialnumber="";
    
     String dbname="der_rri.daily_triangulation_data";
  
   
     dbConn conn = new dbConn();
     
     nextpage="importDtriangulation.jsp";
     
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
            
             if(!fileName.endsWith(".xlsx"))
             {
         System.out.println("___ hii sio ile file tunataka ");
         
         nextpage="importDtriangulation.jsp";
         sessionText="<font color=\"red\">Failed to load a .xls excel file. Please open the file, go to file> options > save as , then save as .xlsx </font>";
             }
            
            }
        //}
     
     if(!fileName.endsWith(".xlsx")){
       
         nextpage="importDtriangulation.jsp";
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
int rowsngapi=1;
int rowCount=9;
for(int a=0;a<totalsheets;a++){
    
    XSSFSheet worksheet = workbook.getSheetAt(a);
    
    String hasdata="no";
    
    System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
    
    String sheetname =  workbook.getSheetName(a);
    
//_______

//if(1==1){
//skip pivot PNS
if(sheetname.equals("Data Triangulation")){
    
    Iterator rowIterator = worksheet.rowIterator();
    
    
    
    int row=3,col=0;
    
    //static number of rows
   
    
//______________________________________________________________________
//-----------facility name-----------------------
XSSFCell cellfacil = worksheet.getRow(6).getCell((short) 2);

if(cellfacil.getCellType()==0){
    //numeric
    facilityName =""+(int)cellfacil.getNumericCellValue();
}
else if(cellfacil.getCellType()==1)
{
    facilityName =cellfacil.getStringCellValue();
}


//-----------mfl-----------------------
XSSFCell cellmfl = worksheet.getRow(6).getCell((short) 3);

        switch (cellmfl.getCellType()) {
            case 0:
                //numeric
                mflcode =""+(int)cellmfl.getNumericCellValue();
                break;
            case 1:
                mflcode =cellmfl.getStringCellValue();
                break;
            case 2:
                mflcode =cellmfl.getRawValue();
                break;
            default:
                System.out.println("mflcode__"+mflcode);
                mflcode =cellmfl.getRawValue();
                break;
        }
System.out.println("Mfl code is "+mflcode);
    
    //    __________________Date Tested_____________________________
    
String dd;
String mm;
String yyyy;

dd="";
mm="";
yyyy="";

    XSSFCell celldd= worksheet.getRow(6).getCell((short)6);
    XSSFCell cellmm= worksheet.getRow(6).getCell((short)7);
    XSSFCell cellyy= worksheet.getRow(6).getCell((short)8);
 // Date
    if(celldd.getCellType()==0)
{   //numeric
    dd =""+(int)celldd.getNumericCellValue();
}
else if(celldd.getCellType()==1)
{
    dd =celldd.getStringCellValue();
}
 
     // Month
    if(cellmm.getCellType()==0)
{   //numeric
    mm =""+(int)cellmm.getNumericCellValue();
}
else if(cellmm.getCellType()==1)
{
    mm =cellmm.getStringCellValue();
}
  
    
        // Year
    if(cellyy.getCellType()==0)
{   //numeric
    yyyy =""+(int)cellyy.getNumericCellValue();
}
else if(cellyy.getCellType()==1)
{
    yyyy =cellyy.getStringCellValue();
}
  datefromfile=yyyy+"-"+mm+"-"+dd;  
  
    
   
    
    
    
     
      //__________________________version row________________
    
     String versionno="";
     
  
     
XSSFCell cellversion= worksheet.getRow(3).getCell((short) 1);

if(cellversion.getCellType()==0){
    //numeric
    versionno =""+(int)cellversion.getNumericCellValue();
}
else if(cellversion.getCellType()==1)
{
    versionno =cellversion.getStringCellValue();
}




    System.out.println("Version no ni :"+versionno);
    
    
    
   
        
    if(versionno.equals("DTT V 1.0.1") ){
        
        while(row<=17){
                 rowsngapi++;
        session.setAttribute("dailyartpos", "<b>"+rowsngapi+"/"+rowCount+"</b>");
        session.setAttribute("dailyartpos_count", (rowsngapi*100)/rowCount);
            
            try {
                System.out.println(" row number "+row);
                XSSFRow rowi = worksheet.getRow(row);
                if( rowi==null )
                {
                    
                    break;
                }
                
                
                if( row>=9 && row<=17 ) {
                    
                    
// _______________________________________________________________


//elements

//indicator

XSSFCell indiccell = rowi.getCell((short)1);

                    switch (indiccell.getCellType()) {
                        case 0:
                            //numeric
                            indicatorid =""+(int)indiccell.getNumericCellValue();
                            break;
                        case 1:
                            indicatorid =indiccell.getStringCellValue();
                            break;
                        default:
                            indicatorid =indiccell.getRawValue();
                            break;
                    }

if(1==1)
 {
                
    //Value
    
    XSSFCell valcell  = rowi.getCell((short) 3);
    
    switch (valcell.getCellType())
    {
        case 0:
            //numeric
            value =""+(int)valcell.getNumericCellValue();
            break;
        case 1:
            value =valcell.getStringCellValue();
            break;
        default:
            System.out.println("cell type "+valcell.getCellType());
            value =""+(int)valcell.getNumericCellValue();
            break;
    }
    
    if(value.trim().equals("")){value="";}
    
  

if(!value.equals("")){ hasdata="yes"; }

             
       
                
                
                 id=weekstart.replace("-","_")+"_"+mflcode+"_"+indicatorid;
                
                
                
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
                if(!mflcode.equals("") ) {
//                        DISTRICT FOUND ADD THE HF TO THE SYSTEM.........................

// if(new Integer(yearmonth)>=201710 && new Integer(yearmonth)<=201712 ){


    //System.out.println("** Inserting data ");
    //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
    
    String inserter="REPLACE INTO "+dbname+" (id,date,indicator_id,mflcode,value,datefromfile,version) "
            + "VALUES(?,?,?,?,?,?,?)";
    conn.pst=conn.connect.prepareStatement(inserter);
    conn.pst.setString(1,id);
    conn.pst.setString(2,date);
    conn.pst.setString(3,indicatorid);
    conn.pst.setString(4,mflcode);
    conn.pst.setString(5,value);
    conn.pst.setString(6,datefromfile);    
    conn.pst.setString(7,versionno);    

    
     System.out.println(""+conn.pst);
    
    conn.pst.executeUpdate();
   
    
    //added++;
    
    if(!insertedfacil.contains(facilityName))
    {
        insertedfacil+=facilityName.replace("'", "")+",";
        added++;
    }
    if(checker==0)
    {
}


//}
                }
                
              
                
                else if(mflcode.equals("") && !value.equals(""))
                {
                    
                    missingFacility+="File  : "+fileNameCopy+"("+sheetname+")"+" has no mflcode selected yet has data<br>";
                    System.out.println(sheetname+ " has no mflcode but has data.");
                    
                    if(!missingwithdatafacil.contains(sheetname))
                    { 
                        
                        missingwithdatafacil+=sheetname+"("+fileNameCopy+"),";
                        nomflcodesites++;
                        
                    }
                    
                }
                
                else if(mflcode.equals("") )
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
                
                
                
            }
                
                }// end of row 17 if
                
                
                
            } //end of iterator
            catch (SQLException ex) {
                Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            row++;
            //System.out.println(" eeend of while looop");
            
        }
        
    }//end of correct template version  

    else {
        
        sessionText="<h2><font color=\"red\">Note: Data was uploaded using Wrong Templete version. Click here to <a class=\"btn btn-success\" href=\"pns/ART_DAILY_FORM_Final 2211.xlsx\">download correct template</a></font><h2>";
        
         }

}
}//end of worksheets loop
rowsngapi=9;
               } catch (InvalidFormatException ex) {
                   Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
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
           Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.st!=null){
      try {
          conn.st.close();
          } catch (SQLException ex) 
          {
              Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.pst!=null){try {
          conn.pst.close();
          } catch (SQLException ex) 
          {
              Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      
  
            if(conn.connect!=null){try 
            {
                conn.connect.close();
            } catch (SQLException ex) {
          Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
      }
}
     
      
       if(conn.connect!=null){
           try {
          conn.connect.close();
               } catch (SQLException ex) {
              Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
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
          Logger.getLogger(importdailyTriangulation.class.getName()).log(Level.SEVERE, null, ex);
      } 
   
   return wb;
}
    
    
}


