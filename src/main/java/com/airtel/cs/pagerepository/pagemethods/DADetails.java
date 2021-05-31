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

    public DADetails(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DADetailsPage.class);
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath(pageElements.headerRow + row + pageElements.headerValue));
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getDAId(int rowNumber) {
        By daId = By.xpath(pageElements.valueRow + rowNumber + pageElements.daID);
        final String text = getText(daId);
        commonLib.info("Getting DA ID from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDADescription(int rowNumber) {
        By daDescription = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDesc);
        final String text = getText(daDescription);
        commonLib.info("Getting DA Description from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getBundleType(int rowNumber) {
        By bundleType = By.xpath(pageElements.valueRow + rowNumber + pageElements.bundleType);
        final String text = getText(bundleType);
        commonLib.info("Getting DA Bundle Type from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDABalance(int rowNumber) {
        By daBalance = By.xpath(pageElements.valueRow + rowNumber + pageElements.daBalance);
        final String text = getText(daBalance);
        commonLib.info("Getting DA Balance from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDADateTime(int rowNumber) {
        By dateTime = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDateTime);
        final String text = getText(dateTime);
        commonLib.info("Getting DA Date and Time from Row Number " + rowNumber + " : " + text);
        return text;
    }


    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.rows);
        return rows.size();
    }

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

    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    public boolean isDAWidgetIsVisible() {
        commonLib.info("Checking is DA Widget Visible");
        return isElementVisible(pageElements.getTitle);
    }

    public String getAccumulatorHeaders(int row) {
        String header = getText(By.xpath(pageElements.accumulatorHeader + row + pageElements.headerValue));
        commonLib.info("Getting Accumulator header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToAccumulator(int row, int column) {
        String value = getText(By.xpath(pageElements.accumulatorColumnHeader + row + pageElements.accumulatorColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getAccumulatorHeaders(column) + "' = " + value);
        return value.trim();
    }

    public Boolean isOfferWidgetDisplay(){
        commonLib.info("Checking that Display Offer widget Display");
        return  isElementVisible(By.xpath(pageElements.displayOfferTitle));
    }

    public String getDisplayOfferHeader(int row) {
        String header = getText(By.xpath(pageElements.offerHeader + row + pageElements.headerValue));
        commonLib.info("Getting Display Offer header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToOffer(int row, int column) {
        String value = getText(By.xpath(pageElements.offerColumnHeader + row + pageElements.offerColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getDisplayOfferHeader(column) + "' = " + value);
        return value.trim();
    }

    public void clickMoreIconButton(int row) {
        commonLib.info("Clicking on More Icon Button");
        By dropDownIcon = By.xpath(pageElements.offerColumnHeader + row + pageElements.moreIcon);
        clickWithoutLoader(dropDownIcon);
    }

    public String getDisplayMoreWidgetHeader(int row, int moreRow) {
        String header = getText(By.xpath(pageElements.offerColumnHeader + row + pageElements.moreHeaderRow + moreRow + pageElements.headerValue));
        commonLib.info("Getting Display Offer header Number " + moreRow + " : " + header);
        return header;
    }

    public String getDisplayMoreWidgetValue(int row, int moreRow, int moreColumn) {
        String value = getText(By.xpath(pageElements.offerColumnHeader + row + pageElements.moreColumnHeader + moreRow + pageElements.offerColumnValue + moreColumn + pageElements.headerValue));
        commonLib.info("Reading '" + getDisplayMoreWidgetHeader(row, moreRow) + "' = " + value);
        return value.trim();
    }

    public Boolean isFriendsFamilyDisplay(){
        commonLib.info("Checking that Friend & Family widget Display");
        return  isElementVisible(By.xpath(pageElements.fnfTitle));
    }

    public String getFriendsFamilyHeaders(int row) {
        String header = getText(By.xpath(pageElements.fnfHeader + row + pageElements.headerValue));
        commonLib.info("Getting Accumulator header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToFriendsFamily(int row, int column) {
        String value = getText(By.xpath(pageElements.fnfColumnHeader + row + pageElements.fnfColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getAccumulatorHeaders(column) + "' = " + value);
        return value.trim();
    }

    public Boolean isFnFWidgetErrorDisplay(){
        commonLib.info("Checking friend & family widget error display");
        return isElementVisible(By.xpath(pageElements.fnfTitle+pageElements.unableToFetch));
    }

    public Boolean isFnFNoResultIconDisplay(){
        commonLib.info("Checking friend & family no result icon display amd message: '"+getText(By.xpath(pageElements.fnfTitle+pageElements.noResultFoundMessage)));
        return isElementVisible(By.xpath(pageElements.fnfTitle+pageElements.noResultFoundIcon));
    }

    public void hoverOnTotalDAIds(int row,int column){
        commonLib.info("Hovering on Number of DA Ids for row number "+row);
        hoverAndClick(By.xpath(pageElements.offerColumnHeader+row+pageElements.offerColumnValue+column+pageElements.headerValue));
    }

    public Boolean isAssociateDAWidgetDisplay(){
        commonLib.info("Checking Associate DA Widget Display");
        return  isElementVisible(By.xpath(pageElements.associatedWidgetTitle));
    }

    public String getAssociateHeaderValue(int row){
        String header = readTextOnRows(By.xpath(pageElements.associatedWidgetTitle+pageElements.associateWidgetHeader), row);
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToAssociateWidget(int row,int column){
        String value = readOnRowColumn(pageElements.associateWidgetRowValue,pageElements.associateWidgetColumnValue, row,column);
        commonLib.info("Reading '" + getDisplayOfferHeader(column) + "' = " + value);
        return value;
    }

    public int getNumberOfAssociateHeader(){
        return getSizeOfElement(By.xpath(pageElements.associatedWidgetTitle+pageElements.associateWidgetHeader));
    }

    public Boolean isPaginationAvailable(){
        commonLib.info("Checking Pagination available or not in offer widget");
        return isElementVisible(pageElements.offerPagination);
    }

    public Boolean checkPreviousBtnDisable(){
        commonLib.info("Checking in Pagination previous button is disable or not in offer widget");
        return isElementVisible(By.xpath(pageElements.displayOfferTitle+pageElements.previousBtnDisable));
    }

    public Boolean checkNextBtnEnable(){
        commonLib.info("Checking in Pagination next button is disable or not in offer widget");
        return isElementVisible(By.xpath(pageElements.displayOfferTitle+pageElements.nextBtnEnable));
    }

    public void clickNextBtn(){
        commonLib.info("Clicking Next button in pagination");
        clickWithoutLoader(By.xpath(pageElements.displayOfferTitle+pageElements.nextBtnEnable));
    }

    public void clickPreviousBtn(){
        commonLib.info("Clicking Previous button in pagination");
        clickWithoutLoader(By.xpath(pageElements.displayOfferTitle+pageElements.previousBtnEnable));
    }

    public String getPaginationText(){
        String value=getText(By.xpath(pageElements.displayOfferTitle+pageElements.paginationCount)).trim();
        commonLib.info("Reading Pagination text "+value);
        return value;
    }




}
