package com.airtel.cs.commonutils.listeners;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private static final int MAX_TRY = 0; //Run the failed test n+1 times
    private int count = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (count < MAX_TRY) {
                count++;
                iTestResult.setStatus(ITestResult.FAILURE);
                extendReportsFailOperations(iTestResult);
                return true;
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }

    public void extendReportsFailOperations(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = ((Driver) testClass).getDriver();
        String base64Screenshot =
                "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(Status.FAIL,
                (Markup) ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot));
    }
}