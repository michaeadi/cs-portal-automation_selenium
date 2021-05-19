package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.TemplateManagementPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class TemplateManagement extends BasePage {


    TemplateManagementPage pageElements;

    public TemplateManagement(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, TemplateManagementPage.class);
    }

    public boolean isPageLoaded() {
        waitVisibility(pageElements.createdTemplateTab);
        final boolean state = isEnabled(pageElements.createdTemplateTab);
        commonLib.info("Checking that is Template Management Page is loaded : " + state);
        return state;
    }

    public void clickCreatedTemplateTab() {
        commonLib.info("Clicking on created template");
        clickAndWaitForLoaderToBeRemoved(pageElements.createdTemplateTab);
    }

    public void clickViewCreatedTemplateTab() {
        commonLib.info("Clicking on view created template");
        clickAndWaitForLoaderToBeRemoved(pageElements.viewCreatedTemplateTab);
    }

    public boolean isAddTemplateAvailable() {
        final boolean visible = isElementVisible(pageElements.addTemplateBtn);
        commonLib.info("Checking create SMS template page have 'Add Template' Option with radio button to select : " + visible);
        return visible;
    }

    public boolean isAddTemplateCategoryAvailable() {
        final boolean visible = isElementVisible(pageElements.addTemplateCategoryBtn);
        commonLib.info("Checking create SMS template page have 'Add Template Category' Option with radio button to select : " + visible);
        return visible;
    }

    public boolean isCategoryAvailable() {
        final boolean visible = isElementVisible(pageElements.categoryLabel);
        commonLib.info("Checking Category label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    public boolean isMessageChannelAvailable() {
        final boolean visible = isElementVisible(pageElements.messageChannelLabel);
        commonLib.info("Checking 'Message Channel' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    public boolean isTemplateNameAvailable() {
        final boolean visible = isElementVisible(pageElements.templateNameLabel);
        commonLib.info("Checking 'Template name' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    public boolean isRoleAvailable() {
        final boolean visible = isElementVisible(pageElements.roleLabel);
        commonLib.info("Checking 'Role' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    public boolean isAgentChannelAvailable() {
        final boolean visible = isElementVisible(pageElements.agentChannelLabel);
        commonLib.info("Checking 'Agent Channel' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    public boolean isSMSLanguageAvailable() {
        final boolean visible = isElementVisible(pageElements.smsLanguageLabel);
        commonLib.info("Checking 'SMS Language' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    public void switchTabToAddTemplateCategory() {
        log.info("Switch Tab to Add template category Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.addTemplateCategoryBtn);
    }

    public void switchTabToAddTemplate() {
        log.info("Switch Tab to Add template category Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.addTemplateBtn);
    }

    public void writeTemplateCategoryName(String text) {
        commonLib.info("Creating Template Category with name: " + text);
        enterText(pageElements.templateCategoryName, text);
    }

    public void writeTemplateName(String text) {
        commonLib.info("Creating Template  with name: " + text);
        enterText(pageElements.templateNameBox, text);
    }

    public void clickAddCategoryBtn() {
        commonLib.info("clicking add template category button");
        clickAndWaitForLoaderToBeRemoved(pageElements.addCategoryBtn);
    }

    public boolean validateAddedCategoryDisplay(String text) {
        List<WebElement> list = returnListOfElement(pageElements.allCategoryList);
        for (int i = 1; i <= list.size(); i++) {
            By categoryName = By.xpath("//div[@class='sms-managment__card-list--card--content-area--content ng-star-inserted']//div[@class=\"sms-managment__card-list--card--content-area--content--sms-card ng-star-inserted\"][" + i + "]//h6");
            String name = getText(categoryName);
            log.info("Reading Category Name: " + name);
            if (name.trim().equalsIgnoreCase(text)) {
                commonLib.pass("Recent Added Template Category found with name :" + text);
                return true;
            }
        }
        commonLib.fail("Recent Added Template Category does not found with name :" + text, true);
        return false;
    }

    public void selectOptionFromList(String text) {
        commonLib.info("Selecting option with name: " + text);
        selectByText(text);
    }

    public void clickTemplateCategory() {
        log.info("Opening Template Category name list");
        commonLib.info("Opening Template Category name list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openCategoryList);
    }

    public void clickAgentRole() {
        commonLib.info("Opening Agent Role list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openRoleList);
    }

    public void clickAgentChannels() {
        commonLib.info("Opening Agent channel list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openChannelList);
    }

    public void clickSMSLanguage() {
        commonLib.info("Opening SMS language list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openLangList);
    }

    public boolean clickCreateTemplateBtn() {
        commonLib.info("Clicking create template button");
        if (driver.findElement(pageElements.createBtn).isEnabled()) {
            clickAndWaitForLoaderToBeRemoved(pageElements.createBtn);
            return true;
        } else {
            return false;
        }
    }

    public void writeSMSContent(String text) {
        commonLib.info("SMS Content: " + text);
        enterText(pageElements.smsContent, text);
    }

    public String readResponseMessage() {
        String text = (String) js.executeScript("return arguments[0].innerHTML", driver.findElement(pageElements.message));
        commonLib.info("Response: " + text);
        return text;
    }
}
