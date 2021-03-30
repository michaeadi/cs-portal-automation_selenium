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

public class AuthTabBeanToExcel {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<AuthTabDataBeans> getData(String path, String SheetName) {

        List<AuthTabDataBeans> userCredsBeanList = new ArrayList<>();
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
                AuthTabDataBeans authTabDataBeans = new AuthTabDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() == 0) {
                    } else {
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

                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(authTabDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return userCredsBeanList;
    }
}
