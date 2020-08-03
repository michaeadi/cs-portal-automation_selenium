package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import tests.BaseTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    By home = By.xpath("//div[text()=\"HOME\"]");

    //Constructor
    public BasePage(WebDriver driver) {

        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(BaseTest.config.getProperty("GeneralWaitInSeconds"))));
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(BaseTest.config.getProperty("ImplicitWaitInSeconds")), TimeUnit.SECONDS);
    }

    public void waitTillLoaderGetsRemoved() {
        //waitVisibility(loader);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    //Click Method
    void click(By elementLocation) {
        waitVisibility(elementLocation);
        highLighterMethod(elementLocation);
        driver.findElement(elementLocation).click();
        log.info("Clicking on element" + elementLocation.toString());
    }

    void scrollToViewElement(By Element) throws InterruptedException {
        WebElement element = driver.findElement(Element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

    //Write Text
    void writeText(By elementLocation, String text) {
        waitVisibility(elementLocation);
        highLighterMethod(elementLocation);
        driver.findElement(elementLocation).sendKeys(text);
        log.info("Writing " + text + "to  " + elementLocation.toString());

    }

    //Read Text
    String readText(By elementLocation) {
        waitVisibility(elementLocation);
//        highLighterMethod(elementLocation);
        return driver.findElement(elementLocation).getText();
    }

    //HighlightElement
    void highLighterMethod(By element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid black;');", driver.findElement(element));
    }

    //Check the state of element
    boolean checkState(By elementLocation) {
        try {
            waitVisibility(elementLocation);
        } finally {
            highLighterMethod(elementLocation);
            return driver.findElement(elementLocation).isEnabled();
        }
    }

    //Wait For Element
    void waitVisibility(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    void waitAndSwitchWindow(int windownumber) {
        wait.until(ExpectedConditions.numberOfWindowsToBe(windownumber));
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(windownumber - 1));
    }

    //Hover and click on Element Using ACTIONS class
    void hoverAndClick(By elementLocation) {
        Actions actions = new Actions(driver);
        waitVisibility(elementLocation);
        WebElement target = driver.findElement(elementLocation);
        actions.moveToElement(target).build().perform();
    }

    //Asserting Current URL
    void AssertCurrentURL(String URL) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, URL);
    }

    //Wait and Switch to Frame
    void waitAndSwitchFrame(String Frame) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Frame));

    }

    public customerInteractionPagePOM openingCustomerInteractionDashboard() {
        log.info("Opening Customer Interactions Dashboard");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Customer Interactions Dashboard");
        click(home);
        waitTillLoaderGetsRemoved();
        return new customerInteractionPagePOM(driver);
    }

    public String getDateFromEpoch(long Epoch, String pattern) {
        Date date = new Date(Epoch);
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public String getTimeFromEpoch(long Epoch, String pattern) {
        Date date = new Date(Epoch);
        Date nearestMinute = DateUtils.round(date, Calendar.MINUTE);
        DateFormat format1 = new SimpleDateFormat(pattern);
        return format1.format(nearestMinute);
    }

    //Switch to parent frame
    void switchToParentFrme() {
        driver.switchTo().parentFrame();
    }

    // is element  visible
    boolean isElementVisible(By Element) {
        return driver.findElement(Element).isDisplayed();
    }


    void selectByText(String text) throws InterruptedException {
        WebElement elementby= driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementby);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]")).click();
        Thread.sleep(2000);
    }

    void clickOutside() {
        Actions action = new Actions(driver);
        action.moveByOffset(0, 0).click().build().perform();
    }

    public void clearInputTag(By element){
        log.info("Clear Search Box");
        driver.findElement(element).clear();
    }

    public boolean validateFilter(By element,String text){
        List<WebElement> list=  driver.findElements(element);
        log.info("Validating Filter");
        for(WebElement x : list){
            log.info("Element Text : "+x.getText());
            if(!x.getText().equalsIgnoreCase(text)){
                return false;
            }
        }
        return true;
    }

}