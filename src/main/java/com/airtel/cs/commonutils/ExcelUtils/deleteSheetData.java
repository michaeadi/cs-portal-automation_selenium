package com.airtel.cs.commonutils.ExcelUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class deleteSheetData {


    //Close input stream


    boolean isEmpty(Row row) {
        try {
            System.out.println(row.getCell(0).getStringCellValue());
            if (row.getLastCellNum() < 0) {
                return false;
            }
            return row.getCell(0).getStringCellValue().equals("");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void deleteData(String filePath, String sheetName) throws IOException {

        //Create an object of File class to open xlsx file

        File file = new File(filePath);

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook guru99Workbook = new XSSFWorkbook(inputStream);

//Read excel sheet by sheet name

        Sheet sheet = guru99Workbook.getSheet(sheetName);

        //Get the current count of rows in excel file
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        //Get the first row from the sheet

        Row row = sheet.getRow(1);

        //Create a new row and append it at last of sheet

        Row newRow = sheet.createRow(rowCount + 1);


        for (int i = 0; i < sheet.getLastRowNum(); i++) {
//            System.out.println(i);
            if (isEmpty(sheet.getRow(i))) {
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                i--;//Adjusts the sweep in accordance to a row removal
            }
        }


        inputStream.close();

        //Create an object of FileOutputStream class to create write data in excel file

        FileOutputStream outputStream = new FileOutputStream(file);

        //write data in the excel file
        //write data in the excel file

        guru99Workbook.write(outputStream);

        //close output stream

        outputStream.close();

    }

    public void deleteData1(String filePath, String sheetName) throws IOException {

        //Create an object of File class to open xlsx file

        File file = new File(filePath);

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook guru99Workbook = new XSSFWorkbook(inputStream);

//Read excel sheet by sheet name

        Sheet sheet = guru99Workbook.getSheet(sheetName);

        //Get the current count of rows in excel file

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        //Get the first row from the sheet

        Row row = sheet.getRow(1);

        //Create a new row and append it at last of sheet

        Row newRow = sheet.createRow(rowCount + 1);

        //Create a loop over the cell of newly created Row
        boolean isRowEmpty = false;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) == null) {
                isRowEmpty = true;
                sheet.shiftRows(i + 1, sheet.getLastRowNum() + 1, -1);
                i--;
                continue;
            }
            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                if (sheet.getRow(i).getCell(j) == null ||
                        sheet.getRow(i).getCell(j).toString().trim().equals("")) {
                    isRowEmpty = true;
                } else {
                    isRowEmpty = false;
                    break;
                }
            }
            if (isRowEmpty) {
                sheet.shiftRows(i + 1, sheet.getLastRowNum() + 1, -1);
                i--;
            }
        }
//        for (int j = 1; j < sheet.getLastRowNum(); j++) {
//            System.out.println(sheet.getLastRowNum());
//            sheet.removeRow(sheet.getRow(j));
//
//
//        }

        //Close input stream

        inputStream.close();

        //Create an object of FileOutputStream class to create write data in excel file

        FileOutputStream outputStream = new FileOutputStream(file);

        //write data in the excel file

        guru99Workbook.write(outputStream);

        //close output stream

        outputStream.close();

    }
}