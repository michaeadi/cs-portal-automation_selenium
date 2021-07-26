package com.airtel.cs.pagerepository.pageelements;


import org.openqa.selenium.By;

public class LoginPage {
    public By enterAUUID = By.xpath("//input[@formcontrolname='loginTypeStatus']");
    public By submitButton = By.xpath("//*[@type='submit']");
    public By enterPassword = By.name("password");
    public By visiblePassword = By.xpath("//*[contains(@class,'visibility-icon')]");
    public By backButton = By.xpath(" //*[@class='back']");
    public By enterAuuid = By.xpath("//input[@formcontrolname='loginTypeStatus']");

}