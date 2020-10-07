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


public class nftrDataExcelToBeanDao {

    static DataFormatter dataFormatter;
    static FormulaEvaluator evaluator;

    private static String fetchValue(Cell cell) {
        evaluator.evaluate(cell);
        return dataFormatter.formatCellValue(cell, evaluator);
    }

    public List<nftrDataBeans> getData(String path, String SheetName) {

        List<nftrDataBeans> userCredsBeanList = new ArrayList<>();
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
                nftrDataBeans nftrDataBeans = new nftrDataBeans();
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cells.getRowNum() == 0) {
                    } else {
                        int columnIndex = cell.getColumnIndex();
                        String cellValue = fetchValue(cell);
                        nftrDataBeans.setRownum((cell.getRowIndex()));

                        switch (columnIndex) {
                            case 0:
                                nftrDataBeans.setIssue(cellValue);
                                break;
                            case 1:
                                nftrDataBeans.setIssueType(cellValue);
                                break;
                            case 2:
                                nftrDataBeans.setIssueSubType(cellValue);
                                break;
                            case 3:
                                nftrDataBeans.setIssueSubSubType(cellValue);
                                break;
                            case 4:
                                nftrDataBeans.setIssueCode(cellValue);
                                break;
                            case 5:
                                nftrDataBeans.setIssueFieldLabel1(cellValue);
                                break;
                            case 6:
                                nftrDataBeans.setIssueFieldType1(cellValue);
                                break;
                            case 7:
                                nftrDataBeans.setIssueFieldMandatory1(cellValue);
                                break;
                            case 8:
                                nftrDataBeans.setIssueFieldLabel2(cellValue);
                                break;
                            case 9:
                                nftrDataBeans.setIssueFieldType2(cellValue);
                                break;
                            case 10:
                                nftrDataBeans.setIssueFieldMandatory2(cellValue);
                                break;
                            case 11:
                                nftrDataBeans.setIssueFieldLabel3(cellValue);
                                break;
                            case 12:
                                nftrDataBeans.setIssueFieldType3(cellValue);
                                break;
                            case 13:
                                nftrDataBeans.setIssueFieldMandatory3(cellValue);
                                break;
                            case 14:
                                nftrDataBeans.setIssueFieldLabel4(cellValue);
                                break;
                            case 15:
                                nftrDataBeans.setIssueFieldType4(cellValue);
                                break;
                            case 16:
                                nftrDataBeans.setIssueFieldMandatory4(cellValue);
                                break;
                            case 17:
                                nftrDataBeans.setIssueFieldLabel5(cellValue);
                                break;
                            case 18:
                                nftrDataBeans.setIssueFieldType5(cellValue);
                                break;
                            case 19:
                                nftrDataBeans.setIssueFieldMandatory5(cellValue);
                                break;
                            case 20:
                                nftrDataBeans.setIssueFieldLabel6(cellValue);
                                break;
                            case 21:
                                nftrDataBeans.setIssueFieldType6(cellValue);
                                break;
                            case 22:
                                nftrDataBeans.setIssueFieldMandatory6(cellValue);
                                break;
                            case 23:
                                nftrDataBeans.setIssueFieldLabel7(cellValue);
                                break;
                            case 24:
                                nftrDataBeans.setIssueFieldType7(cellValue);
                                break;
                            case 25:
                                nftrDataBeans.setIssueFieldMandatory7(cellValue);
                                break;
                            case 26:
                                nftrDataBeans.setWorkgroup1(cellValue);
                                break;
                            case 27:
                                nftrDataBeans.setSLA1(cellValue);
                                break;
                            case 28:
                                nftrDataBeans.setWorkgroup2(cellValue);
                                break;
                            case 29:
                                nftrDataBeans.setSLA2(cellValue);
                                break;
                            case 30:
                                nftrDataBeans.setWorkgroup3(cellValue);
                                break;
                            case 31:
                                nftrDataBeans.setSLA3(cellValue);
                                break;
                            case 32:
                                nftrDataBeans.setWorkgroup4(cellValue);
                                break;
                            case 33:
                                nftrDataBeans.setSLA4(cellValue);
                                break;
                            case 34:
                                nftrDataBeans.setCommittedSLA(cellValue);
                                break;
                            case 35:
                                nftrDataBeans.setAssignmentQueue(cellValue);
                                break;
                            case 36:
                                nftrDataBeans.setPriority(cellValue);
                                break;
                            case 37:
                                nftrDataBeans.setTicketNumber(cellValue);
                                break;

                        }
                    }
                }


                if (cells.getRowNum() != 0) {
                    userCredsBeanList.add(nftrDataBeans);
                }
            }
        } catch (

                IOException e) {
            e.printStackTrace();
        }
        return userCredsBeanList;
    }
}
