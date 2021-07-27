package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.TestDatabean;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.airtel.cs.driver.Driver.commonLib;


public class TestDataExcelToBeanDao {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;
    private static final String FILE_EXTENSION="xlsx";

    /**
     * This method is use to get cell value
     * @param cell The Cell object
     * @return String The value
     */
    private String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    /**
     * This method is use to get all the  User cred based on file path and sheet name
     * @param path The file path of .xlsx file name
     * @param sheetName The sheet name
     * @return List The user cred
     */
    public List<TestDatabean> getData(String path, String sheetName) {

        List<TestDatabean> userCredsBeanList = new ArrayList<>();
        FileInputStream file;
        try {
            file = new FileInputStream(new File(path));
            Workbook workbook;
            if (path.contains(FILE_EXTENSION)) {
                workbook = new XSSFWorkbook(file);
                dataFormatter = new DataFormatter();
                evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
            } else {
                workbook = new HSSFWorkbook(file);
                dataFormatter = new DataFormatter();
                evaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
            }

            Sheet sheet = workbook.getSheet(sheetName);

            for (Row cells : sheet) {
                TestDatabean testDatabean = new TestDatabean();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() > 0) {
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
                                testDatabean.setPuk1(cellValue);
                                break;
                            case 11:
                                testDatabean.setPuk2(cellValue);
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
                            default:
                                break;
                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(testDatabean);
                }
            }
        } catch (IOException e) {
            commonLib.fail("Exception found while reading the test data excel sheet with sheet name "+sheetName+". Error Log: "+e.fillInStackTrace(),false);
        }
        return userCredsBeanList;
    }
}
