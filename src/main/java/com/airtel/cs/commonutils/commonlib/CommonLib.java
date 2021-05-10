package com.airtel.cs.commonutils.commonlib;

import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.driver.Driver;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CommonLib extends Driver {
    public void fail(String message, boolean requireScreenshot) {
        LOGGER.error(message);
        reporter.showInExtentReport(Status.FAIL, message, requireScreenshot);
    }

    public void error(String message, boolean requireScreenshot) {
        LOGGER.error(message);
        reporter.showInExtentReport(Status.FAIL, message, requireScreenshot);
    }

    public void warning(String message) {
        LOGGER.warn(message);
        reporter.showInExtentReport(Status.WARNING, message, false);
    }

    public void skip(String message) {
        LOGGER.warn(message);
        reporter.showInExtentReport(Status.SKIP, message, false);
    }

    public void hardWait(int time, String reasonForWait) {
        try {
            if (!reasonForWait.equals("")) {
                info(reasonForWait + " For - " + time + " seconds");
            }
            time = time * 1000;
            Thread.sleep(time);
        } catch (Exception e) {
            fail("ERROR inside method -[hardWait]" + e.getMessage(), true);
        }
    }

    public void hardWait(int time) {
        hardWait(time, "");
    }

    public void hardWait() {
        hardWait(3);
    }

    public void captureConsoleLogs() {
        info("Printing Browser Console Logs : ");
        try {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            if (logEntries != null) {
                for (LogEntry entry : logEntries) {
                    List<String> strings = Arrays.asList("error", "fail", "exception");
                    if (strings.stream().anyMatch(s -> entry.toString().contains(s))) {
                        fail(" " + entry.getLevel() + " " + entry.getMessage(), false);
                    }
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public String getBrowserConsoleInformation() {
        String sessionId = "";
        try {
            String command = "sessionId";
            Object jsResult = ((JavascriptExecutor) driver).executeScript("return " + command);
            info("Session id - " + jsResult.toString());
            sessionId = jsResult.toString();

        } catch (Exception e) {
            commonLib.error(e.getMessage());
        }
        return sessionId;
    }

    /**
     * The Log.
     */
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    /**
     * Logs Info tag - This will consist info for all steps happening within
     * test-case
     */

    public void infoHighlight(String string1WithoutHighlight, String string2WithHighlight,
                              ReportInfoMessageColorList reportInfoMessageColorList) {
        LOGGER.info(string1WithoutHighlight.concat(string2WithHighlight));
        infoHighlight(string1WithoutHighlight, string2WithHighlight, reportInfoMessageColorList, false);
    }

    public void infoHighlight(String string1WithoutHighlight, String string2WithHighlight,
                              ReportInfoMessageColorList reportInfoMessageColorList, Boolean requiredScreenshot) {
        reporter.showInExtentReportTextHighlight(string1WithoutHighlight, string2WithHighlight,
                reportInfoMessageColorList, requiredScreenshot);
    }

    public void infoHighlightUnmatchedValue(String itemNamesForComparision, String actualValues,
                                            String expectedValues) {
        reporter.showInExtentReportHighlightUnmatchedValue(itemNamesForComparision, actualValues, expectedValues);
    }


    public void info(String message) {
        info(message, false);
    }

    public void logs(String message) {
        LOGGER.info(message);
    }

    public void info(String message, boolean requireScreenshot) {
        LOGGER.info(message);
        reporter.showInExtentReport(Status.INFO, message, requireScreenshot);
    }

    public void infoColored(String message, JavaColors javaColors, boolean requireScreenshot) {
        LOGGER.info(message);
        if (javaColors.equals(JavaColors.GREEN)) {
            reporter.showInExtentReport(Status.PASS, message, requireScreenshot);
        } else if (javaColors.equals(JavaColors.RED)) {
            reporter.showInExtentReport(Status.FAIL, message, requireScreenshot);
        } else {
            reporter.showInExtentReport(Status.INFO, message, requireScreenshot);
        }
    }

    public void pass(String message) {
        LOGGER.info(message);
        reporter.showInExtentReport(Status.PASS, message, false);
    }

    public void setStatusInReport(boolean status, String message, boolean requireScreenshot) {
        if (status) {
            LOGGER.info(message);
            reporter.showInExtentReport(Status.PASS, message + " - is visible successfully", requireScreenshot);
        } else {
            LOGGER.error(message);
            reporter.showInExtentReport(Status.FAIL, message + " - is NOT VISIBLE", requireScreenshot);
        }
    }

    public void setStatusInReport(boolean status, String passMessage, String failMessage, boolean requireScreenshot) {
        if (status) {
            LOGGER.info(passMessage);
            reporter.showInExtentReport(Status.PASS, passMessage, requireScreenshot);
        } else {
            LOGGER.error(failMessage);
            reporter.showInExtentReport(Status.FAIL, failMessage, requireScreenshot);
        }
    }

    public void error(String message) {
        error(message, false);
    }

    /*
     * This method will reload The Page using Java script
     */
    public void doReloadByJavaScript() {

        try {
            info("Going to reload the browser");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("location.reload();");

            if (selUtils.isAlertPresent()) {
                driver.switchTo().alert().accept();
            }
        } catch (Exception e) {
            fail("Failed to Reload Page -" + e.getMessage(), true);
        }

    }

    public String getNowDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date dateobj = new Date();
        return df.format(dateobj);
    }

    public String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            sb.append(str).append(delimiter);
        }
        return sb.substring(0, sb.length() - 1);
    }

    public String convertStringListToString(List<String> strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            sb.append(str).append(delimiter);
        }
        return sb.substring(0, sb.length() - 1);
    }

    public int getRandomNumberBetweenTwoNumbers(int startRange, int endRange) {
        Random r;
        r = new Random();
        return r.nextInt(endRange - startRange) + startRange;
    }

    public long get10DigitRandomNumber() {
        int digitCount = 0;
        long theRandomNum = 0;
        long tempNumber;
        while (digitCount != 10) {
            theRandomNum = (long) (Math.random() * Math.pow(10, 10));
            tempNumber = theRandomNum;
            while (tempNumber > 0) {
                tempNumber = tempNumber / 10;
                digitCount = digitCount + 1;
            }
            if (digitCount != 10) {
                digitCount = 0;
            }
        }
        return theRandomNum;
    }

    /* --------- METHODS FOR 2nd BROWSER ------------- */

    public void openNewTemporaryBrowser2Nd() {
        try {
            defaultDriver = driver;
            ChromeOptions options;
            options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-extensions");
            options.addArguments("chrome.switches", "--disable-extensions");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("enable-automation");
            options.addArguments("--disable-browser-side-navigation");
            options.addArguments("--dns-prefetch-disable");
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--headless", "window-size=1920,1080", "--no-sandbox");
            tempDriver = new ChromeDriver(options);
            driver = tempDriver;
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            tempWindowHandle = tempDriver.getWindowHandle();
        } catch (Exception e) {
            fail("Exception Caught in Method - open_NewTemporaryBrowser_2nd" + "</br>" + "Exception Message - "
                    + e.getMessage(), true);
        }
    }
}