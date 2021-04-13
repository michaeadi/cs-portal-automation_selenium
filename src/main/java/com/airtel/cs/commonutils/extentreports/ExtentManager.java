package com.airtel.cs.commonutils.extentreports;

import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager extends Driver {

    private static ExtentReports extent;

    public static synchronized ExtentReports getReporter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh_mm-a-dd-MMM-yyyy");
        String date = LocalDateTime.now().format(formatter);
        if (extent == null) {
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                extent = new ExtentReports(workingDir + "\\ExtentReports\\CS_Portal-" + OPCO + "-" + evnName + "-" + SUITE_TYPE + "-" + date + ".html", true);
                extent.addSystemInfo("user", "Rahul Gupta");
                extent.assignProject("CS Portal");
                extent.addSystemInfo("OPCO", OPCO);
                extent.addSystemInfo("Environment", evnName);
                extent.addSystemInfo("Suite Type", SUITE_TYPE.toUpperCase());
                extent.loadConfig(new File(workingDir + "/resources/properties/reportextent-config.xml"));

            } else {
                extent = new ExtentReports(workingDir + "/ExtentReports/CS_Portal-Automation-Report-" + OPCO + evnName + "-" + date + ".html", true);
                extent.addSystemInfo("user", "Rahul Gupta");
                extent.assignProject("CS Portal");
                extent.addSystemInfo("OPCO", OPCO);
                extent.addSystemInfo("Environment", evnName);
                extent.addSystemInfo("Suite Type", SUITE_TYPE.toUpperCase());
                extent.loadConfig(new File(workingDir + "/resources/properties/reportextent-config.xml"));

            }
        }
        return extent;
    }
}