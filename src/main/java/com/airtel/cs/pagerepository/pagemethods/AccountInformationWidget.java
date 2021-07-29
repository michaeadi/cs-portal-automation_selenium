package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AccountInformationWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class AccountInformationWidget extends BasePage {

    AccountInformationWidgetPage pageElements;

    private final String SCROLL_TO_WIDGET_MESSAGE = config.getProperty("scrollToWidgetMessage");
    private final String STATUS = "status";
    private final String STATUS_CODE = "statusCode";

    public AccountInformationWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AccountInformationWidgetPage.class);
    }

    /**
     * This method use to check whether account intformation widget display or not
     *
     * @return true/false
     */
    public Boolean isAccountInformationWidgetDisplay() {
        commonLib.info(config.getProperty("accountInfoWidgetDisplay"));
        boolean status = false;
        try {
            scrollToViewElement(pageElements.getTitle);
            status = isElementVisible(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE, true);
        }
        return status;
    }

    /*
       This Method will give us footer auuid shown in Account Information widget
       Account Information Widget
        */
    public String getFooterAuuid() {
        commonLib.info(getText(pageElements.footerAuuid));
        return getText(pageElements.footerAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the Account Information modal
    Account Information Widget
     */
    public String getMiddleAuuid() {
        String result;
        result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        commonLib.info(result);
        return result;
    }

    /**
     * This method use to get detailed account information widget more icon displayed or not
     *
     * @return Boolean The  data value
     */
    public Boolean isActionIconVisibleOnAccountInfo() {
        Boolean status = isElementVisible(pageElements.accountInfoDetailed);
        return status;
    }

    /*
       This Method will give us Last Month Bill Amount
        */
    public String getLastMonthBillAmount() {
        String result = null;
        if (isVisible(pageElements.lastMonthBillAmount)) {
            result = getText(pageElements.lastMonthBillAmount);
            commonLib.info("Last month bill amount on UI is: " + result);
        } else {
            commonLib.fail("Last month bill amount under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us Current Month Un-bill Amount
        */
    public String getCurrentMonthUnBillAmount() {
        String result = null;
        if (isVisible(pageElements.currentMonthUnbillAmount)) {
            result = getText(pageElements.currentMonthUnbillAmount);
            commonLib.info("Current month un-bill amount on UI is: " + result);
        } else {
            commonLib.fail("Current month un-bill amount under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us current cycle
        */
    public String getCurrentCycle() {
        String result = null;
        if (isVisible(pageElements.currentCycle)) {
            result = getText(pageElements.currentCycle);
            commonLib.info("Current Cycle on UI is: " + result);
        } else {
            commonLib.fail("Current Cycle under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us due date
        */
    public String getDueDate() {
        String result = null;
        if (isVisible(pageElements.dueDate)) {
            result = getText(pageElements.dueDate);
            commonLib.info("Due date on UI is: " + result);
        } else {
            commonLib.fail("Due date under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us Last payment mode
        */
    public String getLastPaymentMode() {
        String result = null;
        if (isVisible(pageElements.lastPaymentMode)) {
            result = getText(pageElements.lastPaymentMode);
            commonLib.info("Last Payment Mode on UI is: " + result);
        } else {
            commonLib.fail("Last Payment Mode under Account Information Widget is NOT visible", true);
        }
        return result;
    }


    /*
       This Method will give us security deposit
        */
    public String getSecurityDeposit() {
        String result = null;
        if (isVisible(pageElements.securityDeposit)) {
            result = getText(pageElements.securityDeposit);
            commonLib.info("Security Deposit on UI is: " + result);
        } else {
            commonLib.fail("Security Deposit under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us security deposit
        */
    public String getTotalOutstanding() {
        String result = null;
        if (isVisible(pageElements.totalOutstanding)) {
            result = getText(pageElements.totalOutstanding);
            commonLib.info("Total Outstanding on UI is: " + result);
        } else {
            commonLib.fail("Total Outstanding under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us last month bill date
        */
    public String getLastMonthBillDate() {
        String result = null;
        if (isVisible(pageElements.lastMonthBillDate)) {
            result = getText(pageElements.lastMonthBillDate);
            commonLib.info("Last month bill date on UI is: " + result);
        } else {
            commonLib.fail("Last month bill date under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us account number
        */
    public String getAccountNumber() {
        String result = null;
        if (isVisible(pageElements.accountNumber)) {
            result = getText(pageElements.accountNumber);
            commonLib.info("Account Number on UI is: " + result);
            getStyle(pageElements.accountNumber);
        } else {
            commonLib.fail("Account Number under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    public String getStyle(By elementLocation) {
        String attributeValue = null;
        //getElementByXpath(elementLocation);
        WebElement element = getElementFromBy(elementLocation);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object allAttributes = executor.executeScript(
                "var elem = document.getElementById(element); var css = window.getComputedStyle(elem, null);document.getElementById('demo').innerHTML = 'font-weight:-' +css.getPropertyValue('font-weight')+ 'backgroundColor:-' + css.getPropertyValue('background-color');",
                element);
        commonLib.logs(allAttributes.toString());
        return attributeValue;
    }

    public String getElementByXpath(By elementLocation) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object allAttributes = executor.executeScript("var elementLocation=+elementLocation;var xpathresult = document.evaluate(elementLocation, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;");
        return (String) allAttributes;
    }

    public String getValue(List<String> list, String rowToSearch, String valueToSearch) throws IOException, ParseException {
        String result = null;
        JSONParser parser = new JSONParser();
        for (String s : list)
            if (s.contains(rowToSearch)) {
                JSONObject json = (JSONObject) parser.parse(s);
                if (valueToSearch.equals(STATUS)) {
                    result = String.valueOf(json.get(STATUS));
                    break;
                } else if (valueToSearch.equals(STATUS_CODE)) {
                    result = String.valueOf(json.get(STATUS_CODE));
                    break;
                } else {
                    result = json.get("result").toString();
                    if (StringUtils.contains(String.valueOf(json.get(STATUS_CODE)), "200") && StringUtils.contains(String.valueOf(json.get(STATUS)), "SUCCESS")) {
                        result = result.substring(1, result.length() - 1).replace("'", "");
                        String[] keyValuePairs = result.split(",");
                        Map<String, String> map = new HashMap<>();
                        for (String pair : keyValuePairs) {
                            String[] entry = pair.split(":");
                            map.put(entry[0].trim(), entry[1].trim());
                        }
                        result = map.get(valueToSearch);
                        break;
                    }
                }
            }
        return result;
    }

    /*
       This Method will give us total credit limit
        */
    public String getTotalCreditLimit() {
        String result = null;
        if (isVisible(pageElements.totalCreditLimit)) {
            result = getText(pageElements.totalCreditLimit);
            commonLib.info("Total credit limit is: " + result);
        } else {
            commonLib.fail("Total credit limit under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us available credit limit
        */
    public String getAvailCreditLimit() {
        String result = null;
        if (isVisible(pageElements.availableCreditLimit)) {
            result = getText(pageElements.availableCreditLimit);
            commonLib.info("Available credit limit is: " + result);
        } else {
            commonLib.fail("Available credit limit under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
    This Method will give us credit limit used value
     */
    public String getCreditLmtUsed() {
        String result;
        result = getAttribute(pageElements.progressBarForCreditLimit, "value", false);
        return result;
    }

    /*
    This Method will give us calculated credit limit
     */
    public double getCreditLimitUsed(double creditLimitUsedUIValue, double ttlCreditLmt) {
        return (creditLimitUsedUIValue / 100) * ttlCreditLmt;
    }

    /*
    This Method will give us calculated available limit
     */
    public double getAvailLimit(double doubleMaxVal, double creditLimitUsedUIValue, double ttlCreditLmt) {
        return ((doubleMaxVal - creditLimitUsedUIValue) / 100) * ttlCreditLmt;
    }

    /**
     * This method use to check others tab is displayed or not
     *
     * @return Boolean The  data value
     */
    public Boolean isOthersTabVisible() {
        Boolean status = isElementVisible(pageElements.othersTab);
        commonLib.info("Checking others tab is visible on demographic widget: '" + status);
        return status;
    }

    /**
     * This method use to open others tab in demographic widget
     */
    public void openOthersTab() {
        commonLib.info("Opening others tab");
        if (isVisible(pageElements.othersTab)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.othersTab);
        } else {
            commonLib.error("Unable to open other tab page");
        }
    }

    /**
     * This method is used to get email ID from demographic widget
     *
     * @return
     */
    public String getEmailId() {
        commonLib.info(getText(pageElements.emailId));
        return getText(pageElements.emailId);
    }

    /**
     * This method is used to get email ID from demographic widget
     *
     * @return
     */
    public String getUnableToFetch() {
        commonLib.info(getText(pageElements.unableToFetch));
        return getText(pageElements.unableToFetch);
    }

    /**
     * This method will return round off value up-to two decimal in string format
     *
     * @param input_string
     * @return
     */
    public static String getTwoDecimalValue(String input_string) {
        DecimalFormat df = new DecimalFormat("0.00");
        double value = Double.parseDouble(input_string);
        double input = value;
        String result = df.format(input);
        return result;
    }

    /**
     * This method is used to convert date from long to string
     *
     * @param epoch
     * @param pattern
     * @return
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
     * This method is used to get cycle difference in months
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String getDateDiffInMonths(String str1, String str2) {
        long monthsBetween = ChronoUnit.MONTHS.between(
                LocalDate.parse(str1).withDayOfMonth(1),
                LocalDate.parse(str2).withDayOfMonth(1));
        String result = Long.toString(monthsBetween);
        return result;

    }

    /**
     * This method will give us date
     *
     * @param str
     * @return
     */
    public static String firstTwo(String str) {

        if (str.length() < 2) {
            return str;
        } else {
            return str.substring(0, 2);
        }
    }

    /**
     * This method will return boolean value true if found decimal value upto 2
     *
     * @param input_string
     * @return
     */
    public static boolean getDecimalValue(String input_string) throws ArrayIndexOutOfBoundsException {

        boolean flag = false;
        if (!input_string.equalsIgnoreCase("-")) {
            String[] result = input_string.split("\\.");
            if (result[1].length() == 2) {
                flag = true;
            }
        }
        return flag;
    }

}