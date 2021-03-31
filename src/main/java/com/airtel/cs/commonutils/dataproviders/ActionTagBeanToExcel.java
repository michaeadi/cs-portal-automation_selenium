package com.airtel.cs.commonutils.dataproviders;

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

public class ActionTagBeanToExcel {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<ActionTagDataBeans> getData(String path, String SheetName) {

        List<ActionTagDataBeans> userCredsBeanList = new ArrayList<>();
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
                ActionTagDataBeans actionTagDataBeans = new ActionTagDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() == 0) {
                    } else {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                actionTagDataBeans.setActionTagName(cellValue);
                                break;
                            case 1:
                                actionTagDataBeans.setOption1(cellValue);
                                break;
                            case 2:
                                actionTagDataBeans.setOption2(cellValue);
                                break;
                            case 3:
                                actionTagDataBeans.setOption3(cellValue);
                                break;
                            case 4:
                                actionTagDataBeans.setOption4(cellValue);
                                break;
                            case 5:
                                actionTagDataBeans.setOption5(cellValue);
                            case 6:
                                actionTagDataBeans.setCode(cellValue);
                                break;
                            case 7:
                                actionTagDataBeans.setTestDataUAT(cellValue);
                                break;
                            case 8:
                                actionTagDataBeans.setTestDataProd(cellValue);
                                break;

                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(actionTagDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return userCredsBeanList;
    }
}
