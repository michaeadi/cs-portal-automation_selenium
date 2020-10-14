package Utils.DataProviders;

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

public class QueueStateBeanToExcel {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<QueueStateDataBeans> getData(String path, String SheetName) {

        List<QueueStateDataBeans> userCredsBeanList = new ArrayList<>();
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
                QueueStateDataBeans queueStateDataBeans = new QueueStateDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() == 0) {
                    } else {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                queueStateDataBeans.setQueue(cellValue);
                                break;
                            case 1:
                                queueStateDataBeans.setState1(cellValue);
                                break;
                            case 2:
                                queueStateDataBeans.setState2(cellValue);
                                break;
                            case 3:
                                queueStateDataBeans.setState3(cellValue);
                                break;
                            case 4:
                                queueStateDataBeans.setState4(cellValue);
                                break;
                            case 5:
                                queueStateDataBeans.setState5(cellValue);
                            case 6:
                                queueStateDataBeans.setState6(cellValue);
                                break;
                            case 7:
                                queueStateDataBeans.setState7(cellValue);
                                break;
                            case 8:
                                queueStateDataBeans.setState8(cellValue);
                                break;
                            case 9:
                                queueStateDataBeans.setState9(cellValue);
                            case 10:
                                queueStateDataBeans.setState10(cellValue);
                                break;

                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(queueStateDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return userCredsBeanList;
    }
}
