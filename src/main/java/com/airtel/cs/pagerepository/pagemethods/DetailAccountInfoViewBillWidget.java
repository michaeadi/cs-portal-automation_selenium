package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailAccountInfoViewBillWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class DetailAccountInfoViewBillWidget extends BasePage {

    DetailAccountInfoViewBillWidgetPage pageElements;
    private final String SCROLL_TO_WIDGET_MESSAGE = config.getProperty("scrollToWidgetMessage");

    public DetailAccountInfoViewBillWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DetailAccountInfoViewBillWidgetPage.class);
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
     * This method use to check whether account intformation widget display or not
     *
     * @return true/false
     */
    public Boolean isPdfDisplay() {
        commonLib.info("Checking that pdf is Displayed");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.pdf);
            status = isElementVisible(pageElements.pdf);
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
     *
     * @return
     */
    public String getAccountInfoDetailWidget() {
        commonLib.info(getText(pageElements.accountInfoDetailWidget));
        return getText(pageElements.accountInfoDetailWidget);
    }

    /**
     * This method use to open account info detail page
     */
    public void openAccountInformationDetailPage() {
        commonLib.info("Opening More under account information");
        if (isVisible(pageElements.accountInfoIcon)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.accountInfoIcon);
        } else {
            commonLib.error("Unable to open account info detail page");
        }
    }


    /**
     * This method is used to check account infor tab in detail account info
     *
     * @return
     */
    public String getAccountInfoTabName() {
        commonLib.info(getText(pageElements.accountInfo));
        return getText(pageElements.accountInfo);
    }

    /**
     * This method is used to get Bill Status on Account Info details page
     *
     * @return
     */
    public String getTransactionType(int row) {
        return getText(By.xpath(pageElements.transactionType1 + row + pageElements.transactionType2));
    }

    /**
     * This method is used to check view bill icon in detail account info widget
     *
     * @return
     */
    public Boolean isViewBillIconDisplay(int row) {
        Boolean status = isElementVisible(By.xpath(pageElements.viewBillIcon1 + row + pageElements.viewBillIcon2));
        commonLib.info("Checking view bill icon is visible on detailed account information: '" + status);
        return status;
    }

    /**
     * This method is used to check whether next page icon is visible or not
     *
     * @return
     */
    public Boolean nextPageVisible() {
        Boolean status = isElementVisible(pageElements.clickNext);
        commonLib.info("Checking Nex page icon is visible: " + status);
        return status;
    }

    /**
     * This method is used to click on next page page
     *
     * @return
     */
    public void clickNextPage() {
        commonLib.info("Opening More under account information");
        clickWithoutLoader(pageElements.clickNext);
    }

    /**
     * This method use to click on view bill icon
     */
    public void clickingOnViewBillIcon(int row) {
        commonLib.info("Clicking view bill icon");
        if (isVisible(By.xpath(pageElements.viewBillIcon1 + row + pageElements.viewBillIcon2))) {
            clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.viewBillIcon1 + row + pageElements.viewBillIcon2));
        } else {
            commonLib.error("Unable to open bill");
        }
    }

    /**
     * This method is used to show bill fetch error
     *
     * @return
     */
    public String viewBillError() {
        commonLib.info(getText(pageElements.unableToFetchBill));
        return getText(pageElements.unableToFetchBill);
    }

    /**
     * This method is used to check view bill is visible or not
     *
     * @return
     */
    public Boolean isBillVisible() {
        Boolean status = isElementVisible(pageElements.viewBillOpen);
        commonLib.info("Checking is view bill visible on detailed account information: '" + status);
        return status;
    }

    /**
     * This method is used to check is nexy page icon visible on pdf or not
     * @return
     */
    public Boolean isNextPagePDFVisible() {
        Boolean status = isElementVisible(pageElements.clickNextPage);
        commonLib.info("Checking is next page icon visible on pdf or not: '" + status);
        return status;
    }

    /**
     * This method is used to click on prev page
     */
    public void clickingOnPrevPagePDF() {
        commonLib.info("Clicking prev page on view bill");
        clickAndWaitForLoaderToBeRemoved(pageElements.clickPrevPage);
    }

    /**
     * This method is used to click on next page
     */
    public void clickingOnNextPagePDF() {
        commonLib.info("Clicking next page on view bill");
        clickAndWaitForLoaderToBeRemoved(pageElements.clickNextPage);

    }

    /**
     * This will will route you to Home Tab over customer profile page
     */
    public void goToHomeTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.homePage);
    }

    /**
     * This method is used to get Bill Number on Account Info details page
     * @return
     */
    public String getBillNumber(int row) {
        return getText(By.xpath(pageElements.billNumber1 + row + pageElements.billNumber2));
    }

    /**
     * This will will route you to view history
     */
    public void goToViewHistoryTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.viewHistory);
    }

    /**
     * This will will route you to action trail
     */
    public void goActionTrailTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.actionTrail);
    }

    /**
     * This will will route you to action trail
     */
    public void clickingOnDropDown() {
        clickAndWaitForLoaderToBeRemoved(pageElements.actionTrailLatestDropdown);
    }

    /**
     * This method is used to get bill number from action trail drop down
     * @return
     */
    public String getBillNo() {
        commonLib.info(getText(pageElements.actionTrailBillNo));
        return getText(pageElements.actionTrailBillNo);
    }

    /**
     * This method is used to get action type form action train
     * @return
     */
    public String getActionType() {
        commonLib.info(getText(pageElements.actionType));
        return getText(pageElements.actionType);
    }

    /**
     * This method is used to get reason form action train
     * @return
     */
    public String getReason() {
        commonLib.info(getText(pageElements.reason));
        return getText(pageElements.reason);
    }

    /**
     * This method is used to get comment form action train
     * @return
     */
    public String getComment() {
        commonLib.info(getText(pageElements.comment));
        return getText(pageElements.comment);
    }

    /**
     * Used to get pdf sub widget
     * @param billNo
     * @return
     */
    public String getPdfSubWidget(String billNo) {
        return getText(By.xpath(pageElements.pdfSubWidget1 + billNo + pageElements.pdfSubWidget2));
    }

    /**
     * This method is used to get last 4 chars of string
     * @param input
     * @return
     */
    public static String getLast4Char(String input) {
        String lastFourDigits = null;
        if (input.length() > 4)
        {
            lastFourDigits = input.substring(input.length() - 4);
        }
        else
        {
            lastFourDigits = input;
        }
        return lastFourDigits;
    }

}