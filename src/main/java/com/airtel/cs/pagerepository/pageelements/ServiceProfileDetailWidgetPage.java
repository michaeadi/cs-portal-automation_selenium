package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ServiceProfileDetailWidgetPage {

    public By serviceProfileWidget =By.xpath("//div[@data-csautomation-key='SERVICE_PROFILE']");
    public By moreIcon= By.xpath("//div[@data-csautomation-key='SERVICE_PROFILE']//img[@data-csautomation-key='menuButton']");
    public By hlrOrderHistoryTab=By.xpath("//div[@role='tab']//span[contains(text(),'HLR ORDER HISTORY')]");
    public By hlrOrderHistoryWidget=By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']");
    public By footerAuuid = By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//div[contains(@class,'auuid-container')]");
    public By middleAuuid=By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']");

    public By noResultFound = By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//div[@class='no-result-found ng-star-inserted']//img");
    public By noResultFoundMessage = By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//span[contains(text(),'No Result found')]");
    public By widgetErrorMessage=By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//div[@data-csautomation-key='widgetErrorMsg']");
    public By unableToFetchDataMessage =By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//div[@data-csautomation-key='unableToFetchData']");

    /**
     * Hlr Order History Widget locators
     */
    public By calendar=By.xpath("//input[@data-csautomation-key='datePicker']");
    public By todayFilter=By.xpath("//*[contains(@class,'radio-button')]//span[contains(text(),'Today')]");
    public By twoDaysFilter=By.xpath("//*[contains(@class,'radio-button')]//span[contains(text(),'two days')]");
    public By sevenDaysFilter=By.xpath("//*[contains(@class,'radio-button')]//span[contains(text(),'seven days')]");
    public By timeStamp=By.xpath("//span[contains(text(),'Time Stamp')]");
    public By actionType=By.xpath("//span[contains(text(),'Action Type')]");
    public By currentStatus=By.xpath("//span[contains(text(),'Current Status')]");
    public By remarks=By.xpath("//span[contains(text(),'Remarks')]");
    public By serviceName=By.xpath("//span[contains(text(),'Service Name')]");

    /**
     * Header's value locators
     */
    public String dataRows="//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//div[contains(@class,'table-data-wrapper')]//div[";
    public String dataColumns="]//div[@data-csautomation-key='dataRows']//div[";
    public String dataValue="]//span[@data-csautomation-key='columnValue']";
    public By totalRows=By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//div[@class='card__content restricted ng-star-inserted']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted']");

    /**
     * Pagination locators
     */
    public By previousBtnDisable = By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//li[@class='pagination-previous disabled ng-star-inserted']");
    public By previousBtnEnable = By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//li[@class='pagination-previous ng-star-inserted']");
    public By nextBtnDisable= By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//li[@class='pagination-next disabled ng-star-inserted']");
    public By nextBtnEnable = By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//li[@class='pagination-next ng-star-inserted']");
    public By paginationCount=By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//*[@class='pagination-details']");
    public By servicePagination = By.xpath("//div[@data-csautomation-key='HLR_ORDER_HISTORY_DETAIL']//div[@class='pagination ng-star-inserted']");



}
