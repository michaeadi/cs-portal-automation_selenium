package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.model.cs.response.postpaid.enterprise.MsisdnDetail;
import com.airtel.cs.pagerepository.pageelements.LinkedMsisdnToAccountNoPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class LinkedMsisdnToAccountNoWidget extends BasePage{

    LinkedMsisdnToAccountNoPage pageElements;
    List<WebElement> rows;

    private final String SCROLL_TO_WIDGET_MESSAGE=config.getProperty("scrollToWidgetMessage");

    public LinkedMsisdnToAccountNoWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LinkedMsisdnToAccountNoPage.class);
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
    This Method will give us footer auuid shown in Detailed Account Information widget
    Account Information Widget
        */
    public String getFooterAuuid() {
        commonLib.info(getText(pageElements.footerAuuid));
        return getText(pageElements.footerAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the Detailed Account Information modal
    Account Information Widget
     */
    public String getMiddleAuuid() {
        String result;
        result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        commonLib.info(result);
        return result;
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
            scrollToViewElement(pageElements.getDetailAccInfoWidget);
            status = isElementVisible(pageElements.getDetailAccInfoWidget);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE, true);
        }
        return status;
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
     * This method use to get account information widget more icon displayed or not
     *
     * @return Boolean The  data value
     */
    public Boolean isActionIconVisibleOnAccountInfo() {
        Boolean status = isElementVisible(pageElements.accountInfoIcon);
        commonLib.info("Checking more icon is visible on account information widget: '" + status);
        return status;
    }

    /**
     * This method is used to searched msisdn checkbox in Linked Msisdn Table
     * @return
     */
    public Boolean getSearchMsisdnChkBox() {
        Boolean status = isElementVisible(pageElements.searchMsisdnInLinkedMsisdnWidget);
        commonLib.info("Checking searched msisdn checkbox in Linked Msisdn Table : '" + status);
        return status;
    }

    /**
     * This method is used to get no. of entries in linked msisdn
     * */
    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.linkedMsisdnPagination);
        commonLib.info("rows : "+ rows.size());
        return rows.size();
    }


    /**
     * This method is used to validate pagination in linked msisdn to account
     * @return
     */
    public Boolean getAccountInfoDetailPagination() {
        Boolean status = isElementVisible(pageElements.linkedMsisdnPagination);
        commonLib.info("Account info detail pagination is visible on linked msisdn widget: '" + status);
        return status;
    }


    /**
     * This method will return boolean value for pagination
     * @return
     */
    public Boolean getPagination() {
        Boolean status = isElementVisible(pageElements.pagination);
        commonLib.info("Pagination is visible on linked msisdn widget: '" + status);
        return status;
    }

    /**
     * This method is used to validate Account info checkbox in account info tab
     * @return
     */
    public Boolean getCheckboxAccountInfo() {
        Boolean status = isElementVisible(pageElements.checkboxAccountInfo);
        commonLib.info("Checkbox for account info details is visible on detailed account information: '" + status);
        return status;
    }

    /**
     * This method is used to validate linked msisdn checkbox in account info tab
     * @return
     */
    public Boolean getCheckboxLinkedMsisdn() {
        Boolean status = isElementVisible(pageElements.checkboxLinkedMsisdn);
        commonLib.info("Checkbox for linked msisdn is visible on detailed account information: '" + status);
        return status;
    }

    /**
     * This is used to fetch header names
     * @param column
     * @return
     */
    public String getPackHeaders(int column) {
        String header = getText(By.xpath(pageElements.packDetailsHeaderRow + column + pageElements.packDetailsHeaderValue));
        commonLib.info("Getting Pack header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method is used to check account information tab
     * @return
     */
    public String getAccountInfoTab() {
        commonLib.info(getText(pageElements.accountInfoTab));
        return getText(pageElements.accountInfoTab);
    }

    /**
     * This method is used to check linked msisdn widget in detail account info
     * @return
     */
    public String getAccountInfoDetailWidget() {
        commonLib.info(getText(pageElements.linkedMsisdnWidget));
        return getText(pageElements.linkedMsisdnWidget);
    }

    /**
     * This method is used to check duplicate msisdn in list
     * @param msisdnDetailList
     * @return
     */
    public boolean areAllDistinct(List<MsisdnDetail> msisdnDetailList) {
        return msisdnDetailList.stream().map(MsisdnDetail::getMsisdn).distinct().count() == msisdnDetailList.size();
    }

    /**
     * This method use to get msisdn from linked msisdn detail widget
     * */
    public String getMsisdn(int rowNumber) {
        By msisdn = By.xpath(pageElements.valueRow + rowNumber + pageElements.msisdnValue);
        final String text = getText(msisdn);
        commonLib.info("Getting msisdn from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get sim no from linked msisdn detail widget
     * */
    public String getSim(int rowNumber) {
        By sim = By.xpath(pageElements.valueRow + rowNumber + pageElements.simValue);
        final String text = getText(sim);
        commonLib.info("Getting sim from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get line type from linked msisdn detail widget
     * */
    public String getLineType(int rowNumber) {
        By lineType = By.xpath(pageElements.valueRow + rowNumber + pageElements.lineTypeValue);
        final String text = getText(lineType);
        commonLib.info("Getting lineType from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get payment level from linked msisdn detail widget
     * */
    public String getPaymentLvl(int rowNumber) {
        By paymentLvl = By.xpath(pageElements.valueRow + rowNumber + pageElements.paymentLvlValue);
        final String text = getText(paymentLvl);
        commonLib.info("Getting lineType from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get gsm status from linked msisdn detail widget
     * */
    public String getGsmStatus(int rowNumber) {
        By gsm = By.xpath(pageElements.valueRow + rowNumber + pageElements.gsmStatusValue);
        final String text = getText(gsm);
        commonLib.info("Getting gsm from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get vip from linked msisdn detail widget
     * */
    public String getVip(int rowNumber) {
        By vip = By.xpath(pageElements.valueRow + rowNumber + pageElements.vipValue);
        final String text = getText(vip);
        commonLib.info("Getting vip from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get cug from linked msisdn detail widget
     * */
    public String getCug(int rowNumber) {
        By cug = By.xpath(pageElements.valueRow + rowNumber + pageElements.cugValue);
        final String text = getText(cug);
        commonLib.info("Getting cug from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get dnd from linked msisdn detail widget
     * */
    public String getDnd(int rowNumber) {
        By dnd = By.xpath(pageElements.valueRow + rowNumber + pageElements.dndValue);
        final String text = getText(dnd);
        commonLib.info("Getting dnd from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method is used to ger current usage limit from linked msisdn detail widget
     * @param rowNumber
     * @return
     */
    public String getCurrentUsageLimit(int rowNumber) {
        By cul = By.xpath(pageElements.valueRow + rowNumber + pageElements.currentUsageLmtValue);
        final String text = getText(cul);
        commonLib.info("Getting current usage limit from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method is used to ger security deposit from linked msisdn detail widget
     * @param rowNumber
     * @return
     */
    public String getSecurityDep(int rowNumber) {
        By secDep = By.xpath(pageElements.valueRow + rowNumber + pageElements.securityDepValue);
        final String text = getText(secDep);
        commonLib.info("Getting security deposit from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method is use to click on msisdn search box
     */
    public void clickingMsisdnSearchBox() {
        commonLib.info("Clicking on msisdn search box");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchMsisdnInLinkedMsisdnWidget);
    }

    /**
     * This method is use to enter number in search field
     * @param number The Number
     */
    public void enterNumber(String number) {
        if (isVisible(pageElements.searchMsisdnInLinkedMsisdnWidget)) {
            enterText(pageElements.searchMsisdnInLinkedMsisdnWidget, number);
        } else {
            commonLib.error("Search box is NOT visible");
        }
    }

    /**
     * This method is use to click on msisdn search box
     */
    public void clickingOnSearch() {
        commonLib.info("Clicking on msisdn search box");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtn);
    }

    /**
     * This method is used to check msisdn is invalid or not
     * @return
     */
    public Boolean isMsisdnInvalid() {
        Boolean status = isElementVisible(pageElements.invalidNumber);
        commonLib.info("Checking that number is invalid or not '" + status);
        return status;
    }

    /**
     * This method is used to get invalid number message
     * @return
     */
    public String getInvalidNumberMessage() {
        commonLib.info(getText(pageElements.invalidNumber));
        return getText(pageElements.invalidNumber);
    }

    /**
     * This method is used to get first word form string
     * @param input
     * @return
     */
    public static String firstWord(String input) {
        String result = "";  // Return empty string if no space found

        for(int i = 0; i < input.length(); i++)
        {
            if(input.charAt(i) == ' ')
            {
                result = input.substring(0, i);
                break; // because we're done
            }
        }
        return result;
    }

    /**
     * This is used to check is prev button enable or not
     * @return
     */
    public Boolean isPaginationRightMoveEnable() {
        Boolean status = isElementVisible(pageElements.nextPageArrow);
        commonLib.info("Checking right move in linked msisdn pagination is enable : '" + status);
        return status;
    }

    /**
     * Clicking on next page
     */
    public void clickingOnNextPage() {
        commonLib.info("Clicking on next page");
        clickAndWaitForLoaderToBeRemoved(pageElements.nextPageArrow);
    }

    /**
     * This is used to check is prev button enable or not
     * @return
     */
    public Boolean isPaginationLeftMoveEnable() {
        Boolean status = isElementVisible(pageElements.prevPageArrow);
        commonLib.info("Checking left move in linked msisdn pagination is enable : '" + status);
        return status;
    }

    /**
     * Clicking on previous page
     */
    public void clickingOnPrevPage() {
        commonLib.info("Clicking on prev page");
        clickAndWaitForLoaderToBeRemoved(pageElements.prevPageArrow);
    }

    /**
     * This method use to get total number of checkbox display
     * @return Integer The Size
     */
    public int getTotalWidgetsInPM() {
        List<WebElement> widgetsRowsElements = returnListOfElement(pageElements.totalWidgetsInPm);
        final int size = widgetsRowsElements.size();
        commonLib.info("Getting number of checkbox : " + size);
        return size;
    }

    /**
     * This method use to get header name from profile management
     * @param column The column number
     * @return String The header name
     * */
    public String getHeaders(int column) {
        String header = readTextOnRows(pageElements.accountInfoTab, column);
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

}