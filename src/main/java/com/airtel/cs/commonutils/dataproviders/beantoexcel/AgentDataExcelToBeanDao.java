package com.airtel.cs.commonutils.dataproviders.beantoexcel;

import static com.airtel.cs.driver.Driver.commonLib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.airtel.cs.commonutils.dataproviders.databeans.AgentDataBeans;

public class AgentDataExcelToBeanDao {
  


  static DataFormatter dataFormatter;
  static FormulaEvaluator evaluator;
  private static final String XLSX_FILE_EXTENSION="xlsx";

  /**
   * This method is use to get cell value
   * @param cell The Cell object
   * @return String The value
   */
  private String fetchValue(Cell cell) {
      evaluator.evaluate(cell);
      return dataFormatter.formatCellValue(cell, evaluator);
  }

  /**
   * This method is use to get all the  Agent details based on file path and sheet name
   * @param path The file path of .xlsx file name
   * @param sheetName The sheet name
   * @return List The Config
   */
  public List<AgentDataBeans> getData(String path, String sheetName) {

      List<AgentDataBeans> agentBeanList = new ArrayList<>();
      FileInputStream file;
      try {
          file = new FileInputStream(new File(path));
          Workbook workbook;
          if (path.contains(XLSX_FILE_EXTENSION)) {
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
            AgentDataBeans agentDataBean = new AgentDataBeans();
              Iterator<Cell> cellIterator = cells.cellIterator();
              while (cellIterator.hasNext()) {
                  Cell cell = cellIterator.next();

                  if (cells.getRowNum() > 0) {
                      int columnIndex = cell.getColumnIndex();
                      String cellValue = fetchValue(cell);
                      
                      switch (columnIndex) {
                          case 0:
                              agentDataBean.setAgentId(cellValue);
                              break;
                          case 1:
                              agentDataBean.setAgentAuuid(cellValue);
                              break;
                          case 2:
                              agentDataBean.setAgentName(cellValue);
                              break;
                         

                      }
                  }
              }
              
              if (cells.getRowNum() != 0) {
                  agentBeanList.add(agentDataBean);
              }
          }
      } catch (IOException e) {
          commonLib.fail("Exception found while reading the test data excel sheet with sheet name "+sheetName+". Error Log: "+e.fillInStackTrace(),false);
      }
      return agentBeanList;
  }

}
