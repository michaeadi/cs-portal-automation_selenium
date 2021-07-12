package com.airtel.cs.commonutils.excelutils;

import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.exceptions.ColumnNotFoundException;
import com.airtel.cs.driver.Driver;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class WriteToExcel extends Driver {

    private static final Integer START_INDEX=47;
    private static final Integer END_INDEX=54;
    private Sheet sheet=null;

    public Cell getCellNotNull(Row row,Cell cell,int cellNo){ return cell==null? row.createCell(cellNo):cell; }

    public String getColumnName(Sheet sheet,Integer columnNumber){
        return sheet.getRow(0).getCell(columnNumber).getRichStringCellValue().toString();
    }

    public Integer getColumnNumberWithColumnName(String columnName) throws ColumnNotFoundException {
        for(int columnIndex=START_INDEX;columnIndex<=END_INDEX;columnIndex++){
           if(getColumnName(sheet,columnIndex).equalsIgnoreCase(columnName)){
               return columnIndex;
           }
       }
        throw new ColumnNotFoundException(" No Column Found with Column name "+columnName+" in excel sheet");
    }

    public void writeTicketMetaInfo(String filePath, String sheetName, NftrDataBeans nftrDataBeans, int rowNum) throws IOException, ColumnNotFoundException {
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook book = new XSSFWorkbook(inputStream);
        sheet = book.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Map<String,String> metaInfoMap=objectMapper.convertValue(nftrDataBeans,Map.class);
        for (Map.Entry mapElement : metaInfoMap.entrySet()) {
            String columnName = (String) mapElement.getKey();
            if(mapElement.getValue()!=null){
                Integer columnIndex=getColumnNumberWithColumnName(columnName);
                Cell cell=getCellNotNull(row,row.getCell(columnIndex),columnIndex);
                cell.setCellValue(String.valueOf(mapElement.getValue()));
            }
        }
        inputStream.close();
        //Create an object of FileOutputStream class to create write data in excel file
        FileOutputStream outputStream = new FileOutputStream(file);
        //write data in the excel file
        book.write(outputStream);
        //close output stream
        outputStream.close();
    }
}