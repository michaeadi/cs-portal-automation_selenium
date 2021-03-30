package com.airtel.cs.commonutils;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import tests.frontendagent.BaseTest;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
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

    public static String getDateFromEpoch(long Epoch, String pattern) {
        if (Epoch == 0) {
            return "-";
        } else {
            Date date = new Date(Epoch);
            DateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        }
    }

    public static String getDateFromString(String date, String newPatten, String existingPattern) {
        try {
            Date newDate = new SimpleDateFormat(existingPattern).parse(date);
            DateFormat format = new SimpleDateFormat(newPatten);
            return format.format(newDate);
        } catch (ParseException e) {
            printFailLog("Not able to parse the date: " + date + " " + e.fillInStackTrace());
        }
        return "Invalid Date String";
    }

    public static String getDateFromStringInUTC(String date, String pattern) {
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

    public static String getDateFromEpochInUTC(long Epoch, String pattern) {
        Date date = new Date(Epoch);
        DateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        return format.format(date);
    }

    public static String getTimeFromEpoch(long Epoch, String pattern) {
        Date date = new Date(Epoch);
        Date nearestMinute = DateUtils.round(date, Calendar.MINUTE);
        DateFormat format1 = new SimpleDateFormat(pattern);
        return format1.format(nearestMinute);
    }

    public static boolean isSortOrderDisplay(String historyDateTime, String historyDateTime1, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        final Calendar cal = Calendar.getInstance();
        try {
            if (historyDateTime.contains("Yesterday")) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                cal.add(Calendar.DATE, -1);
                String yesterday = format1.format(cal.getTime());
                historyDateTime = historyDateTime.replace("Yesterday", yesterday);
                System.out.println(historyDateTime + " :" + yesterday);
            }

            if (historyDateTime1.contains("Yesterday")) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                cal.add(Calendar.DATE, -1);
                String yesterday = format1.format(cal.getTime());
                historyDateTime1 = historyDateTime1.replace("Yesterday", yesterday);
                System.out.println(historyDateTime1 + " :" + yesterday);
            }

            if (historyDateTime.contains("Today")) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                String today = format1.format(Calendar.getInstance().getTime());
                historyDateTime = historyDateTime.replace("Today", today);
            }

            if (historyDateTime1.contains("Today")) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                String today = format1.format(Calendar.getInstance().getTime());
                historyDateTime1 = historyDateTime1.replace("Today", today);
            }

            Date date1 = format.parse(historyDateTime);
            Date date2 = format.parse(historyDateTime1);
            if (date1.compareTo(date2) <= 0) {
                log.info(date1 + " come before " + date2);
                return true;
            } else {
                log.info(date1 + " come after " + date2);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String convertToHR(String committedSla) {
        Long ms = Long.parseLong(committedSla);
        log.info("Converting SLA: " + committedSla + " to " + String.valueOf(TimeUnit.MILLISECONDS.toHours(ms)));
        return String.valueOf(TimeUnit.MILLISECONDS.toHours(ms));
    }

    public static String ValueRoundOff(Double value) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(value);
    }

    public static Boolean isCustomerBirthday(String birthDay,String pattern){
        DateFormat format1 = new SimpleDateFormat(pattern);
        String today = format1.format(Calendar.getInstance().getTime());
        return today.equalsIgnoreCase(birthDay);
    }
}
