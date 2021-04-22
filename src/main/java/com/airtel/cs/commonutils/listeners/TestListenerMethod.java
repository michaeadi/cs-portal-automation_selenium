package com.airtel.cs.commonutils.listeners;

import com.airtel.cs.commonutils.extentreports.ExtentManager;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Log4j2
public class TestListenerMethod extends Driver implements ITestListener {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.");
    LocalDateTime now = LocalDateTime.now();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    private static Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        log.info("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("I am in onFinish method " + iTestContext.getName());
        ExtentTestManager.endTestOld();
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info(java.time.LocalTime.now() + " I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info(java.time.LocalTime.now() + " I am in on TestSuccess method " + getTestMethodName(iTestResult) + " succeed");
        //ExtentReports log operation for passed tests.
        ExtentTestManager.getTest().setEndedTime(getTime((iTestResult.getEndMillis())));

        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");

        //Get driver from BaseTest and assign to local webDriver variable.
        Object testClass = iTestResult.getInstance();
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info(java.time.LocalTime.now() + " I am in on TestFailure method " + getTestMethodName(iTestResult) + " failed");
        ExtentTestManager.getTest().setEndedTime(getTime((iTestResult.getEndMillis())));

        //Get driver from BaseTest and assign to local webDriver variable.
        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = ((Driver) testClass).getDriver();

        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
                getScreenshotAs(OutputType.BASE64);

        //ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        ExtentTestManager.getTest().log(LogStatus.ERROR, iTestResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.info("I am in on TestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        ExtentTestManager.getTest().setEndedTime(getTime((iTestResult.getEndMillis())));
        Object testClass = iTestResult.getInstance();
        ExtentTestManager.getTest().log(LogStatus.SKIP, getTestMethodName(iTestResult) + " Test Skipped");

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

}
