package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.TemplateManagementPageElements;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class TemplateManagementPage extends BasePage {


    TemplateManagementPageElements pageElements;

    public TemplateManagementPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, TemplateManagementPageElements.class);
    }

    public boolean isPageLoaded() {
        waitVisibility(pageElements.createdTemplateTab);
        UtilsMethods.printInfoLog("Checking that is Template Management Page is loaded : " + checkState(pageElements.createdTemplateTab));
        return checkState(pageElements.createdTemplateTab);
    }

    public void clickCreatedTemplateTab() {
        UtilsMethods.printInfoLog("Clicking on created template");
        click(pageElements.createdTemplateTab);
    }

    public void clickViewCreatedTemplateTab() {
        UtilsMethods.printInfoLog("Clicking on view created template");
        click(pageElements.viewCreatedTemplateTab);
    }

    public boolean isAddTemplateAvailable() {
        UtilsMethods.printInfoLog("Checking create SMS template page have 'Add Template' Option with radio button to select : " + isElementVisible(pageElements.addTemplateBtn));
        return isElementVisible(pageElements.addTemplateBtn);
    }

    public boolean isAddTemplateCategoryAvailable() {
        UtilsMethods.printInfoLog("Checking create SMS template page have 'Add Template Category' Option with radio button to select : " + isElementVisible(pageElements.addTemplateCategoryBtn));
        return isElementVisible(pageElements.addTemplateCategoryBtn);
    }

    public boolean isCategoryAvailable() {
        UtilsMethods.printInfoLog("Checking Category label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + isElementVisible(pageElements.categoryLabel));
        return isElementVisible(pageElements.categoryLabel);
    }

    public boolean isMessageChannelAvailable() {
        UtilsMethods.printInfoLog("Checking 'Message Channel' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + isElementVisible(pageElements.messageChannelLabel));
        return isElementVisible(pageElements.messageChannelLabel);
    }

    public boolean isTemplateNameAvailable() {
        UtilsMethods.printInfoLog("Checking 'Template name' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + isElementVisible(pageElements.templateNameLabel));
        return isElementVisible(pageElements.templateNameLabel);
    }

    public boolean isRoleAvailable() {
        UtilsMethods.printInfoLog("Checking 'Role' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + isElementVisible(pageElements.roleLabel));
        return isElementVisible(pageElements.roleLabel);
    }

    public boolean isAgentChannelAvailable() {
        UtilsMethods.printInfoLog("Checking 'Agent Channel' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + isElementVisible(pageElements.agentChannelLabel));
        return isElementVisible(pageElements.agentChannelLabel);
    }

    public boolean isSMSLanguageAvailable() {
        UtilsMethods.printInfoLog("Checking 'SMS Language' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + isElementVisible(pageElements.smsLanguageLabel));
        return isElementVisible(pageElements.smsLanguageLabel);
    }

    public void switchTabToAddTemplateCategory() {
        log.info("Switch Tab to Add template category Tab");
        click(pageElements.addTemplateCategoryBtn);
    }

    public void switchTabToAddTemplate() {
        log.info("Switch Tab to Add template category Tab");
        click(pageElements.addTemplateBtn);
    }

    public void writeTemplateCategoryName(String text) {
        UtilsMethods.printInfoLog("Creating Template Category with name: " + text);
        writeText(pageElements.templateCategoryName, text);
    }

    public void writeTemplateName(String text) {
        UtilsMethods.printInfoLog("Creating Template  with name: " + text);
        writeText(pageElements.templateNameBox, text);
    }

    public void clickAddCategoryBtn() {
        UtilsMethods.printInfoLog("clicking add template category button");
        click(pageElements.addCategoryBtn);
    }

    public boolean validateAddedCategoryDisplay(String text) {
        List<WebElement> list = returnListOfElement(pageElements.allCategoryList);
        for (int i = 1; i <= list.size(); i++) {
            By categoryName = By.xpath("//div[@class='sms-managment__card-list--card--content-area--content ng-star-inserted']//div[@class=\"sms-managment__card-list--card--content-area--content--sms-card ng-star-inserted\"][" + i + "]//h6");
            String name = getText(categoryName);
            log.info("Reading Category Name: " + name);
            if (name.trim().equalsIgnoreCase(text)) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "Recent Added Template Category found with name :" + text);
                return true;
            }
        }
        ExtentTestManager.getTest().log(LogStatus.FAIL, "Recent Added Template Category does not found with name :" + text);
        return false;
    }

    public void selectOptionFromList(String text) {
        UtilsMethods.printInfoLog("Selecting option with name: " + text);
        selectByText(text);
    }

    public void clickTemplateCategory() {
        log.info("Opening Template Category name list");
        UtilsMethods.printInfoLog("Opening Template Category name list");
        click(pageElements.openCategoryList);
    }

    public void clickAgentRole() {
        UtilsMethods.printInfoLog("Opening Agent Role list");
        click(pageElements.openRoleList);
    }

    public void clickAgentChannels() {
        UtilsMethods.printInfoLog("Opening Agent channel list");
        click(pageElements.openChannelList);
    }

    public void clickSMSLanguage() {
        UtilsMethods.printInfoLog("Opening SMS language list");
        click(pageElements.openLangList);
    }

    public boolean clickCreateTemplateBtn() {
        UtilsMethods.printInfoLog("Clicking create template button");
        if (driver.findElement(pageElements.createBtn).isEnabled()) {
            click(pageElements.createBtn);
            return true;
        } else {
            return false;
        }
    }

    public void writeSMSContent(String text) {
        UtilsMethods.printInfoLog("SMS Content: " + text);
        writeText(pageElements.smsContent, text);
    }

    public String readResponseMessage() {
        String text = (String) js.executeScript("return arguments[0].innerHTML", driver.findElement(pageElements.message));
        UtilsMethods.printInfoLog("Response: " + text);
        return text;
    }
}
