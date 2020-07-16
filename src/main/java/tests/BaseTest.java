package tests;

import Utils.DataProviders.nftrDataBeans;
import Utils.DataProviders.nftrDataExcelToBeanDao;
import Utils.TestDatabean;
import Utils.TicketStateDataBean;
import Utils.ftrDataBeans;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import Utils.ReadData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BaseTest {


    public static WebDriver driver;
    public static Properties config;
    public String opco;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void classLevelSetup() throws IOException {
        opco = "KE";
        config = new Properties();
        FileInputStream fis = null;
        fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + opco + "-config.properties");
        config.load(fis);
        String browser = config.getProperty("browser");
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }
    }

    @BeforeMethod
    public void methodLevelSetup() {

    }

    @AfterSuite
    public void teardown() {
        driver.quit();
    }

    @DataProvider
    public Object[][] getTestData() {
        Utils.TestDataExcelToBeanDao credsExcelToBeanDao = new Utils.TestDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "test.xlsx");
        List<TestDatabean> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("Sheet"));
        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @DataProvider
    public Object[][] getTestData1() {
        Utils.ftrDataExcelToBeanDao credsExcelToBeanDao = new Utils.ftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "test.xlsx");
        List<ftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("FtrSheet"));

        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }


//    @DataProvider(name = "ticketId")
//    public Object[][] providerTicketId() throws IOException {
//        File Exceldir = new File("Excels");
//        File Excel = new File(Exceldir, "test.xlsx");
//        Object[][] data = ReadData.getData(Excel.getAbsolutePath(), config.getProperty("ticketId"));
//        return data;
//    }

    @DataProvider(name="ticketState")
    public Object[][] ticketStateList() {
        Utils.TicketStateToBean ticketStateToBean = new Utils.TicketStateToBean();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "test.xlsx");
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(Excel.getAbsolutePath(), config.getProperty("ticketState"));
        List<TicketStateDataBean> closeState = new ArrayList<TicketStateDataBean>();
        List<TicketStateDataBean> openState = new ArrayList<TicketStateDataBean>();
        for(TicketStateDataBean state: list){
            if(state.getInternalState().equals(config.getProperty("closeState"))){
                closeState.add(state);
            }else{
                openState.add(state);
            }
        }
        Object[][] hashMapObj = new Object[1][1];

            hashMapObj[0][0] = closeState.get(0);

        return hashMapObj;
    }

    @DataProvider(name="ticketId")
    public Object[][] getTestData2() {
        nftrDataExcelToBeanDao credsExcelToBeanDao = new nftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "test.xlsx");
        List<nftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("NftrSheet"));
        List<nftrDataBeans> finalTicketList = new ArrayList<nftrDataBeans>();
        for(nftrDataBeans nftrTicket: list){
            System.out.println("Ticket Id: "+nftrTicket.getTicketNumber());
            if(nftrTicket.getTicketNumber()==null){
                System.out.println("No Ticket ID Found");
            }else{
                finalTicketList.add(nftrTicket);
            }
        }

        Object[][] hashMapObj = new Object[finalTicketList.size()][1];
        for (int i = 0; i < finalTicketList.size(); i++) {
            hashMapObj[i][0] = finalTicketList.get(i);
        }
        return hashMapObj;
    }



}
