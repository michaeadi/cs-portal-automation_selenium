package tests;

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
    public static List<Header> map = new ArrayList<>();
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
        FileInputStream fis;
        fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + Opco + "-config.properties");
        config.load(fis);
        baseUrl = config.getProperty(Env + "-APIBase");
        String browser = config.getProperty("browser");
        System.out.println(baseUrl);
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




}