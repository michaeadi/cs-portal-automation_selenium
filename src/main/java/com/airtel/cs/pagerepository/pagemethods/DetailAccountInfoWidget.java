package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailAccountInfoWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Log4j2
public class DetailAccountInfoWidget extends BasePage {

    DetailAccountInfoWidgetPage pageElements;
    List<WebElement> rows;
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
     * This method is used to check account information tab
     * @return
     */
    public String getAccountInfoTab() {
        commonLib.info(getText(pageElements.accountInfo));
        return getText(pageElements.accountInfo);
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
     * This method use to get header name from profile management
     * @param column The column number
     * @return String The header name
     * */
    public String getHeaders(int column) {
        String header = readTextOnRows(pageElements.accountInfoTab, column);
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get total number of checkbox display
     * @return Integer The Size
     */
    public int getWidgetRowsSize() {
        List<WebElement> widgetsRowsElements = returnListOfElement(pageElements.checkboxInAccountInfo);
        final int size = widgetsRowsElements.size();
        commonLib.info("Getting number of checkbox : " + size);
        return size;
    }

    /**
     * This method use to check checkbox is visible or not
     * @return true/false
     * */
    public Boolean isCheckboxDisplay(){
        commonLib.info("Checking that checkbox Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.checkboxInAccountInfo);
            status=isElementVisible(pageElements.checkboxInAccountInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return  status;
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
     * This method is used to get no. of entries in account info detail
     * @return
     */
    public String getAccountInfoDetailRows() {
        commonLib.info(getText(pageElements.paginationDetailInfo));
        return getText(pageElements.paginationDetailInfo);
    }

    /**
     * This method will return boolean value true or false if pagination is applicable or not
     * @return
     */
    public Boolean isDetailAccPagination() {
        Boolean status = isElementVisible(pageElements.paginationDetailInfo);
        commonLib.info("Checking detailed account information pagination : '" + status);
        return status;
    }

    /**
     * This method return a boolean value if left move enable on detailed account information pagination
     * @return
     */
    public Boolean isDetailAccPaginationLeftMoveEnable() {
        Boolean status = isElementVisible(pageElements.paginationLeftMove);
        commonLib.info("Checking left move in detailed account information pagination is enable : '" + status);
        return status;
    }

    /**
     * This method return a boolean value if right move enable on detailed account information pagination
     * @return
     */
    public Boolean isDetailAccPaginationRightMoveEnable() {
        Boolean status = isElementVisible(pageElements.paginationRightMove);
        commonLib.info("Checking right move in detailed account information pagination is enable : '" + status);
        return status;
    }

    /**
     * This method is used to clickRightArrow in pagination
     */
    public void clickingAccountInfoRightArrow() {
        commonLib.info("Clicking Right arrow");
        clickAndWaitForLoaderToBeRemoved(pageElements.paginationRightMove);
    }

    /**
     * This method is used to clickLeftArrow in pagination
     */
    public void clickingAccountInfoLeftArrow() {
        commonLib.info("Clicking Left arrow");
        clickAndWaitForLoaderToBeRemoved(pageElements.paginationLeftMove);
    }

    /**
     * This method is used to validate data range icon in detail account info top right corner
     * @return
     */
    public Boolean isDetailAccountInfoDateRangeIconVisible() {
        Boolean status = isElementVisible(pageElements.dateRangeIcon);
        commonLib.info("Checking date range icon is visible or not : '" + status);
        return status;
    }

    /**
     * This method is used to click on date range icon
     */
    public void clickingOnDateRangeIcon() {
        commonLib.info("Clicking Date Range Icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.dateRangeIcon);
    }

    /**
     * This method is used to click on date range done buttone
     */
    public void clickingOnDateRangeDoneButton() {
        commonLib.info("Clicking Date Range Done Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.dateRangeDoneButton);
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
     * This method use to get total number of add on bundles present on UI
     * */
    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.rows);
        return rows.size();
    }

    /**
     * This method will return date time value
     * @param rowNumber
     * @return
     */
    public String getDateTimeValue(int rowNumber) {
        By dateTime = By.xpath(pageElements.valueRow + rowNumber + pageElements.dateTimeValue);
        final String text = getText(dateTime);
        commonLib.info("Getting dateTime from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method will return type value
     * @param rowNumber
     * @return
     */
    public String getTypeValue(int rowNumber) {
        By type = By.xpath(pageElements.valueRow + rowNumber + pageElements.typeValue);
        final String text = getText(type);
        commonLib.info("Getting type from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method will return status value
     * @param rowNumber
     * @return
     */
    public String getStatusValue(int rowNumber) {
        By status = By.xpath(pageElements.valueRow + rowNumber + pageElements.statusValue);
        final String text = getText(status);
        commonLib.info("Getting status from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method will return reference no value
     * @param rowNumber
     * @return
     */
    public String getRefNoValue(int rowNumber) {
        By refNo = By.xpath(pageElements.valueRow + rowNumber + pageElements.refNoValue);
        final String text = getText(refNo);
        commonLib.info("Getting ref no from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method will return bill amount value
     * @param rowNumber
     * @return
     */
    public String getBillAmountValue(int rowNumber) {
        By billAmount = By.xpath(pageElements.valueRow + rowNumber + pageElements.billAmountValue);
        final String text = getText(billAmount);
        commonLib.info("Getting bill amount no from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method will return amount received value
     * @param rowNumber
     * @return
     */
    public String getAmountRecValue(int rowNumber) {
        By amountRec = By.xpath(pageElements.valueRow + rowNumber + pageElements.amountRecValue);
        final String text = getText(amountRec);
        commonLib.info("Getting amount rec from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method is used for date conversion i.e 30-apr-2021 23:59 --> 30 apr 2021 11:59 pm
     * @param input
     * @return
     */
    public static String dateFormat(String input) {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        DateFormat outputformat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
        Date date = null;
        String output = null;
        try{
            date= df.parse(input);
            output = outputformat.format(date);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return output.toLowerCase();
    }

    /**
     * This method is used to get bill amount
     * @param input_string
     * @return
     */
    public static String getAmount(String input_string) {
        String[] newStr = input_string.split("\\s+");
        String amount = null;
        for (int i = 0; i < newStr.length; i++) {
            amount = newStr[1];
        }
        return amount;
    }

}