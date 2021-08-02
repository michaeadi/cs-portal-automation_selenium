package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.airtel.cs.driver.Driver.commonLib;


public class HeaderDataExcelToBeanDao {


    DataFormatter dataFormatter;
    FormulaEvaluator evaluator;
    private static final String FILE_EXTENSION = "xlsx";
    private static final Integer START_INDEX = 0;
    private static final Integer END_INDEX = 12;

    /**
     * This method is use to get cell value
     *
     * @param cell The Cell object
     * @return String The value
     */
    private String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    /**
     * This method is use to get all the  widget Headers based on file path and sheet name
     *
     * @param path      The file path of .xlsx file name
     * @param sheetName The sheet name
     * @return List The headers name
     */
    public List<HeaderDataBean> getData(String path, String sheetName) {

        List<HeaderDataBean> userCredsBeanList = new ArrayList<>();
        List<String> headersName = new ArrayList<>();
        FileInputStream file;
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
                HeaderDataBean headerDataBean = new HeaderDataBean();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cells.getRowNum() > 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);
                        if (columnIndex > START_INDEX && columnIndex <= END_INDEX) {
                            headersName.add(cellValue);
                        }
                        if (columnIndex == 0) {
                            headerDataBean.setTableName(cellValue);
                        }
                    }
                }
                if (cells.getRowNum() != 0) {
                    headersName.removeAll(Arrays.asList("", null));
                    headerDataBean.setHeaderName(headersName);
                    userCredsBeanList.add(headerDataBean);
                    headersName=new ArrayList<>();
                }
            }
        } catch (IOException e) {
            commonLib.fail("Exception found while reading the test data excel sheet with sheet name " + sheetName + ". Error Log: " + e.fillInStackTrace(), false);
        }
        return userCredsBeanList;
    }
}
