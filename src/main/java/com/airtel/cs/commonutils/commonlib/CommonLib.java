package com.airtel.cs.commonutils.commonlib;

import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.driver.Driver;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
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
import java.util.Map;
import java.util.HashMap;

public class CommonLib extends Driver {

    private static Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    /**
     * This method is use to log the failure message into report
     * @param message The Message
     * @param requireScreenshot The screenshot True/False
     */
    public void fail(String message, boolean requireScreenshot) {
        log.error(message);
        assertCheck.append(false);
        reporter.showInExtentReport(Status.FAIL, message, requireScreenshot);
    }

    /**
     * This method is use to log the error message into report
     * @param message The Message
     * @param requireScreenshot The screenshot True/False
     */
    public void error(String message, boolean requireScreenshot) {
        fail(message,requireScreenshot);
    }

    /**
     * This method is use to log the warning message into report
     * @param message The Message
     */
    public void warning(String message) {
        warning(message,false);
    }

    /**
     * This method is use to log the warning message into report
     * @param message The Message
     * @param requireScreenshot The screenshot true/false
     */
    public void warning(String message,boolean requireScreenshot) {
        log.warn(message);
        reporter.showInExtentReport(Status.WARNING, message, requireScreenshot);
    }

    /**
     * This method is use to log the skip message into report
     * @param message The Message
     */
    public void skip(String message) {
        log.warn(message);
        reporter.showInExtentReport(Status.SKIP, message, false);
    }

    /**
     * This method use to halt the execution of test cases for specific time with user defined reason
     * @param time The time in seconds
     * @param reasonForWait The message
     */
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

    /**
     * This method use to halt the execution of test cases for specific time without any reason
     * @param time The time in seconds
     */
    public void hardWait(int time) {
        hardWait(time, "");
    }

    /**
     * This method use to halt the execution of test cases for three seconds in-case of time is not specify
     */
    public void hardWait() {
        hardWait(3);
    }

    /**
     * This method use to capture browser console log
     */
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

    /**
     * This method use to get session id from browser console
     */
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
     * Logs Info tag - This will consist info for all steps happening within
     * test-case
     */
    public void infoHighlight(String string1WithoutHighlight, String string2WithHighlight,
                              ReportInfoMessageColorList reportInfoMessageColorList) {
        log.info(string1WithoutHighlight.concat(string2WithHighlight));
        infoHighlight(string1WithoutHighlight, string2WithHighlight, reportInfoMessageColorList, false);
    }

    /**
     * Logs Info tag - This will consist info for all steps happening within
     * test-case
     */
    public void infoHighlight(String string1WithoutHighlight, String string2WithHighlight,
                              ReportInfoMessageColorList reportInfoMessageColorList, Boolean requiredScreenshot) {
        reporter.showInExtentReportTextHighlight(string1WithoutHighlight, string2WithHighlight,
                reportInfoMessageColorList, requiredScreenshot);
    }

    /**
     * Logs Info tag - This will consist info for all steps happening within
     * test-case
     */
    public void infoHighlightUnmatchedValue(String itemNamesForComparision, String actualValues,
                                            String expectedValues) {
        reporter.showInExtentReportHighlightUnmatchedValue(itemNamesForComparision, actualValues, expectedValues);
    }

    /**
     * This method used to log the info message into report
     * @param message The message
     */
    public void info(String message) {
        info(message, false);
    }

    /**
     * This method used to log the message into report
     * @param message The message
     */
    public void logs(String message) {
        log.info(message);
    }

    /**
     * This method used to log the info message into report
     * @param message The message
     * @param requireScreenshot The Screenshot True/False
     */
    public void info(String message, boolean requireScreenshot) {
        log.info(message);
        reporter.showInExtentReport(Status.INFO, message, requireScreenshot);
    }

    /**
     * This method used to log the info message into report with different colors
     * Based on Color Status Message change
     * Green Color -> represent pass Message
     * Red Color -> represent fail Message
     * Other color -> represent info Message
     * @param message The message
     * @param javaColors The Color
     * @param requireScreenshot The Screenshot True/False
     */
    public void infoColored(String message, JavaColors javaColors, boolean requireScreenshot) {
        log.info(message);
        if (javaColors.equals(JavaColors.GREEN)) {
            reporter.showInExtentReport(Status.PASS, message, requireScreenshot);
        } else if (javaColors.equals(JavaColors.RED)) {
            reporter.showInExtentReport(Status.FAIL, message, requireScreenshot);
        } else {
            reporter.showInExtentReport(Status.INFO, message, requireScreenshot);
        }
    }

    /**
     * This method used to log the pass message into report
     * @param message The message
     */
    public void pass(String message) {
        log.info(message);
        reporter.showInExtentReport(Status.PASS, message, false);
    }

    /**
     * This method is used to set the status pass or fail based on status
     * @param status The status True/False
     * @param message The Message
     * @param requireScreenshot The Screenshot True/False
     */
    public void setStatusInReport(boolean status, String message, boolean requireScreenshot) {
        if (status) {
            log.info(message);
            reporter.showInExtentReport(Status.PASS, message + " - is visible successfully", requireScreenshot);
        } else {
            log.error(message);
            reporter.showInExtentReport(Status.FAIL, message + " - is NOT VISIBLE", requireScreenshot);
        }
    }

    /**
     * This method is used to set the status pass or fail based on status
     * @param status The status True/False
     * @param passMessage The pass message
     * @param failMessage The fail message
     * @param requireScreenshot The Screenshot True/False
     */
    public void setStatusInReport(boolean status, String passMessage, String failMessage, boolean requireScreenshot) {
        if (status) {
            log.info(passMessage);
            reporter.showInExtentReport(Status.PASS, passMessage, requireScreenshot);
        } else {
            log.error(failMessage);
            reporter.showInExtentReport(Status.FAIL, failMessage, requireScreenshot);
        }
    }

    /**
     * This method is use to log the error message into report
     * @param message The Message
     */
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

    /**
     * This method use to get current date and time in format 'yyyy-mm-dd HH:mm:ss'
     * @return The Date & time
     */
    public String getNowDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date dateobj = new Date();
        return df.format(dateobj);
    }

    /**
     * This method use to convert String array to string
     * @param strArr The String Array
     * @param delimiter The delimiter
     * @return String the result
     */
    public String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            sb.append(str).append(delimiter);
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * This method use to convert String array to string
     * @param strArr The String Array
     * @param delimiter The delimiter
     * @return String the result
     */
    public String convertStringListToString(List<String> strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            sb.append(str).append(delimiter);
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * This method use to get random number between two integer
     * @param startRange The start range
     * @param endRange The end range
     * @return Integer The random number
     */
    public int getRandomNumberBetweenTwoNumbers(int startRange, int endRange) {
        Random r;
        r = new Random();
        return r.nextInt(endRange - startRange) + startRange;
    }

    /**
     * This method use to get random number of length 10
     * @return Integer The random number
     */
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
            options.addArguments("--window-size=1792,1120");
            options.setHeadless(true);
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("download.default_directory", excelPath);
            prefs.put("intl.accept_languages", "nl");
            prefs.put("disable-popup-blocking", "true");
            options.setExperimentalOption("prefs", prefs);
            tempDriver = new ChromeDriver(options);
            driver = tempDriver;
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            tempWindowHandle = tempDriver.getWindowHandle();
        } catch (Exception e) {
            fail("Exception Caught in Method - openNewTemporaryBrowser2Nd" + "</br>" + "Exception Message - "
                    + e.getMessage(), true);
        }
    }
}