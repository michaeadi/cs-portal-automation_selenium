package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class InteractionsPOM extends BasePage {

    By code = By.xpath("//span[contains(@class,'mat-select-placeholder ng-tns-c9-')]");
    By issues = By.xpath("//span[starts-with(@class,'ng-tns-c9-')]");
    By search = By.xpath("//input[@placeholder='Search' and @class='search-box mat-input-element mat-form-field-autofill-control cdk-text-field-autofill-monitored ng-star-inserted']");
    By interactionComment = By.xpath("//textarea[@id='interactionComment']");
    By saveButton = By.xpath("//button[@class='btn btn-save ng-star-inserted']");
    By resolvedFTR = By.xpath("//span[@class='ticket-id-color']");
    By closeInteractions = By.xpath("//mat-icon[@class=\"tab-close mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted\"]");
    By addInteractions = By.xpath("//a[@class='issue-add']");
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    By issueDetailHeading = By.xpath("//h3[text()=\"Issue Detail\"]");
    By continueButton = By.xpath("//button[@class=\"yes-btn mat-button\"]");
    By issueDetails = By.xpath("//input[@aria-haspopup=\"true\"]//following-sibling::span/label//mat-label");
    By ticketCommentIcon=By.className("comment-text");
    By commentBox=By.xpath("//textarea[@placeholder='Add Comment...']");
    By addCommentBtn=By.xpath("//div[@class='footer']/button");
    By addedComment=By.xpath("//div[@class='comment-detail ng-star-inserted']");
    By closeCommentTab=By.xpath("//div[@class='header-close']");

    public InteractionsPOM(WebDriver driver) {
        super(driver);
    }

    public void clickOnContinueButton() {
        log.info("clicking on continue button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "clicking on continue button");
        click(continueButton);
    }

    public String isDateFieldAvailable() {
        log.info("Checking is Date Field is available at expected place ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Date Field is available at expected place ");
        return readText(issueDetails);
    }

    public void setDateFieldAvailable(String Date) {
        log.info("Writing Date to Date Field : " + Date);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Writing Date to Date Field : " + Date);
        By issueDetails = By.xpath("//input[@aria-haspopup=\"true\"]");
        writeText(issueDetails, Date);
    }

    public String getIssueDetailLabel(String Num) {
        log.info("Getting the label for issue detail field situated at Position : " + Num);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting the label for issue detail field situated at Position : " + Num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + Num + "']//following-sibling::span/label");
        return readText(issueDetails);
    }

    public void setIssueDetailInput(String Num, String Input) {
        log.info("Writing " + Input + " in label for issue detail field situated at Position : " + Num);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Writing " + Input + " in label for issue detail field situated at Position : " + Num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + Num + "']");
        writeText(issueDetails, Input);
    }

    public void clickOnCode() {
        waitTillLoaderGetsRemoved();
        List<WebElement> listOfElements = driver.findElements(By.xpath("//div[@class=\"mat-select-value\"]"));
        listOfElements.get(0).click();
        log.info("clicking on issue code field");
        ExtentTestManager.getTest().log(LogStatus.INFO, "clicking on issue code field");
    }

    public boolean isSearchVisible() {
        log.info("Checking is search Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is search Visible");
        return isElementVisible(search);
    }

    public void searchCode(String code) {
        log.info("searching issue code " + code);
        ExtentTestManager.getTest().log(LogStatus.INFO, "searching issue code " + code);
        writeText(search, code);
    }

    public void selectCode(String code) throws InterruptedException {
        selectByText(code);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        log.info("selecting issue code " + code);
        ExtentTestManager.getTest().log(LogStatus.INFO, "selecting issue code " + code);
    }

    public String getIssue() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
//        System.out.println(listOfElements1.size());
        log.info("Getting issue ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting issue ");
        return listOfElements1.get(1).getText();

    }

    public String getIssueType() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
        log.info("Getting issue type ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Issue Type ");
        return listOfElements1.get(2).getText();

    }

    public String getIssueSubType() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
        log.info("Getting issue sub type ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Issue Sub Type ");
        return listOfElements1.get(3).getText();
    }

    public String getIssueSubSubType() {
        List<WebElement> listOfElements1 = driver.findElements(issues);
        log.info("Getting issue sub sub type ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting issue sub sub type ");
        return listOfElements1.get(4).getText();
    }

    public void sendComment(String Comment) {
        writeText(interactionComment, Comment);
        log.info("Adding comment -" + Comment);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Adding comment -" + Comment);

    }

    public void clickOnSave() {
        click(saveButton);
        log.info("Clicking on save to create Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on save to create Ticket");

    }

    public boolean isSaveEnable() {
        log.info("Checking is Save button Enabled");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Save button Enabled");
        return checkState(saveButton);
    }

    public boolean isResolvedFTRDisplayed() {
            waitVisibility(resolvedFTR);
            log.info("Checking is Ticket Number or Ticket Status is Displayed : " + isElementVisible(resolvedFTR));
            ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Ticket Number or Ticket Status is Displayed : " + isElementVisible(resolvedFTR));
            return isElementVisible(resolvedFTR);
    }

    public String getResolvedFTRDisplayed() {
        waitVisibility(resolvedFTR);
        log.info("Getting the Ticket Number or Ticket Status  Displayed : " + readText(resolvedFTR));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting the Ticket Number or Ticket Status  Displayed : " + readText(resolvedFTR));
        return readText(resolvedFTR);
    }

    public customerInteractionPagePOM closeInteractions() {
        wait.until(ExpectedConditions.elementToBeClickable(closeInteractions));
        click(closeInteractions);
        log.info("Closing Interaction Screen");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Closing Interaction Screen");
        return new customerInteractionPagePOM(driver);
    }

    public void clickCommentIcon(){
        log.info("Waiting for Comment Ticket Icon");
        waitVisibility(resolvedFTR);
        log.info("Clicking Comment Icon on Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Click On Ticket Comment Icon");
        click(ticketCommentIcon);
    }

    public void openAddedComment(){
        waitVisibility(ticketCommentIcon);
        log.info("Clicking Comment Icon on Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Click On Ticket Comment Icon");
        click(ticketCommentIcon);
    }


    public void addInteractionComment(String text){
        log.info("Add Interaction Ticket Comment: "+text);
        ExtentTestManager.getTest().log(LogStatus.INFO,"Adding Interaction Ticket Comment: "+text);
        writeText(commentBox,text);
    }

    public String getAddedComment(){
        log.info("Validate Added comment displayed on Ticket: "+readText(addedComment));
        ExtentTestManager.getTest().log(LogStatus.INFO,"Added Comment Validate Successfully");
        return readText(addedComment);
    }

    public void saveInteractionComment(){
        log.info("Clicking Save Comment Button");
        click(addCommentBtn);
    }

    public void closeTicketCommentBox(){
        log.info("Closing Ticket Comment Pop up");
        click(closeCommentTab);
    }

}