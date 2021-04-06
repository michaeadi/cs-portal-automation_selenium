package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class WidgetInteractionPageElements {
    public By heading = By.xpath("//label[@class='text-capitalize']");
    public By noInteractionTag = By.xpath("//h4[@class='nointrction ng-star-inserted']");
    public By searchBox = By.xpath("//input[@placeholder=\"Search Category\"]");
    public By closeTab = By.xpath("//label[@class='float-right cursor-pointer']");
    public By listOfIssue = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"]");
    public By commentBox = By.xpath("//input[@name='interactionComment']");
    public By submitBtn = By.xpath("//button[@class='submit-ineraction-btn']");
}
