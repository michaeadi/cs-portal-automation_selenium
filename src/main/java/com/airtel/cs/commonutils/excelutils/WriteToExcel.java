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

import static com.airtel.cs.commonutils.utils.UtilsMethods.stringNotNull;

public class WriteToExcel extends Driver {

    private static final Integer START_INDEX = 47;
    private static final Integer END_INDEX = 54;
    private Sheet sheet = null;

    /**
     * This method is use to get create cell if not created already
     *
     * @param row    The row number
     * @param cell   The Cell Object
     * @param cellNo The Cell Number
     * @return Object The Cell
     */
    public Cell getCellNotNull(Row row, Cell cell, int cellNo) {
        return cell == null ? row.createCell(cellNo) : cell;
    }

    /**
     * This method is use to get column name from sheet based on column number
     *
     * @param sheet        The excel sheet
     * @param columnNumber The Column number
     * @return String The column name
     */
    public String getColumnName(Sheet sheet, Integer columnNumber) {
        String s = null;
        try {
            s = sheet.getRow(0).getCell(columnNumber).getRichStringCellValue().toString();
        } catch (Exception e) {
            commonLib.error(e.getMessage());
        }
        return s;
    }


    /**
     * This method is use to get column number based on column name
     *
     * @param columnName The column name
     * @return Integer The column name
     * @throws ColumnNotFoundException In-case of column name not found
     */
    public Integer getColumnNumberWithColumnName(String columnName) throws ColumnNotFoundException {
        for (int columnIndex = START_INDEX; columnIndex <= END_INDEX; columnIndex++) {
            if (stringNotNull(getColumnName(sheet, columnIndex)).equalsIgnoreCase(columnName)) {
                return columnIndex;
            }
        }
        throw new ColumnNotFoundException(" No Column Found with Column name " + columnName + " in excel sheet");
    }

    /**
     * This method is use to write ticket meta info into given sheet name
     *
     * @param filePath      The file path .xlsx extension
     * @param sheetName     The sheet name
     * @param nftrDataBeans The NFTR data beans
     * @param rowNum        The row number
     * @throws IOException             In-case of file not found
     * @throws ColumnNotFoundException in-case of column name not found
     */
    public void writeTicketMetaInfo(String filePath, String sheetName, NftrDataBeans nftrDataBeans, int rowNum) throws IOException, ColumnNotFoundException {
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook book = new XSSFWorkbook(inputStream);
        sheet = book.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Map<String, String> metaInfoMap = objectMapper.convertValue(nftrDataBeans, Map.class);
        for (Map.Entry mapElement : metaInfoMap.entrySet()) {
            String columnName = (String) mapElement.getKey();
            if (mapElement.getValue() != null) {
                Integer columnIndex = getColumnNumberWithColumnName(columnName);
                Cell cell = getCellNotNull(row, row.getCell(columnIndex), columnIndex);
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

    public void writeTicketNumber(String filePath, String sheetName, String[] dataToWrite, int rowNum) throws IOException {

        commonLib.info("Going to write Ticket Number in " + sheetName + "at" + filePath);
        File file = new File(filePath);

        FileInputStream inputStream = new FileInputStream(file);

        Workbook book = new XSSFWorkbook(inputStream);
        //Read excel sheet by sheet name
        Sheet sheet = book.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Cell cell;
        int ticketRow = 62;
        try {
            cell = row.getCell(ticketRow);
            cell.setCellValue(dataToWrite[0]);

        } catch (NullPointerException e) {
            cell = row.createCell(ticketRow);
            cell.setCellValue(dataToWrite[0]);

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