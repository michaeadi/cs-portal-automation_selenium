package Utils;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import lombok.extern.log4j.Log4j2;
import tests.frontendAgent.BaseTest;

import java.util.concurrent.TimeUnit;

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

    public static void printResponseDetail(Response response) {
        printInfoLog("Then Response : " + response.asString());
        printInfoLog("And Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
        printInfoLog("And Status Code: " + response.getStatusCode());
    }

    public static void printGetRequestDetail(QueryableRequestSpecification queryable) {
        printInfoLog("When Request URL: " + queryable.getURI());
        log.info("And Request Headers are  : " + queryable.getHeaders());
        printInfoLog("And Query Parameter is  : " + queryable.getQueryParams().toString());
    }

    public static void printPostRequestDetail(QueryableRequestSpecification queryable) {
        printInfoLog("When Request URL: " + queryable.getURI());
        log.info("And Request Headers are  : " + queryable.getHeaders());
        printInfoLog("And Body is  : " + queryable.getBody().toString());
    }
}
