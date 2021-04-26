package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class WidgetInteractionPage {
    public static final By heading = By.xpath("//label[@class='text-capitalize']");
    public static final By noInteractionTag = By.xpath("//h4[@class='nointrction ng-star-inserted']");
    public static final By searchBox = By.xpath("//input[@placeholder=\"Search Category\"]");
    public static final By closeTab = By.xpath("//label[@class='float-right cursor-pointer']");
    public static final By listOfIssue = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"]");
    public static final By commentBox = By.xpath("//input[@name='interactionComment']");
    public static final By submitBtn = By.xpath("//button[@class='submit-ineraction-btn']");
}
