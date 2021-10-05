package com.airtel.cs.commonutils.excelutils;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Log4j2
public class ExcelUtils {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public ExcelUtils(String excelPath, String sheetName) {
        try {
            workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception exp) {
            log.info(exp.getMessage());
            exp.getCause();
            exp.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getRowCount();
        getCellData(0, 0);
        getCellDataNumber(1, 1);
        getColCount();
    }

    public static int getRowCount() {
        int rowCount;
        rowCount = sheet.getPhysicalNumberOfRows();
        log.info("rows in excel :" + rowCount);
        return rowCount;
    }

    public static String getCellData(int rownum, int colnum) {

        return sheet.getRow(rownum).getCell(colnum).getStringCellValue();
    }

    public static String getCellDataNumber(int rownum, int colnum) {
        Cell cell1 = sheet.getRow(rownum).getCell(colnum);
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell1);
    }

    public static int getColCount() {
        int colCount;

        colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        log.info("Columns in excel :" + colCount);
        return colCount;
    }
}
