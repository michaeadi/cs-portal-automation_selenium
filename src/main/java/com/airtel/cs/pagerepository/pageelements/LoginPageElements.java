package com.airtel.cs.pagerepository.pageelements;


import org.openqa.selenium.By;

public class LoginPageElements {
    public By enterAUUID = By.xpath("//input[@formcontrolname=\"loginTypeStatus\"]");
    public By submitButton = By.xpath(" //*[@type='submit']");
    public By mobAUUID = By.xpath("/html/body/app-root/app-login/div/div[2]/div/div[2]/mat-card/div/div[2]/mat-card-content/div/form/table/tbody/tr[1]/td/mat-form-field/div/div[1]/div/input");
    public By enterPassword = By.name("password");
    public By visiblePassword = By.xpath(" //*[@class='visibility-icon c-pointer abs visible-status mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted']");
    public By backButton = By.xpath(" //*[@class=\"back\"]");
    public By enteredAUUID = By.xpath("//*[@id=\"mat-input-7\"]");
    public static final By enterAuuid = By.xpath("//input[@formcontrolname='loginTypeStatus']");

}