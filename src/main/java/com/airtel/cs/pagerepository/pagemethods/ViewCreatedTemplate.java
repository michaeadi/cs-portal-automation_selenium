package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ViewCreatedTemplatePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ViewCreatedTemplate extends BasePage {

    ViewCreatedTemplatePage pageElements;

    public ViewCreatedTemplate(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ViewCreatedTemplatePage.class);
    }

    /**
     * This method is use to check is view Created template page display
     * @return true/false
     */
    public boolean isViewCreatedTemplate() {
        commonLib.info("Checking View Created Template Load or Not");
        return isElementVisible(pageElements.template);
    }

    /**
     * This method is use to click agent channel
     */
    public void clickAgentChannel() {
        commonLib.info("Clicking on Agent Channel list");
        clickAndWaitForLoaderToBeRemoved(pageElements.agentChannel);
    }

    /**
     * This method is use to click agent roles
     */
    public void clickRoles() {
        commonLib.info("Clicking on Roles list");
        clickAndWaitForLoaderToBeRemoved(pageElements.roles);
    }

    /**
     * This method is use to click language
     */
    public void clickLanguage() {
        commonLib.info("Clicking on Language list");
        clickAndWaitForLoaderToBeRemoved(pageElements.language);
    }

    /**
     * This method use to write search keyword in search box
     * @param text The Search Keyword
     */
    public void writeSearchKeyword(String text) {
        commonLib.info("Search By Agent Name: " + text);
        enterText(pageElements.searchKeyWord, text);
    }

    /**
     * This method is use to search by name option visible or not
     * @return true/false
     */
    public boolean isSearchByNameAvailable() {
        commonLib.info("Checking Search by template name field Load or Not");
        return isElementVisible(pageElements.searchKeyWord);
    }

    /**
     * This method is use to check template is present or not based on template name
     * @param text The template name
     * @return true/false
     */
    public boolean isTemplatePresent(String text) {
        commonLib.info("Checking Template Present With Name: " + text);
        List<WebElement> list = returnListOfElement(pageElements.allActiveTemplate);
        for (int i = 1; i <= list.size(); i++) {
            By element = By.xpath(pageElements.templateRow + i + pageElements.templateName);
            if (text.equalsIgnoreCase(getText(element).trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to get template name based on index number
     * @param i The index
     * @return String The value
     */
    public String templateName(int i) {
        By element = By.xpath(pageElements.templateRow + i + pageElements.templateName);
        commonLib.info("Reading Template: " + getText(element));
        return getText(element);
    }

    /**
     * This method is used to get template category based on index number
     * @param i The index
     */
    public void templateCategory(int i) {
        By element = By.xpath(pageElements.templateRow + i + pageElements.templateCategoryName);
        commonLib.info("Reading Category: " + getText(element));
    }

    /**
     * This method is used to click on search icon
     */
    public void clickSearchIcon() {
        commonLib.info("Clicking on Search Icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchIcon);
    }

    /**
     * This method is used to select all option present in drop down list
     */
    public void selectALLOption() {
        commonLib.info("Clicking on All Select Option");
        clickAndWaitForLoaderToBeRemoved(pageElements.allOption);
    }

    /**
     * This method use to get all the options present in drop down list
     * @return List the list of options
     */
    public List<String> getAllOptions() {
        List<WebElement> listOfElements = returnListOfElement(pageElements.options);
        commonLib.info("List Size: " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (WebElement listOfElement : listOfElements) {
            try {
                commonLib.info("Reading : " + listOfElement.getText().toLowerCase().trim());
                strings.add(listOfElement.getText().toLowerCase().trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                break;
            }
        }
        return strings;
    }

    /**
     * This method use read the active status of template based on index
     * @param i The index
     */
    public void validateActiveStatus(int i) {
        By status = By.xpath(pageElements.templateRow + i + pageElements.templateActiveStatus);
        final String text = getText(status);
        commonLib.info("Read Status: " + text);
    }

    /**
     * This method use read the de-active status of template based on index
     * @param i The index
     */
    public void validateDeActiveStatus(int i) {
        By status = By.xpath(pageElements.templateRow + i +pageElements.templateDeActiveStatue);
        final String text = getText(status);
        commonLib.info("Read Status: " + text);
    }

    /**
     * This method is use to validate delete icon visible or not based on index number
     * @param i The index
     * @return true/false
     */
    public boolean isDeleteIcon(int i) {
        By icon = By.xpath(pageElements.templateRow + i + pageElements.deleteIcon);
        boolean status = isEnabled(icon);
        commonLib.info("Is delete icon: " + status);
        return status;
    }

    /**
     * This method is use to click delete icon based on index number
     * @param i The index
     */
    public void clickDeleteIcon(int i) {
        By icon = By.xpath(pageElements.templateRow + i + pageElements.deleteIcon);
        commonLib.info("clicking delete icon");
        clickAndWaitForLoaderToBeRemoved(icon);
    }

    /**
     * This method is use to check is edit icon display or not based on index number
     * @param i The index
     * @return true/false
     */
    public boolean isEditIcon(int i) {
        By icon = By.xpath(pageElements.templateRow + i + pageElements.editIcon);
        boolean status = isEnabled(icon);
        commonLib.info("Is edit icon: " + status);
        return status;
    }

    /**
     * This method is use to check is comment icon display or not based on index number
     * @param i The index
     * @return true/false
     */
    public boolean isCommentIcon(int i) {
        By icon = By.xpath(pageElements.templateRow + i + pageElements.commentIcon);
        boolean status = isEnabled(icon);
        commonLib.info("Is comment icon: " + status);
        return status;
    }

    /**
     * This method is use to get template language name based on index number
     * @param i The index
     */
    public void templateLanguage(int i) {
        By icon = By.xpath(pageElements.templateRow + i + pageElements.templateLanguage);
        final String text = getText(icon);
        commonLib.info("Template language: " + text);
    }

    /**
     * This method is use to validate check pagination available or not
     * @return true/false
     */
    public boolean checkPaginationDisplayed() {
        boolean check = isEnabled(pageElements.pagination);
        commonLib.info("Is Pagination Available: " + check);
        return check;
    }

    /**
     * This method to read deactivate pop up title
     */
    public void popUpTitleDeActive() {
        String text = getText(pageElements.deActivePopUpTitle);
        commonLib.info("Pop up title: " + text);
    }

    /**
     * This method to read deactivate pop up message
     */
    public void popUpMessage() {
        String text = getText(pageElements.popUpMessage);
        commonLib.info("Pop up message: " + text);
    }

    /**
     * This method is use to check no button available or not
     * @return true/false
     */
    public boolean isNoButtonAvailable() {
        boolean check = isEnabled(pageElements.noBtn);
        commonLib.info("Pop up 'No' button available: " + check);
        return check;
    }

    /**
     * This method is use to check yes button available or not
     * @return true/false
     */
    public boolean isYesButtonAvailable() {
        boolean check = isEnabled(pageElements.yesBtn);
        commonLib.info("Pop up 'YES' button available: " + check);
        return check;
    }

    /**
     * This method is use to click no btn
     */
    public void clickNoBtn() {
        commonLib.info("Clicking 'No' Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.noBtn);
    }

    /**
     * This method is used to click edit button
     */
    public void clickOnEditBtn() {
        commonLib.info("Clicking 'Edit' Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.editTempBtn);
    }

    /**
     * This method is used to click on frech sms button
     */
    public void clickOnSMSBtn() {
        commonLib.info("Clicking 'French' SMS content");
        clickAndWaitForLoaderToBeRemoved(pageElements.smsContentFrench);
    }

    /**
     * THis method is used to write sms content
     * @param text
     */
    public void writeSMSContent(String text) {
        commonLib.info("SMS Content: " + text);
        enterText(pageElements.smsTextArea, text);
    }

    /**
     * This method is used to validate update button is enable or not
     * @return
     */
    public Boolean isUpadateBtnEnabled() {
        selUtils.clickElementAfterScroll(pageElements.updateBtn);
        return isEnabled(pageElements.updateBtn);
    }

    /**
     * This method is used to click on cancel button
     */
    public void clickOnCancelBtn() {
        commonLib.info("Clicking on cancel button");
        clickAndWaitForLoaderToBeRemoved(pageElements.cancelBtn);
    }

}
