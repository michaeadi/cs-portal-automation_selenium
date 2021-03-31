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


public class ftrDataExcelToBeanDao {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<ftrDataBeans> getData(String path, String SheetName) {

        List<ftrDataBeans> userCredsBeanList = new ArrayList<>();
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
                ftrDataBeans ftrDataBeans = new ftrDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() == 0) {
                    } else {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                ftrDataBeans.setIssue(cellValue);
                                break;
                            case 1:
                                ftrDataBeans.setIssueType(cellValue);
                                break;
                            case 2:
                                ftrDataBeans.setIssueSubType(cellValue);
                                break;
                            case 3:
                                ftrDataBeans.setIssueSubSubType(cellValue);
                                break;
                            case 4:
                                ftrDataBeans.setIssueCode(cellValue);
                                break;
                            case 5:
                                ftrDataBeans.setWidgetName(cellValue);

                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(ftrDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return userCredsBeanList;
    }
}
