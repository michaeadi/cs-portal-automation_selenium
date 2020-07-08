package Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    static String Projectpath;
    static XSSFWorkbook workbook;
    static XSSFSheet Sheet;

    public ExcelUtils (String excelpath, String sheetname) {
        try {
            workbook = new XSSFWorkbook(excelpath);
            Sheet = workbook.getSheet(sheetname);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getrowcount();
        getcelldata(0, 0);
        getcelldataNumber(1, 1);
        getcolcount();

    }

    public static int getrowcount() {
        int rowcount = 0;
        rowcount = Sheet.getPhysicalNumberOfRows();
        System.out.println("rows in excel :" + rowcount);
        return rowcount;

    }

    public static String getcelldata(int rownum, int colnum) {

        return Sheet.getRow(rownum).getCell(colnum).getStringCellValue();
    }

    public static String getcelldataNumber(int rownum, int colnum) {
        Cell          cell1         = Sheet.getRow(rownum).getCell(colnum);
        DataFormatter dataFormatter = new DataFormatter ();
        return dataFormatter.formatCellValue(cell1);

    }

    public static int getcolcount() {
        int colcount = 0;

        colcount = Sheet.getRow(0).getPhysicalNumberOfCells();
        System.out.println("Columns in excel :" + colcount);
        return colcount;


    }

}
