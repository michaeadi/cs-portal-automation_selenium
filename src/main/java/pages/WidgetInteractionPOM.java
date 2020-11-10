package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class WidgetInteractionPOM extends BasePage {

    By heading = By.xpath("//label[@class='text-capitalize']");
    By noInteractionTag = By.xpath("//h4[@class='nointrction ng-star-inserted']");
    By searchBox = By.xpath("//input[@placeholder=\"Search Category\"]");
    By closeTab = By.xpath("//label[@class='float-right cursor-pointer']");
    By listOfIssue = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"]");
    By commentBox = By.xpath("//input[@name='interactionComment']");
    By submitBtn = By.xpath("//button[@class='submit-ineraction-btn']");

    public WidgetInteractionPOM(WebDriver driver) {
        super(driver);
    }

    public String getTabTitle() {
        log.info("Reading Interaction Tab title: " + readText(heading));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Interaction Tab title: " + readText(heading));
        return readText(heading);
    }

    public boolean checkNoInteractionTag() {
        log.info("Is interaction tagged to widget :" + isElementVisible(noInteractionTag));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Is interaction tagged to widget :" + isElementVisible(noInteractionTag));
        return isElementVisible(noInteractionTag);
    }

    public void writeKeyword(String text) {
        log.info("Search Issue with keyword: " + text);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Search Issue with keyword: " + text);
        writeText(searchBox, text);
    }

    public customerInteractionPagePOM closeInteractionTab() {
        log.info("Close interaction tab");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Close interaction tab");
        click(closeTab);
        return new customerInteractionPagePOM(driver);
    }

    public List<String> getListOfIssue() {
        List<WebElement> list = returnListOfElement(listOfIssue);
        List<String> issueList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By issueLabel = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"][" + i + "]//label");
            log.info("Reading Issue label: " + readText(issueLabel));
            issueList.add(readText(issueLabel));
        }
        return issueList;
    }

    public void clickIssueLabel(String text) {
        By issueLabel = By.xpath("//div[@class=\"bottom-drawer__card-body--intraction-list ng-star-inserted\"]//label[contains(text(),'" + text + "')]");
        log.info("Clicking Issue Label");
        click(issueLabel);
    }

    public void writeComment(String text) {
        log.info("Adding Comment : " + text);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Adding comment : " + text);
        writeText(commentBox, text);
    }

    public customerInteractionPagePOM clickSubmitBtn() {
        log.info("Clicking Submit Button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking submit button");
        click(submitBtn);
        ExtentTestManager.getTest().log(LogStatus.PASS, "Issue Created");
        return new customerInteractionPagePOM(driver);
    }

    public void interactionTabClosed() {
        log.info("Waiting for interaction tab to be closed");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(heading));
    }


}
