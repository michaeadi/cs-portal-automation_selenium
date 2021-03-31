package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.UtilsMethods;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.*;
import tests.frontendagent.BaseTest;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Log4j2
public class BasePage extends BaseTest {
    public static Properties config = BaseTest.config;
    public WebDriver driver;
    Wait<WebDriver> wait;
    Wait<WebDriver> wait1;
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    By loader1 = By.xpath("//div[@class=\"ngx-overlay foreground-closing\"]");
    By overlay = By.xpath("//mat-dialog-container[@role='dialog']");
    By timeLine = By.xpath("//app-new-loader[@class=\"ng-star-inserted\"]//div[1]");
    By home = By.xpath("//*[text()=\"HOME\"]");
    By toastMessage = By.xpath("//app-toast-component/p");
    JavascriptExecutor js;

    //Constructor
    public BasePage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> expectation = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").toString().equals("complete");
        wait1 = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Integer.parseInt(BaseTest.config.getProperty("GeneralWaitInSeconds"))))
                .pollingEvery(Duration.ofSeconds(Integer.parseInt(BaseTest.config.getProperty("PoolingWaitInSeconds"))))
                .ignoring(NoSuchElementException.class);
        wait1.until(expectation);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(BaseTest.config.getProperty("GeneralWaitInSeconds"))));
    }


    public void waitTillLoaderGetsRemoved() {
        UtilsMethods.printInfoLog("Waiting for loader to be removed");
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(loader1));
        UtilsMethods.printInfoLog("Loader Removed");
    }

    public void waitTillOverlayGetsRemoved() {
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
    }

    public void waitTillTimeLineGetsRemoved() {
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(timeLine));
    }

    //Click Method
    void click(By elementLocation) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(elementLocation));
            highLighterMethod(elementLocation);
            driver.findElement(elementLocation).click();
            log.info("Clicking on element" + elementLocation.toString());
        } catch (ElementClickInterceptedException e) {
            waitTillLoaderGetsRemoved();
            driver.findElement(elementLocation).click();
            log.info("Clicking Again on element" + elementLocation.toString());
        }
    }

    void scrollToViewElement(By element) throws InterruptedException {
        WebElement element1 = driver.findElement(element);
        waitVisibility(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
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
//        waitTillLoaderGetsRemoved();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid black;');", driver.findElement(element));
    }

    //Check the state of element
    boolean checkState(By elementLocation) {
        try {
            waitVisibility(elementLocation);
            highLighterMethod(elementLocation);
            return driver.findElement(elementLocation).isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }

    }

    //Wait For Element
    public void waitVisibility(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
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

    public CustomerProfilePage openingCustomerInteractionDashboard() {
        log.info("Opening Customer Interactions Dashboard");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Customer Interactions Dashboard");
        click(home);
        waitTillLoaderGetsRemoved();
        return new CustomerProfilePage(driver);
    }

    public String getToastMessage() {
        String message = readText(toastMessage);
        UtilsMethods.printInfoLog(message);
        return message;
    }

    //Switch to parent frame
    void switchToParentFrme() {
        driver.switchTo().parentFrame();
    }

    // is element  visible
    boolean isElementVisible(By element) {
        try {
            return driver.findElement(element).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void selectByText(String text) {
        WebElement elementby = driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementby);
        driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]")).click();
    }

    public void clickOutside() {
        Actions action = new Actions(driver);
        action.moveByOffset(0, 0).click().build().perform();
    }

    public void clearInputTag(By element) {
        log.info("Clear Search Box");
        driver.findElement(element).clear();
    }

    public boolean validateFilter(By element, String text) {
        List<WebElement> list = returnListOfElement(element);
        log.info("Validating Filter");
        for (WebElement x : list) {
            log.info("Element Text : " + x.getText());
            if (!x.getText().equalsIgnoreCase(text)) {
                return false;
            }
        }
        return true;
    }

    public List<WebElement> returnListOfElement(By element) {
        List<WebElement> list = new ArrayList<>();
        try {
            list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
        } catch (TimeoutException | NoSuchElementException e) {
            UtilsMethods.printInfoLog("Not able to Fetch List of Elements :" + e.fillInStackTrace());
        }
        return list;
    }

    public void hardWait(int time) throws InterruptedException {
        time = time * 1000;
        Thread.sleep(time);
    }

    public void hardWait() throws InterruptedException {
        hardWait(3);
    }
    public boolean elementVisibleWithExplictWait(By elementLocation) {
        WebDriverWait webDriverWait = new WebDriverWait(driver,Duration.ofSeconds(5));
        WebElement modal = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(elementLocation));
        return modal.isDisplayed();
    }
}