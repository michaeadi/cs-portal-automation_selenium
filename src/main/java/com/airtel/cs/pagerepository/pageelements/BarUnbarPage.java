package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class BarUnbarPage {
    public By barIcon=By.xpath("//img[contains(@src,'airtelmoneybar.svg')]");
    public By unBarIcon=By.xpath("//img[contains(@src,'airtelmoneyunbar.svg')]");
    public By barUnbarIssuePopUp=By.xpath("//div[contains(@class,'sim-suspend')]");
    public By unbarHeader=By.xpath("//span[contains(text(),'UNBAR')]");
    public By barHeader=By.xpath("//span[contains(text(),'BAR')]");
    public By demographicWidget=By.xpath("//div[contains(@id,'AM_PROFILE')]");

    /*
   Issue Detail Pop up
    */

    public By commentBoxLabel=By.xpath("//div[contains(@class, 'issue-comment--label')]");
    public By selectReasonLabel=By.xpath("//label[contains(text(),'Select Reason *')]");
    public By barTypeLabel=By.xpath("//label[contains(text(),'BAR Type*')]");
    public By submitButton =By.xpath("//button[contains(@class,'submit-btn')]");
    public By cancelButton =By.xpath("//button[contains(@class,'no-btn')]");
    public By selectReason=By.xpath("//label[contains(text(),'Select Reason *')]//following-sibling::mat-select//div[@class='mat-select-value']");
    public By selectBarTpe=By.xpath("//label[contains(text(),'BAR Type *')]//following-sibling::mat-select//div[@class='mat-select-value']");
    public By commentBox=By.xpath("//textarea[contains(@id, 'interactionComment')]");
    public By successMessage=By.xpath("//div[contains(@class,'main-container__body--message')]");
    public By crossIcon=By.xpath("//mat-icon[contains(@class,'cursor-pointer')]");


    /*
    Action Trail tab
     */
    public By homePage=By.xpath("//span[contains(text(),'HOME')]");
    public By viewHistory=By.xpath("//span[contains(text(),'VIEW HISTORY')]");
    public By actionTrail=By.xpath("//div[contains(text(),'Action Trail')]");
    public By actionTrailLatestDropdown=By.xpath("//span[contains(@class,'cursor')]");
    public By actionTrailBillNo=By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p");
    public By actionTrailEmailId=By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p[2]");
    public By actionType=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[1]");
    public By reason=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[3]");
    public By comment=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[6]");
}
