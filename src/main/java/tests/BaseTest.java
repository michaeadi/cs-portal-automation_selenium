package tests;

import Utils.DataProviders.nftrDataBeans;
import Utils.DataProviders.nftrDataExcelToBeanDao;
import Utils.TicketStateDataBean;
import io.github.bonigarcia.wdm.WebDriverManager;
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
public class BaseTest {


    public static WebDriver driver;
    public static Properties config;
    public static String Opco;
    public static String Env;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void classLevelSetup() throws IOException {
//        Opco = "KE";
//        Env = "UAT";
        Opco = System.getProperty("Opco").toUpperCase();
        Env = System.getProperty("Env").toUpperCase();
        config = new Properties();

        System.out.println("OPCO Chosen :" + Opco);
        System.out.println("Environment Chosen : " + Env);
        FileInputStream fis = null;
        fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + Opco + "-config.properties");
        config.load(fis);
        String browser = config.getProperty("browser");
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

}
