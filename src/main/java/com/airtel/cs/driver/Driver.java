package com.airtel.cs.driver;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstantsUtils;
import com.airtel.cs.commonutils.applicationutils.constants.ConstantsUtils;
import com.airtel.cs.commonutils.commonlib.CommonLib;
import com.airtel.cs.commonutils.extentreports.ExtentReport;
import com.airtel.cs.commonutils.seleniumutils.SeleniumCommonUtils;
import com.airtel.cs.pagerepository.pagemethods.PageCollection;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.Header;
import lombok.extern.log4j.Log4j2;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class Driver {


    private static final String PATH_DELIMITER = "/";
    private static final String USER_DIR = "user.dir";
    public static final String OPCO = System.getProperty("Opco").toUpperCase();
    public static WebDriver driver;
    public static WebDriver default_Driver = null;
    public static WebDriver temp_Driver = null;
    public static String loginURL = null;
    public static String parentWindowHandle;
    public static String tempWindowHandle;
    public static PageCollection pages;
    public static final CommonLib commonLib = new CommonLib();
    protected static final Properties config = new Properties();
    public static String evnName = System.getProperty("Env").toUpperCase();
    public static final StringBuilder TESTCASE_DESCRIPTION_BUILDER = new StringBuilder(); // FOR ADDING TESTCASE DESCRIPTION IN EXTNT REPORTS
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSZ";
    public static String excelPath;
    public static List<Header> map = new ArrayList<>();
    public static String Token;
    public static String baseUrl;
    public static String umBaseUrl;
    public static StringBuilder assertCheck;
    public static final ExtentReport reporter = new ExtentReport();
    public static final SeleniumCommonUtils selUtils = new SeleniumCommonUtils();
    public static final LocalDateTime dateTime = new LocalDateTime();
    public static ExtentTest test;
    public static ExtentReports extent;
    public static ConstantsUtils constants = ConstantsUtils.getInstance();
    public static PermissionConstantsUtils commonConstants = PermissionConstantsUtils.getInstance();
    public static Recordset recordset = null;
    public static boolean continueExecutionAPI = true;
    public static boolean continueExecutionBA = true;
    public static boolean continueExecutionBS = true;
    public static boolean continueExecutionFA = true;
    public static String ElementName = ""; // FOR PASSING ELEMENT NAMES TO LOGS
    public static String Message = null;
    public static String token = null;
    public static String RUN_TARIFF_TEST_CASE = constants.getValue(ApplicationConstants.RUN_TARIFF_TEST_CASE);
    public static final String HTMLFILE_PATH = System.getProperty(USER_DIR) + "/resources/htmlreport/" + OPCO + "-" + evnName + PATH_DELIMITER;
    public static final String SUITE_TYPE = System.getProperty("suiteType");
    public static final String EXTENT_REPORT_CONFIG_FILE_LOCATION = System.getProperty(USER_DIR)
            + "/resources/properties/reportextent-config.xml";
    public static final String CLIENT = System.getProperty("Client").toUpperCase();
    private static String browser = null;


    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite(alwaysRun = true)
    public void setup(ITestContext tr) {
        try {
            reportConfigureBase(tr);
            envLevelSetup();
            startBrowser(browser);
            initializePages();
        } catch (Exception e) {
            commonLib.fail(e.getMessage(), true);
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        try {
            initializePages();
        } catch (Exception e) {
            commonLib.error(e.getMessage());
        }
    }

    @BeforeMethod
    public void methodLevelSetup() {
        assertCheck = new StringBuilder(); // @ THIS WILL EMPTY ASSERT STRING-BUILDER BEFORE EACH TEST
    }

    @AfterSuite
    public void teardown() {
        driver.close();
        driver.quit();
    }

    /**
     * Initialize pages.
     */
    public static void initializePages() {
        pages = new PageCollection(driver);
    }

    /**
     * Goto URL.l
     *
     * @param url the url
     */
    // open url method
    public void gotoURL(String url) {
        try {
            commonLib.info("OPENING URL -" + url);
            driver.get(url);
            try {
                driver.switchTo().alert().accept();
            } catch (Exception e) {
            }
            selUtils.waitForPageLoad();
        } catch (Exception e) {
            e.getStackTrace();
            commonLib.warning("Exception in method - | gotoURL | " + "</br>" + e.getMessage());
        }
    }

    private static void reportConfigureBase(ITestContext tr) {
        try {
            String currentClassName = tr.getAllTestMethods()[0].getInstance().toString();
            String reportTitle = null;
            if (currentClassName.toLowerCase().contains("api")) {
                reportTitle = "_APIScenarios_";
            } else if (currentClassName.toLowerCase().contains("ui")) {
                reportTitle = "_CSPortalScenarios_";
            }
            String modifiedHtmlfilePath = null;
            modifiedHtmlfilePath = HTMLFILE_PATH + SUITE_TYPE + reportTitle + dateTime.toString(DATE_FORMAT) + ".html";
            browser = constants.getValue(ApplicationConstants.WEB_BROWSER);
            extent = new ExtentReports(modifiedHtmlfilePath);
            extent.loadConfig(new File(EXTENT_REPORT_CONFIG_FILE_LOCATION));
            extent.addSystemInfo("Application Environment ", OPCO + " " + evnName);
            extent.addSystemInfo("Execution Browser ", browser);
            extent.addSystemInfo("Language Selected ", "English");
        } catch (Exception ex) {
            commonLib.fail(ex.getMessage(), true);
        }
    }

    /*
    This Method is used for environment level setup
     */
    private static void envLevelSetup() throws IOException {

        excelPath = System.getProperty(USER_DIR) + "/resources/excels/" + OPCO + ".xlsx";
        try (FileInputStream fis = new FileInputStream(System.getProperty(USER_DIR) + "/resources/properties/" + OPCO + "-config.properties")) {
            config.load(fis);
        } catch (IOException e) {
            commonLib.error(e.getMessage());
        }
        if (evnName.equalsIgnoreCase("sit")) {
            evnName = "SIT";
        } else if (evnName.equalsIgnoreCase("uat")) {
            evnName = "UAT";
        } else {
            evnName = "PROD";
        }
        baseUrl = config.getProperty(evnName + "-APIBase");
        umBaseUrl = constants.getValue(ApplicationConstants.UM_API_BASE);
        loginURL = constants.getValue(ApplicationConstants.UM_LOGIN_URL);
        assertCheck = new StringBuilder(); // @ THIS WILL EMPTY ASSERT STRING-BUILDER BEFORE EACH TEST
    }

    /*
    This Method is used to start the browser
     */
    private static void startBrowser(String browser) {
        try {
            switch (browser) {
                case "chrome":
                    //commonLib.info("Browser selected for execution - Chrome");
                    WebDriverManager.chromedriver().setup();
                    browserCapabilities();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            commonLib.fail(e.getMessage(), true);
        }
    }

    /*
    This Method is used to set the browser capabilities
     */
    private static void browserCapabilities() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1792,1120");
        options.setHeadless(false);
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", excelPath);
        prefs.put("intl.accept_languages", "nl");
        prefs.put("disable-popup-blocking", "true");
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
}