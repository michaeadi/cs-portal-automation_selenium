package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.PriorityDataBean;
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

public class PriorityDataExcelToBeanDao {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<PriorityDataBean> getData(String path, String sheetName) {

        List<PriorityDataBean> priorityList = new ArrayList<>();
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
                PriorityDataBean priorityDataBean = new PriorityDataBean();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() > 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        if (columnIndex == 0) {
                            priorityDataBean.setTicketPriority(cellValue);
                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    priorityList.add(priorityDataBean);
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return priorityList;
    }
}
