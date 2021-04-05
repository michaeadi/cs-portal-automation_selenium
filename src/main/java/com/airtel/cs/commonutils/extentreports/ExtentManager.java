package com.airtel.cs.commonutils.extentreports;

import com.relevantcodes.extentreports.ExtentReports;
import tests.frontendagent.BaseTest;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager {

    private static ExtentReports extent;

    public synchronized static ExtentReports getReporter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh_mm-a-dd-MMM-yyyy");
        String date = LocalDateTime.now().format(formatter);
        if (extent == null) {
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                extent = new ExtentReports(workingDir + "/ExtentReports/CS_Portal-" + BaseTest.OPCO + "-" + BaseTest.Env + "-" + date + ".html", true);
                extent.addSystemInfo("user", "Rahul Gupta");
                extent.assignProject("CS Portal");
                extent.addSystemInfo("OPCO", BaseTest.OPCO);
                extent.addSystemInfo("Environment", BaseTest.Env);
                extent.loadConfig(new File(workingDir + "/resources/properties/reportextent-config.xml"));

            } else {
                extent = new ExtentReports(workingDir + "/ExtentReports/CS_Portal-Automation-Report-"+BaseTest.OPCO+"-" + date + ".html", true);
                extent.addSystemInfo("user", "Rahul Gupta");
                extent.assignProject("CS Portal");
                extent.addSystemInfo("OPCO", BaseTest.OPCO);
                extent.addSystemInfo("Environment", BaseTest.Env);
                extent.loadConfig(new File(workingDir + "/resources/properties/reportextent-config.xml"));

            }
        }
        return extent;
    }
}