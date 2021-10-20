package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AccountInformationWidgetPage;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
public class AccountInformationWidget extends BasePage {

    AccountInformationWidgetPage pageElements;

    private final String scrollToWidgetMessage = config.getProperty("scrollToWidgetMessage");
    private final String status = "status";
    private final String statusCode = "statusCode";
    Map<String, Object> styleMap;

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
            commonLib.fail(scrollToWidgetMessage, true);
        }
        return status;
    }

    /**
     * This method use to check whether account intformation widget display or not without scrolling
     *
     * @return true/false
     */
    public Boolean isAccountInfoWidgetDisplayWithOutScroll() {
        commonLib.info(config.getProperty("accountInfoWidgetDisplay"));
        return isElementVisible(pageElements.getTitle);
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
        commonLib.info(config.getProperty("iconVisibleOnDetailAccInfo") + status);
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
        if (isVisible(pageElements.securityDepositValue)) {
            result = getText(pageElements.securityDepositValue);
            String[] twoDecimal = result.split("\\.");
            if (twoDecimal[1].length() == 2)
                commonLib.pass("Security Deposit value is upto 2 decimal over UI");
            else
                commonLib.fail("Security Deposit value NOT upto 2 decimal", true);
            commonLib.info("Security Deposit on UI is: " + result);
        } else {
            commonLib.fail("Security Deposit under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
    This Method will return the color for Security Deposit Data Point
     */
    public String getSecurityDepositCurrencyStyle() {
        return selUtils.getDataPointColor(pageElements.securityDepositCurrency);
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
        } else {
            commonLib.fail("Account Number under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    public String getValue(List<String> list, String rowToSearch, String valueToSearch) throws ParseException {
        String result = null;
        JSONParser parser = new JSONParser();
        for (String s : list)
            if (s.contains(rowToSearch)) {
                JSONObject json = (JSONObject) parser.parse(s);
                if (valueToSearch.equals(status)) {
                    result = String.valueOf(json.get(status));
                    break;
                } else if (valueToSearch.equals(statusCode)) {
                    result = String.valueOf(json.get(statusCode));
                    break;
                } else {
                    result = json.get("result").toString();
                    if ((StringUtils.contains(String.valueOf(json.get(statusCode)), "200") || StringUtils.contains(String.valueOf(json.get(statusCode)), "400")) && StringUtils.contains(String.valueOf(json.get(status)), "SUCCESS")) {
                        result = result.substring(1, result.length() - 1).replace("\"", "");
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
        result = result == null || result.equals("") ? "-" : result.toLowerCase().trim();
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
        Boolean visible = isElementVisible(pageElements.othersTab);
        commonLib.info("Checking others tab is visible on demographic widget: '" + visible);
        return visible;
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
     * @return the email id
     */
    public String getEmailId() {
        commonLib.info(getText(pageElements.emailId));
        return getText(pageElements.emailId);
    }

    /**
     * This method is used to get email ID from demographic widget
     *
     * @return the message
     */
    public String getUnableToFetch() {
        commonLib.info(getText(pageElements.unableToFetch));
        return getText(pageElements.unableToFetch);
    }

    /**
     * This method will return round off value up-to two decimal in string format
     *
     * @param inputString inputString input string
     * @return the result
     */
    public static String getTwoDecimalValue(String inputString) {
        DecimalFormat df = new DecimalFormat("0.00");
        double input = Double.parseDouble(inputString);
        return df.format(input);
    }

    /**
     * This method is used to convert date from long to string
     *
     * @param epoch   epoc value
     * @param pattern date pattern
     * @return the result
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
     * @param str1 date 1
     * @param str2 date 2
     * @return the difference
     */
    public static String getDateDiffInMonths(String str1, String str2) {
        long monthsBetween = ChronoUnit.MONTHS.between(
                LocalDate.parse(str1).withDayOfMonth(1),
                LocalDate.parse(str2).withDayOfMonth(1));
        return Long.toString(monthsBetween);

    }

    /**
     * This method will give us date
     *
     * @param str the string
     * @return the substring
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
     * @param inputString the input string
     * @return the result
     */
    public static boolean getDecimalValue(String inputString) throws ArrayIndexOutOfBoundsException {

        boolean flag = false;
        if (!inputString.equalsIgnoreCase("-")) {
            String[] result = inputString.split("\\.");
            if (result[1].length() == 2) {
                flag = true;
            }
        }
        return flag;
    }

    /*
   This Method will let us know is account number is bold or not
    */
    public String getAccountNumberStyle() {
        String result = "";
        if (isElementVisible(pageElements.accountNumber)) {
            result = selUtils.getDataPointFontWeight(pageElements.accountNumber);
        }
        return result;
    }

    /*
  This Method will let us know is last payment mode is bold or not
   */
    public String getLastPaymentModeStyle() {
        String result = "";
        if (isElementVisible(pageElements.lastPaymentMode) && !getText(pageElements.lastPaymentMode).equalsIgnoreCase("-"))
            result = selUtils.getDataPointFontWeight(pageElements.lastPaymentMode);
        else
            commonLib.warning("Last Payment Mode value is not available, so can not verify BOLD characterstics");
        return result;

    }

    /*
    This Method will return the color for Total Credit Limit Data Point
     */
    public String getTotalCreditLmtCurrencyStyle() {
        return selUtils.getDataPointColor(pageElements.totalCreditLimitCurrency);
    }

    /*
    This Method will return the color for Available Credit Limit Data Point
     */
    public String getAvlCreditLmtCurrencyStyle() {
        return selUtils.getDataPointColor(pageElements.availCreditLimitCurrency);
    }

    /*
    This Method will return the color for Last month bill amount Data Point
     */
    public String getLastMonthBillAmountCurrencyStyle() {
        return selUtils.getDataPointColor(pageElements.lastMonthBillAmountCurrency);
    }

    /*
    This Method will return the color for Total Outstanding Data Point
     */
    public String getTotalOutstandingCurrencyStyle() {
        return selUtils.getDataPointColor(pageElements.totalOutstandingCurrency);
    }

    /*
    This Method will return the color for Last Payment Mode Data Point
     */
    public String getLastPaymentModeCurrencyStyle() {
        return selUtils.getDataPointColor(pageElements.lastPaymentModeCurrency);
    }

    /*
    This Method will return the color for Current Month Un-billed Data Point
     */
    public String getCurrentMonthUnbillCurrencyStyle() {
        return selUtils.getDataPointColor(pageElements.currentMonthUnbillCurrency);
    }

    /*
   This Method will let us know is Total credit limit is bold or not
    */
    public String getTtlCreditLimitNumberStyle() {
        String result = "";
        if (isElementVisible(pageElements.totalCreditLimit) && !getText(pageElements.totalCreditLimit).equalsIgnoreCase("-")) {
            result = selUtils.getDataPointFontWeight(pageElements.totalCreditLimit);
        }else
            commonLib.warning("Total credit limit value is not available, so can not verify BOLD characteristics");
        return result;
    }

    /*
   This Method will let us know is available credit limit is bold or not
    */
    public String getAvlCreditLimitNumberStyle() {
        String result = "";
        if (isElementVisible(pageElements.availableCreditLimit) && !getText(pageElements.availableCreditLimit).equalsIgnoreCase("-")) {
            result = selUtils.getDataPointFontWeight(pageElements.availableCreditLimit);
        }else
            commonLib.warning("Available credit limit value is not available, so can not verify BOLD characteristics");
        return result;
    }

    /*
   This Method will let us know is total outstanding is bold or not
    */
    public String getTotalOutstandingLimitNumberStyle() {
        String result = "";
        if (isElementVisible(pageElements.totalOutstanding) && !getText(pageElements.totalOutstanding).equalsIgnoreCase("-")) {
            result = selUtils.getDataPointFontWeight(pageElements.totalOutstanding);
        }else
            commonLib.warning("Total outstanding value is not available, so can not verify BOLD characteristics");
        return result;
    }

    /*
   This Method will let us know is due date is bold or not
    */
    public String getDueDateNumberStyle() {
        String result = "";
        if (isElementVisible(pageElements.dueDate) && !getText(pageElements.dueDate).equalsIgnoreCase("-")) {
            result = selUtils.getDataPointFontWeight(pageElements.dueDate);
        } else
            commonLib.warning("Due Date value is not available, so can not verify BOLD characteristics");
        return result;
    }

    /*
   This Method will let us know is current cycle is bold or not
    */
    public String getCurrentCycleNumberStyle() {
        String result = "";
        if (isElementVisible(pageElements.currentCycle) && !getText(pageElements.currentCycle).equalsIgnoreCase("-")) {
            result = selUtils.getDataPointFontWeight(pageElements.currentCycle);
        }else
            commonLib.warning("Current Cycle value is not available, so can not verify BOLD characteristics");
        return result;
    }

    /*
       This Method will give us Temp credit limit
        */
    public String getTempCreditiLimit() {
        String result = null;
        if (isElementVisible(pageElements.tempCreditLimit)) {
            result = getText(pageElements.tempCreditLimit);
            commonLib.info("Temp credit limit is: " + result);
        } else {
            commonLib.fail("Temp credit limit under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us Temp credit limit currency
        */
    public String getTempCreditCurrency() {
        String result = null;
        if (isElementVisible(pageElements.tempCreditCurrency)) {
            result = getText(pageElements.tempCreditCurrency);
            commonLib.info("Temp credit limit currency is: " + result);
        } else {
            commonLib.fail("Temp credit limit currency under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will check if Temp credit limit info icon is visible
        */
    public Boolean isTempCreditLimitInfoVisible() {
        Boolean status = isElementVisible(pageElements.tempCreditLimitInfoIcon);
        commonLib.info("Temp credit limit info icon visibilty is " + status);
        return status;
    }

    /**
     * This method is use to hover on SIM Status info icon
     */
    public void hoverOnTempCreditLimitInfoIcon() {
        commonLib.info("Hover on SIM Status Reason Info icon");
        hoverOverElement(pageElements.tempCreditLimitInfoIcon);
        commonLib.hardWait(1);
    }

    /*
       This Method will give us Temp credit limit currency
        */
    public String getValidTilldate() {
        String result = null;
        if (isElementVisible(pageElements.validTillDate)) {
            result = getText(pageElements.validTillDate).split("-")[1].trim();
            commonLib.info("Valid Till date is: " + result);
        } else {
            commonLib.fail("Valid Till date under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us current cycle end date
        */
    public String getCurrentCycleEndDate() {
        final String text = getText(pageElements.currentCycleEndDate).split("-")[1].trim();
        commonLib.info(text);
        return text;
    }

    /**
     * This method is use to check raise SR icon display or not
     * @return true/false
     */
    public Boolean isSRIconDisplay(){
        commonLib.info("Checking SR Icon Display or not");
        return isVisible(pageElements.raiseSRIcon);
    }

    /**
     * This method is use to click raise sr icon
     */
    public void clickSRRaiseIcon(){
        commonLib.info("clicking raise SR icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.raiseSRIcon);
    }

    /**
     * This method is use to check raise SR Issue detail pop up display or not
     * @return true/false
     */
    public Boolean isIssueDetailPopUpDisplay(){
        commonLib.info("Checking Issue Pop up Display or not");
        return isVisibleContinueExecution(pageElements.popupTitle);
    }

    /**
     * This method is use to read success pop up message
     * @return String The Value
     */
    public String getSuccessMessage(){
        commonLib.info("Reading success message");
        return getText(By.xpath(pageElements.successMessage));
    }

    /**
     * This method is use to click close icon button of open popup
     */
    public void clickClosePopup(){
        commonLib.info("Closing Open Modal");
        clickWithoutLoader(pageElements.closePopup);
    }

    /**
     * This method use to get account number from issue field pop up
     * @return String The value
     */
    public String getAccountNumberFromIssuePopup(){
        commonLib.info("Reading Account Number from issue pop up");
        return getAttribute(pageElements.accountNumberInput,"value",false);
    }

    /**
     * This method is use to enter account number
     * @param accountNumber The Account Number
     */
    public void enterAccountNumberInPopUp(String accountNumber){
        commonLib.info("Entering Account Number as account number not fetched from UI or API");
        enterText(pageElements.accountNumberInput,accountNumber);
    }

    /**
     * This method use to get Ticket number from issue field pop up
     * @return String The value
     */
    public String getTicketId(){
        commonLib.info("Reading Ticket Id from issue pop up");
        return getText(pageElements.ticketId);
    }

    /**
     * This method use to get Expected Closure Date from issue field pop up
     * @return String The value
     */
    public String getExpectedClosureDate(){
        commonLib.info("Reading Expected Closure Date from issue pop up");
        return getText(pageElements.expectedClosureDate);
    }

    /**
     * This method is use to check enter amount field display or not
     * @return true/false
     */
    public Boolean isEnterAmount(){
        commonLib.info("Checking Amount field display or not");
        return isVisible(pageElements.amountField);
    }

    /**
     * This method is use to enter amount into amount field
     * @param amount The Amount
     */
    public void writeAmount(String amount){
        commonLib.info("Entering Amount");
        enterText(pageElements.amountField,amount);
    }

}