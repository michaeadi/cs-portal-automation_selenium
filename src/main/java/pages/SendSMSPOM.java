package pages;


import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SendSMSPOM extends BasePage {

    By sendSMSTitle = By.xpath("//label[contains(text(),'Send Message')]");
    By customerNumber = By.xpath("//div[@class=\"send-managment__card-list--card--content-area--option-section--form\"][1]//div[@class=\"mat-select-value\"]");
    By openCategory = By.xpath("//app-custom-mat-select[@formcontrolname=\"categorySelect\"]//mat-select");
    By openTemplates = By.xpath("//div[@class=\"send-managment__card-list--card--content-area--option-section--form--options\"][2]//mat-select");
    By openLanguage = By.xpath("//div[@class=\"send-managment__card-list--card--content-area--option-section--form--options\"][3]//mat-select");
    By selectOption1 = By.xpath("//mat-option[1]");
    By messageContent = By.xpath("//textarea[@formcontrolname=\"messageContent\"]");
    By successMessage = By.xpath("//p[contains(text(),'Message sent successfully to')]");
    By cancelBtn = By.xpath("//div[@class='action-btn']//span[contains(text(),'CANCEL')]");
    By submitBtn = By.xpath("//div[@class='action-btn']//span[contains(text(),'SEND')]");
    By searchCategory = By.xpath("//div[@class='input-search ng-star-inserted']//input[@placeholder='Search']");
    By messageReadOnly = By.xpath("//textarea[@readonly=\"true\"]");
    By sendBtnDisabled = By.xpath("//button[@class='disabled-send-button']");
    By customerNumberText = By.xpath("//span[contains(text(),'- Primary Number')]");

    public SendSMSPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        UtilsMethods.printInfoLog("Checking is Send SMS Page Loaded");
        return checkState(sendSMSTitle);
    }

    public String getCustomerNumber() {
        String text = readText(customerNumberText);
        UtilsMethods.printInfoLog("Reading Customer Number: " + text);
        return text.split("-")[0].trim();
    }

    public void selectCategory() {
        click(openCategory);
        UtilsMethods.printInfoLog("Searching Category with name: " + readText(selectOption1));
        writeText(searchCategory, readText(selectOption1));
        UtilsMethods.printInfoLog("Selecting Category with name: " + readText(selectOption1));
        click(selectOption1);
    }

    public String selectTemplateName() {
        click(openTemplates);
        UtilsMethods.printInfoLog("Selecting Category with name: " + readText(selectOption1));
        String text = readText(selectOption1);
        click(selectOption1);
        return text;
    }

    public void selectLanguage() {
        click(openLanguage);
        UtilsMethods.printInfoLog("Selecting Category with name: " + readText(selectOption1));
        click(selectOption1);
    }

    public String getMessageContent() {
        UtilsMethods.printInfoLog("Get Message Content: " + readText(messageContent));
        return readText(messageContent).trim();
    }

    public boolean isCustomerNumber() {
        UtilsMethods.printInfoLog("IS customer number field displayed: " + checkState(customerNumber));
        return checkState(customerNumber);
    }

    public boolean isCategory() {
        UtilsMethods.printInfoLog("IS category number field displayed: " + checkState(openCategory));
        return checkState(openCategory);
    }

    public boolean isTemplateName() {
        UtilsMethods.printInfoLog("IS template name field displayed: " + checkState(openTemplates));
        return checkState(openTemplates);
    }

    public boolean isLanguage() {
        UtilsMethods.printInfoLog("IS language field displayed: " + checkState(openLanguage));
        return checkState(openLanguage);
    }

    public void waitTillSuccessMessage() {
        log.info("Waiting for SMS Send Success pop up");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(successMessage));
    }

    public boolean clickSendSMSBtn() {
        log.info("Checking Send button");
        if (driver.findElement(submitBtn).isEnabled()) {
            UtilsMethods.printInfoLog("Clicking Send button");
            click(submitBtn);
            return true;
        } else {
            return false;
        }
    }

    public boolean isSendBtnDisabled() {
        if (checkState(sendBtnDisabled)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isMessageContentEditable() {
        return checkState(messageReadOnly);
    }


}
