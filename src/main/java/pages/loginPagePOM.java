package pages;

import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class loginPagePOM extends BasePage {

    By enterAUUID = By.xpath("//input[@formcontrolname=\"loginTypeStatus\"]");
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
        UtilsMethods.printInfoLog("Checking is Enter AUUID field is Visible");
        return isElementVisible(enterAUUID);
    }

    public void openBaseURL(String baseURL) {
        driver.get(baseURL);
        UtilsMethods.printInfoLog("Opening URL:-" + baseURL);
    }

    public void enterAUUID(String AUUID) {
        UtilsMethods.printInfoLog("Entering AUUID :" + AUUID + "In username");
        writeText(enterAUUID, AUUID);

    }

    public void clickOnSubmitBtn() {
        UtilsMethods.printInfoLog("Clicking on Submit button");
        click(submitButton);
    }

    public String getEnteredAUUID() {
        UtilsMethods.printInfoLog("reading AUUID from Pre filled field");
        return readText(mobAUUID);
    }

    public void enterPassword(String password) {
        UtilsMethods.printInfoLog("Send password to Password field");
        writeText(enterPassword, password);
    }

    public boolean checkLoginButton() {
        UtilsMethods.printInfoLog("checking login button is enabled or not");
        return checkState(submitButton);
    }

    public void clickOnLogin() {
        UtilsMethods.printInfoLog("Clicking on Login button");
        click(submitButton);
    }

    public void clickOnVisibleButton() {
        UtilsMethods.printInfoLog("Clicking on Visible Password Button");
        click(visiblePassword);
    }

    public String getPasswordText() {
        UtilsMethods.printInfoLog("getting text from Password field ");
        return readText(enterPassword);
    }

    public void clickBackButton() {
        UtilsMethods.printInfoLog("Clicking on back button");
        click(backButton);
    }
}

