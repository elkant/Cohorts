/*
Notes: This raw data is for positive EID. The data doesnt have age and sex
Age and sex should be gotten from the eid tested raw data during the importing of the raw data positives into the eid_datim_output table.

 */

package imports;

import db.dbConn;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scripts.OSValidator;
@MultipartConfig(fileSizeThreshold=1024*1024*20, 	// 20 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100) 

/**
 *
 * @author Emmanuel Kaunda
 */


  public class importstf extends HttpServlet {
   
 
  
 
  String fileName="";
  String fileNames="";
  int checker_dist,checker_hf;
  File file_source;
  HttpSession session;
  private static final long serialVersionUID = 205242440643911308L;
  private static final String UPLOAD_DIR = "uploads";
  String nextpage="";
  String quarterName,facilityName,facilityID,id,missingFacility;
    boolean anyerrors=false;      
 

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
      String full_path="";
    anyerrors=false;
      int year,quarter,checker,missing = 0,added = 0,updated = 0;
      String county_name,county_id, district_name,district_id,hf_name,hf_id;
     fileNames="";
     fileName="";
      String nomflsheets="";
      
     
     XSSFWorkbook outputwb = null;  
      List<FileInputStream> list = new ArrayList<>();
      
    
     /***  
      id	2016_10_14177_1
indicator	1
adult_3m	0
ayp_3m	0
tl_3m	0
adult_6m	0
ayp_6m	0
tl_6m	0
adult_9m	0
ayp_9m	0
tl_9m	0
adult_12m	0
ayp_12m	0
tl_12m	0
mflcode	14177
reportingyear	2016
reportingmonth	10
yearmonth	201610



      
      ***/
     
     
     


 
 
    String serialnumber="";
    
     String dbname="stf_cohort";
  
     
     session=request.getSession();
     dbConn conn = new dbConn();
     nextpage="importstf.jsp";
     String applicationPath = request.getServletContext().getRealPath("");
     String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
     session=request.getSession();
     File fileSaveDir = new File(uploadFilePath);
     if (!fileSaveDir.exists()) {
         fileSaveDir.mkdirs();
     }
    // System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
     for (Part part : request.getParts()) {
         
         ArrayList deletesheets = new ArrayList(); 
         
         fileName = getFileName(part);
         part.write(uploadFilePath + File.separator + fileName);
        // System.out.println("file name is  :  "+fileName);
         if(!fileName.endsWith(".xlsx")){
         nextpage="importstf.jsp";
         session.setAttribute("uploadedstf", "<font color=\"red\">Failed to load the excel file. Please choose a .xlsx excel file .</font>");
     }
     else{
             
             
             
         
             try {
                 fileNames+=fileName+", ";
                 
                 full_path=fileSaveDir.getAbsolutePath()+"/"+fileName;
                 
               //  System.out.println("the saved file directory is  :  "+full_path);
// GET DATA FROM THE EXCEL AND AND OUTPUT IT ON THE CONSOLE..................................
         
//         FileInputStream fileInputStream = new FileInputStream(full_path);
//         XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

FileInputStream fileInputStream = new FileInputStream(full_path);
XSSFWorkbook workbook = (XSSFWorkbook) ReadExcel(full_path);


outputwb=workbook;
list.add(fileInputStream);
//style

XSSFFont font2 = workbook.createFont();
font2.setFontName("Cambria");
font2.setColor((short) 1111);


XSSFCellStyle stylex = workbook.createCellStyle();
stylex.setFont(font2);
stylex.setWrapText(true);
stylex.setFillForegroundColor(HSSFColor.RED.index);
stylex.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
stylex.setBorderTop(HSSFCellStyle.BORDER_THIN);
stylex.setBorderBottom(HSSFCellStyle.BORDER_THIN);
stylex.setBorderLeft(HSSFCellStyle.BORDER_THIN);
stylex.setBorderRight(HSSFCellStyle.BORDER_THIN);
stylex.setAlignment(HSSFCellStyle.ALIGN_LEFT);


int totalsheets=workbook.getNumberOfSheets();

XSSFSheet worksheet=null;


for(int a=0;a<totalsheets;a++){
    
    String insertedfacil="";
    String updatedfacil="";
    id="";
    String indicator="";
    String indicatorid="";
    String adult_3m="";
    String ayp_3m="";
    String tl_3m="";
    String adult_6m="";
    String ayp_6m="";
    String tl_6m="";
    String adult_9m="";
    String ayp_9m="";
    String tl_9m="";
    String adult_12m="";
    String ayp_12m="";
    String tl_12m="";
    
    String mflcode="";
    String reportingyear="";
    String reportingmonth="";
    String yearmonth="";
    
    
    worksheet = workbook.getSheetAt(a);
    
    System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
    //if(!isSheetEmpty(worksheet)){
    //if(1==1){
    String sheetni=workbook.getSheetName(a);
    if(!sheetni.contains("Sheet") &&!sheetni.equalsIgnoreCase("Calendar guide") && !sheetni.equalsIgnoreCase("STF instructions ") ){
        
//______________________________________________________________________
anyerrors=false;
if(1==1) {
//______________________________________________________________________
//-----------facility name-----------------------
XSSFCell cellfacil = worksheet.getRow(2).getCell((short) 1);

if(cellfacil.getCellType()==0){
    //numeric
    facilityName =""+(int)cellfacil.getNumericCellValue();
}
else if(cellfacil.getCellType()==1){
    facilityName =cellfacil.getStringCellValue();
}


//-----------mflcode-----------------------
XSSFCell cellmfl = worksheet.getRow(2).getCell((short) 3);

if(cellmfl.getCellType()==0){
    //numeric
    mflcode =""+(int)cellmfl.getNumericCellValue();
}
else if(cellmfl.getCellType()==1){
    mflcode =cellmfl.getStringCellValue();
}

//incase oone writes the mflcode on the place where we have the MflCode label

XSSFCell cellmfl1 = worksheet.getRow(2).getCell((short) 2);

if(cellmfl1.getCellType()==0){
    //numeric.. assuming if its numeric then its obviusly mflcode
    mflcode =""+(int)cellmfl1.getNumericCellValue();
}
else if(cellmfl1.getCellType()==1){
    if(!cellmfl1.getStringCellValue().equalsIgnoreCase("*MflCode") && !cellmfl1.getStringCellValue().contains("MflCode")){
        mflcode =cellmfl1.getStringCellValue().trim();
    }
}




//-----------year-----------------------
XSSFCell cellyear = worksheet.getRow(2).getCell((short) 7);

if(cellyear.getCellType()==0){
    //numeric
    reportingyear =""+(int)cellyear.getNumericCellValue();
}
else if(cellyear.getCellType()==1){
    reportingyear =cellyear.getStringCellValue();
}

//}

//-----------month-----------------------
XSSFCell cellmonth = worksheet.getRow(2).getCell((short) 5);

if(cellmonth.getCellType()==0){
    //numeric
    reportingmonth =""+(int)cellmonth.getNumericCellValue();
}
else if(cellmonth.getCellType()==1){
    reportingmonth =cellmonth.getStringCellValue();
}
System.out.println("  %%%%%MFL is "+mflcode+" and Month is "+reportingmonth);

if(mflcode==null || mflcode.equals("")){
    
    nomflsheets+=workbook.getSheetName(a)+" ,";
    //set the mflcode input field to be in red background
    cellmfl.setCellValue("Enter Mflcode here");
    cellmfl.setCellStyle(stylex);
    anyerrors=true;
}
else if(reportingmonth.length()>2 || reportingmonth.equals("")){
    
    nomflsheets+=workbook.getSheetName(a)+" ,";
    //set the mflcode input field to be in red background
    cellmonth.setCellValue("Enter Month number here");
    cellmonth.setCellStyle(stylex);
    anyerrors=true;
}

else {
    anyerrors=false;
    deletesheets.add(a);
    
}

}


mflcode=mflcode.replace("*Mfl Code: ", "");
mflcode=mflcode.replace(" ", "");



int i=6,y=0;

//static number of rows
while(i<=20){
    try {
        //System.out.println(" row number "+i);
        XSSFRow rowi = worksheet.getRow(i);
        if( rowi==null )
        {
            
            break;
        }
        
        
        if(i>=6 && i<=20) {
            
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
                
                adult_3m =""+adult_3mcell.getRawValue();
            }
            
            if(adult_3m.trim().equals("")){adult_3m="";}
            
            
            //ayp_3m
            
            XSSFCell ayp_3mcell = rowi.getCell((short) 3);
            
            if(ayp_3mcell.getCellType()==0){
                //numeric
                ayp_3m =""+(int)ayp_3mcell.getNumericCellValue();
            }
            else if(ayp_3mcell.getCellType()==1){
                ayp_3m =ayp_3mcell.getStringCellValue();
            }
            
            else {
                ayp_3m =""+ayp_3mcell.getRawValue();
            }
            
            if(ayp_3m.trim().equals("")){ayp_3m="";}
            
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
                
                tl_3m =""+tl_3mcell.getRawValue();
            }
            
            
            if(tl_3m.trim().equals("")){tl_3m="";}
            
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
                adult_6m =""+adult_6mcell.getRawValue();
            }
            if(adult_6m.trim().equals("")){adult_6m="";}
            
            //ayp_6m
            
            XSSFCell ayp_6mcell = rowi.getCell((short) 6);
            
            if(ayp_6mcell.getCellType()==0){
                //numeric
                ayp_6m =""+(int)ayp_6mcell.getNumericCellValue();
            }
            else if(ayp_6mcell.getCellType()==1){
                ayp_6m =ayp_6mcell.getStringCellValue();
            }
            else {
                
                ayp_6m =""+ayp_6mcell.getRawValue();
                
            }
            
            if(ayp_6m.trim().equals("")){ayp_6m="";}
            
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
                tl_6m =""+tl_6mcell.getRawValue();
                
            }
            if(tl_6m.trim().equals("")){tl_6m="";}
            
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
                adult_9m =""+adult_9mcell.getRawValue();
                
            }
            
            
            //ayp_9m
            
            XSSFCell ayp_9mcell = rowi.getCell((short) 9);
            
            if(ayp_9mcell.getCellType()==0){
                //numeric
                ayp_9m =""+(int)ayp_9mcell.getNumericCellValue();
            }
            else if(ayp_9mcell.getCellType()==1){
                ayp_9m =ayp_9mcell.getStringCellValue();
            }
            else {
                
                ayp_9m =""+ayp_9mcell.getRawValue();
                
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
                
                tl_9m =""+tl_9mcell.getRawValue();
                
            }
            
            if(adult_9m.trim().equals("")){adult_9m="";}
            if(ayp_9m.trim().equals("")){ayp_9m="";}
            if(tl_9m.trim().equals("")){tl_9m="";}
            
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
                
                adult_12m =""+adult_12mcell.getRawValue();
            }
            
            
            //ayp_12m
            
            XSSFCell ayp_12mcell = rowi.getCell((short) 12);
            
            if(ayp_12mcell.getCellType()==0){
                //numeric
                ayp_12m =""+(int)ayp_12mcell.getNumericCellValue();
            }
            else if(ayp_12mcell.getCellType()==1){
                ayp_12m =ayp_12mcell.getStringCellValue();
            }
            else {
                ayp_12m =""+ayp_12mcell.getRawValue();
                
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
                tl_12m =""+tl_12mcell.getRawValue();
                
            }
            
            if(adult_12m.trim().equals("")){adult_12m="";}
            if(ayp_12m.trim().equals("")){ayp_12m="";}
            if(tl_12m.trim().equals("")){tl_12m="";}
            
            
            
        }//end of cell values
        
        
        if(reportingmonth.length()==1){  reportingmonth="0"+reportingmonth; }
        
        yearmonth=reportingyear+""+reportingmonth;
        
        
        if(adult_3m.length()>=4){adult_3m="0";}
        if(ayp_3m.length()>=4){ayp_3m="0";}
        if(tl_3m.length()>=4){tl_3m="0";}
        if(adult_6m.length()>=4){adult_6m="0";}
        if(ayp_6m.length()>=4){ayp_6m="0";}
        if(tl_6m.length()>=4){tl_6m="0";}
        if(adult_9m.length()>=4){adult_9m="0";}
        if(ayp_9m.length()>=4){ayp_9m="0";}
        if(tl_9m.length()>=4){tl_9m="0";}
        if(adult_12m.length()>=4){adult_12m="0";}
        if(ayp_12m.length()>=4){ayp_12m="0";}
        if(tl_12m.length()>=4){tl_12m="0";}
        
        
        
        //================================continue from here========================================
        
        //get indicator id from list of indicators
        //create an id consisting of year_month_mflcode_indicator
        
        
        
        String getindicator="SELECT indicators_id,id,indicator FROM pmtct_art_cohort.indicators where cohort='stf' and indicator like '%"+indicator+"'";
        //dbConn conn= new dbConn();
        //System.out.println(" Qry "+getindicator);
        conn.rs=conn.state.executeQuery(getindicator);
        while(conn.rs.next()){
            
            indicatorid=conn.rs.getString("indicators_id");
            
        }
        
        
        
        
        
        
        
        
        
        String id=reportingyear+"_"+reportingmonth+"_"+mflcode+"_"+indicatorid;
        
        
        
        //System.out.println("test__"+id+"  "+indicator);
        
        
        
        
        
        
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
        if(anyerrors==false) {
//                        DISTRICT FOUND ADD THE HF TO THE SYSTEM.........................


// if(new Integer(yearmonth)>=201710 && new Integer(yearmonth)<=201712 ){

if(checker==0){
    
    //id	SubPartnerID 	Mflcode	samplecode	collectiondate	testingdate	validation	enrollment	treatment_init_date	enroll_cccno	other_reasons	year	quarter
    
    String inserter="INSERT INTO "+dbname+" ( id,indicator,adult_3m,ayp_3m,tl_3m,adult_6m,ayp_6m,tl_6m,adult_9m,ayp_9m,tl_9m,adult_12m,ayp_12m,tl_12m,mflcode,reportingyear,reportingmonth,yearmonth) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    conn.pst=conn.connect.prepareStatement(inserter);
    conn.pst.setString(1,id);
    conn.pst.setString(2,indicatorid);
    conn.pst.setString(3,adult_3m);
    conn.pst.setString(4,ayp_3m);
    conn.pst.setString(5,tl_3m);
    conn.pst.setString(6,adult_6m);
    conn.pst.setString(7,ayp_6m);
    conn.pst.setString(8,tl_6m);
    conn.pst.setString(9,adult_9m);
    conn.pst.setString(10,ayp_9m);
    conn.pst.setString(11,tl_9m);
    conn.pst.setString(12,adult_12m);
    conn.pst.setString(13,ayp_12m);
    conn.pst.setString(14,tl_12m);
    conn.pst.setString(15,mflcode);
    conn.pst.setString(16,reportingyear);
    conn.pst.setString(17,reportingmonth);
    conn.pst.setString(18,yearmonth);
    System.out.println(""+conn.pst);
    conn.pst.executeUpdate();
    
    
    
    
    if(!insertedfacil.contains(workbook.getSheetName(a)))
    {
        insertedfacil+=workbook.getSheetName(a)+",";
        added++;
    }
    
    
    //added++;
    
}
else {
    //id,SubPartnerID,Year,Quarter,Mflcode,Sex ,age,agebracket,SubPartnerNom,dateoftesting,patientccc,batchno,supporttype
    String inserter=" UPDATE "+dbname+" SET id=?,indicator=?,adult_3m=?,ayp_3m=?,tl_3m=?,adult_6m=?,ayp_6m=?,tl_6m=?,adult_9m=?,ayp_9m=?,tl_9m=?,adult_12m=?,ayp_12m=?,tl_12m=?,mflcode=?,reportingyear=?,reportingmonth=?,yearmonth=?"
            + " WHERE id=?";
//
conn.pst=conn.connect.prepareStatement(inserter);

conn.pst.setString(1,id);
conn.pst.setString(2,indicatorid);
conn.pst.setString(3,adult_3m);
conn.pst.setString(4,ayp_3m);
conn.pst.setString(5,tl_3m);
conn.pst.setString(6,adult_6m);
conn.pst.setString(7,ayp_6m);
conn.pst.setString(8,tl_6m);
conn.pst.setString(9,adult_9m);
conn.pst.setString(10,ayp_9m);
conn.pst.setString(11,tl_9m);
conn.pst.setString(12,adult_12m);
conn.pst.setString(13,ayp_12m);
conn.pst.setString(14,tl_12m);
conn.pst.setString(15,mflcode);
conn.pst.setString(16,reportingyear);
conn.pst.setString(17,reportingmonth);
conn.pst.setString(18,yearmonth);
conn.pst.setString(19,id);
conn.pst.executeUpdate();
if(!updatedfacil.contains(workbook.getSheetName(a)))
{
    updatedfacil+=workbook.getSheetName(a)+",";
    updated++;
}
}
// }//end of if current period
        }         
        
        else{
            missing++;
//                        missing facilities
missingFacility+="facility  : "+facilityName+" mfl code : "+mflcode+" not in system "+i+"<br>";
//System.out.println(facilityName+ " error in mflcode or month");
        }
        i++;
    } //end of try
    catch (SQLException ex) {
        Logger.getLogger(importart.class.getName()).log(Level.SEVERE, null, ex);
        //break;
    }
    
}//end of iterator





    }
    
    
}//end of worksheets loop

//now delete unwanted sheets
             } //end of checking if excel file is valid
             catch (InvalidFormatException ex) {
                 Logger.getLogger(importstf.class.getName()).log(Level.SEVERE, null, ex);
             }
      
      
         
         }
         
         
         for(int x=deletesheets.size()-1;x>=0;x--){
          outputwb.removeSheetAt((int) deletesheets.get(x));
             System.out.println("Deleted sheet "+x);
                                               }
         
         //System.out.println("  sahi sheets ni "+outputwb.getNumberOfSheets());
         if(outputwb.getNumberOfSheets()>0){ 
          //now run an output in browser
          
         String allpath = getServletContext().getRealPath("/dbase.txt");
         String mydrive = allpath.substring(0, 1);
         String outputpath=mydrive+":\\HSDSA\\COHORT\\Stf_UploadResults\\response_"+fileName.replace(".xlsx", "")+".xlsx";
             OSValidator ov= new OSValidator();
         
         if(ov.isUnix()){  outputpath="HSDSA/COHORT/Stf_UploadResults/response_"+fileName.replace(".xlsx", "")+".xlsx";  }
         
        // new File(outputpath).mkdirs(); 
         
         //create a file
         
         //File nf=
             System.out.println(" File ni  "+outputpath);    
         //To write your changes to new workbook
FileOutputStream out = new FileOutputStream(outputpath);
outputwb.write(out);
out.close();


          
        //ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        //outputwb.write(outByteStream);
        //byte[] outArray = outByteStream.toByteArray();
       // response.setContentType("application/ms-excel");
       // response.setContentLength(outArray.length);
        //response.setHeader("Expires:", "0"); // eliminates browser caching
        //response.setHeader("Content-Disposition", "attachment; filename=" + "response_"+fileName.replace(".xlsx", "")+".xlsx");
        //response.setHeader("Set-Cookie","fileDownload=true; path=/");
        //OutputStream outStream = response.getOutputStream();
        //outStream.write(outArray);
        //outStream.flush();
        }
         
         
     }//end of multiple workbooks
     
     
   
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
              Logger.getLogger(importstf.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      
      String nomflcode="";
      if(!nomflsheets.equals("")){
      
      nomflcode="<b> "+nomflsheets+"</b> have no mflcodes/wrong month ";
      }
      
    String sessionText="<br/><b> "+added+ "</b> New sheets added <br/> <b> "+updated+"</b> updated sheets<br> <br> <b>"+nomflcode+"</b> <br> Please Check the folder <b>C:/APHIAPLUS/COHORT/Stf_UploadResults </b> for any files with missing information";    
    session.setAttribute("uploadedstf"," Workbooks: "+fileNames.replace("'","")+". "+ sessionText.replace("'",""));
     System.out.println("__"+sessionText);
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
    
boolean isSheetEmpty(XSSFSheet sheet){
    boolean status=true;
    
       Iterator rows = sheet.rowIterator();
       while (rows.hasNext()) {
           status = false;
                    
            System.out.println(sheet.getSheetName()+" is not empty");
          break;
//           XSSFRow row = (XSSFRow) rows.next();
//           Iterator cells = row.cellIterator();
//           while (cells.hasNext()) {
//                XSSFCell cell = (XSSFCell) cells.next();
//                if (cell  == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
//  
//                    System.out.println(sheet.getSheetName()+" is empty");
//                    status = true;
//                }
//                else{
//                System.out.println(sheet.getSheetName()+" is not empty");
//                    status = false;
//                    break;
//                }
//           }
       }
       return status;
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
