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


public class HeaderDataExcelToBeanDao {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<HeaderDataBean> getData(String path, String SheetName) {

        List<HeaderDataBean> userCredsBeanList = new ArrayList<>();
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
                HeaderDataBean HeaderDataBean = new HeaderDataBean();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() == 0) {
                    } else {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                HeaderDataBean.setTableName(cellValue);
                                break;
                            case 1:
                                HeaderDataBean.setRow1(cellValue);
                                break;
                            case 2:
                                HeaderDataBean.setRow2(cellValue);
                                break;
                            case 3:
                                HeaderDataBean.setRow3(cellValue);
                                break;
                            case 4:
                                HeaderDataBean.setRow4(cellValue);
                                break;
                            case 5:
                                HeaderDataBean.setRow5(cellValue);
                                break;
                            case 6:
                                HeaderDataBean.setRow6(cellValue);
                                break;
                            case 7:
                                HeaderDataBean.setRow7(cellValue);
                                break;
                            case 8:
                                HeaderDataBean.setRow8(cellValue);
                                break;
                            case 9:
                                HeaderDataBean.setRow9(cellValue);
                                break;
                            case 10:
                                HeaderDataBean.setRow10(cellValue);
                                break;
                            case 11:
                                HeaderDataBean.setRow11(cellValue);
                                break;
                            case 12:
                                HeaderDataBean.setRow12(cellValue);
                                break;

                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(HeaderDataBean);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return userCredsBeanList;
    }
}
