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

    /**
     * This method is use to get tab title
     * @return String The value
     */
    public String getTabTitle() {
        String text = getText(pageElements.heading);
        commonLib.info("Reading Interaction Tab title: " + text);
        return text;
    }

    /**
     * This method is use to check no issue tagged icon display or not
     * @return String The value
     */
    public boolean checkNoInteractionTag() {
        boolean visible = isElementVisible(pageElements.noInteractionTag);
        commonLib.info("Is interaction tagged to widget :" + visible);
        return visible;
    }

    /**
     * This method is use to write keyword into search box
     * @param text The text
     */
    public void writeKeyword(String text) {
        commonLib.info("Search Issue with keyword: " + text);
        enterText(pageElements.searchBox, text);
    }

    /**
     * This method is use to click close interaction tab icon
     */
    public void closeInteractionTab() {
        commonLib.info("Close interaction tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTab);
    }

    /**
     * This method is use to get all the issue tagged to widget and display over interaction tab
     */
    public List<String> getListOfIssue() {
        List<WebElement> list = returnListOfElement(pageElements.listOfIssue);
        List<String> issueList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By issueLabel = By.xpath(pageElements.issueLabelList + i + pageElements.issueLabel);
            final String text = getText(issueLabel);
            commonLib.info("Reading Issue label: " + text);
            issueList.add(text);
        }
        return issueList;
    }

    /**
     * This method is use to select issue based on issue label
     * @param text The issue label
     */
    public void clickIssueLabel(String text) {
        By issueLabel = By.xpath(pageElements.issueLabelList+pageElements.labelContains + text + "')]");
        commonLib.info("Clicking Issue Label");
        clickAndWaitForLoaderToBeRemoved(issueLabel);
    }

    /**
     * This method is use to write comment into comment box
     * @param text The comment
     */
    public void writeComment(String text) {
        commonLib.info("Adding comment : " + text);
        enterText(pageElements.commentBox, text);
    }

    /**
     * This method use to click submit button return the dashboard page
     * @return Object The dashboard page
     */
    public CustomerProfile clickSubmitBtn() {
        commonLib.info("Clicking submit button");
        clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
        commonLib.pass("Issue Created");
        return new CustomerProfile(driver);
    }

    /**
     * This method use to wait until interaction tab closed
     */
    public void interactionTabClosed() {
        commonLib.info("Waiting for interaction tab to be closed");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.heading));
    }
}
