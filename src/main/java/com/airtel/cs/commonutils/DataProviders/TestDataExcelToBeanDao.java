package com.airtel.cs.commonutils.DataProviders;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TestDataExcelToBeanDao {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<TestDatabean> getData(String path, String SheetName) {

        List<TestDatabean> userCredsBeanList = new ArrayList<>();
        FileInputStream file;
        try {
            file = new FileInputStream(new File(path));
            Workbook workbook;
            if (path.contains("xlsx")) {
                workbook = new XSSFWorkbook(file);
                dataFormatter = new DataFormatter();
                evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
            } else {
                workbook = new HSSFWorkbook(file);
                dataFormatter = new DataFormatter();
                evaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
            }

            Sheet sheet = workbook.getSheet(SheetName);

            for (Row cells : sheet) {
                TestDatabean testDatabean = new TestDatabean();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() == 0) {
                    } else {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                testDatabean.setLoginAUUID(cellValue);
                                break;
                            case 1:
                                testDatabean.setPassword(cellValue);
                                break;
                            case 2:
                                testDatabean.setUserType(cellValue);
                                break;
                            case 3:
                                testDatabean.setCustomerNumber(cellValue);
                                break;
                            case 4:
                                testDatabean.setCustomerName(cellValue);
                                break;
                            case 5:
                                testDatabean.setCustomerDOB(cellValue);
                                break;
                            case 6:
                                testDatabean.setActivationDate(cellValue);
                                break;
                            case 7:
                                testDatabean.setActivationTime(cellValue);
                                break;
                            case 8:
                                testDatabean.setSimNumber(cellValue);
                                break;
                            case 9:
                                testDatabean.setSimType(cellValue);
                                break;
                            case 10:
                                testDatabean.setPUK1(cellValue);
                                break;
                            case 11:
                                testDatabean.setPUK2(cellValue);
                                break;
                            case 12:
                                testDatabean.setIdType(cellValue);
                                break;
                            case 13:
                                testDatabean.setIdNumber(cellValue);
                                break;
                            case 14:
                                testDatabean.setRoleType(cellValue);
                                break;
                            case 15:
                                testDatabean.setProdCustomerNumber(cellValue);
                                break;
                            case 16:
                                testDatabean.setProdSIMNumber(cellValue);
                                break;
                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(testDatabean);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return userCredsBeanList;
    }
}
