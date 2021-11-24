package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DADetailsPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class DADetails extends BasePage {

    DADetailsPage pageElements;
    List<WebElement> rows;
    private final String SCROLL_TO_WIDGET_MESSAGE="Not able scroll to the widget";

    public DADetails(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DADetailsPage.class);
    }

    /**
     * This method use to get header name of DA Widget
     * @param column The column number
     * @return String The header name
     * */
    public String getHeaders(int column) {
        String header = getText(By.xpath(pageElements.headerRow + column + pageElements.headerValue));
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get DA Id from DA detail widget
     * */
    public String getDAId(int rowNumber) {
        By daId = By.xpath(pageElements.valueRow + rowNumber + pageElements.daID);
        final String text = getText(daId);
        commonLib.info("Getting DA ID from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get DA Description from DA detail widget
     * */
    public String getDADescription(int rowNumber) {
        By daDescription = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDesc);
        final String text = getText(daDescription);
        commonLib.info("Getting DA Description from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get DA Bundle Type from DA detail widget
     * */
    public String getBundleType(int rowNumber) {
        By bundleType = By.xpath(pageElements.valueRow + rowNumber + pageElements.bundleType);
        final String text = getText(bundleType);
        commonLib.info("Getting DA Bundle Type from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get DA Balance from DA detail widget
     * */
    public String getDABalance(int rowNumber) {
        By daBalance = By.xpath(pageElements.valueRow + rowNumber + pageElements.daBalance);
        final String text = getText(daBalance);
        commonLib.info("Getting DA Balance from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get DA Date and Time from DA detail widget
     * */
    public String getDADateTime(int rowNumber) {
        By dateTime = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDateTime);
        final String text = getText(dateTime);
        commonLib.info("Getting DA Date and Time from Row Number " + rowNumber + " : " + text);
        return text;
    }


    /**
     * This method use to get total number of DA's transaction present on UI
     * */
    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.rows);
        return rows.size();
    }

    /**
     * This method use to click on ticket icon on widget
     * */
    public WidgetInteraction clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
            return new WidgetInteraction(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on DA History Widget");
        }
        return null;
    }

    /**
     * This method use to get title of widget
     * */
    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        commonLib.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    /**
     * This method use to check DA Widget display or not
     * */
    public boolean isDAWidgetIsVisible() {
        commonLib.info("Checking is DA Widget Visible");
        boolean status=isElementVisible(pageElements.getTitle);
        try {
            scrollToViewElement(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return status;
    }

    /**
     * This method use to check whether UC-UT widget display or not
     * @return true/false
     * */
    public Boolean isOfferWidgetDisplay(){
        commonLib.info("Checking that Display Offer widget Display");
        Boolean status=isElementVisible(By.xpath(pageElements.displayOfferTitle));
        try {
            scrollToViewElement(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return  status;
    }

    /**
     * This method use to get header name of UC-UT Widget
     * @param column The column number
     * @return String The header name
     * */
    public String getDisplayOfferHeader(int column) {
        String header = getText(By.xpath(pageElements.offerHeader + column + pageElements.headerValue));
        commonLib.info("Getting Display Offer header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value from UC-UT widget based on row and column
     * @param row The row number
     * @param column The column number
     * @return String The  data value
     * */
    public String getValueCorrespondingToOffer(int row, int column) {
        String value = getText(By.xpath(pageElements.offerColumnHeader + row + pageElements.offerColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getDisplayOfferHeader(column) + "' = " + value);
        return value.trim();
    }

    /**
     * This method use to click on dropdown icon and open UC-UT Table
     * */
    public void clickMoreIconButton(int row) {
        commonLib.info("Clicking on More Icon Button");
        By dropDownIcon = By.xpath(pageElements.offerColumnHeader + row + pageElements.moreIcon);
        clickWithoutLoader(dropDownIcon);
    }

    /**
     * This method use to get header name of UC-UT Widget Drop down table
     * @param row The row number
     * @param moreRow The row for which drop down table open
     * @return String The header name
     * */
    public String getDisplayMoreWidgetHeader(int row, int moreRow) {
        String header = getText(By.xpath(pageElements.offerColumnHeader + row + pageElements.moreHeaderRow + moreRow + pageElements.headerValue));
        commonLib.info("Getting Display Offer header Number " + moreRow + " : " + header);
        return header;
    }

    /**
     * This method use to get data value of UC-UT Widget Drop down table
     * @param row The row number
     * @param moreRow The row for which drop down table open
     * @param moreColumn The column number
     * @return String The header name
     * */
    public String getDisplayMoreWidgetValue(int row, int moreRow, int moreColumn) {
        String value = getText(By.xpath(pageElements.offerColumnHeader + row + pageElements.moreColumnHeader + moreRow + pageElements.offerColumnValue + moreColumn + pageElements.headerValue));
        commonLib.info("Reading '" + getDisplayMoreWidgetHeader(row, moreRow) + "' = " + value);
        return value.trim();
    }

    /**
     * This method use to check whether friend & family widget display or not
     * @return true/false
     * */
    public Boolean isFriendsFamilyDisplay(){
        commonLib.info("Checking that Friend & Family widget Display");
        Boolean status=isElementVisible(By.xpath(pageElements.fnfTitle));
        try {
            scrollToViewElement(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return  status;
    }

    /**
     * This method use to get header name of friends & family Widget
     * @param column The column number
     * @return String The header name
     * */
    public String getFriendsFamilyHeaders(int column) {
        String header = getText(By.xpath(pageElements.fnfHeader + column + "]"));
        commonLib.info("Getting Accumulator header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value from friend & Family widget based on row and column
     * @param row The row number
     * @param column The column number
     * @return String The  data value
     * */
    public String getValueCorrespondingToFriendsFamily(int row, int column) {
        String value = getText(By.xpath(pageElements.fnfColumnHeader + row + pageElements.fnfColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getFriendsFamilyHeaders(column) + "' = " + value);
        return value.trim();
    }

    /**
     * This method use to get friend & Family widget action icon displayed or not based on row
     * @param row The row number
     * @return Boolean The  data value
     * */
    public Boolean isActionIconOnFriendsFamily(int row) {
        Boolean status = isElementVisible(By.xpath(pageElements.fnfColumnHeader + row + pageElements.actionIcon));
        commonLib.info("Is action icon display '" +status);
        return status;
    }

    /**
     * This method use to get friend & Family widget action icon displayed or not based on row
     * @param row The row number
     * */
    public void clickActionIconOnFriendsFamily(int row) {
        commonLib.info("click action icon associated to msisdn '" +getValueCorrespondingToFriendsFamily(row,1));
        clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.fnfColumnHeader + row + pageElements.actionIcon));
    }

    /**
     * This method use to get friend & Family widget add member icon displayed or not
     * @return Boolean The  status
     * */
    public Boolean isFnFAddMemberIcon(){
        commonLib.info("Checking add member icon display or not");
        return isElementVisible(pageElements.addMemberIcon);
    }

    /**
     * This method use to click friend & Family widget add member icon
     * */
    public void clickAddMemberIcon(){
        commonLib.info("Clicking on add member icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.addMemberIcon);
    }


    /**
     * This method use to check friend & Family widget error display or not
     * */
    public Boolean isFnFWidgetErrorDisplay(){
        commonLib.info("Checking friend & family widget error display");
        return isElementVisible(By.xpath(pageElements.fnfTitle+pageElements.unableToFetch));
    }
    /**
     * This method is used to check friend & Family widget display no result found icon or not
     * @return true/false
     * */
    public Boolean isFnFNoResultIconDisplay(){
        Boolean status=isElementVisible(By.xpath(pageElements.fnfTitle+pageElements.noResultFoundIcon));
        commonLib.info("Checking friend & family no result icon display: '"+status);
        return status;
    }
    /**
     * This method is used to check friend & Family widget no result found message display or not
     * @return String The value of message
     * */
    public String getFnFNoResultMessage(){
        String text=getText(By.xpath(pageElements.fnfTitle+pageElements.noResultFoundMessage));
        commonLib.info("Checking friend & family no result message display: '"+text);
        return text;
    }

    /**
     * This method is used to close pop up
     * */
    public void closePopup() {
        commonLib.info("Closing popup tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.popUpCloseBtn);
    }

    /**
     * This method is use to enter add new fnf member
     * @param mobileNumber The mobile number
     */
    public void enterAddFnfNumber(String mobileNumber){
        commonLib.info("Entering Mobile number: "+mobileNumber);
        enterText(pageElements.addNumber,mobileNumber);
    }

    /**
     * This method is used to get Pop up title text
     * @return String The Value of title
     * */
    public String getPopUpTitle(){
        String text=getText(pageElements.popUpTitle);
        commonLib.info("Reading Pop Up Title: "+text);
        return text;
    }

    /**
     * This method used to hover on DA Id's to open DA associated widget
     * */
    public void hoverOnTotalDAIds(int row,int column){
        commonLib.info("Hovering on Number of DA Ids for row number "+row);
        hoverAndClick(By.xpath(pageElements.offerColumnHeader+row+pageElements.offerColumnValue+column+pageElements.headerValue));
    }

    /**
     * This method use to check whether associated widget display or not
     * @return true/false
     * */
    public Boolean isAssociateDAWidgetDisplay(){
        commonLib.info("Checking Associate DA Widget Display");
        return  isElementVisible(By.xpath(pageElements.associatedWidgetTitle));
    }

    /**
     * This method use to get header name of associated widget
     * @param column The column number
     * @return String The header name
     * */
    public String getAssociateHeaderValue(int column){
        String header = readTextOnRows(By.xpath(pageElements.associatedWidgetTitle+pageElements.associateWidgetHeader), column);
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value from associated widget based on row and column
     * @param row The row number
     * @param column The column number
     * @return String The  data value
     * */
    public String getValueCorrespondingToAssociateWidget(int row,int column){
        String value = readOnRowColumn(pageElements.associateWidgetRowValue,pageElements.associateWidgetColumnValue, row,column);
        commonLib.info("Reading '" + getDisplayOfferHeader(column) + "' = " + value);
        return value;
    }

    /**
     * This method use to get number of associated widget header column
     * */
    public int getNumberOfAssociateHeader(){
        return getSizeOfElement(By.xpath(pageElements.associatedWidgetTitle+pageElements.associateWidgetHeader));
    }

    /**
     * This method use to whether pagination display or not
     * */
    public Boolean isPaginationAvailable(){
        commonLib.info("Checking Pagination available or not in offer widget");
        return isElementVisible(pageElements.offerPagination);
    }

    /**
     * This method use to check previous button in pagination is disable or not
     * */
    public Boolean checkPreviousBtnDisable(){
        commonLib.info("Checking in Pagination previous button is disable or not in offer widget");
        return isElementVisible(By.xpath(pageElements.displayOfferTitle+pageElements.previousBtnDisable));
    }

    /**
     * This method use to check next button in pagination is enable or not
     * */
    public Boolean checkNextBtnEnable(){
        commonLib.info("Checking in Pagination next button is disable or not in offer widget");
        return isElementVisible(By.xpath(pageElements.displayOfferTitle+pageElements.nextBtnEnable));
    }

    /**
     * This method use to click next button in pagination
     * */
    public void clickNextBtn(){
        commonLib.info("Clicking Next button in pagination");
        clickWithoutLoader(By.xpath(pageElements.displayOfferTitle+pageElements.nextBtnEnable));
    }

    /**
     * This method use to click previous button in pagination
     * */
    public void clickPreviousBtn(){
        commonLib.info("Clicking Previous button in pagination");
        clickWithoutLoader(By.xpath(pageElements.displayOfferTitle+pageElements.previousBtnEnable));
    }

    /**
     * This method use to get pagination text display
     * Example : 1 - 5 of 7 Results
     * */
    public String getPaginationText(){
        String value=getText(By.xpath(pageElements.displayOfferTitle+pageElements.paginationCount)).trim();
        commonLib.info("Reading Pagination text "+value);
        return value;
    }

    /**
     * This method is used to get accumulator widget identifier
     * @return String The value
     */
    public String getAccumulatorId(){
        return pageElements.accumulatorWidgetIdentifier;
    }

    /**
     * This method is used to get DA widget identifier
     * @return String The value
     */
    public String getDAWidgetIdentifier(){
        return pageElements.daDetailWidgetIdentifier;
    }

    /**
     * This method is used to get Offers widget identifier
     * @return String The value
     */
    public String getOfferWidgetIdentifier(){
        return pageElements.displayOfferTitle;
    }

}
