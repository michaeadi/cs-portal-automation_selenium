package Utils;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import lombok.extern.log4j.Log4j2;
import tests.frontendAgent.BaseTest;

@Log4j2
public class UtilsMethods extends BaseTest {

    public static void addHeaders(String key, String value) {
        map.add(new Header(key, value));
    }

    public static void printInfoLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.INFO, message);
    }

    public static void printFailLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.FAIL, message);
    }

    public static void printPassLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.PASS, message);
    }

    public static void printWarningLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.WARNING, message);
    }
}
