/*
Notes: This raw data is for positive EID. The data doesnt have age and sex
Age and sex should be gotten from the eid tested raw data during the importing of the raw data positives into the eid_datim_output table.

 */

package pmtct_rri;


import db.dbConn;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
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


  public class importpmtctrri extends HttpServlet {
   
 
  
  String full_path="";
  String fileName="";
  int checker_dist,checker_hf;
  File file_source;
  HttpSession session;
  private static final long serialVersionUID = 205242440643911308L;
  private static final String UPLOAD_DIR = "uploads";
  String nextpage="";
  String quarterName,facilityName,facilityID,missingFacility;
          
  String fileNames="";

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
     String nomflsheets="";
     
     String toolversion="";
String selectedfacility="";
String sheethaserror="";
String sheethaserrorvalue="";
  String haserrorvalue="";  
  String hassuceeded="";  
      int year,quarter,checker,missing = 0,added = 0,updated = 0;
      
      String county_name,county_id, district_name,district_id,hf_name,hf_id;
     fileNames="";
     fileName="";
 
     
     String ujumbe="";
     
    String user="unknown user";
    String userid="unknown user";
    
    
     
     HashMap<String, String> versions= new HashMap<String, String>();
     
     versions.put("PRR", "PR_V 1.0.1");
     //versions.put("MCA", "Maternal Cohort Analysis (MCA) Version 2.0.0");
     
     int rowgani=1;
     int rowCount=97;
     



String dv[]={"row_id","mflcode","main_indicator","indicator","indicid","week1","week2","week3","week4","week5","week6","week7","week8","week9","week10","week11","week12","week13","week14","week15","week16","total"};

//___________________________________________________________________________________________________________



//___________________________________________________________________________________________________________



int filescount=0;



 
    String serialnumber="";
    
     String dbname="internal_system.pmtct_rri";
  
     
     session=request.getSession();
     
     
     if(session.getAttribute("username")!=null) {
         
     user=session.getAttribute("username").toString();
     userid=session.getAttribute("userid").toString();
     
     }
     
     
     
      if(session.getAttribute("filesngapi")!=null) 
      {
          
     int idadiyafiles= new Integer(session.getAttribute("filesngapi").toString());
     System.out.println(" Files za kuupload ni :"+idadiyafiles);  
     rowCount=rowCount*idadiyafiles;
     }
     
     
     dbConn conn = new dbConn();
     
     nextpage="importpmtctrri.jsp";
     
     
     String applicationPath = request.getServletContext().getRealPath("");
     
     String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
     
     session=request.getSession();
     
     File fileSaveDir = new File(uploadFilePath);
     
     if (!fileSaveDir.exists()) 
     {
         fileSaveDir.mkdirs();
     }
     
     System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
     
     for (Part part : request.getParts()) 
     {
         
         
          
         
         
         if(!getFileName(part).equals("") && getFileName(part).contains(".xlsx") )
         
         { 
         fileName = getFileName(part);
         part.write(uploadFilePath + File.separator + fileName);
         System.out.println("file name is  :  "+fileName);
         
         
         
         }
         
           ArrayList uploadedfiles=new ArrayList();
         
          if(!fileName.endsWith(".xlsx"))
          {          
       
          
          nextpage="importpmtctrri.jsp";
          session.setAttribute("upload_success", "<font color=\"red\">Failed to load the excel file. Please choose a .xlsx excel file .</font>");
          
          }
          
          else {
           
                
              
              fileNames+=fileName+", ";
              full_path=fileSaveDir.getAbsolutePath()+"/"+fileName;
              System.out.println("the saved file directory is  :  "+full_path);
// GET DATA FROM THE EXCEL AND AND OUTPUT IT ON THE CONSOLE..................................
FileInputStream fileInputStream = new FileInputStream(full_path);
XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
int totalsheets=workbook.getNumberOfSheets();


//________________________________________________________Get Worksheets_______________________


for(int a=0;a<totalsheets;a++)
{
    
    
    XSSFSheet worksheet = workbook.getSheetAt(a);
    
    
    System.out.println( a+" ("+workbook.getSheetName(a)+") out of "+totalsheets+" sheets");
    
    
    
    
 
//======================================================================Data entry Sheet======================================================================================
//import data from the data entry sheet
if(workbook.getSheetName(a).equals("data_entry"))
{
   
    //________________Tool Version ___________________
   
    XSSFCell cellversion = worksheet.getRow(3).getCell((short) 2);    
    
    if(cellversion.getCellType()==0)
    {   //numeric
        toolversion =""+(int)cellversion.getNumericCellValue();
    }
    else if(cellversion.getCellType()==1)
    {
        toolversion =cellversion.getStringCellValue();
    }
    
    //____________________site selected_____________
    
    XSSFCell cellsite = worksheet.getRow(2).getCell((short)14);    
    
        switch (cellsite.getCellType()) {
            case 0:
                //numeric
                selectedfacility =""+(int)cellsite.getNumericCellValue();
                break;
            case 1:
                selectedfacility =cellsite.getStringCellValue();
                break;
            default:
                selectedfacility =cellsite.getRawValue();
                break;
        }
     //____________________Template has validation error_____________
    
    XSSFCell cellvalidationerror = worksheet.getRow(3).getCell((short) 3);    
    
        switch (cellvalidationerror.getCellType()) {
            case 0:
                //numeric
                sheethaserror =""+(int)cellvalidationerror.getNumericCellValue();
                break;
            case 1:
                sheethaserror =cellvalidationerror.getStringCellValue();
                break;
            default:
                sheethaserror =cellvalidationerror.getRawValue();
                break;
        } 
    
         //____________________Template has validation error_____________
    
    XSSFCell cellvalidationerrorspecific = worksheet.getRow(8).getCell((short) 27);    
    
        switch (cellvalidationerrorspecific.getCellType()) 
        {
            case 0:
                //numeric
                sheethaserrorvalue =""+(int)cellvalidationerrorspecific.getNumericCellValue();
                break;
            case 1:
                sheethaserrorvalue =cellvalidationerrorspecific.getStringCellValue();
                break;
            default:
                sheethaserrorvalue =cellvalidationerrorspecific.getRawValue();
                break;
        } 
    
    
    
    System.out.println("Version:"+toolversion);
    System.out.println("Is facility Selected:"+selectedfacility);
    System.out.println("Validation error:"+sheethaserror);
    System.out.println("Validation error value:"+sheethaserrorvalue);
    
    
    
   
    
}//end of data_entry worksheet
    
    
    
//______________________________________________________________________

//======================================================================DB Sheet======================================================================================
//import data from the db sheet
if(workbook.getSheetName(a).equals("db"))
{
    System.out.println("Inside db loop");
    
    
    
  
    
    boolean haserror=false;
     haserrorvalue="";
    
    
    if(!toolversion.equalsIgnoreCase(versions.get("PRR")) ){
        
        System.out.println("You are not using the latest Tool");
        haserrorvalue="";
     haserrorvalue+="<font color=\"red\">Upload for Data file "+selectedfacility+" Failed: Not Using the Latest recommended Reporting tool</font><br/>";
    }
    
    else  if(selectedfacility.equalsIgnoreCase("") )
    {
        System.out.println("You have not selected facility name______::");
        
         haserrorvalue+="<font color=\"red\">Upload for Data file "+selectedfacility+" Failed: Facility Name not selected</font><br/>";
    
    }
    
     else  if(!sheethaserror.equalsIgnoreCase("") )
    {
        System.out.println("Worksheet has validation error");
        
        haserrorvalue+="<font color=\"red\">Upload for Data file "+selectedfacility+" Failed: Please Correct Validation Errors before uploading </font><br/>";
    
    }
    
    else 
    {
        
        System.out.println("Clean Template");
        
        
        
        
        Iterator rowIterator = worksheet.rowIterator();
        
        
        int i=1,y=0;
        
        //static number of rows
        while(i<=97){
            sheethaserrorvalue="Data Uploaded Succesfully";
            String insertqr_parta= "replace into internal_system.pmtct_rri (";  // finish with )
            String insertqr_partb= " values ("; // finish with )
            
            try {
                rowgani++;
                session.setAttribute("prrpos", "<b>"+rowgani+"/"+rowCount+"</b>");
                session.setAttribute("prrpos_count", (rowgani*100)/rowCount);
                
                System.out.println(" row number "+i);
                
                XSSFRow rowi = worksheet.getRow(i);
                if( rowi==null )
                {
                    
                    break;
                }
                
                if(i>=1 && i<=97) {
                    
                    
                    HashMap<String,String> dvhm=new HashMap<String, String>();
                    
                    String val="";
                    
                    for(int cl=0;cl<dv.length;cl++){
                        
                        XSSFCell custcell = rowi.getCell((short) cl);
                        if(custcell!=null){
                            switch (custcell.getCellType())
                            {
                                case 0:
                                    //numeric
                                    val =""+(int)custcell.getNumericCellValue();
                                    break;
                                case 1:
                                    //string
                                    val =custcell.getStringCellValue();
                                    break;
                                case 2:
                                    //String
                                    val =""+custcell.getRawValue();
                                    break;
                                default:
                                    val =custcell.getRawValue();
                                    break;
                            }
                        }
                        
                        if(val==null){val="";}
                        System.out.println("Value ni "+val);
                        //if(val.trim().equals("")){val="";}
                        dvhm.put(dv[cl], val);
                        
//build an inster qry
if(cl==dv.length-1){
    insertqr_parta+=dv[cl]+")";
    insertqr_partb+="?)";
    //last section
//insertqr_parta+=")";
//insertqr_partb+=")";
}
else {
    insertqr_parta+=dv[cl]+",";
    insertqr_partb+="?,";
}


//dvs.add(val);

                    }//end of for looop
                    
                    String insertqry=insertqr_parta+insertqr_partb;
                    
                    System.out.println(""+insertqry);
                    
                    //conn.st_2.executeUpdate(updateqr);
                    conn.pst1=conn.connect.prepareStatement(insertqry);
                    //now append the values
                    int rc=1;
                    for(int cl=0;cl<dv.length;cl++)
                    {
                        
                        String data=dvhm.get(dv[cl]);
                        
                        conn.pst1.setString(rc,data);
                        
                        rc++;
                        
                    }
                    System.out.println("___"+conn.pst1);
                    if(conn.pst1.executeUpdate()==1)
                    {
                        System.out.println("Data file Uploaded succesfully ");
                        
                        
                    }
                    else {
                        System.out.println(" Data Not Uploaded ");
                        
                    }
                    
                    if(!hassuceeded.contains(selectedfacility)){
                            hassuceeded+=selectedfacility+",";
                        haserrorvalue+="<font color=\"green\">Data file for "+selectedfacility+" Uploaded succesfully</font><br/>";
                        }
                    
                }//end of while 1 to 215 cell values
                
                
                
                
                //================================continue from here========================================
                
                
                
                facilityID="";
                
                checker=0;
                
                
                
                i++;
            } //end of iterator
            catch (SQLException ex) {
                Logger.getLogger(importpmtctrri.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
            
        }//end of while
        
        
        
    }
    
    
} //end of DB WORKSHEET









}//end of worksheets loop
      
         
          }
     
         
     }
    //end of checking if excel file is valid
     if(conn.rs!=null){try {
         conn.rs.close();
          } catch (SQLException ex) {
              Logger.getLogger(importpmtctrri.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.st!=null){try {
          conn.st.close();
          } catch (SQLException ex) {
              Logger.getLogger(importpmtctrri.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      if(conn.pst!=null){try {
          conn.pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(importpmtctrri.class.getName()).log(Level.SEVERE, null, ex);
          }
}
    
            if(conn.connect!=null){try {
                conn.connect.close();
         } catch (SQLException ex) {
             Logger.getLogger(importpmtctrri.class.getName()).log(Level.SEVERE, null, ex);
         }
}
      
       String nomflcode="";
      if(!nomflsheets.equals("")){
      
      nomflcode="<b> "+nomflsheets+"</b> have no mflcodes ";
      }
      
     String sessionText="<br/><b> "+added+ "</b> New data added <br/> <b> "+updated+"</b> updated facilities<br> <br> <b>"+nomflcode+"</b>";    
     session.setAttribute("uploadedPRR"," Workbooks: "+fileNames+". "+ sessionText+"<br/>"+haserrorvalue+"");
    
 
  
    
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
    
    
 
    
     public String getFacilityID(String mflcode, dbConn conn ) throws SQLException 
    {
        String id = "";

        String getindicator = "SELECT SubPartnerID FROM subpartnera where CentreSanteId = '" +mflcode+"'";
             
        conn.rs = conn.st.executeQuery(getindicator);
        while (conn.rs.next()) 
        {
            id = conn.rs.getString("SubPartnerID");
        }
        return id;

    }
     
     
     public String  monthName(String monthno)
     {
        String mn="";
    if(monthno.equals("01")){
    mn="January";
    }
    else if(monthno.equals("02")){
    mn="February";
    }
    else if(monthno.equals("03")){
    mn="March";
    }
     else if(monthno.equals("04")){
    mn="April";
    }   
    else if(monthno.equals("05")){
    mn="May";
    }  
else if(monthno.equals("06")){
    mn="June";
    } 
    
    else if(monthno.equals("07")){
    mn="July";
    } 
     else if(monthno.equals("08")){
    mn="August";
    } 
    
    else if(monthno.equals("09")){
    mn="September";
    }
    else if(monthno.equals("10")){
    mn="October";
    }
    else if(monthno.equals("11")){
    mn="November";
    }
    else if(monthno.equals("12")){
    mn="December";
    }
    return mn;
    }
    
    
     
    
}
