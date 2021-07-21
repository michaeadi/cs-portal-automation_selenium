package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailAccountInfoWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class DetailAccountInfoWidget extends BasePage {

    DetailAccountInfoWidgetPage pageElements;
    private final String SCROLL_TO_WIDGET_MESSAGE=config.getProperty("scrollToWidgetMessage");

    public DetailAccountInfoWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DetailAccountInfoWidgetPage.class);
    }

    /**
     * This method use to check whether account intformation widget display or not
     *
     * @return true/false
     */
    public Boolean isDetailedAccountInformationWidgetDisplay() {
        commonLib.info("Checking that Detailed Account Information widget is Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.getTitle);
            status = isElementVisible(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE, true);
        }
        return status;
    }

    /**
     * This method use to get account information widget more icon displayed or not
     *
     * @return Boolean The  data value
     */
    public Boolean isActionIconVisibleOnAccountInfo() {
        Boolean status = isElementVisible(pageElements.accountInfoIcon);
        commonLib.info("Checking more icon is visible on detailed account information: '" + status);
        return status;
    }

    /**
     * This method is used to check account information widget in detail account info
     * @return
     */
    public String getAccountInfoDetailWidget() {
        commonLib.info(getText(pageElements.accountInfoDetailWidget));
        return getText(pageElements.accountInfoDetailWidget);
    }

    /**
     * This method use to click menu button for account info detail page
     */
    public void openAccountInformationDetailPage() {
        commonLib.info("Opening More under account information");
        if(isVisible(pageElements.accountInfoIcon)){
            clickAndWaitForLoaderToBeRemoved(pageElements.accountInfoIcon);
        }else{
            commonLib.error("Unable to open account info detail page");
        }
    }

    /**
     * This method is used to check account information widget in detail account info
     * @return
     */
    public Boolean isBillDisputeDisplay(int row) {
        Boolean status = isElementVisible(By.xpath(pageElements.billDisputIcon + row + pageElements.billDisputIcon2));
        commonLib.info("Checking Bill dispute thumb icon is visible on detailed account information: '" + status);
        return status;
    }

    /**
     * This method is used to open Bill dispute page
     * @return
     */
    public void openBillDisputePage(int row) {
        commonLib.info("Opening Bill dispute page");
        if(isVisible(By.xpath(pageElements.billDisputIcon + row + pageElements.billDisputIcon2))){
            clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.billDisputIcon + row + pageElements.billDisputIcon2));
        }else{
            commonLib.error("Unable to open Bill dispute page");
        }
    }

    /**
     * This method is used to check account information widget in detail account info
     * @return
     */
    public String getBillDisputeDetailsHeader() {
        commonLib.info(getText(pageElements.billDisputHeader));
        return getText(pageElements.billDisputHeader);
    }


    /**
     * This method is used to get Bill Number
     * @return
     */
    public String getBillDisputeNumber() {
        commonLib.info(getText(pageElements.billNumber));
        return getText(pageElements.billNumber);
    }

    /**
     * This method is used to get Account Number
     * @return
     */
    public String getBillDisputeAccountNumber() {
        commonLib.info(getText(pageElements.accountNumber));
        return getText(pageElements.accountNumber);
    }

    /**
     * This method is used to get Bill Date and Time
     * @return
     */
    public String getBillDisputeDateTime() {
        commonLib.info(getText(pageElements.billDateTime));
        return getText(pageElements.billDateTime);
    }

    /**
     * This method is used to get Bill status
     * @return
     */
    public String getBillDisputeStatus() {
        commonLib.info(getText(pageElements.billStatus));
        return getText(pageElements.billStatus);
    }

    /**
     * This method is used to get Bill Number on Account Info details page
     * @return
     */
    public String getBillNumber(int row) {
        return getText(By.xpath(pageElements.billNumber1 + row + pageElements.billNumber2));
    }

    /**
     * This method is used to get Bill DateTime on Account Info details page
     * @return
     */
    public String getBillDateTime(int row) {
        return getText(By.xpath(pageElements.billDateTime1 + row + pageElements.billDateTime2));
    }

    /**
     * This method is used to get Bill Status on Account Info details page
     * @return
     */
    public String getBillStatus(int row) {
        return getText(By.xpath(pageElements.billStatus1 + row + pageElements.billStatus2));
    }

    /**
     * This method is used to get Bill Status on Account Info details page
     * @return
     */
    public String getTransactionType(int row) {
        return getText(By.xpath(pageElements.transactionType1 + row + pageElements.transactionType2));
    }

    /**
     * This method is used to check whether next page icon is visible or not
     * @return
     */
    public Boolean nextPageVisible(){
        Boolean status = isElementVisible(pageElements.clickNext);
        commonLib.info("Checking Nex page icon is visible: " + status);
        return status;
    }

    /**
     * This method is used to click on next page page
     * @return
     */
    public void clickNextPage() {
        commonLib.info("Opening More under account information");
        clickWithoutLoader(pageElements.clickNext);
    }

    /**
     * This method use to get toast message
     * @return String The Value
     */
    public String getToastText() {
        String result = null;
        if (isVisible(pageElements.toastModal)) {
            result = getText(pageElements.toastModal);
            clickWithoutLoader(pageElements.raiseDisputeCloseButton);
        } else {
            commonLib.fail("Exception in method - getToastText", true);
            commonLib.info("Going to Close Modal through close Button");
            clickWithoutLoader(pageElements.raiseDisputeCloseButton);
        }
        return result;
    }
}