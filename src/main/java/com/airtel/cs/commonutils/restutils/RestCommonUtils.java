package com.airtel.cs.commonutils.restutils;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.google.gson.Gson;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class RestCommonUtils extends Driver {

    public static Response response;
    private static QueryableRequestSpecification queryable;
    private static RequestSpecification request;
    private static final String APPLICATION_JSON = "application/json";
    private static final String CALLING_CS_API = "Calling CS API";
    private static final String FOR_TESTING = "for Testing ";
    private static final String USING_AUUID="using auuid ";
    private static final String TOKEN ="'s token";




    /**
     * This Method will hit the API with POST Method
     *
     * @param endPoint endpoint
     * @param body     body of the api
     */
    public static void commonPostMethod(String endPoint, Object body) {
        commonPostMethod(endPoint, map, body, baseUrl);
    }

    /**
     * This Method will hit the API with POST Method
     *
     * @param endPoint endpoint
     * @param body     body of the api
     * @param url      http url
     */
    public static void commonPostMethod(String endPoint, List<Header> map, Object body, String url) {
        RestAssuredConfig restAssuredConfig = CurlRestAssuredConfigFactory.createConfig();
        try {
            commonLib.infoColored(CALLING_CS_API + " " + endPoint + " " +FOR_TESTING  + USING_AUUID + loginAUUID + TOKEN, JavaColors.BLUE, false);
            baseURI = url;
            Headers headers = new Headers(map);
            String finalbody="";
            
            if (body instanceof String)
              finalbody = (String) body;
            else
              finalbody = objectMapper.writeValueAsString(body);
            
            request = given()
                    .config(restAssuredConfig)
                    .headers(headers)
                    .body(finalbody)
                    .contentType(APPLICATION_JSON);
            response = request.post(endPoint);
            queryable = SpecificationQuerier.query(request);
            UtilsMethods.printResponseDetail(response);
        } catch (Exception | AssertionError e) {
            commonLib.fail("Caught exception in Testcase - commonPostMethod " + e.getMessage(), false);
        } finally {
            commonLib.info(getRequestCurl(queryable.getURI(), queryable.getHeaders(), body));
        }
    }

    /**
     * This Method is used to hit the API which are using GET Method with Query Params and status Code
     *
     * @param endPoint   send the endPoint
     * @param queryParam send query param used for API
     */
    public static void commonGetMethodWithQueryParam(String endPoint, Map<String, Object> queryParam, List<Header> headers) {
        try {
            RestAssuredConfig restAssuredConfig = CurlRestAssuredConfigFactory.createConfig();
            commonLib.infoColored(CALLING_CS_API + " " + endPoint + " " + FOR_TESTING + USING_AUUID + loginAUUID + TOKEN, JavaColors.BLUE, false);
            baseURI = baseUrl;
            Headers header = new Headers(headers);
            request = given()
                    .config(restAssuredConfig)
                    .headers(header)
                    .contentType(APPLICATION_JSON);
            commonLib.info("Query Param Map:-" + queryParam.toString());
            
            StringBuilder stringBuilder = new StringBuilder("?");
            queryParam.forEach((k, v) -> stringBuilder.append(k).append("=").append(v).append("&"));
            endPoint += stringBuilder.toString();
            endPoint.substring(0, endPoint.length() - 1);
            queryable = SpecificationQuerier.query(request);
            response = request.get(endPoint);
            
            UtilsMethods.printResponseDetail(response);
        } catch (Exception | AssertionError e) {
            commonLib.fail("Caught exception in Testcase - commonGetMethodWithQueryParam " + e.getMessage(), false);
        } finally {
            commonLib.info(getRequestCurl(queryable.getURI(), queryable.getHeaders(), null));
        }
    }

    /**
     * This Method is used to hit the API which are using GET Method
     *
     * @param endPoint send the endPoint
     */
    public static void commonGetMethod(String endPoint) {
        commonGetMethod(endPoint, new Headers(map));
    }

    public static void commonGetMethod(String endPoint, Headers headers) {
        RestAssuredConfig restAssuredConfig = CurlRestAssuredConfigFactory.createConfig();
        try {
            commonLib.infoColored(CALLING_CS_API + " " + endPoint + " " + FOR_TESTING + USING_AUUID + loginAUUID + TOKEN, JavaColors.BLUE, false);
            baseURI = baseUrl;
            request = given().config(restAssuredConfig).headers(headers).contentType(APPLICATION_JSON);
            queryable = SpecificationQuerier.query(request);
            response = request.get(endPoint);
            UtilsMethods.printResponseDetail(response);
        } catch (Exception | AssertionError e) {
            commonLib.fail("Caught exception in Testcase - commonGetMethod " + e.getMessage(), false);
        } finally {
            commonLib.info(getRequestCurl(queryable.getURI(), queryable.getHeaders(), null));
        }
    }

    /**
     * This Method is used to hit the API which are using GET Method
     *
     * @param endPoint send the endPoint
     * @param map      send headers used for API
     */
    public static void commonGetMethod(String endPoint, List<Header> map, String url) {
        try {
            commonLib.info("Calling " + " " + endPoint + " " + "API for Testing" + " " + USING_AUUID + loginAUUID + TOKEN);
            baseURI = url;
            Headers headers = new Headers(map);
            request = given().headers(headers).contentType(APPLICATION_JSON);
            queryable = SpecificationQuerier.query(request);
            response = request.get(endPoint);
            restUtils.printGetRequestDetail(queryable);
            restUtils.printResponseDetail(response);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - commonGetMethod " + e.getMessage(), false);
        }
    }

    /**
     * This Method is used to get the curl of http request
     *
     * @param endPoint send the endPoint
     * @param body     the body
     * @param headers  the headers
     * @return String
     */
    public static String getRequestCurl(String endPoint, Headers headers, Object body) {
        StringBuilder stringBuilder = new StringBuilder("curl '");
        stringBuilder.append(endPoint).append("' ");
        headers.forEach(header -> stringBuilder.append("-H '").append(header.getName()).append(": ").append(header.getValue()).append("' "));
        if (ObjectUtils.isNotEmpty(body)) {
            stringBuilder.append("--data-raw '").append(new Gson().toJson(body)).append("' ");
        }
        return stringBuilder.append("--compressed --insecure").toString();
    }

    public String getDateFromEpoch(long epoch, String pattern) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public String getDateFromString(String date, String pattern) {
        try {
            Date newDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").parse(date);
            DateFormat format = new SimpleDateFormat(pattern);
            return format.format(newDate);
        } catch (ParseException e) {
            printFailLog("Not able to parse the date: " + date + " " + e.fillInStackTrace());
        }
        return "Invalid Date String";
    }

    public String getDateFromStringInUTC(String date, String pattern) {
        try {
            Date newDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").parse(date);
            DateFormat format = new SimpleDateFormat(pattern);
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            return format.format(newDate);
        } catch (ParseException e) {
            printFailLog("Not able to parse the date: " + date + " " + e.fillInStackTrace());
        }
        return "Invalid Date String";
    }

    public String getDateFromEpochInUTC(long epoch, String pattern) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        return format.format(date);
    }


    public String getTimeFromEpoch(long epoch, String pattern) {
        Date date = new Date(epoch);
        Date nearestMinute = DateUtils.round(date, Calendar.MINUTE);
        DateFormat format1 = new SimpleDateFormat(pattern);
        return format1.format(nearestMinute);
    }

    public String convertToHR(String committedSla) {
        long ms = Long.parseLong(committedSla);
        commonLib.info("Converting SLA: " + committedSla + " to " + (TimeUnit.MILLISECONDS.toHours(ms)));
        return String.valueOf(TimeUnit.MILLISECONDS.toHours(ms));
    }

    public void printInfoLog(String message) {
        commonLib.info(message);
    }

    public void printFailLog(String message) {
        commonLib.error(message);
        commonLib.fail(message, false);
    }

    public void printPassLog(String message) {
        commonLib.info(message);
    }

    public void printWarningLog(String message) {
        commonLib.warning(message);
    }

    public String valueRoundOff(Double value) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(value);
    }

    public List<Header> invalidToken() {
        List<Header> map1 = new ArrayList<>();
        map1.add(new Header("Opco", OPCO));
        map1.add(new Header("Authorization", "Bearer " + constants.getValue(CommonConstants.INVALID_TOKEN)));
        return map1;
    }

    public void addHeaders(String key, String value) {
        validHeaderList.add(new Header(key, value));
        map.add(new Header(key, value));
    }

    public void printResponseDetail(Response response) {
        printInfoLog("Then Response is -: " + response.asString());
        printInfoLog("Response time is: " + response.getTimeIn(TimeUnit.SECONDS) + " s");
        printInfoLog("Status Code is: " + response.getStatusCode());
    }

    public void printGetRequestDetail(QueryableRequestSpecification queryable) {
        printInfoLog("When Request URL: " + queryable.getURI());
        printInfoLog("Request Headers are  : " + "\n" + queryable.getHeaders());
        if (!(queryable.getQueryParams().toString() == null || queryable.getQueryParams().toString().equals("{}"))) {
            printInfoLog("Query Parameter is  : " + queryable.getQueryParams().toString());
        }
    }

    public void printPostRequestDetail(QueryableRequestSpecification queryable) {
        printInfoLog("When Request URL: " + queryable.getURI());
        printInfoLog("And Request Headers are  : " + queryable.getHeaders());
        printInfoLog("And Body is  : " + queryable.getBody().toString());
    }

    public void clearValidHeaderMap() {
        validHeaderList.clear();
    }

    public void clearInvalidHeaderMap() {
        invalidHeaderList.clear();
    }
}
