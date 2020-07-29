package tests;

import Utils.DataProviders.nftrDataBeans;
import Utils.DataProviders.nftrDataExcelToBeanDao;
import Utils.TicketStateDataBean;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.Header;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Log4j2
public class BaseTest {


    public static WebDriver driver;
    public static Properties config;
    public static String Opco;
    public static String Env;
    public static String ExcelPath;
    public static List<Header> map = new ArrayList<Header>();
    public static String Token;
    public static String baseUrl;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void classLevelSetup() throws IOException {
        Opco = "KE";
        Env = "UAT";
//        Opco = System.getProperty("Opco").toUpperCase();
//        Env = System.getProperty("Env").toUpperCase();
        ExcelPath = Opco + ".xlsx";
        config = new Properties();
        System.out.println("OPCO Chosen :" + Opco);
        System.out.println("Environment Chosen : " + Env);
        FileInputStream fis = null;
        fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + Opco + "-config.properties");
        config.load(fis);
        baseUrl = config.getProperty(Env + "-APIBase");
        String browser = config.getProperty("browser");
        System.out.println(baseUrl);
//        PrintStream lo = new PrintStream();
//        RestAssured.filters(new RequestLoggingFilter(lo), new ResponseLoggingFilter(lo));
//        PrintStream logStream = IoBuilder.forLogger(LogManager.getLogger("system.out"))
//                .setLevel(Level.INFO)
//                .buildPrintStream()
//                .forLogger(log).buildPrintStream();
//        RestAssuredConfig restAssuredConfig = new RestAssuredConfig();
//        LogConfig logConfig = restAssuredConfig.getLogConfig();
//        logConfig
//                .defaultStream(Log4j2)
//                .enablePrettyPrinting(true);
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
//            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1792,1120");
            options.setHeadless(true);
            //Using with Options will start in Headless Browser
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
//        driver.quit();
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
