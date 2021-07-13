package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.WidgetCommonPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WidgetCommonMethod extends BasePage {
    private WidgetCommonPage pageElements;

    public WidgetCommonMethod(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, WidgetCommonPage.class);
    }

    /**
     * This method use to get header name based on row number and widget identifier
     *
     * @param widgetIdentifier The Widget unique identifier
     * @param columnNumber     The column number
     * @return String The header name
     */
    public String getHeaderName(String widgetIdentifier, int columnNumber) {
        By elementLocation = By.xpath(widgetIdentifier + pageElements.widgetHeader);
        String value = readTextOnRows(elementLocation, columnNumber);
        commonLib.info("Getting header Number " + columnNumber + " : " + value);
        return value;
    }

    /**
     * This method use to get column data value based on row number and widget identifier
     *
     * @param widgetIdentifier The Widget unique identifier
     * @param rowNumber        The data row number
     * @param columnNumber     The column number
     * @return String The header name
     */
    public String getColumnValue(String widgetIdentifier, int rowNumber, int columnNumber) {
        By rowElement = By.xpath(widgetIdentifier + pageElements.widgetColumnRows);
        String value = readOnRowColumn(rowElement, By.xpath(pageElements.widgetColumnValue), rowNumber, columnNumber);
        commonLib.info("Reading Row(" + rowNumber + ") : Column(" + columnNumber + ") = " + value);
        return value;
    }

    /**
     * This method use to click widget menu button based on widget identifier
     *
     * @param widgetIdentifier The Widget unique identifier
     */
    public void clickMenuButton(String widgetIdentifier) {
        By elementLocation = By.xpath(widgetIdentifier + pageElements.menuButton);
        commonLib.info("Clicking Menu button");
        clickAndWaitForLoaderToBeRemoved(elementLocation);
    }

    /**
     * This method use to get total number of data rows based on widget identifier
     *
     * @param widgetIdentifier The Widget unique identifier
     * @return Integer The size
     */
    public Integer getNumberOfDataRows(String widgetIdentifier) {
        commonLib.info("Reading Number of elements");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.widgetColumnRows);
        return returnListOfElement(elementLocation).size();
    }

    /**
     * This method use to whether pagination display or not
     *
     * @param widgetIdentifier The widget unique identifier
     * @return true/false
     */
    public Boolean isPaginationAvailable(String widgetIdentifier) {
        commonLib.info("Checking Pagination available or not in widget");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.paginationCount);
        return isElementVisible(elementLocation);
    }

    /**
     * This method use to check previous button in pagination is disable or not
     *
     * @param widgetIdentifier The widget unique identifier
     * @return true/false
     */
    public Boolean checkPreviousBtnDisable(String widgetIdentifier) {
        commonLib.info("Checking in Pagination previous button is disable or not in widget");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.previousBtnDisable);
        return isElementVisible(elementLocation);
    }

    /**
     * This method use to check next button in pagination is enable or not
     *
     * @param widgetIdentifier The widget unique identifier
     * @return true/false
     */
    public Boolean checkNextBtnEnable(String widgetIdentifier) {
        commonLib.info("Checking in Pagination next button is disable or not in widget");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.nextBtnEnable);
        return isElementVisible(elementLocation);
    }

    /**
     * This method use to click next button in pagination
     *
     * @param widgetIdentifier The widget unique identifier
     */
    public void clickNextBtn(String widgetIdentifier) {
        commonLib.info("Clicking Next button in pagination");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.nextBtnEnable);
        clickWithoutLoader(elementLocation);
    }

    /**
     * This method use to click previous button in pagination
     *
     * @param widgetIdentifier The widget unique identifier
     */
    public void clickPreviousBtn(String widgetIdentifier) {
        commonLib.info("Clicking Previous button in pagination");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.previousBtnEnable);
        clickWithoutLoader(elementLocation);
    }

    /**
     * This method use to get pagination text display
     * Example : 1 - 5 of 7 Results
     *
     * @param widgetIdentifier The widget unique identifier
     * @return String The value
     */
    public String getPaginationText(String widgetIdentifier) {
        String value = getText(By.xpath(widgetIdentifier + pageElements.paginationCount)).trim();
        commonLib.info("Reading Pagination text " + value);
        return value;
    }

    /**
     * This method use to check previous button in pagination is disable or not
     *
     * @param widgetIdentifier The widget unique identifier
     * @return true/false
     */
    public Boolean checkNextBtnDisable(String widgetIdentifier) {
        commonLib.info("Checking in Pagination next button is disable or not in widget");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.nextBtnDisable);
        return isElementVisible(elementLocation);
    }

    /**
     * This method use to check date picker visible or not
     *
     * @param widgetIdentifier The widget unique identifier
     * @return true/false
     */
    public Boolean checkDatePickerVisible(String widgetIdentifier) {
        commonLib.info("Checking date picker visible or not in widget");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.datePicker);
        return isElementVisible(elementLocation);
    }

    /**
     * This method use to check Search Box visible or not
     *
     * @param widgetIdentifier The widget unique identifier
     * @return true/false
     */
    public Boolean checkSearchBoxVisible(String widgetIdentifier) {
        commonLib.info("Checking Search Box visible or not in widget");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.searchBox);
        return isElementVisible(elementLocation);
    }

    /**
     * This method is use to write text into search box
     *
     * @param widgetIdentifier The Widget unique identifier
     * @param inputText        The input text
     */
    public void writeTextIntoSearchBox(String widgetIdentifier, String inputText) {
        commonLib.info("Writing " + inputText + " in search box present on widget");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.searchBox);
        enterText(elementLocation, inputText);
    }

    /**
     * This method use to click search button
     *
     * @param widgetIdentifier The widget unique identifier
     */
    public void clickSearchBtn(String widgetIdentifier) {
        commonLib.info("Clicking on Search button");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.searchButton);
        clickAndWaitForLoaderToBeRemoved(elementLocation);
    }

    /**
     * This method use to check is bottom/footer auuid visible text on widget
     *
     * @param widgetIdentifier The widget unique identifier
     * @return String The login agent AUUID
     */
    public String getBottomAuuidText(String widgetIdentifier) {
        commonLib.info("Checking is bottom auuid visible on widget or not");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.bottomAuuid);
        return getText(elementLocation);
    }

    /**
     * This method use to check is middle auuid visible text on widget
     *
     * @param widgetIdentifier The widget unique identifier
     * @return String The login agent AUUID
     */
    public String getMiddleAuuidText(String widgetIdentifier) {
        String result;
        commonLib.info("Checking is bottom auuid visible on widget or not");
        By elementLocation = By.xpath(widgetIdentifier);
        result = getAttribute(elementLocation, pageElements.middleAuuidAtr, false);
        return result;
    }

    /**
     * This method is use to check no result found icon displayed or not
     *
     * @param widgetIdentifier The unique widget identifier
     * @return true/false
     */
    public Boolean isNoResultFoundIconDisplay(String widgetIdentifier) {
        commonLib.info("Checking is no result found icon displayed or not");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.noResultFoundIcon);
        return isElementVisible(elementLocation);
    }

    /**
     * This method is use to get no result found message
     *
     * @param widgetIdentifier The unique widget identifier
     * @return String The value
     */
    public String getNoResultFoundMsg(String widgetIdentifier) {
        commonLib.info("Reading the no result found message");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.noResultFoundMsg);
        return getText(elementLocation);
    }

    /**
     * This method is use to check widget error icon displayed or not
     *
     * @param widgetIdentifier The unique widget identifier
     * @return true/false
     */
    public Boolean isWidgetErrorIconDisplay(String widgetIdentifier) {
        commonLib.info("Checking widget error icon displayed or not");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.widgetErrorIcon);
        return isElementVisible(elementLocation);
    }

    /**
     * This method is use to get widget error message
     *
     * @param widgetIdentifier The unique widget identifier
     * @return String The value
     */
    public String getWidgetErrorMsg(String widgetIdentifier) {
        commonLib.info("Reading the widget error message");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.widgetErrorMsg);
        return getText(elementLocation);
    }

    /**
     * This method is use to get widget api error message when API message displayed
     *
     * @param widgetIdentifier The unique widget identifier
     * @return String The value
     */
    public String getWidgetErrorAPIMsg(String widgetIdentifier) {
        commonLib.info("Reading the widget error message");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.widgetAPIErrorMsg);
        return getText(elementLocation);
    }

    /**
     * This method is use to check widget refresh icon displayed or not
     *
     * @param widgetIdentifier The unique widget identifier
     * @return true/false
     */
    public Boolean isWidgetRefreshIconDisplayed(String widgetIdentifier) {
        commonLib.info("Checking widget refresh icon displayed or not");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.widgetErrorRefreshBtn);
        return isElementVisible(elementLocation);
    }

    /**
     * This method is use to click widget refresh icon
     *
     * @param widgetIdentifier The unique widget identifier
     */
    public void clickWidgetRefreshIcon(String widgetIdentifier) {
        commonLib.info("Checking widget refresh icon displayed or not");
        By elementLocation = By.xpath(widgetIdentifier + pageElements.widgetErrorRefreshBtn);
        clickAndWaitForLoaderToBeRemoved(elementLocation);
    }

    /**
     * This method is use to check widget visible or not based on widget identifier
     *
     * @param widgetIdentifier The unique widget identifier
     * @return true/false
     */
    public Boolean isWidgetVisible(String widgetIdentifier) {
        commonLib.info("Checking widget visible or not");
        By elementLocation = By.xpath(widgetIdentifier);
        return isElementVisible(elementLocation);
    }

    /**
     * This method is use to get widget title text
     *
     * @param widgetIdentifier The unique widget identifier
     * @return true/false
     */
    public String getWidgetTitle(String widgetIdentifier) {
        commonLib.info("Reading widget title displayed on ui");
        return getText(By.xpath(widgetIdentifier));
    }

    /**
     * This method use to check widget load with api response and no overlay loader present
     *
     * @param widgetIdentifier The unique widget identifier
     * @return true/false;
     */
    public Boolean isWidgetLoaderRemoved(String widgetIdentifier) {
        boolean result = false;
        By elementLocation = By.xpath(widgetIdentifier + pageElements.streamLineLoader);
        if (isElementVisible(elementLocation)) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocation));
            result = true;
        }
        return result;
    }

    /**
     * This method is use to check quick action displayed or not for particular row in widget based on widget Identifier
     * @param widgetIdentifier The unique widget identifier
     * @param rowNumber The Row number
     * @param actionNumber The quick action number
     *                     Example ->
     *                     Airtel Money have two action 1)Resend SMS and 2)Reverse Transaction.
     * @return true/false
     */
    public Boolean isQuickAction(String widgetIdentifier,int rowNumber,int actionNumber) {
        WebElement wb = driver.findElements(By.xpath(widgetIdentifier+pageElements.widgetColumnRows)).get(rowNumber).findElement(By.xpath(pageElements.firstQuickAction+actionNumber+"]"));
        return wb.isEnabled();
    }

    /**
     * This method is use to click quick action button for particular row in widget based on widget Identifier
     * @param widgetIdentifier The unique widget identifier
     * @param rowNumber The Row number
     * @param actionNumber The quick action number
     *                     Example ->
     *                     Airtel Money have two action 1)Resend SMS and 2)Reverse Transaction.
     */
    public void clickQuickAction(String widgetIdentifier,int rowNumber,int actionNumber) {
        commonLib.info("Clicking on Quick Action");
        WebElement wb = driver.findElements(By.xpath(widgetIdentifier+pageElements.widgetColumnRows)).get(rowNumber).findElement(By.xpath(pageElements.firstQuickAction+actionNumber+"]"));
        wb.click();
        waitTillLoaderGetsRemoved();
    }

}
