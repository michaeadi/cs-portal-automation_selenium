package com.airtel.cs.commonutils.excelutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    static String projectPath;
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public ExcelUtils(String excelpath, String sheetname) {
        try {
            workbook = new XSSFWorkbook(excelpath);
            sheet = workbook.getSheet(sheetname);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            exp.getCause();
            exp.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getrowcount();
        getcelldata(0, 0);
        getcelldataNumber(1, 1);
        getcolcount();

    }

    public static int getrowcount() {
        int rowCount;
        rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("rows in excel :" + rowCount);
        return rowCount;

    }

    public static String getcelldata(int rownum, int colnum) {

        return sheet.getRow(rownum).getCell(colnum).getStringCellValue();
    }

    public static String getcelldataNumber(int rownum, int colnum) {
        Cell cell1 = sheet.getRow(rownum).getCell(colnum);
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell1);

    }

    public static int getcolcount() {
        int colCount;

        colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        System.out.println("Columns in excel :" + colCount);
        return colCount;


    }

}
