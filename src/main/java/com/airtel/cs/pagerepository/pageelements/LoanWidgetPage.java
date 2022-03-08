package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class LoanWidgetPage {

    /*
     * Header Labels
     * */
    public By loanServiceHeader = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//span[contains(@class,'widget_header_label')]");
    public By vendor = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[1]//span");
    public By loanAmount = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[2]//span[1]");
    public By createdOn = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[3]//span");
    public By currentOutstanding = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[4]//span");
    public By dueDate = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[5]//span");
    /*
     * Sub Header Loan Amount Currency Type
     * */
    public By currencyType = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[2]//span[2]");

    /*
     *Vendors List & Details
     * */
    public By vendorList = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table']//div[@class='card__card-header--card-body--table--data-list ng-star-inserted']");
    
    public String vendorName="]//div[@class='show-error-message ng-star-inserted'][1]//span[1]";
    public String loanAmountValue="]//div[@class='show-error-message ng-star-inserted'][2]//span";
    public String createdOnDateValue="]//div[@class='show-error-message ng-star-inserted'][3]//span[@class='date_time ng-star-inserted']";
    public String createdOnTimeValue="]//div[@class='show-error-message ng-star-inserted'][3]//span[@class='time ng-star-inserted']";
    public String outStandingAmountValue="]//div[@class='show-error-message ng-star-inserted'][4]//div[@class='widget-section']";
    public String dueDateValue="]//div[@class='show-error-message ng-star-inserted'][5]//span[@class='date_time ng-star-inserted']";
    public String dueTimeValue="]//div[@class='show-error-message ng-star-inserted'][5]//span[@class='time ng-star-inserted']";
    public String vendorNameValue="//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table']//span[contains(text(),'";
    public String message="//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table']//span[contains(text(),'";
    public By loanWidget = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//span[@class='card__card-header--label']");
    public String loanServiceValue = "//div[@data-csautomation-key='LOAN_SERVICES']//div[@class='card__card-header--card-body--table']//div[@class='card__card-header--card-body--table--data-list ng-star-inserted'][";
    public By noResultFoundMessage = By.xpath("//div[@data-csautomation-key='LOAN_SERVICES']//span[contains(text(),'No Result found')]");
}
