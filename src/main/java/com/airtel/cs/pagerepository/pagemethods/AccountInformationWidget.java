package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AccountInformationWidgetPage;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

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
        commonLib.info(config.getProperty("iconVisibleOnDetailAccInfo") + status);
        return status;
    }

    /*
       This Method will give us Last Month Bill Amount
        */
    public String getLastMonthBillAmount() {
        commonLib.info(getText(pageElements.lastMonthBillAmount));
        return getText(pageElements.lastMonthBillAmount);
    }

    /*
       This Method will give us Current Month Un-bill Amount
        */
    public String getCurrentMonthUnBillAmount() {
        commonLib.info(getText(pageElements.currentMonthUnbillAmount));
        return getText(pageElements.currentMonthUnbillAmount);
    }

    /*
       This Method will give us current cycle
        */
    public String getCurrentCycle() {
        final String text = getText(pageElements.currentCycle);
        commonLib.info(text);
        return text;
    }

    /*
       This Method will give us due date
        */
    public String getDueDate() {
        commonLib.info(getText(pageElements.dueDate));
        return getText(pageElements.dueDate);
    }

    /*
       This Method will give us Last payment mode
        */
    public String getLastPaymentMode() {
        String result = null;
        if (isVisible(pageElements.securityDeposit)) {
            result = getText(pageElements.lastPaymentMode);
            commonLib.info("Last Payment Mode is: " + result);
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
            commonLib.info("Security Deposit is: " + result);
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
            commonLib.info("Total Outstanding is: " + result);
        } else {
            commonLib.fail("Total Outstanding under Account Information Widget is NOT visible", true);
        }
        return result;
    }

    /*
       This Method will give us account number
        */
    public String getAccountNumber() {
        String result = null;
        String elementCss = null;
        if (isVisible(pageElements.accountNumber)) {
            result = getText(pageElements.accountNumber);
            commonLib.info("Account Number is: " + result);
            elementCss=getAttribute(pageElements.accountNumber,"style",false);

        } else {
            commonLib.fail("Account Number under Account Information Widget is NOT visible", true);
        }
        return result;
    }

   /* public String getStyle(By elementLocation, String attributeName, stylePropCss) {
        var x = document.getElementById(elementLocation);
        var y="";
        if (x.currentStyle)
             y = x.currentStyle[stylePropJs];
        else if (window.getComputedStyle)
             y = document.defaultView.getComputedStyle(x,null).getPropertyValue(stylePropCss);
        return y;
    }*/

    public String getValue(List<String> list, String rowToSearch, String valueToSearch) throws ParseException {
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
    }

    /*
       This Method will give us Temp credit limit currency
        */
    public String getValidTilldate() {
        String result = null;
        if (isElementVisible(pageElements.validTillDate)) {
            result = getText(pageElements.validTillDate);
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
        final String text = getText(pageElements.currentCycleEndDate);
        commonLib.info(text);
        return text;
    }
}