package com.airtel.cs.pagerepository.pageelements;


import org.openqa.selenium.By;

public class LoginPage {
    public By enterAUUID = By.xpath("//input[@formcontrolname=\"loginTypeStatus\"]");
    public By submitButton = By.xpath(" //*[@type='submit']");
    public By enterPassword = By.name("password");
    public By visiblePassword = By.xpath(" //*[@class='visibility-icon c-pointer abs visible-status mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted']");
    public By backButton = By.xpath(" //*[@class=\"back\"]");
    public By enterAuuid = By.xpath("//input[@formcontrolname='loginTypeStatus']");

}