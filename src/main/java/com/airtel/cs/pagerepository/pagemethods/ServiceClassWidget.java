package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ServiceClassWidgetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ServiceClassWidget extends BasePage {

    ServiceClassWidgetPage pageElements;

    public ServiceClassWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ServiceClassWidgetPage.class);
    }

    /**
     * This method use to check whether widget display or not
     * @return true/false
     * */
    public boolean isServiceClassWidgetDisplay() {
        final boolean state = isEnabled(By.xpath(pageElements.title));
        commonLib.info("Is Service Class Widget Display: " + state);
        return state;
    }

    /**
     * This method use to get header name
     * @param column The column number
     * @return String The header name
     * */
    public String getHeaders(int column) {
        String header = getText(By.xpath(pageElements.title + pageElements.headerRow + column + pageElements.headerValue));
        commonLib.info("Getting header Name at Row- " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value from widget based on row and column
     * @param row The row number
     * @param column The column number
     * @return String The  data value
     * */
    public String getValueCorrespondingToServiceProfile(int row, int column) {
        String value = getText(By.xpath(pageElements.title + pageElements.offerColumnHeader + row + pageElements.offerColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getHeaders(column) + "' = " + value);
        return value.trim();
    }

    /**
     * This method use to check service status
     * @param row The row number
     * @return true/false
     * */
    public Boolean getServiceStatus(int row) {
        By status = By.xpath(pageElements.serviceStatus + row + pageElements.toggleBtn);
        return Boolean.valueOf(driver.findElement(status).getAttribute("aria-checked"));
    }

    /**
     * This method used to click service status
     * */
    public void clickServiceStatus(int row) {
        By status = By.xpath(pageElements.serviceStatus+ row + pageElements.toggleBtn);
        clickAndWaitForLoaderToBeRemoved(status);
    }

    /**
     * This method used to get no result found message display on widget
     * */
    public String gettingServiceProfileNoResultFoundMessage() {
        final String text = getText(By.xpath(pageElements.title + pageElements.noResultFoundMessage));
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + text);
        return text;
    }
    /**
     * This method is used to check no result found icon display or not
     * @return true/false
     * */
    public boolean isServiceProfileNoResultFoundVisible() {
        final boolean visible = isElementVisible(By.xpath(pageElements.title + pageElements.noResultFoundIcon));
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + visible);
        return visible;
    }

    /**
     * This method use to check widget error display or not
     * */
    public boolean isServiceProfileErrorVisible() {
        final boolean visible = isElementVisible(By.xpath(pageElements.title + pageElements.unableToFetch));
        commonLib.info("Validating error is visible when there is Error in com.airtel.cs.API : " + visible);
        return visible;
    }

    /**
     * This method use to put comment in comment box
     * */
    public void enterComment(String text) {
        commonLib.info("Writing comment into comment box: " + text);
        enterText(pageElements.commentBox, text);
    }

    /**
     * This method use to clicking on submit button
     * */
    public void clickSubmitBtn() {
        commonLib.info("Clicking Submit Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
    }

    /**
     * This method use to check pop up title in case of bar action
     * */
    public Boolean isBarTitleVisible() {
        return isBarTitleVisible("BARRING");
    }

    /**
     * This method use to check pop up title in case of bar action
     * @param title issue pop up title
     * @return true/false
     * */
    public Boolean isBarTitleVisible(String title) {
        final boolean state = isTextVisible(title);
        commonLib.info("Is service class widget Pop up bar Title Display: " + state);
        return state;
    }

    /**
     * This method use to check pop up title in case of unbar action
     * */
    public Boolean isUnBarTitleVisible() {
        return isUnBarTitleVisible("UNBARRING");
    }

    /**
     * This method use to check pop up title in case of unbar action
     * @param title issue pop up title
     * @return true/false
     * */
    public Boolean isUnBarTitleVisible(String title) {
        final boolean state = isTextVisible(title);
        commonLib.info("Is service class widget Pop up unbar Title Display: " + state);
        return state;
    }

    /**
     * This method use to close pop up window
     * */
    public void closePopupWindow() {
        commonLib.info("Closing Pop up window");
        clickWithoutLoader(pageElements.closeBtn);
    }

    /**
     * This method use to whether pagination display or not
     * */
    public Boolean isPaginationAvailable() {
        commonLib.info("Checking Pagination available or not in HLR widget");
        return isElementVisible(pageElements.servicePagination);
    }

    /**
     * This method use to check previous button in pagination is disable or not
     * */
    public Boolean checkPreviousBtnDisable() {
        commonLib.info("Checking in Pagination previous button is disable or not in offer widget");
        return isElementVisible(By.xpath(pageElements.title + pageElements.previousBtnDisable));
    }

    /**
     * This method use to check next button in pagination is enable or not
     * */
    public Boolean checkNextBtnEnable() {
        commonLib.info("Checking in Pagination next button is disable or not in offer widget");
        return isElementVisible(By.xpath(pageElements.title + pageElements.nextBtnEnable));
    }

    /**
     * This method use to click next button in pagination
     * */
    public void clickNextBtn() {
        commonLib.info("Clicking Next button in pagination");
        clickWithoutLoader(By.xpath(pageElements.title + pageElements.nextBtnEnable));
    }

    /**
     * This method use to click previous button in pagination
     * */
    public void clickPreviousBtn() {
        commonLib.info("Clicking Previous button in pagination");
        clickWithoutLoader(By.xpath(pageElements.title + pageElements.previousBtnEnable));
    }

    /**
     * This method use to get pagination text display
     * Example : 1 - 5 of 7 Results
     * */
    public String getPaginationText() {
        String value = getText(By.xpath(pageElements.title + pageElements.paginationCount)).trim();
        commonLib.info("Reading Pagination text " + value);
        return value;
    }
}
