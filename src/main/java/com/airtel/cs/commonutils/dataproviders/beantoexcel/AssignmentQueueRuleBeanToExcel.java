package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.AssignmentQueueRuleDataBeans;
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

public class AssignmentQueueRuleBeanToExcel {
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
     * This method is use to get all the  queue assignment rule based on file path and sheet name
     * @param path The file path of .xlsx file name
     * @param sheetName The sheet name
     * @return List The rules
     */
    public List<AssignmentQueueRuleDataBeans> getData(String path, String sheetName) {

        List<AssignmentQueueRuleDataBeans> ruleList = new ArrayList<>();
        FileInputStream file = null;
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
                AssignmentQueueRuleDataBeans queueRuleDataBeans = new AssignmentQueueRuleDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() > 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                queueRuleDataBeans.setCategoryCode(cellValue);
                                break;
                            case 1:
                                queueRuleDataBeans.setAttributeName(cellValue);
                                break;
                            case 2:
                                queueRuleDataBeans.setAttributeValue(cellValue);
                                break;
                            case 3:
                                queueRuleDataBeans.setWorkgroupName(cellValue);
                                break;
                            case 4:
                                queueRuleDataBeans.setQueueName(cellValue);
                                break;
                            case 5:
                                queueRuleDataBeans.setTicketPriority(cellValue);
                                break;
                            case 6:
                                queueRuleDataBeans.setTicketState(cellValue);
                                break;
                            case 7:
                                queueRuleDataBeans.setRulePriority(cellValue);
                                break;
                                default:
                                    break;
                        }
                    }
                }
                if (cells.getRowNum() != 0) {
                    ruleList.add(queueRuleDataBeans);
                }
            }
        } catch (IOException e) {
            commonLib.fail("Exception found while reading the test data excel sheet with sheet name "+sheetName+". Error Log: "+e.fillInStackTrace(),false);
        }
        return ruleList;
    }
}
