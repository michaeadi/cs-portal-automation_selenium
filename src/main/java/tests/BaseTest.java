package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import pages.sideMenuPOM;
import utils.ReadData;
import utils.TestDatabean;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class BaseTest {


    public static WebDriver driver;
    public Properties config;
    public String opco;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeClass
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

//        sideMenuPOM sideMenuPOM = new sideMenuPOM(driver);
    }

    @AfterClass
    public void teardown() {
//        driver.quit ();
    }

    @DataProvider
    public Object[][] getTestData() {
//        config = settingEnviroment (Env);
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


    @DataProvider(name = "ticketId")
    public Object[][] providerTicketId() throws IOException {
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "test.xlsx");
        Object[][] data = ReadData.getData(Excel.getAbsolutePath(), config.getProperty("ticketId"));
        return data;
    }


}
