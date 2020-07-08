package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class InteractionsPOM extends BasePage {
    By code = By.xpath("//span[contains(@class,'mat-select-placeholder ng-tns-c9-')]");
    //span[contains(@class,'mat-select-placeholder ng-tns-c9-')]
    By issues = By.xpath("//span[starts-with(@class,'ng-tns-c9-')]");
    By search = By.xpath("//input[@placeholder='Search' and @class='search-box mat-input-element mat-form-field-autofill-control cdk-text-field-autofill-monitored ng-star-inserted']");
    By interactionComment = By.xpath("//textarea[@id='interactionComment']");
    By saveButton = By.xpath("//button[@class='btn btn-save ng-star-inserted']");
    By resolvedFTR = By.xpath("//span[@class='ticket-id-color']");
    By closeInteractions = By.xpath("//mat-icon[@class='tab-close mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted']");
    By addInteractions = By.xpath("//a[@class='issue-add']");
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    List<WebElement> listOfElements = driver.findElements(code);

    public InteractionsPOM(WebDriver driver) {
        super(driver);
    }

    public void clickOnCode() {
//        wait.until(ExpectedConditions.elementToBeClickable(listOfElements.get(0)));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));

        listOfElements.get(0).click();
        log.info("clicking on issue code");
    }

    public void searchCode(String code) {
        writeText(search, code);
        log.info("searching issue code " + code);

    }

    public void selectCode(String code) throws InterruptedException {
        selectByText(code);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        log.info("selecting issue code " + code);
    }

    public String getIssue() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
//        System.out.println(listOfElements1.size());
        log.info("Getting issue ");

        return listOfElements1.get(1).getText();

    }

    public String getIssueType() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
        log.info("Getting issue type ");
        return listOfElements1.get(2).getText();

    }

    public String getIssueSubType() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
        log.info("Getting issue sub type ");
        return listOfElements1.get(3).getText();
    }

    public String getIssueSubSubType() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
        log.info("Getting issue sub sub type ");
        return listOfElements1.get(2).getText();
    }

    public void sendComment(String Comment) {
        writeText(interactionComment, Comment);
        log.info("Adding comment -" + Comment);
    }

    public void clickOnSave() {
        click(saveButton);
        log.info("Clicking on save to create Ticket");
    }

    public boolean isResolvedFTRDisplayed() {
        waitVisibility(resolvedFTR);
        return isElementVisible(resolvedFTR);
    }

    public customerInteractionPagePOM closeInteractions() {
        click(closeInteractions);
        log.info("Closing Interaction Screen");
        return new customerInteractionPagePOM(driver);
    }

}