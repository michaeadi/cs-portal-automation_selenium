package com.airtel.cs.commonutils.dataproviders.rulefile;

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

public class AssignmentQueueRuleBeanToExcel {
    DataFormatter dataFormatter;
    FormulaEvaluator evaluator;

    private String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<AssignmentQueueRuleDataBeans> getData(String path, String sheetName) {

        List<AssignmentQueueRuleDataBeans> ruleList = new ArrayList<>();
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
                        }
                    }
                }
                if (cells.getRowNum() != 0) {
                    ruleList.add(queueRuleDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return ruleList;
    }
}
