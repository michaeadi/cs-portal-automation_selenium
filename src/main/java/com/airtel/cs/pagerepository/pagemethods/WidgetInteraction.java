package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.WidgetInteractionPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class WidgetInteraction extends BasePage {

    WidgetInteractionPage pageElements;

    public WidgetInteraction(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, WidgetInteractionPage.class);
    }

    public String getTabTitle() {
        final String text = getText(pageElements.heading);
        commonLib.info("Reading Interaction Tab title: " + text);
        return text;
    }

    public boolean checkNoInteractionTag() {
        final boolean visible = isElementVisible(pageElements.noInteractionTag);
        commonLib.info("Is interaction tagged to widget :" + visible);
        return visible;
    }

    public void writeKeyword(String text) {
        commonLib.info("Search Issue with keyword: " + text);
        enterText(pageElements.searchBox, text);
    }

    public CustomerProfile closeInteractionTab() {
        commonLib.info("Close interaction tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTab);
        return new CustomerProfile(driver);
    }

    public List<String> getListOfIssue() {
        List<WebElement> list = returnListOfElement(pageElements.listOfIssue);
        List<String> issueList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By issueLabel = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"][" + i + "]//label");
            final String text = getText(issueLabel);
            log.info("Reading Issue label: " + text);
            issueList.add(text);
        }
        return issueList;
    }

    public void clickIssueLabel(String text) {
        By issueLabel = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"]//label[contains(text(),'" + text + "')]");
        log.info("Clicking Issue Label");
        clickAndWaitForLoaderToBeRemoved(issueLabel);
    }

    public void writeComment(String text) {
        commonLib.info("Adding comment : " + text);
        enterText(pageElements.commentBox, text);
    }

    public CustomerProfile clickSubmitBtn() {
        commonLib.info("Clicking submit button");
        clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
        commonLib.pass("Issue Created");
        return new CustomerProfile(driver);
    }

    public void interactionTabClosed() {
        log.info("Waiting for interaction tab to be closed");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.heading));
    }
}
