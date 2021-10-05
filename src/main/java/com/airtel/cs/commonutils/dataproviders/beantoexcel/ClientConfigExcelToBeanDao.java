package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientConfigExcelToBeanDao {
    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<ClientConfigDataBean> getData(String path, String sheetName) {

        List<ClientConfigDataBean> clientConfig = new ArrayList<>();
        FileInputStream file;
        try {
            file = new FileInputStream(path);
            Workbook workbook;

            workbook = new XSSFWorkbook(file);
            dataFormatter = new DataFormatter();
            evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);


            Sheet sheet = workbook.getSheet(sheetName);

            for (Row cells : sheet) {
                ClientConfigDataBean clientConfigDataBean = new ClientConfigDataBean();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() != 0) {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);

                        switch (columnIndex) {
                            case 0:
                                clientConfigDataBean.setFirstCategoryLevel(cellValue);
                                break;
                            case 1:
                                clientConfigDataBean.setLastCategoryLevel(cellValue);
                                break;
                            case 2:
                                clientConfigDataBean.setFieldName(cellValue);
                                break;
                            case 3:
                                clientConfigDataBean.setIsSearchAble(cellValue);
                                break;
                            case 4:
                                clientConfigDataBean.setIsActive(cellValue);
                                break;
                            case 5:
                                clientConfigDataBean.setIsMandatory(cellValue);
                                break;
                            case 6:
                                clientConfigDataBean.setValue(cellValue);
                                break;
                            case 7:
                                clientConfigDataBean.setIsDeActivate(cellValue);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + columnIndex);
                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    clientConfig.add(clientConfigDataBean);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientConfig;
    }
}
