package com.airtel.cs.pagerepository.pageelements;

public class WidgetCommonPage {
    public String widgetHeader="//div[@data-csautomation-key='headerRow']//span[@data-csautomation-key='headerName']";
    public String rowIdentifier="//div[@class='table-data-wrapper ng-star-inserted']//div[";
    public String widgetColumnRows="//div[@data-csautomation-key='dataRows']";
    public String widgetColumnValue="//span[@data-csautomation-key='columnValue']";
    public String menuButton="//img[@data-csautomation-key='menuButton']";
    /**
     * Pagination previous next button
     * */
    public String previousBtnDisable="//li[@class='pagination-previous disabled ng-star-inserted']";
    public String previousBtnEnable="//li[@class='pagination-previous ng-star-inserted']";
    public String nextBtnDisable="//li[@class='pagination-next disabled ng-star-inserted']";
    public String nextBtnEnable="//li[@class='pagination-next ng-star-inserted']";
    public String paginationCount="//div[@data-csautomation-key='paginationResult']";

    /**
     * Other item present on widget which common to widgets
     */
    public String datePicker="//input[@data-csautomation-key='datePicker']";
    public String searchBox="//input[@data-csautomation-key='searchBox']";
    public String searchButton="//input[@data-csautomation-key='searchBtn']";
    public String bottomAuuid="//div[@data-csautomation-key='bottomAuuid']";
    public String middleAuuidAtr="data-auuid";
    public String noResultFoundMsg="//span[@data-csautomation-key='noResultFoundMsg']";
    public String noResultFoundIcon="//img[@data-csautomation-key='noResultFoundIcon']";
    public String widgetErrorIcon="//img[@data-csautomation-key='widgetErrorIcon']";
    public String widgetErrorMsg="//div[@data-csautomation-key='widgetErrorMsg']";
    public String widgetErrorRefreshBtn="//button[@data-csautomation-key='widgetErrorRefreshButton']";
    public String widgetAPIErrorMsg="//div[@data-csautomation-key='widgetErrorMsg']";
    public String streamLineLoader="//div[@data-csautomation-key='streamLineLoader']";
    public String firstQuickAction="//div[@class='quick-action ng-star-inserted']//img[";
}
