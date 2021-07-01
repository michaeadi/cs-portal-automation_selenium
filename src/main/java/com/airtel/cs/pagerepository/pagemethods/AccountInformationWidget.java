package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AccountInformationWidgetPage;
import com.airtel.cs.pagerepository.pageelements.DADetailsPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class AccountInformationWidget extends BasePage {

    AccountInformationWidgetPage pageElements;

    private final String SCROLL_TO_WIDGET_MESSAGE="Not able scroll to the widget";

    public AccountInformationWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AccountInformationWidgetPage.class);
    }

    /**
     * This method use to check whether account intformation widget display or not
     * @return true/false
     * */
    public Boolean isAccountInformationWidgetDisplay(){
        commonLib.info("Checking that Account Information widget is Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.getTitle);
            status=isElementVisible(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return  status;
    }

    /*
       This Method will give us footer auuid shown in Account Information widget
       UHW = Account Information Widget
        */
    public String getFooterAuuid() {
        commonLib.info(getText(pageElements.footerAuuid));
        return getText(pageElements.footerAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the Account Information modal
    UHW = Account Information Widget
     */
    public String getMiddleAuuid() {
        String result;
        result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        commonLib.info(result);
        return result;
    }

    /**
     * This method use to get detailed account information widget more icon displayed or not
     * @return Boolean The  data value
     * */
    public Boolean isActionIconVisibleOnAccountInfo() {
        Boolean status=isElementVisible(pageElements.accountInfoDetailed);
        commonLib.info("Checking more icon is visible on detailed account information: '"+status);
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
        commonLib.info(getText(pageElements.currentCycle));
        return getText(pageElements.currentCycle);
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
        commonLib.info(getText(pageElements.lastPaymentMode));
        return getText(pageElements.lastPaymentMode);
    }

    /*
       This Method will give us security deposit
        */
    public String getSecurityDeposit() {
        commonLib.info(getText(pageElements.securityDeposit));
        return getText(pageElements.securityDeposit);
    }

    /*
       This Method will give us security deposit
        */
    public String getTotalOutstanding() {
        commonLib.info(getText(pageElements.totalOutstanding));
        return getText(pageElements.totalOutstanding);
    }

    /*
       This Method will give us account number
        */
    public String getAccountNumber() {
        commonLib.info(getText(pageElements.accountNumber));
        return getText(pageElements.accountNumber);
    }


}