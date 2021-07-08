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

public class SLARuleFileBeanToExcel {
    DataFormatter dataFormatter;
    FormulaEvaluator evaluator;

    private String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<SLARuleFileDataBeans> getData(String path, String sheetName) {

        List<SLARuleFileDataBeans> ruleList = new ArrayList<>();
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
                SLARuleFileDataBeans slaRuleFileDataBeans = new SLARuleFileDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() > 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                slaRuleFileDataBeans.setCategoryCode(cellValue);
                                break;
                            case 1:
                                slaRuleFileDataBeans.setIsVIP(cellValue);
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
                        }
                    }
                }
                if (cells.getRowNum() != 0) {
                    ruleList.add(slaRuleFileDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return ruleList;
    }
}
