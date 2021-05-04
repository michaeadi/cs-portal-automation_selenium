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

    public boolean isViewCreatedTemplate() {
        log.info("Checking View Created Template Load or Not");
        return isElementVisible(pageElements.template);
    }

    public void clickAgentChannel() {
        log.info("Clicking on Agent Channel list");
        click(pageElements.agentChannel);
    }

    public void clickRoles() {
        log.info("Clicking on Roles list");
        click(pageElements.roles);
    }

    public void clickLanguage() {
        log.info("Clicking on Language list");
        click(pageElements.language);
    }

    public void writeSearchKeyword(String text) {
        commonLib.info("Search By Agent Name: " + text);
        enterText(pageElements.searchKeyWord, text);
    }

    public boolean isSearchByNameAvailable() {
        log.info("Checking Search by template name field Load or Not");
        return isElementVisible(pageElements.searchKeyWord);
    }

    public boolean isTemplatePresent(String text) {
        commonLib.info("Checking Template Present With Name: " + text);
        List<WebElement> list = returnListOfElement(pageElements.allActiveTemplate);
        for (int i = 1; i <= list.size(); i++) {
            By element = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//h6");
            log.info("Reading Template: " + getText(element));
            if (text.equalsIgnoreCase(getText(element).trim())) {
                return true;
            }
        }
        return false;
    }

    public String templateName(int i) {
        By element = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//h6");
        commonLib.info("Reading Template: " + getText(element));
        return getText(element);
    }

    public String templateCategory(int i) {
        By element = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//h5");
        commonLib.info("Reading Category: " + getText(element));
        return getText(element);
    }

    public void clickSearchIcon() {
        log.info("Clicking on Search Icon");
        click(pageElements.searchIcon);
    }

    public void selectALLOption() {
        commonLib.info("Clicking on All Select Option");
        click(pageElements.allOption);
    }

    public ArrayList<String> getAllOptions() {
        List<WebElement> listOfElements = returnListOfElement(pageElements.options);
        log.info("List Size: " + listOfElements.size());
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

    public String validateActiveStatus(int i) {
        By status = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//div//p[1]");
        final String text = getText(status);
        commonLib.info("Read Status: " + text);
        return text;
    }

    public String validateDeActiveStatus(int i) {
        By status = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//div//p[2]");
        final String text = getText(status);
        commonLib.info("Read Status: " + text);
        return text;
    }

    public boolean isTemplateDeActive(int i) {
        By isDeActive = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card deactivate-card ng-star-inserted\"][" + i + "]");
        boolean status = isElementVisible(isDeActive);
        log.info("Is template active: " + status);
        return status;
    }

    public boolean isDeleteIcon(int i) {
        By icon = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//div//img[@title=\"delete\"]");
        boolean status = checkState(icon);
        log.info("Is delete icon: " + status);
        return status;
    }

    public void clickDeleteIcon(int i) {
        By icon = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//div//img[@title=\"delete\"]");
        commonLib.info("clicking delete icon");
        click(icon);
    }

    public boolean isEditIcon(int i) {
        By icon = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//div//img[@title=\"EDIT\"]");
        boolean status = checkState(icon);
        log.info("Is edit icon: " + status);
        return status;
    }

    public boolean isCommentIcon(int i) {
        By icon = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//div//div//img");
        boolean status = checkState(icon);
        log.info("Is comment icon: " + status);
        return status;
    }

    public String templateLanguage(int i) {
        By icon = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//div[2]//span");
        final String text = getText(icon);
        commonLib.info("Template language: " + text);
        return text;
    }

    public void clickToggleButton(int i) {
        By status = By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"][" + i + "]//label//div[1]");
        log.info("Clicking toggle button");
        click(status);
    }

    public boolean checkPaginationDisplayed() {
        boolean check = checkState(pageElements.pagination);
        log.info("Is Pagination Available: " + check);
        return check;
    }

    public String popUpTitleDeActive() {
        String text = getText(pageElements.deActivePopUpTitle);
        commonLib.info("Pop up title: " + text);
        return text;
    }

    public String popUpMessage() {
        String text = getText(pageElements.popUpMessage);
        log.info("Pop up message: " + text);
        commonLib.info("Pop up message: " + text);
        return text;
    }

    public boolean isNoButtonAvailable() {
        boolean check = checkState(pageElements.noBtn);
        commonLib.info("Pop up 'No' button available: " + check);
        return check;
    }

    public boolean isYesButtonAvailable() {
        boolean check = checkState(pageElements.yesBtn);
        commonLib.info("Pop up 'YES' button available: " + check);
        return check;
    }

    public void clickNoBtn() {
        commonLib.info("Clicking 'No' Button");
        click(pageElements.noBtn);
    }


}
