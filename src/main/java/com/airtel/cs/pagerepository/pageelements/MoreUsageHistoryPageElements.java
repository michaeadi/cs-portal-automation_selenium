package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class MoreUsageHistoryPageElements {
    public By bundleName = By.xpath("div[@class=\"ng-star-inserted\"][1]/span");
    public By transactionNumber = By.xpath("div[@class=\"ng-star-inserted\"][2]/span"); //Transaction number does not display yet
    public By dateTime = By.xpath("div[@class=\"ng-star-inserted\"][2]/span");
    public By smsTo = By.xpath("div[@class=\"ng-star-inserted\"][3]/span");
    public By usedData = By.xpath("div[@class=\"ng-star-inserted\"][3]/span");
    public By dataCharges = By.xpath("div[@class=\"ng-star-inserted\"][4]/span");
    public By callDuration = By.xpath("div[@class=\"ng-star-inserted\"][3]/span");
    public By callCharges = By.xpath("div[@class=\"ng-star-inserted\"][5]/span");
    public By callTo = By.xpath("div[@class=\"ng-star-inserted\"][4]/span");
    public By smsCharges = By.xpath("div[@class=\"ng-star-inserted\"][4]/span");
    public By smsHistoryRows = By.xpath("//span[contains(text(),\"SMS History \")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    public By callHistoryRows = By.xpath("//span[contains(text(),\"Call History \")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    public By dataHistoryRows = By.xpath("//span[contains(text(),\"Data History \")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    public By smsDatePicker = By.xpath("//span[contains(text(),\"SMS History \")]//following-sibling::form/span[@class=\"datepicker-transaction ng-star-inserted\"]");
    public By dataDatePicker = By.xpath("//span[contains(text(),\"Data History \")]//following-sibling::form/span[@class=\"datepicker-transaction ng-star-inserted\"]");
    public By callDatePicker = By.xpath("//span[contains(text(),\"Call History \")]//following-sibling::form/span[@class=\"datepicker-transaction ng-star-inserted\"]");
    public By callHistoryNoResultFound = By.xpath("//span[contains(text(),\"Call History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    public By callHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"Call History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    public By smsHistoryNoResultFound = By.xpath("//span[contains(text(),\"SMS History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    public By smsHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"SMS History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    public By dataHistoryNoResultFound = By.xpath("//span[contains(text(),\"Data History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    public By dataHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"Data History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    public By getSMSWidgetTitle = By.xpath("//span[contains(text(),\"SMS History \")]");
    public By getDataWidgetTitle = By.xpath("//span[contains(text(),\"Data History \")]");
    public By getCallWidgetTitle = By.xpath("//span[contains(text(),\"Call History \")]");
    public By ticketSMSIcon = By.xpath("//span[contains(text(),\"SMS History \")]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
    public By ticketDataIcon = By.xpath("//span[contains(text(),\"Data History \")]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
    public By ticketCallIcon = By.xpath("//span[contains(text(),\"Call History \")]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
}
