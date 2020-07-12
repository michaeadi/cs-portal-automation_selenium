package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
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
            ChromeOptions options = new ChromeOptions();
            // Tested in Google Chrome 59 on Linux. More info on:
            // https://developers.google.com/web/updates/2017/04/headless-chrome
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
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
