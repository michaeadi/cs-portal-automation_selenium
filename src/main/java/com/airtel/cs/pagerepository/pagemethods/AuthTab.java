package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.pagerepository.pageelements.AuthTabPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthTab extends BasePage {

    AuthTabPage pageElements;

    public AuthTab(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AuthTabPage.class);
    }

    public boolean isAuthTabLoad() {
        commonLib.info("Checking Authentication tab load");
        return checkState(pageElements.authTabTitle);
    }

    public void clickCloseBtn() {
        commonLib.info("Clicking on close button");
        click(pageElements.authCloseBtn);
    }

    public String getAuthInstruction() {
        final String text = getText(pageElements.authInstruction);
        commonLib.info("Reading auth instruction: " + text);
        return text;
    }

    public void clickNonAuthBtn() {
        commonLib.info("Clicking on Non-Authenticate button");
        if (driver.findElement(pageElements.notAuthBtn).isEnabled())
            click(pageElements.notAuthBtn);
    }

    public void clickAuthBtn() {
        commonLib.info("Clicking on Authenticate button");
        if (driver.findElement(pageElements.authBtn).isEnabled())
            click(pageElements.authBtn);
    }

    public boolean isNonAuthBtnEnable() {
        commonLib.info("Checking Non-Authenticate button is enable");
        return driver.findElement(pageElements.notAuthBtn).isEnabled();
    }

    public boolean isAuthBtnEnable() {
        commonLib.info("Checking Authenticate button is enable");
        return driver.findElement(pageElements.authBtn).isEnabled();
    }

    public Map<String, String> getQuestionAnswer() {
        List<WebElement> list = returnListOfElement(pageElements.listOfQuestions);
        Map<String, String> questionList = new HashMap<>();
        for (int i = 1; i <= list.size(); i++) {
            By question = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"][" + i + "]//span[1]");
            By answer = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"][" + i + "]//span[2]");
            commonLib.info("Question: " + getText(question) + " :" + getText(answer));
            questionList.put(getText(question).replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), getText(answer).trim());
        }
        return questionList;
    }

    public void clickCheckBox(int i) throws InterruptedException {
        commonLib.info("Clicking " + i + "Q Checkbox");
        By checkBox = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"][" + i + "]//mat-checkbox");
        scrollToViewElement(checkBox);
        click(checkBox);
    }

    public boolean isSIMBarPopup() {
        final boolean state = checkState(pageElements.simBarTitle);
        commonLib.info("Is SIM bar/unbar popup open: " + state);
        return state;
    }

    public void closeSIMBarPopup() {
        commonLib.info("Closing SIM bar/unbar popup");
        click(pageElements.simCloseBtn);
    }

    public boolean isIssueDetailTitle() {
        final boolean state = checkState(pageElements.issueDetails);
        commonLib.info("Is Issue Detail Configured: " + state);
        return state;
    }

    public void openSelectPopup() {
        click(pageElements.listOfIssue);
    }

    public List<String> getReasonConfig() {
        List<WebElement> list = returnListOfElement(pageElements.options);
        List<String> reason = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            String text = getText(By.xpath("//mat-option[" + i + "]//span"));
            commonLib.info("Reading Reason: " + text);
            reason.add(text.trim());
        }
        return reason;
    }

    public void chooseReason() {
        commonLib.info("Choosing Reason: " + getText(pageElements.code));
        click(pageElements.code);
    }

    public String getReason() {
        final String text = getText(pageElements.code);
        commonLib.info("Choosing Reason: " + text);
        return text;
    }


    public void enterComment(String text) {
        commonLib.info("Writing comment into comment box: " + text);
        writeText(pageElements.commentBox, text);
    }

    public boolean isCancelBtnEnable() {
        commonLib.info("Checking SIM Bar/unbar cancel button is enable");
        return driver.findElement(pageElements.cancelBtn).isEnabled();
    }

    public boolean isSubmitBtnEnable() {
        commonLib.info("Checking SIM Bar/unbar submit button is enable");
        return driver.findElement(pageElements.submitBtn).isEnabled();
    }

    public void clickCancelBtn() {
        commonLib.info("Clicking Cancel Button");
        click(pageElements.cancelBtn);
    }

    public void clickSubmitBtn() {
        commonLib.info("Clicking Submit Button");
        click(pageElements.submitBtn);
    }

}
