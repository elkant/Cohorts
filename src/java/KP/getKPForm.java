/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KP;

import db.OSValidator;
import db.dbConn;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scripts.copytemplates;

/**
 *
 * @author EKaunda
 */
public class getKPForm extends HttpServlet {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, InvalidFormatException {
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            
            
//_______________________ Create excel templates _________________________________________

            XSSFWorkbook wb = null;

           

            String allpath = getServletContext().getRealPath("/KP_F1.xlsx");

         

        
            String mydrive = allpath.substring(0, 1);
            // wb = new XSSFWorkbook( OPCPackage.open(allpath) );

            Date da = new Date();
            String dat2 = da.toString().replace(" ", "_");
            dat2 = dat2.toString().replace(":", "_");
            
             
     
            String year = "";
            String monthar = null;
            String month = "";
            String county = "";
            String dicname = "";
            String subcountyname = "";
            String subcountynamelist = "";
            String diccode = "";
            String todeletefiles="";

            
            String dicarr[] = null;
            String dic = "(";

            if (request.getParameter("year") != null) 
            {
                year = request.getParameter("year");
            }

            if (request.getParameter("months") != null) 
            {
                monthar = request.getParameter("months");
            }

           

            

            if (request.getParameterValues("dic_name") != null) {
                dicarr = request.getParameterValues("dic_name");

                for (int a = 0; a < dicarr.length; a++) {

                    if (a == dicarr.length - 1) {

                        dic += dicarr[a] + ")";

                    } else {

                        dic += dicarr[a] + ",";

                    }
                }
            }

            //do a loop with all the parameters above considered
            String finalpath = "";

            String where = " and ( dic.active=1  ) ";

          

            if (!dic.equals("(")) 
            {
                where += " and dic.dic_id in  " + dic;

            }
       
            
            String getCount = " select count(*) as dics from internal_system.dic join internal_system.ward on ward.ward_id=dic.ward_id join ( internal_system.district join internal_system.county on county.countyid=district.countyid) on ward.subcountyid=district.DistrictID "
                    + " " + where + " ";
            
            String fetchqry = " select county,districtnom as subcounty ,dic_name as Facility,dic_id as mflcode from internal_system.dic join internal_system.ward on ward.ward_id=dic.ward_id join ( internal_system.district join internal_system.county on county.countyid=district.countyid) on ward.subcountyid=district.DistrictID "
                    + " " + where + " ";
            System.out.println("" + fetchqry);
            dbConn conn = new dbConn();
  int countworkbooks = 0;
            conn.rs = conn.st.executeQuery(getCount);
              while (conn.rs.next()) 
              {
              
               countworkbooks = conn.rs.getInt("dics");   
              
              }
            
            
            conn.rs = conn.st.executeQuery(fetchqry);
          
            int curworkbook = 0;
            String workbooknames="";
            String workbookidentifier="";
            
             String npt = "";
             workbooknames="";
            
           while (conn.rs.next()) {
                
                    county = conn.rs.getString("county");
                    dicname = conn.rs.getString("Facility");
                    subcountyname = conn.rs.getString("subcounty");
                    diccode = conn.rs.getString("mflcode");
                    if(!subcountynamelist.contains(subcountyname)){
                    subcountynamelist+=subcountyname+"_";
                    }
                
                    System.out.println("Mflcode: "+diccode);
                    System.out.println("Subcounty: "+subcountyname);
                    System.out.println("subcountylist: "+subcountynamelist);
                    System.out.println("workbooks: "+countworkbooks);
                    
                //create workbook for each facility
                
                String smonth=monthar;
String emonth=monthar;
String mwezi="";
if(smonth.equals(emonth)){  mwezi=emonth;  } else { mwezi=smonth+"_to_"+emonth; }
                
                //The suffix should be facility, year, firstmonth_lastmonth

            String np = "";
            String zippath="";
            if (OSValidator.isWindows()) 
            {
                np = mydrive + ":\\HSDSA\\InternalSystem\\F1a\\Templates\\KP_MONTHLY_" + dat2 +generateRandomNumber(100, 2000)+ ".xlsx";
                 if(curworkbook==1){//if the workbooks are more than 1
                 zippath = mydrive + ":\\HSDSA\\InternalSystem\\F1a\\Templates\\KP_MONTHLY_Zip_" + county.replace(" ", "_")+"_"+year +"_"+mwezi+ ".zip";
                                   }
            }
            else if (OSValidator.isUnix()) 
            {
                np = "/HSDSA/InternalSystem/F1a/Templates/KP_MONTHLY_" + dat2+generateRandomNumber(100, 2000) + ".xlsx";
                if(curworkbook==1){ //if the workbooks are more than 1
                 zippath = "/HSDSA/InternalSystem/F1a/Templates/KP_MONTHLY_Zip_" + county.replace(" ", "_")+"_"+year+"_"+mwezi+ ".zip";
                                  }
                
            }
            
           todeletefiles+=np+"@"; 
            
            
            
            
           
            String sr = getServletContext().getRealPath("/KP_F1.xlsx");
            //check if file exists

            //first time , it should create those folders that host the macro file
            File f = new File(np);
            if (!f.exists() && !f.isDirectory()) 
            {
                /* do something */
                copytemplates ct = new copytemplates();
                ct.transfermacros(sr, np);
                //rem np is the destination file name  

                System.out.println("Copying kp template..");

            } 
            
            else //copy the file alone  
            {
                copytemplates ct = new copytemplates();

                ct.copymacros(sr, np);

            }
                
            System.out.println("New Path "+np);
                
            String filepth = np;

            File allpathfile = new File(filepth);

            OPCPackage pkg = OPCPackage.open(allpathfile);

           
            wb = new XSSFWorkbook(pkg);
            
            
            
   //_______________________________________________________________         
            

                curworkbook++;
                
      

                for (int a = 0; a < 1; a++) {

                    //each workbook here should have all the months 
                    //loop through the months
                    //if the user has selected the county alone,then get data for all the facilities in the county
                    
                    int mwaka=0;
                    
                    month = monthar;
                    
                    if(month.length()==1){month="0"+month;}
                    
                    if(new Integer(month)>=10){
                        
                    mwaka=new Integer(year)-1;
                    
                    }
                    else{
                    mwaka=new Integer(year);
                    }
                    
                    System.out.println("Mwaka ni "+mwaka);
                    
                    if(a<1)
                    {
                   // wb.cloneSheet(1);
                    }
                    //System.out.println("no of sheets:"+wb.getNumberOfSheets());
                    XSSFSheet shet=wb.getSheetAt(1);
                    wb.setSheetName(a+1, monthName(monthar));
                    
                    //in here, creata sheets    
                    XSSFRow rw = shet.getRow(0);
                    
                    XSSFCell facilcl= rw.getCell(1);
                    facilcl.setCellValue(dicname);
                    
                    XSSFCell mflcl= rw.getCell(5);
                    mflcl.setCellValue(diccode);
                    
                    
                    XSSFCell distcl= rw.getCell(10);
                    distcl.setCellValue(subcountyname);
                    
                    
                    XSSFCell countycl= rw.getCell(19);
                    countycl.setCellValue(county);
                    
                    
                    XSSFCell monthcl= rw.getCell(24);
                    monthcl.setCellValue(month);
                    
                    
                    XSSFCell yearcl= rw.getCell(26);
                    yearcl.setCellValue(mwaka);
                    
                    
                    

                }
                //outside here, create workbooks
                
                  npt = "";
               
                  
                  workbookidentifier=dicname+"_"+year +"_"+mwezi;
                  
            if (OSValidator.isWindows()) 
            {
            npt = mydrive + ":\\HSDSA\\InternalSystem\\F1a\\Templates\\KP_MONTHLY_" + workbookidentifier.replace(" ", "_")+ ".xlsx";
            }
            else if (OSValidator.isUnix()) 
            {
            npt = "/HSDSA/InternalSystem/F1a/Templates/KP_MONTHLY_" + workbookidentifier.replace(" ", "_")+ ".xlsx";
            }
                

  //if this is not the last workbook name, then           
if(curworkbook<countworkbooks)
{
 //workbooknames+=facilityname.replace(" ", "_")+"_"+year+"_"+mwezi+ ".xlsx"+"@";
 workbooknames+=npt+"@";
}
else 
{  
 //workbooknames+=facilityname.replace(" ", "_")+"_"+year+"_"+mwezi+ ".xlsx";
 workbooknames+=npt;
 
}



            String pathtodelete = npt;
            
            //pkg.close();
     
            if(countworkbooks==1){
            
ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
wb.write(outByteStream);
byte[] outArray = outByteStream.toByteArray();
response.setContentType("application/ms-excel");
response.setContentLength(outArray.length);
response.setHeader("Expires:", "0"); // eliminates browser caching
response.setHeader("Content-Disposition", "attachment; filename=" + "KP_MONTHLY_"+workbookidentifier.replace(" ", "_") + ".xlsx");
response.setHeader("Set-Cookie","fileDownload=true; path=/");
OutputStream outStream = response.getOutputStream();
outStream.write(outArray);
outStream.flush();
 pkg.close();
             System.out.println("download workbooks:"+workbooknames);  
             
             File file= new File(pathtodelete);
        
        if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
             
            }
            else 
            {
                
            //write each excel file in the local drive
           
            FileOutputStream outputStream = new FileOutputStream(npt);
            wb.write(outputStream);
            outputStream.flush();
              
            //pkg.close();
             
            //System.out.println("Create workbook file:"+workbooknames);
            
            }
            
            if(curworkbook==countworkbooks && countworkbooks>1 ){
            
            //write the zip
            
                System.out.println(" Last workbook "+workbooknames);
            
            String files[]=workbooknames.split("@");
            

               
            
            
       String filename=county.replace(" ", "_")+"County_"+year +"_"+mwezi;
       
         

byte[] outArray = zipFiles(files);
response.setContentType("application/zip");
response.setContentLength(outArray.length);
response.setHeader("Expires:", "0"); // eliminates browser caching
response.setHeader("Content-Disposition", "attachment; filename=KP_MONTHLY__"+filename+".zip");
response.setHeader("Set-Cookie","fileDownload=true; path=/");
OutputStream outStream = response.getOutputStream();
outStream.write(outArray);
outStream.flush();
 pkg.close();
 
 
                        for (String file1 : files) {
                            File file = new File(file1);
                            if (!file1.equals("")) {
                                if (file.delete()) {
                                    System.out.println(file.getName() + " is deleted!");
                                } else {
                                    System.out.println(file.getName() +" Delete operation failed.");
                                }
                            }       }  
                        
                        
                        
                         System.out.println("To delete "+todeletefiles);
            String filescopy[]=todeletefiles.split("@");
                        for (String filescopy1 : filescopy) {
                            if (!filescopy1.equals("")) {
                                File file = new File(filescopy1);
                                if(file.delete()){
                                    System.out.println(file.getName() + "  deleted!");
                                }else{
                                    System.out.println("Delete operation  failed.");
                                }           }
                        }
                        
                        
            
            
            }//end of if it is the last workbook
            
            
            
            
            
            }//end of while loop
            
            
            
            
            

        } finally {
          // out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (InvalidFormatException ex) {
                Logger.getLogger(getKPForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(getKPForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (InvalidFormatException ex) {
                Logger.getLogger(getKPForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(getKPForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    public String  monthName(String monthno){
        String mn="";
    if(monthno.equals("1")){
    mn="Jan";
    }
    else if(monthno.equals("2")){
    mn="Feb";
    }
    else if(monthno.equals("3")){
    mn="Mar";
    }
     else if(monthno.equals("4")){
    mn="Apr";
    }   
    else if(monthno.equals("5")){
    mn="May";
    }  
else if(monthno.equals("6")){
    mn="Jun";
    } 
    
    else if(monthno.equals("7")){
    mn="Jul";
    } 
     else if(monthno.equals("8")){
    mn="Aug";
    } 
    
    else if(monthno.equals("9")){
    mn="Sep";
    }
    else if(monthno.equals("10")){
    mn="Oct";
    }
    else if(monthno.equals("11")){
    mn="Nov";
    }
    else if(monthno.equals("12")){
    mn="Dec";
    }
    return mn;
    }
    
    
    
    
    
    // private byte[] zipFiles(File directory, String[] files) throws IOException {
     private byte[] zipFiles(String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];

        for (String fileName : files) 
        {
            //FileInputStream fis = new FileInputStream(directory.getPath() + downloadTemplate.FILE_SEPARATOR + fileName);
            File srcFile = new File(fileName);
 
                //FileInputStream fis = new FileInputStream(srcFile);
            
            FileInputStream fis = new FileInputStream(srcFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(srcFile.getName()));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) 
            {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }
    
      public int generateRandomNumber(int start, int end ){
        Random random = new Random();
        long fraction = (long) ((end - start + 1 ) * random.nextDouble());
        return ((int)(fraction + start));
    }
      
      
    
}
