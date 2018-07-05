/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author EKaunda
 */
public class mergeExcel {
 
   public  XSSFWorkbook mergeExcelFiles(XSSFWorkbook destworkbook, List<FileInputStream> list) throws IOException {
    //XSSFWorkbook newworkbook = new XSSFWorkbook();
    
    
    for (FileInputStream fin : list) {
      XSSFWorkbook sourcewb = new XSSFWorkbook(fin);
      for (int i = 0; i < sourcewb.getNumberOfSheets(); i++) {
        XSSFSheet sheet = destworkbook.createSheet(sourcewb.getSheetName(i));
        copySheets(destworkbook, sheet, sourcewb.getSheetAt(i));
      }
    }
    
//    try {
//      writeFile(destworkbook, file);
//    } catch (Exception e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
    return destworkbook;
  }
  
  protected  void writeFile(XSSFWorkbook book, File file) throws Exception {
    FileOutputStream out = new FileOutputStream(file);
    book.write(out);
    out.close();
  }
  
  private  void copySheets(XSSFWorkbook newWorkbook, XSSFSheet newSheet, XSSFSheet sheet){     
    copySheets(newWorkbook, newSheet, sheet, true);
  }     

  private  void copySheets(XSSFWorkbook newWorkbook, XSSFSheet newSheet, XSSFSheet sheet, boolean copyStyle){     
    int newRownumber = newSheet.getLastRowNum() + 1;
    int maxColumnNum = 0;     
    Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null;    
    
    for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {     
      XSSFRow srcRow = sheet.getRow(i);     
      XSSFRow destRow = newSheet.createRow(i + newRownumber);     
      if (srcRow != null) {     
        copyRow(newWorkbook, sheet, newSheet, srcRow, destRow, styleMap);     
        if (srcRow.getLastCellNum() > maxColumnNum) {     
            maxColumnNum = srcRow.getLastCellNum();     
        }     
      }     
    }     
    for (int i = 0; i <= maxColumnNum; i++) {     
      newSheet.setColumnWidth(i, sheet.getColumnWidth(i));     
    }     
  }     
  
  public  void copyRow(XSSFWorkbook newWorkbook, XSSFSheet srcSheet, XSSFSheet destSheet, XSSFRow srcRow, XSSFRow destRow, Map<Integer, XSSFCellStyle> styleMap) {     
    destRow.setHeight(srcRow.getHeight());
    for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {     
      XSSFCell oldCell = srcRow.getCell(j);
      XSSFCell newCell = destRow.getCell(j);
      if (oldCell != null) {     
        if (newCell == null) {     
          newCell = destRow.createCell(j);     
        }     
        copyCell(newWorkbook, oldCell, newCell, styleMap);
      }     
    }                
  }
  
  public static void copyCell(XSSFWorkbook newWorkbook, XSSFCell oldCell, XSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap) {      
    if(styleMap != null) {     
      int stHashCode = oldCell.getCellStyle().hashCode();     
      XSSFCellStyle newCellStyle = styleMap.get(stHashCode);     
      if(newCellStyle == null){     
        newCellStyle = newWorkbook.createCellStyle();     
        newCellStyle.cloneStyleFrom(oldCell.getCellStyle());     
        styleMap.put(stHashCode, newCellStyle);     
      }     
      newCell.setCellStyle(newCellStyle);   
    }     
    switch(oldCell.getCellType()) {     
      case XSSFCell.CELL_TYPE_STRING:     
        newCell.setCellValue(oldCell.getRichStringCellValue());     
        break;     
      case XSSFCell.CELL_TYPE_NUMERIC:     
        newCell.setCellValue(oldCell.getNumericCellValue());     
        break;     
      case XSSFCell.CELL_TYPE_BLANK:     
        newCell.setCellType(XSSFCell.CELL_TYPE_BLANK);     
        break;     
      case XSSFCell.CELL_TYPE_BOOLEAN:     
        newCell.setCellValue(oldCell.getBooleanCellValue());     
        break;     
      case XSSFCell.CELL_TYPE_ERROR:     
        newCell.setCellErrorValue(oldCell.getErrorCellValue());     
        break;     
      case XSSFCell.CELL_TYPE_FORMULA:  
          newCell.setCellType(XSSFCell.CELL_TYPE_FORMULA); 
        newCell.setCellFormula(oldCell.getCellFormula());     
        break;     
      default:     
        break;     
    }
  }   
    
}
