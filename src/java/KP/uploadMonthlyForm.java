/*
Notes: This raw data is for positive EID. The data doesnt have age and sex
Age and sex should be gotten from the eid tested raw data during the importing of the raw data positives into the eid_datim_output table.

 */
package KP;


import General.IdGenerator;
import db.dbConn;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 20, // 20 MB 
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100)

 /**
 *
 @author Emmanuel Kaunda
 */
 /*
 (1)
 **/
public class uploadMonthlyForm extends HttpServlet {

 

    HttpSession session;
    private static final long serialVersionUID = 205242440643911308L;
    private static final String UPLOAD_DIR = "kp_uploads";
   
    String Poirowname;    

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

             String fullname = " Unknown User",email="";
        
    
        Poirowname="poi_row_no";
        
        
        
        try {
            
            
            
             String nextpage = "";
   
    
    String user_id = "";
    String periods,mfl_codes;
    int no_uploads;
    String failed_reason;
            
             String facilityName = null, facilityID, id, county, subcounty;
            
             String full_path = "";
             String fileName = "";
             String partName = "";
             String fileNameCopy = "";
            
            periods=mfl_codes=failed_reason="";
            no_uploads=0;
            String sessionText = "";
            
            int year = 0, added = 0, updated = 0, islocked = 0;
            String subpartnerid = "", yearmonth = "", month = "";
            
            String uploadstatus="";
            String lastexcelid="250";
            
            session = request.getSession();
            if (session.getAttribute("userid") != null) {
                user_id = session.getAttribute("userid").toString();
            }
            
            added = 0;
            updated = 0;
            
            fileNameCopy = "";
            fileName = "";
            
            File file_source;
            
            int mailstosent=0;
            
            HashMap<String, String> maildetails= new HashMap<String, String>();
            
            
            
            id = "";
            String indicator = "";
            String indicatorid = "";
            
            String todeleteymf="";//to be passed to the kp_temp deleting method
            String totransferymf="";//to be passed to the transfer method for moving data from fas temp to the respective table
            
            String unique_ym="";//to be passed to validation method
            String unique_subpartner=","; //to be passed to the validation method
            
            String unimporteddata = "";
            String updateddata = "";
            String newdaata = "";
            
            ArrayList msgal = new ArrayList();
            
            int startcol = 3;
            int endcol = 27;
            
            String mflcode = "";
             dbConn conn = new dbConn();
             
             
       
            String activeversion = "Form 1A  version 1.1.0";
         
            
            String dbname = "kp_temp";
            
           session.setAttribute("kpform1a", "<b>Checking F1a Version</b>");
        session.setAttribute("kpform1a_count", 1);  
            
            HashMap<String,String> uploaderdetails=getUsers(conn, user_id); 
            
            
              fullname = uploaderdetails.get("name");
                    email =  uploaderdetails.get("email");
            
            //GET ALLOWED PERIOD AND FACILITIES
            String getinfo = "SELECT IFNULL(periods,'') AS periods,IFNULL(mfl_codes,'') AS mfl_codes FROM fas_allowed_excel_uploads";
            conn.rs = conn.st.executeQuery(getinfo);
            if(conn.rs.next())
            {
              periods = conn.rs.getString("periods");
              mfl_codes = conn.rs.getString("mfl_codes");
            }
             
            nextpage = "kp_main.jsp";
            String excelfilename = "";
            
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
            session = request.getSession();
            
            File fileSaveDir = new File(uploadFilePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            
            
            added = 0;
            updated = 0;
            for (Part part : request.getParts()) {
              System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
              
              partName=getFileName(part);
              
                if (!partName.equals("")) {
                    
                    fileName = partName;
                    
                    fileNameCopy += fileName + ",";
                    part.write(uploadFilePath + File.separator + fileName);
                    
                    if (!fileName.endsWith(".xlsx")) {
                        
                        nextpage = "kp_main.jsp";
                        sessionText = "<font color=\"red\">Failed to load a .xls excel file. Please open the file, go to file> options > save as , then save as .xlsx </font>";
                    }
                    
                }
                //}
                
                if (!fileName.endsWith(".xlsx")) {
                    failed_reason+= "Wrong File Uploaded. We only allow upload of the template you downloaded.<br>";
                    nextpage = "kp_main.jsp";
                } else {
                    
                    //start reading the contents
                    try {
                        boolean issentexcel = false;
                        uploadstatus="";
                        excelfilename = fileName;
                        //System.out.println("Tunaanza");
                        full_path = fileSaveDir.getAbsolutePath() + "/" + fileName; //end of checking if excel file is valid
                        System.out.println("the saved file directory is  :  " + full_path);
                        session.setAttribute("kpform1a", "<b>Uploading F1a File</b>");
                        session.setAttribute("kpform1a_count", 20);
                        
                        FileInputStream fileInputStream = new FileInputStream(full_path);
                        BufferedInputStream bfs = new BufferedInputStream(fileInputStream);
                        XSSFWorkbook workbook = new XSSFWorkbook(bfs);
                        int rowCount=245;
                        
                        String rn="select count(id) from kp_indicators where is_active=1 and dataset='kp_monthly'";
                        
                        conn.rs=conn.st.executeQuery(rn);
                        
                        if(conn.rs.next()){
                        rowCount=conn.rs.getInt(1);
                        }
                        
                        int totalsheets = workbook.getNumberOfSheets();
                        
                        for (int sheetno = 0; sheetno < totalsheets; sheetno++) {
                            
                            XSSFSheet worksheet = workbook.getSheetAt(sheetno);
                            
                            System.out.println(sheetno + " (" + workbook.getSheetName(sheetno) + ") out of " + totalsheets + " sheets");
                            
                            String sheetname = workbook.getSheetName(sheetno);
                            
                            boolean hasdata = false;
                      
                        
                          int tukowapi=6;
                            
//skip instructions page
if (!sheetname.equals("Instructions")) {
 
    String indexcellstarting="";
    //get basic period and orgunit details
   
     Poirowname="poi_row_no";   
   
    
   
    
    System.out.println("Poi row cell ni "+Poirowname);
    
    //get basic period and orgunit details
    XSSFCell facilcell = worksheet.getRow(0).getCell((short) 1);
    
    if (facilcell.getCellType() == 1) {
        facilityName = facilcell.getStringCellValue();
    }
    
    XSSFCell mflcell = worksheet.getRow(0).getCell((short) 5);
    
    if (mflcell.getCellType() == 0) {
        mflcode = "" + (int) mflcell.getNumericCellValue();
    } else if (mflcell.getCellType() == 1) {
        mflcode = mflcell.getStringCellValue();
    }
    
    XSSFCell subcountycell = worksheet.getRow(0).getCell((short) 10);
    if (subcountycell.getCellType() == 1) {
        subcounty = subcountycell.getStringCellValue();
    }
    
    XSSFCell countycell = worksheet.getRow(0).getCell((short) 19);
    if (countycell.getCellType() == 1) {
        county = countycell.getStringCellValue();
    }
    
    XSSFCell monthcell = worksheet.getRow(0).getCell((short) 24);
    
    if (monthcell.getCellType() == 0) {
        month = "" + (int) monthcell.getNumericCellValue();
    } else if (monthcell.getCellType() == 1) {
        month = monthcell.getStringCellValue();
    }
    
    XSSFCell yearcell = worksheet.getRow(0).getCell((short) 26);
    
    if (yearcell.getCellType() == 0) {
        year = (int) yearcell.getNumericCellValue();
    } else if (yearcell.getCellType() == 1) {
        year = new Integer(yearcell.getStringCellValue());
    }
    
    yearmonth = year + "" + month;
    
    if(periods.contains(","+yearmonth+",") && ((mfl_codes.equals("") ||(!mfl_codes.equals("") && mfl_codes.contains(","+mflcode+","))))){
    no_uploads++;
    //______________________CHECK EXCEL TEMPLATE  VERSION___________________
    String excelversion = "";
    
    XSSFCell versioncell = worksheet.getRow(3).getCell((short) 0);
    
    if (versioncell.getCellType() == 0) {
        //numeric
        excelversion = "" + (int) versioncell.getNumericCellValue();
    } else if (versioncell.getCellType() == 1) {
        excelversion = versioncell.getStringCellValue();
    }
    //System.out.println(excelversion + " vs " + activeversion);
    if (excelversion.equals(activeversion)) {
        
        boolean hasexcecuted = false;
        int uploadlocked = 0;
        
//_______version row________________

ArrayList colskey = new ArrayList();

colskey.add("m_1");
colskey.add("f_1");
colskey.add("m_4");
colskey.add("f_4");
colskey.add("m_9");
colskey.add("f_9");
colskey.add("m_14");
colskey.add("f_14");
colskey.add("m_19");
colskey.add("f_19");
colskey.add("m_24");
colskey.add("f_24");
colskey.add("m_29");
colskey.add("f_29");
colskey.add("m_34");
colskey.add("f_34");
colskey.add("m_39");
colskey.add("f_39");
colskey.add("m_44");
colskey.add("f_44");
colskey.add("m_49");
colskey.add("f_49");
colskey.add("m_50");
colskey.add("f_50");
colskey.add("total");

//____________________Supported Areas per Facility and SubpartnerID____________________
String supported_services = " WHERE (is_active=1 ) && ("+Poirowname+" is not null )  ";

String support_column_name, support_column_value;
int num_serv_supported = 0;
// READ FACILITY SUPPORTED SERVICES
String get_supported_service = "SELECT SubPartnerID, IFNULL(PMTCT,0) AS PMTCT,IFNULL(ART,0) AS ART,IFNULL(VMMC,0) AS VMMC,IFNULL(HTC,0) AS HTC,IFNULL(Gender,0) AS Gender,IFNULL(PNS,0) AS PNS, IFNULL(IPD,0) AS IPD FROM subpartnera WHERE CentresanteID='" + mflcode + "'";
System.out.println("" + get_supported_service);
conn.rs = conn.st.executeQuery(get_supported_service);
ResultSetMetaData metaData = conn.rs.getMetaData();
int col_count = metaData.getColumnCount(); //number of column
if (conn.rs.next()) {
    supported_services += " AND (";
    for (int i = 1; i <= col_count; i++) {
        support_column_name = metaData.getColumnLabel(i);
        support_column_value = conn.rs.getString(support_column_name);
        if (i == 1) {
            //first column is the subpartner ID
            subpartnerid = support_column_value;
        } else {
            
            //if the facility supports atleast one service
            if (support_column_value.equals("1")) {
                num_serv_supported++;
                supported_services += support_column_name + "=" + support_column_value + " OR ";
            }
        }
    }
    
    if (num_serv_supported > 0) {
        supported_services = removeLast(supported_services, 3) + ")";
    }
}
//System.out.println("Tuko wapi new:"+tukowapi);
//System.out.println("Supported services : " + supported_services);

//___________________Read Active and Supported Indicators Only______________________
// --Here, we have already mapped each element/indicator's row no as per the excel upload module into an existing fas_indicators table
//--we will fetch a list of the indicators and the respective row no. then use the result set to tell us in which row of the uploaded excel template to get data for each indicator element.
//--Any time there is a row-wise change in the excel upload file(including insertoing a new row),
//there is need to update the column poi_row_no in the table fas_indicators accordingly
String table = "";
String code = "";
String indicator_name = "";
int poirow = 0;
ArrayList insertal=new ArrayList();
String getsections = "SELECT id,database_name,code,"+Poirowname+",concat('Uploaded: ',main_indicator,' , ',indicator) as indicator FROM fas_indicators " + supported_services + " and dataset='form1a' order by order_no ";

System.out.println("__"+getsections);
conn.rs2 = conn.st2.executeQuery(getsections);

while (conn.rs2.next()) {   
    
        
   //System.out.println("Tuko wapi new2:"+tukowapi);
   
    String insert = " Replace into " + dbname + " Set ";
    table = conn.rs2.getString("database_name");
    indicatorid = conn.rs2.getString("id");
    code = conn.rs2.getString("code");
    indicator_name = conn.rs2.getString("indicator");
    poirow = conn.rs2.getInt(Poirowname);
    //while inside this , now read each indicator from the respective table row
    
    try {
        
        XSSFRow rowi = worksheet.getRow(poirow);
        if (rowi == null) {
            System.out.println(" Row no not existing thus skipped for code " + code);
            break;
        }
        
        //put data data values into an excel file
         id = yearmonth + "_" + subpartnerid + "_" + indicatorid;
        insert += " id='" + id + "',facility_id='" + subpartnerid + "',indicator_id='" + indicatorid + "',yearmonth='" + yearmonth + "',";
        
        for (int d = 0; d < colskey.size(); d++) {
            
            String val = "";
            
            XSSFCell valcell = worksheet.getRow(poirow).getCell((short) d + startcol);
            //System.out.println("For indicator => "+indicatorid+", age=> "+colskey.get(d)+" => color : "+valcell.getCellStyle().getFillBackgroundColorColor());
            
            
            
            switch (valcell.getCellType()) {
                case 0:
                    val = "" + (int) valcell.getNumericCellValue(); //integer
                    hasdata = true;//if there is a typed numeric value then the excel should be uploaded
                    break;
                case 1:
                    val = valcell.getStringCellValue();//string
                    break;
                case 2:
                    val = "" + (int) valcell.getNumericCellValue(); //formula
                    
                    break;
                case 3:
                    val = valcell.getStringCellValue();//blank
                    break;
                default:
                    val = "" + (int) valcell.getNumericCellValue();
                    
                    break;
            }
           // System.out.println(indicator_name+" ni Value "+val+" na Ni cell type "+valcell.getCellType()+" na ni color "+valcell.getCellStyle().getFillBackgroundColorColor());
            //put the values into a hash table
            //for blanks, insert null instead of a blank
            if (valcell.getCellType() == 3) 
            {
                insert += colskey.get(d).toString() + "=null, ";
            } else {
            insert += colskey.get(d).toString() + "='" + val + "', ";
                   }
            
        }
        
        insert += " is_locked='0',user_id='" + user_id + "',user_pc='" + getComputerName() + "',destination_table='" + table + "'";
        
        //================================check if data for facility is locked========================================
        String getindicator = "SELECT is_locked FROM check_locked  where is_locked = '1' and id like '" + yearmonth + "_" + subpartnerid + "%' limit 1 ";
        // System.out.println(""+getindicator);
       
        //hasexcecuted variable checks if data for a specific yearmonth is locked on the original yearmonths 
        if (!hasexcecuted) {
           // conn.rs = conn.st.executeQuery(getindicator);
            //if (conn.rs.next()) 
            if (1==2) 
            {
                // System.out.println(" data upload locked ");
                hasexcecuted = true;
                //uploadlocked = conn.rs.getInt("is_locked");
                uploadlocked = 0;
                msgal.add("Data Upload for " + facilityName + " , year " + year + " and month " + month + " is already locked");
                String tx="Failed: Data Upload locked for excel sheet "+sheetname+" for  " + year + " ," + month+" \n ";
                if(!uploadstatus.contains(tx))
                {
                    uploadstatus+=tx;
                }
                
            } else {
                
            }
            
            
        }
        
        //if not locked, and has data , do entries
        if (uploadlocked != 1 && hasdata == true) {
            String ujumbe = "Data Upload for " + facilityName + " , year " + year + " and month " + month + " imported successfully";
            if (!msgal.contains(ujumbe)) {
                msgal.add(ujumbe);
            }
           // System.out.println("" + insert + ";");
            session.setAttribute("form1a", "<b>Saving into temp db "+tukowapi+"/"+rowCount+"</b>");
        session.setAttribute("form1a_count", (tukowapi*100)/rowCount);
        tukowapi++;
            if (conn.st4.executeUpdate(insert) >= 1) {
                
                
                if(!unique_ym.contains(yearmonth)){unique_ym+=yearmonth+",";}
                
                if(!unique_subpartner.contains(","+subpartnerid+",")){unique_subpartner+=subpartnerid+",";}
                
                
                String ymf = "'" + yearmonth + "_" + subpartnerid + "',";
                
                if (!todeleteymf.contains(ymf))
                {
                    todeleteymf += ymf;
                }
                
               
                
                //execute the waiting insert statements in the insert arraylist and reset the arraylist
                for(int g=0;g<insertal.size();g++)
                {
                    //System.out.println("Inserted at late stage "+insertal.get(g).toString());    
                conn.st4.executeUpdate(insertal.get(g).toString());
                if(g==insertal.size()-1)
                {//last row, reset 
                insertal.clear();
                }
                
                }
                
                //System.out.println("Data inserted into kp_temp:");
                
                //____________________________________AUDIT TRAIL______________________________________
                
                insertAuditTrail(conn, indicator_name, yearmonth, facilityName, id, user_id,fullname);
                
                //____________________________________AUDIT TRAIL______________________________________
                
            }
            else
            {
                
                String tx="Failed: Upload query failed in excel sheet "+sheetname+" for  " + year + " , " + month+" \n " ;
                if(!uploadstatus.contains(tx))
                {
                    uploadstatus+=tx;
                }
                
            }
            
        } 
        //if entry is not locked but so far for all the rows, no indication of a typed value
        else if (uploadlocked != 1 && hasdata == false) {
            
            //save insert statements and preserve them so that if a type data value is found on lower sections of the excel, then all the inserts can be done at once
            insertal.add(insert);
            
            //if this is the last row and no data typed so far, then conclude excel is blank
            
            
            
            if(indicatorid.equals(lastexcelid))
            {
            String tx="Failed: Blank excel sheet for  " + year + " , " + month+" \n " ;
            if(!uploadstatus.contains(tx))
            {
                uploadstatus+=tx;
            }
            
            String ujumbe = " Data Upload for " + facilityName + " , year " + year + " and month " + month + " Not Imported since no data in in excel";
            if (!msgal.contains(ujumbe)) {
                msgal.add(ujumbe);
            }
            }
            
        }
        
    } //end of try
    catch (SQLException ex) {
        Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
}  //end of while loop

    }//end of correct version
    else {
        no_uploads=0;
        failed_reason+= "Failed: You have used Wrong F1a template version "+excelversion+" . Expected Version is 4.0.4 <a href='uploadMonthlyFormv43.jsp'>Upload Version 4.0.3 here</href> <br>";

        String tx="Failed: You have used Wrong template version "+excelversion+" . Expected Version is 4.0.4. <a href='uploadMonthlyFormv43.jsp'>Upload Version 4.0.3 here</href> \n " ;
        if(!uploadstatus.contains(tx))
        {
            uploadstatus+=tx;
        }
        String ujumbe = "Note: Data for " + facilityName + " was uploaded using Wrong Templete version. Click here to <a class=\\\"btn btn-success\\\" href=\\\"gettemplate.jsp\\\">download new template\"";
        if (!msgal.contains(ujumbe)) 
        {
            msgal.add(ujumbe);
        }
        mailstosent++;
        
        maildetails.put("fac"+mailstosent, facilityName);
        maildetails.put("st"+mailstosent, uploadstatus);
        maildetails.put("fp"+mailstosent, full_path);
        maildetails.put("fn"+mailstosent, excelfilename);
        maildetails.put("fulln"+mailstosent, fullname);
        
        
    }
}
else{
  uploadstatus+="The period you are uploading for has been Locked/Blocked i.e Period: "+yearmonth+" and mflcode: "+mflcode+"\n";
  failed_reason+= "The period you are uploading for has been Locked/Blocked i.e <br><br>Period: "+yearmonth+" and mflcode: "+mflcode+"<br>";

  
                
                    fullname = uploaderdetails.get("name");
                    email =  uploaderdetails.get("email");
                    //System.out.println("email:"+email);
              
                
               
                
}
    
       
}

    
    //if upload has completed, then sent excel workbook via email
    if (sheetno == totalsheets-1 && issentexcel == false) 
    {
        mailstosent++;
        
        maildetails.put("fac"+mailstosent, facilityName);
        maildetails.put("st"+mailstosent, uploadstatus);
        maildetails.put("fp"+mailstosent, full_path);
        maildetails.put("fn"+mailstosent, excelfilename);
        maildetails.put("fulln"+mailstosent, fullname);
        
        System.out.println("F1 file upload for  : "+facilityName+" Status: "+uploadstatus);
        
        issentexcel = true;
        //SendF1excel(facilityName, uploadstatus , full_path, excelfilename, fullname);
    }
    
        


  }//end of worksheets loop
                        
        session.setAttribute("form1a", "<b>Data Saving complete</b>");
        session.setAttribute("form1a_count", 100);  
    
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }//end of multiple sheets for loop
            
            
            //call the validation method here
            //sample unique_subpartner ,20,234,
            System.out.println("unique ym: "+unique_ym);
            
            if(!unique_ym.equalsIgnoreCase("")){
            unique_ym=removeLast(unique_ym,1);
            unique_subpartner=removeLast(unique_subpartner,1).replaceFirst(",", "");
            }
            
            System.out.println(""+unique_ym);
            System.out.println(""+unique_subpartner);
            
            JSONObject obj;
            ValidateExcelSL vExcel = new ValidateExcelSL();
            obj = vExcel.validate(unique_subpartner.split(","), unique_ym.split(","),"kp_temp",request);
            
            int error_per_sheet,total_errors = 0,warnings;
            String warning;
            warning = "";
            
            
            if(obj.containsKey("output")){
                
              JSONArray jarray = (JSONArray)obj.get("output");
                
              
              for(int i=0;i<jarray.size();i++)
              {
               error_per_sheet =  vExcel.error_per_sheet((JSONObject)jarray.get(i));
               if(error_per_sheet==0){ // send this data to main tables facilityid, yearmonth
//transfer
      String facilid=((JSONObject)jarray.get(i)).get("facility_id").toString();
      String yearm=((JSONObject)jarray.get(i)).get("yearmonth").toString();
      
     
      
      if(i==jarray.size()-1)
      {
      //last row
      totransferymf+="'"+yearm+"_"+facilid+"'";
      
      transferdata(conn, totransferymf);
      
      /**
      pushDataToDHIS2 pd= new pushDataToDHIS2();      
      //System.out.println("to transfer ni: "+totransferymf);
     ArrayList cols=pd.getDistinctF1aColumns(conn,"Form 1 A");      
              
      ResultSet f1adata=pd.GetForm1aData(conn, yearm, facilid, cols);
       
      dhisconfig dc = new dhisconfig();
          
     //get username and password for DHIS2 here
     session.setAttribute("form1a", "<b>Uploading F1a Copy to ANYB DHIS2</b>");
     String dn =  uploaderdetails.get("dhis_username");
     String dp =  uploaderdetails.get("dhis_password");
     
            dc.setDhis2_username(dn);
            dc.setDhis2_Password(dp);
     
            org.json.JSONObject jo=pd.toJsonString(conn,f1adata, cols, dc.getDhis2_username());
            System.out.println("uploading to DHIS2");
            pd.UploadF1aToServer(jo,dc.getDhis2_username(),dc.getDhis2_Password() );            
       
           **/ 
      
      }
      else 
      {
      totransferymf+="'"+yearm+"_"+facilid+"',";
      }
       String tx = "Sucess: Uploaded excel for Facility "+getFacilityname(conn,facilid)+" " + yearm.substring(0, 4) + " , " + yearm.substring(4) + " \n ";
                   System.out.println(""+tx);
                if (!uploadstatus.contains(tx)) 
                {
                    uploadstatus += tx;
                }
                maildetails.put("st"+mailstosent, uploadstatus);
                 
                                     }
               else
               {// increment errors
                total_errors+=error_per_sheet;
                
                  String yearm=((JSONObject)jarray.get(i)).get("yearmonth").toString();
                 String facilid=((JSONObject)jarray.get(i)).get("facility_id").toString();
                 
                  String tx = "Failed: Failed Validation for Facility "+getFacilityname(conn,facilid)+" " + yearm.substring(0, 4) + " , " + yearm.substring(4) + " \n ";
                                  System.out.println(""+tx);
                  if (!uploadstatus.contains(tx)) 
                {
                    uploadstatus += tx;
                }
                   maildetails.put("st"+mailstosent, uploadstatus);
                   
               }
              }
          }
            
         
       for(int q=1;q<=mailstosent;q++){
                try {
                    session.setAttribute("form1a", "<b>sending F1a Copy to Server</b>");
        session.setAttribute("form1a_count", 99); 
                    //send to developers
                    SendF1excel(maildetails.get("fac"+q), maildetails.get("st"+q) , maildetails.get("fp"+q), maildetails.get("fn"+q), maildetails.get("fulln"+q),"aphiabackup@gmail.com,DJuma@fhi360.org,MNderitu@fhi360.org,Ekaunda@fhi360.org","Admin");
                    
                    //send to user
                    if(!email.equals(""))
                        session.setAttribute("form1a", "<b>sending F1a Copy to System user</b>");
        session.setAttribute("form1a_count", 99);
                    SendF1excel(maildetails.get("fac"+q), maildetails.get("st"+q) , maildetails.get("fp"+q), maildetails.get("fn"+q), maildetails.get("fulln"+q),email,maildetails.get("fulln"+q));
                   session.setAttribute("form1a", "<b>Completed upload process</b>");
        session.setAttribute("form1a_count", 100); 
                } catch (MessagingException ex) {
                    Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
                }
       }
            
        session.removeAttribute("form1a");
        session.removeAttribute("form1a_count");
            
            System.out.println("uploaded : "+no_uploads);
             
          if(total_errors==0 && no_uploads>0){
          warning = vExcel.gel_all_warnings(obj);
          System.out.println(warning);
          session.setAttribute("warnings", warning);
          session.setAttribute("message", " <img src=\"images/uploaded.png\"> <b id=\"notify\"></b> ");
          
          
          
          response.sendRedirect("kp_main.jsp");
          }
          
          else if(no_uploads==0){
          session.setAttribute("warnings", "");
          session.setAttribute("message", " <img src=\"images/failed.png\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b id=\"notify\">ERROR: "+failed_reason+"</b> ");
          response.sendRedirect("kp_main.jsp"); 
          }
          
          else if(total_errors>0){
          session.removeAttribute("warnings");
          session.removeAttribute("message");
          
          session.setAttribute("ref_form1a","yes");
        
                        
            XSSFWorkbook wb1;
             wb1 = vExcel.generateExcel(obj); // to generate Excel file
//          out.println(obj);
    ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
    wb1.write(outByteStream);
    byte [] outArray = outByteStream.toByteArray();
    response.setContentType("application/ms-excel");
    response.setContentLength(outArray.length);
    response.setHeader("Expires:", "0"); // eliminates browser caching
    response.setHeader("Set-Cookie:", "fileDownload=true; path=/"); // set cookie header
    response.setHeader("Content-Disposition", "attachment; filename=Data_Quality_Errors.xlsx");
    OutputStream outStream = response.getOutputStream();
    outStream.write(outArray);
    outStream.flush();
     
          } 
          else{
              
          }
           
          //call delete facilities from kp_temp
                    
           // System.out.println("to delete : "+todeleteymf);
            if(!todeleteymf.equalsIgnoreCase("")){
             deletefacilities(conn, removeLast(todeleteymf,1));
            }
                   
                   
                   //send mail
                   
            
            
            try {
                if (conn.rs != null) {conn.rs.close();}
                if (conn.st != null) {conn.st.close();}
                if (conn.rs1 != null) {conn.rs1.close();}
                if (conn.st2 != null) {conn.st2.close();}
                if (conn.rs3 != null) {conn.rs3.close();}
                if (conn.st3 != null) {conn.st3.close();}
                if (conn.rs4 != null) {conn.rs4.close();}
                if (conn.st4 != null) {conn.st4.close();}
                if (conn.rs_1 != null) {conn.rs_1.close();}
                if (conn.st_1 != null) {conn.st_1.close();}
                if (conn.rs_2 != null) {conn.rs_2.close();}
                if (conn.st_2 != null) {conn.st_2.close();}
                if (conn.pst != null) {conn.pst.close();}
                if (conn.connect != null) {conn.connect.close();}
                
                
                
            } catch (SQLException ex) {
                Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // pullHTS hts= new pullHTS();
            // hts.hts_pns(yearmonth, yearmonth, "");
            
           // response.sendRedirect(nextpage);
            
        } catch (SQLException ex) {
            Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        String file_name = "";
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= " + contentDisp);
        String[] tokens = contentDisp.split(";");

        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                file_name = token.substring(token.indexOf("=") + 2, token.length() - 1);
                System.out.println("content-disposition final : " + file_name);
                
                break;
            }
            
        }
        
        return file_name;
    }

    public Workbook ReadExcel(String excelpath) throws IOException, InvalidFormatException {
        Workbook wb = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelpath));
            wb = WorkbookFactory.create(inputStream);
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wb;
    }

    public static String removeLast(String str, int num) {
        return str.substring(0, str.length() - num);
    }

    private String getComputerName() 
    {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
            return env.get("COMPUTERNAME");
        } else if (env.containsKey("HOSTNAME")) {
            return env.get("HOSTNAME");
        } else {
            return "Unknown Computer";
        }
    }

    private void SendF1excel(String facility, String status, String path, String finalfilename, String uploadername,String email,String username) throws MessagingException {
        String toAddress = "";

        String filenames = path;
        String stat="";
        
        if(!status.equals("")){stat=" Below are the upload results:\n "+status;}

        IdGenerator gn = new IdGenerator();

        String textBody = "Hi "+username+",\nAttached is a Form 1A data upload for " + facility + " uploaded by " + uploadername + " on date " + gn.toDay() + " .\n"
                + "\n "+stat+" \n *******This is a system autogenerated message*****";
        toAddress = email;
        String host = "smtp.gmail.com";
        String Password = "plusaphia";
        String from = "aphiabackup@gmail.com";
        // toAddress = "aphiapluschwsattendance@gmail.com";  filled above...
        String filename = filenames;
        //Get system properties
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        message.setRecipients(Message.RecipientType.TO, toAddress);

        message.setSubject(facility + " F1A data Upload by " + uploadername);

        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setText(textBody);

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(filename);

        messageBodyPart.setDataHandler(new DataHandler(source));

        //messageBodyPart.setFileName("Form1a_"+facility+"_"+yearmonth+".xlsx");
        messageBodyPart.setFileName(finalfilename);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        try {
            javax.mail.Transport tr = session.getTransport("smtps");
            tr.connect(host, from, Password);
            tr.sendMessage(message, message.getAllRecipients());

            tr.close();

        } catch (SendFailedException sfe) {

            System.out.println(sfe);
        }
    }

    public boolean transferdata(dbConn conn, String yearmonth_subpartnerid) 
    {
boolean retvalue=true;
        try {
            //sample yearmonth_subpartnerid   '201901_226','201902_226','201903_226'
            
            String qry = "select * from kp_temp where concat(yearmonth,'_',facility_id) in (" + yearmonth_subpartnerid + ") group by destination_table";
            conn.rs3 = conn.st3.executeQuery(qry);
            
            String destinationtable = "";
            
            String colstomigrate = "";
            String replaceqry = "";
            
            ResultSetMetaData metaData = conn.rs3.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            metaData = conn.rs3.getMetaData();
            columnCount = metaData.getColumnCount();
            int count = 0;
            
            while (conn.rs3.next()) {
                
                destinationtable = conn.rs3.getString("destination_table");
                
                if (count == 0)
                {
                    
                    //______Note, for the below qry to work, the last column must contain the destination table__
                    for (int i = 1; i <= columnCount; i++) {
                        //in the the last column
                        if (i == columnCount) {
                            
                        } else if (i == columnCount - 1) {
                            colstomigrate += " " + metaData.getColumnLabel(i) + " ";
                        } else {
                            colstomigrate += " " + metaData.getColumnLabel(i) + ", ";
                        }
                        
                    }//end of for loop
                    count++;
                }//end of if
                //data rows
                // we are only geting the distinct cols
// sample qry REPLACE fas_hts SELECT `id`,`facility_id`,`indicator_id`,`yearmonth`,`m_uk`,`f_uk`,`m_1`,`f_1`,`m_4`,`f_4`,`m_9`,`f_9`,`m_14`,`f_14`,`m_19`,`f_19`,`m_24`,`f_24`,`m_29`,`f_29`,`m_34`,`f_34`,`m_39`,`f_39`,`m_44`,`f_44`,`m_49`,`f_49`,`m_50`,`f_50`,`f_total`,`m_total`,`total`,`is_locked`,`user_id`,`user_pc`,`timestamp` FROM kp_temp where destination_table='fas_hts';

//delete the existing data first

String deleteqry=" delete from "+destinationtable+" where concat(yearmonth,'_',facility_id) in (" + yearmonth_subpartnerid + ")";

               // System.out.println(" To delete column: "+deleteqry);

                conn.st_1.executeUpdate(deleteqry);
                
            String skipblanks=" and concat_ws(',',m_uk,f_uk,m_1,f_1,m_4,f_4,m_9,f_9,m_14,f_14,m_19,f_19,m_24,f_24,m_29,f_29,m_34,f_34,m_39,f_39,m_44,f_44,m_49,f_49,m_50,f_50,total) !='0' && concat_ws(',',m_uk,f_uk,m_1,f_1,m_4,f_4,m_9,f_9,m_14,f_14,m_19,f_19,m_24,f_24,m_29,f_29,m_34,f_34,m_39,f_39,m_44,f_44,m_49,f_49,m_50,f_50,total) !='' ";    
replaceqry = "Replace  " + destinationtable + " select " + colstomigrate + " from kp_temp where destination_table='" + destinationtable + "' and concat(yearmonth,'_',facility_id) in (" + yearmonth_subpartnerid + ")  "+skipblanks+" ";
//System.out.println(""+replaceqry);
conn.st_1.executeUpdate(replaceqry);
count++;

            }
            
           
       } catch (SQLException ex) {
            Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
            retvalue=false;
        }
         return retvalue;
    }
    
     public boolean deletefacilities(dbConn conn, String yearmonth_subpartnerid)  
     {
boolean iscomplete=true;
        
        try {
            //sample yearmonth_subpartnerid   '201901_226','201902_226','201903_226'
            
            //delete the updated data
            //select * from kp_temp where concat(yearmonth,'_',facility_id) in ('201901_226','201901_377','201902_377','201902_226','201903_226')
            String deleteqry = "delete from kp_temp where concat(yearmonth,'_',facility_id) in ("+yearmonth_subpartnerid+") ";
            conn.st_1.executeUpdate(deleteqry);
        } catch (SQLException ex) 
        {
            iscomplete=false;
            Logger.getLogger(uploadMonthlyForm.class.getName()).log(Level.SEVERE, null, ex);
        }
      return iscomplete;
     }
     
     
     public void insertAuditTrail( dbConn conn,String indicator_name,String yearmonth, String facname, String id, String userid , String fullname) throws SQLException{
              
                
                
                // update the audit trails table with relevant information
                String insert_audit_trails = "INSERT INTO fas_audit_trails (entry_id,table_name,fullname,facility,indicator,yearmonth,user_pc) VALUES(?,?,?,?,?,?,?)";
                conn.pst = conn.connect.prepareStatement(insert_audit_trails);
                conn.pst.setString(1, id);
                conn.pst.setString(2, "kp_temp");
                conn.pst.setString(3, fullname);
                conn.pst.setString(4, facname);
                conn.pst.setString(5, indicator_name);
                conn.pst.setString(6, yearmonth);
                conn.pst.setString(7, getComputerName());
                
                conn.pst.executeUpdate();
                
                //____________________________________END AUDIT TRAIL______________________________________
     
     }
     
     public String getFacilityname(dbConn conn, String facilityid) throws SQLException{
     String facility="unkown";
     
     conn.rs_6=conn.st_6.executeQuery("select subpartnernom from subpartnera where subpartnerid='"+facilityid+"'");
     
     while(conn.rs_6.next())
     {
     facility=conn.rs_6.getString(1);
     }
     
     return facility;
     
     }
     
     
     
     public HashMap<String,String> getUsers(dbConn conn, String user_id) throws SQLException{
     
         String fname="";
         String mail="";
         
         String duname="";
         String dpword="";
         
         
     String getusername = "SELECT fname,lname,IFNULL(email,'aphiabackup@gmail.com') AS email , dhis2_uname, dhis2_pword FROM user WHERE userid='" + user_id + "'";
                conn.rs1 = conn.st1.executeQuery(getusername);
                if (conn.rs1.next())
                {
                    fname = conn.rs1.getString(1) + " " + conn.rs1.getString(2);
                    mail = conn.rs1.getString(3);
                    duname = conn.rs1.getString(4);
                    dpword = conn.rs1.getString(5);
                    //System.out.println("email:"+email);
                }
                
                HashMap<String,String> map= new HashMap<String, String>();
     String[] details={fname,mail};
     
     map.put("email", mail);
     map.put("name", fname);
     map.put("dhis_username", duname);
     map.put("dhis_password", dpword);
                
                return map;
     }
     
}
