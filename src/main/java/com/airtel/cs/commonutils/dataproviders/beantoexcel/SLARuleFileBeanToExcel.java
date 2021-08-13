package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.SLARuleFileDataBeans;
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

public class SLARuleFileBeanToExcel {
    DataFormatter dataFormatter;
    FormulaEvaluator evaluator;
    private static final String XLSX_FILE_EXTENSION="xlsx";

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
     * This method is use to get all the  SLA Calculation rule based on file path and sheet name
     * @param path The file path of .xlsx file name
     * @param sheetName The sheet name
     * @return List The rules
     */
    public List<SLARuleFileDataBeans> getData(String path, String sheetName) {

        List<SLARuleFileDataBeans> ruleList = new ArrayList<>();
        FileInputStream file;
        try {
            file = new FileInputStream(new File(path));
            Workbook workbook;
            if (path.contains(XLSX_FILE_EXTENSION)) {
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
                SLARuleFileDataBeans slaRuleFileDataBeans = new SLARuleFileDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() > 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell)==null?"":fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                slaRuleFileDataBeans.setCategoryCode(cellValue);
                                break;
                            case 1:
                                slaRuleFileDataBeans.setCustomerVip(cellValue);
                                break;
                            case 2:
                                slaRuleFileDataBeans.setLineType(cellValue);
                                break;
                            case 3:
                                slaRuleFileDataBeans.setCustomerType(cellValue);
                                break;
                            case 4:
                                slaRuleFileDataBeans.setServiceCategory(cellValue);
                                break;
                            case 5:
                                slaRuleFileDataBeans.setCustomerSegment(cellValue);
                                break;
                            case 6:
                                slaRuleFileDataBeans.setCustomerSubSegment(cellValue);
                                break;
                            case 7:
                                slaRuleFileDataBeans.setInteractionChannel(cellValue);
                                break;
                            case 8:
                                slaRuleFileDataBeans.setWorkgroup1(cellValue);
                                break;
                            case 9:
                                slaRuleFileDataBeans.setSla1(cellValue);
                                break;
                            case 10:
                                slaRuleFileDataBeans.setWorkgroup2(cellValue);
                                break;
                            case 11:
                                slaRuleFileDataBeans.setSla2(cellValue);
                                break;
                            case 12:
                                slaRuleFileDataBeans.setWorkgroup3(cellValue);
                                break;
                            case 13:
                                slaRuleFileDataBeans.setSla3(cellValue);
                                break;
                            case 14:
                                slaRuleFileDataBeans.setWorkgroup4(cellValue);
                                break;
                            case 15:
                                slaRuleFileDataBeans.setSla4(cellValue);
                                break;
                            case 16:
                                slaRuleFileDataBeans.setCommittedSLA(cellValue);
                                break;
                            case 17:
                                slaRuleFileDataBeans.setDefaultRule(cellValue);
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (cells.getRowNum() != 0) {
                    ruleList.add(slaRuleFileDataBeans);
                }
            }
        } catch (IOException e) {
            commonLib.fail("Exception found while reading the test data excel sheet with sheet name "+sheetName+". Error Log: "+e.fillInStackTrace(),false);
        }
        return ruleList;
    }
}
