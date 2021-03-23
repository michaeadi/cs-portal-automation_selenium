package pages;


import Utils.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationTabPOM extends BasePage {

    By authTabTitle = By.xpath("//app-authentication-block-modal//span[@class=\"main-container__header--title\"]");
    By authCloseBtn = By.xpath("//mat-icon[contains(text(),'close')]");
    By listOfQuestions = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"]");
    By authInstruction = By.xpath("//div[@class=\"main-container__body--right--instructions\"]//p");
    By NotAuthBtn = By.xpath("//div[@class=\"main-container__body--right--buttons\"]//button[1]");
    By authBtn = By.xpath("//div[@class=\"main-container__body--right--buttons\"]//button[2]");

    //SIM Bar/Unbar pop up
    By simBarTitle = By.xpath("//div[@class=\"main-container__header\"]//span");
    By simCloseBtn = By.xpath("//div[@class=\"main-container__header\"]//mat-icon");
    By issueDetails = By.xpath("//div[@formarrayname=\"issueDetails\"]//div[contains(text(),'Issue Detail:')]");
    By listOfIssue = By.xpath("//div[@formarrayname=\"issueDetails\"]//mat-select");
    By options = By.xpath("//mat-option");
    By commentBox = By.xpath("//textarea[@id='interactionComment']");
    By cancelBtn = By.xpath("//button[@class=\"no-btn\"]");
    By submitBtn = By.xpath("//button[@class=\"submit-btn\"]");
    By code = By.xpath("//mat-option[1]//span");

    public AuthenticationTabPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isAuthTabLoad() {
        UtilsMethods.printInfoLog("Checking Authentication tab load");
        return checkState(authTabTitle);
    }

    public void clickCloseBtn() {
        UtilsMethods.printInfoLog("Clicking on close button");
        click(authCloseBtn);
    }

    public String getAuthInstruction() {
        UtilsMethods.printInfoLog("Reading auth instruction: " + readText(authInstruction));
        return readText(authInstruction);
    }

    public void clickNonAuthBtn() {
        UtilsMethods.printInfoLog("Clicking on Non-Authenticate button");
        if (driver.findElement(NotAuthBtn).isEnabled())
            click(NotAuthBtn);
    }

    public void clickAuthBtn() {
        UtilsMethods.printInfoLog("Clicking on Authenticate button");
        if (driver.findElement(authBtn).isEnabled())
            click(authBtn);
    }

    public boolean isNonAuthBtnEnable() {
        UtilsMethods.printInfoLog("Checking Non-Authenticate button is enable");
        return driver.findElement(NotAuthBtn).isEnabled();
    }

    public boolean isAuthBtnEnable() {
        UtilsMethods.printInfoLog("Checking Authenticate button is enable");
        return driver.findElement(authBtn).isEnabled();
    }

    public Map<String, String> getQuestionAnswer() {
        List<WebElement> list = returnListOfElement(listOfQuestions);
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
        UtilsMethods.printInfoLog("Is SIM bar/unbar popup open: " + checkState(simBarTitle));
        return checkState(simBarTitle);
    }

    public void closeSIMBarPopup() {
        UtilsMethods.printInfoLog("Closing SIM bar/unbar popup");
        click(simCloseBtn);
    }

    public boolean isIssueDetailTitle() {
        UtilsMethods.printInfoLog("Is Issue Detail Configured: " + checkState(issueDetails));
        return checkState(issueDetails);
    }

    public void openSelectPopup() {
        click(listOfIssue);
    }

    public List<String> getReasonConfig() {
        List<WebElement> list = returnListOfElement(options);
        List<String> reason = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            String text = readText(By.xpath("//mat-option[" + i + "]//span"));
            UtilsMethods.printInfoLog("Reading Reason: " + text);
            reason.add(text.trim());
        }
        return reason;
    }

    public void chooseReason() {
        UtilsMethods.printInfoLog("Choosing Reason: " + readText(code));
        click(code);
    }

    public String getReason(){
        UtilsMethods.printInfoLog("Choosing Reason: " + readText(code));
        return readText(code);
    }


    public void writeComment(String text) {
        UtilsMethods.printInfoLog("Writing comment into comment box: " + text);
        writeText(commentBox, text);
    }

    public boolean isCancelBtnEnable() {
        UtilsMethods.printInfoLog("Checking SIM Bar/unbar cancel button is enable");
        return driver.findElement(cancelBtn).isEnabled();
    }

    public boolean isSubmitBtnEnable() {
        UtilsMethods.printInfoLog("Checking SIM Bar/unbar submit button is enable");
        return driver.findElement(submitBtn).isEnabled();
    }

    public void clickCancelBtn() {
        UtilsMethods.printInfoLog("Clicking Cancel Button");
        click(cancelBtn);
    }

    public void clickSubmitBtn() {
        UtilsMethods.printInfoLog("Clicking Submit Button");
        click(submitBtn);
    }

}
