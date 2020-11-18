package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import tests.BaseTest;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Log4j2
public class BasePage {
    public static Properties config = BaseTest.config;
    public WebDriver driver;
    Wait<WebDriver> wait;
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    By loader1 = By.xpath("//div[@class=\"ngx-overlay foreground-closing\"]");
    By overlay = By.xpath("//mat-dialog-container[@role='dialog']");
    By timeLine = By.xpath("//app-new-loader[@class=\"ng-star-inserted\"]//div[1]");
    By home = By.xpath("//div[text()=\"HOME\"]");

    //Constructor
    public BasePage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        ExpectedCondition<Boolean> expectation = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").toString().equals("complete");
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Integer.parseInt(BaseTest.config.getProperty("GeneralWaitInSeconds"))))
                .pollingEvery(Duration.ofSeconds(Integer.parseInt(BaseTest.config.getProperty("PoolingWaitInSeconds"))))
                .ignoring(NoSuchElementException.class);
        wait.until(expectation);
    }


    public void waitTillLoaderGetsRemoved() {
        printInfoLog("Waiting for loader to be removed");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader1));
        printInfoLog("Loader Removed");
    }

    public void waitTillOverlayGetsRemoved() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
    }

    public void waitTillTimeLineGetsRemoved() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(timeLine));
    }

    //Click Method
    void click(By elementLocation) {
        wait.until(ExpectedConditions.elementToBeClickable(elementLocation));
        highLighterMethod(elementLocation);
//        WebElement element = driver.findElement(elementLocation);
//        Actions actions = new Actions(driver);
//        actions.moveToElement(element).click().perform();
        driver.findElement(elementLocation).click();
        log.info("Clicking on element" + elementLocation.toString());
    }

    void scrollToViewElement(By Element) throws InterruptedException {
        WebElement element = driver.findElement(Element);
        waitVisibility(Element);
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
//        waitTillLoaderGetsRemoved();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid black;');", driver.findElement(element));
    }

    //Check the state of element
    boolean checkState(By elementLocation) {
        waitVisibility(elementLocation);
        highLighterMethod(elementLocation);
        return driver.findElement(elementLocation).isEnabled();

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

    void hover(By elementLocation) {
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

    public String getDateFromString(String date, String pattern) {
        try {
            Date newDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").parse(date);
            DateFormat format = new SimpleDateFormat(pattern);
            return format.format(newDate);
        } catch (ParseException e) {
            printFailLog("Not able to parse the date: " + date + " " + e.fillInStackTrace());
        }
        return "Invalid Date String";
    }

    public String getDateFromStringInUTC(String date, String pattern) {
        try {
            Date newDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").parse(date);
            DateFormat format = new SimpleDateFormat(pattern);
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            return format.format(newDate);
        } catch (ParseException e) {
            printFailLog("Not able to parse the date: " + date + " " + e.fillInStackTrace());
        }
        return "Invalid Date String";
    }

    public String getDateFromEpochInUTC(long Epoch, String pattern) {
        Date date = new Date(Epoch);
        DateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
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
        try {
            return driver.findElement(Element).isDisplayed();
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

    public boolean isSortOrderDisplay(String historyDateTime, String historyDateTime1, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        final Calendar cal = Calendar.getInstance();
        try {
            if (historyDateTime.contains("Yesterday")) {
                String pattern1=pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                cal.add(Calendar.DATE, -1);
                String yesterday = format1.format(cal.getTime());
                historyDateTime=historyDateTime.replace("Yesterday",yesterday);
                System.out.println(historyDateTime+" :"+yesterday);
            }

            if (historyDateTime1.contains("Yesterday")) {
                String pattern1=pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                cal.add(Calendar.DATE, -1);
                String yesterday = format1.format(cal.getTime());
                historyDateTime1=historyDateTime1.replace("Yesterday",yesterday);
                System.out.println(historyDateTime1+" :"+yesterday);
            }

            if (historyDateTime.contains("Today")) {
                String pattern1=pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                String today = format1.format(Calendar.getInstance().getTime());
                historyDateTime=historyDateTime.replace("Today",today);
            }

            if (historyDateTime1.contains("Today")) {
                String pattern1=pattern.split("hh")[0].trim();
                DateFormat format1 = new SimpleDateFormat(pattern1);
                String today = format1.format(Calendar.getInstance().getTime());
                historyDateTime1=historyDateTime1.replace("Today",today);
            }

            Date date1 = format.parse(historyDateTime);
            Date date2 = format.parse(historyDateTime1);
            if (date1.compareTo(date2) <= 0) {
                log.info(date1 + " come before " + date2);
                return true;
            } else {
                log.info(date1 + " come after " + date2);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String convertToHR(String committedSla) {
        Long ms = Long.parseLong(committedSla);
        log.info("Converting SLA: " + committedSla + " to " + String.valueOf(TimeUnit.MILLISECONDS.toHours(ms)));
        return String.valueOf(TimeUnit.MILLISECONDS.toHours(ms));
    }

    public void printInfoLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.INFO, message);
    }

    public void printFailLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.FAIL, message);
    }

    public void printPassLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.PASS, message);
    }

    public void printWarningLog(String message) {
        log.info(message);
        ExtentTestManager.getTest().log(LogStatus.WARNING, message);
    }

    public String ValueRoundOff(Double value) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(value);
    }

    public List<WebElement> returnListOfElement(By element) {
        List<WebElement> list = new ArrayList<>();
        try {
            list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
        } catch (TimeoutException | NoSuchElementException e) {
            printInfoLog("Not able to Fetch List of Elements :" + e.fillInStackTrace());
        }
        return list;
    }
}