package Utils.ExtentReports;

import com.relevantcodes.extentreports.ExtentReports;
import tests.frontendAgent.BaseTest;

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
                extent = new ExtentReports(workingDir + "\\ExtentReports\\CS_Portal-" + BaseTest.Opco + "-" + BaseTest.Env + "-" + BaseTest.suiteType + "-" + date + ".html", true);
                extent.addSystemInfo("user", "Rahul Gupta");
                extent.assignProject("CS Portal");
                extent.addSystemInfo("OPCO", BaseTest.Opco);
                extent.addSystemInfo("Environment", BaseTest.Env);
                extent.addSystemInfo("Suite Type", BaseTest.suiteType.toUpperCase());
                extent.loadConfig(new File(workingDir + "\\src\\main\\resources\\reportextent-config.xml"));

            } else {
                extent = new ExtentReports(workingDir + "/ExtentReports/CS_Portal-Automation-Report-" + BaseTest.Opco + BaseTest.Env + "-" + date + ".html", true);
                extent.addSystemInfo("user", "Rahul Gupta");
                extent.assignProject("CS Portal");
                extent.addSystemInfo("OPCO", BaseTest.Opco);
                extent.addSystemInfo("Environment", BaseTest.Env);
                extent.addSystemInfo("Suite Type", BaseTest.suiteType.toUpperCase());
                extent.loadConfig(new File(workingDir + "/src/main/resources/reportextent-config.xml"));

            }
        }
        return extent;
    }
}
