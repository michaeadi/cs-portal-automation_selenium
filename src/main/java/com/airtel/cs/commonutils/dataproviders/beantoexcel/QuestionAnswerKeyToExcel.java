package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.QuestionAnswerKeyDataBeans;
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

public class QuestionAnswerKeyToExcel {

    DataFormatter dataFormatter;
    FormulaEvaluator evaluator;

    private String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<QuestionAnswerKeyDataBeans> getData(String path, String sheetName) {

        List<QuestionAnswerKeyDataBeans> questionAnswerKeyDataBeans = new ArrayList<>();
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
                QuestionAnswerKeyDataBeans keyDataBeans = new QuestionAnswerKeyDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() > 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                keyDataBeans.setQuestionLabel(cellValue);
                                break;
                            case 1:
                                keyDataBeans.setQuestionKey(cellValue);
                                break;
                            case 2:
                                keyDataBeans.setAnswerKey(cellValue);
                                break;
                            default:
                                break;
                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    questionAnswerKeyDataBeans.add(keyDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return questionAnswerKeyDataBeans;
    }
}
