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


  public class importndefaulter extends HttpServlet {
   
 
  
 
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
np_3m	0
def_3m	0
tl_3m	0
np_6m	0
def_6m	0
tl_6m	0
np_9m	0
def_9m	0
tl_9m	0
np_12m	0
def_12m	0
tl_12m	0
mflcode	14177
reportingyear	2016
reportingmonth	10
yearmonth	201610



      
      ***/
     
     
     


 
 
    String serialnumber="";
    
     String dbname="new_defaulter_cohort";
  
     
     session=request.getSession();
     dbConn conn = new dbConn();
     nextpage="importdefaulter.jsp";
     String applicationPath = request.getServletContext().getRealPath("");
     String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
     session=request.getSession();
     File fileSaveDir = new File(uploadFilePath);
     if (!fileSaveDir.exists()) {
         fileSaveDir.mkdirs();
     }
     System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
     for (Part part : request.getParts()) {
         
         ArrayList deletesheets = new ArrayList(); 
         
         fileName = getFileName(part);
         part.write(uploadFilePath + File.separator + fileName);
         System.out.println("file name is  :  "+fileName);
         if(!fileName.endsWith(".xlsx")){
         nextpage="importdefaulter.jsp";
         session.setAttribute("uploadedstf", "<font color=\"red\">Failed to load the excel file. Please choose a .xlsx excel file .</font>");
     }
     else{
             
             
             
         
             try {
                 fileNames+=fileName+", ";
                 
                 full_path=fileSaveDir.getAbsolutePath()+"/"+fileName;
                 
                 System.out.println("the saved file directory is  :  "+full_path);
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
    String np_3m="0";
    String def_3m="0";
    String tl_3m="0";
    String np_6m="0";
    String def_6m="0";
    String tl_6m="0";
    String np_9m="0";
    String def_9m="0";
    String tl_9m="0";
    String np_12m="0";
    String def_12m="0";
    String tl_12m="0";
    
    String mflcode="";
    String reportingyear="";
    String reportingmonth="";
    String yearmonth="";
    
    
    worksheet = workbook.getSheetAt(a);
    
    System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
    //if(!isSheetEmpty(worksheet)){
    String sheetni=workbook.getSheetName(a);
    if(!sheetni.contains("New & Defaulter instructions")&&!sheetni.contains("Sheet") &&!sheetni.equalsIgnoreCase("Calendar guide") && !sheetni.equalsIgnoreCase("STF instructions ") ){
        
        
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






int i=6,y=0;

//static number of rows
while(i<=18){
    try {
        //System.out.println(" row number "+i);
        XSSFRow rowi = worksheet.getRow(i);
        if( rowi==null )
        {
            
            break;
        }
        
        
        if(i>=6 && i<=18) {
            
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
            
            
            //np_3m
            
            XSSFCell np_3mcell = rowi.getCell((short) 2);
            
            switch (np_3mcell.getCellType()) {
                case 0:
                    //numeric
                    np_3m =""+(int)np_3mcell.getNumericCellValue();
                    break;
                case 1:
                    np_3m =np_3mcell.getStringCellValue();
                    break;
                default:
                    np_3m =""+np_3mcell.getRawValue();
                    break;
            }
            
            if(np_3m.trim().equals("")){np_3m="0";}
            
            
            //def_3m
            
            XSSFCell def_3mcell = rowi.getCell((short) 3);
            
            if(def_3mcell.getCellType()==0){
                //numeric
                def_3m =""+(int)def_3mcell.getNumericCellValue();
            }
            else if(def_3mcell.getCellType()==1){
                def_3m =def_3mcell.getStringCellValue();
            }
            
            else {
                def_3m =""+def_3mcell.getRawValue();
            }
            
            if(def_3m.trim().equals("")){def_3m="0";}
            
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
            
            
            if(tl_3m.trim().equals("")){tl_3m="0";}
            
            //np_6m
            
            XSSFCell np_6mcell = rowi.getCell((short) 5);
            
            if(np_6mcell.getCellType()==0){
                //numeric
                np_6m =""+(int)np_6mcell.getNumericCellValue();
            }
            else if(np_6mcell.getCellType()==1){
                np_6m =np_6mcell.getStringCellValue();
            }
            else {
                np_6m =""+np_6mcell.getRawValue();
            }
            if(np_6m.trim().equals("")){np_6m="0";}
            
            //def_6m
            
            XSSFCell def_6mcell = rowi.getCell((short) 6);
            
            if(def_6mcell.getCellType()==0){
                //numeric
                def_6m =""+(int)def_6mcell.getNumericCellValue();
            }
            else if(def_6mcell.getCellType()==1){
                def_6m =def_6mcell.getStringCellValue();
            }
            else {
                
                def_6m =""+def_6mcell.getRawValue();
                
            }
            
            if(def_6m.trim().equals("")){def_6m="0";}
            
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
            if(tl_6m.trim().equals("")){tl_6m="0";}
            
            //np_9m
            
            XSSFCell np_9mcell = rowi.getCell((short) 8);
            
            if(np_9mcell.getCellType()==0){
                //numeric
                np_9m =""+(int)np_9mcell.getNumericCellValue();
            }
            else if(np_9mcell.getCellType()==1){
                np_9m =np_9mcell.getStringCellValue();
            }
            else {
                np_9m =""+np_9mcell.getRawValue();
                
            }
            
            
            //def_9m
            
            XSSFCell def_9mcell = rowi.getCell((short) 9);
            
            if(def_9mcell.getCellType()==0){
                //numeric
                def_9m =""+(int)def_9mcell.getNumericCellValue();
            }
            else if(def_9mcell.getCellType()==1){
                def_9m =def_9mcell.getStringCellValue();
            }
            else {
                
                def_9m =""+def_9mcell.getRawValue();
                
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
            
            if(np_9m.trim().equals("")){np_9m="0";}
            if(def_9m.trim().equals("")){def_9m="0";}
            if(tl_9m.trim().equals("")){tl_9m="0";}
            
            //np_12m
            
            XSSFCell np_12mcell = rowi.getCell((short) 11);
            
            if(np_12mcell.getCellType()==0){
                //numeric
                np_12m =""+(int)np_12mcell.getNumericCellValue();
            }
            else if(np_12mcell.getCellType()==1){
                np_12m =np_12mcell.getStringCellValue();
            }
            else {
                
                np_12m =""+np_12mcell.getRawValue();
            }
            
            
            //def_12m
            
            XSSFCell def_12mcell = rowi.getCell((short) 12);
            
            if(def_12mcell.getCellType()==0){
                //numeric
                def_12m =""+(int)def_12mcell.getNumericCellValue();
            }
            else if(def_12mcell.getCellType()==1){
                def_12m =def_12mcell.getStringCellValue();
            }
            else {
                def_12m =""+def_12mcell.getRawValue();
                
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
            
            if(np_12m.trim().equals("")){np_12m="0";}
            if(def_12m.trim().equals("")){def_12m="0";}
            if(tl_12m.trim().equals("")){tl_12m="0";}
            
            
            
        }//end of cell values
        
        
        if(reportingmonth.length()==1){  reportingmonth="0"+reportingmonth; }
        
        yearmonth=reportingyear+""+reportingmonth;
        
        
        
        if(np_3m.length()>=4  || np_3m.equals("N/A") ||  np_3m.equals("NA")){np_3m="0";}
        if(def_3m.length()>=4 || def_3m.equals("N/A") ||  def_3m.equals("NA")){def_3m="0";}
        if(tl_3m.length()>=4 || tl_3m.equals("N/A") ||  tl_3m.equals("NA")){tl_3m="0";}
        if(np_6m.length()>=4 || np_6m.equals("N/A") ||  np_6m.equals("NA")){np_6m="0";}
        if(def_6m.length()>=4 || def_6m.equals("N/A") ||  def_6m.equals("NA")){def_6m="0";}
        if(tl_6m.length()>=4 || tl_6m.equals("N/A") ||  tl_6m.equals("NA")){tl_6m="0";}
        if(tl_6m.length()>=4 || tl_6m.equals("N/A") ||  tl_6m.equals("NA")){tl_6m="0";}
        if(np_9m.length()>=4 || np_9m.equals("N/A") ||  np_9m.equals("NA")){np_9m="0";}
        if(tl_9m.length()>=4 || tl_9m.equals("N/A") ||  tl_9m.equals("NA")){tl_9m="0";}
        if(def_9m.length()>=4 || def_9m.equals("N/A") ||  def_9m.equals("NA")){def_9m="0";}
        if(tl_9m.length()>=4 || tl_9m.equals("N/A") ||  tl_9m.equals("NA")){tl_9m="0";}
        if(np_12m.length()>=4 || np_12m.equals("N/A") ||  np_12m.equals("NA")){np_12m="0";}
        if(def_12m.length()>=4 || def_12m.equals("N/A") ||  def_12m.equals("NA")){def_12m="0";}
        if(tl_12m.length()>=4 || tl_12m.equals("N/A") ||  tl_12m.equals("NA")){tl_12m="0";}
        
        
        
        
        
        
        //================================continue from here========================================
        
        //get indicator id from list of indicators
        //create an id consisting of year_month_mflcode_indicator
        
        
        
        String getindicator="SELECT indicators_id,id,indicator FROM pmtct_art_cohort.indicators where cohort='defaulter' and indicator like '%"+indicator+"'";
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
    
    String inserter="INSERT INTO "+dbname+" ( id,indicator,np_3m,def_3m,tl_3m,np_6m,def_6m,tl_6m,np_9m,def_9m,tl_9m,np_12m,def_12m,tl_12m,mflcode,reportingyear,reportingmonth,yearmonth) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    conn.pst=conn.connect.prepareStatement(inserter);
    conn.pst.setString(1,id);
    conn.pst.setString(2,indicatorid);
    conn.pst.setString(3,np_3m);
    conn.pst.setString(4,def_3m);
    conn.pst.setString(5,tl_3m);
    conn.pst.setString(6,np_6m);
    conn.pst.setString(7,def_6m);
    conn.pst.setString(8,tl_6m);
    conn.pst.setString(9,np_9m);
    conn.pst.setString(10,def_9m);
    conn.pst.setString(11,tl_9m);
    conn.pst.setString(12,np_12m);
    conn.pst.setString(13,def_12m);
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
    String inserter=" UPDATE "+dbname+" SET id=?,indicator=?,np_3m=?,def_3m=?,tl_3m=?,np_6m=?,def_6m=?,tl_6m=?,np_9m=?,def_9m=?,tl_9m=?,np_12m=?,def_12m=?,tl_12m=?,mflcode=?,reportingyear=?,reportingmonth=?,yearmonth=?"
            + " WHERE id=?";
//
conn.pst=conn.connect.prepareStatement(inserter);

conn.pst.setString(1,id);
conn.pst.setString(2,indicatorid);
conn.pst.setString(3,np_3m);
conn.pst.setString(4,def_3m);
conn.pst.setString(5,tl_3m);
conn.pst.setString(6,np_6m);
conn.pst.setString(7,def_6m);
conn.pst.setString(8,tl_6m);
conn.pst.setString(9,np_9m);
conn.pst.setString(10,def_9m);
conn.pst.setString(11,tl_9m);
conn.pst.setString(12,np_12m);
conn.pst.setString(13,def_12m);
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
        System.out.println(" Sheetname "+sheetni+" failed uploading");         
        
        break;
    }
    
}//end of iterator





    }
}//end of worksheets loop

//now delete unwanted sheets
             } //end of checking if excel file is valid
             catch (InvalidFormatException ex) {
                 Logger.getLogger(importndefaulter.class.getName()).log(Level.SEVERE, null, ex);
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
         String outputpath=mydrive+":\\HSDSA\\COHORT\\Defaulter_UploadResults\\response_"+fileName.replace(".xlsx", "")+".xlsx";
             OSValidator ov= new OSValidator();
         
         if(ov.isUnix()){  outputpath="HSDSA/COHORT/Defaulter_UploadResults/response_"+fileName.replace(".xlsx", "")+".xlsx";  }
         
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
              Logger.getLogger(importndefaulter.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      
      String nomflcode="";
      if(!nomflsheets.equals("")){
      
      nomflcode="<b> "+nomflsheets+"</b> have no mflcodes/wrong month ";
      }
      
    String sessionText="<br/><b> "+added+ "</b> New sheets added <br/> <b> "+updated+"</b> updated sheets<br> <br> <b>"+nomflcode+"</b> <br> Please Check the folder <b>C:/APHIAPLUS/COHORT/Defaulter_UploadResults </b> for any files with missing information";    
    session.setAttribute("uploadeddefaulter"," Workbooks: "+fileNames.replace("'","")+". "+ sessionText.replace("'",""));
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
