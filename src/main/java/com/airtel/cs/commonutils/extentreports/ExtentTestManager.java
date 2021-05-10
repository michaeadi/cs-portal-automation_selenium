package com.airtel.cs.commonutils.extentreports;

import com.airtel.cs.driver.Driver;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager extends Driver {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized void endTestOld() {
        extent.removeTest(extentTestMap.get((int) Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        test = extent.createTest(testName, desc);
        test.assignCategory(testName);
        test.assignAuthor("CS QA Airtel-Africa");
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
}