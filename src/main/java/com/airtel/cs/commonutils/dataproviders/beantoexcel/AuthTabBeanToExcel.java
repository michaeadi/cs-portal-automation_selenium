package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.AuthTabDataBeans;
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

public class AuthTabBeanToExcel {

    DataFormatter dataFormatter;
    FormulaEvaluator evaluator;
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
     * This method is use to get all the  Authentication policy based on file path and sheet name
     * @param path The file path of .xlsx file name
     * @param sheetName The sheet name
     * @return List The Policies
     */
    public List<AuthTabDataBeans> getData(String path, String sheetName) {

        List<AuthTabDataBeans> userCredsBeanList = new ArrayList<>();
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
                AuthTabDataBeans authTabDataBeans = new AuthTabDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() > 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                authTabDataBeans.setPolicyName(cellValue);
                                break;
                            case 1:
                                authTabDataBeans.setPolicyMessage(cellValue);
                                break;
                            case 2:
                                authTabDataBeans.setMinAnswer(cellValue);
                                break;
                            case 3:
                                authTabDataBeans.setQ1(cellValue);
                                break;
                            case 4:
                                authTabDataBeans.setQ2(cellValue);
                                break;
                            case 5:
                                authTabDataBeans.setQ3(cellValue);
                                break;
                            case 6:
                                authTabDataBeans.setQ4(cellValue);
                                break;
                            case 7:
                                authTabDataBeans.setQ5(cellValue);
                                break;
                            case 8:
                                authTabDataBeans.setQ6(cellValue);
                                break;
                            case 9:
                                authTabDataBeans.setQ7(cellValue);
                                break;
                            case 10:
                                authTabDataBeans.setQ8(cellValue);
                                break;
                            case 11:
                                authTabDataBeans.setQ9(cellValue);
                                break;
                            case 12:
                                authTabDataBeans.setQ10(cellValue);
                                break;
                            default:
                                break;
                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(authTabDataBeans);
                }
            }
        } catch (IOException e) {
            commonLib.fail("Exception found while reading the test data excel sheet with sheet name "+sheetName+". Error Log: "+e.fillInStackTrace(),false);
        }
        return userCredsBeanList;
    }
}
