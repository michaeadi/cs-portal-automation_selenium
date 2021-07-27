package com.airtel.cs.commonutils.listeners;

import com.airtel.cs.commonutils.extentreports.ExtentReport;
import com.airtel.cs.driver.Driver;
import com.aventstack.extentreports.Status;
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
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    }

    @Override
    public void onTestFailure(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
       /* try {
            String className = tr.getMethod().getInstance().getClass().getName();
            int idx = className.lastIndexOf('.');
            className = className.substring(idx + 1, className.length());
            ExtentReport.startTest(className, tr.getName());
            ExtentReport.test.log(Status.SKIP,
                    tr.getName() + " - Testcase is getting skipped due to some failure." + "</br>" + tr.getStatus());
            ExtentReport.endTest();
        } catch (Exception ex) {
            Driver.commonLib.error(ex.getMessage());
        }*/
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
