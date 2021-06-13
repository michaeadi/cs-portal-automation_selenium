package com.airtel.cs.pagerepository.pageelements;

public class WidgetCommonPage {
    public String widgetHeader="//div[@data-csautomation-key='headerRow']//span[@data-csautomation-key='headerName']";
    public String widgetColumnRows="//div[@data-csautomation-key='dataRows']";
    public String widgetColumnValue="//span[@data-csautomation-key='columnValue']";
    public String menuButton="//span[@data-csautomation-key='menuButton']";
    /**
     * Pagination previous next button
     * */
    public String previousBtnDisable="//li[@class='pagination-previous disabled ng-star-inserted']";
    public String previousBtnEnable="//li[@class='pagination-previous ng-star-inserted']";
    public String nextBtnDisable="//li[@class='pagination-next disabled ng-star-inserted']";
    public String nextBtnEnable="//li[@class='pagination-next ng-star-inserted']";
    public String paginationCount="//div[@data-csautomation-key='paginationResult']";
}
