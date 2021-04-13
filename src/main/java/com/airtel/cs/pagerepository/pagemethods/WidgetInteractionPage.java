package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.WidgetInteractionPageElements;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class WidgetInteractionPage extends BasePage {

    WidgetInteractionPageElements pageElements;

    public WidgetInteractionPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, WidgetInteractionPageElements.class);
    }

    public String getTabTitle() {
        UtilsMethods.printInfoLog("Reading Interaction Tab title: " + getText(pageElements.heading));
        return getText(pageElements.heading);
    }

    public boolean checkNoInteractionTag() {
        UtilsMethods.printInfoLog("Is interaction tagged to widget :" + isElementVisible(pageElements.noInteractionTag));
        return isElementVisible(pageElements.noInteractionTag);
    }

    public void writeKeyword(String text) {
        UtilsMethods.printInfoLog("Search Issue with keyword: " + text);
        writeText(pageElements.searchBox, text);
    }

    public CustomerProfilePage closeInteractionTab() {
        UtilsMethods.printInfoLog("Close interaction tab");
        click(pageElements.closeTab);
        return new CustomerProfilePage(driver);
    }

    public List<String> getListOfIssue() {
        List<WebElement> list = returnListOfElement(pageElements.listOfIssue);
        List<String> issueList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By issueLabel = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"][" + i + "]//label");
            log.info("Reading Issue label: " + getText(issueLabel));
            issueList.add(getText(issueLabel));
        }
        return issueList;
    }

    public void clickIssueLabel(String text) {
        By issueLabel = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"]//label[contains(text(),'" + text + "')]");
        log.info("Clicking Issue Label");
        click(issueLabel);
    }

    public void writeComment(String text) {
        UtilsMethods.printInfoLog("Adding comment : " + text);
        writeText(pageElements.commentBox, text);
    }

    public CustomerProfilePage clickSubmitBtn() {
        UtilsMethods.printInfoLog("Clicking submit button");
        click(pageElements.submitBtn);
        ExtentTestManager.getTest().log(LogStatus.PASS, "Issue Created");
        return new CustomerProfilePage(driver);
    }

    public void interactionTabClosed() {
        log.info("Waiting for interaction tab to be closed");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.heading));
    }
}
