package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.AuthTabPageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthTabPage extends BasePage {

    AuthTabPageElements pageElements;

    public AuthTabPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AuthTabPageElements.class);
    }

    public boolean isAuthTabLoad() {
        UtilsMethods.printInfoLog("Checking Authentication tab load");
        return checkState(pageElements.authTabTitle);
    }

    public void clickCloseBtn() {
        UtilsMethods.printInfoLog("Clicking on close button");
        click(pageElements.authCloseBtn);
    }

    public String getAuthInstruction() {
        UtilsMethods.printInfoLog("Reading auth instruction: " + readText(pageElements.authInstruction));
        return readText(pageElements.authInstruction);
    }

    public void clickNonAuthBtn() {
        UtilsMethods.printInfoLog("Clicking on Non-Authenticate button");
        if (driver.findElement(pageElements.notAuthBtn).isEnabled())
            click(pageElements.notAuthBtn);
    }

    public void clickAuthBtn() {
        UtilsMethods.printInfoLog("Clicking on Authenticate button");
        if (driver.findElement(pageElements.authBtn).isEnabled())
            click(pageElements.authBtn);
    }

    public boolean isNonAuthBtnEnable() {
        UtilsMethods.printInfoLog("Checking Non-Authenticate button is enable");
        return driver.findElement(pageElements.notAuthBtn).isEnabled();
    }

    public boolean isAuthBtnEnable() {
        UtilsMethods.printInfoLog("Checking Authenticate button is enable");
        return driver.findElement(pageElements.authBtn).isEnabled();
    }

    public Map<String, String> getQuestionAnswer() {
        List<WebElement> list = returnListOfElement(pageElements.listOfQuestions);
        Map<String, String> questionList = new HashMap<>();
        for (int i = 1; i <= list.size(); i++) {
            By question = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"][" + i + "]//span[1]");
            By answer = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"][" + i + "]//span[2]");
            UtilsMethods.printInfoLog("Question: " + readText(question) + " :" + readText(answer));
            questionList.put(readText(question).replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), readText(answer).trim());
        }
        return questionList;
    }

    public void clickCheckBox(int i) throws InterruptedException {
        UtilsMethods.printInfoLog("Clicking " + i + "Q Checkbox");
        By checkBox = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"][" + i + "]//mat-checkbox");
        scrollToViewElement(checkBox);
        click(checkBox);
    }

    public boolean isSIMBarPopup() {
        UtilsMethods.printInfoLog("Is SIM bar/unbar popup open: " + checkState(pageElements.simBarTitle));
        return checkState(pageElements.simBarTitle);
    }

    public void closeSIMBarPopup() {
        UtilsMethods.printInfoLog("Closing SIM bar/unbar popup");
        click(pageElements.simCloseBtn);
    }

    public boolean isIssueDetailTitle() {
        UtilsMethods.printInfoLog("Is Issue Detail Configured: " + checkState(pageElements.issueDetails));
        return checkState(pageElements.issueDetails);
    }

    public void openSelectPopup() {
        click(pageElements.listOfIssue);
    }

    public List<String> getReasonConfig() {
        List<WebElement> list = returnListOfElement(pageElements.options);
        List<String> reason = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            String text = readText(By.xpath("//mat-option[" + i + "]//span"));
            UtilsMethods.printInfoLog("Reading Reason: " + text);
            reason.add(text.trim());
        }
        return reason;
    }

    public void chooseReason() {
        UtilsMethods.printInfoLog("Choosing Reason: " + readText(pageElements.code));
        click(pageElements.code);
    }

    public String getReason() {
        UtilsMethods.printInfoLog("Choosing Reason: " + readText(pageElements.code));
        return readText(pageElements.code);
    }


    public void writeComment(String text) {
        UtilsMethods.printInfoLog("Writing comment into comment box: " + text);
        writeText(pageElements.commentBox, text);
    }

    public boolean isCancelBtnEnable() {
        UtilsMethods.printInfoLog("Checking SIM Bar/unbar cancel button is enable");
        return driver.findElement(pageElements.cancelBtn).isEnabled();
    }

    public boolean isSubmitBtnEnable() {
        UtilsMethods.printInfoLog("Checking SIM Bar/unbar submit button is enable");
        return driver.findElement(pageElements.submitBtn).isEnabled();
    }

    public void clickCancelBtn() {
        UtilsMethods.printInfoLog("Clicking Cancel Button");
        click(pageElements.cancelBtn);
    }

    public void clickSubmitBtn() {
        UtilsMethods.printInfoLog("Clicking Submit Button");
        click(pageElements.submitBtn);
    }

}
