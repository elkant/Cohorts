/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KP;


import General.IdGenerator;
import db.dbConn;
import java.io.ByteArrayOutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author EKaunda
 */
public class micarereportingrates extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, InvalidFormatException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        
         IdGenerator IG = new IdGenerator();
        String createdOn = IG.CreatedOn();
        
     
        
        /* TODO output your page here. You may use following sample code. */
//______________________________________________________________________________________
//                       CREATE THE WORKSHEETS          
//______________________________________________________________________________________  
        





XSSFWorkbook wb1;
 
String pathtodelete=null;

Date da= new Date();

String dat2 = da.toString().replace(" ", "_");

dat2 = dat2.toString().replace(":", "_");



String filepath="micareliftup_Detailed_"+dat2+".xlsx";



//wb = new XSSFWorkbook( OPCPackage.open(allpath) );


//XSSFWorkbook wb = wb1;


SXSSFWorkbook wb = new SXSSFWorkbook(1000);

        Font font =  wb.createFont();
        font.setFontHeightInPoints((short) 18);
        font.setFontName("Cambria");
        font.setColor((short) 0000);
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        XSSFFont font2 = (XSSFFont) wb.createFont();
        font2.setFontName("Cambria");
        font2.setColor((short) 0000);
        CellStyle style2 = wb.createCellStyle();
        style2.setFont(font2);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        CellStyle stborder = wb.createCellStyle();
        stborder.setBorderTop(HSSFCellStyle.BORDER_THIN);
        stborder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        stborder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        stborder.setBorderRight(HSSFCellStyle.BORDER_THIN);
        stborder.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        CellStyle stylex = wb.createCellStyle();
        stylex.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        stylex.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        stylex.setBorderTop(HSSFCellStyle.BORDER_THIN);
        stylex.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        stylex.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        stylex.setBorderRight(HSSFCellStyle.BORDER_THIN);
        stylex.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        CellStyle stylesum = wb.createCellStyle();
        stylesum.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        stylesum.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        stylesum.setBorderTop(HSSFCellStyle.BORDER_THIN);
        stylesum.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        stylesum.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        stylesum.setBorderRight(HSSFCellStyle.BORDER_THIN);
        stylesum.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        Font fontx = wb.createFont();
        fontx.setColor(HSSFColor.BLACK.index);
        fontx.setFontName("Cambria");
        stylex.setFont(fontx);
        stylex.setWrapText(true);

        stylesum.setFont(fontx);
        stylesum.setWrapText(true);

        Sheet shet = wb.createSheet("ReportingRates");

        String year="";
       IdGenerator dats= new IdGenerator();
        
        String startdate="2019-05-13";
        String enddate=IG.toDay();
      String lip="";
        String county="";
           if(request.getParameter("startdate")!=null)
        {
        
        startdate=request.getParameter("startdate");
        
        }
        if(request.getParameter("enddate")!=null)
        {
        
            enddate=request.getParameter("enddate");
        
        }
        
        if(request.getParameter("liprpt")!=null)
        {
        
            lip=request.getParameter("liprpt");
        
        }
        
        String sd=startdate.replace("-","").substring(0, 6);
        String ed=enddate.replace("-","").substring(0, 6);
      

        dbConn conn = new dbConn();
        
        //========Query 1=================
        
   
        
     
   
        
        
     
        
        //_______________________________________________________________________________________________
        
        

        
        
       
        
        
        
        //_______________________________________________________________________________________________
        
        
        
        
//        
//        XSSFRow rw0=shet.createRow(1);
//        XSSFCell cell = rw0.createCell(0);
//        cell.setCellValue("Surge Tracker for Period "+startdate+"  to "+enddate+"");
//        cell.setCellStyle(style);
//        shet.addMergedRegion(new CellRangeAddress(1, 1, 0,10));
//                    
                int count1  = 1;
        
       
        
        //========Query two====Facility Details==============
        
        String qry = "call internal_system.sp_kp_micare_reporting_rates_perlip('"+ed+"','"+lip+"');";
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
         Row rw = shet.createRow(count);
//rw.setHeightInPoints(26);
                for (int i = 1; i <= columnCount; i++) 
                {
//skip header
                    mycolumns.add(metaData.getColumnLabel(i));
                    Cell cell0 = rw.createCell(i - 1);
                    cell0.setCellValue(metaData.getColumnLabel(i));
                    cell0.setCellStyle(stylex);

                    //create row header
                }//end of for loop
                count++;
            }//end of if
            //data rows     
            Row rw = shet.createRow(count);

            for (int a = 0; a < columnCount; a++) 
            {
               // System.out.print(mycolumns.get(a) + ":" + conn.rs.getString("" + mycolumns.get(a)));

                Cell cell0 = rw.createCell(a);
                 if(isNumeric(conn.rs.getString("" + mycolumns.get(a))))
                 {
                cell0.setCellValue(conn.rs.getInt(mycolumns.get(a).toString()));
                 }
                else 
                {
                  // System.out.println(mycolumns.get(a)+" Last option"+conn.rs.getString("" + mycolumns.get(a)));
                   //System.out.println( mycolumns.get(a)+" --Last option"+conn.rs.getString("" + mycolumns.get(a)));
                     cell0.setCellValue(conn.rs.getString("" + mycolumns.get(a).toString()));//
                    //cell0.setCellValue(conn.rs.getString("" + mycolumns.get(a)));
                   
                }
            
                cell0.setCellStyle(style2);

            }

            // System.out.println("");
            count++;
        }

        
        
        //Autofreeze  || Autofilter  || Remove Gridlines ||  
     if(count!=count1)   {
       // shet.setAutoFilter(new CellRangeAddress(count1, count - 1, 0, columnCount-1));

        //System.out.println("1,"+rowpos+",0,"+colposcopy);
        for (int i = 0; i <= columnCount; i++) 
        {
          //  shet.autoSizeColumn(i);
        }

      //  shet.setDisplayGridlines(false);
      //  shet.createFreezePane(6, 4);
    }
     
    
     
        
        if(conn.rs!=null){conn.rs.close();}
        if(conn.rs1!=null){conn.rs1.close();}
        if(conn.st!=null){conn.st.close();}
        if(conn.st1!=null){conn.st1.close();}
        if(conn.connect!=null){conn.connect.close();}
        
        
       

        System.out.println("" + "micare_reports_Gen_" + createdOn.trim() + ".xlsx");

        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        wb.write(outByteStream);
        byte[] outArray = outByteStream.toByteArray();
        response.setContentType("application/ms-excel");
        response.setContentLength(outArray.length);
        response.setHeader("Expires:", "0"); // eliminates browser caching
        response.setHeader("Content-Disposition", "attachment; filename=" + "Micare_and_Liftup_Reportingrates_btn_"+startdate+"_to_"+enddate+"_gen_" + createdOn.trim() + ".xlsx");
        response.setHeader("Set-Cookie","fileDownload=true; path=/");
        OutputStream outStream = response.getOutputStream();
        outStream.write(outArray);
        outStream.flush();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(micareliftupreport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(micareliftupreport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(micareliftupreport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(micareliftupreport.class.getName()).log(Level.SEVERE, null, ex);
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
