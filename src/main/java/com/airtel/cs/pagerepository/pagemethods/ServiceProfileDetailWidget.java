package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ServiceProfileDetailWidgetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ServiceProfileDetailWidget extends BasePage {
    ServiceProfileDetailWidgetPage pageElements;

    public ServiceProfileDetailWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ServiceProfileDetailWidgetPage.class);
    }

    /**
     * This Method will let us know Service Profile Widget is visible or not
     * @return
     */
    public boolean isServiceProfileWidgetVisible() {
        if (isElementVisible(pageElements.serviceProfileWidget)) {
            commonLib.info("Service Profile Widget is Visible");
            return true;
        }
        return false;
    }

    /**
     * This method use to open service profile detail page
     */
    public void openServiceProfileDetailPage() {
        commonLib.info("Opening detailed page of Service Profile");
        if (isVisible(pageElements.moreIcon)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.moreIcon);
        } else {
            commonLib.error("Unable to open service profile detail page");
        }
    }

    /**
     * This method is used to check HLR Order History tab visible or not
     * @return
     */
    public boolean isHLROrderHistoryTabVisible() {
        Boolean status = isElementVisible(pageElements.hlrOrderHistoryTab);
        commonLib.info("Is HLR Order History tab visible : " + status);
        return status;
    }

    /**
     * This method is used to HLR Order History Widget visible or not
     * @return
     */
    public boolean isHLROrderHistoryWidgetVisible() {
        Boolean status = isElementVisible(pageElements.hlrOrderHistoryWidget);
        commonLib.info("Is HLR Order History widget visible : " + status);
        return status;
    }
    /**
     * This method is used to check today filter visible or not
     * @return
     */
    public boolean isTodayFilterVisible() {
        Boolean status = isElementVisible(pageElements.todayFilter);
        commonLib.info("Is Today Filter visible : " + status);
        return status;
    }

    /**
     * This method is used to check two days filter visible or not
     * @return
     */
    public boolean isTwoDaysFilterVisible() {
        Boolean status = isElementVisible(pageElements.twoDaysFilter);
        commonLib.info("Is two days filter visible : " + status);
        return status;
    }

    /**
     * This method is used to check seven days filter  visible or not
     * @return
     */
    public boolean isSevenDaysFilterVisible() {
        Boolean status = isElementVisible(pageElements.sevenDaysFilter);
        commonLib.info("Is seven days filter visible :" + status);
        return status;
    }

    /**
     * This method is used to check Timestamp label visible or not
     * @return
     */
    public boolean isTimestampVisible() {
        Boolean status = isElementVisible(pageElements.timeStamp);
        commonLib.info("Is Timestamp visible : " + status);
        return status;
    }

    /**
     * This method is used to check Action Type label visible or not
     * @return
     */
    public boolean isActionTypeVisible() {
        Boolean status = isElementVisible(pageElements.actionType);
        commonLib.info("Is Action type visible : " + status);
        return status;
    }

    /**
     * This method is used to check remarks label visible or not
     * @return
     */
    public boolean isRemarksVisible() {
        Boolean status = isElementVisible(pageElements.remarks);
        commonLib.info("Is Remarks visible : " + status);
        return status;
    }

    /**
     * This method is used to check Current status label visible or not
     * @return
     */
    public boolean isCurrentStatusVisible() {
        Boolean status = isElementVisible(pageElements.currentStatus);
        commonLib.info("Is Current status visible : " + status);
        return status;
    }

    /**
     * This method is used to check Service Name label visible or not
     * @return
     */
    public boolean isServiceNameVisible() {
        Boolean status = isElementVisible(pageElements.serviceName);
        commonLib.info("Is Service Name visible : " + status);
        return status;
    }

    /**
     * This method is used to get Footer auuid of HLR Order History Widget
     * @return
     */
    public String getFooterAuuid() {
        String result = getText(pageElements.footerAuuid);
        commonLib.info("Getting footer auuid :" + result);
        return result;
    }

    /**
     * This method is used to get Middle auuid of HLR Order History Widget
     * @return
     */
    public String getMiddleAuuid() {
        String result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        commonLib.info("Getting footer auuid :" + result);
        return result;
    }

    /**
     * This method is use to check no result found icon visible or not
     *
     * @return true/false
     */
    public boolean isNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.noResultFound);
        commonLib.info("Is error icon visible when there is no data available in HLR Order History Widget : " + visible);
        return visible;
    }

    /**
     * This method is used to check error message visible or not
     *
     * @return true/false
     */
    public Boolean isWidgetErrorMessageVisible() {
        Boolean status=(isElementVisible(pageElements.widgetErrorMessage) || isElementVisible(pageElements.unableToFetchDataMessage));
        commonLib.info("Is error message visible when there is widget error :" + status);
        return status;
    }

    /**
     * This method is used to get no result found message
     *
     * @return String The String
     */
    public String getNoResultFoundMessage() {
        final String text = getText(pageElements.noResultFoundMessage);
        commonLib.info("Getting error message when there is no data available in HLR Order History Widget : " + text);
        return text;
    }

    /**
     * This method is used get first header value based on passed row and column
     * @param row
     * @param column
     * @return
     */
    public String getHeaderValue(int row, int column) {
        String result;
        result = getText(By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.dataValue));
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
    }

    /**
     * This method is used to get total no. of rows
     */
    public int getNoOfRows()
    {
        if(isVisibleContinueExecution(pageElements.totalRows))
        {
            List<WebElement> list= returnListOfElement(pageElements.totalRows);
            return list.size();
        }else {
            commonLib.warning("No Data is available under Service Profile Widget");
            return 0;
        }
    }

    /**
     * This method is used to check whether pagination is displayed or not
     */
    public Boolean isPaginationAvailable() {
        commonLib.info("Checking pagination available or not in HLR Order History widget");
        return isElementVisible(pageElements.servicePagination);
    }

    /**
     * This method is used to check previous button in pagination is disabled or not
     */
    public Boolean checkPreviousBtnDisable() {
        commonLib.info("Checking pagination's previous button is disabled or not ");
        return isElementVisible((pageElements.previousBtnDisable));
    }

    /**
     * This method is used to check next button in pagination is enabled or not
     */
    public Boolean checkNextBtnEnable() {
        commonLib.info("Checking pagination's next button is disabled or not");
        return isElementVisible((pageElements.nextBtnEnable));
    }

    /**
     * This method is used to get pagination text display
     * Example : 1 - 6 of 10 Results
     */
    public String getPaginationText() {
        String value = getText(pageElements.paginationCount).trim();
        commonLib.info("Reading Pagination text " + value);
        return value;
    }

    /**
     * This method is used use to click next button in pagination
     */
    public void clickNextBtn() {
        commonLib.info("Clicking Next button in pagination");
        clickWithoutLoader(pageElements.nextBtnEnable);
    }

    /**
     * This method is used to click previous button in pagination
     */
    public void clickPreviousBtn() {
        commonLib.info("Clicking Previous button in pagination");
        clickWithoutLoader(pageElements.previousBtnEnable);
    }

    /**
     * This method use to get current status's text colour
     */
    public String getCurrentStatusColour(int row, int column) {
       By status=By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.dataValue);
        return selUtils.getDataPointColor(status);
    }
}
