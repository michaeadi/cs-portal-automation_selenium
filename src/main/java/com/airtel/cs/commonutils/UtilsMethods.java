package com.airtel.cs.commonutils;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.agents.AgentDetailAttribute;
import com.airtel.cs.model.response.agents.Authorities;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Log4j2
public class UtilsMethods extends Driver {

    private static final String YESTERDAY = "Yesterday";
    private static final String TODAY = "Today";
    private static RequestSource api=new RequestSource();

    /**
     * This method use to add headers
     * @param key The Key
     * @param value The Value
     */
    public static void addHeaders(String key, String value) {
        map.add(new Header(key, value));
    }

    /**
     * This method use to print response detail
     * @param response The response object
     */
    public static void printResponseDetail(Response response) {
        commonLib.info("Then Response : " + response.asString());
        commonLib.info("And Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
        commonLib.info("And Status Code: " + response.getStatusCode());
    }

    /**
     * This method use to print Get method api request
     * @param queryable The Request query
     */
    public static void printGetRequestDetail(QueryableRequestSpecification queryable) {
        commonLib.info("When Request URL: " + queryable.getURI());
        log.info("And Request Headers are  : " + queryable.getHeaders());
        if (!(queryable.getQueryParams().toString() == null || queryable.getQueryParams().toString().equals("{}"))) {
            commonLib.info("Query Parameter is  : " + queryable.getQueryParams().toString());
        }
    }

    /**
     * This method use to print post method api request
     * @param queryable The Post Request query
     */
    public static void printPostRequestDetail(QueryableRequestSpecification queryable) {
        commonLib.info("When Request URL: " + queryable.getURI());
        log.info("And Request Headers are  : " + queryable.getHeaders());
        commonLib.info("And Body is  : " + queryable.getBody().toString());
    }

    /**
     * This method is used to get date from epoch in given pattern
     * @param epoch The Epoch
     * @param pattern The Pattern
     * @return String The date
     */
    public static String getDateFromEpoch(long epoch, String pattern) {
        if (epoch == 0) {
            return "-";
        } else {
            Date date = new Date(epoch);
            DateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        }
    }

    /**
     * This method is used to convert the date to required pattern
     * @param date The date
     * @param newPatten The new pattern date format
     * @param existingPattern The existing pattern date format
     * @return String The new date
     */
    public static String getDateFromString(String date, String newPatten, String existingPattern) {
        try {
            Date newDate = new SimpleDateFormat(existingPattern).parse(date);
            DateFormat format = new SimpleDateFormat(newPatten);
            return format.format(newDate);
        } catch (ParseException e) {
            commonLib.fail("Not able to parse the date: " + date + " " + e.fillInStackTrace(), true);
        }
        return "Invalid Date String";
    }

    /**
     * This method is used to convert given date date into utc time zone
     * @param date The date
     * @param pattern The pattern
     * @return String The String
     */
    public static String getDateFromStringInUTC(String date, String pattern) {
        try {
            Date newDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").parse(date);
            DateFormat format = new SimpleDateFormat(pattern);
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            return format.format(newDate);
        } catch (ParseException e) {
            commonLib.fail("Not able to parse the date: " + date + " " + e.fillInStackTrace(), true);
        }
        return "Invalid Date String";
    }

    /**
     * This method used to convert epoch time into UTC date in given pattern
     * @param epoch The Epoch
     * @param pattern The pattern
     * @return String The date in UTC
     */
    public static String getDateFromEpochInUTC(long epoch, String pattern) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        return format.format(date);
    }

    /**
     * This method used to get time from epoch
     * @param epoch The epoch
     * @param pattern The pattern
     * @return String The String
     */
    public static String getTimeFromEpoch(long epoch, String pattern) {
        Date date = new Date(epoch);
        Date nearestMinute = DateUtils.round(date, Calendar.MINUTE);
        DateFormat format1 = new SimpleDateFormat(pattern);
        return format1.format(nearestMinute);
    }

    /**
     * This method used to check is first date is less than second date
     * @param historyDateTime first date
     * @param historyDateTime1 second date
     * @param pattern date format pattern
     * @return true/false
     * */
    public static boolean isSortOrderDisplay(String historyDateTime, String historyDateTime1, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        final Calendar cal = Calendar.getInstance();
        try {
            if (historyDateTime.contains(YESTERDAY)) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                cal.add(Calendar.DATE, -1);
                String yesterday = format1.format(cal.getTime());
                historyDateTime = historyDateTime.replace(YESTERDAY, yesterday);
                commonLib.info(historyDateTime + " :" + yesterday);
            }

            if (historyDateTime1.contains(YESTERDAY)) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                cal.add(Calendar.DATE, -1);
                String yesterday = format1.format(cal.getTime());
                historyDateTime1 = historyDateTime1.replace(YESTERDAY, yesterday);
                commonLib.info(historyDateTime1 + " :" + yesterday);
            }

            if (historyDateTime.contains(TODAY)) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                String today = format1.format(Calendar.getInstance().getTime());
                historyDateTime = historyDateTime.replace(TODAY, today);
            }

            if (historyDateTime1.contains(TODAY)) {
                String pattern1 = pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                String today = format1.format(Calendar.getInstance().getTime());
                historyDateTime1 = historyDateTime1.replace(TODAY, today);
            }

            Date date1 = format.parse(historyDateTime);
            Date date2 = format.parse(historyDateTime1);
            if (date2.compareTo(date1) <= 0) {
                log.info(date2 + " come before " + date1);
                return true;
            } else {
                log.info(date2 + " come after " + date1);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            commonLib.fail("Date Patter does not same as date string Please check date pattern.",false);
            return false;
        }
    }

    /**
     * This method use to convert milliseconds to hour
     * @param committedSla time in milliseconds
     * @return conversion milliseconds to hour
     * */
    public static String convertToHR(String committedSla) {
        long ms = Long.parseLong(committedSla);
        final String valueOf = String.valueOf(TimeUnit.MILLISECONDS.toHours(ms));
        log.info("Converting SLA: " + committedSla + " to " + valueOf);
        return valueOf;
    }

    /**
     * This method is used to round off up to 2 decimal point
     * @param value value to round off
     * @return value round off up to 2 digit
     * */

    public static String valueRoundOff(Double value) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(value);
    }

    /**
     * This method is used to check today date and month is same as given date month and date
     * @param birthDay birth date
     * @param pattern date format in which birth date display
     * @return true/false whether today date same as birth date
     * */

    public static Boolean isCustomerBirthday(String birthDay, String pattern) {
        String d="dd-MM";
        DateFormat format1 = new SimpleDateFormat(d);
        String today = format1.format(Calendar.getInstance().getTime());
        Date customerBirthDate;
        try {
            customerBirthDate = new SimpleDateFormat(pattern).parse(birthDay);
            String customerBday = format1.format(customerBirthDate);
            return today.equalsIgnoreCase(customerBday);
        }catch (ParseException e) {
            commonLib.fail("Not able to parse the customer birth date :"+birthDay,false);
        }
        return false;
    }

    /**
     * This method use to check whether user has permission assign or not
     * @param headers auth header
     * @param permissionName permission name to check
     * @return true/false based on user have permission or not
     * */
    public static Boolean isUserHasPermission(Headers headers, String permissionName){
        AgentDetailAttribute agentDetailAPI=api.getAgentDetail(headers);
        if(agentDetailAPI.getStatusCode()!=200){
            commonLib.fail("Not able to get Agent detail using agent api",false);
            return false;
        }else{
            List<Authorities> allPermissions=agentDetailAPI.getResult().getUserDetails().getUserDetails().getAuthorities();
            for(Authorities permission:allPermissions){
                if(permission.getAuthority().equalsIgnoreCase(permissionName)){
                    return true;
                }
            }
        }
        return false;
    }
}
