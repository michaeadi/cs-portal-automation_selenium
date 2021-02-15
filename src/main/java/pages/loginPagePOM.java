package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class loginPagePOM extends BasePage {

    By enterAUUID = By.xpath("///input[@formcontrolname=\"loginTypeStatus\"]");
    By submitButton = By.xpath(" //*[@type='submit']");
    By mobAUUID = By.xpath("/html/body/app-root/app-login/div/div[2]/div/div[2]/mat-card/div/div[2]/mat-card-content/div/form/table/tbody/tr[1]/td/mat-form-field/div/div[1]/div/input");
    //    By mobAUUID = By.xpath("//input[@autocomplete=\"new-password\" and  @formcontrolname=\"number\"]");
    By enterPassword = By.name("password");
    By visiblePassword = By.xpath(" //*[@class='visibility-icon c-pointer abs visible-status mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted']");
    By backButton = By.xpath(" //*[@class=\"back\"]");
    By enteredAUUID = By.xpath("//*[@id=\"mat-input-7\"]");

    public loginPagePOM(WebDriver driver) {
        super(driver);
    }

    public boolean isEnterAUUIDFieldVisible() {
        log.info("Checking is Enter AUUID field is Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Enter AUUID field is Visible");
        return isElementVisible(enterAUUID);
    }

    public void openBaseURL(String baseURL) {
        driver.get(baseURL);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL:-" + baseURL);
        log.info("Opening URL:-" + baseURL);
    }

    public void enterAUUID(String AUUID) {
        log.info("Entering AUUID :" + AUUID + "In username");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Entering AUUID :" + AUUID + "In username");
        writeText(enterAUUID, AUUID);

    }

    public void clickOnSubmitBtn() {
        log.info("Clicking on Submit button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Submit button");
        click(submitButton);
    }

    public String getEnteredAUUID() {
        log.info("reading AUUID from Pre filled field");
        ExtentTestManager.getTest().log(LogStatus.INFO, "reading AUUID from Pre filled field");
        return readText(mobAUUID);
    }

    public void enterPassword(String password) {
        log.info("Send password to Password field");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Send password to Password field");
        writeText(enterPassword, password);
    }

    public boolean checkLoginButton() {
        log.info("checking login button is enabled or not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "checking login button is enabled or not");
        return checkState(submitButton);
    }

    public void clickOnLogin() {
        log.info("Clicking on Login button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Login button");
        click(submitButton);
    }

    public void clickOnVisibleButton() {
        log.info("Clicking on Visible Password Button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Visible Password Button");
        click(visiblePassword);
    }

    public String getPasswordText() {
        log.info("getting text from Password field ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "getting text from Password field ");
        return readText(enterPassword);
    }

    public void clickBackButton() {
        log.info("Clicking on back button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on back button");
        click(backButton);
    }
}

