package com.airtel.cs.driver;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.ConstantsUtils;
import com.airtel.cs.commonutils.commonlib.CommonLib;
import com.airtel.cs.commonutils.extentreports.ExtentReport;
import com.airtel.cs.commonutils.listeners.TestListenerMethod;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.commonutils.seleniumutils.SeleniumCommonUtils;
import com.airtel.cs.pagerepository.pagemethods.PageCollection;
import com.airtel.cs.pagerepository.pagemethods.WidgetCommonMethod;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.codoid.products.fillo.Recordset;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.Header;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;

@Log4j2
public class Driver {

    private static final String PATH_DELIMITER = "/";
    private static final String USER_DIR = "user.dir";
    public static final String OPCO = System.getProperty("Opco").toUpperCase();
    public static WebDriver driver;
    public static WebDriver defaultDriver = null;
    public static WebDriver tempDriver = null;
    public static String loginURL = null;
    public static String parentWindowHandle;
    public static String tempWindowHandle;
    public static PageCollection pages;
    public static final CommonLib commonLib = new CommonLib();
    protected static final Properties config = new Properties();
    public static String evnName = System.getProperty("Env").toUpperCase();
    public static final StringBuilder TESTCASE_DESCRIPTION_BUILDER = new StringBuilder(); // FOR ADDING TESTCASE DESCRIPTION IN EXTNT REPORTS
    public static final String DATE_FORMAT = "yyyy-MM-dd HH-mm-ss.SSSZ";
    public static String excelPath;
    public static List<Header> map = new ArrayList<>();
    public static String token;
    public static String baseUrl;
    public static String srBaseUrl;
    public static String umBaseUrl;
    public static String srUMBaseUrl;
    public static StringBuilder assertCheck;
    public static final ExtentReport reporter = new ExtentReport();
    public static final SeleniumCommonUtils selUtils = new SeleniumCommonUtils();
    public static final LocalDateTime dateTime = new LocalDateTime();
    public static ExtentTest test;
    public static ExtentReports extent;
    public static ExtentSparkReporter spark;
    public static ConstantsUtils constants = ConstantsUtils.getInstance();
    public static Recordset recordset = null;
    public static boolean continueExecutionAPI = true;
    public static boolean continueExecutionBA = true;
    public static boolean continueExecutionBS = true;
    public static boolean continueExecutionFA = true;
    public static boolean continueExecutionBU = true;
    public static boolean continueUnAssignment = false;
    public static String elementName = ""; // FOR PASSING ELEMENT NAMES TO LOGS
    public static String message = null;
    public static final String RUN_TARIFF_TEST_CASE = constants.getValue(ApplicationConstants.RUN_TARIFF_TEST_CASE);
    public static final String HTML_FILE_PATH = System.getProperty(USER_DIR) + "/resources/htmlreport/" + OPCO.toLowerCase() + "-" + evnName.toLowerCase() + PATH_DELIMITER;
    public static String MODIFIED_HTML_FILE_PATH = "";
    public static final String SUITE_TYPE = System.getProperty("suiteType");
    private static String browser = null;
    public static String currentTestCaseName;
    public static String currentClassName;
    public static String nftrSheetValue;
    public static String ftrSheetValue;
    public static String reason;
    public static String loginAUUID;
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static WidgetCommonMethod widgetMethods;
    public static String download = System.getProperty(USER_DIR) + "\\resources\\excels\\";
    public static String authToken;
    public static final BaseActions actions = new BaseActions();
    public static final RestCommonUtils restUtils = new RestCommonUtils();
    public static final RequestSource api = new RequestSource();
    public static boolean isSelfcareNOTConfigured = false;
    public static List<Header> validHeaderList = new ArrayList<>();
    public static List<Header> invalidHeaderList = new ArrayList<>();

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite(alwaysRun = true)
    public void setup(ITestContext tr) {
        try {
            reportConfigureBase(tr);
            envLevelSetup();
            startBrowser(browser);
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

    @BeforeMethod(alwaysRun = true)
    public static void methodLevelSetup(ITestResult tr) {
        try {
            currentClassName = getClassName(tr);
            ExtentReport.startTest(currentClassName, currentTestCaseName);
            assertCheck = new StringBuilder(); // @ THIS WILL EMPTY ASSERT STRING-BUILDER BEFORE EACH TEST
        } catch (Exception ex) {
            commonLib.error(ex.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public static void endTest() {
        try {
            ExtentReport.endTest();
        } catch (Exception e) {
            commonLib.error(e.getMessage());
        }
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        try {
            ExtentReport.endTest();
        } catch (Exception e) {
            commonLib.error(e.getMessage());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void closeReporting(ITestContext iTestContext) {
        ExtentReport.endTest();
        driver.close();
        if (driver != null) {
            driver.quit();
        }
        createEmailableReport();
    }

    /**
     * This method is used to create emailable html report
     */
    private void createEmailableReport() {
        try {
            String templatePath = System.getProperty("user.dir") + constants.getValue("template.file.path");
            File input = new File(templatePath);
            Document doc = Jsoup.parse(input, "UTF-8");
            Element table = doc.select("table.table-bordered").first();
            Element td = table.select("td[class=total]").first();
            td.text(Integer.toString(TestListenerMethod.totalTest));
            td = table.select("td[class=pass]").first();
            td.text(Integer.toString(TestListenerMethod.passedTest));
            td = table.select("td[class=fail]").first();
            td.text(Integer.toString(TestListenerMethod.failedTest));
            td = table.select("td[class=skip]").first();
            td.text(Integer.toString(TestListenerMethod.skippedTest));
            td = table.select("td[class=opcoEnv]").first();
            td.text(Driver.OPCO + "/" + Driver.evnName);
            td = table.select("td[class=reportUrl]").first();
            td.text(MODIFIED_HTML_FILE_PATH);
            final File f = new File(System.getProperty(USER_DIR) + constants.getValue("emailable.report.path"));
            FileUtils.writeStringToFile(f, doc.outerHtml(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            commonLib.fail("Exception in Method - createEmailableReport" + e.getMessage(), false);
        }
    }

    /**
     * Initialize pages.
     */
    public static void initializePages() {
        pages = new PageCollection(driver);
        widgetMethods = pages.getWidgetCommonMethod();
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
            driver.switchTo().alert().accept();
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
            MODIFIED_HTML_FILE_PATH = HTML_FILE_PATH + SUITE_TYPE + reportTitle + dateTime.toString(DATE_FORMAT) + ".html";
            browser = constants.getValue(ApplicationConstants.WEB_BROWSER);
            extent = new ExtentReports();
            spark = new ExtentSparkReporter(MODIFIED_HTML_FILE_PATH);
            extent.attachReporter(spark);
            spark.config().setDocumentTitle("Airtel Africa CS Automation");
            spark.config().setReportName("Automation Report -" + OPCO + " CS Portal");
            extent.setSystemInfo("Application Environment ", OPCO + " " + evnName);
            extent.setSystemInfo("Execution Browser ", browser.toUpperCase());
            extent.setSystemInfo("Language Selected ", "English");
            extent.setSystemInfo("Suite Type", SUITE_TYPE.toUpperCase());
            nftrSheetValue = constants.getValue(CommonConstants.SUITE_TYPE).equals(SUITE_TYPE) ? CommonConstants.SANITY_NFTR_SHEET : CommonConstants.REGRESSION_NFTR_SHEET;
            ftrSheetValue = constants.getValue(CommonConstants.SUITE_TYPE).equals(SUITE_TYPE) ? CommonConstants.SANITY_FTR_SHEET : CommonConstants.REGRESSION_FTR_SHEET;
        } catch (Exception ex) {
            commonLib.fail("Exception in Method - reportConfigureBase " + ex.getMessage(), false);
        }
    }

    /*
    This Method is used for environment level setup
     */
    private static void envLevelSetup() {

        excelPath = System.getProperty(USER_DIR) + "/resources/excels/" + OPCO + ".xlsx";
        try (FileInputStream fis = new FileInputStream(System.getProperty(USER_DIR) + "/resources/properties/" + OPCO.toLowerCase() + "-" + evnName.toLowerCase() + ".properties")) {
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
        baseUrl = constants.getValue(ApplicationConstants.API_BASE);
        umBaseUrl = constants.getValue(ApplicationConstants.UM_API_BASE);
        loginURL = constants.getValue(ApplicationConstants.UM_LOGIN_URL);
        srBaseUrl = constants.getValue(ApplicationConstants.SR_API_BASE);
        srUMBaseUrl = constants.getValue(ApplicationConstants.SR_UM_API_BASE);
        assertCheck = new StringBuilder(); // @ THIS WILL EMPTY ASSERT STRING-BUILDER BEFORE EACH TEST
    }

    /*
    This Method is used to start the browser
     */
    private static void startBrowser(String browser) {
        try {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    System.out.println("Chrome setup done");
                    browserCapabilities();
                    System.out.println("Browser setup done");
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
        System.out.println("In browserCapabilities");
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences loggingprefs = new LoggingPreferences();
        loggingprefs.enable(LogType.BROWSER, Level.WARNING);
        loggingprefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.addArguments("--no-sandbox", "--window-size=1792,1120", "--verbose", "--disable-dev-shm-usage");
        options.setHeadless(true);
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", download);
        prefs.put("intl.accept_languages", "nl");
        prefs.put("disable-popup-blocking", "true");
        options.setExperimentalOption("prefs", prefs);
        options.setAcceptInsecureCerts(true);
        options.setCapability("goog:loggingPrefs", loggingprefs);
        options.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    /**
     * THIS WILL GET - CURRENT CLASS NAME, METHOD NAME SET TO GLOBAL VARIABLE -
     */
    public static String getClassName(ITestResult tr) {
        String className = tr.getMethod().getInstance().getClass().getName();
        int classNameStartingIndex = className.lastIndexOf('.');
        className = className.substring(classNameStartingIndex + 1, className.length());
        currentTestCaseName = tr.getMethod().getMethodName();
        return className;
    }
}