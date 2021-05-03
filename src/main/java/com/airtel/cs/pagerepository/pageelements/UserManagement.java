package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class UserManagement {
    public By roles = By.xpath("//*[text()='Roles']");
    public By editCSBetaUserRole = By.xpath("//*[text()=' Automation CS Beta User Role ']/ancestor::td/following-sibling::td//*[text()=' Edit ']");
    public By updateRoleBtn = By.xpath("//*[text()=' UPDATE ROLE ']");
    public By logoutUMBtn = By.xpath("//*[text()='power_settings_new']");
    public By totalUsersHeading = By.xpath("//span[text()=\"Total users\"]");
    public By interactionChannel = By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"false\"]");
    public By searchAuuid = By.xpath("//input[@placeholder=\"UserId/name\"]");
    public By searchButton = By.xpath("//img[@class=\"filter-container__filter--form--section--search-icon\"]");
    public By viewEditButton = By.xpath("//div[@class=\"agent-list-container__agent-list--list-row--value--cta--action-button--action-label\"]");
    public By updateButton = By.xpath("//button[@class=\"new-user-CTA__submit mat-button\"]");
    public By cancelButton = By.xpath("//button[@class=\"new-user-CTA__cancel mat-button\"]");
    public By channelsOptions = By.xpath("//mat-option[@role=\"option\"]/span");
    public By workflowsOptions = By.xpath("//span[@class='mat-option-text']");
    public By ticketBucketSize = By.xpath("//tr[@class=\"agent-list-container__agent-list--list-row ng-star-inserted\"]/td[7]/div");
    public By bucketSize = By.xpath("//input[@formcontrolname=\"bucketSize\"]");
    public By addUser = By.xpath("//button//span[contains(text(),'Add User')]");
    public By addUserPageTitle = By.xpath("//span[contains(text(),'ADD NEW USER')]");
    public By userManagementPage = By.xpath("//*[text()='User Management']");

}
