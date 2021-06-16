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

    /**
     * This method is use to check template management page load or not
     * @return true/false
     */
    public boolean isPageLoaded() {
        waitVisibility(pageElements.createdTemplateTab);
        final boolean state = isEnabled(pageElements.createdTemplateTab);
        commonLib.info("Checking that is Template Management Page is loaded : " + state);
        return state;
    }

    /**
     * This method use to click create template tab
     */
    public void clickCreatedTemplateTab() {
        commonLib.info("Clicking on created template");
        clickAndWaitForLoaderToBeRemoved(pageElements.createdTemplateTab);
    }

    /**
     * This method use to click view created template tab
     */
    public void clickViewCreatedTemplateTab() {
        commonLib.info("Clicking on view created template");
        clickAndWaitForLoaderToBeRemoved(pageElements.viewCreatedTemplateTab);
    }

    /**
     * This method is use to check add template option display or not
     * @return true/false
     */
    public boolean isAddTemplateAvailable() {
        final boolean visible = isElementVisible(pageElements.addTemplateBtn);
        commonLib.info("Checking create SMS template page have 'Add Template' Option with radio button to select : " + visible);
        return visible;
    }

    /**
     * This method is use to check add template category option display or not
     * @return true/false
     */
    public boolean isAddTemplateCategoryAvailable() {
        final boolean visible = isElementVisible(pageElements.addTemplateCategoryBtn);
        commonLib.info("Checking create SMS template page have 'Add Template Category' Option with radio button to select : " + visible);
        return visible;
    }

    /**
     * This method is use to check category label display or not on the 'Add Template' tab
     * @return true/false
     */
    public boolean isCategoryAvailable() {
        final boolean visible = isElementVisible(pageElements.categoryLabel);
        commonLib.info("Checking Category label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    /**
     * This method is use to check message channel label display or not on the 'Add Template' tab
     * @return true/false
     */
    public boolean isMessageChannelAvailable() {
        final boolean visible = isElementVisible(pageElements.messageChannelLabel);
        commonLib.info("Checking 'Message Channel' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    /**
     * This method is use to check template label display or not on the 'Add Template' tab
     * @return true/false
     */
    public boolean isTemplateNameAvailable() {
        final boolean visible = isElementVisible(pageElements.templateNameLabel);
        commonLib.info("Checking 'Template name' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    /**
     * This method is use to check role label display or not on the 'Add Template' tab
     * @return true/false
     */
    public boolean isRoleAvailable() {
        final boolean visible = isElementVisible(pageElements.roleLabel);
        commonLib.info("Checking 'Role' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    /**
     * This method is use to check agent channel display or not on the 'Add Template' tab
     * @return true/false
     */
    public boolean isAgentChannelAvailable() {
        final boolean visible = isElementVisible(pageElements.agentChannelLabel);
        commonLib.info("Checking 'Agent Channel' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    /**
     * This method is use to check SMS language display or not on the 'Add Template' tab
     * @return true/false
     */
    public boolean isSMSLanguageAvailable() {
        final boolean visible = isElementVisible(pageElements.smsLanguageLabel);
        commonLib.info("Checking 'SMS Language' label displayed on the 'Add Template' tab, when the Customer channel is selected as 'SMS'." + visible);
        return visible;
    }

    /**
     * This method use to switch to add template category tab from add template tab
     */
    public void switchTabToAddTemplateCategory() {
        log.info("Switch Tab to Add template category Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.addTemplateCategoryBtn);
    }

    /**
     * This method use to switch to add 'add template' tab from template category tab
     */
    public void switchTabToAddTemplate() {
        log.info("Switch Tab to Add template category Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.addTemplateBtn);
    }

    /**
     * This method is use to write template category name
     * @param text The text
     */
    public void writeTemplateCategoryName(String text) {
        commonLib.info("Creating Template Category with name: " + text);
        enterText(pageElements.templateCategoryName, text);
    }

    /**
     * This method is use to write template name
     * @param text The text
     */
    public void writeTemplateName(String text) {
        commonLib.info("Creating Template  with name: " + text);
        enterText(pageElements.templateNameBox, text);
    }

    /**
     * This method is use to click add category button
     */
    public void clickAddCategoryBtn() {
        commonLib.info("clicking add template category button");
        clickAndWaitForLoaderToBeRemoved(pageElements.addCategoryBtn);
    }

    /**
     * This method is use to check template category display or not based on text
     * @return true/false
     */
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

    /**
     * This method to select options based on given text
     * @param text The text
     */
    public void selectOptionFromList(String text) {
        commonLib.info("Selecting option with name: " + text);
        selectByText(text);
    }

    /**
     * This method is use to click template category
     */
    public void clickTemplateCategory() {
        log.info("Opening Template Category name list");
        commonLib.info("Opening Template Category name list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openCategoryList);
    }

    /**
     * This method is use to click Agent role option and open role list
     */
    public void clickAgentRole() {
        commonLib.info("Opening Agent Role list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openRoleList);
    }

    /**
     * This method is use to click Agent channels option and open channels list
     */
    public void clickAgentChannels() {
        commonLib.info("Opening Agent channel list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openChannelList);
    }

    /**
     * This method is use to click SMS Language option and open Language list
     */
    public void clickSMSLanguage() {
        commonLib.info("Opening SMS language list");
        clickAndWaitForLoaderToBeRemoved(pageElements.openLangList);
    }

    /**
     * This method is use to click the create template button and return the status button enable or not
     * @return true/false
     */
    public boolean clickCreateTemplateBtn() {
        commonLib.info("Clicking create template button");
        if (driver.findElement(pageElements.createBtn).isEnabled()) {
            clickAndWaitForLoaderToBeRemoved(pageElements.createBtn);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is use to write sms content
     * @param text The text
     */
    public void writeSMSContent(String text) {
        commonLib.info("SMS Content: " + text);
        enterText(pageElements.smsContent, text);
    }

    /**
     * This method is use to read response message
     * @return String The String
     */
    public String readResponseMessage() {
        String text = (String) js.executeScript("return arguments[0].innerHTML", driver.findElement(pageElements.message));
        commonLib.info("Response: " + text);
        return text;
    }
}
