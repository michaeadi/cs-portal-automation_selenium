package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ServiceClassWidgetPageElements {
    public By title = By.xpath("//span[contains(text(),'Service Profile')]");
    public By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Service Profile\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]");
    public By serviceProfileNoResultFound = By.xpath("//span[contains(text(),\"Service Profile\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/img");
    public By serviceProfileNoResultFoundMessage = By.xpath("//span[contains(text(),\"Service Profile\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    public By serviceProfileError = By.xpath("//span[contains(text(),\"Service Profile\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"widget-error apiMsgBlock ng-star-inserted\"][1]");
}
