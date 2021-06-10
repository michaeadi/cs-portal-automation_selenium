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
        commonLib.info("Checking Authentication tab loaded");
        return isEnabled(pageElements.authTabTitle);
    }

    public void clickCloseBtn() {
        commonLib.info("Clicking on close button");
        clickAndWaitForLoaderToBeRemoved(pageElements.authCloseBtn);
    }

    public String getAuthInstruction() {
        final String text = getText(pageElements.authInstruction);
        commonLib.info("Reading auth instruction: " + text);
        return text;
    }

    public void clickNonAuthBtn() {
        commonLib.info("Clicking on Non-Authenticate button");
        if (driver.findElement(pageElements.notAuthBtn).isEnabled())
            clickAndWaitForLoaderToBeRemoved(pageElements.notAuthBtn);
    }

    public void clickAuthBtn() {
        if (driver.findElement(pageElements.authBtn).isEnabled()) {
            commonLib.info("Clicking on Authenticate button");
            clickWithoutLoader(pageElements.authBtn);
        } else
            clickWithoutLoader(pageElements.authCloseBtn);
    }

    /*
    This Method will get the widget unlock message
     */
    public String getWidgetUnlockMessage() {
        return getText(pageElements.widgetUnlockMsg);
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
        commonLib.info("Clicking " + i + "Ques Checkbox");
        By checkBox = By.xpath("//*[@class='main-container__body--left--wrapper ng-star-inserted'][" + i + "]//mat-checkbox");
        scrollToViewElement(checkBox);
        clickWithoutLoader(checkBox);
    }

    public boolean isSIMBarPopup() {
        final boolean state = isElementVisible(pageElements.simBarTitle);
        commonLib.info("Is popup open: " + state);
        return state;
    }

    public void closeSIMBarPopup() {
        commonLib.info("Closing SIM bar/unbar popup");
        clickAndWaitForLoaderToBeRemoved(pageElements.simCloseBtn);
    }

    public boolean isIssueDetailTitleVisible() {
        boolean result = false;
        if (isVisible(pageElements.issueDetails)) {
            final boolean state = isEnabled(pageElements.issueDetails);
            commonLib.info("Is Issue Detail Configured: " + state);
            result = state;
        } else {
            commonLib.fail("Exception in method - isIssueDetailTitleVisible", true);
        }
        return result;
    }

    public void clickSelectReasonDropDown() {
        if (isVisible(pageElements.listOfIssue)) {
            clickWithoutLoader(pageElements.listOfIssue);
        } else {
            commonLib.fail("Exception in method - clickSelectReasonDropDown", true);
        }
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
        clickWithoutLoader(pageElements.code);
    }

    public String getReason() {
        final String text = getText(pageElements.code);
        commonLib.info("Choosing Reason: " + text);
        return text;
    }


    public void enterComment(String text) {
        commonLib.info("Writing comment into comment box: " + text);
        enterText(pageElements.commentBox, text);
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
        clickAndWaitForLoaderToBeRemoved(pageElements.cancelBtn);
    }

    public void clickSubmitBtn() {
        if (isClickable(pageElements.submitBtn)) {
            commonLib.info("Clicking Submit Button");
            clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
        } else {
            commonLib.fail("Exception in Method - clickSubmitBtn", true);
        }
    }

    public String getToastText() {
        String result = null;
        if (isVisible(pageElements.toastModal)) {
            result = getText(pageElements.toastModal);
            //if (!result.equalsIgnoreCase("Internet Setting has been sent on customer` s device"))
            clickWithoutLoader(pageElements.closeBtn);
        } else {
            commonLib.fail("Exception in method - getToastText", true);
            commonLib.info("Going to Close Modal through close Button");
            clickWithoutLoader(pageElements.closeBtn);
        }
        return result;
    }

    public String getErrorMessage(){
        String text=getText(pageElements.errorMessage);
        commonLib.info("Reading Error Message Display over Pop up: "+text);
        return text;
    }

}
