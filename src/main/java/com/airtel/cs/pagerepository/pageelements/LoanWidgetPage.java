package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class LoanWidgetPage {

    /*
     * Header Labels
     * */
    public By vendor = By.xpath("//div[@id='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[1]//span");
    public By loanAmount = By.xpath("//div[@id='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[2]//span[1]");
    public By createdOn = By.xpath("//div[@id='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[3]//span");
    public By currentOutstanding = By.xpath("//div[@id='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[4]//span");
    public By dueDate = By.xpath("//div[@id='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[5]//span");
    /*
     * Sub Header Loan Amount Currency Type
     * */
    public By currencyType = By.xpath("//div[@id='LOAN_SERVICES']//div[@class='card__card-header--card-body--table--list-heading']//div[2]//span[2]");

    /*
     *Vendors List & Details
     * */
    public By vendorList = By.xpath("//div[@id='LOAN_SERVICES']//div[@class='card__card-header--card-body--table']//div[@class='card__card-header--card-body--table--data-list ng-star-inserted']");
}
