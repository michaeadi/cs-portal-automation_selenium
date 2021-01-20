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
import java.util.*;

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
    public static String suiteType;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void classLevelSetup() throws IOException {
//        Opco = "MW";
//        Env = "UAT";
//        suiteType = "Sanity";


        if (System.getProperty("suiteType").equalsIgnoreCase("Regression")) {
            suiteType = "Regression";
        } else if (System.getProperty("suiteType").equalsIgnoreCase("Sanity")) {
            suiteType = "Sanity";
        }


        Opco = System.getProperty("Opco").toUpperCase();
        Env = System.getProperty("Env").toUpperCase();
        if (Env.equalsIgnoreCase("TEST")) Env = "SIT";
        ExcelPath = Opco + ".xlsx";
        config = new Properties();
        FileInputStream fis;
        fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + Opco + "-config.properties");
        config.load(fis);
        System.out.println(config.getProperty(suiteType + "-NftrSheet"));
        System.out.println("OPCO Chosen :" + Opco);
        System.out.println("Environment Chosen : " + Env);
        System.out.println("Suite Type : " + suiteType);

        baseUrl = config.getProperty(Env + "-APIBase");
        String browser = config.getProperty("browser");
        System.out.println(baseUrl);
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().proxy("172.23.12.116:4145").setup(); //Always use this on server
//            WebDriverManager.chromedriver().setup(); //Use this for local for proxy issue
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--window-size=1792,1120");
            options.setHeadless(true);
            Map<String, Object> prefs = new HashMap<String, Object>();
            //prefs.put("download.prompt_for_download", false);
            prefs.put("download.default_directory", System.getProperty("user.dir")+"\\Excels");
            prefs.put("intl.accept_languages", "nl");
            prefs.put("disable-popup-blocking", "true");
            options.setExperimentalOption("prefs", prefs);
            //Using with Options will start in Headless Browser
            driver = new ChromeDriver(options);
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


}