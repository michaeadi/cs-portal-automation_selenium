package com.airtel.cs.commonutils.restutils;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.google.gson.Gson;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class RestCommonUtils extends Driver {

    public static Response response;
    private static QueryableRequestSpecification queryable;
    private static RequestSpecification request;
    private static final String APPLICATION_JSON = "application/json";
    private static final String USING = "Using";
    private static final String API_FOR_TESTING = " API for Testing";


    /**
     * This Method will hit the API with POST Method
     *
     * @param endPoint endpoint
     * @param body     body of the api
     */
    public static void commonPostMethod(String endPoint, Object body) {
        commonPostMethod(endPoint, body, baseUrl);
    }

    /**
     * This Method will hit the API with POST Method
     *
     * @param endPoint endpoint
     * @param body     body of the api
     * @param url      http url
     */
    public static void commonPostMethod(String endPoint, Object body, String url) {
        RestAssuredConfig restAssuredConfig = CurlRestAssuredConfigFactory.createConfig();
        try {
            commonLib.info(USING + endPoint + API_FOR_TESTING);
            baseURI = url;
            Headers headers = new Headers(map);
            request = given()
                    .config(restAssuredConfig)
                    .headers(headers)
                    .body(objectMapper.writeValueAsString(body))
                    .contentType(APPLICATION_JSON);
            response = request.post(endPoint);
            queryable = SpecificationQuerier.query(request);
            commonLib.info(getRequestCurl(queryable.getURI(),queryable.getHeaders(),body));
            UtilsMethods.printResponseDetail(response);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - commonPostMethod " + e.getMessage(), false);
        }
    }

    /**
     * This Method is used to hit the API which are suing GET Method with Query Params
     *
     * @param endPoint   send the endPoint
     * @param queryParam send query param used for API
     */
    public static void commonGetMethodWithQueryParam(String endPoint, Map<String, Object> queryParam) {
        commonGetMethodWithQueryParam(endPoint, queryParam, 200);
    }

    /**
     * This Method is used to hit the API which are using GET Method with Query Params and status Code
     *
     * @param endPoint   send the endPoint
     * @param queryParam send query param used for API
     * @param statusCode send status code which you want from this API
     */
    public static void commonGetMethodWithQueryParam(String endPoint, Map<String, Object> queryParam, Integer statusCode) {
        try {
            RestAssuredConfig restAssuredConfig = CurlRestAssuredConfigFactory.createConfig();
            commonLib.info(USING + endPoint + API_FOR_TESTING);
            baseURI = baseUrl;
            Headers headers = new Headers(map);
            request = given()
                    .config(restAssuredConfig)
                    .headers(headers)
                    .contentType(APPLICATION_JSON);
            commonLib.info("Query Param Map:-" + queryParam.toString());
            queryParam.forEach(request::queryParam);
            queryable = SpecificationQuerier.query(request);
            response = request.get(endPoint).then().assertThat().statusCode(statusCode).extract().response();

            StringBuilder stringBuilder = new StringBuilder("?");
            queryParam.forEach((k, v) -> stringBuilder.append(k).append("=").append(v).append("&"));
            endPoint += stringBuilder.toString();
            endPoint.substring(0, endPoint.length() - 1);
            commonLib.info(getRequestCurl(queryable.getURI(), queryable.getHeaders(), null));
            UtilsMethods.printResponseDetail(response);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - commonGetMethodWithQueryParam " + e.getMessage(), false);
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
            commonLib.info(USING + endPoint + API_FOR_TESTING);
            baseURI = baseUrl;
            request = given().config(restAssuredConfig).headers(headers).contentType(APPLICATION_JSON);
            queryable = SpecificationQuerier.query(request);
            response = request.get(endPoint);
            commonLib.info(getRequestCurl(queryable.getURI(), queryable.getHeaders(), null));
            UtilsMethods.printResponseDetail(response);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - commonGetMethod " + e.getMessage(), false);
        }
    }

    /**
     * This Method is used to get the curl of http request
     *
     * @param endPoint send the endPoint
     * @param body
     * @param headers
     * @return String
     */
    public static String getRequestCurl(String endPoint, Headers headers, Object body){
        StringBuilder stringBuilder = new StringBuilder("curl '");
        stringBuilder.append(endPoint).append("' ");
        headers.forEach(header->{
            stringBuilder.append("-H '").append(header.getName()).append(": ").append(header.getValue()).append("' ");
        });
        if(ObjectUtils.isNotEmpty(body)){
            stringBuilder.append("--data-raw '").append(new Gson().toJson(body)).append("' ");
        }
        return stringBuilder.append("--compressed --insecure").toString();
    }
}
