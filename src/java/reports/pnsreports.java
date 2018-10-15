/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;


import General.IdGenerator;



import db.dbConn;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import static scripts.OSValidator.isUnix;
import scripts.copytemplates;

/**
 *
 * @author EKaunda
 */
public class pnsreports extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            //PrintWriter out = response.getWriter();

            /* TODO output your page here. You may use following sample code. */
//______________________________________________________________________________________
//                       CREATE THE WORKSHEETS
//______________________________________________________________________________________




String allpath = getServletContext().getRealPath("/pnstemplate.xlsx");

XSSFWorkbook wb1;
 
String pathtodelete=null;

Date da= new Date();
String dat2 = da.toString().replace(" ", "_");

dat2 = dat2.toString().replace(":", "_");

String mydrive = allpath.substring(0, 1);

String np=mydrive+":\\HSDSA\\PNS\\MACROS\\";

String filepath="PNS_REPORT"+dat2+".xlsx";


if(isUnix()){
    np="/HSDSA/PNS/MACROS/";
}


 new File(np).mkdirs();

 np+=filepath;
 
//check if file exists
String sourcepath = getServletContext().getRealPath("/pnstemplate.xlsx");

File f = new File(np);
if(!f.exists()&& !f.isFile() ) {
    /* do something */
    
    copytemplates ct= new copytemplates();
    System.out.println("Copying macros..");
    ct.transfermacros(sourcepath,np);
    
}
else
    //copy the file alone  
{
    
    copytemplates ct= new copytemplates();
//copy the agebased file only
ct.copymacros(sourcepath,np);

}

            System.out.println("Copying done..");


File allpathfile= new File(np);

OPCPackage pkg = OPCPackage.open(allpathfile);

pathtodelete=np;


//wb = new XSSFWorkbook( OPCPackage.open(allpath) );
wb1 = new XSSFWorkbook(pkg);


//SXSSFWorkbook wb = new SXSSFWorkbook(wb1, 100);





XSSFWorkbook wb = wb1;










XSSFFont font = wb.createFont();
font.setFontHeightInPoints((short) 18);
font.setFontName("Cambria");
font.setColor((short) 0000);
CellStyle style = wb.createCellStyle();
style.setFont(font);
style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
XSSFFont font2 = wb.createFont();
font2.setFontName("Cambria");
font2.setColor((short) 0000);

CellStyle style2 = wb.createCellStyle();
style2.setFont(font2);
style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
style2.setAlignment(XSSFCellStyle.ALIGN_LEFT);



CellStyle stylec = wb.createCellStyle();
stylec.setFont(font2);
stylec.setBorderTop(XSSFCellStyle.BORDER_THIN);
stylec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
stylec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
stylec.setBorderRight(XSSFCellStyle.BORDER_THIN);
stylec.setAlignment(XSSFCellStyle.ALIGN_LEFT);


XSSFCellStyle stborder = wb.createCellStyle();
stborder.setBorderTop(XSSFCellStyle.BORDER_THIN);
stborder.setBorderBottom(XSSFCellStyle.BORDER_THIN);
stborder.setBorderLeft(XSSFCellStyle.BORDER_THIN);
stborder.setBorderRight(XSSFCellStyle.BORDER_THIN);
stborder.setAlignment(XSSFCellStyle.ALIGN_CENTER);

XSSFCellStyle stylex = wb.createCellStyle();
stylex.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
stylex.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
stylex.setBorderTop(XSSFCellStyle.BORDER_THIN);
stylex.setBorderBottom(XSSFCellStyle.BORDER_THIN);
stylex.setBorderLeft(XSSFCellStyle.BORDER_THIN);
stylex.setBorderRight(XSSFCellStyle.BORDER_THIN);
stylex.setAlignment(XSSFCellStyle.ALIGN_LEFT);

XSSFCellStyle stylesum = wb.createCellStyle();
stylesum.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
stylesum.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
stylesum.setBorderTop(XSSFCellStyle.BORDER_THIN);
stylesum.setBorderBottom(XSSFCellStyle.BORDER_THIN);
stylesum.setBorderLeft(XSSFCellStyle.BORDER_THIN);
stylesum.setBorderRight(XSSFCellStyle.BORDER_THIN);
stylesum.setAlignment(XSSFCellStyle.ALIGN_CENTER);

XSSFFont fontx = wb.createFont();
fontx.setColor(HSSFColor.BLACK.index);
fontx.setFontName("Cambria");
stylex.setFont(fontx);
stylex.setWrapText(true);

stylesum.setFont(fontx);
stylesum.setWrapText(true);

XSSFSheet rawsheet = wb.getSheet("PNS Raw Data");
XSSFSheet monthlysheet = wb.createSheet("PNS Monthly Data");









XSSFSheet Sheetnames[]={rawsheet,monthlysheet};
String viewnames[]={"rpt_pns_raw","rpt_pns_raw_monthly"};

String year="";
       IdGenerator dats= new IdGenerator();
        
        String startdate="2018-04-16";
        String enddate="2018-04-21";
        String subcounty="";
        String county="";
        if(request.getParameter("startdate")!=null)
        {
        
            startdate=request.getParameter("startdate");
        
        }
        if(request.getParameter("enddate")!=null)
        {
        
            enddate=request.getParameter("enddate");
        
        }



//startdate=startdate.replace("-", "").substring(0,6);
// enddate=enddate.replace("-", "").substring(0,6);


//        //subcounty
//        if(request.getParameter("rpt_subcounty")!=null)
//        {
//            subcounty=request.getParameter("rpt_subcounty");
//        }
//        //county
//        if(request.getParameter("rpt_county")!=null)
//        {
//         county=request.getParameter("rpt_county");
//        }

dbConn conn = new dbConn();
//========Query 1=================

String orgunits="1=1 ";
        
        if(!county.equals("")){
        orgunits+=" and county.CountyID like '"+county+"' ";
        }
        if(!subcounty.equals("") ){
            
         orgunits+=" and district.DistrictID like '"+subcounty+"' ";
        
        }

//for(int sheetno=0;sheetno < wb.getNumberOfSheets();sheetno++){
int cn=0;
for(XSSFSheet shet:Sheetnames){
    
    XSSFRow rw0=null;
    XSSFCell cell = null;
    if( shet.getSheetName().equals("PNS raw Data") ){ 
        rw0=shet.getRow(1); 
        cell=rw0.getCell(0);
    }
    else {
    rw0=shet.createRow(1);
    cell=rw0.createCell(0);
    }
    
    if(!(shet.getSheetName().equals("PNS raw Data"))){ 
    cell.setCellValue(shet.getSheetName()+" from date "+startdate+"  to "+enddate);
    cell.setCellStyle(style);
    shet.addMergedRegion(new CellRangeAddress(1, 1, 0,10));
    }
    else {
    
     cell.setCellValue(shet.getSheetName()+" from "+startdate+"  to "+enddate);
    }
    
    int count1  = 3;
    
    //shet.getSheetName();
    
    
    
    //========Query two====Facility Details==============
    
   // String qry = "select * from "+viewnames[cn]+"  where "+orgunits;
    
      String qry = "call "+viewnames[cn]+"('"+startdate+"','"+enddate+"')";
    
    cn++;
    System.out.println(qry);
    conn.rs = conn.st.executeQuery(qry);
    
    ResultSetMetaData metaData = conn.rs.getMetaData();
    int columnCount = metaData.getColumnCount();
    
    metaData = conn.rs.getMetaData();
    columnCount = metaData.getColumnCount();
    int count = count1;
    ArrayList mycolumns = new ArrayList();
    
    while (conn.rs.next()) {
        
        if (count == (count1)) {
//header rows
XSSFRow rw = null;
 if(!(shet.getSheetName().equals("PNS raw Data")  )){ 
 rw = shet.createRow(count);
 rw.setHeightInPoints(26);
 }
 else {
  rw = shet.getRow(count);
 }


//dont reate headers for the dailydata by counsellor since its a table

for (int i = 1; i <= columnCount; i++) {
    
    mycolumns.add(metaData.getColumnLabel(i));
    XSSFCell cell0 = null;
    if(!(shet.getSheetName().equals("PNS raw Data"))){
    cell0 = rw.createCell(i - 1);
    cell0.setCellValue(metaData.getColumnLabel(i));
    cell0.setCellStyle(stylex);
    }
    else {
        
      
        
    cell0 = rw.getCell(i - 1);
      if(!shet.getSheetName().equals("PNS raw Data")){
    cell0.setCellValue(metaData.getColumnLabel(i));
    }
    }
    
    
    
    
    //create row header
}//end of for loop
 count++;       


        }//end of headers
        //data rows
        XSSFRow rw = null;
        
       // if(!(shet.getSheetName().equals("PNS raw Data"))){
        if(1==1){
         rw = shet.createRow(count);
        }
        else {
         rw = shet.getRow(count);
        
        }
        
        for (int a = 0; a < columnCount; a++) {
            //System.out.print(mycolumns.get(a) + ":" + conn.rs.getString("" + mycolumns.get(a)));
            
            XSSFCell cell0 = null;
            
            //if(!(shet.getSheetName().equals("PNS raw Data")  )){
            if(1==1){
            cell0 = rw.createCell(a);
            }
            else {
             cell0 = rw.getCell(a);
            }
           
            
            if(isNumeric(conn.rs.getString("" + mycolumns.get(a)))){
                // if(1==1){
                
                
                
                if(conn.rs.getString(""+mycolumns.get(a)).length()>3 && conn.rs.getString(""+mycolumns.get(a)).contains(".")){
                    System.out.println("Number:"+conn.rs.getString(""+mycolumns.get(a)));
                    

                    cell0.setCellValue(conn.rs.getDouble(mycolumns.get(a).toString()));
                    stylec.setDataFormat(wb.createDataFormat().getFormat("0%"));
                    cell0.setCellStyle(stylec);
                }
                else {
                    cell0.setCellValue(conn.rs.getInt(mycolumns.get(a).toString()));
                    cell0.setCellStyle(style2);
                }
                
            }
            else
            {
                //cell0.setCellValue(conn.rs.getString("" + mycolumns.get(a)));
                
                cell0.setCellValue(conn.rs.getString("" + mycolumns.get(a)));
                cell0.setCellStyle(style2);
            }
            
            
            
        }
        
        // System.out.println("");
        count++;
    }
    
    
    if(!shet.getSheetName().equals("PNS raw Data")){
    //Autofreeze  || Autofilter  || Remove Gridlines ||
     System.out.println("shet.setAutoFilter(new CellRangeAddress("+count1+", "+(count - 1)+", 0, "+(columnCount-1));
    shet.setAutoFilter(new CellRangeAddress(count1, count - 1, 0, columnCount-1));
       
    
    //System.out.println("1,"+rowpos+",0,"+colposcopy);
    for (int i = 0; i <= columnCount; i++) {
        shet.autoSizeColumn(i);
    }
    
    shet.setDisplayGridlines(false);
    shet.createFreezePane(5, 4);
}
}



        if(1==1){
     XSSFSheet sheet= wb.getSheet("PNS raw Data");
        // tell your xssfsheet where its content begins and where it ends
((XSSFSheet)rawsheet).getCTWorksheet().getDimension().setRef("A4:AD" + (rawsheet.getLastRowNum() + 1));

CTTable ctTable = ((XSSFSheet)rawsheet).getTables().get(0).getCTTable();

ctTable.setRef("A4:AD" + (rawsheet.getLastRowNum() + 1)); // adjust reference as needed


        
        }



 XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);  

if(conn.rs!=null){conn.rs.close();}
if(conn.rs1!=null){conn.rs1.close();}
if(conn.st!=null){conn.st.close();}
if(conn.st1!=null){conn.st1.close();}
if(conn.connect!=null){conn.connect.close();}


IdGenerator IG = new IdGenerator();
String createdOn = IG.CreatedOn();

System.out.println("" + "PNS_rpt_from_" + createdOn.trim() + ".xlsx");

ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
wb.write(outByteStream);
byte[] outArray = outByteStream.toByteArray();
response.setContentType("application/ms-excel");
response.setContentLength(outArray.length);
response.setHeader("Expires:", "0"); // eliminates browser caching
response.setHeader("Content-Disposition", "attachment; filename=" + "PNS_rpt_from_"+startdate+"_to_"+enddate+"_gen_" + createdOn.trim() + ".xlsx");
response.setHeader("Set-Cookie","fileDownload=true; path=/");
OutputStream outStream = response.getOutputStream();
outStream.write(outArray);
outStream.flush();
 pkg.close();
         //wb.dispose();
       // response.sendRedirect("index.jsp");
           
         File file= new File(pathtodelete);
        
        if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}


        } catch (InvalidFormatException ex) {
            Logger.getLogger(pnsreports.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(pnsreports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(pnsreports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static boolean isNumeric(String strNum) {
    try {
        double d = Double.parseDouble(strNum);
    } catch (NumberFormatException | NullPointerException nfe) {
        return false;
    }
    return true;
}
    
}
