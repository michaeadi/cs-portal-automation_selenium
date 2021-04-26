package com.airtel.cs.commonutils.listeners;

import com.airtel.cs.commonutils.extentreports.ExtentReport;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.TestResult;

import java.lang.reflect.Field;

@Log4j2
public class TestListenerMethod extends TestListenerAdapter {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        log.info("I am in on Start method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", Driver.driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("I am in on Finish method " + iTestContext.getName());
        Driver.reporter.endTest();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info(java.time.LocalTime.now() + " I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        setTestNameInXml(iTestResult);
        super.onTestSuccess(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        try {
            String className = tr.getMethod().getInstance().getClass().getName();
            int idx = className.lastIndexOf('.');
            className = className.substring(idx + 1, className.length());
            ExtentReport.startTest(className, tr.getName());
            ExtentReport.test.log(LogStatus.SKIP,
                    tr.getName() + " - Testcase is getting skipped due to some failure." + "</br>" + tr.getStatus());
            ExtentReport.endTest(Driver.test);
        } catch (Exception ex) {
            Driver.commonLib.error(ex.getMessage());
        }

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    /**
     * Sets the test name in xml.
     *
     * @param tr the new test name in xml
     */
    private void setTestNameInXml(ITestResult tr) {
        try {
            Field mMethod = TestResult.class.getDeclaredField("m_method");
            mMethod.setAccessible(true);
            mMethod.set(tr, tr.getMethod().clone());
            Field mMethodName = BaseTestMethod.class.getDeclaredField("m_methodName");
            mMethodName.setAccessible(true);
            mMethodName.set(tr.getMethod(), tr.getTestName());
        } catch (IllegalAccessException ex) {
            Reporter.log(ex.getLocalizedMessage(), true);
        } catch (NoSuchFieldException ex) {
            Driver.commonLib.error(ex.getMessage());
        }
    }

}
